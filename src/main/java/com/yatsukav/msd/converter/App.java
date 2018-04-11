package com.yatsukav.msd.converter;

import ncsa.hdf.object.h5.H5File;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.yatsukav.msd.converter.Column.*;

public class App {

    private static final String SEPARATOR = "\t";
    private static final AtomicLong recordsNum = new AtomicLong();
    private static final Object lock = new Object();
    private static List<Column> columns;

    public static void main(String[] args) throws IOException {
        // check argument
        if (args.length == 0 || !Files.exists(Paths.get(args[0]))) {
            System.out.println("You should set directory for scan in first argument." +
                    "\nExample: java -jar msd_converter.jar \"./MillionSongDataset\"");
            return;
        }

        long startTime = System.currentTimeMillis();
        convert2csv(args[0], "MSD.csv");

        System.out.println("Total processing time, ms: " + (System.currentTimeMillis() - startTime));
        System.out.println("Converting is finished.");
    }

    static void convert2csv(String inputDir, String fileName) throws IOException {
        // get all HDF5 song files
        Collection<File> files = FileUtils.listFiles(new File(inputDir), new String[]{"h5"}, true);
        System.out.println("Total number of files: " + files.size());

        System.out.println("processing...");
        try (FileWriter writer = new FileWriter(fileName)) {
            // write header
            columns = defaultColumns();
            System.out.println("Columns: " + columns);
            System.out.println("Number of columns: " + columns.size());
            List<String> headers = columns.stream()
                    .map(Column::toString)
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
            writer.write(String.join(SEPARATOR, headers) + "\n");

            // split files list to process each part in parallel
            int chunkSize = files.size() / Runtime.getRuntime().availableProcessors();
            chunkSize = (chunkSize == 0) ? files.size() : chunkSize;
            List<List<File>> filesParts = ListUtils.partition(new ArrayList<>(files), chunkSize);

            // write contents
            ExecutorService executor = Executors.newWorkStealingPool();
            for (List<File> filesPart : filesParts) {
                executor.submit(() -> writeToWile(writer, filesPart));
            }
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void writeToWile(FileWriter writer, List<File> files) {
        for (File file : files) {
            try {
                // convert
                String csvData = hdf5ToCsv(file);
                // write to file
                synchronized (App.lock) {
                    writer.write(csvData);
                }
                // count amount of total processed songs
                int linesCount = csvData.replaceAll("[^\n]", "").length();
                long curRecNum = recordsNum.addAndGet(linesCount);
                // log every 10 songs
                if (curRecNum % 10 == 0) {
                    System.out.println(curRecNum + " songs was processed...");
                }
            } catch (Exception e) {
                System.out.println("Error at: " + file.getAbsolutePath());
            }
        }
    }

    private static String hdf5ToCsv(File file) {
        StringBuilder result = new StringBuilder();
        H5File h5 = Hdf5Getters.hdf5_open_readonly(file.getAbsolutePath());
        try {
            for (int songIdx = 0; songIdx < Hdf5Getters.get_num_songs(h5); songIdx++) {
                int finalSongIdx = songIdx;
                List<String> data = columns.stream().map(col -> col.getData(h5, finalSongIdx))
                        .map(obj -> (obj == null) ? "" : obj)
                        .map(obj -> (obj.equals("[]") || obj.equals("NaN") || obj.equals("null")) ? null : obj)
                        .map(obj -> (obj instanceof Number && String.valueOf(((Number) obj).doubleValue()).startsWith("0.000")) ? null : obj)
                        .map(obj -> (obj instanceof String && (((String) obj).isEmpty() || ((String) obj).contains("null"))) ? null : obj)
                        .map(obj -> (obj != null) ? String.valueOf(obj) : "")
                        .collect(Collectors.toList());
                result.append(String.join(SEPARATOR, data)).append("\n");
            }
        } finally {
            Hdf5Getters.hdf5_close(h5);
        }
        return result.toString();
    }

    private static List<Column> defaultColumns() {
        return Arrays.asList(ARTIST_NAME, ARTIST_HOTNESS, ARTIST_ID, ARTIST_LOCATION, ARTIST_LATITUDE, ARTIST_LONGITUDE,
                ARTIST_TERMS, ARTIST_TAGS, YEAR, RELEASE, TITLE, SONG_HOTNESS, DURATION, END_OF_FADE_IN, LOUDNESS,
                MODE, MODE_CONFIDENCE, START_OF_FADE_OUT, TEMPO, TIME_SIGNATURE, TIME_SIGNATURE_CONFIDENCE
        );
    }

}

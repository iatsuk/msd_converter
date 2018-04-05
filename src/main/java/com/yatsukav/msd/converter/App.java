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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class App {

    private static final String SEPARATOR = ";";
    private static final AtomicLong recordsNum = new AtomicLong();
    private static final Object lock = new Object();

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
            List<String> headers = getHeaders();
            System.out.println("Columns: " + headers);
            System.out.println("Number of columns: " + headers.size());
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
                H5File h5 = Hdf5Getters.hdf5_open_readonly(file.getAbsolutePath());
                for (int songIdx = 0; songIdx < Hdf5Getters.get_num_songs(h5); songIdx++) {
                    List<String> data = getData(h5, songIdx).stream()
                            .map(obj -> (obj == null) ? "" : obj)
                            .map(obj -> (obj.equals("[]") || obj.equals("NaN") || obj.equals("null")) ? null : obj)
                            .map(obj -> (obj instanceof Number && String.valueOf(((Number) obj).doubleValue()).startsWith("0.000")) ? null : obj)
                            .map(obj -> (obj instanceof String && ((String) obj).isEmpty()) ? null : obj)
                            .map(obj -> (obj != null) ? String.valueOf(obj) : "")
                            .collect(Collectors.toList());
                    synchronized (App.lock) {
                        writer.write(String.join(SEPARATOR, data) + "\n");
                    }
                    long curRecNum = recordsNum.incrementAndGet();
                    if (curRecNum % 10 == 0) {
                        System.out.println(curRecNum + " songs was processed...");
                    }
                }
                Hdf5Getters.hdf5_close(h5);
            } catch (Exception e) {
                System.out.println("Error at: " + file.getAbsolutePath());
                e.printStackTrace();
            }
        }
    }

    private static List<String> getHeaders() {
        return Arrays.asList(
                "artist_name",
                "artist_hotness",
                "artist_id",
                "artist_location",
                "artist_latitude",
                "artist_longitude",
                "artist_terms",
                "artist_tags",
                "year",
                "release",
                "title",
                "song_hotness",
                "duration",
                "end_of_fade_in",
                "loudness",
                "mode",
                "mode_confidence",
                "start_of_fade_out",
                "tempo",
                "time_signature",
                "time_signature_confidence"
        );
    }

    private static List<Object> getData(H5File h5, int songIdx) {
        return Arrays.asList(
                safeRead(() -> Hdf5Getters.get_artist_name(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_artist_hotttnesss(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_artist_id(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_artist_location(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_artist_latitude(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_artist_longitude(h5, songIdx)),
                safeRead(() -> "\"" + Arrays.toString(Hdf5Getters.get_artist_terms(h5, songIdx))) + "\"",
                safeRead(() -> "\"" + Arrays.toString(Hdf5Getters.get_artist_mbtags(h5, songIdx))) + "\"",
                safeRead(() -> Hdf5Getters.get_year(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_release(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_title(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_song_hotttnesss(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_duration(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_end_of_fade_in(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_loudness(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_mode(h5, songIdx) == 0 ? "major" : "minor"),
                safeRead(() -> Hdf5Getters.get_mode_confidence(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_start_of_fade_out(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_tempo(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_time_signature(h5, songIdx)),
                safeRead(() -> Hdf5Getters.get_time_signature_confidence(h5, songIdx))
        );
    }

    private static Object safeRead(Callable callable) {
        try {
            return callable.call();
        } catch (Throwable throwable) {
            return null;
        }
    }
}

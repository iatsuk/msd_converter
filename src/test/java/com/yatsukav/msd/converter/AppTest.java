package com.yatsukav.msd.converter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class AppTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void convert2csv() throws Exception {
        String srcPath = Optional.ofNullable(getClass().getClassLoader().getResource("TRAKORW128F1484DF0.h5"))
                .orElseThrow(() -> new IllegalStateException("Internal Error: No resource for test")).getPath();
        if (srcPath.contains(":")) {
            srcPath = srcPath.substring(1);
        }

        String input = Paths.get(srcPath).getParent().toString();
        String output = folder.newFile().getAbsolutePath();
        App.convert2csv(input, output);

        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(output))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        }
        assertEquals("Converting result",
                result.toString(),
                "artist_name\tartist_hotness\tartist_id\tartist_location\tartist_latitude\tartist_longitude\t" +
                        "artist_terms\tartist_tags\tyear\trelease\ttitle\tsong_hotness\tduration\tend_of_fade_in\t" +
                        "loudness\tmode\tmode_confidence\tstart_of_fade_out\ttempo\ttime_signature\ttime_signature_confidence\n" +

                        "Janet Jackson\t0.5682014218067547\tART4QZC1187FB51612\tLos Angeles &amp; New York\tNaN\tNaN\t" +
                        "\"[dance pop, hip hop, rock, urban, pop, adult contemporary, ballad, soundtrack, disco, funk, " +
                        "reggae, blues, singer, sexy, female, funky, jazz, country, classic, vocal, groove, sensual, " +
                        "soulful, diva, black, soul, fusion]\"\t\"[pop and chart, the queen, janet-jackson]\"\t1993\t" +
                        "Janet\tHold On Baby\t0.25055171998902936\t10.34404\t0.0\t-32.742\tminor\t0.318\t10.344\t61.878\t7\t0.317\n");
    }

}

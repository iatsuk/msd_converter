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
                "artist_name;artist_hotness;artist_id;artist_location;artist_latitude;artist_longitude;" +
                        "artist_terms;artist_tags;year;release;title;song_hotness;duration;end_of_fade_in;loudness;" +
                        "mode;mode_confidence;start_of_fade_out;tempo;time_signature;time_signature_confidence\n" +
                        "Janet Jackson;0.5682014218067547;ART4QZC1187FB51612;Los Angeles &amp; New York;NaN;NaN;" +

                        "\"[dance pop, hip hop, rock, urban, pop, adult contemporary, ballad, soundtrack, disco, " +
                        "funk, reggae, blues, singer, sexy, female, funky, jazz, country, classic, vocal, groove, " +
                        "sensual, soulful, diva, black, soul, fusion]\";\"[pop and chart, the queen, janet-jackson]\";" +
                        "1993;Janet;Hold On Baby;0.25055171998902936;10.34404;0.0;-32.742;minor;0.318;10.344;61.878;7;0.317\n");
    }

}

package com.yatsukav.msd.converter;

import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Optional;

public class AppTest {

    @Ignore
    @Test
    public void main() throws Exception {
        String srcPath = Optional.ofNullable(getClass().getClassLoader().getResource("TRAKORW128F1484DF0.h5"))
                .orElseThrow(() -> new IllegalStateException("Internal Error: No resource for test")).getPath();
        if (srcPath.contains(":")) {
            srcPath = srcPath.substring(1);
        }

        App.main(new String[]{Paths.get(srcPath).getParent().toString()});

        try (BufferedReader reader = new BufferedReader(new FileReader("MSD.csv"))) {
            System.out.println(reader.readLine());
            System.out.println(reader.readLine());
            System.out.println(reader.readLine());
        }
    }

}

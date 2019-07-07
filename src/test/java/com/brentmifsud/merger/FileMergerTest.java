package com.brentmifsud.merger;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileMergerTest {

    public static final FileMerger subject = new FileMerger();
    public static String[] input = {"src/test/resources/first.html", "src/test/resources/second.csv"};

    @Test
    void prepareInputFiles() {
        List<File> result = subject.prepareInputFiles(input);
        for (File file : result) {
            assertThat(file.canRead()).isEqualTo(true);
        }
    }
}
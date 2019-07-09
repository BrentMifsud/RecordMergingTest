package com.brentmifsud.merger;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class FileMergerTest {

    private static final FileMerger subject = new FileMerger();
    private static String[] input = {"src/test/resources/first.html", "src/test/resources/second.csv"};
    private static File output = new File("out/combined.csv");

    @Test
    void prepareInputFiles() {
        List<File> result = subject.prepareInputFiles(input);
        for (File file : result) {
            assertThat(file.canRead()).isTrue();
        }
    }

    @Test
    void parseFiles() {
        List<File> parseInput = subject.prepareInputFiles(input);
        Map<String, Map<String, String>> result = subject.parseFiles(parseInput);
        assertThat(result.size() == 4);
    }

    @Test
    void sortById() {
        List<File> parseInput = subject.prepareInputFiles(input);
        Map<String, Map<String, String>> result = subject.parseFiles(parseInput);
        List<String> expected = result.keySet().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        List<Map<String, String>> actual = subject.sortById(result);

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i).get("ID")).isEqualTo(expected.get(i));
        }
    }

    @Test
    void writeOutputFile() {
        if (output.exists()) output.delete();
        List<File> parseInput = subject.prepareInputFiles(input);
        Map<String, Map<String, String>> dataMap = subject.parseFiles(parseInput);
        List<Map<String, String>> sortedData = subject.sortById(dataMap);

        try {
            subject.writeOutputFile(sortedData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(output.exists()).isTrue();
    }
}
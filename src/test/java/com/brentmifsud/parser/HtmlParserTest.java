package com.brentmifsud.parser;

import com.brentmifsud.domain.FileSchema.FirstSchema;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlParserTest {

    private static final HtmlParser subject = new HtmlParser();
    private static final File input = new File("src/test/resources/first.html");
    private static final List<FirstSchema> expectedResult = Arrays.asList(
            new FirstSchema("1111", "John Smith", "123 Apple Street", "555-1234"),
            new FirstSchema("5555", "Jane Doe", "456 Orange Street", "555-5678")
    );

    @Test
    void parseTableToPojoList() {
        List<FirstSchema> result = subject.parseTableToPojoList(input, FirstSchema.class);
        assertThat(result).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedResult);
    }
}
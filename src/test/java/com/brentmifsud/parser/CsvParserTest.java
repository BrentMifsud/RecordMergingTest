package com.brentmifsud.parser;

import com.brentmifsud.domain.FileSchema.SecondSchema;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CsvParserTest {

    private static final CsvParser subject = new CsvParser();
    private static final File input = new File("src/test/resources/second.csv");
    private static final List<SecondSchema> expectedResult = Arrays.asList(
            new SecondSchema("Pilot", "Jerry Springfield", "Male", "6666"),
            new SecondSchema("Teacher", "Jane Doe", "Female", "5555"),
            new SecondSchema("Doctor", "Mary Phil", "Female", "3333")
    );

    @Test
    void parseTableToPojoList() {
        List<SecondSchema> secondSchemaList = subject.parseTableToPojoList(input, SecondSchema.class);
        assertThat(secondSchemaList).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedResult);
    }
}
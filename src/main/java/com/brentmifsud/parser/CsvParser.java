package com.brentmifsud.parser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import static com.google.common.io.Files.newReader;

public class CsvParser implements IParser {
    @Override
    public <T> List<T> parseTableToPojoList(File file, Class<T> schema) {
        MappingStrategy<Object> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(schema);

        List<T> tList = null;
        try {
            Reader reader = newReader(file, Charset.defaultCharset());
            CsvToBean cb = new CsvToBeanBuilder<>(reader)
                    .withMappingStrategy(mappingStrategy)
                    .withType(schema)
                    .build();
            tList = cb.parse();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tList;
    }
}

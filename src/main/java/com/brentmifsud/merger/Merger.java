package com.brentmifsud.merger;

import com.brentmifsud.domain.FileSchema.firstSchema;
import com.brentmifsud.domain.FileSchema.secondSchema;
import com.brentmifsud.domain.SupportedFileTypes;
import com.brentmifsud.parser.CsvParser;
import com.brentmifsud.parser.HtmlParser;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Merger {

    HtmlParser htmlParser = new HtmlParser();
    CsvParser csvParser = new CsvParser();

    public void prepareFiles(String[] filePaths) {
        List<File> files = new ArrayList<>();

        for (String filePath : filePaths) {
            File file = new File(filePath);

            if (file.canRead()) {
                files.add(new File(filePath));
            } else {
                System.out.println("Cannot read file: " + filePath);
                System.exit(1);
            }
        }

        List<Object> parsedFiles = new ArrayList<>();

        for (File file : files){
            String extension = FilenameUtils.getExtension(file.getName());

            //Add more cases as you add more supported file types
            switch(SupportedFileTypes.valueOf(extension.toUpperCase())){
                case HTML:
                    parsedFiles.add(htmlParser.parseTableToPojoList(file, firstSchema.class));
                    break;
                case CSV:
                    parsedFiles.add(csvParser.parseTableToPojoList(file, secondSchema.class));
                    break;
                default:
                    //This will never be reached
                    break;
            }
        }
    }
}

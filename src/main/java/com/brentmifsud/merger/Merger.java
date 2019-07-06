package com.brentmifsud.merger;

import com.brentmifsud.domain.FileSchema.firstSchema;
import com.brentmifsud.domain.SupportedFileTypes;
import com.brentmifsud.parser.HtmlParser;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Merger {

    HtmlParser htmlParser = new HtmlParser();

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

        for (File file : files){
            String extension = FilenameUtils.getExtension(file.getName());

            //Add more cases as you add more supported file types
            switch(SupportedFileTypes.valueOf(extension.toUpperCase())){
                case HTML:
                    htmlParser.parseTableToPojoList(file, firstSchema.class);
                    break;
                case CSV:
                    System.out.println("csvParser.parseTableToPojoList(file, firstSchema.class);");
                    break;
                default:
                    //This will never be reached
                    break;
            }
        }
    }
}

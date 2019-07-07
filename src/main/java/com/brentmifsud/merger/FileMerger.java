package com.brentmifsud.merger;

import com.brentmifsud.domain.FileSchema.FirstSchema;
import com.brentmifsud.domain.FileSchema.SecondSchema;
import com.brentmifsud.domain.SupportedFileTypes;
import com.brentmifsud.parser.CsvParser;
import com.brentmifsud.parser.HtmlParser;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static com.brentmifsud.RecordMerger.FILENAME_COMBINED;

public class FileMerger {

    //This hashmap will contain the combined record for each customer.
    private Map<String, Map<String, String>> dataMap = new HashMap<>();
    private HtmlParser htmlParser = new HtmlParser();
    private CsvParser csvParser = new CsvParser();

    public void prepareFilesForMerging(String[] filePaths) {
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

        Set<String> idSet = new HashSet<>();

        for (File file : files) {
            String extension = FilenameUtils.getExtension(file.getName());

            //Add more cases as you add more supported file types
            switch (SupportedFileTypes.valueOf(extension.toUpperCase())) {
                case HTML:
                    List<FirstSchema> htmlData = htmlParser.parseTableToPojoList(file, FirstSchema.class);
                    for (FirstSchema item : htmlData) {
                        String id = item.getId();
                        idSet.add(id);
                        Map<String, String> customer = dataMap.get(id) != null ? dataMap.get(id) : new HashMap<>();
                        customer.put("ID", id);
                        customer.put("Name", item.getName());
                        customer.put("Address", item.getAddress());
                        customer.put("PhoneNum", item.getPhoneNum());
                        dataMap.put(id, customer);
                    }
                    break;
                case CSV:
                    List<SecondSchema> csvData = csvParser.parseTableToPojoList(file, SecondSchema.class);
                    for (SecondSchema item : csvData) {
                        String id = item.getId();
                        idSet.add(id);
                        Map<String, String> customer = dataMap.get(id) != null ? dataMap.get(id) : new HashMap<>();
                        customer.put("ID", id);
                        customer.put("Name", item.getName());
                        customer.put("Gender", item.getGender());
                        customer.put("Occupation", item.getOccupation());
                        dataMap.put(id, customer);
                    }
                    break;
                default:
                    //This will never be reached
                    break;
            }
        }

        try {
            writeOutputFile(dataMap, idSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeOutputFile(Map<String, Map<String, String>> dataMap, Set<String> idSet) throws IOException {
        //Find the record with the most fields
        int maxProperties = 0;
        String maxPropertiesId = "";
        for (String id : idSet) {
            int size = dataMap.get(id).values().size();
            if (size > maxProperties) {
                maxPropertiesId = id;
                maxProperties = size;
            }
        }


        //Prepare Output File
        File outputFile = new File("out/" + FILENAME_COMBINED);
        if (outputFile.getParentFile() != null) outputFile.getParentFile().mkdirs();
        //If theres already an output file, clear it.
        if (outputFile.exists()) outputFile.delete();
        outputFile.createNewFile();

        PrintWriter pw = new PrintWriter(outputFile);


        //Build and write header
        List<String> headerFields = new ArrayList<>();
        dataMap.get(maxPropertiesId).forEach((k, v) -> headerFields.add(k));
        StringBuilder headerLine = new StringBuilder();
        for (int i = 0; i < headerFields.size(); i++) {
            if (i == headerFields.size() - 1) {
                headerLine
                        .append('"')
                        .append(headerFields.get(i))
                        .append('"');
            } else {
                headerLine
                        .append('"')
                        .append(headerFields.get(i))
                        .append('"')
                        .append(',');
            }
        }
        pw.println(headerLine);

        //Build and write the merged records
        for (String item : idSet) {
            Map<String, String> record = dataMap.get(item);
            StringBuilder recordString = new StringBuilder();
            int i = 0;
            for (String field : headerFields) {
                String rec = record.get(field);
                if (i == headerFields.size() - 1) {
                    if (rec != null)
                        recordString
                                .append('"')
                                .append(rec)
                                .append('"');
                } else {
                    if (rec != null)
                        recordString
                                .append('"')
                                .append(rec)
                                .append('"')
                                .append(',');
                    else recordString
                            .append('"')
                            .append('"')
                            .append(',');
                }
                i++;
            }
            pw.println(recordString);
        }
        pw.close();
    }
}

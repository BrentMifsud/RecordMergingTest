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
import java.util.stream.Collectors;

import static com.brentmifsud.RecordMerger.FILENAME_COMBINED;

public class FileMerger {
    private HtmlParser htmlParser = new HtmlParser();
    private CsvParser csvParser = new CsvParser();

    /**
     * Validates the files passed in as arguments
     *
     * @param args file paths
     * @return List of Files
     */
    public List<File> prepareInputFiles(String[] args) {
        // Ensure all input files are valid
        List<File> files = new ArrayList<>();
        for (String arg : args) {
            if (!SupportedFileTypes.isSupportedFileType(arg)) {
                System.out.println("Input file extension is not supported.");
                System.out.println("Please input one of the following file types:");
                for (SupportedFileTypes fileExtension : SupportedFileTypes.values()) {
                    System.out.println("- " + fileExtension.name());
                }
            } else {
                File file = new File(arg);
                if (file.canRead()) files.add(file);
                else {
                    System.out.println("Cannot read File: " + file.getName());
                    System.exit(1);
                }
            }
        }
        return files;
    }

    public Map<String, Map<String, String>> parseFiles(List<File> files) {
        Map<String, Map<String, String>> dataMap = new HashMap<>();

        for (File file : files) {
            String extension = FilenameUtils.getExtension(file.getName());

            //Add more cases as you add more supported file types
            switch (SupportedFileTypes.valueOf(extension.toUpperCase())) {
                case HTML:
                    List<FirstSchema> htmlData = htmlParser.parseTableToPojoList(file, FirstSchema.class);
                    for (FirstSchema item : htmlData) {
                        String id = item.getId();
                        Map<String, String> customer = dataMap.get(id) != null ? dataMap.get(id) : new HashMap<>();
                        customer.put("ID", id);
                        customer.put("Name", item.getName());
                        customer.put("Address", item.getAddress());
                        customer.put("PhoneNum", item.getPhoneNum());
                        dataMap.put(id, customer);

                    }
                    System.out.println("Finished parsing: " + file.getName());
                    break;
                case CSV:
                    List<SecondSchema> csvData = csvParser.parseTableToPojoList(file, SecondSchema.class);
                    for (SecondSchema item : csvData) {
                        String id = item.getId();
                        Map<String, String> customer = dataMap.get(id) != null ? dataMap.get(id) : new HashMap<>();
                        customer.put("ID", id);
                        customer.put("Name", item.getName());
                        customer.put("Gender", item.getGender());
                        customer.put("Occupation", item.getOccupation());
                        dataMap.put(id, customer);

                    }
                    System.out.println("Finished parsing: " + file.getName());
                    break;
                default:
                    //This will never be reached
                    break;
            }
        }

        return dataMap;
    }

    public List<Map<String, String>> sortById(Map<String, Map<String, String>> dataMap) {
        //Sort records by ID
        List<String> sortedKeys = dataMap.keySet()
                .stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        List<Map<String,String>> sortedData = new ArrayList<>();

        for (String key : sortedKeys){
            sortedData.add(dataMap.get(key));
        }

        return sortedData;
    }

    public void writeOutputFile(List<Map<String,String>> mapList) throws IOException {
        //Find the record that has data in every column.
        //We need the fields from this record to generically build the header row without knowing what each column is.
        int maxPropertiesCount = 0;
        Map<String,String> maxProperties = null;
        for (Map<String,String> dataItem : mapList) {
            if (dataItem.values().size() >= maxPropertiesCount) {
                maxPropertiesCount = dataItem.values().size();
                maxProperties = dataItem;
            }
        }

        //Build and write header
        List<String> headerFields = maxProperties.entrySet()
                        .stream()
                        .map(entry -> entry.getKey())
                        .collect(Collectors.toList());

        String headerLine = getOutputHeader(headerFields);

        PrintWriter pw;
        try {
            pw = getOutputFilePrintWriter();
        } catch (IOException e) {
            throw new IOException("Unable to create PrintWriter for output");
        }

        pw.println(headerLine);

        //Build and write the merged records
        for (Map<String,String> record : mapList) {
            StringBuilder recordString = getOutputRecord(headerFields, record);
            pw.println(recordString);
        }

        //Close the print writer
        pw.close();
    }


    //Helper Methods

    private PrintWriter getOutputFilePrintWriter() throws IOException {
        //Prepare Output File
        File outputFile = new File("out/" + FILENAME_COMBINED);
        if (outputFile.getParentFile() != null) outputFile.getParentFile().mkdirs();

        //If there is already an output file, clear it.
        if (outputFile.exists()) outputFile.delete();

        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            throw new IOException("Unable to create output file: " + outputFile.getName());
        }

        return new PrintWriter(outputFile);
    }

    private String getOutputHeader(List<String> headerFields) {
        StringBuilder headerLine = new StringBuilder();
        for (int i = 0; i < headerFields.size(); i++) {
            if (i == headerFields.size() - 1) {
                headerLine.append('"').append(headerFields.get(i)).append('"');
            } else headerLine.append('"').append(headerFields.get(i)).append('"').append(',');
        }
        return headerLine.toString();
    }

    private StringBuilder getOutputRecord(List<String> headerFields, Map<String, String> record) {
        StringBuilder recordString = new StringBuilder();
        int i = 0;
        for (String field : headerFields) {
            String rec = record.get(field);
            if (i == headerFields.size() - 1) {
                if (rec != null) recordString.append('"').append(rec).append('"');
            } else {
                if (rec != null) recordString.append('"').append(rec).append('"').append(',');
                else recordString.append('"').append('"').append(',');
            }
            i++;
        }
        return recordString;
    }
}

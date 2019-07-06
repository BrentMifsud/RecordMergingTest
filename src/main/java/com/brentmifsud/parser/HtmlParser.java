package com.brentmifsud.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class HtmlParser implements IParser {

    /**
     * Parse an html table into a pojo
     * @param file html file
     * @param schema the schema for an html table record
     * @param <T> the schema type
     * @return Returns a pojo of type T
     */
    @Override
    public <T> List<T> parseFileToPojo(File file, Class<T> schema) {
        //Get Constructor to build T class
        Constructor tConstructor = schema.getConstructors()[0];
        tConstructor.setAccessible(true);

        //Get fields to access T class properties
        Field[] fields = schema.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            System.out.println(field);
        }

        //Parse html with jsoup
        Document doc = null;

        try {
            doc = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Get the table
        Element table = requireNonNull(doc).getElementById("directory");

        //Get the rows from the table
        Elements rows = table.getElementsByTag("tr");

        //Get the header row
        Elements headerElements = rows.get(0).getElementsByTag("th");

        //Populate the headers into a list of strings.
        //This will be used with the reflected fields to produce a pojo.
        List<String> headers = new ArrayList<>();

        for (Element column : headerElements) {
            headers.add(column.childNode(0).toString());
        }



        List<T> tList = new ArrayList<>();

        return tList;
    }
}

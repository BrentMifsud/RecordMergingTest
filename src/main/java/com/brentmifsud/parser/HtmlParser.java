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
     *
     * @param file   html file
     * @param schema the schema for an html table record
     * @param <T>    the schema type
     * @return Returns a pojo of type T
     */
    @Override
    public <T> List<T> parseTableToPojoList(File file, Class<T> schema) {
                //Parse html with jsoup
        Document doc = null;

        try {
            doc = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Get the table
        Elements table = requireNonNull(doc).getElementsByTag("tr");

        List<T> tList = getTs(schema, table);

        return tList;
    }

    private <T> List<T> getTs(Class<T> schema, Elements rows) {
        //Produce list of T from remaining table rows
        List<T> tList = new ArrayList<>();

        //Get Constructor and fields to work with generic class
        Constructor tConstructor = schema.getConstructors()[0];
        Field[] fields = schema.getDeclaredFields();

        //Set constructor and fields as accessible
        tConstructor.setAccessible(true);
        for (Field field : fields) {
            field.setAccessible(true);
        }

        for (int i = 1; i < rows.size(); i++) {
            List<String> values = getChildStrings(rows.get(i).getElementsByTag("td"));

            try {
                T t = schema.newInstance();
                for (int f = 0; f < fields.length; f++) {
                    fields[f].set(t, values.get(f));
                }
                tList.add(t);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return tList;
    }

    private List<String> getChildStrings(Elements row) {
        List<String> values = new ArrayList<>();
        for (Element element : row) {
            values.add(element.text());
        }
        return values;
    }
}

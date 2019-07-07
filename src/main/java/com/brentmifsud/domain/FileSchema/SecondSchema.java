package com.brentmifsud.domain.FileSchema;

import com.opencsv.bean.CsvBindByName;

public class SecondSchema {
    @CsvBindByName(column = "Occupation")
    private String occupation;
    @CsvBindByName(column = "Name")
    private String name;
    @CsvBindByName(column = "Gender")
    private String gender;
    @CsvBindByName(column = "ID")
    private String id;

    public SecondSchema() {
    }

    public SecondSchema(String occupation, String name, String gender, String id) {
        this.occupation = occupation;
        this.name = name;
        this.gender = gender;
        this.id = id;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "SecondSchema{" +
                "occupation='" + occupation + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

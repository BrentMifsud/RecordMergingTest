package com.brentmifsud.domain.FileSchema;

import com.opencsv.bean.CsvBindByName;

public class secondSchema {
    @CsvBindByName(column = "Occupation")
    private String occupation;
    @CsvBindByName(column = "Name")
    private String name;
    @CsvBindByName(column = "Gender")
    private String gender;
    @CsvBindByName(column = "ID")
    private String id;

    public secondSchema() {
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "secondSchema{" +
                "occupation='" + occupation + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

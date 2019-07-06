package com.brentmifsud.domain.FileSchema;

public class secondSchema {
    String occupation;
    String name;
    String gender;
    String id;

    public secondSchema(String occupation, String name, String gender, String id) {
        this.occupation = occupation;
        this.name = name;
        this.gender = gender;
        this.id = id;
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

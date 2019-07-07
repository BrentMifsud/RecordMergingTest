package com.brentmifsud.domain.FileSchema;

/**
 * This is the schema for first.html
 */
public class FirstSchema {
    private String id;
    private String name;
    private String address;
    private String phoneNum;

    public FirstSchema() {
    }

    public FirstSchema(String id, String name, String address, String phoneNum) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    @Override
    public String toString() {
        return "FirstSchema{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}

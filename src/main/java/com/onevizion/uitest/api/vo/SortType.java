package com.onevizion.uitest.api.vo;

public enum SortType {

    ASC("Sort ASC", "asc", "0"),
    DESC("Sort DESC", "des", "1");

    private String name;
    private String typeString;
    private String typeNumber;

    private SortType(String name, String typeString, String typeNumber) {
        this.name = name;
        this.typeString = typeString;
        this.typeNumber = typeNumber;
    }

    public String getName() {
        return name;
    }

    public String getTypeString() {
        return typeString;
    }

    public String getTypeNumber() {
        return typeNumber;
    }

}
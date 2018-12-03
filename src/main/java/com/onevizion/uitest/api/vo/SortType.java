package com.onevizion.uitest.api.vo;

public enum SortType {

    ASC("Sort ASC"),
    DESC("Sort DESC");

    private String name;

    private SortType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
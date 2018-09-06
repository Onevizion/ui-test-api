package com.onevizion.guitest.vo;

public enum DateFormatType {

    MM_DD_YYYY_SLASH("MM/DD/YYYY"),
    DD_MM_YYYY_SLASH("DD/MM/YYYY"),
    MM_DD_YYYY_DOT("MM.DD.YYYY"),
    DD_MM_YYYY_DOT("DD.MM.YYYY"),
    MM_DD_YY_SLASH("MM/DD/YY"),
    DD_MM_YY_SLASH("DD/MM/YY"),
    MM_DD_YY_DOT("MM.DD.YY"),
    DD_MM_YY_DOT("DD.MM.YY");

    private String format;

    private DateFormatType(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

}
package com.onevizion.uitest.api.vo;

public enum DateFormatType {

    MM_DD_YYYY_SLASH("MM/DD/YYYY", "MM/dd/yyyy", "/"),
    DD_MM_YYYY_SLASH("DD/MM/YYYY", "dd/MM/yyyy", "/"),
    MM_DD_YYYY_DOT("MM.DD.YYYY", "MM.dd.yyyy", "."),
    DD_MM_YYYY_DOT("DD.MM.YYYY", "dd.MM.yyyy", "."),
    MM_DD_YY_SLASH("MM/DD/YY", "MM/dd/yy", "/"),
    DD_MM_YY_SLASH("DD/MM/YY", "dd/MM/yy", "/"),
    MM_DD_YY_DOT("MM.DD.YY", "MM.dd.yy", "."),
    DD_MM_YY_DOT("DD.MM.YY", "dd.MM.yy", ".");

    private String format;
    private String javaFormat;
    private String separator;

    private DateFormatType(String format, String javaFormat, String separator) {
        this.format = format;
        this.javaFormat = javaFormat;
        this.separator = separator;
    }

    public String getFormat() {
        return format;
    }

    public String getJavaFormat() {
        return javaFormat;
    }

    public String getSeparator() {
        return separator;
    }

}
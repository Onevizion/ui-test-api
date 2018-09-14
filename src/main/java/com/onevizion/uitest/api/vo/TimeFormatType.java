package com.onevizion.uitest.api.vo;

public enum TimeFormatType {

    HOURS_12("hh:mm:ss aa"),
    HOURS_24("HH:mm:ss");

    private String format;

    private TimeFormatType(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}

package com.onevizion.uitest.api.vo;

public enum TimeFormatType {

    HOURS_12("12 Hour (hh:mm:ss aa)", "hh:mm:ss aa"),
    HOURS_24("24 Hour (HH:mm:ss)", "HH:mm:ss");

    private String label;
    private String value;

    private TimeFormatType(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

}
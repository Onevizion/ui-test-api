package com.onevizion.uitest.api.vo;

public enum WpDatePairType {

    BASELINE("Baseline"),
    PROJECTED("Projected"),
    PROMISED("Promised"),
    ACTUAL("Actual"),
    EARLY("Early Date"),
    LATE("Late Date"),
    PROJECTED_DELTA("Projected Delta");

    private String name;

    private WpDatePairType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
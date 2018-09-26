package com.onevizion.uitest.api.vo;

public enum DashAxisType {

    X("X-Axis"),
    Y("Y-Axis");

    private String name;

    private DashAxisType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
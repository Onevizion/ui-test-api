package com.onevizion.guitest.vo;

public enum PrivType {

    R("R"),
    RA("RA"),
    RE("RE"),
    REA("REA"),
    READ("READ"),
    RED("RED"),
    NONE("NONE");

    private String name;

    private PrivType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
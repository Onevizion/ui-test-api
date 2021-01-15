package com.onevizion.uitest.api.vo;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

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

    public static PrivType getForStr(String privTypeStr) {
        if ("R".equals(privTypeStr)) {
            return PrivType.R;
        } else if ("RA".equals(privTypeStr)) {
            return PrivType.RA;
        } else if ("RE".equals(privTypeStr)) {
            return PrivType.RE;
        } else if ("REA".equals(privTypeStr)) {
            return PrivType.REA;
        } else if ("READ".equals(privTypeStr)) {
            return PrivType.READ;
        } else if ("RED".equals(privTypeStr)) {
            return PrivType.RED;
        } else if ("NONE".equals(privTypeStr)) {
            return PrivType.NONE;
        } else {
            throw new SeleniumUnexpectedException();
        }
    }

}
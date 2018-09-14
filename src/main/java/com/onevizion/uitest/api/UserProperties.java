package com.onevizion.uitest.api;

import org.springframework.stereotype.Component;

@Component
public class UserProperties {

    private String dateFormat;
    private String javaTimeFormat;

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getJavaTimeFormat() {
        return javaTimeFormat;
    }

    public void setJavaTimeFormat(String javaTimeFormat) {
        this.javaTimeFormat = javaTimeFormat;
    }

    public String getJavaDateTimeFormat() {
        return dateFormat + " " + javaTimeFormat;
    }

}
package com.onevizion.uitest.api.vo;

public enum SecGroupType {

    REPORT("Report"),
    TOUR("Trackor Tour"),
    RULE("Rule"),
    REPORT_DELIVERY("Report Delivery"),
    NOTIFICATION("Notification"),
    MENU("Menu Application"),
    IMPORT("Import"),
    NOTIFICATION_TYPE("Notification Type"),
    VIEW("Global View"),
    DISCIPLINE("Discipline");

    private String name;

    private SecGroupType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
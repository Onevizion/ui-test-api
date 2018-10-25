package com.onevizion.uitest.api.vo;

public enum BplComponentType {

    APPLET("Config App (0)", "Description"),
    FIELD("Config Field (0)", "Field Name"),
    TAB("Config Tab (0)", "Description"),
    VTABLE("Configured V_Tables (0)", "Table Name"),
    DB_PACKAGE("DB Package (0)", "Package Name"),
    FILTER("Filter Options (0)", "Filter Name"),
    IMPORT("Import (0)", "Employee Name"),
    INTEGRATION("Integration (0)", "Integration Name"),
    DB_MAT_VIEW("Materialized View (0)", "Name"),
    MENU("Menu Application (0)", "Employee Name"),
    NOTIFICATION("Notification (0)", "Filter Name"),
    REPORT("Report (0)", "Report Name"),
    RULE("Rule (0)", "Rule Name"),
    SEC_ROLE("Security Role (0)", "Role Name"),
    BPD_ITEM("Static Documentation Items (0)", ""),
    TRACKOR_FORM("Trackor Form (0)", "Form Name"),
    TRACKOR_TOUR("Trackor Tour (0)", "Tour Label"),
    TRACKOR_TYPE("Trackor Type (0)", "Trackor Type"),
    VIEW("View Options (0)", "View Name"),
    WORKFLOW("WorkFlow (0)", "WF Template Name"),
    WORKPLAN("WorkPlan (0)", "Template Name");

    private String name;
    private String columnName;

    private BplComponentType(String name, String columnName) {
        this.name = name;
        this.columnName = columnName;
    }

    public String getName() {
        return name;
    }

    public String getColumnName() {
        return columnName;
    }

}
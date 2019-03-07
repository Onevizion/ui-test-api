package com.onevizion.uitest.api.vo;

public enum BplComponentType {

    APPLET("Config App", "Description"),
    FIELD("Config Field", "Field Name"),
    TAB("Config Tab", "Description"),
    VTABLE("Configured V_Tables", "Table Name"),
    DB_PACKAGE("DB Package", "DB Package Name"),
    FILTER("Filter Options", "Filter Name"),
    IMPORT("Import", "Employee Name"),
    INTEGRATION("Integration", "Integration Name"),
    DB_MAT_VIEW("Materialized View", "Name"),
    MENU("Menu Application", "Employee Name"),
    NOTIFICATION("Notification", "Filter Name"),
    REPORT("Report", "Report Name"),
    RULE("Rule", "Rule Name"),
    SEC_ROLE("Security Role", "Role Name"),
    BPD_ITEM("Static Documentation Items", ""),
    TRACKOR_FORM("Trackor Form", "Form Name"),
    TRACKOR_TOUR("Trackor Tour", "Tour Label"),
    TRACKOR_TYPE("Trackor Type", "Trackor Type"),
    VIEW("View Options", "View Name"),
    WORKFLOW("WorkFlow", "WF Template Name"),
    WORKPLAN("WorkPlan", "Template Name");

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
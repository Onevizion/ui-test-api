package com.onevizion.uitest.api.vo;

public enum BplComponentType {

    APPLET("Applet", "Description"),
    FIELD("Field", "Field Name"),
    TAB("Configured Tab", "Description"),
    VTABLE("Dynamic V_Tables", "Table Name"),
    DB_PACKAGE("DB Package", "Package Name"),
    FILTER("Filter", "Filter Name"),
    IMPORT("Import", "Name"),
    INTEGRATION("Integration", "Integration Name"),
    DB_MAT_VIEW("Materialized View", "Name"),
    MENU("Menu Application", "Name"),
    NOTIFICATION("Notifications", "Filter Name"),
    REPORT("Report", "Report Name"),
    RULE("Rule", "Rule Name"),
    SEC_ROLE("Security Role", "Role Name"),
    BPD_ITEM("Static Documentation Items", ""),
    TRACKOR_FORM("Trackor Form", "Form Name"),
    TRACKOR_TOUR("Trackor Tours", "Tour Label"),
    TRACKOR_TYPE("Trackor Type", "Trackor Type"),
    VIEW("View Option", "View Name"),
    WORKFLOW("WorkFlow", "WF Template Name"),
    WORKPLAN("WP Template", "Template Name");

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
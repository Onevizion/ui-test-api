package com.onevizion.uitest.api.vo;

public enum RuleTypeType {

    CHANGE_PASSWORD("User Password Updated (After)"),
    FORM_BUTTONS("Form Buttons"),
    ON_LOGON("Login Attempted (After)"),
    EFILE_BLOB_DATA_UPDATE("EFile BLOB_DATA Updated (After)"),
    BY_TIME_HOURLY("Execute Hourly"),
    BY_TIME_MIDNIGHT("Execute Nightly"),
    FIELD_UPDATE("Field Updated (After)"),
    IMPORT_COMPLETE("Import Completed (After)"),
    IMPORT_START("Import Started (After)"),
    RELATION_CREATE("Relation Created (After)"),
    RELATION_DELETE_AFTER("Relation Deleted (After)"),
    RELATION_DELETE_BEFORE("Relation Deleted (Before)"),
    TRACKOR_CREATE("Trackor Created (After)"),
    TRACKOR_DELETE("Trackor Deleted (Before)"),
    TRACKOR_UPDATE("Trackor Updated (After)"),
    TRACKOR_CREATE_AND_UPDATE("Trackor Created/Updated (After)"),
    USER_CREATE("User Created (After)"),
    USER_UPDATE("User Updated (After)"),
    REPORT_EXECUTED("Report Executed (After)"),
    REPORT_EXECUTED_SUCCESS("Report Executed - Success (After)"),
    REPORT_EXECUTED_DELIVERED("Report Executed - Delivered (After)"),
    WF_DELETE("WorkFlow Deleted (Before)"),
    WF_FINISH("WorkFlow Finished (After)"),
    WF_START("WorkFlow Started (After)"),
    WF_UPDATE("WorkFlow Updated (After)"),
    WP_CREATE("WorkPlan Created (After)"),
    WP_DELETE("WorkPlan Deleted (Before)"),
    WP_UPDATE("WorkPlan Updated (After)"),
    WP_TASK_UPDATE("Task Updated (After)"),
    WP_TASK_DATE_UPDATE("Task Date Updated (After)"),
    COMMENT_CREATE_TRACKOR("Trackor Chat Message Created (After)"),
    COMMENT_CREATE_TASK("Task Chat Message Created (After)"),
    SHARE_PAGE("Page Shared (After)");

    private String name;

    private RuleTypeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
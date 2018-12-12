package com.onevizion.uitest.api.vo;

public enum RuleTypeType {

    CHANGE_PASSWORD("User Password Updated"),
    FORM_BUTTONS("Form Buttons"),
    ON_LOGON("Login Attempted"),
    EFILE_BLOB_DATA_UPDATE("EFile BLOB_DATA Updated"),
    BY_TIME_HOURLY("Execute Hourly"),
    BY_TIME_MIDNIGHT("Execute Nightly"),
    CONFIG_FIELD_UPDATE("Configured Field Updated"),
    IMPORT_COMPLETE("Import Completed"),
    IMPORT_START("Import Start"),
    RELATION_CREATE("Relation Created"),
    RELATION_DELETE("Relation Deleted"),
    TRACKOR_CREATE("Trackor Created"),
    TRACKOR_DELETE("Trackor Deleted"),
    TRACKOR_UPDATE("Trackor Updated"),
    USER_CREATE("User Created"),
    USER_UPDATE("User Updated"),
    REPORT_EXECUTED("Report Executed"),
    REPORT_EXECUTED_SUCCESS("Report Executed - Success"),
    WF_DELETE("WorkFlow Deleted"),
    WF_FINISH("WorkFlow Finished"),
    WF_START("WorkFlow Started"),
    WF_UPDATE("WorkFlow Updated"),
    WP_CREATE("WorkPlan Created"),
    WP_DELETE("WorkPlan Deleted"),
    WP_UPDATE("WorkPlan Updated"),
    WP_TASK_UPDATE("Task Updated"),
    WP_TASK_DATE_UPDATE("Task Date Updated");

    private String name;

    private RuleTypeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
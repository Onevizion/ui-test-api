package com.onevizion.uitest.api.vo;

public enum RuleTypeType {

    CHANGE_PASSWORD("After User Password Update"),
    FORM_BUTTONS("Form Buttons"),
    ON_LOGON("After Login Attempt"),
    EFILE_BLOB_DATA_UPDATE("After EFile BLOB_DATA Update"),
    BY_TIME_HOURLY("Execute Hourly"),
    BY_TIME_MIDNIGHT("Execute Nightly"),
    CONFIG_FIELD_UPDATE("After Configured Field Update"),
    IMPORT_COMPLETE("After Import Complete"),
    IMPORT_START("After Import Start"),
    RELATION_CREATE("After Relation Create"),
    RELATION_DELETE_AFTER("After Relation Delete"),
    RELATION_DELETE_BEFORE("Before Relation Delete"),
    TRACKOR_CREATE("After Trackor Create"),
    TRACKOR_DELETE("Before Trackor Delete"),
    TRACKOR_UPDATE("After Trackor Update"),
    USER_CREATE("After User Create"),
    USER_UPDATE("After User Update"),
    REPORT_EXECUTED("After Report Executed"),
    REPORT_EXECUTED_SUCCESS("After Report Executed - Success"),
    REPORT_EXECUTED_DELIVERED("After Report Executed - Delivered"),
    WF_DELETE("Before WorkFlow Delete"),
    WF_FINISH("After WorkFlow Finish"),
    WF_START("After WorkFlow Start"),
    WF_UPDATE("After WorkFlow Update"),
    WP_CREATE("After WorkPlan Create"),
    WP_DELETE("Before WorkPlan Delete"),
    WP_UPDATE("After WorkPlan Update"),
    WP_TASK_UPDATE("After Task Update"),
    WP_TASK_DATE_UPDATE("After Task Date Update");

    private String name;

    private RuleTypeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
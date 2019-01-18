package com.onevizion.uitest.api.vo;

public enum NotificationType {

    TRACKOR_ADDED("Trackor Added", "TRACKOR_ADDED"),
    TRACKOR_UPDATED("Trackor Updated", "TRACKOR_UPDATED"),
    TRACKOR_DELETED("Trackor Deleted", "TRACKOR_DELETED"),
    TASK_DATE_CHANGED("Task Date Changed", "TASK_DATE_CHANGED"),
    WF_STEP_STARTED("WF Step Started", "WF_STEP_STARTED"),
    WF_STEP_FINISHED("WF Step Finished", "WF_STEP_FINISHED"),
    INTEGRATION_STARTED("Integration Started", "INTEGRATION_STARTED"),
    INTEGRATION_FINISHED_SUCCESS("Integration Finished Successfully", "INTEGRATION_FINISHED_SUCCESS"),
    INTEGRATION_FINISHED_WITH_ERROR("Integration Finished with Errors", "INTEGRATION_FINISHED_WITH_ERROR");

    private String type;
    private String name;

    private NotificationType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}
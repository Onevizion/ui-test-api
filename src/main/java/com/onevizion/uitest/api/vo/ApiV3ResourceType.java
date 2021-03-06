package com.onevizion.uitest.api.vo;

public enum ApiV3ResourceType {

    AUTH_TOKEN("auth-token"),
    AUTHORIZE("authorize"),
    CHATS("chats"),
    COMPONENTS_EXPORT("components-export"),
    COMPONENTS_IMPORT("components-import"),
    EXPORTS("exports"),
    FIELD_COLORS("field-colors"),
    FILES("files"),
    FILTERS("filters"),
    IMPORTS("imports"),
    INTEGRATION_LOGS("integration-logs"),
    INTEGRATION_RUNS("integration-runs"),
    INTEGRATIONS("integrations"),
    LOCKS("locks"),
    OPTIONS("options"),
    PARAMS("params"),
    PRIVILEGES("privileges"),
    PROGRAMS("programs"),
    REPORTS("reports"),
    SECURITY_ROLES("security-roles"),
    SERVER_INFO("server-info"),
    TASKS("tasks"),
    TRACKOR_TYPE_SPECS("trackor-type-specs"),
    TRACKOR_TYPES_TREE("trackor-types-tree"),
    TRACKORS("trackors"),
    USER_SETTINGS("user-settings"),
    USERS("users"),
    VIEWS("views"),
    WORKPLANS("workplans");

    private String name;

    private ApiV3ResourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
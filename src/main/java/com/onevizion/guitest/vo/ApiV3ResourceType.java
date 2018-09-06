package com.onevizion.guitest.vo;

public enum ApiV3ResourceType {

    EXPORTS("exports"),
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
    TASKS("tasks"),
    TRACKOR_TYPE_SPECS("trackor-type-specs"),
    TRACKOR_TYPES_TREE("trackor-types-tree"),
    TRACKORS("trackors"),
    USER_SETTINGS("user-settings"),
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
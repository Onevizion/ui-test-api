package com.onevizion.uitest.api.vo;

public enum ApiV3EndpointType {

    IMPORTS_READ_RUN_BY_PROCESS_ID("GET", "/v3/imports/runs/{process_id}", "Read import run by process id"),
    IMPORTS_READ("GET", "/v3/imports", "Read imports specs"),
    IMPORTS_READ_RUNS("GET", "/v3/imports/runs", "Read import runs"),
    IMPORTS_SEARCH_RUNS("GET", "/v3/imports/{import_id}/runs", "Search import runs"),
    IMPORTS_INTERRUPT("POST", "/v3/imports/runs/{process_id}/interrupt", "Interrupt import"),
    IMPORTS_RUN("POST", "/v3/imports/{import_id}/run", "Run import"),
    PARAMS_READ_PROGRAM_PARAM("GET", "/v3/params/program/{param_name}", "Read Program Parameter"),
    PARAMS_READ_SYSTEM_PARAM("GET", "/v3/params/system/{param_name}", "Read System Parameter"),
    PROGRAMS_CREATE_PROGRAM("POST", "/v3/programs", "ID of new Program created"),
    SECURITY_ROLES_READ("GET", "/v3/security-roles/{role_name}/users", "Read Users assigned to Security Role"),
    USER_SETTINGS_READ("GET", "/v3/user_settings", "Read user settings"),
    VIEWS_READ_VIEWS("GET", "/v3/trackor_types/{trackor_type}/views", "Read Views"),
    VIEWS_READ_VIEW_COLUMNS("GET", "/v3/trackor_types/{trackor_type}/views/{view_name}", "Read View fields"),
    VIEWS_SET_VIEW_AS_CURRENT("POST", "/v3/trackor_types/{trackor_type}/views/{view_name}/set_as_current", "Set view as current"),
    WORKPLANS_READ_WPS("GET", "/v3/wps", "Read Workplans"),
    WORKPLANS_READ_WP("GET", "/v3/wps/{workplan_id}", "Read Workplan by id"),
    WORKPLANS_CREATE_WP("POST", "/v3/trackors/{trackor_id}/assign_wp", "Assign Workplan to Trackor");

    private String method;
    private String path;
    private String description;

    private ApiV3EndpointType(String method, String path, String description) {
        this.method = method;
        this.path = path;
        this.description = description;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

}
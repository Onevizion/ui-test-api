package com.onevizion.uitest.api.vo;

public enum ApiV3EndpointType {

    IMPORTS_READ_RUN_BY_PROCESS_ID("GET", "/v3/imports/runs/{process_id}", "Read import run by process id"),
    IMPORTS_READ("GET", "/v3/imports", "Read imports specs"),
    IMPORTS_READ_RUNS("GET", "/v3/imports/runs", "Read import runs"),
    IMPORTS_SEARCH_RUNS("GET", "/v3/imports/{import_id}/runs", "Search import runs"),
    IMPORTS_INTERRUPT("POST", "/v3/imports/runs/{process_id}/interrupt", "Interrupt import"),
    IMPORTS_RUN("POST", "/v3/imports/{import_id}/run", "Run import"),
    SECURITY_ROLES_READ("GET", "/v3/security-roles/{role_name}/users", "Read Users assigned to Security Role");

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
package com.onevizion.uitest.api.vo;

public enum ApiV3EndpointType {

    AUTH_TOKEN_CREATE("POST", "/v3/auth_token", "Generate API Authentication Token"),

    CHATS_UNSUBSCRIBE("DELETE", "/v3/chats/{chat_id}/subscribers", "Unsubscribe users to Chat"),
    CHATS_READ_SUBSCRIBERS("GET", "/v3/chats/{chat_id}/subscribers", "Read Chat subscribers"),
    CHATS_READ_CHATS("GET", "/v3/chats", "Find Chats by Trackor ID or Task ID"),
    CHATS_READ_USERS("GET", "/v3/chats/{chat_id}/authors", "Read Chat authors"),
    CHATS_READ_MESSAGES("GET", "/v3/chats/{chat_id}/messages", "Load Chat Messages"),
    CHATS_SUBSCRIBE("POST", "/v3/chats/{chat_id}/subscribers", "Subscribe users to Chat"),
    CHATS_CREATE_CHAT("POST", "/v3/chats", "Create new Chat"),
    CHATS_CREATE_MESSAGE("POST", "/v3/chats/{chat_id}/messages", "Create Chat Message"),

    FIELD_COLORS_READ_COLOR_BY_TT_AND_TIDS("GET", "/v3/trackor_types/{trackor_type}/trackors/fields/colors", "Read Field Colors by Trackor Type and Trackor ids"),
    FIELD_COLORS_READ_OPTIONS("GET", "/v3/trackor_types/{trackor_type}/fields/{field_name}/colors/options", "Read Field Color options"),
    FIELD_COLORS_READ_COLOR_BY_TID("GET", "/v3/trackors/{trackor_id}/fields/colors", "Read Field Colors by Trackor id"),
    FIELD_COLORS_UPDATE("PUT", "/v3/trackors/{trackor_id}/fields/{field_name}/colors", "Update Field Color by Trackor ID and Field name"),

    FILTERS_READ_FILTERS_BY_TT("GET", "/v3/trackor_types/{trackor_type}/filters", "Read filters by Trackor Type"),
    FILTERS_READ_FILTER_CONDITIONS("GET", "/v3/trackor_types/{trackor_type}/filters/{filter_name}", "Read filter conditions"),
    FILTERS_SET_FILTER_AS_CURRENT("POST", "/v3/trackor_types/{trackor_type}/filters/{filter_name}/set_as_current", "Set filter as current"),
    
    IMPORTS_READ_RUN_BY_PROCESS_ID("GET", "/v3/imports/runs/{process_id}", "Read import run by process id"),
    IMPORTS_READ("GET", "/v3/imports", "Read imports specs"),
    IMPORTS_READ_RUNS("GET", "/v3/imports/runs", "Read import runs"),
    IMPORTS_SEARCH_RUNS("GET", "/v3/imports/{import_id}/runs", "Search import runs"),
    IMPORTS_INTERRUPT("POST", "/v3/imports/runs/{process_id}/interrupt", "Interrupt import"),
    IMPORTS_RUN("POST", "/v3/imports/{import_id}/run", "Run import"),

    INTEGRATION_LOGS_READ_LOGS("GET", "/v3/integrations/runs/logs", "Read Integration Logs"),
    INTEGRATION_LOGS_READ_LOG("GET", "/v3/integrations/runs/logs/{integration_log_id}", "Read Integration Log by Integration Log Id"),
    INTEGRATION_LOGS_READ_LOG_FILE("GET", "/v3/integrations/runs/logs/{integration_log_id}/file", "Read Integration Log File by Integration Log Id"),
    INTEGRATION_LOGS_CREATE_LOG("POST", "/v3/integrations/runs/{process_id}/logs", "Create Integration Log"),
    INTEGRATION_LOGS_CREATE_LOG_WITH_FILE("POST", "/v3/integrations/runs/{process_id}/logs/file", "Create Integration Log with file"),

    INTEGRATION_RUNS_READ_RUNS("GET", "/v3/integrations/runs", "Read Integration Runs"),
    INTEGRATION_RUNS_READ_RUN("GET", "/v3/integrations/runs/{process_id}", "Read Integration Run by Process Id"),

    INTEGRATIONS_READ_INTEGRATIONS("GET", "/v3/integrations", "Read Integrations"),
    INTEGRATIONS_READ_INTEGRATION("GET", "/v3/integrations/{integration_name}", "Read Integration by Name"),
    INTEGRATIONS_READ_SETTINGS_FILE("GET", "/v3/integrations/{integration_name}/settings", "Read Integration Settings File by Integration Name"),

    LOCKS_READ_FOR_TRACKORS("GET", "/v3/trackor_types/{trackor_type}/trackors/locks", "Read locks by Trackor Type and Trackor ids"),
    LOCKS_READ_FOR_TRACKOR("GET", "/v3/trackors/{trackor_id}/locks", "Read locks by Trackor id"),

    OPTIONS_READ("GET", "/v3/trackors/{trackor_id}/options/{field_name}", "Read Field options"),

    PARAMS_READ_PROGRAM_PARAM("GET", "/v3/params/program/{param_name}", "Read Program Parameter"),
    PARAMS_READ_SYSTEM_PARAM("GET", "/v3/params/system/{param_name}", "Read System Parameter"),

    PRIVILEGES_READ("GET", "/v3/privs", "Read privileges"),

    PROGRAMS_CREATE_PROGRAM("POST", "/v3/programs", "ID of new Program created"),

    SECURITY_ROLES_READ("GET", "/v3/security-roles/{role_name}/users", "Read Users assigned to Security Role"),

    SERVER_INFO_READ("GET", "/v3/server_info", "Read server information"),

    TASKS_READ_TASK("GET", "/v3/tasks", "Read Task"),
    TASKS_READ_TASK_BY_ID("GET", "/v3/tasks/{task_id}", "Read Task by Task id"),
    TASKS_READ_TASKS("GET", "/v3/wps/{workplan_id}/tasks", "Read Tasks by Workplan id"),
    TASKS_UPDATE_TASK_PART("PATCH", "/v3/tasks/{task_id}", "Update Task Partial"),
    TASKS_UPDATE_TASK_PART_BY_TRACKOR_ID("PATCH", "/v3/trackors/{trackor_id}/wps/{workplan_name}/tasks", "Update Task Partial by Trackor id and Workplan name"),
    TASKS_UPDATE_TASK_PART_BY_WP_ID("PATCH", "/v3/wps/{workplan_id}/tasks", "Update Task Partial by Workplan id"),
    TASKS_UPDATE_TASK_FULL("PUT", "/v3/tasks/{task_id}", "Update Task"),

    TRACKOR_TYPE_SPECS_READ_FIELDS("GET", "/v3/trackor_types/{trackor_type}", "Read Fields specs"),
    TRACKOR_TYPE_SPECS_READ_TAB("GET", "/v3/trackor_types/applets/{applet_id}/tabs/{tab_id}", "Read Tab spec"),
    TRACKOR_TYPE_SPECS_READ_TABS("GET", "/v3/trackor_types/{trackor_type}/applets/{applet_id}/tabs", "Read Tabs specs"),
    TRACKOR_TYPE_SPECS_READ_APPLET("GET", "/v3/trackor_types/{trackor_type}/applets/{applet_id}", "Read Applet spec"),
    TRACKOR_TYPE_SPECS_READ_APPLETS("GET", "/v3/trackor_types/{trackor_type}/applets", "Read Applets specs"),

    TRACKOR_TYPES_TREE_READ_TREE("GET", "/v3/trackor_tree", "Read Trackor tree"),
    TRACKOR_TYPES_TREE_READ_ALL_TTS("GET", "/v3/trackor_types", "Read all Trackor Types"),
    TRACKOR_TYPES_TREE_READ_CHILDRENS("GET", "/v3/trackor_types/{trackor_type}/children", "Read children Trackor Types"),
    TRACKOR_TYPES_TREE_READ_PARENTS("GET", "/v3/trackor_types/{trackor_type}/parents", "Read parent Trackor Types"),

    TRACKORS_DELETE("DELETE", "/v3/trackor_types/{trackor_type}/trackors", "Delete Trackor"),
    TRACKORS_READ("GET", "/v3/trackor_types/{trackor_type}/trackors", "Read Trackor data"),
    TRACKORS_READ_BY_ID("GET", "/v3/trackors/{trackor_id}", "Read Trackor data by Trackor id"),
    TRACKORS_READ_APPLET("GET", "/v3/trackors/{trackor_id}/applets/{applet_id}", "Read Trackor Applet data"),
    TRACKORS_READ_TAB("GET", "/v3/trackors/{trackor_id}/applets/{applet_id}/tabs/{tab_id}", "Read Trackor Tab data"),
    TRACKORS_CREATE("POST", "/v3/trackor_types/{trackor_type}/trackors", "Create Trackor"),
    TRACKORS_SEARCH("POST", "/v3/trackor_types/{trackor_type}/trackors/search", "Search Trackors"),
    TRACKORS_UPDATE("PUT", "/v3/trackor_types/{trackor_type}/trackors", "Update Trackor"),
    TRACKORS_UPDATE_BY_ID("PUT", "/v3/trackors/{trackor_id}", "Update Trackor data"),

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
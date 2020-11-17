package com.onevizion.uitest.api.vo;

public enum RuleClassType {

    PL_SQL("PL/SQL"),
    GEOCODING("Geocoding"),
    ZIP_EFILES("ZipEFiles"),
    COPY_EFILE("Copy EFile"),
    HTTP_CALL("HTTP Call"),
    FORWARD_GEOCODING("Forward Geocoding"),
    TOWER_DIAGRAM("Tower Diagram"),
    MAP_SNIPPET("Map Snippet"),
    TOWER_PROFILE("Tower Profile"),
    PROCESS_NOTIFICATIONS("Process Notifications");

    private String name;

    private RuleClassType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
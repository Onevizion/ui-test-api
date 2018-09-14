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
    GEOCODING_ARCGIS("Geocoding ArcGIS"),
    FORWARD_GEOCODING_ARCGIS("Forward Geocoding ArcGIS"),
    TOWER_PROFILE("Tower Profile");

    private String name;

    private RuleClassType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
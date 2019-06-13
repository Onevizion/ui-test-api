package com.onevizion.uitest.api.vo;

public enum ConfigFieldType {

    TEXT("Text", "Text", "TEXT"),
    NUMBER("Number", "Number", "NUMBER"),
    DATE("Date", "Date", "DATE"),
    CHECKBOX("Checkbox", "Checkbox", "CHECKBOX"),
    DROP_DOWN("Drop-Down", "Drop-Down", "DROP_DOWN"),
    MEMO("Memo", "Memo", "MEMO"),
    WIKI("Wiki", "Wiki", "WIKI"),
    DB_DROP_DOWN("DB Drop-Down", "DB Drop-Down", "DB_DD"),
    DB_SELECTOR("DB Selector", "DB Selector", "DB_SEL"),
    SELECTOR("Selector", "Selector", "SELECTOR"),
    LATITUDE("Latitude", "Latitude", "LATITUDE"),
    LONGITUDE("Longitude", "Longitude", "LONGITUDE"),
    ELECTRONIC_FILE("Electronic File", "EFile", "EFILE"),
    TRACKOR_SELECTOR("Trackor Selector", "Tr Sel", "TR_SEL"),
    MULTI_TRACKOR_SELECTOR("Trackor MultiSelector", "Multi Tr Sel", "MULTI_TR_SEL"),
    TRACKOR_DROP_DOWN("Trackor Drop-Down", "Tr DD", "TR_DD"),
    CALCULATED("Calculated", "Calculated", "CALC"),
    HYPERLINK("Hyperlink", "Hyperlink", "HYPERLINK"),
    ROLLUP("Rollup", "Rollup", "ROLLUP"),
    MULTI_SELECTOR("MultiSelector", "MultiSelector", "MULTI_SEL"),
    DATE_TIME("Date/Time", "Date/Time", "DATE_TIME"),
    TIME("Time", "Time", "TIME");

    private String label;
    private String shortLabel; //Field name can not be longer than 30 characters and can not be a reserved Oracle word
    private String shortName; //Field name can not be longer than 30 characters and can not be a reserved Oracle word

    private ConfigFieldType(String label, String shortLabel, String shortName) {
        this.label = label;
        this.shortLabel = shortLabel;
        this.shortName = shortName;
    }

    public String getLabel() {
        return label;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public String getShortName() {
        return shortName;
    }

}
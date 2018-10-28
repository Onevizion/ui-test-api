package com.onevizion.uitest.api.vo;

public enum ConfigFieldEfileMetadataType {

    LATITUDE("Latitude"),
    LONGITUDE("Longitude"),
    DATE_TIME_TEXT("Date Time(Text)"),
    DATE_TIME("Date Time"),
    DATE("Date"),
    TIME("Time"),
    HEIGHT("Height"),
    WIDTH("Width"),
    CAMERA_MAN("Camera Manufacturer"),
    CAMERA_MODEL("Camera Model"),
    F_NUMBER("F-Number"),
    EXPOSURE_TIME("Exposure Time"),
    FLASH("Flash"),
    FOCAL_LENGTH("Focal Length"),
    COLOR_SPACE("Color Space"),
    FILE_TYPE("File Type"),
    VIDEO_DURATION("Video Duration(seconds)");

    private String label;

    private ConfigFieldEfileMetadataType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
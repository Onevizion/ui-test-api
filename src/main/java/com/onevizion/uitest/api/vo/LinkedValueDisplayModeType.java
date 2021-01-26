package com.onevizion.uitest.api.vo;

public enum LinkedValueDisplayModeType {

    HINT(1, "Hint"),
    IN_PLACE(2, "In Place");

    private int id;
    private String label;

    private LinkedValueDisplayModeType(int id, String label) {
        this.id = id;
        this.label = label;
    }
    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

}
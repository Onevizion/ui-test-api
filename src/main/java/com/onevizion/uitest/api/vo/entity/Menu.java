package com.onevizion.uitest.api.vo.entity;

public class Menu {

    private String name;
    private String label;
    private String description;
    private String orderNumber;
    private String dropgridFolder;
    private String iconLabel;

    public Menu(String name, String label, String description, String orderNumber, String dropgridFolder, String iconLabel) {
        this.name = name;
        this.label = label;
        this.description = description;
        this.orderNumber = orderNumber;
        this.dropgridFolder = dropgridFolder;
        this.iconLabel = iconLabel;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getDropgridFolder() {
        return dropgridFolder;
    }

    public String getIconLabel() {
        return iconLabel;
    }

}
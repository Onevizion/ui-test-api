package com.onevizion.uitest.api.vo.entity;

public class Menu {

    private String name;
    private String description;
    private String defaultMenuItem;
    private String dropgridFolder;
    private String showTipOfTheDay;

    public Menu(String name, String description, String defaultMenuItem, String dropgridFolder, String showTipOfTheDay) {
        this.name = name;
        this.description = description;
        this.defaultMenuItem = defaultMenuItem;
        this.dropgridFolder = dropgridFolder;
        this.showTipOfTheDay = showTipOfTheDay;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDefaultMenuItem() {
        return defaultMenuItem;
    }

    public String getDropgridFolder() {
        return dropgridFolder;
    }

    public String getShowTipOfTheDay() {
        return showTipOfTheDay;
    }

}
package com.onevizion.uitest.api.helper.tab;

import org.openqa.selenium.WebElement;

public class ListboxElementTab {

    //TODO need create AbstractListboxElement

    private WebElement webElement;
    private String id;
    private String label;
    private String rowsCount;

    public WebElement getWebElement() {
        return webElement;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(String rowsCount) {
        this.rowsCount = rowsCount;
    }

}
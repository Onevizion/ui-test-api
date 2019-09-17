package com.onevizion.uitest.api.vo;

import org.openqa.selenium.WebElement;

public class ListboxElement {

    private WebElement webElement;
    private String id;
    private String label;

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

}
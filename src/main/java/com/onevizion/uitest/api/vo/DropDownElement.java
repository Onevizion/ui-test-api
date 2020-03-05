package com.onevizion.uitest.api.vo;

import org.openqa.selenium.WebElement;

public class DropDownElement {

    private WebElement webElement;
    private String label;

    public WebElement getWebElement() {
        return webElement;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
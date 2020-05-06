package com.onevizion.uitest.api.helper.chat;

import org.openqa.selenium.WebElement;

public class ListboxElementChatSubscriber {

    //TODO need create AbstractListboxElement

    private WebElement webElement;
    private String id;
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
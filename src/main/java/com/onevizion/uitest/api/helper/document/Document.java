package com.onevizion.uitest.api.helper.document;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.jquery.Jquery;

@Component
public class Document {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private DocumentWait documentWait;

    @Autowired
    private Jquery jquery;

    @Autowired
    private ElementWait elementWait;

    public void open(String page) {
        waitReadyStateComplete();
        jquery.waitLoad();

        open2(page);

        waitReadyStateComplete();
        jquery.waitLoad();
    }

    public void open2(String page) {
        WebElement html = seleniumSettings.getWebDriver().findElement(By.tagName("html"));
        seleniumSettings.getWebDriver().get("about:blank");
        seleniumSettings.getWebDriver().get(page);
        elementWait.waitElementNotExist(html);
    }

    public void waitReadyStateComplete() {
        documentWait.waitReadyStateComplete();
    }

}
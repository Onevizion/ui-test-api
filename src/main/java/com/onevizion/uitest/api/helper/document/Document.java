package com.onevizion.uitest.api.helper.document;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.jquery.Jquery;

@Component
public class Document {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private DocumentWait documentWait;

    @Resource
    private Jquery jquery;

    @Resource
    private ElementWait elementWait;

    public void open(String page) {
        waitReadyStateComplete();
        jquery.waitLoad();

        open2(page);
    }

    public void open2(String page) {
        WebElement html = seleniumSettings.getWebDriver().findElement(By.tagName("html"));
        seleniumSettings.getWebDriver().get("about:blank");
        seleniumSettings.getWebDriver().get(page);
        elementWait.waitElementNotExist(html);

        waitReadyStateComplete();
        jquery.waitLoad();
    }

    public void waitReadyStateComplete() {
        documentWait.waitReadyStateComplete();
    }

}
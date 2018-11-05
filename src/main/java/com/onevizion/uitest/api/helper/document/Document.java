package com.onevizion.uitest.api.helper.document;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class Document {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private DocumentWait documentWait;

    public void open(String page) {
        seleniumSettings.getWebDriver().get(page);
        waitReadyStateComplete();
    }

    public void waitReadyStateComplete() {
        documentWait.waitReadyStateComplete();
    }

}
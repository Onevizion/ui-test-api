package com.onevizion.guitest.helper.document;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.SeleniumSettings;

@Component
class DocumentWaitHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private DocumentJsHelper documentJsHelper;

    void waitReadyStateComplete() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for ReadyState complete is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return documentJsHelper.isReadyStateComplete();
                }
            });
    }

}
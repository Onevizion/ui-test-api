package com.onevizion.uitest.api.helper.document;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class DocumentWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private DocumentJs documentJs;

    void waitReadyStateComplete() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for ReadyState complete is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return documentJs.isReadyStateComplete();
                }
            });
    }

}
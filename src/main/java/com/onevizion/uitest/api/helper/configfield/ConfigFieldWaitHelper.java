package com.onevizion.uitest.api.helper.configfield;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class ConfigFieldWaitHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ConfigFieldJsHelper configFieldJsHelper;

    void waitFieldNameUpdated() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for JQuery loading is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return configFieldJsHelper.isFieldNameUpdated();
                }
            });
    }

}
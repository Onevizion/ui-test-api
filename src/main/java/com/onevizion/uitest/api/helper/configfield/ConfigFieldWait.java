package com.onevizion.uitest.api.helper.configfield;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class ConfigFieldWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ConfigFieldJs configFieldJs;

    void waitFieldNameUpdated() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for JQuery loading is failed")
            .until(webdriver -> configFieldJs.isFieldNameUpdated());
    }

}
package com.onevizion.uitest.api.helper.jquery;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class JqueryWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private JqueryJs jqueryJs;

    void waitJQueryLoad() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for JQuery loading is failed")
            .until(webdriver -> jqueryJs.isJQueryNotActive());
    }

}
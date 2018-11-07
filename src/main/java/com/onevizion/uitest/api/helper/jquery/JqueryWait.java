package com.onevizion.uitest.api.helper.jquery;

import javax.annotation.Resource;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class JqueryWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private JqueryJs jqueryJs;

    void waitJQueryLoad() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for JQuery loading is failed")
            .until(webdriver -> jqueryJs.isJQueryNotActive());
    }

}
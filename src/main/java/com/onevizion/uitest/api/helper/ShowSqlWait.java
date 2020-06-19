package com.onevizion.uitest.api.helper;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class ShowSqlWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ShowSqlJs showSqlJs;

    void waitIsUsageLogUpdated(Long gridId) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Usage Log updating is failed")
            .until(webdriver -> showSqlJs.isUsageLogUpdated(gridId));
    }

}
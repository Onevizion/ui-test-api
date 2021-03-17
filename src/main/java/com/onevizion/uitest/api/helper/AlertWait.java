package com.onevizion.uitest.api.helper;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class AlertWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    public void waitAlert() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Timed out after " + seleniumSettings.getDefaultTimeout() + " seconds waiting for alert.")
            .until(webdriver -> webdriver.switchTo().alert().getText() != null);
    }

}
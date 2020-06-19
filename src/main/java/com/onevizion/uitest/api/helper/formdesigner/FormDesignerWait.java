package com.onevizion.uitest.api.helper.formdesigner;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Wait;

@Component
class FormDesignerWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Wait wait;

    void waitFormDesignerLoad() {
        wait.waitWebElement(By.id("loaderformDes"));

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for form is failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !webdriver.findElement(By.id("loaderformDes")).isDisplayed());
    }

}
package com.onevizion.uitest.api.helper.formdesigner;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Wait;

@Component
class FormDesignerWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Wait wait;

    @Resource
    private FormDesignerJs formDesignerJs;

    void waitFormDesignerLoad() {
        wait.waitWebElement(By.id("loaderformDes"));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for form is failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return !seleniumSettings.getWebDriver().findElement(By.id("loaderformDes")).isDisplayed();
                }
            });
    }

    void waitListBoxReady() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for listBox loading is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return formDesignerJs.isReadyListBox();
                }
            });
    }

}
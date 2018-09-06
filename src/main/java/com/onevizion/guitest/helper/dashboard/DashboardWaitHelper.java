package com.onevizion.guitest.helper.dashboard;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.AbstractSeleniumCore;
import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.helper.WaitHelper;

@Component
class DashboardWaitHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private WaitHelper waitHelper;

    void waitDashboardPageLoaded() {
        waitHelper.waitWebElement(By.id("dashContainer"));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + 0L));

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
        .withMessage("Waiting for dashboard is failed.")
        .ignoring(StaleElementReferenceException.class)
        .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return !seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + 0L)).isDisplayed();
            }
        });
    }

    void waitDashboardLoad() {
        waitHelper.waitWebElement(By.id("loaderDashboard"));

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
        .withMessage("Waiting for dashboard is failed.")
        .ignoring(StaleElementReferenceException.class)
        .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return !seleniumSettings.getWebDriver().findElement(By.id("loaderDashboard")).isDisplayed();
            }
        });
    }

}
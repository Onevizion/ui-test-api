package com.onevizion.uitest.api.helper.dashboard;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Wait;

@Component
class DashboardWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Wait wait;

    void waitDashboardPageLoaded() {
        wait.waitWebElement(By.id("dashContainer"));
        wait.waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + 0L));

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for dashboard is failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !webdriver.findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + 0L)).isDisplayed());
    }

    void waitDashboardLoad() {
        wait.waitWebElement(By.id("loaderDashboard"));

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for dashboard is failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> webdriver.findElement(By.id("loaderDashboard")).isDisplayed());

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for dashboard is failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !webdriver.findElement(By.id("loaderDashboard")).isDisplayed());
    }

}
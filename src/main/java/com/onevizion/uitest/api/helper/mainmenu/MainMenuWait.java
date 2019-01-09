package com.onevizion.uitest.api.helper.mainmenu;

import java.util.function.Supplier;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class MainMenuWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private MainMenuJs mainMenuJs;

    void waitPageTitle(String title) {
        Supplier<String> actualValueSupplier = ()-> seleniumSettings.getWebDriver().findElement(By.id("ttlPage")).getAttribute("textContent");

        Supplier<String> messageSupplier = ()-> "Waiting for Page Title expectedVal=[" + title + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> title.equals(actualValueSupplier.get()));
    }

    void waitTabTitle(String title) {
        Supplier<String> actualValueSupplier = ()-> seleniumSettings.getWebDriver().findElement(By.id("ttlTab")).getAttribute("textContent");

        Supplier<String> messageSupplier = ()-> "Waiting for Tab Title expectedVal=[" + title + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> title.equals(actualValueSupplier.get()));
    }

    void waitLeftMenuSearchUpdated() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for JQuery loading is failed")
            .until(webdriver -> mainMenuJs.isLeftMenuSearchUpdated());
    }

}
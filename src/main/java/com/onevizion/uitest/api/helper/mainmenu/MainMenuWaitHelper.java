package com.onevizion.uitest.api.helper.mainmenu;

import java.util.function.Supplier;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class MainMenuWaitHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private MainMenuJsHelper mainMenuJsHelper;

    void waitPageTitle(String title) {
        Supplier<String> actualValueSupplier = ()-> {
            return seleniumSettings.getWebDriver().findElement(By.id("ttlPage")).getText();
        };

        Supplier<String> messageSupplier = ()-> {
            return "Waiting for Page Title expectedVal=[" + title + "] actualVal=[" + actualValueSupplier.get() + "] is failed";
        };

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return title.equals(actualValueSupplier.get());
                }
            });
    }

    void waitLeftMenuSearchUpdated() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for JQuery loading is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return mainMenuJsHelper.isLeftMenuSearchUpdated();
                }
            });
    }

}
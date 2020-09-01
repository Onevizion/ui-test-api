package com.onevizion.uitest.api.helper.page.button;

import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class PageButtonWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    void waitButtonDisabled(String buttonId) {
        BooleanSupplier actualValueSupplier = ()-> {
            List<String> classes = Arrays.asList(seleniumSettings.getWebDriver().findElement(By.id(buttonId)).getAttribute("class").split(" "));
            return classes.contains("disabled");
        };

        Supplier<String> messageSupplier = ()-> "Waiting for Button with id=[" + buttonId + "] disabled is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> actualValueSupplier.getAsBoolean());
    }

    void waitButtonEnabled(String buttonId) {
        BooleanSupplier actualValueSupplier = ()-> {
            List<String> classes = Arrays.asList(seleniumSettings.getWebDriver().findElement(By.id(buttonId)).getAttribute("class").split(" "));
            return !classes.contains("disabled");
        };

        Supplier<String> messageSupplier = ()-> "Waiting for Button with id=[" + buttonId + "] enabled is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> actualValueSupplier.getAsBoolean());
    }

}
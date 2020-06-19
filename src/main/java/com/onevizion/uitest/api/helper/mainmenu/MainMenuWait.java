package com.onevizion.uitest.api.helper.mainmenu;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class MainMenuWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private MainMenuJs mainMenuJs;

    @Autowired
    private MainMenu mainMenu;

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

    void waitDinamicMenusCount(int dinamicMenusCount) {
        IntSupplier actualValueSupplier = ()-> mainMenu.getDinamicMenusCount();

        Supplier<String> messageSupplier = ()-> "Waiting dinamic menus count expectedVal=[" + dinamicMenusCount + "] actualVal=[" + actualValueSupplier.getAsInt() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> dinamicMenusCount == actualValueSupplier.getAsInt());
    }

}
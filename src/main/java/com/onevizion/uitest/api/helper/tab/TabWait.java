package com.onevizion.uitest.api.helper.tab;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Wait;

@Component
class TabWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Wait wait;

    void waitLoad(int position) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for tab with position=[" + position + "] is failed")
            .until(webdriver -> !webdriver.findElement(By.id("divPage" + position)).getAttribute("innerHTML").contains("Loading Tab. Please wait..."));

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for tab with position=[" + position + "] is failed")
            .until(webdriver -> "2".equals(webdriver.findElement(By.id("divPage" + position)).getAttribute("loadingstate")));

        wait.waitAllImagesLoad();
    }

}
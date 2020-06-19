package com.onevizion.uitest.api.helper.map;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class MapWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private MapJs mapJs;

    void waitIsMapLoaded() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Map loading is failed")
            .until(webdriver -> mapJs.isMapLoaded());
    }

}
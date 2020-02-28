package com.onevizion.uitest.api.helper.mapper;

import javax.annotation.Resource;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class MapperWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private MapperJs mapperJs;

    public void waitIsMapLoaded() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Map loading is failed")
            .until(webdriver -> mapperJs.isMapLoaded());
    }

}
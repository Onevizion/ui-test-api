package com.onevizion.uitest.api.helper.wiki;

import javax.annotation.Resource;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class FckEditorWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private FckEditorJs fckEditorJs;

    void waitReady(String name) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for FckEditor ready is failed")
            .until(webdriver -> fckEditorJs.isReady(name));
    }

}
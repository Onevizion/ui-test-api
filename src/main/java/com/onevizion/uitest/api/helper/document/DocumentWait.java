package com.onevizion.uitest.api.helper.document;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class DocumentWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private DocumentJs documentJs;

    void waitReadyStateComplete() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for ReadyState complete is failed")
            .until(webdriver -> documentJs.isReadyStateComplete());
    }

}
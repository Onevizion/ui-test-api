package com.onevizion.uitest.api.helper;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class ListboxWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ListboxJs listboxJs;

    void waitIsReadyListbox(String listboxId) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for listbox [" + listboxId + "] loading is failed")
            .until(webdriver -> listboxJs.isReadyListbox(listboxId));
    }

}
package com.onevizion.uitest.api.helper;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class RelationSelectorWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private RelationSelectorJs relationSelectorJs;

    void waitIsReadyRelationSelector(Long gridIdx) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Relation Selector loading is failed")
            .until(webdriver -> relationSelectorJs.isReadyRelationSelector(gridIdx));
    }

    void waitIsReadyMutationObserver(Long gridIdx) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
        .withMessage("Waiting for Mutation Observer loading is failed")
        .until(webdriver -> relationSelectorJs.isReadyMutationObserver(gridIdx));
    }

}
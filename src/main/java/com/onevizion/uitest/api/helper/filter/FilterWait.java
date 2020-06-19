package com.onevizion.uitest.api.helper.filter;

import java.util.function.Supplier;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class FilterWait {

    @Autowired
    private Filter filter;

    @Autowired
    private SeleniumSettings seleniumSettings;

    void waitCurrentFilterName(Long gridIdx, String filterName) {
        Supplier<String> actualValueSupplier = ()-> filter.getCurrentFilterName(gridIdx);

        Supplier<String> messageSupplier = ()-> "Waiting for Current Filter Name gridIdx=[" + gridIdx + "] expectedVal=[" + filterName + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> filterName.equals(actualValueSupplier.get()));
    }

}
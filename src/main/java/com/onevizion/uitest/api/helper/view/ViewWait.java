package com.onevizion.uitest.api.helper.view;

import java.util.function.Supplier;

import javax.annotation.Resource;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class ViewWait {

    @Resource
    private View view;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ViewJs viewJs;

    void waitCurrentViewName(Long gridIdx, String viewName) {
        Supplier<String> actualValueSupplier = ()-> view.getCurrentViewName(gridIdx);

        Supplier<String> messageSupplier = ()-> "Waiting for Current View Name gridIdx=[" + gridIdx + "] expectedVal=[" + viewName + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> viewName.equals(actualValueSupplier.get()));
    }

    void waitLeftListBoxReady() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for leftListBox loading is failed")
            .until(webdriver -> viewJs.isReadyLeftListBox());
    }

    void waitRightListBoxReady() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for rightListBox loading is failed")
            .until(webdriver -> viewJs.isReadyRightListBox());
    }

}
package com.onevizion.guitest.helper.view;

import java.util.function.Supplier;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.SeleniumSettings;

@Component
class ViewWaitHelper {

    @Resource
    private ViewHelper viewHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ViewJsHelper viewJsHelper;

    void waitCurrentViewName(Long gridIdx, String viewName) {
        Supplier<String> actualValueSupplier = ()-> {
            return viewHelper.getCurrentViewName(gridIdx);
        };

        Supplier<String> messageSupplier = ()-> {
            return "Waiting for Current View Name gridIdx=[" + gridIdx + "] expectedVal=[" + viewName + "] actualVal=[" + actualValueSupplier.get() + "] is failed";
        };

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return viewName.equals(actualValueSupplier.get());
                }
            });
    }

    void waitLeftListBoxReady() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for leftListBox loading is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return viewJsHelper.isReadyLeftListBox();
                }
            });
    }

    void waitRightListBoxReady() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for rightListBox loading is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return viewJsHelper.isReadyRightListBox();
                }
            });
    }

}
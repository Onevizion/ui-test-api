package com.onevizion.guitest.helper;

import java.util.function.Supplier;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.SeleniumSettings;

@Component
public class FilterWaitHelper {

    @Resource
    private FilterHelper filterHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    public void waitCurrentFilterName(Long gridIdx, String filterName) {
        Supplier<String> actualValueSupplier = ()-> {
            return filterHelper.getCurrentFilterName(gridIdx);
        };

        Supplier<String> messageSupplier = ()-> {
            return "Waiting for Current Filter Name gridIdx=[" + gridIdx + "] expectedVal=[" + filterName + "] actualVal=[" + actualValueSupplier.get() + "] is failed";
        };

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return filterName.equals(actualValueSupplier.get());
                }
            });
    }

}
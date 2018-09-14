package com.onevizion.uitest.api.helper.grid;

import java.util.function.Supplier;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class Grid2Wait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Grid2Js grid2Js;

    void waitLoadAllRows(Long gridIdx) {
        Supplier<Boolean> actualValueSupplier = ()-> {
            return grid2Js.isLoadAllRowsDone(gridIdx);
        };

        Supplier<String> messageSupplier = ()-> {
            return "Waiting for load all rows is failed";
        };

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return actualValueSupplier.get();
                }
            });
    }

}
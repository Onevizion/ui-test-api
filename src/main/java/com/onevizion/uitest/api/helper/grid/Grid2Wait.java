package com.onevizion.uitest.api.helper.grid;

import java.util.function.Supplier;

import javax.annotation.Resource;

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
        Supplier<Boolean> actualValueSupplier = ()-> grid2Js.isLoadAllRowsDone(gridIdx);

        Supplier<String> messageSupplier = ()-> "Waiting for load all rows is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> actualValueSupplier.get());
    }

}
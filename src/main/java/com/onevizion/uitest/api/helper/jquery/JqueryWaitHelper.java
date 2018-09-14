package com.onevizion.uitest.api.helper.jquery;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class JqueryWaitHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private JqueryJsHelper jqueryJsHelper;

    public void waitJQueryLoad() {
        waitJquery();
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for JQuery loading is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return jqueryJsHelper.isJQueryNotActive();
                }
            });
    }

    private void waitJquery() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for JQuery is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return jqueryJsHelper.isJqueryExist();
                }
            });
    }

}
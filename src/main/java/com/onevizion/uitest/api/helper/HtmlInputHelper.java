package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class HtmlInputHelper {

    private final Logger logger = LoggerFactory.getLogger(HtmlInputHelper.class);

    @Resource
    private SeleniumSettings seleniumSettings;

    public void setValueByName(String name, String value) {
        boolean isException = true;
        while (isException) {
            seleniumSettings.getWebDriver().findElement(By.name(name)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(name)).sendKeys(value);

            String actualValue = seleniumSettings.getWebDriver().findElement(By.name(name)).getAttribute("value");
            if (value.equals(actualValue)) {
                isException = false;
            } else {
                logger.warn(seleniumSettings.getTestName() + " setValue works wrong. value[" + value + "] actualValue[" + actualValue + "]");
            }
        }
    }

}
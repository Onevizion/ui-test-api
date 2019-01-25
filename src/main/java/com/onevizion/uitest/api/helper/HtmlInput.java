package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumLogger;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class HtmlInput {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private SeleniumLogger seleniumLogger;

    //TODO
    //I think problem what not exist waiting and other js code change focus
    //need add waiting in all places instead use this method
    @Deprecated
    public void setValueByName(String name, String value) {
        boolean isException = true;
        while (isException) {
            seleniumSettings.getWebDriver().findElement(By.name(name)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(name)).sendKeys(value);

            String actualValue = seleniumSettings.getWebDriver().findElement(By.name(name)).getAttribute("value");
            if (value.equals(actualValue)) {
                isException = false;
            } else {
                seleniumLogger.warn(seleniumSettings.getTestName() + " setValue works wrong. value[" + value + "] actualValue[" + actualValue + "]");
            }
        }
    }

}
package com.onevizion.uitest.api.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class Alert {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private AlertWait alertWait;

    public void accept() {
        alertWait.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();
    }

    public void accept(String message) {
        alertWait.waitAlert();
        Assert.assertEquals(seleniumSettings.getWebDriver().switchTo().alert().getText(), message, "Alert have wrong message");
        seleniumSettings.getWebDriver().switchTo().alert().accept();
    }

    public void acceptWithErrorReportId(String message) {
        alertWait.waitAlert();
        String actualErrorStr = seleniumSettings.getWebDriver().switchTo().alert().getText().substring(0, seleniumSettings.getWebDriver().switchTo().alert().getText().indexOf("Error Report ID"));
        Assert.assertEquals(actualErrorStr, message, "Alert have wrong message");
        seleniumSettings.getWebDriver().switchTo().alert().accept();
    }

}
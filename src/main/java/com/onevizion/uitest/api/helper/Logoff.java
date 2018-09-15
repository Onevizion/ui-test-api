package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.document.Document;

@Component
public class Logoff {

    @Resource
    private Wait wait;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Window window;

    @Resource
    private Document document;

    public void logoff() {
        elementWaitHelper.waitElementById("topPanelUserNameBtn");
        elementWaitHelper.waitElementVisibleById("topPanelUserNameBtn");
        elementWaitHelper.waitElementDisplayById("topPanelUserNameBtn");

        WebElement userNameBtn = seleniumSettings.getWebDriver().findElement(By.id("topPanelUserNameBtn"));
        userNameBtn.click();

        elementWaitHelper.waitElementById("userNameMenu");
        elementWaitHelper.waitElementVisibleById("userNameMenu");
        elementWaitHelper.waitElementDisplayById("userNameMenu");

        elementWaitHelper.waitElementById("userNameMenuItemlogoff");
        elementWaitHelper.waitElementVisibleById("userNameMenuItemlogoff");
        elementWaitHelper.waitElementDisplayById("userNameMenuItemlogoff");

        WebElement logoffBtn = seleniumSettings.getWebDriver().findElement(By.id("userNameMenuItemlogoff"));
        logoffBtn.click();

        seleniumSettings.getWebDriver().switchTo().alert().accept();

        elementWaitHelper.waitElementById("auth_header");
        elementWaitHelper.waitElementById("auth_login");
        elementWaitHelper.waitElementById("auth_unable");

        elementWaitHelper.waitElementById("username");
        elementWaitHelper.waitElementById("password");
        elementWaitHelper.waitElementById("btn");

        document.waitReadyStateComplete();
    }

    public void logoffFromApiV3() {
        seleniumSettings.getWebDriver().get(seleniumSettings.getServerUrl());

        wait.waitWebElement(By.id("mainContainer"));
        wait.waitWebElement(By.id("Table1"));
        wait.waitWebElement(By.id("messageInfoDivContainer"));
        wait.waitWebElement(By.id("messageErrorDivContainer"));

        logoff();
    }

}
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
    private ElementWait elementWait;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Window window;

    @Resource
    private Document document;

    public void logoff() {
        elementWait.waitElementById("topPanelUserNameBtn");
        elementWait.waitElementVisibleById("topPanelUserNameBtn");
        elementWait.waitElementDisplayById("topPanelUserNameBtn");

        WebElement userNameBtn = seleniumSettings.getWebDriver().findElement(By.id("topPanelUserNameBtn"));
        userNameBtn.click();

        elementWait.waitElementById("userNameMenu");
        elementWait.waitElementVisibleById("userNameMenu");
        elementWait.waitElementDisplayById("userNameMenu");

        elementWait.waitElementById("userNameMenuItemlogoff");
        elementWait.waitElementVisibleById("userNameMenuItemlogoff");
        elementWait.waitElementDisplayById("userNameMenuItemlogoff");

        WebElement logoffBtn = seleniumSettings.getWebDriver().findElement(By.id("userNameMenuItemlogoff"));
        logoffBtn.click();

        seleniumSettings.getWebDriver().switchTo().alert().accept();

        elementWait.waitElementById("auth_header");
        elementWait.waitElementById("auth_login");
        elementWait.waitElementById("auth_unable");

        elementWait.waitElementById("username");
        elementWait.waitElementById("password");
        elementWait.waitElementById("btn");

        document.waitReadyStateComplete();
    }

    public void logoffFromApiV3() {
        document.open(seleniumSettings.getServerUrl());

        wait.waitWebElement(By.id("mainContainer"));
        wait.waitWebElement(By.id("Table1"));
        wait.waitWebElement(By.id("messageInfoDivContainer"));
        wait.waitWebElement(By.id("messageErrorDivContainer"));

        logoff();
    }

}
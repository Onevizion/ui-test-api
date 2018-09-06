package com.onevizion.guitest.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.helper.document.DocumentHelper;

@Component
public class LogoffHelper {

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private WindowHelper windowHelper;

    @Resource
    private DocumentHelper documentHelper;

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

        documentHelper.waitReadyStateComplete();
    }

    public void logoffFromApiV3() {
        seleniumSettings.getWebDriver().get(seleniumSettings.getServerUrl());

        waitHelper.waitWebElement(By.id("mainContainer"));
        waitHelper.waitWebElement(By.id("Table1"));
        waitHelper.waitWebElement(By.id("messageInfoDivContainer"));
        waitHelper.waitWebElement(By.id("messageErrorDivContainer"));

        logoff();
    }

}
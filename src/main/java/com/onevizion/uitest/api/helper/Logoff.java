package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.document.Document;
import com.onevizion.uitest.api.helper.jquery.Jquery;

@Component
public class Logoff {

    private static final String ID_USER = "topPanelUserNameLbl";
    private static final String ID_USERMENU = "userPopupMenu";
    private static final String ID_USERMENU_LOGOFF = "itemLogoff";

    @Resource
    private Wait wait;

    @Resource
    private ElementWait elementWait;

    @Resource
    private Element element;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Window window;

    @Resource
    private Document document;

    @Resource
    private Jquery jquery;

    public void logoff() {
        elementWait.waitElementById(ID_USER);
        elementWait.waitElementVisibleById(ID_USER);
        elementWait.waitElementDisplayById(ID_USER);

        element.clickById(ID_USER);

        elementWait.waitElementById(ID_USERMENU);
        elementWait.waitElementVisibleById(ID_USERMENU);
        elementWait.waitElementDisplayById(ID_USERMENU);

        elementWait.waitElementById(ID_USERMENU_LOGOFF);
        elementWait.waitElementVisibleById(ID_USERMENU_LOGOFF);
        elementWait.waitElementDisplayById(ID_USERMENU_LOGOFF);

        element.clickById(ID_USERMENU_LOGOFF);

        seleniumSettings.getWebDriver().switchTo().alert().accept();

        elementWait.waitElementById("auth_header");
        elementWait.waitElementById("auth_login");
        elementWait.waitElementById("auth_unable");

        elementWait.waitElementById("username");
        elementWait.waitElementById("password");
        elementWait.waitElementById("btn");

        document.waitReadyStateComplete();
        jquery.waitLoad();
    }

    public void logoffFromApiV3() {
        document.open(seleniumSettings.getServerUrl());

        wait.waitWebElement(By.id("mainContainer"));
        wait.waitWebElement(By.id("Table1"));
        wait.waitWebElement(By.id("messageInfoDivContainer"));
        wait.waitWebElement(By.id("messageErrorDivContainer"));

        elementWait.waitElementById(ID_USER);
        elementWait.waitElementVisibleById(ID_USER);
        elementWait.waitElementDisplayById(ID_USER);

        wait.waitWebElement(By.id(ID_USERMENU_LOGOFF));

        logoff();
    }

}
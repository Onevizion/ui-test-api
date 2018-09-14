package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.document.Document;

@Component
public class Login {

    @Resource
    private Wait wait;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Document document;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    public void login(String userName, String userPassword) {
        document.waitReadyStateComplete();

        elementWaitHelper.waitElementById("username");
        elementWaitHelper.waitElementVisibleById("username");
        elementWaitHelper.waitElementDisplayById("username");
        elementWaitHelper.waitElementById("password");
        elementWaitHelper.waitElementVisibleById("password");
        elementWaitHelper.waitElementDisplayById("password");

        WebElement userNameElem = seleniumSettings.getWebDriver().findElement(By.name("username"));
        userNameElem.clear();
        userNameElem.sendKeys(userName);

        WebElement passwordElem = seleniumSettings.getWebDriver().findElement(By.name("password"));
        passwordElem.clear();
        passwordElem.sendKeys(userPassword);

        seleniumSettings.getWebDriver().findElement(By.id("btn")).click();

        wait.waitWebElement(By.id("mainContainer"));
        wait.waitWebElement(By.id("Table1"));
        wait.waitWebElement(By.id("messageInfoDivContainer"));
        wait.waitWebElement(By.id("messageErrorDivContainer"));
    }

    public void failLogin(String userName, String userPassword, String errorMessage) {
        document.waitReadyStateComplete();

        elementWaitHelper.waitElementById("username");
        elementWaitHelper.waitElementVisibleById("username");
        elementWaitHelper.waitElementDisplayById("username");
        elementWaitHelper.waitElementById("password");
        elementWaitHelper.waitElementVisibleById("password");
        elementWaitHelper.waitElementDisplayById("password");

        WebElement userNameElem = seleniumSettings.getWebDriver().findElement(By.name("username"));
        userNameElem.clear();
        userNameElem.sendKeys(userName);

        WebElement passwordElem = seleniumSettings.getWebDriver().findElement(By.name("password"));
        passwordElem.clear();
        passwordElem.sendKeys(userPassword);

        seleniumSettings.getWebDriver().findElement(By.id("btn")).click();

        wait.waitWebElement(By.id("auth_message"));
        elementWaitHelper.waitElementAttributeById("auth_message", "innerText", errorMessage);
    }

    public void loginIntoApiV3() {
        login(seleniumSettings.getTestUser(), seleniumSettings.getTestPasswordApiV3());

        seleniumSettings.getWebDriver().get(seleniumSettings.getServerUrl() + "/api/v3");

        wait.waitWebElement(By.className("info_title"));
        wait.waitWebElement(By.className("authorize__btn"));

        WebElement authorizeBtn = seleniumSettings.getWebDriver().findElement(By.className("authorize__btn"));
        authorizeBtn.click();

        wait.waitWebElement(By.name("username"));
        wait.waitWebElement(By.name("password"));

        WebElement userNameElem = seleniumSettings.getWebDriver().findElement(By.name("username"));
        userNameElem.clear();
        userNameElem.sendKeys(seleniumSettings.getTestUser());

        WebElement passwordElem = seleniumSettings.getWebDriver().findElement(By.name("password"));
        passwordElem.clear();
        passwordElem.sendKeys(seleniumSettings.getTestPasswordApiV3());

        seleniumSettings.getWebDriver().findElement(By.className("auth_submit__button")).click();

        wait.waitWebElement(By.className("info_title"));
        wait.waitWebElement(By.className("authorize__btn"));
    }

}
package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.document.DocumentHelper;

@Component
public class LoginHelper {

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private DocumentHelper documentHelper;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    public void login(String userName, String userPassword) {
        documentHelper.waitReadyStateComplete();

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

        waitHelper.waitWebElement(By.id("mainContainer"));
        waitHelper.waitWebElement(By.id("Table1"));
        waitHelper.waitWebElement(By.id("messageInfoDivContainer"));
        waitHelper.waitWebElement(By.id("messageErrorDivContainer"));
    }

    public void failLogin(String userName, String userPassword, String errorMessage) {
        documentHelper.waitReadyStateComplete();

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

        waitHelper.waitWebElement(By.id("auth_message"));
        elementWaitHelper.waitElementAttributeById("auth_message", "innerText", errorMessage);
    }

    public void loginIntoApiV3() {
        login(seleniumSettings.getTestUser(), seleniumSettings.getTestPasswordApiV3());

        seleniumSettings.getWebDriver().get(seleniumSettings.getServerUrl() + "/api/v3");

        waitHelper.waitWebElement(By.className("info_title"));
        waitHelper.waitWebElement(By.className("authorize__btn"));

        WebElement authorizeBtn = seleniumSettings.getWebDriver().findElement(By.className("authorize__btn"));
        authorizeBtn.click();

        waitHelper.waitWebElement(By.name("username"));
        waitHelper.waitWebElement(By.name("password"));

        WebElement userNameElem = seleniumSettings.getWebDriver().findElement(By.name("username"));
        userNameElem.clear();
        userNameElem.sendKeys(seleniumSettings.getTestUser());

        WebElement passwordElem = seleniumSettings.getWebDriver().findElement(By.name("password"));
        passwordElem.clear();
        passwordElem.sendKeys(seleniumSettings.getTestPasswordApiV3());

        seleniumSettings.getWebDriver().findElement(By.className("auth_submit__button")).click();

        waitHelper.waitWebElement(By.className("info_title"));
        waitHelper.waitWebElement(By.className("authorize__btn"));
    }

}
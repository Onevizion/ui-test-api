package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.document.Document;
import com.onevizion.uitest.api.helper.jquery.Jquery;

@Component
public class Login {

    private static final String ID_USER = "topPanelUserNameLbl";
    private static final String ID_USERMENU_LOGOFF = "itemLogoff";

    @Resource
    private Wait wait;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Document document;

    @Resource
    private ElementWait elementWait;

    @Resource
    private Jquery jquery;

    public void login(String userName, String userPassword) {
        document.waitReadyStateComplete();

        elementWait.waitElementById("username");
        elementWait.waitElementVisibleById("username");
        elementWait.waitElementDisplayById("username");
        elementWait.waitElementById("password");
        elementWait.waitElementVisibleById("password");
        elementWait.waitElementDisplayById("password");

        WebElement userNameElem = seleniumSettings.getWebDriver().findElement(By.name("username"));
        userNameElem.clear();
        userNameElem.sendKeys(userName);

        WebElement passwordElem = seleniumSettings.getWebDriver().findElement(By.name("password"));
        passwordElem.clear();
        passwordElem.sendKeys(userPassword);

        seleniumSettings.getWebDriver().findElement(By.id("btn")).click();

        elementWait.waitElementNotExist(userNameElem);
        elementWait.waitElementNotExist(passwordElem);
        document.waitReadyStateComplete();
        jquery.waitLoad();

        wait.waitWebElement(By.id("mainContainer"));
        wait.waitWebElement(By.id("Table1"));
        wait.waitWebElement(By.id("messageInfoDivContainer"));
        wait.waitWebElement(By.id("messageErrorDivContainer"));

        elementWait.waitElementById(ID_USER);
        elementWait.waitElementVisibleById(ID_USER);
        elementWait.waitElementDisplayById(ID_USER);

        wait.waitWebElement(By.id(ID_USERMENU_LOGOFF));
    }

    public void failLogin(String userName, String userPassword, String errorMessage) {
        document.waitReadyStateComplete();
        jquery.waitLoad();

        elementWait.waitElementById("username");
        elementWait.waitElementVisibleById("username");
        elementWait.waitElementDisplayById("username");
        elementWait.waitElementById("password");
        elementWait.waitElementVisibleById("password");
        elementWait.waitElementDisplayById("password");

        WebElement userNameElem = seleniumSettings.getWebDriver().findElement(By.name("username"));
        userNameElem.clear();
        userNameElem.sendKeys(userName);

        WebElement passwordElem = seleniumSettings.getWebDriver().findElement(By.name("password"));
        passwordElem.clear();
        passwordElem.sendKeys(userPassword);

        seleniumSettings.getWebDriver().findElement(By.id("btn")).click();

        elementWait.waitElementNotExist(userNameElem);
        elementWait.waitElementNotExist(passwordElem);
        document.waitReadyStateComplete();
        jquery.waitLoad();

        elementWait.waitElementById("auth_message");
        elementWait.waitElementVisibleById("auth_message");
        elementWait.waitElementDisplayById("auth_message");
        elementWait.waitElementById("auth_close");
        elementWait.waitElementVisibleById("auth_close");
        elementWait.waitElementDisplayById("auth_close");

        elementWait.waitElementAttributeById("auth_message", "innerText", errorMessage);
        seleniumSettings.getWebDriver().findElement(By.id("auth_close")).click();

        elementWait.waitElementNotVisibleById("auth_message");
        elementWait.waitElementNotDisplayById("auth_message");
        elementWait.waitElementNotVisibleById("auth_close");
        elementWait.waitElementNotDisplayById("auth_close");
    }

    public void loginIntoApiV3() {
        login(seleniumSettings.getTestUser(), seleniumSettings.getTestPasswordApiV3());

        document.open(seleniumSettings.getServerUrl() + "/api/v3");

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
package com.onevizion.uitest.api.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.document.Document;
import com.onevizion.uitest.api.helper.jquery.Jquery;

@Component
public class Login {

    private static final String ID_USER = "topPanelUserNameLbl";
    private static final String ID_USERMENU_LOGOFF = "itemLogoff";

    @Autowired
    private Wait wait;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Document document;

    @Autowired
    private ElementWait elementWait;

    @Autowired
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

    public void mustChangePasswordLogin(String userName, String userPassword, String newUserPassword) {
        WebElement html;

        document.waitReadyStateComplete();
        jquery.waitLoad();
        html = seleniumSettings.getWebDriver().findElement(By.tagName("html"));
        elementWait.waitElementById("username");
        elementWait.waitElementVisibleById("username");
        elementWait.waitElementDisplayById("username");
        seleniumSettings.getWebDriver().findElement(By.name("username")).sendKeys(userName);
        seleniumSettings.getWebDriver().findElement(By.name("password")).sendKeys(userPassword);
        seleniumSettings.getWebDriver().findElement(By.id("btn")).click();
        elementWait.waitElementNotExist(html);

        document.waitReadyStateComplete();
        jquery.waitLoad();
        html = seleniumSettings.getWebDriver().findElement(By.tagName("html"));
        elementWait.waitElementById("CurPass");
        elementWait.waitElementVisibleById("CurPass");
        elementWait.waitElementDisplayById("CurPass");
        seleniumSettings.getWebDriver().findElement(By.name("CurPass")).sendKeys(userPassword);
        seleniumSettings.getWebDriver().findElement(By.name("NewPass1")).sendKeys(newUserPassword);
        seleniumSettings.getWebDriver().findElement(By.name("NewPass2")).sendKeys(newUserPassword);
        seleniumSettings.getWebDriver().findElement(By.id("auth_submit")).click();
        elementWait.waitElementNotExist(html);
    }

    public void loginIntoApiV3() {
        login(seleniumSettings.getTestUser(), seleniumSettings.getTestPassword());

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
        passwordElem.sendKeys(seleniumSettings.getTestPassword());

        seleniumSettings.getWebDriver().findElement(By.className("auth_submit__button")).click();

        wait.waitWebElement(By.className("info_title"));
        wait.waitWebElement(By.className("authorize__btn"));
    }

    public void loginIntoApiV3ByToken(String accessKey, String secretKey) {
        login(seleniumSettings.getTestUser(), seleniumSettings.getTestPassword());

        document.open(seleniumSettings.getServerUrl() + "/api/v3");

        wait.waitWebElement(By.className("info_title"));
        wait.waitWebElement(By.className("authorize__btn"));

        WebElement authorizeBtn = seleniumSettings.getWebDriver().findElement(By.className("authorize__btn"));
        authorizeBtn.click();

        wait.waitWebElement(By.name("accessKey"));
        wait.waitWebElement(By.name("secretKey"));

        WebElement accessKeyElem = seleniumSettings.getWebDriver().findElement(By.name("accessKey"));
        accessKeyElem.clear();
        accessKeyElem.sendKeys(accessKey);

        WebElement secretKeyElem = seleniumSettings.getWebDriver().findElement(By.name("secretKey"));
        secretKeyElem.clear();
        secretKeyElem.sendKeys(secretKey);

        seleniumSettings.getWebDriver().findElements(By.className("auth_submit__button")).get(1).click();

        wait.waitWebElement(By.className("info_title"));
        wait.waitWebElement(By.className("authorize__btn"));
    }

    public void loginSsoFirstTime(String userName, String password, Long ssoId) {
        WebElement samlButtonElem = seleniumSettings.getWebDriver().findElement(By.id("btn_" + ssoId));
        samlButtonElem.click();
        elementWait.waitElementNotExist(samlButtonElem);

        elementWait.waitElementById("IDToken1");
        elementWait.waitElementVisibleById("IDToken1");
        elementWait.waitElementDisplayById("IDToken1");
        elementWait.waitElementById("IDToken2");
        elementWait.waitElementVisibleById("IDToken2");
        elementWait.waitElementDisplayById("IDToken2");
        elementWait.waitElementByName("Login.Submit");
        elementWait.waitElementVisibleByName("Login.Submit");
        elementWait.waitElementDisplayByName("Login.Submit");
        document.waitReadyStateComplete();

        WebElement userNameElem = seleniumSettings.getWebDriver().findElement(By.id("IDToken1"));
        userNameElem.clear();
        userNameElem.sendKeys(userName);

        WebElement passwordElem = seleniumSettings.getWebDriver().findElement(By.id("IDToken2"));
        passwordElem.clear();
        passwordElem.sendKeys(password);

        seleniumSettings.getWebDriver().findElement(By.name("Login.Submit")).click();

        elementWait.waitElementNotExist(userNameElem);
        elementWait.waitElementNotExist(passwordElem);

        wait.waitWebElement(By.id("mainContainer"));
        wait.waitWebElement(By.id("messageInfoDivContainer"));
        wait.waitWebElement(By.id("messageErrorDivContainer"));

        elementWait.waitElementById(ID_USER);
        elementWait.waitElementVisibleById(ID_USER);
        elementWait.waitElementDisplayById(ID_USER);

        wait.waitWebElement(By.id(ID_USERMENU_LOGOFF));

        document.waitReadyStateComplete();
        jquery.waitLoad();
    }

    public void loginSsoSecondTime(Long ssoId) {
        WebElement samlButtonElem = seleniumSettings.getWebDriver().findElement(By.id("btn_" + ssoId));
        samlButtonElem.click();
        elementWait.waitElementNotExist(samlButtonElem);

        wait.waitWebElement(By.id("mainContainer"));
        wait.waitWebElement(By.id("messageInfoDivContainer"));
        wait.waitWebElement(By.id("messageErrorDivContainer"));

        elementWait.waitElementById(ID_USER);
        elementWait.waitElementVisibleById(ID_USER);
        elementWait.waitElementDisplayById(ID_USER);

        wait.waitWebElement(By.id(ID_USERMENU_LOGOFF));

        document.waitReadyStateComplete();
        jquery.waitLoad();
    }

}
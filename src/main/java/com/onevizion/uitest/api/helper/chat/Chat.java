package com.onevizion.uitest.api.helper.chat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.ElementWait;

@Component
public class Chat {

    private static final String ID_MAIN_BUTTON = "btnComments";
    private static final String ID_MAIN_PANEL = "slideChatPanel";
    private static final String ID_MAIN_LOADER = "loaderChatLoader";
    private static final String ID_MANAGE = "btnManage";
    private static final String ID_MANAGE_PANEL = "managePanel";
    private static final String ID_COMMENT_TEXT = "replyForm";
    private static final String ID_COMMENT_FILE = "btnAddFile";
    private static final String ID_COMMENT_SEND = "btnSend";
    private static final String CLASS_TOGGLE = "toggle";
    private static final String ID_SUBSCRIBE = "btnSubscribe";
    private static final String ID_CLOSE_MANAGE_PANEL = "btnOffManagePanel";
    private static final String CLASS_USER_ON_MANAGE_PANEL = "user_item";
    private static final String CLASS_USER_NAME_ON_MANAGE_PANEL = "ui_name";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementWait elementWait;

    public void checkMainPanelOnFormExist() {
        checkElementExist(ID_MAIN_BUTTON);
        checkElementExist(ID_MAIN_PANEL);
    }

    public void checkMainPanelOnFormNotExist() {
        checkElementNotExist(ID_MAIN_BUTTON);
        checkElementNotExist(ID_MAIN_PANEL);
    }

    public void checkMainPanelInGridExist() {
        checkElementExist(ID_MAIN_BUTTON + AbstractSeleniumCore.getGridIdx());
        checkElementExist(ID_MAIN_PANEL + AbstractSeleniumCore.getGridIdx());
    }

    public void checkMainPanelInGridNotExist() {
        checkElementNotExist(ID_MAIN_BUTTON + AbstractSeleniumCore.getGridIdx());
        checkElementNotExist(ID_MAIN_PANEL + AbstractSeleniumCore.getGridIdx());
    }

    public void checkAddNewCommentOnFormExist() {
        elementWait.waitElementVisibleById(ID_COMMENT_TEXT);
        elementWait.waitElementDisplayById(ID_COMMENT_TEXT);
        elementWait.waitElementVisibleById(ID_COMMENT_FILE);
        elementWait.waitElementDisplayById(ID_COMMENT_FILE);
        elementWait.waitElementVisibleById(ID_COMMENT_SEND);
        elementWait.waitElementDisplayById(ID_COMMENT_SEND);
    }

    public void checkAddNewCommentOnFormNotExist() {
        elementWait.waitElementNotVisibleById(ID_COMMENT_TEXT);
        elementWait.waitElementNotDisplayById(ID_COMMENT_TEXT);
        elementWait.waitElementNotVisibleById(ID_COMMENT_FILE);
        elementWait.waitElementNotDisplayById(ID_COMMENT_FILE);
        elementWait.waitElementNotVisibleById(ID_COMMENT_SEND);
        elementWait.waitElementNotDisplayById(ID_COMMENT_SEND);
    }

    public void checkAddNewCommentInGridExist() {
        elementWait.waitElementVisibleById(ID_COMMENT_TEXT + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementDisplayById(ID_COMMENT_TEXT + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementVisibleById(ID_COMMENT_FILE + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementDisplayById(ID_COMMENT_FILE + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementVisibleById(ID_COMMENT_SEND + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementDisplayById(ID_COMMENT_SEND + AbstractSeleniumCore.getGridIdx());
    }

    public void checkAddNewCommentInGridNotExist() {
        elementWait.waitElementNotVisibleById(ID_COMMENT_TEXT + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementNotDisplayById(ID_COMMENT_TEXT + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementNotVisibleById(ID_COMMENT_FILE + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementNotDisplayById(ID_COMMENT_FILE + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementNotVisibleById(ID_COMMENT_SEND + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementNotDisplayById(ID_COMMENT_SEND + AbstractSeleniumCore.getGridIdx());
    }

    public void checkManageSubscribersOnFormExist() {
        elementWait.waitElementVisibleById(ID_MANAGE);
        elementWait.waitElementDisplayById(ID_MANAGE);
    }

    public void checkManageSubscribersOnFormNotExist() {
        elementWait.waitElementNotVisibleById(ID_MANAGE);
        elementWait.waitElementNotDisplayById(ID_MANAGE);
    }

    public void checkManageSubscribersInGridExist() {
        elementWait.waitElementVisibleById(ID_MANAGE + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementDisplayById(ID_MANAGE + AbstractSeleniumCore.getGridIdx());
    }

    public void checkManageSubscribersInGridNotExist() {
        elementWait.waitElementNotVisibleById(ID_MANAGE + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementNotDisplayById(ID_MANAGE + AbstractSeleniumCore.getGridIdx());
    }

    public void checkSubscribeToggleOnFormExists() {
        int count = getToggleCount(ID_MAIN_PANEL);
        Assert.assertEquals(count > 0, true, "Toggles not found");
    }

    public void checkSubscribeToggleOnFormNotExists() {
        int count = getToggleCount(ID_MAIN_PANEL);
        Assert.assertEquals(count == 0, true, "Toggles found");
    }

    public void checkSubscribeToggleInGridExists() {
        int count = getToggleCount(ID_MAIN_PANEL + AbstractSeleniumCore.getGridIdx());
        Assert.assertEquals(count > 0, true, "Toggles not found");
    }

    public void checkSubscribeToggleInGridNotExists() {
        int count = getToggleCount(ID_MAIN_PANEL + AbstractSeleniumCore.getGridIdx());
        Assert.assertEquals(count == 0, true, "Toggles found");
    }

    public void openChatPanelOnForm() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_MAIN_BUTTON)).click();
        elementWait.waitElementVelocityAnimatedFinishById(ID_MAIN_PANEL);
        elementWait.waitElementNotVisibleById(ID_MAIN_LOADER);
        elementWait.waitElementNotDisplayById(ID_MAIN_LOADER);
    }

    public void openChatPanelInGrid() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_MAIN_BUTTON + AbstractSeleniumCore.getGridIdx())).click();
        elementWait.waitElementVelocityAnimatedFinishById(ID_MAIN_PANEL + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementNotVisibleById(ID_MAIN_LOADER + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementNotDisplayById(ID_MAIN_LOADER + AbstractSeleniumCore.getGridIdx());
    }

    public void closeChatPanelOnForm() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_MAIN_BUTTON)).click();
        elementWait.waitElementVelocityAnimatedFinishById(ID_MAIN_PANEL);
    }

    public void closeChatPanelInGrid() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_MAIN_BUTTON + AbstractSeleniumCore.getGridIdx())).click();
        elementWait.waitElementVelocityAnimatedFinishById(ID_MAIN_PANEL + AbstractSeleniumCore.getGridIdx());
    }

    public void openSubscribePanelOnForm() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_MANAGE)).click();
        elementWait.waitElementVisibleById(ID_MANAGE_PANEL);
        elementWait.waitElementDisplayById(ID_MANAGE_PANEL);
        elementWait.waitElementNotVisibleById(ID_MAIN_LOADER);
        elementWait.waitElementNotDisplayById(ID_MAIN_LOADER);
    }

    public void openSubscribePanelInGrid() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_MANAGE + AbstractSeleniumCore.getGridIdx())).click();
        elementWait.waitElementVisibleById(ID_MANAGE_PANEL + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementDisplayById(ID_MANAGE_PANEL + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementNotVisibleById(ID_MAIN_LOADER + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementNotDisplayById(ID_MAIN_LOADER + AbstractSeleniumCore.getGridIdx());
    }

    public void closeSubscribePanelOnForm() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_CLOSE_MANAGE_PANEL)).click();
        elementWait.waitElementNotVisibleById(ID_MANAGE_PANEL);
        elementWait.waitElementNotDisplayById(ID_MANAGE_PANEL);
        elementWait.waitElementVisibleById(ID_MAIN_PANEL);
        elementWait.waitElementDisplayById(ID_MAIN_PANEL);
    }

    public void closeSubscribePanelInGrid() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_CLOSE_MANAGE_PANEL + AbstractSeleniumCore.getGridIdx())).click();
        elementWait.waitElementNotVisibleById(ID_MANAGE_PANEL + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementNotDisplayById(ID_MANAGE_PANEL + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementVisibleById(ID_MAIN_PANEL + AbstractSeleniumCore.getGridIdx());
        elementWait.waitElementDisplayById(ID_MAIN_PANEL + AbstractSeleniumCore.getGridIdx());
    }

    public void subscribeCurrentUserOnForm() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_SUBSCRIBE)).click();
        elementWait.waitElementNotVisibleById(ID_SUBSCRIBE);
    }

    public void subscribeCurrentUserInGrid() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_SUBSCRIBE + AbstractSeleniumCore.getGridIdx())).click();
        elementWait.waitElementNotVisibleById(ID_SUBSCRIBE + AbstractSeleniumCore.getGridIdx());
    }

    public void subscribeUserOnForm(String userName) {
        WebElement user = getUserFromManagePanelOnForm(userName);
        user.click();
        WebElement toggle = user.findElement(By.className(CLASS_TOGGLE));
        elementWait.waitElementEnabled(toggle); //TODO Chat-152516
    }

    public void subscribeUserInGrid(String userName) {
        WebElement user = getUserFromManagePanelInGrid(userName);
        user.click();
        WebElement toggle = user.findElement(By.className(CLASS_TOGGLE));
        elementWait.waitElementEnabled(toggle); //TODO Chat-152516
    }

    public void unsubscribeUserOnForm(String userName) {
        WebElement user = getUserFromManagePanelOnForm(userName);
        user.click();
        WebElement toggle = user.findElement(By.className(CLASS_TOGGLE));
        elementWait.waitElementEnabled(toggle); //TODO Chat-152516
    }

    public void unsubscribeUserInGrid(String userName) {
        WebElement user = getUserFromManagePanelInGrid(userName);
        user.click();
        WebElement toggle = user.findElement(By.className(CLASS_TOGGLE));
        elementWait.waitElementEnabled(toggle); //TODO Chat-152516
    }

    public void checkCurrentUserIsSubscribedOnForm() {
        boolean isSubscribe = !seleniumSettings.getWebDriver().findElement(By.id(ID_SUBSCRIBE)).isDisplayed();
        Assert.assertEquals(isSubscribe, true);
    }

    public void checkCurrentUserIsSubscribedInGrid() {
        boolean isSubscribe = !seleniumSettings.getWebDriver().findElement(By.id(ID_SUBSCRIBE + AbstractSeleniumCore.getGridIdx())).isDisplayed();
        Assert.assertEquals(isSubscribe, true);
    }

    public void checkCurrentUserIsUnsubscribedOnForm() {
        boolean isSubscribe = !seleniumSettings.getWebDriver().findElement(By.id(ID_SUBSCRIBE)).isDisplayed();
        Assert.assertEquals(isSubscribe, false);
    }

    public void checkCurrentUserIsUnsubscribedInGrid() {
        boolean isSubscribe = !seleniumSettings.getWebDriver().findElement(By.id(ID_SUBSCRIBE + AbstractSeleniumCore.getGridIdx())).isDisplayed();
        Assert.assertEquals(isSubscribe, false);
    }

    public void checkUserIsSubscribedOnForm(String userName) {
        WebElement user = getUserFromManagePanelOnForm(userName);
        boolean isSubscribe = isUserSubscribed(user);
        Assert.assertEquals(isSubscribe, true);
    }

    public void checkUserIsSubscribedInGrid(String userName) {
        WebElement user = getUserFromManagePanelInGrid(userName);
        boolean isSubscribe = isUserSubscribed(user);
        Assert.assertEquals(isSubscribe, true);
    }

    public void checkUserIsUnsubscribedOnForm(String userName) {
        WebElement user = getUserFromManagePanelOnForm(userName);
        boolean isSubscribe = isUserSubscribed(user);
        Assert.assertEquals(isSubscribe, false);
    }

    public void checkUserIsUnsubscribedInGrid(String userName) {
        WebElement user = getUserFromManagePanelInGrid(userName);
        boolean isSubscribe = isUserSubscribed(user);
        Assert.assertEquals(isSubscribe, false);
    }

    private WebElement getUserFromManagePanelOnForm(String userName) {
        WebElement result = null;

        List<WebElement> users = seleniumSettings.getWebDriver().findElement(By.id(ID_MANAGE_PANEL)).findElements(By.className(CLASS_USER_ON_MANAGE_PANEL));
        for (WebElement user : users) {
            if (userName.equals(user.findElement(By.className(CLASS_USER_NAME_ON_MANAGE_PANEL)).getAttribute("textContent"))) {
                if (result != null) {
                    throw new SeleniumUnexpectedException("User [" + userName + "] found many times");
                }
                result = user;
            }
        }

        if (result == null) {
            throw new SeleniumUnexpectedException("User [" + userName + "] not found");
        }

        return result;
    }

    private WebElement getUserFromManagePanelInGrid(String userName) {
        WebElement result = null;

        List<WebElement> users = seleniumSettings.getWebDriver().findElement(By.id(ID_MANAGE_PANEL + AbstractSeleniumCore.getGridIdx())).findElements(By.className(CLASS_USER_ON_MANAGE_PANEL));
        for (WebElement user : users) {
            if (userName.equals(user.findElement(By.className(CLASS_USER_NAME_ON_MANAGE_PANEL)).getAttribute("textContent"))) {
                if (result != null) {
                    throw new SeleniumUnexpectedException("User [" + userName + "] found many times");
                }
                result = user;
            }
        }

        if (result == null) {
            throw new SeleniumUnexpectedException("User [" + userName + "] not found");
        }

        return result;
    }

    private boolean isUserSubscribed(WebElement user) {
        String userClass = user.getAttribute("class");
        String toggleClass = user.findElement(By.className(CLASS_TOGGLE)).getAttribute("class");
        if (userClass.contains("selected") && toggleClass.contains("toggle_on")) {
            return true;
        }
        return false;
    }

    private void checkElementExist(String id) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = seleniumSettings.getWebDriver().findElements(By.id(id)).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        Assert.assertEquals(count, 1, "Element not found");
    }

    private void checkElementNotExist(String id) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = seleniumSettings.getWebDriver().findElements(By.id(id)).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        Assert.assertEquals(count, 0, "Element found");
    }

    private int getToggleCount(String id) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = seleniumSettings.getWebDriver().findElement(By.id(id)).findElements(By.className(CLASS_TOGGLE)).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        return count;
    }

}
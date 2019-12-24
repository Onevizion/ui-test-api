package com.onevizion.uitest.api.helper.chat;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class Chat {

    private static final String ID_MAIN_BUTTON = "btnComments";
    private static final String ID_MAIN_PANEL = "slideChatPanel";
    private static final String ID_MANAGE = "btnManage";
    private static final String ID_COMMENT_TEXT = "replyForm";
    private static final String ID_COMMENT_FILE = "btnAddFile";
    private static final String ID_COMMENT_SEND = "btnSend";
    private static final String CLASS_TOGGLE = "toggle";

    @Resource
    private SeleniumSettings seleniumSettings;

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
        checkElementExist(ID_COMMENT_TEXT);
        checkElementExist(ID_COMMENT_FILE);
        checkElementExist(ID_COMMENT_SEND);
    }

    public void checkAddNewCommentOnFormNotExist() {
        checkElementNotExist(ID_COMMENT_TEXT);
        checkElementNotExist(ID_COMMENT_FILE);
        checkElementNotExist(ID_COMMENT_SEND);
    }

    public void checkAddNewCommentInGridExist() {
        checkElementExist(ID_COMMENT_TEXT + AbstractSeleniumCore.getGridIdx());
        checkElementExist(ID_COMMENT_FILE + AbstractSeleniumCore.getGridIdx());
        checkElementExist(ID_COMMENT_SEND + AbstractSeleniumCore.getGridIdx());
    }

    public void checkAddNewCommentInGridNotExist() {
        checkElementNotExist(ID_COMMENT_TEXT + AbstractSeleniumCore.getGridIdx());
        checkElementNotExist(ID_COMMENT_FILE + AbstractSeleniumCore.getGridIdx());
        checkElementNotExist(ID_COMMENT_SEND + AbstractSeleniumCore.getGridIdx());
    }

    public void checkManageSubscribersOnFormExist() {
        checkElementExist(ID_MANAGE);
    }

    public void checkManageSubscribersOnFormNotExist() {
        checkElementNotExist(ID_MANAGE);
    }

    public void checkManageSubscribersInGridExist() {
        checkElementExist(ID_MANAGE + AbstractSeleniumCore.getGridIdx());
    }

    public void checkManageSubscribersInGridNotExist() {
        checkElementNotExist(ID_MANAGE + AbstractSeleniumCore.getGridIdx());
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
    }

    public void openChatPanelInGrid() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_MAIN_BUTTON + AbstractSeleniumCore.getGridIdx())).click();
    }

    public void closeChatPanelOnForm() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_MAIN_BUTTON)).click();
    }

    public void closeChatPanelInGrid() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_MAIN_BUTTON + AbstractSeleniumCore.getGridIdx())).click();
    }

    public void openSubscribePanelOnForm() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_MANAGE)).click();
    }

    public void openSubscribePanelInGrid() {
        seleniumSettings.getWebDriver().findElement(By.id(ID_MANAGE + AbstractSeleniumCore.getGridIdx())).click();
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
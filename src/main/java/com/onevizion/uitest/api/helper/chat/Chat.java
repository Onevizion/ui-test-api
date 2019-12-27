package com.onevizion.uitest.api.helper.chat;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
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
package com.onevizion.uitest.api.helper;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class Privilegies {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Wait wait;

    @Resource
    private ElementWait elementWait;

    public void checkAddCloneEditDelButtons(Long gridIdx, Boolean isDisplayAdd, Boolean isDisplayClone, Boolean isDisplayEdit, Boolean isDisplayDel) {
        if (isDisplayAdd != null) {
            wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + gridIdx));
            if (isDisplayAdd.booleanValue()) {
                //elementWaitHelper.waitElementAttributeById(AbstractSelenium.BUTTON_ADD_ID_BASE + gridIdx, "style", "background-image: none;");
                elementWait.waitElementAttributeById(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + gridIdx, "style", Arrays.asList("background-image: none"));
            } else {
                //elementWaitHelper.waitElementAttributeById(AbstractSelenium.BUTTON_ADD_ID_BASE + gridIdx, "style", "background-image: none; display: none;");
                elementWait.waitElementAttributeById(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + gridIdx, "style", Arrays.asList("background-image: none", "display: none"));
            }
        } else {
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + gridIdx)).size(), 0, AbstractSeleniumCore.BUTTON_ADD_ID_BASE + gridIdx + " found");
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        if (isDisplayClone != null) {
            wait.waitWebElement(By.id(CloneButton.BUTTON_CLONE_ID_BASE + gridIdx));
            if (isDisplayClone.booleanValue()) {
                //elementWaitHelper.waitElementAttributeById(CloneButtonHelper.BUTTON_CLONE_ID_BASE + gridIdx, "style", "background-image: none;");
                elementWait.waitElementAttributeById(CloneButton.BUTTON_CLONE_ID_BASE + gridIdx, "style", Arrays.asList("background-image: none"));
            } else {
                //elementWaitHelper.waitElementAttributeById(CloneButtonHelper.BUTTON_CLONE_ID_BASE + gridIdx, "style", "background-image: none; display: none;");
                elementWait.waitElementAttributeById(CloneButton.BUTTON_CLONE_ID_BASE + gridIdx, "style", Arrays.asList("background-image: none", "display: none"));
            }
        } else {
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.id(CloneButton.BUTTON_CLONE_ID_BASE + gridIdx)).size(), 0, CloneButton.BUTTON_CLONE_ID_BASE + gridIdx + " found");
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        if (isDisplayEdit != null) {
            wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + gridIdx));
            if (isDisplayEdit.booleanValue()) {
                //elementWaitHelper.waitElementAttributeById(AbstractSelenium.BUTTON_EDIT_ID_BASE + gridIdx, "style", "background-image: none;");
                elementWait.waitElementAttributeById(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + gridIdx, "style", Arrays.asList("background-image: none"));
            } else {
                //elementWaitHelper.waitElementAttributeById(AbstractSeleniumInternalPage.BUTTON_EDIT_ID_BASE + gridIdx, "style", "background-image: none; display: none;");
                elementWait.waitElementAttributeById(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + gridIdx, "style", Arrays.asList("background-image: none", "display: none"));
            }
        } else {
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + gridIdx)).size(), 0, AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + gridIdx + " found");
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        if (isDisplayDel != null) {
            wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_DELETE_ID_BASE + gridIdx));
            if (isDisplayDel.booleanValue()) {
                //elementWaitHelper.waitElementAttributeById(AbstractSelenium.BUTTON_DELETE_ID_BASE + gridIdx, "style", "background-image: none;");
                elementWait.waitElementAttributeById(AbstractSeleniumCore.BUTTON_DELETE_ID_BASE + gridIdx, "style", Arrays.asList("background-image: none"));
            } else {
                //elementWaitHelper.waitElementAttributeById(AbstractSelenium.BUTTON_DELETE_ID_BASE + gridIdx, "style", "background-image: none; display: none;");
                elementWait.waitElementAttributeById(AbstractSeleniumCore.BUTTON_DELETE_ID_BASE + gridIdx, "style", Arrays.asList("background-image: none", "display: none"));
            }
        } else {
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.id(AbstractSeleniumCore.BUTTON_DELETE_ID_BASE + gridIdx)).size(), 0, AbstractSeleniumCore.BUTTON_DELETE_ID_BASE + gridIdx + " found");
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

    public void checkOkCancelApplyButtons(Boolean isEnabledOk, Boolean isEnabledCancel, Boolean isEnabledApply) {
        if (isEnabledOk != null) {
            if (isEnabledOk.booleanValue()) {
                elementWait.waitElementEnabledById(AbstractSeleniumCore.BUTTON_OK_ID_BASE);
            } else {
                elementWait.waitElementDisabledById(AbstractSeleniumCore.BUTTON_OK_ID_BASE);
            }
        } else {
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE)).size(), 0, AbstractSeleniumCore.BUTTON_OK_ID_BASE + " found");
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        if (isEnabledCancel != null) {
            if (isEnabledCancel.booleanValue()) {
                elementWait.waitElementEnabledById(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE);
            } else {
                elementWait.waitElementDisabledById(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE);
            }
        } else {
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE)).size(), 0, AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE + " found");
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        if (isEnabledApply != null) {
            if (isEnabledApply.booleanValue()) {
                elementWait.waitElementEnabledById(AbstractSeleniumCore.BUTTON_APPLY_ID);
            } else {
                elementWait.waitElementDisabledById(AbstractSeleniumCore.BUTTON_APPLY_ID);
            }
        } else {
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.id(AbstractSeleniumCore.BUTTON_APPLY_ID)).size(), 0, AbstractSeleniumCore.BUTTON_APPLY_ID + " found");
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

}
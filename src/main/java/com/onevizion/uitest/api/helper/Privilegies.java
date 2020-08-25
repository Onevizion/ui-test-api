package com.onevizion.uitest.api.helper;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.grid.button.GridButton;

@Component
public class Privilegies {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private GridButton gridButton;

    public void checkAddCloneEditDelButtons(Long gridIdx, boolean isDisplayAdd, boolean isDisplayClone, boolean isDisplayEdit, boolean isDisplayDel) {
        if (isDisplayAdd) {
            gridButton.checkAddButtonExist(gridIdx);
        } else {
            gridButton.checkAddButtonNotExist(gridIdx);
        }

        if (isDisplayClone) {
            gridButton.checkCloneButtonExist(gridIdx);
        } else {
            gridButton.checkCloneButtonNotExist(gridIdx);
        }

        if (isDisplayEdit) {
            gridButton.checkEditButtonExist(gridIdx);
        } else {
            gridButton.checkEditButtonNotExist(gridIdx);
        }

        if (isDisplayDel) {
            gridButton.checkDeleteButtonExist(gridIdx);
        } else {
            gridButton.checkDeleteButtonNotExist(gridIdx);
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
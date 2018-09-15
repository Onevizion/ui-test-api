package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class CloneButton {

    private final static String BUTTON_LIST_ADD_ID_BASE = "listAdd";
    final static String BUTTON_CLONE_ID_BASE = "btnClone";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Wait wait;

    @Resource
    private Window window;

    @Resource
    private ElementWait elementWait;

    public void openCloneForm(Long gridIdx) {
        showCloneButton(gridIdx);

        window.openModal(By.id(BUTTON_CLONE_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    private void showCloneButton(Long gridIdx) {
        elementWait.waitElementById(BUTTON_LIST_ADD_ID_BASE + gridIdx);
        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_LIST_ADD_ID_BASE + gridIdx)).click();

        elementWait.waitElementById(BUTTON_CLONE_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_CLONE_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_CLONE_ID_BASE + gridIdx);
    }

}
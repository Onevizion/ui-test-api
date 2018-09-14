package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class GridRowButtonHelper { //TODO rename to GridRowHelper

    private final static String BUTTON_LIST_EDIT_ID_BASE = "listEdit";
    private final static String BUTTON_EDIT_ROW_ID_BASE = "btnEditRow";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private Window window;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    public void openGridRowForm(Long gridIdx) {
        showGridRowButton(gridIdx);

        window.openModal(By.id(BUTTON_EDIT_ROW_ID_BASE + gridIdx));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridRowEditorLoad();
    }

    private void showGridRowButton(Long gridIdx) {
        elementWaitHelper.waitElementById(BUTTON_LIST_EDIT_ID_BASE + gridIdx);
        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_LIST_EDIT_ID_BASE + gridIdx)).click();

        elementWaitHelper.waitElementById(BUTTON_EDIT_ROW_ID_BASE + gridIdx);
        elementWaitHelper.waitElementVisibleById(BUTTON_EDIT_ROW_ID_BASE + gridIdx);
        elementWaitHelper.waitElementDisplayById(BUTTON_EDIT_ROW_ID_BASE + gridIdx);
    }

}
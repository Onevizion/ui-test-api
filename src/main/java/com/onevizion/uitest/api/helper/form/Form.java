package com.onevizion.uitest.api.helper.form;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.tab.Tab;

@Component
public class Form {

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private Tab tab;

    public void openAdd() {
        openAdd(AbstractSeleniumCore.getGridIdx().intValue());
    }

    public void openAdd(int gridIdx) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        tab.waitLoad(1);
    }

    public void openAddWithoutTabs() {
        openAddWithoutTabs(AbstractSeleniumCore.getGridIdx().intValue());
    }

    public void openAddWithoutTabs(int gridIdx) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openEdit() {
        openEdit(AbstractSeleniumCore.getGridIdx().intValue());
    }

    public void openEdit(int gridIdx) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        tab.waitLoad(1);
    }

    public void openEditWithoutTabs() {
        openEditWithoutTabs(AbstractSeleniumCore.getGridIdx().intValue());
    }

    public void openEditWithoutTabs(int gridIdx) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

}
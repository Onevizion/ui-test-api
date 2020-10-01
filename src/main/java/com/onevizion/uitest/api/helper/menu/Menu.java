package com.onevizion.uitest.api.helper.menu;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.helper.NewNewDropDown;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.helper.tree.Tree;

@Component
public class Menu {

    @Autowired
    private NewNewDropDown newNewDropDown;

    @Autowired
    private Tree tree;

    @Autowired
    private Jquery jquery;

    @Autowired
    private Wait wait;

    public void selectMenu(String name) {
        newNewDropDown.selectMenu(name);

        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad();
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad();
    }

    public void openAddMenuForm() {
        newNewDropDown.openAddMenuForm();

        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openEditMenuForm(String name) {
        newNewDropDown.openEditMenuForm(name);

        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        jquery.waitLoad();
    }

    public void openCloneMenuForm(String name) {
        newNewDropDown.openCloneMenuForm(name);

        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        jquery.waitLoad();
    }

    public void deleteMenu(String name) {
        newNewDropDown.deleteMenu(name);

        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad();
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad();
    }

}
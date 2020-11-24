package com.onevizion.uitest.api.helper.comppack;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.helper.NewNewDropDown;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.jquery.Jquery;

@Component
public class ComponentPackage {

    @Autowired
    private Grid2 grid2;

    @Autowired
    private Jquery jquery;

    @Autowired
    private Wait wait;

    @Autowired
    private NewNewDropDown newNewDropDown;

    public void selectComponentPackage(String name) {
        newNewDropDown.selectComponentPackage(name);
        grid2.waitLoad();
    }

    public void openAddComponentPackageForm() {
        newNewDropDown.openFormAddComponentPackage();

        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openEditComponentPackageForm(String name) {
        newNewDropDown.openFormEditComponentPackage(name);

        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        jquery.waitLoad();
    }

    public void deleteComponentPackage(String name) {
        newNewDropDown.deleteComponentPackage(name);
        grid2.waitLoad();
    }

}
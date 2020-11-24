package com.onevizion.uitest.api.helper.entity;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.comppack.ComponentPackage;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.vo.entity.ComponentPackageVo;

@Component
public class EntityComponentPackage {

    @Autowired
    private Window window;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ComponentPackage componentPackage;

    public void add(ComponentPackageVo componentPackageVo) {
        componentPackage.openAddComponentPackageForm();

        seleniumSettings.getWebDriver().findElement(By.name("name")).sendKeys(componentPackageVo.getName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(ComponentPackageVo componentPackageOld, ComponentPackageVo componentPackageVo) {
        componentPackage.openEditComponentPackageForm(componentPackageOld.getName());

        seleniumSettings.getWebDriver().findElement(By.name("name")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("name")).sendKeys(componentPackageVo.getName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testOnForm(ComponentPackageVo componentPackageVo) {
        componentPackage.openEditComponentPackageForm(componentPackageVo.getName());

        assertElement.assertText("name", componentPackageVo.getName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
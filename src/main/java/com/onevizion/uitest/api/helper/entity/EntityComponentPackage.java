package com.onevizion.uitest.api.helper.entity;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.NewDropDown;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.vo.entity.ComponentPackage;

@Component
public class EntityComponentPackage {

    @Resource
    private NewDropDown newDropDown;

    @Resource
    private Wait wait;

    @Resource
    private Window window;

    @Resource
    private Grid2 grid2;

    @Resource
    private AssertElement assertElement;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementWait elementWait;

    public void add(ComponentPackage componentPackage) {
        seleniumSettings.getWebDriver().findElement(By.id("new_lbCompPkg0")).click();
        elementWait.waitElementById("new_rows_lbCompPkg0");
        elementWait.waitElementVisibleById("new_lbCompPkg0");
        elementWait.waitElementDisplayById("new_rows_lbCompPkg0");

        window.openModal(By.id("btnAddCompPkg0"));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("name")).sendKeys(componentPackage.getName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad(AbstractSeleniumCore.getGridIdx());
    }

    public void edit(ComponentPackage componentPackageOld, ComponentPackage componentPackage) {
        newDropDown.openEditComponentPackageForm(componentPackageOld.getName());
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("name")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("name")).sendKeys(componentPackage.getName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad(AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(ComponentPackage componentPackage) {
        newDropDown.openEditComponentPackageForm(componentPackage.getName());
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertText("name", componentPackage.getName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
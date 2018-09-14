package com.onevizion.uitest.api.helper.entity;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.ElementWaitHelper;
import com.onevizion.uitest.api.helper.NewDropDown;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
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
    private AssertHelper assertHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    public void add(ComponentPackage componentPackage) {
        seleniumSettings.getWebDriver().findElement(By.id("new_lbCompPkg0")).click();
        elementWaitHelper.waitElementById("new_rows_lbCompPkg0");
        elementWaitHelper.waitElementVisibleById("new_lbCompPkg0");
        elementWaitHelper.waitElementDisplayById("new_rows_lbCompPkg0");

        window.openModal(By.id("btnAddCompPkg0"));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("name")).sendKeys(componentPackage.getName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(ComponentPackage componentPackageOld, ComponentPackage componentPackage) {
        newDropDown.openEditComponentPackageForm(componentPackageOld.getName());
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("name")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("name")).sendKeys(componentPackage.getName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(ComponentPackage componentPackage) {
        newDropDown.openEditComponentPackageForm(componentPackage.getName());
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertHelper.AssertText("name", componentPackage.getName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
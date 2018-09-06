package com.onevizion.guitest.helper.entity;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.AbstractSeleniumCore;
import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.helper.AssertHelper;
import com.onevizion.guitest.helper.ElementWaitHelper;
import com.onevizion.guitest.helper.NewDropDownHelper;
import com.onevizion.guitest.helper.WaitHelper;
import com.onevizion.guitest.helper.WindowHelper;
import com.onevizion.guitest.vo.entity.ComponentPackage;

@Component
public class EntityComponentPackageHelper {

    @Resource
    private NewDropDownHelper newDropDownHelper;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private WindowHelper windowHelper;

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

        windowHelper.openModal(By.id("btnAddCompPkg0"));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("name")).sendKeys(componentPackage.getName());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(ComponentPackage componentPackageOld, ComponentPackage componentPackage) {
        newDropDownHelper.openEditComponentPackageForm(componentPackageOld.getName());
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("name")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("name")).sendKeys(componentPackage.getName());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(ComponentPackage componentPackage) {
        newDropDownHelper.openEditComponentPackageForm(componentPackage.getName());
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        assertHelper.AssertText("name", componentPackage.getName());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
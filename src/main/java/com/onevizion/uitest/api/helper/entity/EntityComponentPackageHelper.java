package com.onevizion.uitest.api.helper.entity;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.ElementWaitHelper;
import com.onevizion.uitest.api.helper.NewDropDownHelper;
import com.onevizion.uitest.api.helper.WaitHelper;
import com.onevizion.uitest.api.helper.WindowHelper;
import com.onevizion.uitest.api.vo.entity.ComponentPackage;

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
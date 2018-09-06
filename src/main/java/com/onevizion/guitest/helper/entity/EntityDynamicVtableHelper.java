package com.onevizion.guitest.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.guitest.AbstractSeleniumCore;
import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.helper.AssertHelper;
import com.onevizion.guitest.helper.ElementHelper;
import com.onevizion.guitest.helper.GridHelper;
import com.onevizion.guitest.helper.JsHelper;
import com.onevizion.guitest.helper.TabHelper;
import com.onevizion.guitest.helper.WaitHelper;
import com.onevizion.guitest.helper.WindowHelper;
import com.onevizion.guitest.vo.entity.DynamicVtable;
import com.onevizion.guitest.vo.entity.DynamicVtableValue;

@Component
public class EntityDynamicVtableHelper {

    @Resource
    private WindowHelper windowHelper;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementHelper elementHelper;

    @Resource
    private TabHelper tabHelper;

    @Resource
    private EntityDynamicVtableValueHelper entityDynamicVtableValueHelper;

    public void addWithChilds(DynamicVtable dynamicVtable) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("attribVtableName")).sendKeys(dynamicVtable.getName());

        seleniumSettings.getWebDriver().findElement(By.name("attribVtableDesc")).sendKeys(dynamicVtable.getDesc());

        elementHelper.clickById(AbstractSeleniumCore.BUTTON_APPLY_ID);
        waitHelper.waitReloadForm("reloaded=1");
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        tabHelper.goToTab(2L); //Values
        waitHelper.waitGridLoad(2L, 2L);

        for (DynamicVtableValue dynamicVtableValue : dynamicVtable.getValues()) {
            entityDynamicVtableValueHelper.add(dynamicVtableValue);
        }

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void add(DynamicVtable dynamicVtable) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("attribVtableName")).sendKeys(dynamicVtable.getName());

        seleniumSettings.getWebDriver().findElement(By.name("attribVtableDesc")).sendKeys(dynamicVtable.getDesc());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(DynamicVtable dynamicVtable) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("attribVtableName")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("attribVtableName")).sendKeys(dynamicVtable.getName());

        seleniumSettings.getWebDriver().findElement(By.name("attribVtableDesc")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("attribVtableDesc")).sendKeys(dynamicVtable.getDesc());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testWithChilds(DynamicVtable dynamicVtable) {
        testInGrid(AbstractSeleniumCore.getGridIdx(), 0L, dynamicVtable);
        testOnForm(dynamicVtable);

        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        tabHelper.goToTab(2L); //Values
        waitHelper.waitGridLoad(2L, 2L);

        Assert.assertEquals(gridHelper.getGridRowsCount(2L), new Long(dynamicVtable.getValues().size()));
        for (int i = 0; i < dynamicVtable.getValues().size(); i++) {
            jsHelper.selectGridRow(2L, new Long(i));
            entityDynamicVtableValueHelper.testInGrid(2L, new Long(i), dynamicVtable.getValues().get(i));
            entityDynamicVtableValueHelper.testOnForm(dynamicVtable.getValues().get(i));
        }

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testOnForm(DynamicVtable dynamicVtable) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        assertHelper.AssertText("attribVtableName", dynamicVtable.getName());
        assertHelper.AssertText("attribVtableDesc", dynamicVtable.getDesc());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, DynamicVtable dynamicVtable) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Table Name"), dynamicVtable.getName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Description"), dynamicVtable.getDesc());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
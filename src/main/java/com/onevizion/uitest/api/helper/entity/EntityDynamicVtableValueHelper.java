package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.CheckboxHelper;
import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.JsHelper;
import com.onevizion.uitest.api.helper.WaitHelper;
import com.onevizion.uitest.api.helper.WindowHelper;
import com.onevizion.uitest.api.helper.colorpicker.ColorPickerHelper;
import com.onevizion.uitest.api.vo.entity.DynamicVtableValue;

@Component
public class EntityDynamicVtableValueHelper {

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
    private ColorPickerHelper colorPickerHelper;

    @Resource
    private CheckboxHelper checkboxHelper;

    public void add(DynamicVtableValue dynamicVtableValue) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + 2L));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("Value")).sendKeys(dynamicVtableValue.getValue());

        seleniumSettings.getWebDriver().findElement(By.name("OrderNum")).sendKeys(dynamicVtableValue.getOrdNum());

        windowHelper.openModal(By.name("btncolorDisplayed"));
        colorPickerHelper.setValue("#" + dynamicVtableValue.getColor());
        windowHelper.closeModal(By.className("dhx_button_save"));

        if ((dynamicVtableValue.getDisplay().equals("YES") && !checkboxHelper.isCheckedByName("display"))
                || (dynamicVtableValue.getDisplay().equals("NO") && checkboxHelper.isCheckedByName("display"))) {
            checkboxHelper.clickByName("display");
        }

        if ((dynamicVtableValue.getFilterable().equals("YES") && !checkboxHelper.isCheckedByName("filterable"))
                || (dynamicVtableValue.getFilterable().equals("NO") && checkboxHelper.isCheckedByName("filterable"))) {
            checkboxHelper.clickByName("filterable");
        }

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(2L, 2L);
    }

    public void edit(DynamicVtableValue dynamicVtableValue) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + 2L));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("Value")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("Value")).sendKeys(dynamicVtableValue.getValue());

        seleniumSettings.getWebDriver().findElement(By.name("OrderNum")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("OrderNum")).sendKeys(dynamicVtableValue.getOrdNum());

        windowHelper.openModal(By.name("btncolorDisplayed"));
        colorPickerHelper.setValue("#" + dynamicVtableValue.getColor());
        windowHelper.closeModal(By.className("dhx_button_save"));

        if ((dynamicVtableValue.getDisplay().equals("YES") && !checkboxHelper.isCheckedByName("display"))
                || (dynamicVtableValue.getDisplay().equals("NO") && checkboxHelper.isCheckedByName("display"))) {
            checkboxHelper.clickByName("display");
        }

        if ((dynamicVtableValue.getFilterable().equals("YES") && !checkboxHelper.isCheckedByName("filterable"))
                || (dynamicVtableValue.getFilterable().equals("NO") && checkboxHelper.isCheckedByName("filterable"))) {
            checkboxHelper.clickByName("filterable");
        }

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(2L, 2L);
    }

    public void testOnForm(DynamicVtableValue dynamicVtableValue) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + 2L));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        assertHelper.AssertText("Value", dynamicVtableValue.getValue());
        assertHelper.AssertText("OrderNum", dynamicVtableValue.getOrdNum());
        assertHelper.AssertText("color", dynamicVtableValue.getColor());
        assertHelper.AssertCheckBoxNew("display", dynamicVtableValue.getDisplay());
        assertHelper.AssertCheckBoxNew("filterable", dynamicVtableValue.getFilterable());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, DynamicVtableValue dynamicVtableValue) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Value"), dynamicVtableValue.getValue());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Order Number"), dynamicVtableValue.getOrdNum());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Color"), dynamicVtableValue.getColor());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Display?"), dynamicVtableValue.getDisplay());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Filterable?"), dynamicVtableValue.getFilterable());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
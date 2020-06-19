package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.colorpicker.ColorPicker;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.vo.entity.DynamicVtableValue;

@Component
public class EntityDynamicVtableValue {

    private static final String VALUE = "Value";
    private static final String LABEL = "valueLabel";
    private static final String ORD_NUM = "OrderNum";
    private static final String DISPLAY = "display";
    private static final String FILTERABLE = "filterable";

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Grid grid;

    @Autowired
    private Js js;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ColorPicker colorPicker;

    @Autowired
    private Checkbox checkbox;

    @Autowired
    private Grid2 grid2;

    public void add(DynamicVtableValue dynamicVtableValue) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + 2L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(VALUE)).sendKeys(dynamicVtableValue.getValue());

        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(dynamicVtableValue.getLabel());

        seleniumSettings.getWebDriver().findElement(By.name(ORD_NUM)).sendKeys(dynamicVtableValue.getOrdNum());

        window.openModal(By.name("btncolorDisplayed"));
        colorPicker.setValue("#" + dynamicVtableValue.getColor());
        window.closeModal(By.className("dhx_button_save"));

        if ((dynamicVtableValue.getDisplay().equals("YES") && !checkbox.isCheckedByName(DISPLAY))
                || (dynamicVtableValue.getDisplay().equals("NO") && checkbox.isCheckedByName(DISPLAY))) {
            checkbox.clickByName(DISPLAY);
        }

        if ((dynamicVtableValue.getFilterable().equals("YES") && !checkbox.isCheckedByName(FILTERABLE))
                || (dynamicVtableValue.getFilterable().equals("NO") && checkbox.isCheckedByName(FILTERABLE))) {
            checkbox.clickByName(FILTERABLE);
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad(2L);
    }

    public void edit(DynamicVtableValue dynamicVtableValue) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + 2L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(VALUE)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(VALUE)).sendKeys(dynamicVtableValue.getValue());

        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(dynamicVtableValue.getLabel());

        seleniumSettings.getWebDriver().findElement(By.name(ORD_NUM)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(ORD_NUM)).sendKeys(dynamicVtableValue.getOrdNum());

        window.openModal(By.name("btncolorDisplayed"));
        colorPicker.setValue("#" + dynamicVtableValue.getColor());
        window.closeModal(By.className("dhx_button_save"));

        if ((dynamicVtableValue.getDisplay().equals("YES") && !checkbox.isCheckedByName(DISPLAY))
                || (dynamicVtableValue.getDisplay().equals("NO") && checkbox.isCheckedByName(DISPLAY))) {
            checkbox.clickByName(DISPLAY);
        }

        if ((dynamicVtableValue.getFilterable().equals("YES") && !checkbox.isCheckedByName(FILTERABLE))
                || (dynamicVtableValue.getFilterable().equals("NO") && checkbox.isCheckedByName(FILTERABLE))) {
            checkbox.clickByName(FILTERABLE);
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad(2L);
    }

    public void testOnForm(DynamicVtableValue dynamicVtableValue) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + 2L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertText(VALUE, dynamicVtableValue.getValue());
        assertElement.assertText(LABEL, dynamicVtableValue.getLabel());
        assertElement.assertText(ORD_NUM, dynamicVtableValue.getOrdNum());
        assertElement.assertText("color", dynamicVtableValue.getColor());
        assertElement.assertCheckbox(DISPLAY, dynamicVtableValue.getDisplay());
        assertElement.assertCheckbox(FILTERABLE, dynamicVtableValue.getFilterable());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, DynamicVtableValue dynamicVtableValue) {
        Map<Long, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Value"), dynamicVtableValue.getValue());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Label"), dynamicVtableValue.getLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Order Number"), dynamicVtableValue.getOrdNum());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Color"), dynamicVtableValue.getColor());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Display?"), dynamicVtableValue.getDisplay());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Filterable?"), dynamicVtableValue.getFilterable());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
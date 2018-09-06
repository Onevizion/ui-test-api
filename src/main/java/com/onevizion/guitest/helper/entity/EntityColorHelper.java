package com.onevizion.guitest.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.AbstractSeleniumCore;
import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.helper.AssertHelper;
import com.onevizion.guitest.helper.GridHelper;
import com.onevizion.guitest.helper.JsHelper;
import com.onevizion.guitest.helper.WaitHelper;
import com.onevizion.guitest.helper.WindowHelper;
import com.onevizion.guitest.helper.colorpicker.ColorPickerHelper;
import com.onevizion.guitest.vo.entity.Color;

@Component
public class EntityColorHelper {

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

    public void add(Color color) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("colorName")).sendKeys(color.getName());

        windowHelper.openModal(By.name("btncolorPicker"));
        colorPickerHelper.setValue("#" + color.getValue());
        windowHelper.closeModal(By.className("dhx_button_save"));

        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(color.getDescription());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(Color color) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("colorName")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("colorName")).sendKeys(color.getName());

        windowHelper.openModal(By.name("btncolorPicker"));
        colorPickerHelper.setValue("#" + color.getValue());
        windowHelper.closeModal(By.className("dhx_button_save"));

        seleniumSettings.getWebDriver().findElement(By.name("description")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(color.getDescription());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(Color color) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        assertHelper.AssertText("colorName", color.getName());
        assertHelper.AssertText("rgbValue", color.getValue());
        assertHelper.AssertText("description", color.getDescription());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, Color color) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Color Code"), color.getName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "RGB Value"), color.getValue());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Description"), color.getDescription());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
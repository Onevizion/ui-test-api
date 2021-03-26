package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.colorpicker.ColorPicker;
import com.onevizion.uitest.api.helper.form.Form;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.vo.entity.Color;

@Component
public class EntityColor {

    @Autowired
    private Window window;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Form form;

    @Autowired
    private Grid grid;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private Js js;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ColorPicker colorPicker;

    public void add(Color color) {
        form.openAddWithoutTabs();

        seleniumSettings.getWebDriver().findElement(By.name("colorName")).sendKeys(color.getName());

        window.openModal(By.name("btncolorPicker"));
        colorPicker.setValue("#" + color.getValue());
        window.closeModal(By.className("dhx_button_save"));

        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(color.getDescription());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(Color color) {
        form.openEditWithoutTabs();

        seleniumSettings.getWebDriver().findElement(By.name("colorName")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("colorName")).sendKeys(color.getName());

        window.openModal(By.name("btncolorPicker"));
        colorPicker.setValue("#" + color.getValue());
        window.closeModal(By.className("dhx_button_save"));

        seleniumSettings.getWebDriver().findElement(By.name("description")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(color.getDescription());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testOnForm(Color color) {
        form.openEditWithoutTabs();

        assertElement.assertText("colorName", color.getName());
        assertElement.assertText("rgbValue", color.getValue());
        assertElement.assertText("description", color.getDescription());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, int rowIndex, Color color) {
        Map<Integer, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Color Code"), color.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "RGB Value"), color.getValue());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Description"), color.getDescription());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
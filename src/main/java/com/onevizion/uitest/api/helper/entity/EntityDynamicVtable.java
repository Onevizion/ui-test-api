package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.form.Form;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.grid.sort.GridSort;
import com.onevizion.uitest.api.helper.tab.Tab;
import com.onevizion.uitest.api.vo.SortType;
import com.onevizion.uitest.api.vo.entity.DynamicVtable;
import com.onevizion.uitest.api.vo.entity.DynamicVtableValue;

@Component
public class EntityDynamicVtable {

    private static final String NAME = "attribVtableName";
    private static final String DESCRIPTION = "attribVtableDesc";

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Form form;

    @Autowired
    private Grid grid;

    @Autowired
    private Js js;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Element element;

    @Autowired
    private Tab tab;

    @Autowired
    private EntityDynamicVtableValue entityDynamicVtableValue;

    @Autowired
    private GridSort gridSort;

    @Autowired
    private Grid2 grid2;

    public void addWithChilds(DynamicVtable dynamicVtable) {
        add(dynamicVtable);

        form.openEdit();

        tab.goToTab(2); //Values
        grid2.waitLoad(2L);

        for (DynamicVtableValue dynamicVtableValue : dynamicVtable.getValues()) {
            entityDynamicVtableValue.add(dynamicVtableValue);
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void add(DynamicVtable dynamicVtable) {
        form.openAdd();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(dynamicVtable.getName());

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(dynamicVtable.getDesc());

        element.clickById(AbstractSeleniumCore.BUTTON_APPLY_ID);
        wait.waitReloadForm("reloaded=1");
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tab.goToTab(3); //Components Package
        grid2.waitLoad(3L);
        grid.clearAssignmentGridColumn2(3L, 0L);
        grid.selectAssignmentGridColumn2New(3L, 0, 2, dynamicVtable.getPackages());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(DynamicVtable dynamicVtable) {
        form.openEdit();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(dynamicVtable.getName());

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(dynamicVtable.getDesc());

        tab.goToTab(3); //Components Package
        grid2.waitLoad(3L);
        grid.clearAssignmentGridColumn2(3L, 0L);
        grid.selectAssignmentGridColumn2New(3L, 0, 2, dynamicVtable.getPackages());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testWithChilds(DynamicVtable dynamicVtable) {
        testInGrid(AbstractSeleniumCore.getGridIdx(), 0, dynamicVtable);
        testOnForm(dynamicVtable);

        form.openEdit();

        tab.goToTab(2); //Values
        grid2.waitLoad(2L);

        gridSort.sortColumn(2L, SortType.ASC, "Value");

        Assert.assertEquals(grid.getGridRowsCount(2L), dynamicVtable.getValues().size());
        for (int i = 0; i < dynamicVtable.getValues().size(); i++) {
            js.selectGridRow(2L, i);
            entityDynamicVtableValue.testInGrid(2L, i, dynamicVtable.getValues().get(i));
            entityDynamicVtableValue.testOnForm(dynamicVtable.getValues().get(i));
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testOnForm(DynamicVtable dynamicVtable) {
        form.openEdit();

        assertElement.assertText(NAME, dynamicVtable.getName());
        assertElement.assertText(DESCRIPTION, dynamicVtable.getDesc());

        tab.goToTab(3); //Components Package
        grid2.waitLoad(3L);
        grid.checkAssignmentGridColumn2New(3L, 0, 2, dynamicVtable.getPackages());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, int rowIndex, DynamicVtable dynamicVtable) {
        Map<Integer, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Table Name"), dynamicVtable.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Description"), dynamicVtable.getDesc());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
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
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.tab.Tab;
import com.onevizion.uitest.api.vo.entity.WpDiscipline;

@Component
public class EntityWpDiscipline {

    private static final String NAME = "discpType";
    private static final String DESCRIPTION = "description";

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Tab tab;

    @Autowired
    private Grid grid;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Js js;

    public void add(WpDiscipline wpDiscipline) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(wpDiscipline.getName());

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(wpDiscipline.getDescription());

        tab.goToTab(2); //Role Assignments
        grid2.waitLoad(2L);
        grid.clearAssignmentGridColumn2(2L, 0L);
        grid.selectAssignmentGridColumn2New(2L, 0, 2, wpDiscipline.getRoles());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(WpDiscipline wpDiscipline) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(wpDiscipline.getName());

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(wpDiscipline.getDescription());

        tab.goToTab(2);//Role Assignments
        grid2.waitLoad(2L);
        grid.clearAssignmentGridColumn2(2L, 0L);
        grid.selectAssignmentGridColumn2New(2L, 0, 2, wpDiscipline.getRoles());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testOnForm(WpDiscipline wpDiscipline) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertText(NAME, wpDiscipline.getName());
        assertElement.assertText(DESCRIPTION, wpDiscipline.getDescription());

        tab.goToTab(2); //Role Assignments
        grid2.waitLoad(2L);
        grid.checkAssignmentGridColumn2New(2L, 0, 2, wpDiscipline.getRoles());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, int rowIndex, WpDiscipline wpDiscipline) {
        Map<Integer, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Discipline"), wpDiscipline.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Description"), wpDiscipline.getDescription());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Tab;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.vo.entity.WpDiscipline;

@Component
public class EntityWpDiscipline {

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Tab tab;

    @Resource
    private Grid grid;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Js js;

    public void add(WpDiscipline wpDiscipline) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("discpType")).sendKeys(wpDiscipline.getName());

        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(wpDiscipline.getDescription());

        tab.goToTab(2L); //Role Assignments
        wait.waitGridLoad(2L, 2L);
        grid.clearAssignmentGridColumn2(2L, 0L);
        grid.selectAssignmentGridColumn2New(2L, 0L, 2L, wpDiscipline.getRoles());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(WpDiscipline wpDiscipline) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("discpType")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("discpType")).sendKeys(wpDiscipline.getName());

        seleniumSettings.getWebDriver().findElement(By.name("description")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(wpDiscipline.getDescription());

        tab.goToTab(2L);//Role Assignments
        wait.waitGridLoad(2L, 2L);
        grid.clearAssignmentGridColumn2(2L, 0L);
        grid.selectAssignmentGridColumn2New(2L, 0L, 2L, wpDiscipline.getRoles());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(WpDiscipline wpDiscipline) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.AssertText("discpType", wpDiscipline.getName());
        assertElement.AssertText("description", wpDiscipline.getDescription());

        tab.goToTab(2L); //Role Assignments
        wait.waitGridLoad(2L, 2L);
        grid.checkAssignmentGridColumn2New(2L, 0L, 2L, wpDiscipline.getRoles());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, WpDiscipline wpDiscipline) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Discipline"), wpDiscipline.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Description"), wpDiscipline.getDescription());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
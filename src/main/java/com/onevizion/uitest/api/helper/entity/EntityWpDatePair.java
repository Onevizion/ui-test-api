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
import com.onevizion.uitest.api.vo.entity.WpDatePair;

@Component
public class EntityWpDatePair {

    private static final String NAME = "wpTaskDateType";
    private static final String LABEL = "label";
    private static final String SHORT_LABEL = "abbrLabel";

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

    public void add(WpDatePair wpDatePair) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(wpDatePair.getName());

        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(wpDatePair.getLabel());

        seleniumSettings.getWebDriver().findElement(By.name(SHORT_LABEL)).sendKeys(wpDatePair.getShortLabel());

        tab.goToTab(2L); //Role Privs
        wait.waitGridLoad(2L, 2L);
        grid.clearAssignmentGridColumn(2L, 1L);
        grid.clearAssignmentGridColumn(2L, 2L);
        grid.clearAssignmentGridColumn(2L, 3L);
        grid.clearAssignmentGridColumn(2L, 4L);
        grid.clearAssignmentGridColumn(2L, 5L);
        grid.selectAssignmentGridColumnNew(2L, 1L, 0L, wpDatePair.getRoles(), "R");
        grid.selectAssignmentGridColumnNew(2L, 2L, 0L, wpDatePair.getRoles(), "E");
        grid.selectAssignmentGridColumnNew(2L, 3L, 0L, wpDatePair.getRoles(), "A");
        grid.selectAssignmentGridColumnNew(2L, 4L, 0L, wpDatePair.getRoles(), "D");
        grid.selectAssignmentGridColumnNew(2L, 5L, 0L, wpDatePair.getRoles(), "N");

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(WpDatePair wpDatePair) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(wpDatePair.getName());

        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(wpDatePair.getLabel());

        seleniumSettings.getWebDriver().findElement(By.name(SHORT_LABEL)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(SHORT_LABEL)).sendKeys(wpDatePair.getShortLabel());

        tab.goToTab(2L); //Role Privs
        wait.waitGridLoad(2L, 2L);
        grid.clearAssignmentGridColumn(2L, 1L);
        grid.clearAssignmentGridColumn(2L, 2L);
        grid.clearAssignmentGridColumn(2L, 3L);
        grid.clearAssignmentGridColumn(2L, 4L);
        grid.clearAssignmentGridColumn(2L, 5L);
        grid.selectAssignmentGridColumnNew(2L, 1L, 0L, wpDatePair.getRoles(), "R");
        grid.selectAssignmentGridColumnNew(2L, 2L, 0L, wpDatePair.getRoles(), "E");
        grid.selectAssignmentGridColumnNew(2L, 3L, 0L, wpDatePair.getRoles(), "A");
        grid.selectAssignmentGridColumnNew(2L, 4L, 0L, wpDatePair.getRoles(), "D");
        grid.selectAssignmentGridColumnNew(2L, 5L, 0L, wpDatePair.getRoles(), "N");

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(WpDatePair wpDatePair) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertText(NAME, wpDatePair.getName());
        assertElement.assertText(LABEL, wpDatePair.getLabel());
        assertElement.assertText(SHORT_LABEL, wpDatePair.getShortLabel());

        tab.goToTab(2L); //Role Privs
        wait.waitGridLoad(2L, 2L);
        grid.checkAssignmentGridColumnNew(2L, 1L, 0L, wpDatePair.getRoles(), "R");
        grid.checkAssignmentGridColumnNew(2L, 2L, 0L, wpDatePair.getRoles(), "E");
        grid.checkAssignmentGridColumnNew(2L, 3L, 0L, wpDatePair.getRoles(), "A");
        grid.checkAssignmentGridColumnNew(2L, 4L, 0L, wpDatePair.getRoles(), "D");
        grid.checkAssignmentGridColumnNew(2L, 5L, 0L, wpDatePair.getRoles(), "N");

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, WpDatePair wpDatePair) {
        Map<Long, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Date Pair Name"), wpDatePair.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Label"), wpDatePair.getLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Short Label"), wpDatePair.getShortLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "In Use"), wpDatePair.getUse());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
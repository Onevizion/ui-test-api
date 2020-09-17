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
import com.onevizion.uitest.api.vo.WpDatePairType;
import com.onevizion.uitest.api.vo.entity.WpDatePair;

@Component
public class EntityWpDatePair {

    private static final String NAME = "wpTaskDateType";
    private static final String LABEL = "label";
    private static final String SHORT_NAME = "abbrName";
    private static final String SHORT_LABEL = "abbrLabel";

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

    public void add(WpDatePair wpDatePair) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(wpDatePair.getName());

        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(wpDatePair.getLabel());

        seleniumSettings.getWebDriver().findElement(By.name(SHORT_NAME)).sendKeys(wpDatePair.getShortName());

        seleniumSettings.getWebDriver().findElement(By.name(SHORT_LABEL)).sendKeys(wpDatePair.getShortLabel());

        tab.goToTab(2); //Role Privs
        grid2.waitLoad(2L);
        grid.clearAssignmentGridColumn(2L, 1);
        grid.clearAssignmentGridColumn(2L, 2);
        grid.clearAssignmentGridColumn(2L, 3);
        grid.clearAssignmentGridColumn(2L, 4);
        grid.clearAssignmentGridColumn(2L, 5);
        grid.selectAssignmentGridColumnNew(2L, 1, 0, wpDatePair.getRoles(), "R");
        grid.selectAssignmentGridColumnNew(2L, 2, 0, wpDatePair.getRoles(), "E");
        grid.selectAssignmentGridColumnNew(2L, 3, 0, wpDatePair.getRoles(), "A");
        grid.selectAssignmentGridColumnNew(2L, 4, 0, wpDatePair.getRoles(), "D");
        grid.selectAssignmentGridColumnNew(2L, 5, 0, wpDatePair.getRoles(), "N");

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(WpDatePair wpDatePair) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(wpDatePair.getName());

        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(wpDatePair.getLabel());

        seleniumSettings.getWebDriver().findElement(By.name(SHORT_NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(SHORT_NAME)).sendKeys(wpDatePair.getShortName());

        seleniumSettings.getWebDriver().findElement(By.name(SHORT_LABEL)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(SHORT_LABEL)).sendKeys(wpDatePair.getShortLabel());

        tab.goToTab(2); //Role Privs
        grid2.waitLoad(2L);
        grid.clearAssignmentGridColumn(2L, 1);
        grid.clearAssignmentGridColumn(2L, 2);
        grid.clearAssignmentGridColumn(2L, 3);
        grid.clearAssignmentGridColumn(2L, 4);
        grid.clearAssignmentGridColumn(2L, 5);
        grid.selectAssignmentGridColumnNew(2L, 1, 0, wpDatePair.getRoles(), "R");
        grid.selectAssignmentGridColumnNew(2L, 2, 0, wpDatePair.getRoles(), "E");
        grid.selectAssignmentGridColumnNew(2L, 3, 0, wpDatePair.getRoles(), "A");
        grid.selectAssignmentGridColumnNew(2L, 4, 0, wpDatePair.getRoles(), "D");
        grid.selectAssignmentGridColumnNew(2L, 5, 0, wpDatePair.getRoles(), "N");

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testOnForm(WpDatePair wpDatePair) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertText(NAME, wpDatePair.getName());
        assertElement.assertText(LABEL, wpDatePair.getLabel());
        assertElement.assertText(SHORT_NAME, wpDatePair.getShortName());
        assertElement.assertText(SHORT_LABEL, wpDatePair.getShortLabel());

        if (!WpDatePairType.PROJECTED_DELTA.getName().equals(wpDatePair.getName())) {
            tab.goToTab(2); //Role Privs
            grid2.waitLoad(2L);
            grid.checkAssignmentGridColumnNew(2L, 1, 0, wpDatePair.getRoles(), "R");
            if (!WpDatePairType.BASELINE.getName().equals(wpDatePair.getName())
                    && !WpDatePairType.EARLY.getName().equals(wpDatePair.getName())
                    && !WpDatePairType.LATE.getName().equals(wpDatePair.getName())) {
                grid.checkAssignmentGridColumnNew(2L, 2, 0, wpDatePair.getRoles(), "E");
                grid.checkAssignmentGridColumnNew(2L, 3, 0, wpDatePair.getRoles(), "A");
                grid.checkAssignmentGridColumnNew(2L, 4, 0, wpDatePair.getRoles(), "D");
            }
            grid.checkAssignmentGridColumnNew(2L, 5, 0, wpDatePair.getRoles(), "N");
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, int rowIndex, WpDatePair wpDatePair) {
        Map<Integer, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Date Pair Name"), wpDatePair.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Label"), wpDatePair.getLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Short Name"), wpDatePair.getShortName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Short Label"), wpDatePair.getShortLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "In Use"), wpDatePair.getUse());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
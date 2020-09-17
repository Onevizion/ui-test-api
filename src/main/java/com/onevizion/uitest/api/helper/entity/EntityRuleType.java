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
import com.onevizion.uitest.api.vo.entity.RuleType;

@Component
public class EntityRuleType {

    private static final String NAME = "ruleType";
    private static final String LABEL = "TypeLabel";
    private static final String DESCRIPTION = "DescLabel";
    private static final String START_DATE_TIME = "startDate";
    private static final String REPEAT_INTERVAL = "repeatInterval";

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private Js js;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Grid grid;

    public void add(RuleType ruleType) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(ruleType.getName());
        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(ruleType.getLabel());
        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(ruleType.getDescription());
        seleniumSettings.getWebDriver().findElement(By.name(START_DATE_TIME)).sendKeys(ruleType.getStartDateTime());
        seleniumSettings.getWebDriver().findElement(By.name(REPEAT_INTERVAL)).sendKeys(ruleType.getRepeatInterval());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(RuleType ruleType) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(ruleType.getName());
        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(ruleType.getLabel());
        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(ruleType.getDescription());
        seleniumSettings.getWebDriver().findElement(By.name(START_DATE_TIME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(START_DATE_TIME)).sendKeys(ruleType.getStartDateTime());
        seleniumSettings.getWebDriver().findElement(By.name(REPEAT_INTERVAL)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(REPEAT_INTERVAL)).sendKeys(ruleType.getRepeatInterval());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testOnForm(RuleType ruleType) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertText(NAME, ruleType.getName());
        assertElement.assertText(LABEL, ruleType.getLabel());
        assertElement.assertText(DESCRIPTION, ruleType.getDescription());
        assertElement.assertText(START_DATE_TIME, ruleType.getStartDateTime());
        assertElement.assertText(REPEAT_INTERVAL, ruleType.getRepeatInterval());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, int rowIndex, RuleType ruleType) {
        Map<Integer, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Rule Type"), ruleType.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Rule Type Label"), ruleType.getLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Description"), ruleType.getDescription());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Start Date/Time"), ruleType.getStartDateTime());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Repeat Interval"), ruleType.getRepeatInterval());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
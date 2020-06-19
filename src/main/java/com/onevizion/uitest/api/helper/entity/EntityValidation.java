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
import com.onevizion.uitest.api.vo.entity.Validation;

@Component
public class EntityValidation {

    private static final String NAME = "name";
    private static final String PATTERN = "pattern";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private SeleniumSettings seleniumSettings;

    public void add(Validation validation) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(validation.getName());
        seleniumSettings.getWebDriver().findElement(By.name(PATTERN)).sendKeys(validation.getPattern());
        seleniumSettings.getWebDriver().findElement(By.name(ERROR_MESSAGE)).sendKeys(validation.getErrorMessage());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(Validation validation) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(validation.getName());
        seleniumSettings.getWebDriver().findElement(By.name(PATTERN)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(PATTERN)).sendKeys(validation.getPattern());
        seleniumSettings.getWebDriver().findElement(By.name(ERROR_MESSAGE)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(ERROR_MESSAGE)).sendKeys(validation.getErrorMessage());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testOnForm(Validation validation) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertText(NAME, validation.getName());
        assertElement.assertText(PATTERN, validation.getPattern());
        assertElement.assertText(ERROR_MESSAGE, validation.getErrorMessage());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, Validation validation) {
        Map<Long, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Name"), validation.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Pattern"), validation.getPattern());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Error Message"), validation.getErrorMessage());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
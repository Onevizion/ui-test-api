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
import com.onevizion.uitest.api.vo.entity.TrackorClass;

@Component
public class EntityTrackorClass {

    private static final String NAME = "className";
    private static final String ORDER_NUMBER = "orderNumber";

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private Grid2 grid2;

    public void add(TrackorClass trackorClass) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        //trackorType disabled

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(trackorClass.getName());

        seleniumSettings.getWebDriver().findElement(By.name(ORDER_NUMBER)).sendKeys(trackorClass.getOrderNumber());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(TrackorClass trackorClass) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        //trackorType disabled

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(trackorClass.getName());

        seleniumSettings.getWebDriver().findElement(By.name(ORDER_NUMBER)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(ORDER_NUMBER)).sendKeys(trackorClass.getOrderNumber());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testOnForm(TrackorClass trackorClass) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertText("trackorType", trackorClass.getTrackorType());
        assertElement.assertText(NAME, trackorClass.getName());
        assertElement.assertText(ORDER_NUMBER, trackorClass.getOrderNumber());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, TrackorClass trackorClass) {
        Map<Integer, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Trackor Type"), trackorClass.getTrackorType());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Trackor Class"), trackorClass.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Order Number"), trackorClass.getOrderNumber());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Used"), trackorClass.getUsedCount());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
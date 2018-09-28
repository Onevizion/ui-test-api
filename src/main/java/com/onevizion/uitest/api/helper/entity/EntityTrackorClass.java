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
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.vo.entity.TrackorClass;

@Component
public class EntityTrackorClass {

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Js js;

    @Resource
    private Grid grid;

    public void add(TrackorClass trackorClass) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        //trackorType disabled

        seleniumSettings.getWebDriver().findElement(By.name("className")).sendKeys(trackorClass.getName());

        seleniumSettings.getWebDriver().findElement(By.name("orderNumber")).sendKeys(trackorClass.getOrderNumber());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(TrackorClass trackorClass) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        //trackorType disabled

        seleniumSettings.getWebDriver().findElement(By.name("className")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("className")).sendKeys(trackorClass.getName());

        seleniumSettings.getWebDriver().findElement(By.name("orderNumber")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("orderNumber")).sendKeys(trackorClass.getOrderNumber());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(TrackorClass trackorClass) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.AssertText("trackorType", trackorClass.getTrackorType());
        assertElement.AssertText("className", trackorClass.getName());
        assertElement.AssertText("orderNumber", trackorClass.getOrderNumber());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, TrackorClass trackorClass) {
        Map<Long, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Trackor Type"), trackorClass.getTrackorType());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Trackor Class"), trackorClass.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Order Number"), trackorClass.getOrderNumber());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Used"), trackorClass.getUsedCount());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
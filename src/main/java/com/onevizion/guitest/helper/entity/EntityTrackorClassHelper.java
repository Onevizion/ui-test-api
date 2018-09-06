package com.onevizion.guitest.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.AbstractSeleniumCore;
import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.helper.AssertHelper;
import com.onevizion.guitest.helper.GridHelper;
import com.onevizion.guitest.helper.JsHelper;
import com.onevizion.guitest.helper.WaitHelper;
import com.onevizion.guitest.helper.WindowHelper;
import com.onevizion.guitest.vo.entity.TrackorClass;

@Component
public class EntityTrackorClassHelper {

    @Resource
    private WindowHelper windowHelper;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private GridHelper gridHelper;

    public void add(TrackorClass trackorClass) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        //trackorType disabled

        seleniumSettings.getWebDriver().findElement(By.name("className")).sendKeys(trackorClass.getName());

        seleniumSettings.getWebDriver().findElement(By.name("orderNumber")).sendKeys(trackorClass.getOrderNumber());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(TrackorClass trackorClass) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        //trackorType disabled

        seleniumSettings.getWebDriver().findElement(By.name("className")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("className")).sendKeys(trackorClass.getName());

        seleniumSettings.getWebDriver().findElement(By.name("orderNumber")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("orderNumber")).sendKeys(trackorClass.getOrderNumber());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(TrackorClass trackorClass) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        assertHelper.AssertText("trackorType", trackorClass.getTrackorType());
        assertHelper.AssertText("className", trackorClass.getName());
        assertHelper.AssertText("orderNumber", trackorClass.getOrderNumber());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, TrackorClass trackorClass) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Trackor Type"), trackorClass.getTrackorType());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Trackor Class"), trackorClass.getName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Order Number"), trackorClass.getOrderNumber());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Used"), trackorClass.getUsedCount());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
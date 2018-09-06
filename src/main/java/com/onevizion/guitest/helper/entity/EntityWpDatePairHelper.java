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
import com.onevizion.guitest.helper.TabHelper;
import com.onevizion.guitest.helper.WaitHelper;
import com.onevizion.guitest.helper.WindowHelper;
import com.onevizion.guitest.vo.entity.WpDatePair;

@Component
public class EntityWpDatePairHelper {

    @Resource
    private WindowHelper windowHelper;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private TabHelper tabHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private JsHelper jsHelper;

    public void add(WpDatePair wpDatePair) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("wpTaskDateType")).sendKeys(wpDatePair.getName());

        seleniumSettings.getWebDriver().findElement(By.name("label")).sendKeys(wpDatePair.getLabel());

        seleniumSettings.getWebDriver().findElement(By.name("abbrLabel")).sendKeys(wpDatePair.getShortLabel());

        tabHelper.goToTab(2L); //Role Privs
        waitHelper.waitGridLoad(2L, 2L);
        gridHelper.clearAssignmentGridColumn(2L, 1L);
        gridHelper.clearAssignmentGridColumn(2L, 2L);
        gridHelper.clearAssignmentGridColumn(2L, 3L);
        gridHelper.clearAssignmentGridColumn(2L, 4L);
        gridHelper.clearAssignmentGridColumn(2L, 5L);
        gridHelper.selectAssignmentGridColumnNew(2L, 1L, 0L, wpDatePair.getRoles(), "R");
        gridHelper.selectAssignmentGridColumnNew(2L, 2L, 0L, wpDatePair.getRoles(), "E");
        gridHelper.selectAssignmentGridColumnNew(2L, 3L, 0L, wpDatePair.getRoles(), "A");
        gridHelper.selectAssignmentGridColumnNew(2L, 4L, 0L, wpDatePair.getRoles(), "D");
        gridHelper.selectAssignmentGridColumnNew(2L, 5L, 0L, wpDatePair.getRoles(), "N");

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(WpDatePair wpDatePair) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("wpTaskDateType")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("wpTaskDateType")).sendKeys(wpDatePair.getName());

        seleniumSettings.getWebDriver().findElement(By.name("label")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("label")).sendKeys(wpDatePair.getLabel());

        seleniumSettings.getWebDriver().findElement(By.name("abbrLabel")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("abbrLabel")).sendKeys(wpDatePair.getShortLabel());

        tabHelper.goToTab(2L); //Role Privs
        waitHelper.waitGridLoad(2L, 2L);
        gridHelper.clearAssignmentGridColumn(2L, 1L);
        gridHelper.clearAssignmentGridColumn(2L, 2L);
        gridHelper.clearAssignmentGridColumn(2L, 3L);
        gridHelper.clearAssignmentGridColumn(2L, 4L);
        gridHelper.clearAssignmentGridColumn(2L, 5L);
        gridHelper.selectAssignmentGridColumnNew(2L, 1L, 0L, wpDatePair.getRoles(), "R");
        gridHelper.selectAssignmentGridColumnNew(2L, 2L, 0L, wpDatePair.getRoles(), "E");
        gridHelper.selectAssignmentGridColumnNew(2L, 3L, 0L, wpDatePair.getRoles(), "A");
        gridHelper.selectAssignmentGridColumnNew(2L, 4L, 0L, wpDatePair.getRoles(), "D");
        gridHelper.selectAssignmentGridColumnNew(2L, 5L, 0L, wpDatePair.getRoles(), "N");

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(WpDatePair wpDatePair) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        assertHelper.AssertText("wpTaskDateType", wpDatePair.getName());
        assertHelper.AssertText("label", wpDatePair.getLabel());
        assertHelper.AssertText("abbrLabel", wpDatePair.getShortLabel());

        tabHelper.goToTab(2L); //Role Privs
        waitHelper.waitGridLoad(2L, 2L);
        gridHelper.checkAssignmentGridColumnNew(2L, 1L, 0L, wpDatePair.getRoles(), "R");
        gridHelper.checkAssignmentGridColumnNew(2L, 2L, 0L, wpDatePair.getRoles(), "E");
        gridHelper.checkAssignmentGridColumnNew(2L, 3L, 0L, wpDatePair.getRoles(), "A");
        gridHelper.checkAssignmentGridColumnNew(2L, 4L, 0L, wpDatePair.getRoles(), "D");
        gridHelper.checkAssignmentGridColumnNew(2L, 5L, 0L, wpDatePair.getRoles(), "N");

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, WpDatePair wpDatePair) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Date Pair Name"), wpDatePair.getName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Label"), wpDatePair.getLabel());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Short Label"), wpDatePair.getShortLabel());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "In Use"), wpDatePair.getUse());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
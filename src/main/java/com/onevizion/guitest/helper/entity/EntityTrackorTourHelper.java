package com.onevizion.guitest.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.guitest.AbstractSeleniumCore;
import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.exception.SeleniumUnexpectedException;
import com.onevizion.guitest.helper.AssertHelper;
import com.onevizion.guitest.helper.GridHelper;
import com.onevizion.guitest.helper.JsHelper;
import com.onevizion.guitest.helper.PsSelectorHelper;
import com.onevizion.guitest.helper.TabHelper;
import com.onevizion.guitest.helper.WaitHelper;
import com.onevizion.guitest.helper.WindowHelper;
import com.onevizion.guitest.vo.entity.TrackorTour;
import com.onevizion.guitest.vo.entity.TrackorTourStep;

@Component
public class EntityTrackorTourHelper {

    @Resource
    private WindowHelper windowHelper;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private PsSelectorHelper psSelectorHelper;

    @Resource
    private TabHelper tabHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private EntityTrackorTourStepHelper entityTrackorTourStepHelper;

    public void addWithChilds(TrackorTour trackorTour) {
        add(trackorTour);

        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        tabHelper.goToTab(2L); //Tour Steps
        waitHelper.waitGridLoad(2L, 2L);

        for (TrackorTourStep trackorTourStep : trackorTour.getSteps()) {
            entityTrackorTourStepHelper.add(trackorTourStep);
        }

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void add(TrackorTour trackorTour) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("label")).sendKeys(trackorTour.getLabel());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("xitorTypeId"))).selectByVisibleText(trackorTour.getTrackorType());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("startTourPlace"))).selectByVisibleText(trackorTour.getStartPlace());

        if ("Page".equals(trackorTour.getStartPlace())) {
            psSelectorHelper.selectSpecificValue(By.name("btngridPageName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorTour.getPageName(), 1L);
        } else if ("Config Applet".equals(trackorTour.getStartPlace())) {
            psSelectorHelper.selectSpecificValue(By.name("btnconfigAppName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorTour.getAppletName(), 1L);
        } else if ("Config Tab".equals(trackorTour.getStartPlace())) {
            psSelectorHelper.selectSpecificValue(By.name("btnconfigGroupName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorTour.getTabName(), 1L);
        } else {
            throw new SeleniumUnexpectedException("Not support StartPlace [" + trackorTour.getStartPlace() + "]");
        }

        new Select(seleniumSettings.getWebDriver().findElement(By.name("orderNumber"))).selectByVisibleText(trackorTour.getOrderNumber());

        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(trackorTour.getDescription());

        tabHelper.goToTab(2L); //Role Assignments
        waitHelper.waitGridLoad(2L, 2L);
        gridHelper.clearAssignmentGridColumn2(2L, 0L);
        gridHelper.selectAssignmentGridColumn2New(2L, 0L, 2L, trackorTour.getRoles());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(TrackorTour trackorTour) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("label")).clear();;
        seleniumSettings.getWebDriver().findElement(By.name("label")).sendKeys(trackorTour.getLabel());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("xitorTypeId"))).selectByVisibleText(trackorTour.getTrackorType());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("startTourPlace"))).selectByVisibleText(trackorTour.getStartPlace());

        if ("Page".equals(trackorTour.getStartPlace())) {
            psSelectorHelper.selectSpecificValue(By.name("btngridPageName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorTour.getPageName(), 1L);
        } else if ("Config Applet".equals(trackorTour.getStartPlace())) {
            psSelectorHelper.selectSpecificValue(By.name("btnconfigAppName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorTour.getAppletName(), 1L);
        } else if ("Config Tab".equals(trackorTour.getStartPlace())) {
            psSelectorHelper.selectSpecificValue(By.name("btnconfigGroupName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorTour.getTabName(), 1L);
        } else {
            throw new SeleniumUnexpectedException("Not support StartPlace [" + trackorTour.getStartPlace() + "]");
        }

        new Select(seleniumSettings.getWebDriver().findElement(By.name("orderNumber"))).selectByVisibleText(trackorTour.getOrderNumber());

        seleniumSettings.getWebDriver().findElement(By.name("description")).clear();;
        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(trackorTour.getDescription());

        tabHelper.goToTab(3L);//Role Assignments
        waitHelper.waitGridLoad(3L, 3L);
        gridHelper.clearAssignmentGridColumn2(3L, 0L);
        gridHelper.selectAssignmentGridColumn2New(3L, 0L, 2L, trackorTour.getRoles());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testWithChilds(TrackorTour trackorTour) {
        testInGrid(AbstractSeleniumCore.getGridIdx(), 0L, trackorTour);
        testOnForm(trackorTour);

        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        tabHelper.goToTab(2L); //Tour Steps
        waitHelper.waitGridLoad(2L, 2L);

        Assert.assertEquals(gridHelper.getGridRowsCount(2L), new Long(trackorTour.getSteps().size()));
        for (int i = 0; i < trackorTour.getSteps().size(); i++) {
            jsHelper.selectGridRow(2L, new Long(i));
            entityTrackorTourStepHelper.testInGrid(2L, new Long(i), trackorTour.getSteps().get(i));
            entityTrackorTourStepHelper.testOnForm(trackorTour.getSteps().get(i));
        }

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testOnForm(TrackorTour trackorTour) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        assertHelper.AssertText("label", trackorTour.getLabel());
        assertHelper.AssertSelect("xitorTypeId", trackorTour.getTrackorType());
        assertHelper.AssertSelect("startTourPlace", trackorTour.getStartPlace());

        if ("Page".equals(trackorTour.getStartPlace())) {
            assertHelper.AssertRadioPsSelector("gridPageName", "btngridPageName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorTour.getPageName(), 1L, true);
        } else if ("Config Applet".equals(trackorTour.getStartPlace())) {
            assertHelper.AssertRadioPsSelector("configAppName", "btnconfigAppName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorTour.getAppletName(), 1L, true);
        } else if ("Config Tab".equals(trackorTour.getStartPlace())) {
            assertHelper.AssertRadioPsSelector("configGroupName", "btnconfigGroupName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorTour.getTabName(), 1L, true);
        } else {
            throw new SeleniumUnexpectedException("Not support StartPlace [" + trackorTour.getStartPlace() + "]");
        }

        assertHelper.AssertSelect("orderNumber", trackorTour.getOrderNumber());
        assertHelper.AssertText("description", trackorTour.getDescription());

        tabHelper.goToTab(3L); //Role Assignments
        waitHelper.waitGridLoad(3L, 3L);
        gridHelper.checkAssignmentGridColumn2New(3L, 0L, 2L, trackorTour.getRoles());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, TrackorTour trackorTour) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Tour Label"), trackorTour.getLabel());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Trackor Type"), trackorTour.getTrackorType());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Page Name"), trackorTour.getPageName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Config App Name"), trackorTour.getAppletName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Config Tab Name"), trackorTour.getTabName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Order Number"), trackorTour.getOrderNumber());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Description"), trackorTour.getDescription());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.JsHelper;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.vo.entity.ReportGroup;

@Component
public class EntityReportGroup {

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private GridHelper gridHelper;

    public void add(ReportGroup reportGroup) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("reportGroup")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("reportGroup")).sendKeys(reportGroup.getName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(ReportGroup reportGroup) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("reportGroup")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("reportGroup")).sendKeys(reportGroup.getName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(ReportGroup reportGroup) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertHelper.AssertText("reportGroup", reportGroup.getName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, ReportGroup reportGroup) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Report Group Name"), reportGroup.getName());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
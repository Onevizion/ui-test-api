package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.CheckboxHelper;
import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.JsHelper;
import com.onevizion.uitest.api.helper.PsSelector;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.vo.entity.TrackorForm;

@Component
public class EntityTrackorForm {

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private PsSelector psSelector;

    @Resource
    private CheckboxHelper checkboxHelper;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private GridHelper gridHelper;

    public void add(TrackorForm trackorForm) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("trackorFormName")).sendKeys(trackorForm.getName());

        By btnOpenReport = By.xpath("//*[string(@submitName)='btnreportName'] | //*[string(@name)='btnreportName']");
        psSelector.selectSpecificValue(btnOpenReport, By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorForm.getReportName(), 1L);

        By btnOpenImport = By.xpath("//*[string(@submitName)='btnimportName'] | //*[string(@name)='btnimportName']");
        psSelector.selectSpecificValue(btnOpenImport, By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorForm.getImportName(), 1L);

        By btnOpenUser = By.xpath("//*[string(@submitName)='btnuserName'] | //*[string(@name)='btnuserName']");
        psSelector.selectSpecificValue(btnOpenUser, By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorForm.getUserName(), 1L);

        seleniumSettings.getWebDriver().findElement(By.name("impScheduleInterval")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("impScheduleInterval")).sendKeys(trackorForm.getInterval());

        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(trackorForm.getDescription());

        if ((trackorForm.getImportEmailSubject().equals("YES") && !checkboxHelper.isCheckedByName("impEmailSubject"))
                || (trackorForm.getImportEmailSubject().equals("NO") && checkboxHelper.isCheckedByName("impEmailSubject"))) {
            checkboxHelper.clickByName("impEmailSubject");
        }

        if ((trackorForm.getImportEmailBody().equals("YES") && !checkboxHelper.isCheckedByName("impEmailBody"))
                || (trackorForm.getImportEmailBody().equals("NO") && checkboxHelper.isCheckedByName("impEmailBody"))) {
            checkboxHelper.clickByName("impEmailBody");
        }

        if ((trackorForm.getCreateImport().equals("YES") && !checkboxHelper.isCheckedByName("createImport"))
                || (trackorForm.getCreateImport().equals("NO") && checkboxHelper.isCheckedByName("createImport"))) {
            checkboxHelper.clickByName("createImport");
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(TrackorForm trackorForm) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("trackorFormName")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("trackorFormName")).sendKeys(trackorForm.getName());

        By btnOpenReport = By.xpath("//*[string(@submitName)='btnreportName'] | //*[string(@name)='btnreportName']");
        psSelector.selectSpecificValue(btnOpenReport, By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorForm.getReportName(), 1L);

        By btnOpenImport = By.xpath("//*[string(@submitName)='btnimportName'] | //*[string(@name)='btnimportName']");
        psSelector.selectSpecificValue(btnOpenImport, By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorForm.getImportName(), 1L);

        By btnOpenUser = By.xpath("//*[string(@submitName)='btnuserName'] | //*[string(@name)='btnuserName']");
        psSelector.selectSpecificValue(btnOpenUser, By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorForm.getUserName(), 1L);

        seleniumSettings.getWebDriver().findElement(By.name("impScheduleInterval")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("impScheduleInterval")).sendKeys(trackorForm.getInterval());

        seleniumSettings.getWebDriver().findElement(By.name("description")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(trackorForm.getDescription());

        if ((trackorForm.getImportEmailSubject().equals("YES") && !checkboxHelper.isCheckedByName("impEmailSubject"))
                || (trackorForm.getImportEmailSubject().equals("NO") && checkboxHelper.isCheckedByName("impEmailSubject"))) {
            checkboxHelper.clickByName("impEmailSubject");
        }

        if ((trackorForm.getImportEmailBody().equals("YES") && !checkboxHelper.isCheckedByName("impEmailBody"))
                || (trackorForm.getImportEmailBody().equals("NO") && checkboxHelper.isCheckedByName("impEmailBody"))) {
            checkboxHelper.clickByName("impEmailBody");
        }

        if ((trackorForm.getCreateImport().equals("YES") && !checkboxHelper.isCheckedByName("createImport"))
                || (trackorForm.getCreateImport().equals("NO") && checkboxHelper.isCheckedByName("createImport"))) {
            checkboxHelper.clickByName("createImport");
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(TrackorForm trackorForm) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertHelper.AssertText("trackorFormName", trackorForm.getName());
        assertHelper.AssertRadioPsSelector("reportName", "btnreportName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorForm.getReportName(), 1L, true);
        assertHelper.AssertRadioPsSelector("importName", "btnimportName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorForm.getImportName(), 1L, true);
        assertHelper.AssertRadioPsSelector("userName", "btnuserName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorForm.getUserName(), 1L, true);
        assertHelper.AssertText("impScheduleInterval", trackorForm.getInterval());
        assertHelper.AssertText("description", trackorForm.getDescription());
        assertHelper.AssertCheckBoxNew("impEmailSubject", trackorForm.getImportEmailSubject());
        assertHelper.AssertCheckBoxNew("impEmailBody", trackorForm.getImportEmailBody());
        assertHelper.AssertCheckBoxNew("createImport", trackorForm.getCreateImport());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, TrackorForm trackorForm) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Form Name"), trackorForm.getName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Report Name"), trackorForm.getReportName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Import Name"), trackorForm.getImportName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Description"), trackorForm.getDescription());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
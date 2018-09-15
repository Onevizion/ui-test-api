package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
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
    private Checkbox checkbox;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Js js;

    @Resource
    private Grid grid;

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

        if ((trackorForm.getImportEmailSubject().equals("YES") && !checkbox.isCheckedByName("impEmailSubject"))
                || (trackorForm.getImportEmailSubject().equals("NO") && checkbox.isCheckedByName("impEmailSubject"))) {
            checkbox.clickByName("impEmailSubject");
        }

        if ((trackorForm.getImportEmailBody().equals("YES") && !checkbox.isCheckedByName("impEmailBody"))
                || (trackorForm.getImportEmailBody().equals("NO") && checkbox.isCheckedByName("impEmailBody"))) {
            checkbox.clickByName("impEmailBody");
        }

        if ((trackorForm.getCreateImport().equals("YES") && !checkbox.isCheckedByName("createImport"))
                || (trackorForm.getCreateImport().equals("NO") && checkbox.isCheckedByName("createImport"))) {
            checkbox.clickByName("createImport");
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

        if ((trackorForm.getImportEmailSubject().equals("YES") && !checkbox.isCheckedByName("impEmailSubject"))
                || (trackorForm.getImportEmailSubject().equals("NO") && checkbox.isCheckedByName("impEmailSubject"))) {
            checkbox.clickByName("impEmailSubject");
        }

        if ((trackorForm.getImportEmailBody().equals("YES") && !checkbox.isCheckedByName("impEmailBody"))
                || (trackorForm.getImportEmailBody().equals("NO") && checkbox.isCheckedByName("impEmailBody"))) {
            checkbox.clickByName("impEmailBody");
        }

        if ((trackorForm.getCreateImport().equals("YES") && !checkbox.isCheckedByName("createImport"))
                || (trackorForm.getCreateImport().equals("NO") && checkbox.isCheckedByName("createImport"))) {
            checkbox.clickByName("createImport");
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(TrackorForm trackorForm) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.AssertText("trackorFormName", trackorForm.getName());
        assertElement.AssertRadioPsSelector("reportName", "btnreportName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorForm.getReportName(), 1L, true);
        assertElement.AssertRadioPsSelector("importName", "btnimportName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorForm.getImportName(), 1L, true);
        assertElement.AssertRadioPsSelector("userName", "btnuserName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorForm.getUserName(), 1L, true);
        assertElement.AssertText("impScheduleInterval", trackorForm.getInterval());
        assertElement.AssertText("description", trackorForm.getDescription());
        assertElement.AssertCheckBoxNew("impEmailSubject", trackorForm.getImportEmailSubject());
        assertElement.AssertCheckBoxNew("impEmailBody", trackorForm.getImportEmailBody());
        assertElement.AssertCheckBoxNew("createImport", trackorForm.getCreateImport());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, TrackorForm trackorForm) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Form Name"), trackorForm.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Report Name"), trackorForm.getReportName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Import Name"), trackorForm.getImportName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Description"), trackorForm.getDescription());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
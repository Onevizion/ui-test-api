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

    private static final String NAME = "trackorFormName";
    private static final String INTERVAL = "impScheduleInterval";
    private static final String DESCRIPTION = "description";
    private static final String IMPORT_EMAIL_SUBJECT = "impEmailSubject";
    private static final String IMPORT_EMAIL_BODY = "impEmailBody";
    private static final String CREATE_IMPORT = "createImport";

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

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(trackorForm.getName());

        psSelector.selectSpecificValue(By.name("btnreportName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorForm.getReportName(), 1L);

        psSelector.selectSpecificValue(By.name("btnimportName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorForm.getImportName(), 1L);

        psSelector.selectSpecificValue(By.name("btnuserName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorForm.getUserName(), 1L);

        seleniumSettings.getWebDriver().findElement(By.name(INTERVAL)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(INTERVAL)).sendKeys(trackorForm.getInterval());

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(trackorForm.getDescription());

        if ((trackorForm.getImportEmailSubject().equals("YES") && !checkbox.isCheckedByName(IMPORT_EMAIL_SUBJECT))
                || (trackorForm.getImportEmailSubject().equals("NO") && checkbox.isCheckedByName(IMPORT_EMAIL_SUBJECT))) {
            checkbox.clickByName(IMPORT_EMAIL_SUBJECT);
        }

        if ((trackorForm.getImportEmailBody().equals("YES") && !checkbox.isCheckedByName(IMPORT_EMAIL_BODY))
                || (trackorForm.getImportEmailBody().equals("NO") && checkbox.isCheckedByName(IMPORT_EMAIL_BODY))) {
            checkbox.clickByName(IMPORT_EMAIL_BODY);
        }

        if ((trackorForm.getCreateImport().equals("YES") && !checkbox.isCheckedByName(CREATE_IMPORT))
                || (trackorForm.getCreateImport().equals("NO") && checkbox.isCheckedByName(CREATE_IMPORT))) {
            checkbox.clickByName(CREATE_IMPORT);
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(TrackorForm trackorForm) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(trackorForm.getName());

        psSelector.selectSpecificValue(By.name("btnreportName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorForm.getReportName(), 1L);

        psSelector.selectSpecificValue(By.name("btnimportName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorForm.getImportName(), 1L);

        psSelector.selectSpecificValue(By.name("btnuserName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorForm.getUserName(), 1L);

        seleniumSettings.getWebDriver().findElement(By.name(INTERVAL)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(INTERVAL)).sendKeys(trackorForm.getInterval());

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(trackorForm.getDescription());

        if ((trackorForm.getImportEmailSubject().equals("YES") && !checkbox.isCheckedByName(IMPORT_EMAIL_SUBJECT))
                || (trackorForm.getImportEmailSubject().equals("NO") && checkbox.isCheckedByName(IMPORT_EMAIL_SUBJECT))) {
            checkbox.clickByName(IMPORT_EMAIL_SUBJECT);
        }

        if ((trackorForm.getImportEmailBody().equals("YES") && !checkbox.isCheckedByName(IMPORT_EMAIL_BODY))
                || (trackorForm.getImportEmailBody().equals("NO") && checkbox.isCheckedByName(IMPORT_EMAIL_BODY))) {
            checkbox.clickByName(IMPORT_EMAIL_BODY);
        }

        if ((trackorForm.getCreateImport().equals("YES") && !checkbox.isCheckedByName(CREATE_IMPORT))
                || (trackorForm.getCreateImport().equals("NO") && checkbox.isCheckedByName(CREATE_IMPORT))) {
            checkbox.clickByName(CREATE_IMPORT);
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(TrackorForm trackorForm) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.AssertText(NAME, trackorForm.getName());
        assertElement.AssertRadioPsSelector("reportName", "btnreportName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorForm.getReportName(), 1L, true);
        assertElement.AssertRadioPsSelector("importName", "btnimportName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorForm.getImportName(), 1L, true);
        assertElement.AssertRadioPsSelector("userName", "btnuserName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorForm.getUserName(), 1L, true);
        assertElement.AssertText(INTERVAL, trackorForm.getInterval());
        assertElement.AssertText(DESCRIPTION, trackorForm.getDescription());
        assertElement.AssertCheckBoxNew(IMPORT_EMAIL_SUBJECT, trackorForm.getImportEmailSubject());
        assertElement.AssertCheckBoxNew(IMPORT_EMAIL_BODY, trackorForm.getImportEmailBody());
        assertElement.AssertCheckBoxNew(CREATE_IMPORT, trackorForm.getCreateImport());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, TrackorForm trackorForm) {
        Map<Long, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Form Name"), trackorForm.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Report Name"), trackorForm.getReportName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Import Name"), trackorForm.getImportName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Description"), trackorForm.getDescription());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
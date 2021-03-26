package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Selector;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.form.Form;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.vo.entity.TrackorForm;

@Component
public class EntityTrackorForm {

    private static final String NAME = "trackorFormName";
    private static final String INTERVAL = "impScheduleInterval";
    private static final String DESCRIPTION = "description";
    private static final String IMPORT_EMAIL_SUBJECT = "impEmailSubject";
    private static final String IMPORT_EMAIL_BODY = "impEmailBody";
    private static final String CREATE_IMPORT = "createImport";

    @Autowired
    private Window window;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Selector selector;

    @Autowired
    private Checkbox checkbox;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Js js;

    @Autowired
    private Form form;

    @Autowired
    private Grid grid;

    @Autowired
    private Grid2 grid2;

    public void add(TrackorForm trackorForm) {
        form.openAdd();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(trackorForm.getName());

        selector.selectRadio(By.name("btnreportName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1, trackorForm.getReportName(), 1L);

        selector.selectRadio(By.name("btnimportName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1, trackorForm.getImportName(), 1L);

        selector.selectRadio(By.name("btnuserName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1, trackorForm.getUserName(), 1L);

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
        grid2.waitLoad();
    }

    public void edit(TrackorForm trackorForm) {
        form.openEdit();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(trackorForm.getName());

        selector.selectRadio(By.name("btnreportName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1, trackorForm.getReportName(), 1L);

        selector.selectRadio(By.name("btnimportName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1, trackorForm.getImportName(), 1L);

        selector.selectRadio(By.name("btnuserName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1, trackorForm.getUserName(), 1L);

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
        grid2.waitLoad();
    }

    public void testOnForm(TrackorForm trackorForm) {
        form.openEdit();

        assertElement.assertText(NAME, trackorForm.getName());
        assertElement.assertRadioPsSelector("reportName", "btnreportName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorForm.getReportName(), 1L, true);
        assertElement.assertRadioPsSelector("importName", "btnimportName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorForm.getImportName(), 1L, true);
        assertElement.assertRadioPsSelector("userName", "btnuserName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorForm.getUserName(), 1L, true);
        assertElement.assertText(INTERVAL, trackorForm.getInterval());
        assertElement.assertText(DESCRIPTION, trackorForm.getDescription());
        assertElement.assertCheckbox(IMPORT_EMAIL_SUBJECT, trackorForm.getImportEmailSubject());
        assertElement.assertCheckbox(IMPORT_EMAIL_BODY, trackorForm.getImportEmailBody());
        assertElement.assertCheckbox(CREATE_IMPORT, trackorForm.getCreateImport());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, int rowIndex, TrackorForm trackorForm) {
        Map<Integer, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Form Name"), trackorForm.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Report Name"), trackorForm.getReportName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Import Name"), trackorForm.getImportName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Description"), trackorForm.getDescription());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
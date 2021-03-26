package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.form.Form;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.html.input.file.HtmlInputFile;
import com.onevizion.uitest.api.helper.page.button.PageButton;
import com.onevizion.uitest.api.helper.tab.Tab;
import com.onevizion.uitest.api.vo.entity.Integration;

@Component
public class EntityIntegration {

    private static final String URL = "gitRepoUrl";
    private static final String NAME = "integrationName";
    private static final String VERSION = "version";
    private static final String COMMAND = "command";
    private static final String SETTINGS_FILE = "txtintegrationSettingsFile";
    private static final String LOG_LEVEL = "logLevel";
    private static final String READ_FROM_STDOUT = "readFromStdout";
    private static final String DESCRIPTION = "description";
    private static final String SCHEDULE = "schedule";
    private static final String ENABLED = "enabled";

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Checkbox checkbox;

    @Autowired
    private Form form;

    @Autowired
    private Grid grid;

    @Autowired
    private Js js;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Element element;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private Tab tab;

    @Autowired
    private HtmlInputFile htmlInputFile;

    @Autowired
    private PageButton pageButton;

    public void add(Integration integration) {
        pageButton.openIntegrationAddForm(AbstractSeleniumCore.getGridIdx());

        seleniumSettings.getWebDriver().findElement(By.name(URL)).sendKeys(integration.getUrl());

        element.clickByName(NAME);
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(integration.getName());

        element.clickById(AbstractSeleniumCore.BUTTON_APPLY_ID);
        wait.waitReloadForm("reloaded=1");
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(VERSION)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(VERSION)).sendKeys(integration.getVersion());

        seleniumSettings.getWebDriver().findElement(By.name(COMMAND)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(COMMAND)).sendKeys(integration.getCommand());

        htmlInputFile.uploadOnAdminIntegration(integration.getSettingsFile());

        new Select(seleniumSettings.getWebDriver().findElement(By.name(LOG_LEVEL))).selectByVisibleText(integration.getLogLevel());

        if ((integration.getReadFromStdout().equals("YES") && !checkbox.isCheckedByName(READ_FROM_STDOUT))
                || (integration.getReadFromStdout().equals("NO") && checkbox.isCheckedByName(READ_FROM_STDOUT))) {
            checkbox.clickByName(READ_FROM_STDOUT);
        }

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(integration.getDescription());

        seleniumSettings.getWebDriver().findElement(By.name(SCHEDULE)).sendKeys(integration.getSchedule());

        if ((integration.getEnabled().equals("YES") && !checkbox.isCheckedByName(ENABLED))
                || (integration.getEnabled().equals("NO") && checkbox.isCheckedByName(ENABLED))) {
            checkbox.clickByName(ENABLED);
        }

        tab.goToTab(2); //Components Package
        grid2.waitLoad(2L);
        grid.clearAssignmentGridColumn2(2L, 0L);
        grid.selectAssignmentGridColumn2New(2L, 0, 2, integration.getPackages());

        window.closeModalAndWaitGridLoad(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    public void edit(Integration integration) {
        form.openEdit();

        seleniumSettings.getWebDriver().findElement(By.name(URL)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(URL)).sendKeys(integration.getUrl());

        element.clickByName(NAME);
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(integration.getName());

        seleniumSettings.getWebDriver().findElement(By.name(VERSION)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(VERSION)).sendKeys(integration.getVersion());

        seleniumSettings.getWebDriver().findElement(By.name(COMMAND)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(COMMAND)).sendKeys(integration.getCommand());

        htmlInputFile.uploadOnAdminIntegration(integration.getSettingsFile());

        new Select(seleniumSettings.getWebDriver().findElement(By.name(LOG_LEVEL))).selectByVisibleText(integration.getLogLevel());

        if ((integration.getReadFromStdout().equals("YES") && !checkbox.isCheckedByName(READ_FROM_STDOUT))
                || (integration.getReadFromStdout().equals("NO") && checkbox.isCheckedByName(READ_FROM_STDOUT))) {
            checkbox.clickByName(READ_FROM_STDOUT);
        }

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(integration.getDescription());

        seleniumSettings.getWebDriver().findElement(By.name(SCHEDULE)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(SCHEDULE)).sendKeys(integration.getSchedule());

        if ((integration.getEnabled().equals("YES") && !checkbox.isCheckedByName(ENABLED))
                || (integration.getEnabled().equals("NO") && checkbox.isCheckedByName(ENABLED))) {
            checkbox.clickByName(ENABLED);
        }

        tab.goToTab(2); //Components Package
        grid2.waitLoad(2L);
        grid.clearAssignmentGridColumn2(2L, 0L);
        grid.selectAssignmentGridColumn2New(2L, 0, 2, integration.getPackages());

        window.closeModalAndWaitGridLoad(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    public void testOnForm(Integration integration) {
        form.openEdit();

        assertElement.assertText(URL, integration.getUrl());
        assertElement.assertText(NAME, integration.getName());
        assertElement.assertText(VERSION, integration.getVersion());
        assertElement.assertText(COMMAND, integration.getCommand());
        assertElement.assertText(SETTINGS_FILE, integration.getSettingsFile());
        assertElement.assertSelect(LOG_LEVEL, integration.getLogLevel());
        assertElement.assertCheckbox(READ_FROM_STDOUT, integration.getReadFromStdout());
        assertElement.assertText(DESCRIPTION, integration.getDescription());
        assertElement.assertText(SCHEDULE, integration.getSchedule());
        assertElement.assertCheckbox(ENABLED, integration.getEnabled());

        tab.goToTab(2); //Components Package
        grid2.waitLoad(2L);
        grid.checkAssignmentGridColumn2New(2L, 0, 2, integration.getPackages());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, int rowIndex, Integration integration) {
        Map<Integer, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Repository URL"), integration.getUrl());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Integration Name"), integration.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Version"), integration.getVersion());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Command"), integration.getCommand());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Settings File"), integration.getSettingsFile());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Log Level"), integration.getLogLevel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Read From Stdout"), integration.getReadFromStdout());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Description"), integration.getDescription());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Schedule"), integration.getSchedule());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Enabled"), integration.getEnabled());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
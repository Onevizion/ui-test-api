package com.onevizion.uitest.api.helper.grid.button;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.tab.Tab;
import com.onevizion.uitest.api.helper.tree.Tree;

@Component
public class GridButton {

    private static final String BUTTON_EDIT_ID_BASE = "btnEdit";

    private static final String BUTTON_OPTIONS_ID_BASE = "btnoptionsGroupOpener";
    private static final String BUTTON_OPTIONS_PANEL_ID_BASE = "optionsGrouppopupMenu";

    private static final String BUTTON_APPLETS_ID_BASE = "btneditGroupOpener";
    private static final String BUTTON_APPLETS_PANEL_ID_BASE = "editGrouppopupMenu";

    private static final String BUTTON_APPLET_TASKS_ID_BASE = "itemTask";
    private static final String BUTTON_APPLET_WF_ID_BASE = "itemWF";

    private static final String BUTTON_GRID_ROW_EDITOR_ID_BASE = "itemEditRow";
    private static final String BUTTON_SHOW_SQL_ID_BASE = "itemSQL";
    private static final String BUTTON_RULES_ID_BASE = "itemRules";
    private static final String BUTTON_CLONE_ID_BASE = "itemClone";
    private static final String BUTTON_EXPORT_RUN_ID_BASE = "itemGridExport";
    private static final String BUTTON_EXPORT_HISTORY_ID_BASE = "itemExportHistory";
    private static final String BUTTON_DELETE_TREE_ID_BASE = "itemDeleteTree";
    private static final String BUTTON_DELETE_ID_BASE = "itemDelete";
    private static final String BUTTON_UP_TREE_ID_BASE = "itemUpTree";
    private static final String BUTTON_DOWN_TREE_ID_BASE = "itemDownTree";
    private static final String BUTTON_UP_ID_BASE = "itemUp";
    private static final String BUTTON_DOWN_ID_BASE = "itemDown";
    private static final String BUTTON_CALL_STACK_ID_BASE = "itemCallStack";

    private static final String BUTTON_COMPONENT_EXPORT_ID_BASE = "itemExportRun";
    private static final String BUTTON_COMPONENT_IMPORT_ID_BASE = "itemImportRun";
    private static final String BUTTON_COLORS_ID_BASE = "itemColors";
    private static final String BUTTON_COORDINATES_ID_BASE = "itemCoordLinks";
    private static final String BUTTON_VALIDATIONS_ID_BASE = "itemValidation";
    private static final String BUTTON_FIELD_CASCADING_FIELDS_ID_BASE = "itemFieldsMap";
    private static final String BUTTON_CASCADE_FIELDS_IMPORT_RELATIONS_ID_BASE = "itemVtableLinksValueTree";
    private static final String BUTTON_CASCADE_FIELDS_RELATIONS_ID_BASE = "itemVtableLinksTree";
    private static final String BUTTON_REPORT_GROUPS_ID_BASE = "itemReportGroup";
    private static final String BUTTON_REPORT_TEST_SQL_ID_BASE = "itemTestSql";
    private static final String BUTTON_REPORT_SCAN_PARAMS_ID_BASE = "itemScanParams";
    private static final String BUTTON_REPORT_DETAILS_ID_BASE = "btnDetails";
    private static final String BUTTON_WP_DISCIPLINES_ID_BASE = "itemDiscp";
    private static final String BUTTON_WP_DATE_PAIRS_ID_BASE = "itemDatePairs";
    private static final String BUTTON_WP_CALENDARS_ID_BASE = "itemCalendar";
    private static final String BUTTON_APPLET_REORDER_ID_BASE = "itemReorderApplets";
    private static final String BUTTON_WF_EDITOR_ID_BASE = "itemVisualEditor";
    private static final String BUTTON_DG_DELETE_CONFIG_ID_BASE = "itemDelConfig";
    private static final String BUTTON_LABEL_REPLACE_TEXT_ID_BASE = "itemReplace";
    private static final String BUTTON_INTEGRATION_ADD_ID_BASE = "itemAddIntegration";
    private static final String BUTTON_V_TABLE_REORDER_ID_BASE = "itemReorder";
    private static final String BUTTON_IMPORT_RECOVERY_ID_BASE = "itemRecover";
    private static final String BUTTON_IMPORT_RECOVERY_HISTORY_ID_BASE = "itemRecoveryHistory";
    private static final String BUTTON_IMPORT_INTERRUPT_ID_BASE = "itemImpStop";
    private static final String BUTTON_TF_EMAIL_ACCOUNT_ID_BASE = "itemEmailAccountSettings";
    private static final String BUTTON_TT_TRACKOR_MAIL_ID_BASE = "itemTmSetup";
    private static final String BUTTON_AUDIT_LOG_RECOVERY_ID_BASE = "itemRecover";

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private Window window;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private Wait wait;

    @Autowired
    private Element element;

    @Autowired
    private Tree tree;

    @Autowired
    private Tab tab;

    @Autowired
    private GridButtonWait gridButtonWait;

    public void openAppletForm(Long gridIdx, Long configAppId) {
        openAppletsPanel(gridIdx);

        element.moveToElementById("item" + configAppId);

        elementWait.waitElementById("item" + configAppId);
        elementWait.waitElementVisibleById("item" + configAppId);
        elementWait.waitElementDisplayById("item" + configAppId);
        gridButtonWait.waitButtonEnabled("item" + configAppId);

        window.openModal(By.id("item" + configAppId));
    }

    public void checkAppletNotExist(Long gridIdx, Long configAppId) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = seleniumSettings.getWebDriver().findElements(By.id("item" + configAppId)).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertEquals(count, 0);

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        count = seleniumSettings.getWebDriver().findElements(By.id("btn" + configAppId)).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertEquals(count, 0);
    }

    public void openAppletTasks(Long gridIdx) {
        openAppletsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_APPLET_TASKS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_APPLET_TASKS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_APPLET_TASKS_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_APPLET_TASKS_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_APPLET_TASKS_ID_BASE + gridIdx));
        //wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        //wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        grid2.waitLoad();
        //wait.waitFormLoad();
    }

    public void openAppletWf(Long gridIdx) {
        openAppletsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_APPLET_WF_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_APPLET_WF_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_APPLET_WF_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_APPLET_WF_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_APPLET_WF_ID_BASE + gridIdx));
        wait.waitWebElement(By.id("Close"));
        tab.waitLoad(1);
    }

    public void openGridRowEditorForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_GRID_ROW_EDITOR_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_GRID_ROW_EDITOR_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_GRID_ROW_EDITOR_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_GRID_ROW_EDITOR_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_GRID_ROW_EDITOR_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridRowEditorLoad();
    }

    public void openShowSqlForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_SHOW_SQL_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_SHOW_SQL_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_SHOW_SQL_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_SHOW_SQL_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_SHOW_SQL_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        wait.waitFormLoad();
    }

    public void openRulesForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_RULES_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_RULES_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_RULES_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_RULES_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_RULES_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad(1L);
    }

    public void openCloneForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_CLONE_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_CLONE_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_CLONE_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_CLONE_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_CLONE_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openExportRunForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_EXPORT_RUN_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_EXPORT_RUN_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_EXPORT_RUN_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_EXPORT_RUN_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_EXPORT_RUN_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openExportHistoryGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_EXPORT_HISTORY_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_EXPORT_HISTORY_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_EXPORT_HISTORY_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_EXPORT_HISTORY_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_EXPORT_HISTORY_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        grid2.waitLoad();
    }

    public void clickDeleteTree(Long treeIdx) {
        openOptionsPanel(treeIdx);

        elementWait.waitElementById(BUTTON_DELETE_TREE_ID_BASE + treeIdx);
        elementWait.waitElementVisibleById(BUTTON_DELETE_TREE_ID_BASE + treeIdx);
        elementWait.waitElementDisplayById(BUTTON_DELETE_TREE_ID_BASE + treeIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_DELETE_TREE_ID_BASE + treeIdx);

        element.clickById(BUTTON_DELETE_TREE_ID_BASE + treeIdx);
        wait.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();
        tree.waitLoad(treeIdx);
    }

    public void clickDeleteGrid(Long gridIdx, String message) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_DELETE_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_DELETE_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_DELETE_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_DELETE_ID_BASE + gridIdx);

        element.clickById(BUTTON_DELETE_ID_BASE + gridIdx);
        wait.waitAlert();
        Assert.assertEquals(seleniumSettings.getWebDriver().switchTo().alert().getText(), message, "Alert have wrong message");
        seleniumSettings.getWebDriver().switchTo().alert().accept();
        grid2.waitLoad(gridIdx);
    }

    public void clickUpTree(Long treeIdx) {
        openOptionsPanel(treeIdx);

        elementWait.waitElementById(BUTTON_UP_TREE_ID_BASE + treeIdx);
        elementWait.waitElementVisibleById(BUTTON_UP_TREE_ID_BASE + treeIdx);
        elementWait.waitElementDisplayById(BUTTON_UP_TREE_ID_BASE + treeIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_UP_TREE_ID_BASE + treeIdx);

        element.clickById(BUTTON_UP_TREE_ID_BASE + treeIdx);
        tree.waitLoad(treeIdx);
    }

    public void clickDownTree(Long treeIdx) {
        openOptionsPanel(treeIdx);

        elementWait.waitElementById(BUTTON_DOWN_TREE_ID_BASE + treeIdx);
        elementWait.waitElementVisibleById(BUTTON_DOWN_TREE_ID_BASE + treeIdx);
        elementWait.waitElementDisplayById(BUTTON_DOWN_TREE_ID_BASE + treeIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_DOWN_TREE_ID_BASE + treeIdx);

        element.clickById(BUTTON_DOWN_TREE_ID_BASE + treeIdx);
        tree.waitLoad(treeIdx);
    }

    public void clickUp(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_UP_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_UP_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_UP_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_UP_ID_BASE + gridIdx);

        element.clickById(BUTTON_UP_ID_BASE + gridIdx);
        grid2.waitLoad(gridIdx);
    }

    public void clickDown(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_DOWN_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_DOWN_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_DOWN_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_DOWN_ID_BASE + gridIdx);

        element.clickById(BUTTON_DOWN_ID_BASE + gridIdx);
        grid2.waitLoad(gridIdx);
    }

    public void openCallStackGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_CALL_STACK_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_CALL_STACK_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_CALL_STACK_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_CALL_STACK_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_CALL_STACK_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        grid2.waitLoad();
    }

    public void openComponentExportForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_COMPONENT_EXPORT_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_COMPONENT_EXPORT_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_COMPONENT_EXPORT_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_COMPONENT_EXPORT_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_COMPONENT_EXPORT_ID_BASE + gridIdx));
        wait.waitReloadForm("form=1");
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void clickComponentImport(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_COMPONENT_IMPORT_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_COMPONENT_IMPORT_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_COMPONENT_IMPORT_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_COMPONENT_IMPORT_ID_BASE + gridIdx);

        element.clickById(BUTTON_COMPONENT_IMPORT_ID_BASE + gridIdx);
        grid2.waitLoad(gridIdx);
    }

    public void openColorsGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_COLORS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_COLORS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_COLORS_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_COLORS_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_COLORS_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    public void openCoordinatesGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_COORDINATES_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_COORDINATES_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_COORDINATES_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_COORDINATES_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_COORDINATES_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    public void openValidationsGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_VALIDATIONS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_VALIDATIONS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_VALIDATIONS_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_VALIDATIONS_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_VALIDATIONS_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    public void openFieldCascadingFieldsForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_FIELD_CASCADING_FIELDS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_FIELD_CASCADING_FIELDS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_FIELD_CASCADING_FIELDS_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_FIELD_CASCADING_FIELDS_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_FIELD_CASCADING_FIELDS_ID_BASE + gridIdx));
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
    }

    public void openCascadeFieldsImportRelationsForm(Long treeIdx) {
        openOptionsPanel(treeIdx);

        elementWait.waitElementById(BUTTON_CASCADE_FIELDS_IMPORT_RELATIONS_ID_BASE + treeIdx);
        elementWait.waitElementVisibleById(BUTTON_CASCADE_FIELDS_IMPORT_RELATIONS_ID_BASE + treeIdx);
        elementWait.waitElementDisplayById(BUTTON_CASCADE_FIELDS_IMPORT_RELATIONS_ID_BASE + treeIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_CASCADE_FIELDS_IMPORT_RELATIONS_ID_BASE + treeIdx);

        window.openModal(By.id(BUTTON_CASCADE_FIELDS_IMPORT_RELATIONS_ID_BASE + treeIdx));
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        wait.waitFormLoad();
    }

    public void openCascadeFieldsRelationsForm(Long treeIdx) {
        openOptionsPanel(treeIdx);

        elementWait.waitElementById(BUTTON_CASCADE_FIELDS_RELATIONS_ID_BASE + treeIdx);
        elementWait.waitElementVisibleById(BUTTON_CASCADE_FIELDS_RELATIONS_ID_BASE + treeIdx);
        elementWait.waitElementDisplayById(BUTTON_CASCADE_FIELDS_RELATIONS_ID_BASE + treeIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_CASCADE_FIELDS_RELATIONS_ID_BASE + treeIdx);

        window.openModal(By.id(BUTTON_CASCADE_FIELDS_RELATIONS_ID_BASE + treeIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openReportGroupsGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_REPORT_GROUPS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_REPORT_GROUPS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_REPORT_GROUPS_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_REPORT_GROUPS_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_REPORT_GROUPS_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    public void openReportTestSqlForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_REPORT_TEST_SQL_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_REPORT_TEST_SQL_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_REPORT_TEST_SQL_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_REPORT_TEST_SQL_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_REPORT_TEST_SQL_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        wait.waitFormLoad();
        wait.waitCodeMirrorLoad("SQL");
        elementWait.waitElementNotVisibleById("lblSuccess");
        elementWait.waitElementNotDisplayById("lblSuccess");
        elementWait.waitElementNotVisibleById("lblFailure");
        elementWait.waitElementNotDisplayById("lblFailure");
    }

    public void clickReportScanParams(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_REPORT_SCAN_PARAMS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_REPORT_SCAN_PARAMS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_REPORT_SCAN_PARAMS_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_REPORT_SCAN_PARAMS_ID_BASE + gridIdx);

        element.clickById(BUTTON_REPORT_SCAN_PARAMS_ID_BASE + gridIdx);
        grid2.waitLoad(gridIdx);
    }

    public void openReportDetailsForm(Long gridIdx) {
        window.openModal(By.id(BUTTON_REPORT_DETAILS_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openWpDisciplinesGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_WP_DISCIPLINES_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_WP_DISCIPLINES_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_WP_DISCIPLINES_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_WP_DISCIPLINES_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_WP_DISCIPLINES_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    public void openWpDatePairsGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_WP_DATE_PAIRS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_WP_DATE_PAIRS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_WP_DATE_PAIRS_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_WP_DATE_PAIRS_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_WP_DATE_PAIRS_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    public void openWpCalendarsGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_WP_CALENDARS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_WP_CALENDARS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_WP_CALENDARS_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_WP_CALENDARS_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_WP_CALENDARS_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    public void openAppletReorderForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_APPLET_REORDER_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_APPLET_REORDER_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_APPLET_REORDER_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_APPLET_REORDER_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_APPLET_REORDER_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openWfEditorForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_WF_EDITOR_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_WF_EDITOR_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_WF_EDITOR_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_WF_EDITOR_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_WF_EDITOR_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        wait.waitFormLoad();
    }

    public void openDgDeleteConfigForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_DG_DELETE_CONFIG_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_DG_DELETE_CONFIG_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_DG_DELETE_CONFIG_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_DG_DELETE_CONFIG_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_DG_DELETE_CONFIG_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        grid2.waitLoad(1L);
    }

    public void openLabelReplaceTextForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_LABEL_REPLACE_TEXT_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_LABEL_REPLACE_TEXT_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_LABEL_REPLACE_TEXT_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_LABEL_REPLACE_TEXT_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_LABEL_REPLACE_TEXT_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openIntegrationAddForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_INTEGRATION_ADD_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_INTEGRATION_ADD_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_INTEGRATION_ADD_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_INTEGRATION_ADD_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_INTEGRATION_ADD_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void clickVtableReorder(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_V_TABLE_REORDER_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_V_TABLE_REORDER_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_V_TABLE_REORDER_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_V_TABLE_REORDER_ID_BASE + gridIdx);

        element.clickById(BUTTON_V_TABLE_REORDER_ID_BASE + gridIdx);
        wait.waitAlert();
        Assert.assertEquals(seleniumSettings.getWebDriver().switchTo().alert().getText(), "Are you sure you want to reorder the entries based on alphabetically sorting the values?");
        seleniumSettings.getWebDriver().switchTo().alert().accept();
        grid2.waitLoad(gridIdx);
    }

    public void clickImportRecover(Long gridIdx, String processId) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_IMPORT_RECOVERY_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_IMPORT_RECOVERY_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_IMPORT_RECOVERY_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_IMPORT_RECOVERY_ID_BASE + gridIdx);

        element.clickById(BUTTON_IMPORT_RECOVERY_ID_BASE + gridIdx);
        wait.waitAlert();
        Assert.assertEquals(seleniumSettings.getWebDriver().switchTo().alert().getText(), "Are you sure you want to recover selected import (Process ID = " + processId + ")?", "Alert have wrong message");
        seleniumSettings.getWebDriver().switchTo().alert().accept();
    }

    public void openImportRecoveryHistoryForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_IMPORT_RECOVERY_HISTORY_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_IMPORT_RECOVERY_HISTORY_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_IMPORT_RECOVERY_HISTORY_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_IMPORT_RECOVERY_HISTORY_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_IMPORT_RECOVERY_HISTORY_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        grid2.waitLoad();
    }

    public void clickImportInterrupt(Long gridIdx, String processId) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_IMPORT_INTERRUPT_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_IMPORT_INTERRUPT_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_IMPORT_INTERRUPT_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_IMPORT_INTERRUPT_ID_BASE + gridIdx);

        element.clickById(BUTTON_IMPORT_INTERRUPT_ID_BASE + gridIdx);
        wait.waitAlert();
        Assert.assertEquals(seleniumSettings.getWebDriver().switchTo().alert().getText(), "Are you sure you want to interrupt selected import (Process ID = " + processId + ")?", "Alert have wrong message");
        seleniumSettings.getWebDriver().switchTo().alert().accept();
    }

    public void openTfEmailAccountForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_TF_EMAIL_ACCOUNT_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_TF_EMAIL_ACCOUNT_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_TF_EMAIL_ACCOUNT_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_TF_EMAIL_ACCOUNT_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_TF_EMAIL_ACCOUNT_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openTtTrackorMailForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_TT_TRACKOR_MAIL_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_TT_TRACKOR_MAIL_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_TT_TRACKOR_MAIL_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_TT_TRACKOR_MAIL_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_TT_TRACKOR_MAIL_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openAuditLogRecoverForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_AUDIT_LOG_RECOVERY_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_AUDIT_LOG_RECOVERY_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_AUDIT_LOG_RECOVERY_ID_BASE + gridIdx);
        gridButtonWait.waitButtonEnabled(BUTTON_AUDIT_LOG_RECOVERY_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_AUDIT_LOG_RECOVERY_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void waitButtonEditDisabled(Long gridIdx) {
        gridButtonWait.waitButtonDisabled(BUTTON_EDIT_ID_BASE + gridIdx);
    }

    public void waitButtonEditEnabled(Long gridIdx) {
        gridButtonWait.waitButtonEnabled(BUTTON_EDIT_ID_BASE + gridIdx);
    }

    private void openOptionsPanel(Long gridIdx) {
        elementWait.waitElementById(BUTTON_OPTIONS_ID_BASE + gridIdx);
        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_OPTIONS_ID_BASE + gridIdx)).click();

        elementWait.waitElementById(BUTTON_OPTIONS_PANEL_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_OPTIONS_PANEL_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_OPTIONS_PANEL_ID_BASE + gridIdx);
    }

    private void openAppletsPanel(Long gridIdx) {
        elementWait.waitElementById(BUTTON_APPLETS_ID_BASE + gridIdx);
        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_APPLETS_ID_BASE + gridIdx)).click();

        elementWait.waitElementById(BUTTON_APPLETS_PANEL_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_APPLETS_PANEL_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_APPLETS_PANEL_ID_BASE + gridIdx);
    }

}
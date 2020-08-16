package com.onevizion.uitest.api.helper.grid.button;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.tree.Tree;

@Component
public class GridButton {

    private static final String BUTTON_OPTIONS_ID_BASE = "btnoptionsGroupOpener";
    private static final String BUTTON_OPTIONS_PANEL_ID_BASE = "optionsGrouppopupMenu";

    private static final String BUTTON_APPLETS_ID_BASE = "btneditGroupOpener";
    private static final String BUTTON_APPLETS_PANEL_ID_BASE = "editGrouppopupMenu";

    private static final String BUTTON_APPLET_TASKS_ID_BASE = "itemTask";

    private static final String BUTTON_GRID_ROW_EDITOR_ID_BASE = "itemEditRow";
    private static final String BUTTON_SHOW_SQL_ID_BASE = "itemSQL";
    private static final String BUTTON_RULES_ID_BASE = "itemRules";
    private static final String BUTTON_CLONE_ID_BASE = "itemClone";
    private static final String BUTTON_EXPORT_RUN_ID_BASE = "itemGridExport";
    private static final String BUTTON_EXPORT_HISTORY_ID_BASE = "itemExportHistory";
    private static final String BUTTON_DELETE_TREE_ID_BASE = "itemDeleteTree";

    private static final String BUTTON_COMPONENT_EXPORT_ID_BASE = "itemExportRun";
    private static final String BUTTON_COMPONENT_IMPORT_ID_BASE = "itemImportRun";
    private static final String BUTTON_COLORS_ID_BASE = "itemColors";
    private static final String BUTTON_COORDINATES_ID_BASE = "itemCoordLinks";
    private static final String BUTTON_VALIDATIONS_ID_BASE = "itemValidation";
    private static final String BUTTON_REPORT_GROUPS_ID_BASE = "itemReportGroup";
    private static final String BUTTON_WP_DISCIPLINES_ID_BASE = "itemDiscp";
    private static final String BUTTON_WP_DATE_PAIRS_ID_BASE = "itemDatePairs";
    private static final String BUTTON_WP_CALENDARS_ID_BASE = "itemCalendar";
    private static final String BUTTON_APPLET_REORDER_ID_BASE = "itemReorder";
    private static final String BUTTON_WF_EDITOR_ID_BASE = "itemVisualEditor";
    private static final String BUTTON_DG_DELETE_CONFIG_ID_BASE = "itemDelConfig";

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

    public void openAppletForm(Long gridIdx, Long configAppId) {
        openAppletsPanel(gridIdx);

        element.moveToElementById("item" + configAppId);

        elementWait.waitElementById("item" + configAppId);
        elementWait.waitElementVisibleById("item" + configAppId);
        elementWait.waitElementDisplayById("item" + configAppId);

        window.openModal(By.id("item" + configAppId));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    public void openAppletTasks(Long gridIdx) {
        openAppletsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_APPLET_TASKS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_APPLET_TASKS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_APPLET_TASKS_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_APPLET_TASKS_ID_BASE + gridIdx));
        //wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        //wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        grid2.waitLoad();
        //wait.waitFormLoad();
    }

    public void openGridRowEditorForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_GRID_ROW_EDITOR_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_GRID_ROW_EDITOR_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_GRID_ROW_EDITOR_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_GRID_ROW_EDITOR_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridRowEditorLoad();
    }

    public void openShowSqlForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_SHOW_SQL_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_SHOW_SQL_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_SHOW_SQL_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_SHOW_SQL_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        wait.waitFormLoad();
    }

    public void openRulesForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_RULES_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_RULES_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_RULES_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_RULES_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad(1L);
    }

    public void openCloneForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_CLONE_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_CLONE_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_CLONE_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_CLONE_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openExportRunForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_EXPORT_RUN_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_EXPORT_RUN_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_EXPORT_RUN_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_EXPORT_RUN_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openExportHistoryGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_EXPORT_HISTORY_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_EXPORT_HISTORY_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_EXPORT_HISTORY_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_EXPORT_HISTORY_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        grid2.waitLoad();
    }

    public void clickDeleteTree(Long treeIdx) {
        openOptionsPanel(treeIdx);

        elementWait.waitElementById(BUTTON_DELETE_TREE_ID_BASE + treeIdx);
        elementWait.waitElementVisibleById(BUTTON_DELETE_TREE_ID_BASE + treeIdx);
        elementWait.waitElementDisplayById(BUTTON_DELETE_TREE_ID_BASE + treeIdx);

        element.clickById(BUTTON_DELETE_TREE_ID_BASE + treeIdx);
        wait.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();
        tree.waitLoad(treeIdx);
    }

    public void openComponentExportForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_COMPONENT_EXPORT_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_COMPONENT_EXPORT_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_COMPONENT_EXPORT_ID_BASE + gridIdx);

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

        element.clickById(BUTTON_COMPONENT_IMPORT_ID_BASE + gridIdx);
        grid2.waitLoad(gridIdx);
    }

    public void openColorsGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_COLORS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_COLORS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_COLORS_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_COLORS_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    public void openCoordinatesGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_COORDINATES_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_COORDINATES_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_COORDINATES_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_COORDINATES_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    public void openValidationsGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_VALIDATIONS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_VALIDATIONS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_VALIDATIONS_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_VALIDATIONS_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    public void openReportGroupsGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_REPORT_GROUPS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_REPORT_GROUPS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_REPORT_GROUPS_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_REPORT_GROUPS_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    public void openWpDisciplinesGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_WP_DISCIPLINES_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_WP_DISCIPLINES_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_WP_DISCIPLINES_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_WP_DISCIPLINES_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    public void openWpDatePairsGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_WP_DATE_PAIRS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_WP_DATE_PAIRS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_WP_DATE_PAIRS_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_WP_DATE_PAIRS_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    public void openWpCalendarsGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_WP_CALENDARS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_WP_CALENDARS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_WP_CALENDARS_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_WP_CALENDARS_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    public void openAppletReorderForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_APPLET_REORDER_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_APPLET_REORDER_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_APPLET_REORDER_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_APPLET_REORDER_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openWfEditorForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_WF_EDITOR_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_WF_EDITOR_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_WF_EDITOR_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_WF_EDITOR_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        wait.waitFormLoad();
    }

    public void openDgDeleteConfigForm(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_DG_DELETE_CONFIG_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_DG_DELETE_CONFIG_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_DG_DELETE_CONFIG_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_DG_DELETE_CONFIG_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        grid2.waitLoad(1L);
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
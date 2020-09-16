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
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.vo.entity.SecurityRole;

@Component
public class EntitySecurityRole {

    private static final String NAME = "roleType";
    private static final String DESCRIPTION = "description";

    private static final String DEF_PRIV_APPLICATION_MIGRATION_R = "cbPrivR30";
    private static final String DEF_PRIV_APPLICATION_MIGRATION_E = "cbPrivE30";
    private static final String DEF_PRIV_APPLICATION_MIGRATION_A = "cbPrivA30";
    private static final String DEF_PRIV_APPLICATION_MIGRATION_D = "cbPrivD30";
    private static final String DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_R = "cbPrivR40";
    private static final String DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_E = "cbPrivE40";
    private static final String DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_A = "cbPrivA40";
    private static final String DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_D = "cbPrivD40";
    private static final String DEF_PRIV_AUDIT_LOG_R = "cbPrivR50";
    private static final String DEF_PRIV_AUDIT_LOG_E = "cbPrivE50";
    private static final String DEF_PRIV_AUDIT_LOG_D = "cbPrivD50";
    private static final String DEF_PRIV_BUILD_APPLICATION_R = "cbPrivR60";
    private static final String DEF_PRIV_BUILD_APPLICATION_E = "cbPrivE60";
    private static final String DEF_PRIV_BUILD_APPLICATION_A = "cbPrivA60";
    private static final String DEF_PRIV_BUILD_APPLICATION_D = "cbPrivD60";
    private static final String DEF_PRIV_CHAT_COMMENT_R = "cbPrivR70";
    private static final String DEF_PRIV_CHAT_COMMENT_E = "cbPrivE70";
    private static final String DEF_PRIV_CHAT_COMMENT_A = "cbPrivA70";
    private static final String DEF_PRIV_CHAT_COMMENT_D = "cbPrivD70";
    private static final String DEF_PRIV_CELL_COLOR_E = "cbPrivE9";
    private static final String DEF_PRIV_APPLET_R = "cbPrivR2";
    private static final String DEF_PRIV_APPLET_E = "cbPrivE2";
    private static final String DEF_PRIV_TAB_R = "cbPrivR3";
    private static final String DEF_PRIV_TAB_E = "cbPrivE3";
    private static final String DEF_PRIV_DASHBOARD_R = "cbPrivR24";
    private static final String DEF_PRIV_DASHBOARD_E = "cbPrivE24";
    private static final String DEF_PRIV_DATA_INGESTION_R = "cbPrivR80";
    private static final String DEF_PRIV_DATA_INGESTION_E = "cbPrivE80";
    private static final String DEF_PRIV_DATA_INGESTION_A = "cbPrivA80";
    private static final String DEF_PRIV_DATA_INGESTION_D = "cbPrivD80";
    private static final String DEF_PRIV_DATA_VIEW_R = "cbPrivR90";
    private static final String DEF_PRIV_DATA_VIEW_E = "cbPrivE90";
    private static final String DEF_PRIV_DATA_VIEW_A = "cbPrivA90";
    private static final String DEF_PRIV_DATA_VIEW_D = "cbPrivD90";
    private static final String DEF_PRIV_DESIGN_APPLICATION_R = "cbPrivR100";
    private static final String DEF_PRIV_DESIGN_APPLICATION_E = "cbPrivE100";
    private static final String DEF_PRIV_DESIGN_APPLICATION_A = "cbPrivA100";
    private static final String DEF_PRIV_DESIGN_APPLICATION_D = "cbPrivD100";
    private static final String DEF_PRIV_DOCUMENTATION_R = "cbPrivR110";
    private static final String DEF_PRIV_DOCUMENTATION_E = "cbPrivE110";
    private static final String DEF_PRIV_DOCUMENTATION_A = "cbPrivA110";
    private static final String DEF_PRIV_DOCUMENTATION_D = "cbPrivD110";
    private static final String DEF_PRIV_DOCUMENTS_R = "cbPrivR120";
    private static final String DEF_PRIV_DOCUMENTS_E = "cbPrivE120";
    private static final String DEF_PRIV_DOCUMENTS_A = "cbPrivA120";
    private static final String DEF_PRIV_DOCUMENTS_D = "cbPrivD120";
    private static final String DEF_PRIV_FEATURE_VISIBILITY_R = "cbPrivR130";
    private static final String DEF_PRIV_INTEGRATION_HUB_R = "cbPrivR140";
    private static final String DEF_PRIV_INTEGRATION_HUB_E = "cbPrivE140";
    private static final String DEF_PRIV_INTEGRATION_HUB_A = "cbPrivA140";
    private static final String DEF_PRIV_INTEGRATION_HUB_D = "cbPrivD140";
    private static final String DEF_PRIV_LOCALIZATION_R = "cbPrivR150";
    private static final String DEF_PRIV_LOCALIZATION_E = "cbPrivE150";
    private static final String DEF_PRIV_LOCALIZATION_A = "cbPrivA150";
    private static final String DEF_PRIV_LOCALIZATION_D = "cbPrivD150";
    private static final String DEF_PRIV_RELATION_R = "cbPrivR12";
    private static final String DEF_PRIV_RELATION_E = "cbPrivE12";
    private static final String DEF_PRIV_RELATION_A = "cbPrivA12";
    private static final String DEF_PRIV_RELATION_D = "cbPrivD12";
    private static final String DEF_PRIV_SYSTEM_ADMINISTRATION_R = "cbPrivR160";
    private static final String DEF_PRIV_SYSTEM_ADMINISTRATION_E = "cbPrivE160";
    private static final String DEF_PRIV_SYSTEM_ADMINISTRATION_A = "cbPrivA160";
    private static final String DEF_PRIV_SYSTEM_ADMINISTRATION_D = "cbPrivD160";
    private static final String DEF_PRIV_TRACKOR_TYPE_R = "cbPrivR15";
    private static final String DEF_PRIV_TRACKOR_TYPE_E = "cbPrivE15";
    private static final String DEF_PRIV_TRACKOR_TYPE_A = "cbPrivA15";
    private static final String DEF_PRIV_TRACKOR_TYPE_D = "cbPrivD15";
    private static final String DEF_PRIV_USER_SECURITY_R = "cbPrivR170";
    private static final String DEF_PRIV_USER_SECURITY_E = "cbPrivE170";
    private static final String DEF_PRIV_USER_SECURITY_A = "cbPrivA170";
    private static final String DEF_PRIV_USER_SECURITY_D = "cbPrivD170";
    private static final String DEF_PRIV_WP_TASK_FIELD_R = "cbPrivR23";
    private static final String DEF_PRIV_WP_TASK_FIELD_E = "cbPrivE23";
    private static final String DEF_PRIV_WP_TASK_FIELD_A = "cbPrivA23";
    private static final String DEF_PRIV_WP_TASK_FIELD_D = "cbPrivD23";
    private static final String DEF_PRIV_WORKFLOW_TEMPLATE_R = "cbPrivR10";
    private static final String DEF_PRIV_WORKFLOW_TEMPLATE_E = "cbPrivE10";
    private static final String DEF_PRIV_WORKFLOW_TEMPLATE_A = "cbPrivA10";
    private static final String DEF_PRIV_WORKFLOW_MANAGEMENT_R = "cbPrivR180";
    private static final String DEF_PRIV_WORKFLOW_MANAGEMENT_E = "cbPrivE180";
    private static final String DEF_PRIV_WORKFLOW_MANAGEMENT_A = "cbPrivA180";
    private static final String DEF_PRIV_WORKFLOW_MANAGEMENT_D = "cbPrivD180";
    private static final String DEF_PRIV_WORKPLAN_MANAGEMENT_R = "cbPrivR190";
    private static final String DEF_PRIV_WORKPLAN_MANAGEMENT_E = "cbPrivE190";
    private static final String DEF_PRIV_WORKPLAN_MANAGEMENT_A = "cbPrivA190";
    private static final String DEF_PRIV_WORKPLAN_MANAGEMENT_D = "cbPrivD190";
    private static final String DEF_PRIV_WORKPLAN_TASKS_R = "cbPrivR200";
    private static final String DEF_PRIV_WORKPLAN_TASKS_E = "cbPrivE200";
    private static final String DEF_PRIV_WORKPLAN_TASKS_A = "cbPrivA200";
    private static final String DEF_PRIV_WORKPLAN_TASKS_D = "cbPrivD200";

    private static final String DEF_LOCK_PRIV_FIELD_L = "cbLock5";
    private static final String DEF_LOCK_PRIV_FIELD_U = "cbUnlock5";
    private static final String DEF_LOCK_PRIV_RELATION_L = "cbLock6";
    private static final String DEF_LOCK_PRIV_RELATION_U = "cbUnlock6";

    private static final String DEF_ASSIGN_DISCIPLINE = "autoDiscpAssign";
    private static final String DEF_ASSIGN_MENU = "autoAppAssign";
    private static final String DEF_ASSIGN_GLOBAL_VIEW = "autoViewAssign";
    private static final String DEF_ASSIGN_GLOBAL_FILTER = "autoFilterAssign";
    private static final String DEF_ASSIGN_GLOBAL_PORTAL = "autoPortalAssign";
    private static final String DEF_ASSIGN_RULE = "autoRuleAssign";
    private static final String DEF_ASSIGN_IMPORT = "autoImportAssign";
    private static final String DEF_ASSIGN_REPORT = "autoReportAssign";
    private static final String DEF_ASSIGN_GLOBAL_NOTIF = "autoNotifAssign";
    private static final String DEF_ASSIGN_CHAT_NOTIF = "autoChatAssign";
    private static final String DEF_ASSIGN_TRACKOR_TOUR = "autoTourAssign";

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private Checkbox checkbox;

    public void add(SecurityRole securityRole) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(securityRole.getName());

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(securityRole.getDescription());

        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationMigrationR(), DEF_PRIV_APPLICATION_MIGRATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationMigrationE(), DEF_PRIV_APPLICATION_MIGRATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationMigrationA(), DEF_PRIV_APPLICATION_MIGRATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationMigrationD(), DEF_PRIV_APPLICATION_MIGRATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationProcessManagementR(), DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationProcessManagementE(), DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationProcessManagementA(), DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationProcessManagementD(), DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getAuditLogR(), DEF_PRIV_AUDIT_LOG_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getAuditLogE(), DEF_PRIV_AUDIT_LOG_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getAuditLogD(), DEF_PRIV_AUDIT_LOG_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getBuildApplicationR(), DEF_PRIV_BUILD_APPLICATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getBuildApplicationE(), DEF_PRIV_BUILD_APPLICATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getBuildApplicationA(), DEF_PRIV_BUILD_APPLICATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getBuildApplicationD(), DEF_PRIV_BUILD_APPLICATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getChatCommentR(), DEF_PRIV_CHAT_COMMENT_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getChatCommentE(), DEF_PRIV_CHAT_COMMENT_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getChatCommentA(), DEF_PRIV_CHAT_COMMENT_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getChatCommentD(), DEF_PRIV_CHAT_COMMENT_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getCellColorE(), DEF_PRIV_CELL_COLOR_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletR(), DEF_PRIV_APPLET_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletE(), DEF_PRIV_APPLET_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getTabR(), DEF_PRIV_TAB_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getTabE(), DEF_PRIV_TAB_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getDashboardR(), DEF_PRIV_DASHBOARD_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getDashboardE(), DEF_PRIV_DASHBOARD_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataIngestionR(), DEF_PRIV_DATA_INGESTION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataIngestionE(), DEF_PRIV_DATA_INGESTION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataIngestionA(), DEF_PRIV_DATA_INGESTION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataIngestionD(), DEF_PRIV_DATA_INGESTION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataViewR(), DEF_PRIV_DATA_VIEW_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataViewE(), DEF_PRIV_DATA_VIEW_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataViewA(), DEF_PRIV_DATA_VIEW_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataViewD(), DEF_PRIV_DATA_VIEW_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getDesignApplicationR(), DEF_PRIV_DESIGN_APPLICATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getDesignApplicationE(), DEF_PRIV_DESIGN_APPLICATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getDesignApplicationA(), DEF_PRIV_DESIGN_APPLICATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getDesignApplicationD(), DEF_PRIV_DESIGN_APPLICATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentationR(), DEF_PRIV_DOCUMENTATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentationE(), DEF_PRIV_DOCUMENTATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentationA(), DEF_PRIV_DOCUMENTATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentationD(), DEF_PRIV_DOCUMENTATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentsR(), DEF_PRIV_DOCUMENTS_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentsE(), DEF_PRIV_DOCUMENTS_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentsA(), DEF_PRIV_DOCUMENTS_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentsD(), DEF_PRIV_DOCUMENTS_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getFeatureVisibilityR(), DEF_PRIV_FEATURE_VISIBILITY_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getIntegrationHubR(), DEF_PRIV_INTEGRATION_HUB_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getIntegrationHubE(), DEF_PRIV_INTEGRATION_HUB_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getIntegrationHubA(), DEF_PRIV_INTEGRATION_HUB_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getIntegrationHubD(), DEF_PRIV_INTEGRATION_HUB_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getLocalizationR(), DEF_PRIV_LOCALIZATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getLocalizationE(), DEF_PRIV_LOCALIZATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getLocalizationA(), DEF_PRIV_LOCALIZATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getLocalizationD(), DEF_PRIV_LOCALIZATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationR(), DEF_PRIV_RELATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationE(), DEF_PRIV_RELATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationA(), DEF_PRIV_RELATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationD(), DEF_PRIV_RELATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getSystemAdministrationR(), DEF_PRIV_SYSTEM_ADMINISTRATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getSystemAdministrationE(), DEF_PRIV_SYSTEM_ADMINISTRATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getSystemAdministrationA(), DEF_PRIV_SYSTEM_ADMINISTRATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getSystemAdministrationD(), DEF_PRIV_SYSTEM_ADMINISTRATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeR(), DEF_PRIV_TRACKOR_TYPE_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeE(), DEF_PRIV_TRACKOR_TYPE_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeA(), DEF_PRIV_TRACKOR_TYPE_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeD(), DEF_PRIV_TRACKOR_TYPE_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getUserSecurityR(), DEF_PRIV_USER_SECURITY_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getUserSecurityE(), DEF_PRIV_USER_SECURITY_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getUserSecurityA(), DEF_PRIV_USER_SECURITY_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getUserSecurityD(), DEF_PRIV_USER_SECURITY_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getWpTaskFieldR(), DEF_PRIV_WP_TASK_FIELD_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getWpTaskFieldE(), DEF_PRIV_WP_TASK_FIELD_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getWpTaskFieldA(), DEF_PRIV_WP_TASK_FIELD_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getWpTaskFieldD(), DEF_PRIV_WP_TASK_FIELD_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkFlowTemplateR(), DEF_PRIV_WORKFLOW_TEMPLATE_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkFlowTemplateE(), DEF_PRIV_WORKFLOW_TEMPLATE_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkFlowTemplateA(), DEF_PRIV_WORKFLOW_TEMPLATE_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowManagementR(), DEF_PRIV_WORKFLOW_MANAGEMENT_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowManagementE(), DEF_PRIV_WORKFLOW_MANAGEMENT_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowManagementA(), DEF_PRIV_WORKFLOW_MANAGEMENT_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowManagementD(), DEF_PRIV_WORKFLOW_MANAGEMENT_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanManagementR(), DEF_PRIV_WORKPLAN_MANAGEMENT_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanManagementE(), DEF_PRIV_WORKPLAN_MANAGEMENT_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanManagementA(), DEF_PRIV_WORKPLAN_MANAGEMENT_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanManagementD(), DEF_PRIV_WORKPLAN_MANAGEMENT_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanTasksR(), DEF_PRIV_WORKPLAN_TASKS_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanTasksE(), DEF_PRIV_WORKPLAN_TASKS_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanTasksA(), DEF_PRIV_WORKPLAN_TASKS_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanTasksD(), DEF_PRIV_WORKPLAN_TASKS_D);

        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableFieldL(), DEF_LOCK_PRIV_FIELD_L);
        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableFieldU(), DEF_LOCK_PRIV_FIELD_U);
        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableRelationL(), DEF_LOCK_PRIV_RELATION_L);
        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableRelationU(), DEF_LOCK_PRIV_RELATION_U);

        setCheckboxValue(securityRole.getDefaultAssignments().getDiscipline(), DEF_ASSIGN_DISCIPLINE);
        setCheckboxValue(securityRole.getDefaultAssignments().getMenuAppliction(), DEF_ASSIGN_MENU);
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalView(), DEF_ASSIGN_GLOBAL_VIEW);
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalFilter(), DEF_ASSIGN_GLOBAL_FILTER);
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalPortal(), DEF_ASSIGN_GLOBAL_PORTAL);
        setCheckboxValue(securityRole.getDefaultAssignments().getRule(), DEF_ASSIGN_RULE);
        setCheckboxValue(securityRole.getDefaultAssignments().getImportt(), DEF_ASSIGN_IMPORT);
        setCheckboxValue(securityRole.getDefaultAssignments().getReport(), DEF_ASSIGN_REPORT);
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalNotif(), DEF_ASSIGN_GLOBAL_NOTIF);
        setCheckboxValue(securityRole.getDefaultAssignments().getChatNotif(), DEF_ASSIGN_CHAT_NOTIF);
        setCheckboxValue(securityRole.getDefaultAssignments().getTrackorTour(), DEF_ASSIGN_TRACKOR_TOUR);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(SecurityRole securityRole) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(securityRole.getName());

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(securityRole.getDescription());

        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationMigrationR(), DEF_PRIV_APPLICATION_MIGRATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationMigrationE(), DEF_PRIV_APPLICATION_MIGRATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationMigrationA(), DEF_PRIV_APPLICATION_MIGRATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationMigrationD(), DEF_PRIV_APPLICATION_MIGRATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationProcessManagementR(), DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationProcessManagementE(), DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationProcessManagementA(), DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getApplicationProcessManagementD(), DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getAuditLogR(), DEF_PRIV_AUDIT_LOG_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getAuditLogE(), DEF_PRIV_AUDIT_LOG_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getAuditLogD(), DEF_PRIV_AUDIT_LOG_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getBuildApplicationR(), DEF_PRIV_BUILD_APPLICATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getBuildApplicationE(), DEF_PRIV_BUILD_APPLICATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getBuildApplicationA(), DEF_PRIV_BUILD_APPLICATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getBuildApplicationD(), DEF_PRIV_BUILD_APPLICATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getChatCommentR(), DEF_PRIV_CHAT_COMMENT_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getChatCommentE(), DEF_PRIV_CHAT_COMMENT_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getChatCommentA(), DEF_PRIV_CHAT_COMMENT_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getChatCommentD(), DEF_PRIV_CHAT_COMMENT_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getCellColorE(), DEF_PRIV_CELL_COLOR_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletR(), DEF_PRIV_APPLET_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletE(), DEF_PRIV_APPLET_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getTabR(), DEF_PRIV_TAB_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getTabE(), DEF_PRIV_TAB_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getDashboardR(), DEF_PRIV_DASHBOARD_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getDashboardE(), DEF_PRIV_DASHBOARD_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataIngestionR(), DEF_PRIV_DATA_INGESTION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataIngestionE(), DEF_PRIV_DATA_INGESTION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataIngestionA(), DEF_PRIV_DATA_INGESTION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataIngestionD(), DEF_PRIV_DATA_INGESTION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataViewR(), DEF_PRIV_DATA_VIEW_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataViewE(), DEF_PRIV_DATA_VIEW_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataViewA(), DEF_PRIV_DATA_VIEW_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getDataViewD(), DEF_PRIV_DATA_VIEW_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getDesignApplicationR(), DEF_PRIV_DESIGN_APPLICATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getDesignApplicationE(), DEF_PRIV_DESIGN_APPLICATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getDesignApplicationA(), DEF_PRIV_DESIGN_APPLICATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getDesignApplicationD(), DEF_PRIV_DESIGN_APPLICATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentationR(), DEF_PRIV_DOCUMENTATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentationE(), DEF_PRIV_DOCUMENTATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentationA(), DEF_PRIV_DOCUMENTATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentationD(), DEF_PRIV_DOCUMENTATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentsR(), DEF_PRIV_DOCUMENTS_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentsE(), DEF_PRIV_DOCUMENTS_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentsA(), DEF_PRIV_DOCUMENTS_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getDocumentsD(), DEF_PRIV_DOCUMENTS_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getFeatureVisibilityR(), DEF_PRIV_FEATURE_VISIBILITY_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getIntegrationHubR(), DEF_PRIV_INTEGRATION_HUB_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getIntegrationHubE(), DEF_PRIV_INTEGRATION_HUB_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getIntegrationHubA(), DEF_PRIV_INTEGRATION_HUB_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getIntegrationHubD(), DEF_PRIV_INTEGRATION_HUB_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getLocalizationR(), DEF_PRIV_LOCALIZATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getLocalizationE(), DEF_PRIV_LOCALIZATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getLocalizationA(), DEF_PRIV_LOCALIZATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getLocalizationD(), DEF_PRIV_LOCALIZATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationR(), DEF_PRIV_RELATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationE(), DEF_PRIV_RELATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationA(), DEF_PRIV_RELATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationD(), DEF_PRIV_RELATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getSystemAdministrationR(), DEF_PRIV_SYSTEM_ADMINISTRATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getSystemAdministrationE(), DEF_PRIV_SYSTEM_ADMINISTRATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getSystemAdministrationA(), DEF_PRIV_SYSTEM_ADMINISTRATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getSystemAdministrationD(), DEF_PRIV_SYSTEM_ADMINISTRATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeR(), DEF_PRIV_TRACKOR_TYPE_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeE(), DEF_PRIV_TRACKOR_TYPE_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeA(), DEF_PRIV_TRACKOR_TYPE_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeD(), DEF_PRIV_TRACKOR_TYPE_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getUserSecurityR(), DEF_PRIV_USER_SECURITY_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getUserSecurityE(), DEF_PRIV_USER_SECURITY_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getUserSecurityA(), DEF_PRIV_USER_SECURITY_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getUserSecurityD(), DEF_PRIV_USER_SECURITY_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getWpTaskFieldR(), DEF_PRIV_WP_TASK_FIELD_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getWpTaskFieldE(), DEF_PRIV_WP_TASK_FIELD_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getWpTaskFieldA(), DEF_PRIV_WP_TASK_FIELD_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getWpTaskFieldD(), DEF_PRIV_WP_TASK_FIELD_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkFlowTemplateR(), DEF_PRIV_WORKFLOW_TEMPLATE_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkFlowTemplateE(), DEF_PRIV_WORKFLOW_TEMPLATE_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkFlowTemplateA(), DEF_PRIV_WORKFLOW_TEMPLATE_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowManagementR(), DEF_PRIV_WORKFLOW_MANAGEMENT_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowManagementE(), DEF_PRIV_WORKFLOW_MANAGEMENT_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowManagementA(), DEF_PRIV_WORKFLOW_MANAGEMENT_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowManagementD(), DEF_PRIV_WORKFLOW_MANAGEMENT_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanManagementR(), DEF_PRIV_WORKPLAN_MANAGEMENT_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanManagementE(), DEF_PRIV_WORKPLAN_MANAGEMENT_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanManagementA(), DEF_PRIV_WORKPLAN_MANAGEMENT_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanManagementD(), DEF_PRIV_WORKPLAN_MANAGEMENT_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanTasksR(), DEF_PRIV_WORKPLAN_TASKS_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanTasksE(), DEF_PRIV_WORKPLAN_TASKS_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanTasksA(), DEF_PRIV_WORKPLAN_TASKS_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkplanTasksD(), DEF_PRIV_WORKPLAN_TASKS_D);

        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableFieldL(), DEF_LOCK_PRIV_FIELD_L);
        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableFieldU(), DEF_LOCK_PRIV_FIELD_U);
        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableRelationL(), DEF_LOCK_PRIV_RELATION_L);
        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableRelationU(), DEF_LOCK_PRIV_RELATION_U);

        setCheckboxValue(securityRole.getDefaultAssignments().getDiscipline(), DEF_ASSIGN_DISCIPLINE);
        setCheckboxValue(securityRole.getDefaultAssignments().getMenuAppliction(), DEF_ASSIGN_MENU);
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalView(), DEF_ASSIGN_GLOBAL_VIEW);
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalFilter(), DEF_ASSIGN_GLOBAL_FILTER);
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalPortal(), DEF_ASSIGN_GLOBAL_PORTAL);
        setCheckboxValue(securityRole.getDefaultAssignments().getRule(), DEF_ASSIGN_RULE);
        setCheckboxValue(securityRole.getDefaultAssignments().getImportt(), DEF_ASSIGN_IMPORT);
        setCheckboxValue(securityRole.getDefaultAssignments().getReport(), DEF_ASSIGN_REPORT);
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalNotif(), DEF_ASSIGN_GLOBAL_NOTIF);
        setCheckboxValue(securityRole.getDefaultAssignments().getChatNotif(), DEF_ASSIGN_CHAT_NOTIF);
        setCheckboxValue(securityRole.getDefaultAssignments().getTrackorTour(), DEF_ASSIGN_TRACKOR_TOUR);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testOnForm(SecurityRole securityRole) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertText(NAME, securityRole.getName());
        assertElement.assertText(DESCRIPTION, securityRole.getDescription());

        assertElement.assertCheckbox(DEF_PRIV_APPLICATION_MIGRATION_R, securityRole.getDefaultPrivs().getApplicationMigrationR());
        assertElement.assertCheckbox(DEF_PRIV_APPLICATION_MIGRATION_E, securityRole.getDefaultPrivs().getApplicationMigrationE());
        assertElement.assertCheckbox(DEF_PRIV_APPLICATION_MIGRATION_A, securityRole.getDefaultPrivs().getApplicationMigrationA());
        assertElement.assertCheckbox(DEF_PRIV_APPLICATION_MIGRATION_D, securityRole.getDefaultPrivs().getApplicationMigrationD());
        assertElement.assertCheckbox(DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_R, securityRole.getDefaultPrivs().getApplicationProcessManagementR());
        assertElement.assertCheckbox(DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_E, securityRole.getDefaultPrivs().getApplicationProcessManagementE());
        assertElement.assertCheckbox(DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_A, securityRole.getDefaultPrivs().getApplicationProcessManagementA());
        assertElement.assertCheckbox(DEF_PRIV_APPLICATION_PROCESS_MANAGEMENT_D, securityRole.getDefaultPrivs().getApplicationProcessManagementD());
        assertElement.assertCheckbox(DEF_PRIV_AUDIT_LOG_R, securityRole.getDefaultPrivs().getAuditLogR());
        assertElement.assertCheckbox(DEF_PRIV_AUDIT_LOG_E, securityRole.getDefaultPrivs().getAuditLogE());
        assertElement.assertCheckbox(DEF_PRIV_AUDIT_LOG_D, securityRole.getDefaultPrivs().getAuditLogD());
        assertElement.assertCheckbox(DEF_PRIV_BUILD_APPLICATION_R, securityRole.getDefaultPrivs().getBuildApplicationR());
        assertElement.assertCheckbox(DEF_PRIV_BUILD_APPLICATION_E, securityRole.getDefaultPrivs().getBuildApplicationE());
        assertElement.assertCheckbox(DEF_PRIV_BUILD_APPLICATION_A, securityRole.getDefaultPrivs().getBuildApplicationA());
        assertElement.assertCheckbox(DEF_PRIV_BUILD_APPLICATION_D, securityRole.getDefaultPrivs().getBuildApplicationD());
        assertElement.assertCheckbox(DEF_PRIV_CHAT_COMMENT_R, securityRole.getDefaultPrivs().getChatCommentR());
        assertElement.assertCheckbox(DEF_PRIV_CHAT_COMMENT_E, securityRole.getDefaultPrivs().getChatCommentE());
        assertElement.assertCheckbox(DEF_PRIV_CHAT_COMMENT_A, securityRole.getDefaultPrivs().getChatCommentA());
        assertElement.assertCheckbox(DEF_PRIV_CHAT_COMMENT_D, securityRole.getDefaultPrivs().getChatCommentD());
        assertElement.assertCheckbox(DEF_PRIV_CELL_COLOR_E, securityRole.getDefaultPrivs().getCellColorE());
        assertElement.assertCheckbox(DEF_PRIV_APPLET_R, securityRole.getDefaultPrivs().getAppletR());
        assertElement.assertCheckbox(DEF_PRIV_APPLET_E, securityRole.getDefaultPrivs().getAppletE());
        assertElement.assertCheckbox(DEF_PRIV_TAB_R, securityRole.getDefaultPrivs().getTabR());
        assertElement.assertCheckbox(DEF_PRIV_TAB_E, securityRole.getDefaultPrivs().getTabE());
        assertElement.assertCheckbox(DEF_PRIV_DASHBOARD_R, securityRole.getDefaultPrivs().getDashboardR());
        assertElement.assertCheckbox(DEF_PRIV_DASHBOARD_E, securityRole.getDefaultPrivs().getDashboardE());
        assertElement.assertCheckbox(DEF_PRIV_DATA_INGESTION_R, securityRole.getDefaultPrivs().getDataIngestionR());
        assertElement.assertCheckbox(DEF_PRIV_DATA_INGESTION_E, securityRole.getDefaultPrivs().getDataIngestionE());
        assertElement.assertCheckbox(DEF_PRIV_DATA_INGESTION_A, securityRole.getDefaultPrivs().getDataIngestionA());
        assertElement.assertCheckbox(DEF_PRIV_DATA_INGESTION_D, securityRole.getDefaultPrivs().getDataIngestionD());
        assertElement.assertCheckbox(DEF_PRIV_DATA_VIEW_R, securityRole.getDefaultPrivs().getDataViewR());
        assertElement.assertCheckbox(DEF_PRIV_DATA_VIEW_E, securityRole.getDefaultPrivs().getDataViewE());
        assertElement.assertCheckbox(DEF_PRIV_DATA_VIEW_A, securityRole.getDefaultPrivs().getDataViewA());
        assertElement.assertCheckbox(DEF_PRIV_DATA_VIEW_D, securityRole.getDefaultPrivs().getDataViewD());
        assertElement.assertCheckbox(DEF_PRIV_DESIGN_APPLICATION_R, securityRole.getDefaultPrivs().getDesignApplicationR());
        assertElement.assertCheckbox(DEF_PRIV_DESIGN_APPLICATION_E, securityRole.getDefaultPrivs().getDesignApplicationE());
        assertElement.assertCheckbox(DEF_PRIV_DESIGN_APPLICATION_A, securityRole.getDefaultPrivs().getDesignApplicationA());
        assertElement.assertCheckbox(DEF_PRIV_DESIGN_APPLICATION_D, securityRole.getDefaultPrivs().getDesignApplicationD());
        assertElement.assertCheckbox(DEF_PRIV_DOCUMENTATION_R, securityRole.getDefaultPrivs().getDocumentationR());
        assertElement.assertCheckbox(DEF_PRIV_DOCUMENTATION_E, securityRole.getDefaultPrivs().getDocumentationE());
        assertElement.assertCheckbox(DEF_PRIV_DOCUMENTATION_A, securityRole.getDefaultPrivs().getDocumentationA());
        assertElement.assertCheckbox(DEF_PRIV_DOCUMENTATION_D, securityRole.getDefaultPrivs().getDocumentationD());
        assertElement.assertCheckbox(DEF_PRIV_DOCUMENTS_R, securityRole.getDefaultPrivs().getDocumentsR());
        assertElement.assertCheckbox(DEF_PRIV_DOCUMENTS_E, securityRole.getDefaultPrivs().getDocumentsE());
        assertElement.assertCheckbox(DEF_PRIV_DOCUMENTS_A, securityRole.getDefaultPrivs().getDocumentsA());
        assertElement.assertCheckbox(DEF_PRIV_DOCUMENTS_D, securityRole.getDefaultPrivs().getDocumentsD());
        assertElement.assertCheckbox(DEF_PRIV_FEATURE_VISIBILITY_R, securityRole.getDefaultPrivs().getFeatureVisibilityR());
        assertElement.assertCheckbox(DEF_PRIV_INTEGRATION_HUB_R, securityRole.getDefaultPrivs().getIntegrationHubR());
        assertElement.assertCheckbox(DEF_PRIV_INTEGRATION_HUB_E, securityRole.getDefaultPrivs().getIntegrationHubE());
        assertElement.assertCheckbox(DEF_PRIV_INTEGRATION_HUB_A, securityRole.getDefaultPrivs().getIntegrationHubA());
        assertElement.assertCheckbox(DEF_PRIV_INTEGRATION_HUB_D, securityRole.getDefaultPrivs().getIntegrationHubD());
        assertElement.assertCheckbox(DEF_PRIV_LOCALIZATION_R, securityRole.getDefaultPrivs().getLocalizationR());
        assertElement.assertCheckbox(DEF_PRIV_LOCALIZATION_E, securityRole.getDefaultPrivs().getLocalizationE());
        assertElement.assertCheckbox(DEF_PRIV_LOCALIZATION_A, securityRole.getDefaultPrivs().getLocalizationA());
        assertElement.assertCheckbox(DEF_PRIV_LOCALIZATION_D, securityRole.getDefaultPrivs().getLocalizationD());
        assertElement.assertCheckbox(DEF_PRIV_RELATION_R, securityRole.getDefaultPrivs().getRelationR());
        assertElement.assertCheckbox(DEF_PRIV_RELATION_E, securityRole.getDefaultPrivs().getRelationE());
        assertElement.assertCheckbox(DEF_PRIV_RELATION_A, securityRole.getDefaultPrivs().getRelationA());
        assertElement.assertCheckbox(DEF_PRIV_RELATION_D, securityRole.getDefaultPrivs().getRelationD());
        assertElement.assertCheckbox(DEF_PRIV_SYSTEM_ADMINISTRATION_R, securityRole.getDefaultPrivs().getSystemAdministrationR());
        assertElement.assertCheckbox(DEF_PRIV_SYSTEM_ADMINISTRATION_E, securityRole.getDefaultPrivs().getSystemAdministrationE());
        assertElement.assertCheckbox(DEF_PRIV_SYSTEM_ADMINISTRATION_A, securityRole.getDefaultPrivs().getSystemAdministrationA());
        assertElement.assertCheckbox(DEF_PRIV_SYSTEM_ADMINISTRATION_D, securityRole.getDefaultPrivs().getSystemAdministrationD());
        assertElement.assertCheckbox(DEF_PRIV_TRACKOR_TYPE_R, securityRole.getDefaultPrivs().getTrackorTypeR());
        assertElement.assertCheckbox(DEF_PRIV_TRACKOR_TYPE_E, securityRole.getDefaultPrivs().getTrackorTypeE());
        assertElement.assertCheckbox(DEF_PRIV_TRACKOR_TYPE_A, securityRole.getDefaultPrivs().getTrackorTypeA());
        assertElement.assertCheckbox(DEF_PRIV_TRACKOR_TYPE_D, securityRole.getDefaultPrivs().getTrackorTypeD());
        assertElement.assertCheckbox(DEF_PRIV_USER_SECURITY_R, securityRole.getDefaultPrivs().getUserSecurityR());
        assertElement.assertCheckbox(DEF_PRIV_USER_SECURITY_E, securityRole.getDefaultPrivs().getUserSecurityE());
        assertElement.assertCheckbox(DEF_PRIV_USER_SECURITY_A, securityRole.getDefaultPrivs().getUserSecurityA());
        assertElement.assertCheckbox(DEF_PRIV_USER_SECURITY_D, securityRole.getDefaultPrivs().getUserSecurityD());
        assertElement.assertCheckbox(DEF_PRIV_WP_TASK_FIELD_R, securityRole.getDefaultPrivs().getWpTaskFieldR());
        assertElement.assertCheckbox(DEF_PRIV_WP_TASK_FIELD_E, securityRole.getDefaultPrivs().getWpTaskFieldE());
        assertElement.assertCheckbox(DEF_PRIV_WP_TASK_FIELD_A, securityRole.getDefaultPrivs().getWpTaskFieldA());
        assertElement.assertCheckbox(DEF_PRIV_WP_TASK_FIELD_D, securityRole.getDefaultPrivs().getWpTaskFieldD());
        assertElement.assertCheckbox(DEF_PRIV_WORKFLOW_TEMPLATE_R, securityRole.getDefaultPrivs().getWorkFlowTemplateR());
        assertElement.assertCheckbox(DEF_PRIV_WORKFLOW_TEMPLATE_E, securityRole.getDefaultPrivs().getWorkFlowTemplateE());
        assertElement.assertCheckbox(DEF_PRIV_WORKFLOW_TEMPLATE_A, securityRole.getDefaultPrivs().getWorkFlowTemplateA());
        assertElement.assertCheckbox(DEF_PRIV_WORKFLOW_MANAGEMENT_R, securityRole.getDefaultPrivs().getWorkflowManagementR());
        assertElement.assertCheckbox(DEF_PRIV_WORKFLOW_MANAGEMENT_E, securityRole.getDefaultPrivs().getWorkflowManagementE());
        assertElement.assertCheckbox(DEF_PRIV_WORKFLOW_MANAGEMENT_A, securityRole.getDefaultPrivs().getWorkflowManagementA());
        assertElement.assertCheckbox(DEF_PRIV_WORKFLOW_MANAGEMENT_D, securityRole.getDefaultPrivs().getWorkflowManagementD());
        assertElement.assertCheckbox(DEF_PRIV_WORKPLAN_MANAGEMENT_R, securityRole.getDefaultPrivs().getWorkplanManagementR());
        assertElement.assertCheckbox(DEF_PRIV_WORKPLAN_MANAGEMENT_E, securityRole.getDefaultPrivs().getWorkplanManagementE());
        assertElement.assertCheckbox(DEF_PRIV_WORKPLAN_MANAGEMENT_A, securityRole.getDefaultPrivs().getWorkplanManagementA());
        assertElement.assertCheckbox(DEF_PRIV_WORKPLAN_MANAGEMENT_D, securityRole.getDefaultPrivs().getWorkplanManagementD());
        assertElement.assertCheckbox(DEF_PRIV_WORKPLAN_TASKS_R, securityRole.getDefaultPrivs().getWorkplanTasksR());
        assertElement.assertCheckbox(DEF_PRIV_WORKPLAN_TASKS_E, securityRole.getDefaultPrivs().getWorkplanTasksE());
        assertElement.assertCheckbox(DEF_PRIV_WORKPLAN_TASKS_A, securityRole.getDefaultPrivs().getWorkplanTasksA());
        assertElement.assertCheckbox(DEF_PRIV_WORKPLAN_TASKS_D, securityRole.getDefaultPrivs().getWorkplanTasksD());

        assertElement.assertCheckbox(DEF_LOCK_PRIV_FIELD_L, securityRole.getDefaultLockPrivs().getLockableFieldL());
        assertElement.assertCheckbox(DEF_LOCK_PRIV_FIELD_U, securityRole.getDefaultLockPrivs().getLockableFieldU());
        assertElement.assertCheckbox(DEF_LOCK_PRIV_RELATION_L, securityRole.getDefaultLockPrivs().getLockableRelationL());
        assertElement.assertCheckbox(DEF_LOCK_PRIV_RELATION_U, securityRole.getDefaultLockPrivs().getLockableRelationU());

        assertElement.assertCheckbox(DEF_ASSIGN_DISCIPLINE, securityRole.getDefaultAssignments().getDiscipline());
        assertElement.assertCheckbox(DEF_ASSIGN_MENU, securityRole.getDefaultAssignments().getMenuAppliction());
        assertElement.assertCheckbox(DEF_ASSIGN_GLOBAL_VIEW, securityRole.getDefaultAssignments().getGlobalView());
        assertElement.assertCheckbox(DEF_ASSIGN_GLOBAL_FILTER, securityRole.getDefaultAssignments().getGlobalFilter());
        assertElement.assertCheckbox(DEF_ASSIGN_GLOBAL_PORTAL, securityRole.getDefaultAssignments().getGlobalPortal());
        assertElement.assertCheckbox(DEF_ASSIGN_RULE, securityRole.getDefaultAssignments().getRule());
        assertElement.assertCheckbox(DEF_ASSIGN_IMPORT, securityRole.getDefaultAssignments().getImportt());
        assertElement.assertCheckbox(DEF_ASSIGN_REPORT, securityRole.getDefaultAssignments().getReport());
        assertElement.assertCheckbox(DEF_ASSIGN_GLOBAL_NOTIF, securityRole.getDefaultAssignments().getGlobalNotif());
        assertElement.assertCheckbox(DEF_ASSIGN_CHAT_NOTIF, securityRole.getDefaultAssignments().getChatNotif());
        assertElement.assertCheckbox(DEF_ASSIGN_TRACKOR_TOUR, securityRole.getDefaultAssignments().getTrackorTour());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, SecurityRole securityRole) {
        Map<Integer, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Role Name"), securityRole.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Description"), securityRole.getDescription());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

    private void setCheckboxValue(String value, String checkboxName) {
        if ((value.equals("YES") && !checkbox.isCheckedByName(checkboxName))
                || (value.equals("NO") && checkbox.isCheckedByName(checkboxName))) {
            checkbox.clickByName(checkboxName);
        }
    }

}
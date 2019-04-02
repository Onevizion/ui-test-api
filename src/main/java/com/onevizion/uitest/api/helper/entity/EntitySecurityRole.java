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
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.vo.entity.SecurityRole;

@Component
public class EntitySecurityRole {

    private static final String NAME = "roleType";
    private static final String DESCRIPTION = "description";

    private static final String DEF_PRIV_APPLET_R = "cbPrivR1";
    private static final String DEF_PRIV_APPLET_E = "cbPrivE1";
    private static final String DEF_PRIV_APPLET_A = "cbPrivA1";
    private static final String DEF_PRIV_APPLET_D = "cbPrivD1";
    private static final String DEF_PRIV_CONFIG_APPLET_R = "cbPrivR2";
    private static final String DEF_PRIV_CONFIG_APPLET_E = "cbPrivE2";
    private static final String DEF_PRIV_CONFIG_TAB_R = "cbPrivR3";
    private static final String DEF_PRIV_CONFIG_TAB_E = "cbPrivE3";
    private static final String DEF_PRIV_RELATION_R = "cbPrivR12";
    private static final String DEF_PRIV_RELATION_E = "cbPrivE12";
    private static final String DEF_PRIV_RELATION_A = "cbPrivA12";
    private static final String DEF_PRIV_RELATION_D = "cbPrivD12";
    private static final String DEF_PRIV_SUPERUSER_APPLET_R = "cbPrivR9";
    private static final String DEF_PRIV_SUPERUSER_APPLET_E = "cbPrivE9";
    private static final String DEF_PRIV_SUPERUSER_APPLET_A = "cbPrivA9";
    private static final String DEF_PRIV_SUPERUSER_APPLET_D = "cbPrivD9";
    private static final String DEF_PRIV_TRACKOR_TYPE_R = "cbPrivR15";
    private static final String DEF_PRIV_TRACKOR_TYPE_E = "cbPrivE15";
    private static final String DEF_PRIV_TRACKOR_TYPE_A = "cbPrivA15";
    private static final String DEF_PRIV_TRACKOR_TYPE_D = "cbPrivD15";
    private static final String DEF_PRIV_WORKFLOW_R = "cbPrivR10";
    private static final String DEF_PRIV_WORKFLOW_E = "cbPrivE10";
    private static final String DEF_PRIV_WORKFLOW_A = "cbPrivA10";

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
    private static final String DEF_ASSIGN_TRACKOR_TOUR = "autoTourAssign";

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Js js;

    @Resource
    private Grid grid;

    @Resource
    private Grid2 grid2;

    @Resource
    private Checkbox checkbox;

    public void add(SecurityRole securityRole) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(securityRole.getName());

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(securityRole.getDescription());

        setCheckboxValue(securityRole.getDefaultPrivs().getAppletR(), DEF_PRIV_APPLET_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletE(), DEF_PRIV_APPLET_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletA(), DEF_PRIV_APPLET_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletD(), DEF_PRIV_APPLET_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigAppletR(), DEF_PRIV_CONFIG_APPLET_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigAppletE(), DEF_PRIV_CONFIG_APPLET_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigTabR(), DEF_PRIV_CONFIG_TAB_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigTabE(), DEF_PRIV_CONFIG_TAB_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationR(), DEF_PRIV_RELATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationE(), DEF_PRIV_RELATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationA(), DEF_PRIV_RELATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationD(), DEF_PRIV_RELATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletR(), DEF_PRIV_SUPERUSER_APPLET_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletE(), DEF_PRIV_SUPERUSER_APPLET_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletA(), DEF_PRIV_SUPERUSER_APPLET_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletD(), DEF_PRIV_SUPERUSER_APPLET_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeR(), DEF_PRIV_TRACKOR_TYPE_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeE(), DEF_PRIV_TRACKOR_TYPE_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeA(), DEF_PRIV_TRACKOR_TYPE_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeD(), DEF_PRIV_TRACKOR_TYPE_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowR(), DEF_PRIV_WORKFLOW_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowE(), DEF_PRIV_WORKFLOW_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowA(), DEF_PRIV_WORKFLOW_A);

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

        setCheckboxValue(securityRole.getDefaultPrivs().getAppletR(), DEF_PRIV_APPLET_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletE(), DEF_PRIV_APPLET_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletA(), DEF_PRIV_APPLET_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletD(), DEF_PRIV_APPLET_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigAppletR(), DEF_PRIV_CONFIG_APPLET_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigAppletE(), DEF_PRIV_CONFIG_APPLET_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigTabR(), DEF_PRIV_CONFIG_TAB_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigTabE(), DEF_PRIV_CONFIG_TAB_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationR(), DEF_PRIV_RELATION_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationE(), DEF_PRIV_RELATION_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationA(), DEF_PRIV_RELATION_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationD(), DEF_PRIV_RELATION_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletR(), DEF_PRIV_SUPERUSER_APPLET_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletE(), DEF_PRIV_SUPERUSER_APPLET_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletA(), DEF_PRIV_SUPERUSER_APPLET_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletD(), DEF_PRIV_SUPERUSER_APPLET_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeR(), DEF_PRIV_TRACKOR_TYPE_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeE(), DEF_PRIV_TRACKOR_TYPE_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeA(), DEF_PRIV_TRACKOR_TYPE_A);
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeD(), DEF_PRIV_TRACKOR_TYPE_D);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowR(), DEF_PRIV_WORKFLOW_R);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowE(), DEF_PRIV_WORKFLOW_E);
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowA(), DEF_PRIV_WORKFLOW_A);

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

        assertElement.assertCheckbox(DEF_PRIV_APPLET_R, securityRole.getDefaultPrivs().getAppletR());
        assertElement.assertCheckbox(DEF_PRIV_APPLET_E, securityRole.getDefaultPrivs().getAppletE());
        assertElement.assertCheckbox(DEF_PRIV_APPLET_A, securityRole.getDefaultPrivs().getAppletA());
        assertElement.assertCheckbox(DEF_PRIV_APPLET_D, securityRole.getDefaultPrivs().getAppletD());

        assertElement.assertCheckbox(DEF_PRIV_CONFIG_APPLET_R, securityRole.getDefaultPrivs().getConfigAppletR());
        assertElement.assertCheckbox(DEF_PRIV_CONFIG_APPLET_E, securityRole.getDefaultPrivs().getConfigAppletE());

        assertElement.assertCheckbox(DEF_PRIV_CONFIG_TAB_R, securityRole.getDefaultPrivs().getConfigTabR());
        assertElement.assertCheckbox(DEF_PRIV_CONFIG_TAB_E, securityRole.getDefaultPrivs().getConfigTabE());

        assertElement.assertCheckbox(DEF_PRIV_RELATION_R, securityRole.getDefaultPrivs().getRelationR());
        assertElement.assertCheckbox(DEF_PRIV_RELATION_E, securityRole.getDefaultPrivs().getRelationE());
        assertElement.assertCheckbox(DEF_PRIV_RELATION_A, securityRole.getDefaultPrivs().getRelationA());
        assertElement.assertCheckbox(DEF_PRIV_RELATION_D, securityRole.getDefaultPrivs().getRelationD());

        assertElement.assertCheckbox(DEF_PRIV_SUPERUSER_APPLET_R, securityRole.getDefaultPrivs().getSuperuserAppletR());
        assertElement.assertCheckbox(DEF_PRIV_SUPERUSER_APPLET_E, securityRole.getDefaultPrivs().getSuperuserAppletE());
        assertElement.assertCheckbox(DEF_PRIV_SUPERUSER_APPLET_A, securityRole.getDefaultPrivs().getSuperuserAppletA());
        assertElement.assertCheckbox(DEF_PRIV_SUPERUSER_APPLET_D, securityRole.getDefaultPrivs().getSuperuserAppletD());

        assertElement.assertCheckbox(DEF_PRIV_TRACKOR_TYPE_R, securityRole.getDefaultPrivs().getTrackorTypeR());
        assertElement.assertCheckbox(DEF_PRIV_TRACKOR_TYPE_E, securityRole.getDefaultPrivs().getTrackorTypeE());
        assertElement.assertCheckbox(DEF_PRIV_TRACKOR_TYPE_A, securityRole.getDefaultPrivs().getTrackorTypeA());
        assertElement.assertCheckbox(DEF_PRIV_TRACKOR_TYPE_D, securityRole.getDefaultPrivs().getTrackorTypeD());

        assertElement.assertCheckbox(DEF_PRIV_WORKFLOW_R, securityRole.getDefaultPrivs().getWorkflowR());
        assertElement.assertCheckbox(DEF_PRIV_WORKFLOW_E, securityRole.getDefaultPrivs().getWorkflowE());
        assertElement.assertCheckbox(DEF_PRIV_WORKFLOW_A, securityRole.getDefaultPrivs().getWorkflowA());

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
        assertElement.assertCheckbox(DEF_ASSIGN_TRACKOR_TOUR, securityRole.getDefaultAssignments().getTrackorTour());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, SecurityRole securityRole) {
        Map<Long, String> gridVals = new HashMap<>();

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
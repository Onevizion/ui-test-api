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
import com.onevizion.uitest.api.vo.entity.SecurityRole;

@Component
public class EntitySecurityRole {

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
    private Checkbox checkbox;

    public void add(SecurityRole securityRole) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("roleType")).sendKeys(securityRole.getName());

        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(securityRole.getDescription());

        setCheckboxValue(securityRole.getDefaultPrivs().getAppletR(), "cbPrivR1");
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletE(), "cbPrivE1");
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletA(), "cbPrivA1");
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletD(), "cbPrivD1");
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigAppletR(), "cbPrivR2");
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigAppletE(), "cbPrivE2");
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigTabR(), "cbPrivR3");
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigTabE(), "cbPrivE3");
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationR(), "cbPrivR12");
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationE(), "cbPrivE12");
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationA(), "cbPrivA12");
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationD(), "cbPrivD12");
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletR(), "cbPrivR9");
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletE(), "cbPrivE9");
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletA(), "cbPrivA9");
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletD(), "cbPrivD9");
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeR(), "cbPrivR15");
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeE(), "cbPrivE15");
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeA(), "cbPrivA15");
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeD(), "cbPrivD15");
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowR(), "cbPrivR10");
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowE(), "cbPrivE10");
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowA(), "cbPrivA10");

        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableFieldL(), "cbLock5");
        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableFieldU(), "cbUnlock5");
        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableRelationL(), "cbLock6");
        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableRelationU(), "cbUnlock6");

        setCheckboxValue(securityRole.getDefaultAssignments().getDiscipline(), "autoDiscpAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getMenuAppliction(), "autoAppAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalView(), "autoViewAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalFilter(), "autoFilterAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalPortal(), "autoPortalAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getRule(), "autoRuleAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getImportt(), "autoImportAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getReport(), "autoReportAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalNotif(), "autoNotifAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getTrackorTour(), "autoTourAssign");

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(SecurityRole securityRole) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("roleType")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("roleType")).sendKeys(securityRole.getName());

        seleniumSettings.getWebDriver().findElement(By.name("description")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(securityRole.getDescription());

        setCheckboxValue(securityRole.getDefaultPrivs().getAppletR(), "cbPrivR1");
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletE(), "cbPrivE1");
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletA(), "cbPrivA1");
        setCheckboxValue(securityRole.getDefaultPrivs().getAppletD(), "cbPrivD1");
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigAppletR(), "cbPrivR2");
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigAppletE(), "cbPrivE2");
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigTabR(), "cbPrivR3");
        setCheckboxValue(securityRole.getDefaultPrivs().getConfigTabE(), "cbPrivE3");
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationR(), "cbPrivR12");
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationE(), "cbPrivE12");
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationA(), "cbPrivA12");
        setCheckboxValue(securityRole.getDefaultPrivs().getRelationD(), "cbPrivD12");
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletR(), "cbPrivR9");
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletE(), "cbPrivE9");
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletA(), "cbPrivA9");
        setCheckboxValue(securityRole.getDefaultPrivs().getSuperuserAppletD(), "cbPrivD9");
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeR(), "cbPrivR15");
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeE(), "cbPrivE15");
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeA(), "cbPrivA15");
        setCheckboxValue(securityRole.getDefaultPrivs().getTrackorTypeD(), "cbPrivD15");
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowR(), "cbPrivR10");
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowE(), "cbPrivE10");
        setCheckboxValue(securityRole.getDefaultPrivs().getWorkflowA(), "cbPrivA10");

        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableFieldL(), "cbLock5");
        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableFieldU(), "cbUnlock5");
        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableRelationL(), "cbLock6");
        setCheckboxValue(securityRole.getDefaultLockPrivs().getLockableRelationU(), "cbUnlock6");

        setCheckboxValue(securityRole.getDefaultAssignments().getDiscipline(), "autoDiscpAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getMenuAppliction(), "autoAppAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalView(), "autoViewAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalFilter(), "autoFilterAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalPortal(), "autoPortalAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getRule(), "autoRuleAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getImportt(), "autoImportAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getReport(), "autoReportAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getGlobalNotif(), "autoNotifAssign");
        setCheckboxValue(securityRole.getDefaultAssignments().getTrackorTour(), "autoTourAssign");

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(SecurityRole securityRole) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.AssertText("roleType", securityRole.getName());
        assertElement.AssertText("description", securityRole.getDescription());

        assertElement.AssertCheckBoxNew("cbPrivR1", securityRole.getDefaultPrivs().getAppletR());
        assertElement.AssertCheckBoxNew("cbPrivE1", securityRole.getDefaultPrivs().getAppletE());
        assertElement.AssertCheckBoxNew("cbPrivA1", securityRole.getDefaultPrivs().getAppletA());
        assertElement.AssertCheckBoxNew("cbPrivD1", securityRole.getDefaultPrivs().getAppletD());

        assertElement.AssertCheckBoxNew("cbPrivR2", securityRole.getDefaultPrivs().getConfigAppletR());
        assertElement.AssertCheckBoxNew("cbPrivE2", securityRole.getDefaultPrivs().getConfigAppletE());

        assertElement.AssertCheckBoxNew("cbPrivR3", securityRole.getDefaultPrivs().getConfigTabR());
        assertElement.AssertCheckBoxNew("cbPrivE3", securityRole.getDefaultPrivs().getConfigTabE());

        assertElement.AssertCheckBoxNew("cbPrivR12", securityRole.getDefaultPrivs().getRelationR());
        assertElement.AssertCheckBoxNew("cbPrivE12", securityRole.getDefaultPrivs().getRelationE());
        assertElement.AssertCheckBoxNew("cbPrivA12", securityRole.getDefaultPrivs().getRelationA());
        assertElement.AssertCheckBoxNew("cbPrivD12", securityRole.getDefaultPrivs().getRelationD());

        assertElement.AssertCheckBoxNew("cbPrivR9", securityRole.getDefaultPrivs().getSuperuserAppletR());
        assertElement.AssertCheckBoxNew("cbPrivE9", securityRole.getDefaultPrivs().getSuperuserAppletE());
        assertElement.AssertCheckBoxNew("cbPrivA9", securityRole.getDefaultPrivs().getSuperuserAppletA());
        assertElement.AssertCheckBoxNew("cbPrivD9", securityRole.getDefaultPrivs().getSuperuserAppletD());

        assertElement.AssertCheckBoxNew("cbPrivR15", securityRole.getDefaultPrivs().getTrackorTypeR());
        assertElement.AssertCheckBoxNew("cbPrivE15", securityRole.getDefaultPrivs().getTrackorTypeE());
        assertElement.AssertCheckBoxNew("cbPrivA15", securityRole.getDefaultPrivs().getTrackorTypeA());
        assertElement.AssertCheckBoxNew("cbPrivD15", securityRole.getDefaultPrivs().getTrackorTypeD());

        assertElement.AssertCheckBoxNew("cbPrivR10", securityRole.getDefaultPrivs().getWorkflowR());
        assertElement.AssertCheckBoxNew("cbPrivE10", securityRole.getDefaultPrivs().getWorkflowE());
        assertElement.AssertCheckBoxNew("cbPrivA10", securityRole.getDefaultPrivs().getWorkflowA());

        assertElement.AssertCheckBoxNew("cbLock5", securityRole.getDefaultLockPrivs().getLockableFieldL());
        assertElement.AssertCheckBoxNew("cbUnlock5", securityRole.getDefaultLockPrivs().getLockableFieldU());

        assertElement.AssertCheckBoxNew("cbLock6", securityRole.getDefaultLockPrivs().getLockableRelationL());
        assertElement.AssertCheckBoxNew("cbUnlock6", securityRole.getDefaultLockPrivs().getLockableRelationU());

        assertElement.AssertCheckBoxNew("autoDiscpAssign", securityRole.getDefaultAssignments().getDiscipline());
        assertElement.AssertCheckBoxNew("autoAppAssign", securityRole.getDefaultAssignments().getMenuAppliction());
        assertElement.AssertCheckBoxNew("autoViewAssign", securityRole.getDefaultAssignments().getGlobalView());
        assertElement.AssertCheckBoxNew("autoFilterAssign", securityRole.getDefaultAssignments().getGlobalFilter());
        assertElement.AssertCheckBoxNew("autoPortalAssign", securityRole.getDefaultAssignments().getGlobalPortal());
        assertElement.AssertCheckBoxNew("autoRuleAssign", securityRole.getDefaultAssignments().getRule());
        assertElement.AssertCheckBoxNew("autoImportAssign", securityRole.getDefaultAssignments().getImportt());
        assertElement.AssertCheckBoxNew("autoReportAssign", securityRole.getDefaultAssignments().getReport());
        assertElement.AssertCheckBoxNew("autoNotifAssign", securityRole.getDefaultAssignments().getGlobalNotif());
        assertElement.AssertCheckBoxNew("autoTourAssign", securityRole.getDefaultAssignments().getTrackorTour());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, SecurityRole securityRole) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

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
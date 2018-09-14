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
import com.onevizion.uitest.api.helper.WaitHelper;
import com.onevizion.uitest.api.helper.WindowHelper;
import com.onevizion.uitest.api.vo.entity.SecurityRole;

@Component
public class EntitySecurityRole {

    @Resource
    private WindowHelper windowHelper;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private CheckboxHelper checkboxHelper;

    public void add(SecurityRole securityRole) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

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

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(SecurityRole securityRole) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

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

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(SecurityRole securityRole) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        assertHelper.AssertText("roleType", securityRole.getName());
        assertHelper.AssertText("description", securityRole.getDescription());

        assertHelper.AssertCheckBoxNew("cbPrivR1", securityRole.getDefaultPrivs().getAppletR());
        assertHelper.AssertCheckBoxNew("cbPrivE1", securityRole.getDefaultPrivs().getAppletE());
        assertHelper.AssertCheckBoxNew("cbPrivA1", securityRole.getDefaultPrivs().getAppletA());
        assertHelper.AssertCheckBoxNew("cbPrivD1", securityRole.getDefaultPrivs().getAppletD());

        assertHelper.AssertCheckBoxNew("cbPrivR2", securityRole.getDefaultPrivs().getConfigAppletR());
        assertHelper.AssertCheckBoxNew("cbPrivE2", securityRole.getDefaultPrivs().getConfigAppletE());

        assertHelper.AssertCheckBoxNew("cbPrivR3", securityRole.getDefaultPrivs().getConfigTabR());
        assertHelper.AssertCheckBoxNew("cbPrivE3", securityRole.getDefaultPrivs().getConfigTabE());

        assertHelper.AssertCheckBoxNew("cbPrivR12", securityRole.getDefaultPrivs().getRelationR());
        assertHelper.AssertCheckBoxNew("cbPrivE12", securityRole.getDefaultPrivs().getRelationE());
        assertHelper.AssertCheckBoxNew("cbPrivA12", securityRole.getDefaultPrivs().getRelationA());
        assertHelper.AssertCheckBoxNew("cbPrivD12", securityRole.getDefaultPrivs().getRelationD());

        assertHelper.AssertCheckBoxNew("cbPrivR9", securityRole.getDefaultPrivs().getSuperuserAppletR());
        assertHelper.AssertCheckBoxNew("cbPrivE9", securityRole.getDefaultPrivs().getSuperuserAppletE());
        assertHelper.AssertCheckBoxNew("cbPrivA9", securityRole.getDefaultPrivs().getSuperuserAppletA());
        assertHelper.AssertCheckBoxNew("cbPrivD9", securityRole.getDefaultPrivs().getSuperuserAppletD());

        assertHelper.AssertCheckBoxNew("cbPrivR15", securityRole.getDefaultPrivs().getTrackorTypeR());
        assertHelper.AssertCheckBoxNew("cbPrivE15", securityRole.getDefaultPrivs().getTrackorTypeE());
        assertHelper.AssertCheckBoxNew("cbPrivA15", securityRole.getDefaultPrivs().getTrackorTypeA());
        assertHelper.AssertCheckBoxNew("cbPrivD15", securityRole.getDefaultPrivs().getTrackorTypeD());

        assertHelper.AssertCheckBoxNew("cbPrivR10", securityRole.getDefaultPrivs().getWorkflowR());
        assertHelper.AssertCheckBoxNew("cbPrivE10", securityRole.getDefaultPrivs().getWorkflowE());
        assertHelper.AssertCheckBoxNew("cbPrivA10", securityRole.getDefaultPrivs().getWorkflowA());

        assertHelper.AssertCheckBoxNew("cbLock5", securityRole.getDefaultLockPrivs().getLockableFieldL());
        assertHelper.AssertCheckBoxNew("cbUnlock5", securityRole.getDefaultLockPrivs().getLockableFieldU());

        assertHelper.AssertCheckBoxNew("cbLock6", securityRole.getDefaultLockPrivs().getLockableRelationL());
        assertHelper.AssertCheckBoxNew("cbUnlock6", securityRole.getDefaultLockPrivs().getLockableRelationU());

        assertHelper.AssertCheckBoxNew("autoDiscpAssign", securityRole.getDefaultAssignments().getDiscipline());
        assertHelper.AssertCheckBoxNew("autoAppAssign", securityRole.getDefaultAssignments().getMenuAppliction());
        assertHelper.AssertCheckBoxNew("autoViewAssign", securityRole.getDefaultAssignments().getGlobalView());
        assertHelper.AssertCheckBoxNew("autoFilterAssign", securityRole.getDefaultAssignments().getGlobalFilter());
        assertHelper.AssertCheckBoxNew("autoPortalAssign", securityRole.getDefaultAssignments().getGlobalPortal());
        assertHelper.AssertCheckBoxNew("autoRuleAssign", securityRole.getDefaultAssignments().getRule());
        assertHelper.AssertCheckBoxNew("autoImportAssign", securityRole.getDefaultAssignments().getImportt());
        assertHelper.AssertCheckBoxNew("autoReportAssign", securityRole.getDefaultAssignments().getReport());
        assertHelper.AssertCheckBoxNew("autoNotifAssign", securityRole.getDefaultAssignments().getGlobalNotif());
        assertHelper.AssertCheckBoxNew("autoTourAssign", securityRole.getDefaultAssignments().getTrackorTour());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, SecurityRole securityRole) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Role Name"), securityRole.getName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Description"), securityRole.getDescription());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

    private void setCheckboxValue(String value, String checkboxName) {
        if ((value.equals("YES") && !checkboxHelper.isCheckedByName(checkboxName))
                || (value.equals("NO") && checkboxHelper.isCheckedByName(checkboxName))) {
            checkboxHelper.clickByName(checkboxName);
        }
    }

}
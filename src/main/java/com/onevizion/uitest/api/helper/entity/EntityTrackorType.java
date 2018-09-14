package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.CheckboxHelper;
import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Tab;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.vo.entity.TrackorType;

@Component
public class EntityTrackorType {

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private Js js;

    @Resource
    private Tab tab;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private CheckboxHelper checkboxHelper;

    public void add(TrackorType trackorType) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("trackorType")).sendKeys(trackorType.getName());
        seleniumSettings.getWebDriver().findElement(By.name("appletLabel")).sendKeys(trackorType.getLabel());
        seleniumSettings.getWebDriver().findElement(By.name("trackoridLabel")).sendKeys(trackorType.getLabelItemId());
        seleniumSettings.getWebDriver().findElement(By.name("trackorClassLabel")).sendKeys(trackorType.getLabelClass());
        seleniumSettings.getWebDriver().findElement(By.name("prefixLabel")).sendKeys(trackorType.getLabelPrefix());
        seleniumSettings.getWebDriver().findElement(By.name("myTrackorsLabel")).sendKeys(trackorType.getLabelMyItems());

        (new Select(seleniumSettings.getWebDriver().findElement(By.name("wpModeId")))).selectByVisibleText(trackorType.getLimitWp());
        (new Select(seleniumSettings.getWebDriver().findElement(By.name("componentsPackageId")))).selectByVisibleText(trackorType.getCompPack());

        if ((trackorType.getClone().equals("YES") && !checkboxHelper.isCheckedByName("cloningAllowed"))
                || (trackorType.getClone().equals("NO") && checkboxHelper.isCheckedByName("cloningAllowed"))) {
            checkboxHelper.clickByName("cloningAllowed");
        }
        if ((trackorType.getTemplate().equals("YES") && !checkboxHelper.isCheckedByName("template"))
                || (trackorType.getTemplate().equals("NO") && checkboxHelper.isCheckedByName("template"))) {
            checkboxHelper.clickByName("template");
        }
        if ((trackorType.getUser().equals("YES") && !checkboxHelper.isCheckedByName("user"))
                || (trackorType.getUser().equals("NO") && checkboxHelper.isCheckedByName("user"))) {
            checkboxHelper.clickByName("user");
        }
        if ((trackorType.getEfileContainer().equals("YES") && !checkboxHelper.isCheckedByName("efileContainer"))
                || (trackorType.getEfileContainer().equals("NO") && checkboxHelper.isCheckedByName("efileContainer"))) {
            checkboxHelper.clickByName("efileContainer");
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(TrackorType trackorType) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        
        
        
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(TrackorType trackorType) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertHelper.AssertText("trackorType", trackorType.getName());
        assertHelper.AssertText("appletLabel", trackorType.getLabel());
        assertHelper.AssertText("trackoridLabel", trackorType.getLabelItemId());
        assertHelper.AssertText("trackorClassLabel", trackorType.getLabelClass());
        assertHelper.AssertText("prefixLabel", trackorType.getLabelPrefix());
        assertHelper.AssertText("myTrackorsLabel", trackorType.getLabelMyItems());
        assertHelper.AssertRadioPsSelector("objDisplayField", "btnobjDisplayField", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getAliasField(), 1L, true);
        assertHelper.AssertCheckboxPsSelector("xsFieldsStr", "btnxsFieldsStr", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getAutoFilterFields(), 1L, true);
        assertHelper.AssertSelect("wpModeId", trackorType.getLimitWp());
        assertHelper.AssertSelect("componentsPackageId", trackorType.getCompPack());
        assertHelper.AssertCheckBoxNew("cloningAllowed", trackorType.getClone());
        assertHelper.AssertCheckBoxNew("template", trackorType.getTemplate());
        assertHelper.AssertCheckBoxNew("user", trackorType.getUser());
        assertHelper.AssertCheckBoxNew("efileContainer", trackorType.getEfileContainer());

        tab.goToTab(2L); // Key Generation
        assertHelper.AssertCheckBoxNew("autokey", trackorType.getAutoKey());
        assertHelper.AssertText("autokeyStartAt", trackorType.getAutoKeyStartAt());
        assertHelper.AssertSelect("lbOwner1", trackorType.getOwner1());
        assertHelper.AssertRadioPsSelector("Field1", "btnField1", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getField1(), 1L, true);
        assertHelper.AssertSelect("lbSeparator1", trackorType.getSeparator1());
        assertHelper.AssertSelect("lbOwner2", trackorType.getOwner2());
        assertHelper.AssertRadioPsSelector("Field2", "btnField2", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getField2(), 1L, true);
        assertHelper.AssertSelect("lbSeparator2", trackorType.getSeparator2());
        assertHelper.AssertText("staticText3", trackorType.getStaticText3());
        assertHelper.AssertSelect("lbSeparator3", trackorType.getSeparator3());
        assertHelper.AssertSelect("lbDigits", trackorType.getDigits4());
        assertHelper.AssertSelect("lbUnique", trackorType.getUniqueAcross4());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRolePrivs(List<String> secGroupsRead, List<String> secGroupsEdit, List<String> secGroupsAdd, List<String> secGroupsDelete, List<String> secGroupsNone) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tab.goToTab(3L); //Role Privs
        wait.waitGridLoad(3L, 3L);
        gridHelper.checkAssignmentGridColumn(3L, 1L, secGroupsRead);
        gridHelper.checkAssignmentGridColumn(3L, 2L, secGroupsEdit);
        gridHelper.checkAssignmentGridColumn(3L, 3L, secGroupsAdd);
        gridHelper.checkAssignmentGridColumn(3L, 4L, secGroupsDelete);
        gridHelper.checkAssignmentGridColumn(3L, 5L, secGroupsNone);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRoleRestrictions(List<String> rolesRest, String roleRest) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tab.goToTab(4L); //Role Restrictions
        wait.waitGridLoad(4L, 4L);
        gridHelper.checkPrivilegieGridColumn(4L, 1L, rolesRest, roleRest);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, TrackorType trackorType) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Trackor-type Name"), trackorType.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Trackor Label"), trackorType.getLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "\"Item ID\" Label"), trackorType.getLabelItemId());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Prefix Label"), trackorType.getLabelPrefix());
        gridVals.put(js.getColumnIndexByLabel(gridId, "\"My Items\" Label"), trackorType.getLabelMyItems());
        gridVals.put(js.getColumnIndexByLabel(gridId, "\"Class\" Label"), trackorType.getLabelClass());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Is ePM User?"), trackorType.getUser());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Can be a Template"), trackorType.getTemplate());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
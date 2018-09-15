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
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.Grid;
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
    private AssertElement assertElement;

    @Resource
    private Grid grid;

    @Resource
    private Js js;

    @Resource
    private Tab tab;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Checkbox checkbox;

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

        if ((trackorType.getClone().equals("YES") && !checkbox.isCheckedByName("cloningAllowed"))
                || (trackorType.getClone().equals("NO") && checkbox.isCheckedByName("cloningAllowed"))) {
            checkbox.clickByName("cloningAllowed");
        }
        if ((trackorType.getTemplate().equals("YES") && !checkbox.isCheckedByName("template"))
                || (trackorType.getTemplate().equals("NO") && checkbox.isCheckedByName("template"))) {
            checkbox.clickByName("template");
        }
        if ((trackorType.getUser().equals("YES") && !checkbox.isCheckedByName("user"))
                || (trackorType.getUser().equals("NO") && checkbox.isCheckedByName("user"))) {
            checkbox.clickByName("user");
        }
        if ((trackorType.getEfileContainer().equals("YES") && !checkbox.isCheckedByName("efileContainer"))
                || (trackorType.getEfileContainer().equals("NO") && checkbox.isCheckedByName("efileContainer"))) {
            checkbox.clickByName("efileContainer");
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

        assertElement.AssertText("trackorType", trackorType.getName());
        assertElement.AssertText("appletLabel", trackorType.getLabel());
        assertElement.AssertText("trackoridLabel", trackorType.getLabelItemId());
        assertElement.AssertText("trackorClassLabel", trackorType.getLabelClass());
        assertElement.AssertText("prefixLabel", trackorType.getLabelPrefix());
        assertElement.AssertText("myTrackorsLabel", trackorType.getLabelMyItems());
        assertElement.AssertRadioPsSelector("objDisplayField", "btnobjDisplayField", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getAliasField(), 1L, true);
        assertElement.AssertCheckboxPsSelector("xsFieldsStr", "btnxsFieldsStr", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getAutoFilterFields(), 1L, true);
        assertElement.AssertSelect("wpModeId", trackorType.getLimitWp());
        assertElement.AssertSelect("componentsPackageId", trackorType.getCompPack());
        assertElement.AssertCheckBoxNew("cloningAllowed", trackorType.getClone());
        assertElement.AssertCheckBoxNew("template", trackorType.getTemplate());
        assertElement.AssertCheckBoxNew("user", trackorType.getUser());
        assertElement.AssertCheckBoxNew("efileContainer", trackorType.getEfileContainer());

        tab.goToTab(2L); // Key Generation
        assertElement.AssertCheckBoxNew("autokey", trackorType.getAutoKey());
        assertElement.AssertText("autokeyStartAt", trackorType.getAutoKeyStartAt());
        assertElement.AssertSelect("lbOwner1", trackorType.getOwner1());
        assertElement.AssertRadioPsSelector("Field1", "btnField1", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getField1(), 1L, true);
        assertElement.AssertSelect("lbSeparator1", trackorType.getSeparator1());
        assertElement.AssertSelect("lbOwner2", trackorType.getOwner2());
        assertElement.AssertRadioPsSelector("Field2", "btnField2", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getField2(), 1L, true);
        assertElement.AssertSelect("lbSeparator2", trackorType.getSeparator2());
        assertElement.AssertText("staticText3", trackorType.getStaticText3());
        assertElement.AssertSelect("lbSeparator3", trackorType.getSeparator3());
        assertElement.AssertSelect("lbDigits", trackorType.getDigits4());
        assertElement.AssertSelect("lbUnique", trackorType.getUniqueAcross4());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRolePrivs(List<String> secGroupsRead, List<String> secGroupsEdit, List<String> secGroupsAdd, List<String> secGroupsDelete, List<String> secGroupsNone) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tab.goToTab(3L); //Role Privs
        wait.waitGridLoad(3L, 3L);
        grid.checkAssignmentGridColumn(3L, 1L, secGroupsRead);
        grid.checkAssignmentGridColumn(3L, 2L, secGroupsEdit);
        grid.checkAssignmentGridColumn(3L, 3L, secGroupsAdd);
        grid.checkAssignmentGridColumn(3L, 4L, secGroupsDelete);
        grid.checkAssignmentGridColumn(3L, 5L, secGroupsNone);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRoleRestrictions(List<String> rolesRest, String roleRest) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tab.goToTab(4L); //Role Restrictions
        wait.waitGridLoad(4L, 4L);
        grid.checkPrivilegieGridColumn(4L, 1L, rolesRest, roleRest);

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

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
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
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.tab.Tab;
import com.onevizion.uitest.api.vo.entity.TrackorType;

@Component
public class EntityTrackorType {

    private static final String NAME = "trackorType";
    private static final String LABEL = "appletLabel";
    private static final String LABEL_ITEM = "trackoridLabel";
    private static final String LABEL_CLASS = "trackorClassLabel";
    private static final String LABEL_PREFIX = "prefixLabel";
    private static final String LABEL_MY_ITEMS = "myTrackorsLabel";
    private static final String LIMIT_WP = "wpModeId";
    private static final String COMP_PACKAGE = "componentsPackageId";
    private static final String CLONE = "cloningAllowed";
    private static final String TEMPLATE = "template";
    private static final String USER = "user";
    private static final String EFILE_CONTAINER = "efileContainer";

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Grid grid;

    @Resource
    private Grid2 grid2;

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

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(trackorType.getName());
        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(trackorType.getLabel());
        seleniumSettings.getWebDriver().findElement(By.name(LABEL_ITEM)).sendKeys(trackorType.getLabelItemId());
        seleniumSettings.getWebDriver().findElement(By.name(LABEL_CLASS)).sendKeys(trackorType.getLabelClass());
        seleniumSettings.getWebDriver().findElement(By.name(LABEL_PREFIX)).sendKeys(trackorType.getLabelPrefix());
        seleniumSettings.getWebDriver().findElement(By.name(LABEL_MY_ITEMS)).sendKeys(trackorType.getLabelMyItems());

        (new Select(seleniumSettings.getWebDriver().findElement(By.name(LIMIT_WP)))).selectByVisibleText(trackorType.getLimitWp());
        (new Select(seleniumSettings.getWebDriver().findElement(By.name(COMP_PACKAGE)))).selectByVisibleText(trackorType.getCompPack());

        if ((trackorType.getClone().equals("YES") && !checkbox.isCheckedByName(CLONE))
                || (trackorType.getClone().equals("NO") && checkbox.isCheckedByName(CLONE))) {
            checkbox.clickByName(CLONE);
        }
        if ((trackorType.getTemplate().equals("YES") && !checkbox.isCheckedByName(TEMPLATE))
                || (trackorType.getTemplate().equals("NO") && checkbox.isCheckedByName(TEMPLATE))) {
            checkbox.clickByName(TEMPLATE);
        }
        if ((trackorType.getUser().equals("YES") && !checkbox.isCheckedByName(USER))
                || (trackorType.getUser().equals("NO") && checkbox.isCheckedByName(USER))) {
            checkbox.clickByName(USER);
        }
        if ((trackorType.getEfileContainer().equals("YES") && !checkbox.isCheckedByName(EFILE_CONTAINER))
                || (trackorType.getEfileContainer().equals("NO") && checkbox.isCheckedByName(EFILE_CONTAINER))) {
            checkbox.clickByName(EFILE_CONTAINER);
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(TrackorType trackorType) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        
        
        
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testOnForm(TrackorType trackorType) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertText(NAME, trackorType.getName());
        assertElement.assertText(LABEL, trackorType.getLabel());
        assertElement.assertText(LABEL_ITEM, trackorType.getLabelItemId());
        assertElement.assertText(LABEL_CLASS, trackorType.getLabelClass());
        assertElement.assertText(LABEL_PREFIX, trackorType.getLabelPrefix());
        assertElement.assertText(LABEL_MY_ITEMS, trackorType.getLabelMyItems());
        assertElement.assertRadioPsSelector("objDisplayField", "btnobjDisplayField", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getAliasField(), 1L, true);
        assertElement.assertCheckboxPsSelector("xsFieldsStr", "btnxsFieldsStr", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getAutoFilterFields(), 1L, true);
        assertElement.assertSelect(LIMIT_WP, trackorType.getLimitWp());
        assertElement.assertSelect(COMP_PACKAGE, trackorType.getCompPack());
        assertElement.assertCheckbox(CLONE, trackorType.getClone());
        assertElement.assertCheckbox(TEMPLATE, trackorType.getTemplate());
        assertElement.assertCheckbox(USER, trackorType.getUser());
        assertElement.assertCheckbox(EFILE_CONTAINER, trackorType.getEfileContainer());

        tab.goToTab(2L); // Key Generation
        assertElement.assertCheckbox("autokey", trackorType.getAutoKey());
        assertElement.assertText("autokeyStartAt", trackorType.getAutoKeyStartAt());
        assertElement.assertSelect("lbOwner1", trackorType.getOwner1());
        assertElement.assertRadioPsSelector("Field1", "btnField1", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getField1(), 1L, true);
        assertElement.assertSelect("lbSeparator1", trackorType.getSeparator1());
        assertElement.assertSelect("lbOwner2", trackorType.getOwner2());
        assertElement.assertRadioPsSelector("Field2", "btnField2", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getField2(), 1L, true);
        assertElement.assertSelect("lbSeparator2", trackorType.getSeparator2());
        assertElement.assertText("staticText3", trackorType.getStaticText3());
        assertElement.assertSelect("lbSeparator3", trackorType.getSeparator3());
        assertElement.assertSelect("lbDigits", trackorType.getDigits4());
        assertElement.assertSelect("lbUnique", trackorType.getUniqueAcross4());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRolePrivs(List<String> secGroupsRead, List<String> secGroupsEdit, List<String> secGroupsAdd, List<String> secGroupsDelete, List<String> secGroupsNone) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tab.goToTab(3L); //Role Privs
        grid2.waitLoad(3L);
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
        grid2.waitLoad(4L);
        grid.checkPrivilegieGridColumn(4L, 1L, rolesRest, roleRest);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, TrackorType trackorType) {
        Map<Long, String> gridVals = new HashMap<>();

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
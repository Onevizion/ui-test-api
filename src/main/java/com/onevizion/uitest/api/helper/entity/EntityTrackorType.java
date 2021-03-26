package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.List;
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
    private static final String CLONE = "cloningAllowed";
    private static final String TEMPLATE = "template";
    private static final String USER = "user";
    private static final String EFILE_CONTAINER = "efileContainer";
    private static final String COMMENTS = "supportChats";

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Form form;

    @Autowired
    private Grid grid;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private Js js;

    @Autowired
    private Tab tab;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Checkbox checkbox;

    @Autowired
    private Element element;

    public void add(TrackorType trackorType) {
        form.openAdd();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(trackorType.getName());
        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(trackorType.getLabel());
        seleniumSettings.getWebDriver().findElement(By.name(LABEL_ITEM)).sendKeys(trackorType.getLabelItemId());
        seleniumSettings.getWebDriver().findElement(By.name(LABEL_CLASS)).sendKeys(trackorType.getLabelClass());
        seleniumSettings.getWebDriver().findElement(By.name(LABEL_PREFIX)).sendKeys(trackorType.getLabelPrefix());
        seleniumSettings.getWebDriver().findElement(By.name(LABEL_MY_ITEMS)).sendKeys(trackorType.getLabelMyItems());

        (new Select(seleniumSettings.getWebDriver().findElement(By.name(LIMIT_WP)))).selectByVisibleText(trackorType.getLimitWp());

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
        if ((trackorType.getComments().equals("YES") && !checkbox.isCheckedByName(COMMENTS))
                || (trackorType.getComments().equals("NO") && checkbox.isCheckedByName(COMMENTS))) {
            checkbox.clickByName(COMMENTS);
        }

        element.clickById(AbstractSeleniumCore.BUTTON_APPLY_ID);
        wait.waitReloadForm("reloaded=1");
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tab.goToTab(6); //Components Package
        grid2.waitLoad(6L);
        grid.clearAssignmentGridColumn2(6L, 0L);
        grid.selectAssignmentGridColumn2New(6L, 0, 2, trackorType.getPackages());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(TrackorType trackorType) {
        form.openEdit();

        

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testOnForm(TrackorType trackorType) {
        form.openEdit();

        assertElement.assertText(NAME, trackorType.getName());
        assertElement.assertText(LABEL, trackorType.getLabel());
        assertElement.assertText(LABEL_ITEM, trackorType.getLabelItemId());
        assertElement.assertText(LABEL_CLASS, trackorType.getLabelClass());
        assertElement.assertText(LABEL_PREFIX, trackorType.getLabelPrefix());
        assertElement.assertText(LABEL_MY_ITEMS, trackorType.getLabelMyItems());
        assertElement.assertRadioPsSelector("objDisplayField", "btnobjDisplayField", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getAliasField(), 1L, true);
        assertElement.assertSelect(LIMIT_WP, trackorType.getLimitWp());
        assertElement.assertCheckbox(CLONE, trackorType.getClone());
        assertElement.assertCheckbox(TEMPLATE, trackorType.getTemplate());
        assertElement.assertCheckbox(USER, trackorType.getUser());
        assertElement.assertCheckbox(EFILE_CONTAINER, trackorType.getEfileContainer());
        assertElement.assertCheckbox(COMMENTS, trackorType.getComments());

        tab.goToTab(2); // Key Generation
        assertElement.assertCheckbox("autokey", trackorType.getAutoKey());
        assertElement.assertText("autokeyStartAt", trackorType.getAutoKeyStartAt());
        assertElement.assertSelect("lbOwner1", trackorType.getOwner1());
        assertElement.assertRadioPsSelector("Field1", "btnField1", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getField1(), 1L, true);
        assertElement.assertText("txtSeparator1", trackorType.getSeparator1());
        assertElement.assertSelect("lbOwner2", trackorType.getOwner2());
        assertElement.assertRadioPsSelector("Field2", "btnField2", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorType.getField2(), 1L, true);
        assertElement.assertText("txtSeparator2", trackorType.getSeparator2());
        assertElement.assertText("staticText3", trackorType.getStaticText3());
        assertElement.assertText("txtSeparator3", trackorType.getSeparator3());
        assertElement.assertSelect("lbDigits", trackorType.getDigits4());
        assertElement.assertSelect("lbUnique", trackorType.getUniqueAcross4());

        tab.goToTab(6); //Components Package
        grid2.waitLoad(6L);
        grid.checkAssignmentGridColumn2New(6L, 0, 2, trackorType.getPackages());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRolePrivs(List<String> secGroupsRead, List<String> secGroupsEdit, List<String> secGroupsAdd, List<String> secGroupsDelete, List<String> secGroupsNone) {
        form.openEdit();

        tab.goToTab(3); //Role Privs
        grid2.waitLoad(3L);
        grid.checkAssignmentGridColumn(3L, 1, secGroupsRead);
        grid.checkAssignmentGridColumn(3L, 2, secGroupsEdit);
        grid.checkAssignmentGridColumn(3L, 3, secGroupsAdd);
        grid.checkAssignmentGridColumn(3L, 4, secGroupsDelete);
        grid.checkAssignmentGridColumn(3L, 5, secGroupsNone);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRoleRestrictions(List<String> rolesRest, String roleRest) {
        form.openEdit();

        tab.goToTab(4); //Role Restrictions
        grid2.waitLoad(4L);
        grid.checkPrivilegieGridColumn(4L, 1, rolesRest, roleRest);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, int rowIndex, TrackorType trackorType) {
        Map<Integer, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Trackor-type Name"), trackorType.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Trackor Label"), trackorType.getLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "\"Item ID\" Label"), trackorType.getLabelItemId());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Prefix Label"), trackorType.getLabelPrefix());
        gridVals.put(js.getColumnIndexByLabel(gridId, "\"My Items\" Label"), trackorType.getLabelMyItems());
        gridVals.put(js.getColumnIndexByLabel(gridId, "\"Class\" Label"), trackorType.getLabelClass());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Is ePM User?"), trackorType.getUser());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Can be a Template"), trackorType.getTemplate());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Enable Chat"), trackorType.getComments());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
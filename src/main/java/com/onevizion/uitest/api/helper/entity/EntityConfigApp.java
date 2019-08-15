package com.onevizion.uitest.api.helper.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.DualListbox;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.HtmlSelect;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Listbox;
import com.onevizion.uitest.api.helper.PsSelector;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.tab.Tab;
import com.onevizion.uitest.api.helper.view.View;
import com.onevizion.uitest.api.vo.ListboxElement;
import com.onevizion.uitest.api.vo.entity.ConfigApp;

@Component
public class EntityConfigApp {

    private static final String NAME = "configAppName";
    private static final String LABEL = "appLabelText";
    private static final String TRACKOR_TYPE = "ttid";
    private static final String RELATED_TRACKOR_TYPE = "wpTtid";
    private static final String ICON = "icon";
    private static final String ICON_BUTTON = "btnicon";

    private static final String BUTTON_ADD_TAB = "addItem";
    private static final String BUTTON_REMOVE_ALL_TABS = "removeAllItems";

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private View view;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private PsSelector psSelector;

    @Resource
    private Element element;

    @Resource
    private Tab tab;

    @Resource
    private DualListbox dualListbox;

    @Resource
    private Listbox listbox;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Js js;

    @Resource
    private Grid grid;

    @Resource
    private Grid2 grid2;

    @Resource
    private HtmlSelect htmlSelect;

    public void add(ConfigApp configApp) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        view.waitLeftListBoxReady();
        view.waitRightListBoxReady();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(configApp.getName());
        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(configApp.getLabel());
        new Select(seleniumSettings.getWebDriver().findElement(By.name(TRACKOR_TYPE))).selectByVisibleText(configApp.getTrackorType());
        if (isCanHaveRelatedTrackorType(configApp)) {
            new Select(seleniumSettings.getWebDriver().findElement(By.name(RELATED_TRACKOR_TYPE))).selectByVisibleText(configApp.getRelatedTrackorType());
        }
        psSelector.selectSpecificValue(By.name(ICON_BUTTON), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configApp.getIcon(), 1L);

        element.clickById(AbstractSeleniumCore.BUTTON_APPLY_ID);
        wait.waitReloadForm("reloaded=1");
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        view.waitLeftListBoxReady();
        view.waitRightListBoxReady();

        tab.goToTab(2L); //Tabs
        for (String tab : configApp.getTabs()) {
            dualListbox.addValueByTextNew(BUTTON_ADD_TAB, tab);
        }

        tab.goToTab(3L); //Role Privs
        grid2.waitLoad(3L);
        grid.clearAssignmentGridColumn(3L, 1L);
        grid.clearAssignmentGridColumn(3L, 2L);
        grid.clearAssignmentGridColumn(3L, 3L);
        grid.clearAssignmentGridColumn(3L, 4L);
        grid.clearAssignmentGridColumn(3L, 5L);
        grid.selectAssignmentGridColumnNew(3L, 1L, 0L, configApp.getRoles(), "R");
        grid.selectAssignmentGridColumnNew(3L, 2L, 0L, configApp.getRoles(), "E");
        grid.selectAssignmentGridColumnNew(3L, 3L, 0L, configApp.getRoles(), "A");
        grid.selectAssignmentGridColumnNew(3L, 4L, 0L, configApp.getRoles(), "D");
        grid.selectAssignmentGridColumnNew(3L, 5L, 0L, configApp.getRoles(), "N");

        if (isDynamicTrackorType(configApp)) {
            tab.goToTab(4L); //Trackor Classes
            grid2.waitLoad(4L);
            grid.clearAssignmentGridColumn2(4L, 0L);
            grid.selectAssignmentGridColumn2New(4L, 0L, 2L, configApp.getClasses());
        }

        if (isDynamicTrackorType(configApp)) {
            tab.goToTab(5L); //ePM Pages
            grid2.waitLoad(5L);
            grid.clearAssignmentGridColumn2(5L, 0L);
            grid.selectAssignmentGridColumn2New(5L, 0L, 2L, configApp.getPages());
        }

        Long packagesTabIndex;
        if (isDynamicTrackorType(configApp)) {
            packagesTabIndex = 6L;
        } else {
            packagesTabIndex = 4L;
        }
        tab.goToTab(packagesTabIndex); //Components Package
        grid2.waitLoad(packagesTabIndex);
        grid.clearAssignmentGridColumn2(packagesTabIndex, 0L);
        grid.selectAssignmentGridColumn2New(packagesTabIndex, 0L, 2L, configApp.getPackages());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(ConfigApp configApp) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        view.waitLeftListBoxReady();
        view.waitRightListBoxReady();

        tab.goToTab(2L); //Tabs
        if (seleniumSettings.getWebDriver().findElement(By.id(BUTTON_REMOVE_ALL_TABS)).isEnabled()) {
            seleniumSettings.getWebDriver().findElement(By.id(BUTTON_REMOVE_ALL_TABS)).click();
        }
        List<String> rightApps = new ArrayList<>();
        List<WebElement> rightColumns = view.getRightColumns();
        for (WebElement rightColumn : rightColumns) {
            rightApps.add(htmlSelect.getOptionTextNew(rightColumn));
        }
        for (String tab : configApp.getTabs()) {
            if (!rightApps.contains(tab)) {
                dualListbox.addValueByTextNew(BUTTON_ADD_TAB, tab);
            }
        }

        tab.goToTab(3L); //Role Privs
        grid2.waitLoad(3L);
        grid.clearAssignmentGridColumn(3L, 1L);
        grid.clearAssignmentGridColumn(3L, 2L);
        if (isDynamicTrackorType(configApp) && isMaster(configApp)) {
            grid.clearAssignmentGridColumn(3L, 3L);
            grid.clearAssignmentGridColumn(3L, 4L);
        }
        grid.clearAssignmentGridColumn(3L, 5L);
        grid.selectAssignmentGridColumnNew(3L, 1L, 0L, configApp.getRoles(), "R");
        grid.selectAssignmentGridColumnNew(3L, 2L, 0L, configApp.getRoles(), "E");
        if (isDynamicTrackorType(configApp) && isMaster(configApp)) {
            grid.selectAssignmentGridColumnNew(3L, 3L, 0L, configApp.getRoles(), "A");
            grid.selectAssignmentGridColumnNew(3L, 4L, 0L, configApp.getRoles(), "D");
        }
        grid.selectAssignmentGridColumnNew(3L, 5L, 0L, configApp.getRoles(), "N");

        if (isDynamicTrackorType(configApp)) {
            tab.goToTab(4L); //Trackor Classes
            grid2.waitLoad(4L);
            grid.clearAssignmentGridColumn2(4L, 0L);
            grid.selectAssignmentGridColumn2New(4L, 0L, 2L, configApp.getClasses());
        }

        if (isDynamicTrackorType(configApp)) {
            tab.goToTab(5L); //ePM Pages
            grid2.waitLoad(5L);
            grid.clearAssignmentGridColumn2(5L, 0L);
            grid.selectAssignmentGridColumn2New(5L, 0L, 2L, configApp.getPages());
        }

        Long packagesTabIndex;
        if (isDynamicTrackorType(configApp)) {
            packagesTabIndex = 6L;
        } else {
            packagesTabIndex = 4L;
        }
        tab.goToTab(packagesTabIndex); //Components Package
        grid2.waitLoad(packagesTabIndex);
        grid.clearAssignmentGridColumn2(packagesTabIndex, 0L);
        grid.selectAssignmentGridColumn2New(packagesTabIndex, 0L, 2L, configApp.getPackages());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testOnForm(ConfigApp configApp) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        view.waitLeftListBoxReady();
        view.waitRightListBoxReady();

        assertElement.assertText(NAME, configApp.getName());
        assertElement.assertText(LABEL, configApp.getLabel());
        assertElement.assertSelect(TRACKOR_TYPE, configApp.getTrackorType());
        if (isCanHaveRelatedTrackorType(configApp)) {
            assertElement.assertSelect(RELATED_TRACKOR_TYPE, configApp.getRelatedTrackorType());
        }
        assertElement.assertRadioPsSelector(ICON, ICON_BUTTON, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx(), configApp.getIcon(), 1L, true);

        tab.goToTab(2L); //Tabs
        List<ListboxElement> rightTabs = listbox.getElements("rightListBox");
        listbox.checkElementsCount(rightTabs, configApp.getTabs().size());
        for (int i = 0; i < configApp.getTabs().size(); i++) {
            listbox.checkElementByLabel(rightTabs, i + 1, configApp.getTabs().get(i));
        }

        tab.goToTab(3L); //Role Privs
        grid2.waitLoad(3L);
        grid.checkAssignmentGridColumnNew(3L, 1L, 0L, configApp.getRoles(), "R");
        grid.checkAssignmentGridColumnNew(3L, 2L, 0L, configApp.getRoles(), "E");
        if (isDynamicTrackorType(configApp) && isMaster(configApp)) {
            grid.checkAssignmentGridColumnNew(3L, 3L, 0L, configApp.getRoles(), "A");
            grid.checkAssignmentGridColumnNew(3L, 4L, 0L, configApp.getRoles(), "D");
        }
        grid.checkAssignmentGridColumnNew(3L, 5L, 0L, configApp.getRoles(), "N");

        if (isDynamicTrackorType(configApp)) {
            tab.goToTab(4L); //Trackor Classes
            grid2.waitLoad(4L);
            grid.checkAssignmentGridColumn2New(4L, 0L, 2L, configApp.getClasses());
        }

        if (isDynamicTrackorType(configApp)) {
            tab.goToTab(5L); //ePM Pages
            grid2.waitLoad(5L);
            grid.checkAssignmentGridColumn2New(5L, 0L, 2L, configApp.getPages());
        }

        Long packagesTabIndex;
        if (isDynamicTrackorType(configApp)) {
            packagesTabIndex = 6L;
        } else {
            packagesTabIndex = 4L;
        }
        tab.goToTab(packagesTabIndex); //Components Package
        grid2.waitLoad(packagesTabIndex);
        grid.checkAssignmentGridColumn2New(packagesTabIndex, 0L, 2L, configApp.getPackages());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, ConfigApp configApp) {
        Map<Long, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Trackor Type"), configApp.getTrackorType());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Applet Name"), configApp.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Applet Label"), configApp.getLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Related Trackor Type"), configApp.getRelatedTrackorType());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Is Master"), configApp.getIsMaster());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

    private boolean isDynamicTrackorType(ConfigApp configApp) {
        return !"Tasks".equals(configApp.getTrackorType()) && !"Work Plan".equals(configApp.getTrackorType()) && !"Workflow".equals(configApp.getTrackorType());
    }

    private boolean isMaster(ConfigApp configApp) {
        return "YES".equals(configApp.getIsMaster());
    }

    private boolean isCanHaveRelatedTrackorType(ConfigApp configApp) {
        return "Tasks".equals(configApp.getTrackorType()) || "Work Plan".equals(configApp.getTrackorType());
    }

}
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
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Listbox;
import com.onevizion.uitest.api.helper.Selector;
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

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private View view;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Selector selector;

    @Autowired
    private Element element;

    @Autowired
    private Tab tab;

    @Autowired
    private Listbox listbox;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private Grid2 grid2;

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
        selector.selectRadio(By.name(ICON_BUTTON), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1, configApp.getIcon(), 1L);

        element.clickById(AbstractSeleniumCore.BUTTON_APPLY_ID);
        wait.waitReloadForm("reloaded=1");
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        view.waitLeftListBoxReady();
        view.waitRightListBoxReady();

        tab.goToTab(2); //Tabs
        List<ListboxElement> leftTabs = listbox.getElements("leftListBox");
        for (String tab : configApp.getTabs()) {
            listbox.moveElementByLabel(leftTabs, tab, BUTTON_ADD_TAB);
        }

        tab.goToTab(3); //Role Privs
        grid2.waitLoad(3L);
        grid.clearAssignmentGridColumn(3L, 1);
        grid.clearAssignmentGridColumn(3L, 2);
        grid.clearAssignmentGridColumn(3L, 3);
        grid.clearAssignmentGridColumn(3L, 4);
        grid.clearAssignmentGridColumn(3L, 5);
        grid.selectAssignmentGridColumnNew(3L, 1, 0, configApp.getRoles(), "R");
        grid.selectAssignmentGridColumnNew(3L, 2, 0, configApp.getRoles(), "E");
        grid.selectAssignmentGridColumnNew(3L, 3, 0, configApp.getRoles(), "A");
        grid.selectAssignmentGridColumnNew(3L, 4, 0, configApp.getRoles(), "D");
        grid.selectAssignmentGridColumnNew(3L, 5, 0, configApp.getRoles(), "N");

        if (isDynamicTrackorType(configApp)) {
            tab.goToTab(4); //Trackor Classes
            grid2.waitLoad(4L);
            grid.clearAssignmentGridColumn2(4L, 0L);
            grid.selectAssignmentGridColumn2New(4L, 0, 2, configApp.getClasses());
        }

        if (isDynamicTrackorType(configApp)) {
            tab.goToTab(5); //ePM Pages
            grid2.waitLoad(5L);
            grid.clearAssignmentGridColumn2(5L, 0L);
            grid.selectAssignmentGridColumn2New(5L, 0, 2, configApp.getPages());
        }

        Long packagesTabIndex;
        if (isDynamicTrackorType(configApp)) {
            packagesTabIndex = 6L;
        } else {
            packagesTabIndex = 4L;
        }
        tab.goToTab(packagesTabIndex.intValue()); //Components Package
        grid2.waitLoad(packagesTabIndex);
        grid.clearAssignmentGridColumn2(packagesTabIndex, 0L);
        grid.selectAssignmentGridColumn2New(packagesTabIndex, 0, 2, configApp.getPackages());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(ConfigApp configApp) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        view.waitLeftListBoxReady();
        view.waitRightListBoxReady();

        tab.goToTab(2); //Tabs
        if (seleniumSettings.getWebDriver().findElement(By.id(BUTTON_REMOVE_ALL_TABS)).isEnabled()) {
            seleniumSettings.getWebDriver().findElement(By.id(BUTTON_REMOVE_ALL_TABS)).click();
        }
        List<ListboxElement> rightTabs = listbox.getElements("rightListBox");
        List<ListboxElement> leftTabs = listbox.getElements("leftListBox");
        for (String tab : configApp.getTabs()) {
            boolean alreadyInRightList = rightTabs.stream().anyMatch(p -> p.getLabel().equals(tab));
            if (!alreadyInRightList) {
                listbox.moveElementByLabel(leftTabs, tab, BUTTON_ADD_TAB);
            }
        }

        tab.goToTab(3); //Role Privs
        grid2.waitLoad(3L);
        grid.clearAssignmentGridColumn(3L, 1);
        grid.clearAssignmentGridColumn(3L, 2);
        if (isDynamicTrackorType(configApp) && isMaster(configApp)) {
            grid.clearAssignmentGridColumn(3L, 3);
            grid.clearAssignmentGridColumn(3L, 4);
        }
        grid.clearAssignmentGridColumn(3L, 5);
        grid.selectAssignmentGridColumnNew(3L, 1, 0, configApp.getRoles(), "R");
        grid.selectAssignmentGridColumnNew(3L, 2, 0, configApp.getRoles(), "E");
        if (isDynamicTrackorType(configApp) && isMaster(configApp)) {
            grid.selectAssignmentGridColumnNew(3L, 3, 0, configApp.getRoles(), "A");
            grid.selectAssignmentGridColumnNew(3L, 4, 0, configApp.getRoles(), "D");
        }
        grid.selectAssignmentGridColumnNew(3L, 5, 0, configApp.getRoles(), "N");

        if (isDynamicTrackorType(configApp)) {
            tab.goToTab(4); //Trackor Classes
            grid2.waitLoad(4L);
            grid.clearAssignmentGridColumn2(4L, 0L);
            grid.selectAssignmentGridColumn2New(4L, 0, 2, configApp.getClasses());
        }

        if (isDynamicTrackorType(configApp)) {
            tab.goToTab(5); //ePM Pages
            grid2.waitLoad(5L);
            grid.clearAssignmentGridColumn2(5L, 0L);
            grid.selectAssignmentGridColumn2New(5L, 0, 2, configApp.getPages());
        }

        Long packagesTabIndex;
        if (isDynamicTrackorType(configApp)) {
            packagesTabIndex = 6L;
        } else {
            packagesTabIndex = 4L;
        }
        tab.goToTab(packagesTabIndex.intValue()); //Components Package
        grid2.waitLoad(packagesTabIndex);
        grid.clearAssignmentGridColumn2(packagesTabIndex, 0L);
        grid.selectAssignmentGridColumn2New(packagesTabIndex, 0, 2, configApp.getPackages());

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
        assertElement.assertText(ICON, configApp.getIcon());

        tab.goToTab(2); //Tabs
        List<ListboxElement> rightTabs = listbox.getElements("rightListBox");
        listbox.checkElementsCount(rightTabs, configApp.getTabs().size());
        for (int i = 0; i < configApp.getTabs().size(); i++) {
            listbox.checkElementByLabel(rightTabs, i + 1, configApp.getTabs().get(i));
        }

        tab.goToTab(3); //Role Privs
        grid2.waitLoad(3L);
        grid.checkAssignmentGridColumnNew(3L, 1, 0, configApp.getRoles(), "R");
        grid.checkAssignmentGridColumnNew(3L, 2, 0, configApp.getRoles(), "E");
        if (isDynamicTrackorType(configApp) && isMaster(configApp)) {
            grid.checkAssignmentGridColumnNew(3L, 3, 0, configApp.getRoles(), "A");
            grid.checkAssignmentGridColumnNew(3L, 4, 0, configApp.getRoles(), "D");
        }
        grid.checkAssignmentGridColumnNew(3L, 5, 0, configApp.getRoles(), "N");

        if (isDynamicTrackorType(configApp)) {
            tab.goToTab(4); //Trackor Classes
            grid2.waitLoad(4L);
            grid.checkAssignmentGridColumn2New(4L, 0, 2, configApp.getClasses());
        }

        if (isDynamicTrackorType(configApp)) {
            tab.goToTab(5); //ePM Pages
            grid2.waitLoad(5L);
            grid.checkAssignmentGridColumn2New(5L, 0, 2, configApp.getPages());
        }

        Long packagesTabIndex;
        if (isDynamicTrackorType(configApp)) {
            packagesTabIndex = 6L;
        } else {
            packagesTabIndex = 4L;
        }
        tab.goToTab(packagesTabIndex.intValue()); //Components Package
        grid2.waitLoad(packagesTabIndex);
        grid.checkAssignmentGridColumn2New(packagesTabIndex, 0, 2, configApp.getPackages());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, ConfigApp configApp) {
        Map<Integer, String> gridVals = new HashMap<>();

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
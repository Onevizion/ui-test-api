package com.onevizion.uitest.api.helper.entity;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.PsSelector;
import com.onevizion.uitest.api.helper.Tab;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.tree.Tree;
import com.onevizion.uitest.api.vo.entity.TrackorTreeItem;

@Component
public class EntityTrackorTreeItem {

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private AssertElement assertElement;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private PsSelector psSelector;

    @Resource
    private Checkbox checkbox;

    @Resource
    private Tab tab;

    @Resource
    private Grid grid;

    @Resource
    private Tree tree;

    public void add(TrackorTreeItem trackorTreeItem) {
        tree.selectParentTreeItem(AbstractSeleniumCore.getTreeIdx(), "-1", trackorTreeItem);

        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        psSelector.selectSpecificValue(By.name("btntrackorType"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, trackorTreeItem.getTrackorType(), 1L);

        new Select(seleniumSettings.getWebDriver().findElement(By.name("cardinalityId"))).selectByVisibleText(trackorTreeItem.getCardinality());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("uniqueByTtId"))).selectByVisibleText(trackorTreeItem.getUniqueBy());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("colorId"))).selectByVisibleText(trackorTreeItem.getColor());

        if ((trackorTreeItem.getChildRequiresParent().equals("YES") && !checkbox.isCheckedByName("childRequiresParent"))
                || (trackorTreeItem.getChildRequiresParent().equals("NO") && checkbox.isCheckedByName("childRequiresParent"))) {
            checkbox.clickByName("childRequiresParent");
        }

        if ((trackorTreeItem.getOnParentDeleteCascade().equals("YES") && !checkbox.isCheckedByName("onParentDeleteCascade"))
                || (trackorTreeItem.getOnParentDeleteCascade().equals("NO") && checkbox.isCheckedByName("onParentDeleteCascade"))) {
            checkbox.clickByName("onParentDeleteCascade");
        }

        if ((trackorTreeItem.getLockable().equals("YES") && !checkbox.isCheckedByName("lockable"))
                || (trackorTreeItem.getLockable().equals("NO") && checkbox.isCheckedByName("lockable"))) {
            checkbox.clickByName("lockable");
        }

        if ((trackorTreeItem.getShowAll().equals("YES") && !checkbox.isCheckedByName("showAllInTrcontainer"))
                || (trackorTreeItem.getShowAll().equals("NO") && checkbox.isCheckedByName("showAllInTrcontainer"))) {
            checkbox.clickByName("showAllInTrcontainer");
        }

        window.closeModalAndWaitTreeLoad(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    public void edit(TrackorTreeItem trackorTreeItem) {
        tree.selectTreeItem(AbstractSeleniumCore.getTreeIdx(), "-1", trackorTreeItem);

        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.AssertText("treePath", trackorTreeItem.getTreePath());
        assertElement.AssertText("trackorType", trackorTreeItem.getTrackorType());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("cardinalityId"))).selectByVisibleText(trackorTreeItem.getCardinality());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("uniqueByTtId"))).selectByVisibleText(trackorTreeItem.getUniqueBy());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("colorId"))).selectByVisibleText(trackorTreeItem.getColor());

        if ((trackorTreeItem.getChildRequiresParent().equals("YES") && !checkbox.isCheckedByName("childRequiresParent"))
                || (trackorTreeItem.getChildRequiresParent().equals("NO") && checkbox.isCheckedByName("childRequiresParent"))) {
            checkbox.clickByName("childRequiresParent");
        }

        if ((trackorTreeItem.getOnParentDeleteCascade().equals("YES") && !checkbox.isCheckedByName("onParentDeleteCascade"))
                || (trackorTreeItem.getOnParentDeleteCascade().equals("NO") && checkbox.isCheckedByName("onParentDeleteCascade"))) {
            checkbox.clickByName("onParentDeleteCascade");
        }

        if ((trackorTreeItem.getLockable().equals("YES") && !checkbox.isCheckedByName("lockable"))
                || (trackorTreeItem.getLockable().equals("NO") && checkbox.isCheckedByName("lockable"))) {
            checkbox.clickByName("lockable");
        }

        if ((trackorTreeItem.getShowAll().equals("YES") && !checkbox.isCheckedByName("showAllInTrcontainer"))
                || (trackorTreeItem.getShowAll().equals("NO") && checkbox.isCheckedByName("showAllInTrcontainer"))) {
            checkbox.clickByName("showAllInTrcontainer");
        }

        window.closeModalAndWaitTreeLoad(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    public void testOnForm(TrackorTreeItem trackorTreeItem) {
        tree.selectTreeItem(AbstractSeleniumCore.getTreeIdx(), "-1", trackorTreeItem);

        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.AssertText("treePath", trackorTreeItem.getTreePath());
        assertElement.AssertText("trackorType", trackorTreeItem.getTrackorType());
        assertElement.AssertSelect("cardinalityId", trackorTreeItem.getCardinality());
        assertElement.AssertSelect("uniqueByTtId", trackorTreeItem.getUniqueBy());
        assertElement.AssertSelect("colorId", trackorTreeItem.getColor());
        assertElement.AssertCheckBoxNew("childRequiresParent", trackorTreeItem.getChildRequiresParent());
        assertElement.AssertCheckBoxNew("onParentDeleteCascade", trackorTreeItem.getOnParentDeleteCascade());
        assertElement.AssertCheckBoxNew("lockable", trackorTreeItem.getLockable());
        assertElement.AssertCheckBoxNew("showAllInTrcontainer", trackorTreeItem.getShowAll());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRolePrivs(List<String> rolePrivsRead, List<String> rolePrivsEdit, List<String> rolePrivsAdd, List<String> rolePrivsDelete, List<String> rolePrivsNone) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tab.goToTab(2L); //Role Privs
        wait.waitGridLoad(2L, 2L);
        grid.checkAssignmentGridColumn(2L, 1L, rolePrivsRead);
        grid.checkAssignmentGridColumn(2L, 2L, rolePrivsEdit);
        grid.checkAssignmentGridColumn(2L, 3L, rolePrivsAdd);
        grid.checkAssignmentGridColumn(2L, 4L, rolePrivsDelete);
        grid.checkAssignmentGridColumn(2L, 5L, rolePrivsNone);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRoleLockPrivs(List<String> roleLockPrivsLock, List<String> roleLockPrivsUnlock, List<String> roleLockPrivsNone) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tab.goToTab(3L); //Role Lock Privs
        wait.waitGridLoad(3L, 3L);
        grid.checkAssignmentGridColumn(3L, 1L, roleLockPrivsLock);
        grid.checkAssignmentGridColumn(3L, 2L, roleLockPrivsUnlock);
        grid.checkAssignmentGridColumn(3L, 3L, roleLockPrivsNone);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
package com.onevizion.uitest.api.helper.entity;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.CheckboxHelper;
import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.PsSelectorHelper;
import com.onevizion.uitest.api.helper.TabHelper;
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
    private AssertHelper assertHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private PsSelectorHelper psSelectorHelper;

    @Resource
    private CheckboxHelper checkboxHelper;

    @Resource
    private TabHelper tabHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private Tree tree;

    public void add(TrackorTreeItem trackorTreeItem) {
        tree.selectParentTreeItem(AbstractSeleniumCore.getTreeIdx(), "-1", trackorTreeItem);

        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        psSelectorHelper.selectSpecificValue(By.name("btntrackorType"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, trackorTreeItem.getTrackorType(), 1L);

        new Select(seleniumSettings.getWebDriver().findElement(By.name("cardinalityId"))).selectByVisibleText(trackorTreeItem.getCardinality());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("uniqueByTtId"))).selectByVisibleText(trackorTreeItem.getUniqueBy());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("colorId"))).selectByVisibleText(trackorTreeItem.getColor());

        if ((trackorTreeItem.getChildRequiresParent().equals("YES") && !checkboxHelper.isCheckedByName("childRequiresParent"))
                || (trackorTreeItem.getChildRequiresParent().equals("NO") && checkboxHelper.isCheckedByName("childRequiresParent"))) {
            checkboxHelper.clickByName("childRequiresParent");
        }

        if ((trackorTreeItem.getOnParentDeleteCascade().equals("YES") && !checkboxHelper.isCheckedByName("onParentDeleteCascade"))
                || (trackorTreeItem.getOnParentDeleteCascade().equals("NO") && checkboxHelper.isCheckedByName("onParentDeleteCascade"))) {
            checkboxHelper.clickByName("onParentDeleteCascade");
        }

        if ((trackorTreeItem.getLockable().equals("YES") && !checkboxHelper.isCheckedByName("lockable"))
                || (trackorTreeItem.getLockable().equals("NO") && checkboxHelper.isCheckedByName("lockable"))) {
            checkboxHelper.clickByName("lockable");
        }

        if ((trackorTreeItem.getShowAll().equals("YES") && !checkboxHelper.isCheckedByName("showAllInTrcontainer"))
                || (trackorTreeItem.getShowAll().equals("NO") && checkboxHelper.isCheckedByName("showAllInTrcontainer"))) {
            checkboxHelper.clickByName("showAllInTrcontainer");
        }

        window.closeModalAndWaitTreeLoad(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    public void edit(TrackorTreeItem trackorTreeItem) {
        tree.selectTreeItem(AbstractSeleniumCore.getTreeIdx(), "-1", trackorTreeItem);

        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertHelper.AssertText("treePath", trackorTreeItem.getTreePath());
        assertHelper.AssertText("trackorType", trackorTreeItem.getTrackorType());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("cardinalityId"))).selectByVisibleText(trackorTreeItem.getCardinality());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("uniqueByTtId"))).selectByVisibleText(trackorTreeItem.getUniqueBy());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("colorId"))).selectByVisibleText(trackorTreeItem.getColor());

        if ((trackorTreeItem.getChildRequiresParent().equals("YES") && !checkboxHelper.isCheckedByName("childRequiresParent"))
                || (trackorTreeItem.getChildRequiresParent().equals("NO") && checkboxHelper.isCheckedByName("childRequiresParent"))) {
            checkboxHelper.clickByName("childRequiresParent");
        }

        if ((trackorTreeItem.getOnParentDeleteCascade().equals("YES") && !checkboxHelper.isCheckedByName("onParentDeleteCascade"))
                || (trackorTreeItem.getOnParentDeleteCascade().equals("NO") && checkboxHelper.isCheckedByName("onParentDeleteCascade"))) {
            checkboxHelper.clickByName("onParentDeleteCascade");
        }

        if ((trackorTreeItem.getLockable().equals("YES") && !checkboxHelper.isCheckedByName("lockable"))
                || (trackorTreeItem.getLockable().equals("NO") && checkboxHelper.isCheckedByName("lockable"))) {
            checkboxHelper.clickByName("lockable");
        }

        if ((trackorTreeItem.getShowAll().equals("YES") && !checkboxHelper.isCheckedByName("showAllInTrcontainer"))
                || (trackorTreeItem.getShowAll().equals("NO") && checkboxHelper.isCheckedByName("showAllInTrcontainer"))) {
            checkboxHelper.clickByName("showAllInTrcontainer");
        }

        window.closeModalAndWaitTreeLoad(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    public void testOnForm(TrackorTreeItem trackorTreeItem) {
        tree.selectTreeItem(AbstractSeleniumCore.getTreeIdx(), "-1", trackorTreeItem);

        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertHelper.AssertText("treePath", trackorTreeItem.getTreePath());
        assertHelper.AssertText("trackorType", trackorTreeItem.getTrackorType());
        assertHelper.AssertSelect("cardinalityId", trackorTreeItem.getCardinality());
        assertHelper.AssertSelect("uniqueByTtId", trackorTreeItem.getUniqueBy());
        assertHelper.AssertSelect("colorId", trackorTreeItem.getColor());
        assertHelper.AssertCheckBoxNew("childRequiresParent", trackorTreeItem.getChildRequiresParent());
        assertHelper.AssertCheckBoxNew("onParentDeleteCascade", trackorTreeItem.getOnParentDeleteCascade());
        assertHelper.AssertCheckBoxNew("lockable", trackorTreeItem.getLockable());
        assertHelper.AssertCheckBoxNew("showAllInTrcontainer", trackorTreeItem.getShowAll());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRolePrivs(List<String> rolePrivsRead, List<String> rolePrivsEdit, List<String> rolePrivsAdd, List<String> rolePrivsDelete, List<String> rolePrivsNone) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tabHelper.goToTab(2L); //Role Privs
        wait.waitGridLoad(2L, 2L);
        gridHelper.checkAssignmentGridColumn(2L, 1L, rolePrivsRead);
        gridHelper.checkAssignmentGridColumn(2L, 2L, rolePrivsEdit);
        gridHelper.checkAssignmentGridColumn(2L, 3L, rolePrivsAdd);
        gridHelper.checkAssignmentGridColumn(2L, 4L, rolePrivsDelete);
        gridHelper.checkAssignmentGridColumn(2L, 5L, rolePrivsNone);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRoleLockPrivs(List<String> roleLockPrivsLock, List<String> roleLockPrivsUnlock, List<String> roleLockPrivsNone) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tabHelper.goToTab(3L); //Role Lock Privs
        wait.waitGridLoad(3L, 3L);
        gridHelper.checkAssignmentGridColumn(3L, 1L, roleLockPrivsLock);
        gridHelper.checkAssignmentGridColumn(3L, 2L, roleLockPrivsUnlock);
        gridHelper.checkAssignmentGridColumn(3L, 3L, roleLockPrivsNone);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
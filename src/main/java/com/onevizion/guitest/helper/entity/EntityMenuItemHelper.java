package com.onevizion.guitest.helper.entity;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.AbstractSeleniumCore;
import com.onevizion.guitest.exception.SeleniumUnexpectedException;
import com.onevizion.guitest.helper.AssertHelper;
import com.onevizion.guitest.helper.WaitHelper;
import com.onevizion.guitest.helper.WindowHelper;
import com.onevizion.guitest.helper.jquery.JqueryWaitHelper;
import com.onevizion.guitest.helper.tree.TreeHelper;
import com.onevizion.guitest.vo.MenuItemType;
import com.onevizion.guitest.vo.entity.MenuItem;

@Component
public class EntityMenuItemHelper {

    @Resource
    private WindowHelper windowHelper;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private JqueryWaitHelper jqueryWaitHelper;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private TreeHelper treeHelper;

    public void testOnForm(MenuItem menuItem) {
        treeHelper.selectTreeItem(AbstractSeleniumCore.getTreeIdx(), "-1", menuItem);

        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();
        jqueryWaitHelper.waitJQueryLoad();

        if (MenuItemType.SUBGROUP.equals(menuItem.getMenuItemType())) {
            assertHelper.AssertText("label", menuItem.getLabel());
            assertHelper.AssertCheckBoxNew("visible", menuItem.getVisible());
        } else if (MenuItemType.PORTAL.equals(menuItem.getMenuItemType())) {
            assertHelper.AssertSelectById("OnClick", menuItem.getItemType());
            assertHelper.AssertText("label", menuItem.getLabel());
            assertHelper.AssertText("visibleURL", menuItem.getUrl());
            assertHelper.AssertSelect("portalId", menuItem.getPortal());
            assertHelper.AssertCheckBoxNew("visible", menuItem.getVisible());
        } else if (MenuItemType.DASHBOURD.equals(menuItem.getMenuItemType())) {
            assertHelper.AssertSelectById("OnClick", menuItem.getItemType());
            assertHelper.AssertText("label", menuItem.getLabel());
            assertHelper.AssertText("visibleURL", menuItem.getUrl());
            assertHelper.AssertSelect("dashboardId", menuItem.getDashbourd());
            assertHelper.AssertCheckBoxNew("visible", menuItem.getVisible());
        } else if (MenuItemType.PAGE_WITHOUT_TRACKOR_TYPE.equals(menuItem.getMenuItemType())) {
            assertHelper.AssertSelectById("OnClick", menuItem.getItemType());
            assertHelper.AssertText("label", menuItem.getLabel());
            assertHelper.AssertText("visibleURL", menuItem.getUrl());
            assertHelper.AssertSelect("viewOptId", menuItem.getView());
            assertHelper.AssertCheckBoxNew("hideView", menuItem.getHideView());
            assertHelper.AssertSelect("filterOptId", menuItem.getFilter());
            assertHelper.AssertCheckBoxNew("hideFilter", menuItem.getHideFilter());
            assertHelper.AssertCheckBoxNew("visible", menuItem.getVisible());
        } else if (MenuItemType.PAGE_WITH_TRACKOR_TYPE.equals(menuItem.getMenuItemType())) {
            assertHelper.AssertSelectById("OnClick", menuItem.getItemType());
            assertHelper.AssertText("label", menuItem.getLabel());
            assertHelper.AssertText("visibleURL", menuItem.getUrl());
            assertHelper.AssertSelect("primaryXitorTypeId", menuItem.getTrackorType());
            assertHelper.AssertSelect("viewOptId", menuItem.getView());
            assertHelper.AssertCheckBoxNew("hideView", menuItem.getHideView());
            assertHelper.AssertSelect("filterOptId", menuItem.getFilter());
            assertHelper.AssertCheckBoxNew("hideFilter", menuItem.getHideFilter());
            assertHelper.AssertCheckBoxNew("visible", menuItem.getVisible());
        } else if (MenuItemType.ACTION.equals(menuItem.getMenuItemType())) {
            assertHelper.AssertSelectById("OnClick", menuItem.getItemType());
            assertHelper.AssertText("label", menuItem.getLabel());
            assertHelper.AssertCheckBoxNew("visible", menuItem.getVisible());
        } else {
            throw new SeleniumUnexpectedException("Not support MenuItemType. MenuItemType=" + menuItem.getMenuItemType());
        }

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
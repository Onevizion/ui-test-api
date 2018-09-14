package com.onevizion.uitest.api.helper.entity;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.jquery.JqueryWait;
import com.onevizion.uitest.api.helper.tree.Tree;
import com.onevizion.uitest.api.vo.MenuItemType;
import com.onevizion.uitest.api.vo.entity.MenuItem;

@Component
public class EntityMenuItem {

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private JqueryWait jqueryWait;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private Tree tree;

    public void testOnForm(MenuItem menuItem) {
        tree.selectTreeItem(AbstractSeleniumCore.getTreeIdx(), "-1", menuItem);

        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        jqueryWait.waitJQueryLoad();

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

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
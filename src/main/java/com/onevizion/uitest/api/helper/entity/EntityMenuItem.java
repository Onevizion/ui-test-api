package com.onevizion.uitest.api.helper.entity;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertElement;
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
    private AssertElement assertElement;

    @Resource
    private Tree tree;

    public void testOnForm(MenuItem menuItem) {
        tree.selectItem(AbstractSeleniumCore.getTreeIdx(), "-1", menuItem);

        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        jqueryWait.waitJQueryLoad();

        if (MenuItemType.SUBGROUP.equals(menuItem.getMenuItemType())) {
            assertElement.AssertText("label", menuItem.getLabel());
            assertElement.AssertCheckBoxNew("visible", menuItem.getVisible());
        } else if (MenuItemType.PORTAL.equals(menuItem.getMenuItemType())) {
            assertElement.AssertSelectById("OnClick", menuItem.getItemType());
            assertElement.AssertText("label", menuItem.getLabel());
            assertElement.AssertText("visibleURL", menuItem.getUrl());
            assertElement.AssertSelect("portalId", menuItem.getPortal());
            assertElement.AssertCheckBoxNew("visible", menuItem.getVisible());
        } else if (MenuItemType.DASHBOURD.equals(menuItem.getMenuItemType())) {
            assertElement.AssertSelectById("OnClick", menuItem.getItemType());
            assertElement.AssertText("label", menuItem.getLabel());
            assertElement.AssertText("visibleURL", menuItem.getUrl());
            assertElement.AssertSelect("dashboardId", menuItem.getDashbourd());
            assertElement.AssertCheckBoxNew("visible", menuItem.getVisible());
        } else if (MenuItemType.PAGE_WITHOUT_TRACKOR_TYPE.equals(menuItem.getMenuItemType())) {
            assertElement.AssertSelectById("OnClick", menuItem.getItemType());
            assertElement.AssertText("label", menuItem.getLabel());
            assertElement.AssertText("visibleURL", menuItem.getUrl());
            assertElement.AssertSelect("viewOptId", menuItem.getView());
            assertElement.AssertCheckBoxNew("hideView", menuItem.getHideView());
            assertElement.AssertSelect("filterOptId", menuItem.getFilter());
            assertElement.AssertCheckBoxNew("hideFilter", menuItem.getHideFilter());
            assertElement.AssertCheckBoxNew("visible", menuItem.getVisible());
        } else if (MenuItemType.PAGE_WITH_TRACKOR_TYPE.equals(menuItem.getMenuItemType())) {
            assertElement.AssertSelectById("OnClick", menuItem.getItemType());
            assertElement.AssertText("label", menuItem.getLabel());
            assertElement.AssertText("visibleURL", menuItem.getUrl());
            assertElement.AssertSelect("primaryXitorTypeId", menuItem.getTrackorType());
            assertElement.AssertSelect("viewOptId", menuItem.getView());
            assertElement.AssertCheckBoxNew("hideView", menuItem.getHideView());
            assertElement.AssertSelect("filterOptId", menuItem.getFilter());
            assertElement.AssertCheckBoxNew("hideFilter", menuItem.getHideFilter());
            assertElement.AssertCheckBoxNew("visible", menuItem.getVisible());
        } else if (MenuItemType.ACTION.equals(menuItem.getMenuItemType())) {
            assertElement.AssertSelectById("OnClick", menuItem.getItemType());
            assertElement.AssertText("label", menuItem.getLabel());
            assertElement.AssertCheckBoxNew("visible", menuItem.getVisible());
        } else {
            throw new SeleniumUnexpectedException("Not support MenuItemType. MenuItemType=" + menuItem.getMenuItemType());
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
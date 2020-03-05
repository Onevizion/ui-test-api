package com.onevizion.uitest.api.helper.entity;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.helper.tree.Tree;
import com.onevizion.uitest.api.vo.MenuItemType;
import com.onevizion.uitest.api.vo.entity.MenuItem;

@Component
public class EntityMenuItem {

    private static final String LABEL = "label";
    private static final String VISIBLE = "visible";
    private static final String URL = "url";
    private static final String ITEM_TYPE = "OnClick";
    private static final String VIEW = "viewOptId";
    private static final String HIDE_VIEW = "hideView";
    private static final String FILTER = "filterOptId";
    private static final String HIDE_FILTER = "hideFilter";
    private static final String PORTAL = "portalId";
    private static final String DASHBOARD = "dashboardId";
    private static final String TRACKOR_TYPE = "primaryXitorTypeId";

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private Jquery jquery;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Tree tree;

    public void testOnForm(MenuItem menuItem) {
        tree.selectItem(AbstractSeleniumCore.getTreeIdx(), "-1", menuItem);

        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        jquery.waitLoad();

        if (MenuItemType.SUBGROUP.equals(menuItem.getMenuItemType())) {
            assertElement.assertText(LABEL, menuItem.getLabel());
            assertElement.assertCheckbox(VISIBLE, menuItem.getVisible());
        } else if (MenuItemType.PORTAL.equals(menuItem.getMenuItemType())) {
            assertElement.assertSelectById(ITEM_TYPE, menuItem.getItemType());
            assertElement.assertText(LABEL, menuItem.getLabel());
            assertElement.assertText(URL, menuItem.getUrl());
            assertElement.assertSelect(PORTAL, menuItem.getPortal());
            assertElement.assertCheckbox(VISIBLE, menuItem.getVisible());
        } else if (MenuItemType.DASHBOURD.equals(menuItem.getMenuItemType())) {
            assertElement.assertSelectById(ITEM_TYPE, menuItem.getItemType());
            assertElement.assertText(LABEL, menuItem.getLabel());
            assertElement.assertText(URL, menuItem.getUrl());
            assertElement.assertSelect(DASHBOARD, menuItem.getDashbourd());
            assertElement.assertCheckbox(VISIBLE, menuItem.getVisible());
        } else if (MenuItemType.PAGE_WITHOUT_TRACKOR_TYPE.equals(menuItem.getMenuItemType())) {
            assertElement.assertSelectById(ITEM_TYPE, menuItem.getItemType());
            assertElement.assertText(LABEL, menuItem.getLabel());
            assertElement.assertText(URL, menuItem.getUrl());
            assertElement.assertSelect(VIEW, menuItem.getView());
            assertElement.assertCheckbox(HIDE_VIEW, menuItem.getHideView());
            assertElement.assertSelect(FILTER, menuItem.getFilter());
            assertElement.assertCheckbox(HIDE_FILTER, menuItem.getHideFilter());
            assertElement.assertCheckbox(VISIBLE, menuItem.getVisible());
        } else if (MenuItemType.PAGE_WITH_TRACKOR_TYPE.equals(menuItem.getMenuItemType())) {
            assertElement.assertSelectById(ITEM_TYPE, menuItem.getItemType());
            assertElement.assertText(LABEL, menuItem.getLabel());
            assertElement.assertText(URL, menuItem.getUrl());
            assertElement.assertSelect(TRACKOR_TYPE, menuItem.getTrackorType());
            assertElement.assertSelect(VIEW, menuItem.getView());
            assertElement.assertCheckbox(HIDE_VIEW, menuItem.getHideView());
            assertElement.assertSelect(FILTER, menuItem.getFilter());
            assertElement.assertCheckbox(HIDE_FILTER, menuItem.getHideFilter());
            assertElement.assertCheckbox(VISIBLE, menuItem.getVisible());
        } else {
            throw new SeleniumUnexpectedException("Not support MenuItemType. MenuItemType=" + menuItem.getMenuItemType());
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
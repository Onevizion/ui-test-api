package com.onevizion.uitest.api.helper.entity;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.NewDropDownHelper;
import com.onevizion.uitest.api.helper.Tab;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.jquery.JqueryWait;
import com.onevizion.uitest.api.vo.entity.Menu;

@Component
public class EntityMenu {

    @Resource
    private Wait wait;

    @Resource
    private Window window;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private Tab tab;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private NewDropDownHelper newDropDownHelper;

    @Resource
    private JqueryWait jqueryWait;

    public void testOnForm(Menu menu) {
        newDropDownHelper.openEditMenuForm(menu.getName());
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        jqueryWait.waitJQueryLoad();

        assertHelper.AssertText("name", menu.getName());
        assertHelper.AssertText("description", menu.getDescription());
        assertHelper.AssertSelect("defaultMenuItemId", menu.getDefaultMenuItem());
        assertHelper.AssertSelectWithFolder("dropgridFolderId", menu.getDropgridFolder());
        assertHelper.AssertCheckBoxNew("showTipOfTheDay", menu.getShowTipOfTheDay());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRoleAssignments(Menu menu, List<String> roles) {
        newDropDownHelper.openEditMenuForm(menu.getName());
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tab.goToTab(2L); // Roles
        wait.waitGridLoad(2L, 2L);
        gridHelper.checkAssignmentGridColumn2(2L, 0L, roles);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
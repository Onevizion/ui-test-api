package com.onevizion.guitest.helper.entity;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.AbstractSeleniumCore;
import com.onevizion.guitest.helper.AssertHelper;
import com.onevizion.guitest.helper.GridHelper;
import com.onevizion.guitest.helper.NewDropDownHelper;
import com.onevizion.guitest.helper.TabHelper;
import com.onevizion.guitest.helper.WaitHelper;
import com.onevizion.guitest.helper.WindowHelper;
import com.onevizion.guitest.helper.jquery.JqueryWaitHelper;
import com.onevizion.guitest.vo.entity.Menu;

@Component
public class EntityMenuHelper {

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private WindowHelper windowHelper;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private TabHelper tabHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private NewDropDownHelper newDropDownHelper;

    @Resource
    private JqueryWaitHelper jqueryWaitHelper;

    public void testOnForm(Menu menu) {
        newDropDownHelper.openEditMenuForm(menu.getName());
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();
        jqueryWaitHelper.waitJQueryLoad();

        assertHelper.AssertText("name", menu.getName());
        assertHelper.AssertText("description", menu.getDescription());
        assertHelper.AssertSelect("defaultMenuItemId", menu.getDefaultMenuItem());
        assertHelper.AssertSelectWithFolder("dropgridFolderId", menu.getDropgridFolder());
        assertHelper.AssertCheckBoxNew("showTipOfTheDay", menu.getShowTipOfTheDay());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRoleAssignments(Menu menu, List<String> roles) {
        newDropDownHelper.openEditMenuForm(menu.getName());
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        tabHelper.goToTab(2L); // Roles
        waitHelper.waitGridLoad(2L, 2L);
        gridHelper.checkAssignmentGridColumn2(2L, 0L, roles);

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
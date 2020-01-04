package com.onevizion.uitest.api.helper.entity;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.NewDropDown;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.helper.tab.Tab;
import com.onevizion.uitest.api.vo.entity.Menu;

@Component
public class EntityMenu {

    @Resource
    private Wait wait;

    @Resource
    private Window window;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Tab tab;

    @Resource
    private Grid grid;

    @Resource
    private Grid2 grid2;

    @Resource
    private NewDropDown newDropDown;

    @Resource
    private Jquery jquery;

    public void testOnForm(Menu menu) {
        newDropDown.openEditMenuForm(menu.getName());
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        jquery.waitLoad();

        assertElement.assertText("name", menu.getName());
        assertElement.assertText("description", menu.getDescription());
        assertElement.assertText("orderNumber", menu.getOrderNumber());
        assertElement.assertSelectWithFolder("dropgridFolderId", menu.getDropgridFolder());
        //assertElement.assertText("icon", menu.getIconName()); TODO

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRoleAssignments(Menu menu, List<String> roles) {
        newDropDown.openEditMenuForm(menu.getName());
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tab.goToTab(2L); // Roles
        grid2.waitLoad(2L);
        grid.checkAssignmentGridColumn2(2L, 0L, roles);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
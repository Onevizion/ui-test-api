package com.onevizion.uitest.api.helper.entity;

import java.util.List;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.NewNewDropDown;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.tab.Tab;
import com.onevizion.uitest.api.vo.entity.Menu;

@Component
public class EntityMenu {

    @Autowired
    private Window window;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Tab tab;

    @Autowired
    private Grid grid;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private NewNewDropDown newNewDropDown;

    public void testOnForm(Menu menu) {
        newNewDropDown.openEditMenuForm(menu.getLabel());

        assertElement.assertText("name", menu.getName());
        assertElement.assertText("label", menu.getLabel());
        assertElement.assertText("description", menu.getDescription());
        assertElement.assertText("orderNumber", menu.getOrderNumber());
        assertElement.assertSelectWithFolder("dropgridFolderId", menu.getDropgridFolder());
        //assertElement.assertText("icon", menu.getIconName()); TODO

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRoleAssignments(Menu menu, List<String> roles) {
        newNewDropDown.openEditMenuForm(menu.getLabel());

        tab.goToTab(2); // Roles
        grid2.waitLoad(2L);
        grid.checkAssignmentGridColumn2(2L, 0L, roles);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
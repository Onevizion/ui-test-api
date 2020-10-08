package com.onevizion.uitest.api.helper.entity;

import java.util.List;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.menu.Menu;
import com.onevizion.uitest.api.helper.tab.Tab;
import com.onevizion.uitest.api.vo.entity.MenuVo;

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
    private Menu menu;

    public void testOnForm(MenuVo menuVo) {
        menu.openEditMenuForm(menuVo.getLabel());

        assertElement.assertText("name", menuVo.getName());
        assertElement.assertText("label", menuVo.getLabel());
        assertElement.assertText("description", menuVo.getDescription());
        assertElement.assertText("orderNumber", menuVo.getOrderNumber());
        assertElement.assertSelectWithFolder("dropgridFolderId", menuVo.getDropgridFolder());
        //assertElement.assertText("icon", menuVo.getIconName()); TODO

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testRoleAssignments(MenuVo menuVo, List<String> roles) {
        menu.openEditMenuForm(menuVo.getLabel());

        tab.goToTab(2); // Roles
        grid2.waitLoad(2L);
        grid.checkAssignmentGridColumn2(2L, 0L, roles);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
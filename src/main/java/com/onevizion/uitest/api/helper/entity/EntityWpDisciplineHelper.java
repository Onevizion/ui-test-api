package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.JsHelper;
import com.onevizion.uitest.api.helper.TabHelper;
import com.onevizion.uitest.api.helper.WaitHelper;
import com.onevizion.uitest.api.helper.WindowHelper;
import com.onevizion.uitest.api.vo.entity.WpDiscipline;

@Component
public class EntityWpDisciplineHelper {

    @Resource
    private WindowHelper windowHelper;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private TabHelper tabHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private JsHelper jsHelper;

    public void add(WpDiscipline wpDiscipline) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("discpType")).sendKeys(wpDiscipline.getName());

        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(wpDiscipline.getDescription());

        tabHelper.goToTab(2L); //Role Assignments
        waitHelper.waitGridLoad(2L, 2L);
        gridHelper.clearAssignmentGridColumn2(2L, 0L);
        gridHelper.selectAssignmentGridColumn2New(2L, 0L, 2L, wpDiscipline.getRoles());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(WpDiscipline wpDiscipline) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("discpType")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("discpType")).sendKeys(wpDiscipline.getName());

        seleniumSettings.getWebDriver().findElement(By.name("description")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(wpDiscipline.getDescription());

        tabHelper.goToTab(2L);//Role Assignments
        waitHelper.waitGridLoad(2L, 2L);
        gridHelper.clearAssignmentGridColumn2(2L, 0L);
        gridHelper.selectAssignmentGridColumn2New(2L, 0L, 2L, wpDiscipline.getRoles());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(WpDiscipline wpDiscipline) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        assertHelper.AssertText("discpType", wpDiscipline.getName());
        assertHelper.AssertText("description", wpDiscipline.getDescription());

        tabHelper.goToTab(2L); //Role Assignments
        waitHelper.waitGridLoad(2L, 2L);
        gridHelper.checkAssignmentGridColumn2New(2L, 0L, 2L, wpDiscipline.getRoles());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, WpDiscipline wpDiscipline) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Discipline"), wpDiscipline.getName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Description"), wpDiscipline.getDescription());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.vo.entity.ConfigAppExtParam;

@Component
public class EntityConfigAppExtParam {

    private static final String NAME = "paramName";
    private static final String DESCRIPTION = "description";
    private static final String SQL = "sqlText";

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Js js;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Grid grid;

    @Resource
    private Grid2 grid2;

    public void add(ConfigAppExtParam configAppExtParam) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys("param1");

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys("desc param1");

        wait.waitCodeMirrorLoad(SQL);
        js.setValueToCodeMirror(SQL, "select 1 from dual");

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(ConfigAppExtParam configAppExtParam) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys("param1edit");

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys("desc param1 edit");

        wait.waitCodeMirrorLoad(SQL);
        js.setValueToCodeMirror(SQL, "select 11 from dual");

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testOnForm(ConfigAppExtParam configAppExtParam) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertText(NAME, configAppExtParam.getName());
        assertElement.assertText(DESCRIPTION, configAppExtParam.getDescription());
        assertElement.assertCodeMirror(SQL, configAppExtParam.getSql());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, ConfigAppExtParam configAppExtParam) {
        Map<Long, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "URL Param"), configAppExtParam.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Description"), configAppExtParam.getDescription());
        gridVals.put(js.getColumnIndexByLabel(gridId, "SQL Statement"), configAppExtParam.getSql());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
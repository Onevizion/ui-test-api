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
import com.onevizion.uitest.api.vo.entity.ConfigAppExtParam;

@Component
public class EntityConfigAppExtParam {

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

    public void add(ConfigAppExtParam configAppExtParam) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("paramName")).sendKeys("param1");

        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys("desc param1");

        wait.waitCodeMirrorLoad("sqlText");
        js.setValueToCodeMirror("sqlText", "select 1 from dual");

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(ConfigAppExtParam configAppExtParam) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("paramName")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("paramName")).sendKeys("param1edit");

        seleniumSettings.getWebDriver().findElement(By.name("description")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys("desc param1 edit");

        wait.waitCodeMirrorLoad("sqlText");
        js.setValueToCodeMirror("sqlText", "select 11 from dual");

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(ConfigAppExtParam configAppExtParam) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.AssertText("paramName", configAppExtParam.getName());
        assertElement.AssertText("description", configAppExtParam.getDescription());
        assertElement.AssertCodeMirror("sqlText", configAppExtParam.getSql());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, ConfigAppExtParam configAppExtParam) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "URL Param"), configAppExtParam.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Description"), configAppExtParam.getDescription());
        gridVals.put(js.getColumnIndexByLabel(gridId, "SQL Statement"), configAppExtParam.getSql());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
package com.onevizion.guitest.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.AbstractSeleniumCore;
import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.helper.AssertHelper;
import com.onevizion.guitest.helper.GridHelper;
import com.onevizion.guitest.helper.JsHelper;
import com.onevizion.guitest.helper.WaitHelper;
import com.onevizion.guitest.helper.WindowHelper;
import com.onevizion.guitest.vo.entity.ConfigAppExtParam;

@Component
public class EntityConfigAppExtParamHelper {

    @Resource
    private WindowHelper windowHelper;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private GridHelper gridHelper;

    public void add(ConfigAppExtParam configAppExtParam) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("paramName")).sendKeys("param1");

        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys("desc param1");

        waitHelper.waitCodeMirrorLoad("sqlText");
        jsHelper.setValueToCodeMirror("sqlText", "select 1 from dual");

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(ConfigAppExtParam configAppExtParam) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name("paramName")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("paramName")).sendKeys("param1edit");

        seleniumSettings.getWebDriver().findElement(By.name("description")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys("desc param1 edit");

        waitHelper.waitCodeMirrorLoad("sqlText");
        jsHelper.setValueToCodeMirror("sqlText", "select 11 from dual");

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(ConfigAppExtParam configAppExtParam) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        assertHelper.AssertText("paramName", configAppExtParam.getName());
        assertHelper.AssertText("description", configAppExtParam.getDescription());
        assertHelper.AssertCodeMirror("sqlText", configAppExtParam.getSql());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, ConfigAppExtParam configAppExtParam) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "URL Param"), configAppExtParam.getName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Description"), configAppExtParam.getDescription());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "SQL Statement"), configAppExtParam.getSql());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
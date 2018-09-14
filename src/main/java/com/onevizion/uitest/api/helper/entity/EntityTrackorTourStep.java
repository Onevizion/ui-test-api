package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.CheckboxHelper;
import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.vo.entity.TrackorTourStep;

@Component
public class EntityTrackorTourStep {

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private CheckboxHelper checkboxHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private Js js;

    @Resource
    private AssertHelper assertHelper;

    public void add(TrackorTourStep trackorTourStep) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + 2L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        new Select(seleniumSettings.getWebDriver().findElement(By.name("elementTypeId"))).selectByVisibleText(trackorTourStep.getElementType());

        if (trackorTourStep.getElementType().equals("name=")) {
            seleniumSettings.getWebDriver().findElement(By.name("elementName")).sendKeys(trackorTourStep.getElementName());
        } else if (trackorTourStep.getElementType().equals("id=")) {
            seleniumSettings.getWebDriver().findElement(By.name("elementId")).sendKeys(trackorTourStep.getElementId());
        } else {
            throw new SeleniumUnexpectedException("Not support ElementType [" + trackorTourStep.getElementType() + "]");
        }

        seleniumSettings.getWebDriver().findElement(By.name("duration")).sendKeys(trackorTourStep.getDuration());

        if ((trackorTourStep.getOrphaned().equals("YES") && !checkboxHelper.isCheckedByName("orphaned"))
                || (trackorTourStep.getOrphaned().equals("NO") && checkboxHelper.isCheckedByName("orphaned"))) {
            checkboxHelper.clickByName("orphaned");
        }

        if ((trackorTourStep.getBackdrop().equals("YES") && !checkboxHelper.isCheckedByName("backdrop"))
                || (trackorTourStep.getBackdrop().equals("NO") && checkboxHelper.isCheckedByName("backdrop"))) {
            checkboxHelper.clickByName("backdrop");
        }

        new Select(seleniumSettings.getWebDriver().findElement(By.name("placement"))).selectByVisibleText(trackorTourStep.getPlacement());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("actionId"))).selectByVisibleText(trackorTourStep.getAction());

        seleniumSettings.getWebDriver().findElement(By.name("formNumber")).sendKeys(trackorTourStep.getFormNumber());

        seleniumSettings.getWebDriver().findElement(By.name("titleLabel")).sendKeys(trackorTourStep.getTitleLabel());

        wait.waitWebElement(By.id("contentLabel"));
        js.setValueToFCKEditor("contentLabel", trackorTourStep.getContentLabel());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(2L, 2L);
    }

    public void edit(TrackorTourStep trackorTourStep) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + 2L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        new Select(seleniumSettings.getWebDriver().findElement(By.name("elementTypeId"))).selectByVisibleText(trackorTourStep.getElementType());

        if (trackorTourStep.getElementType().equals("name=")) {
            seleniumSettings.getWebDriver().findElement(By.name("elementName")).sendKeys(trackorTourStep.getElementName());
        } else if (trackorTourStep.getElementType().equals("id=")) {
            seleniumSettings.getWebDriver().findElement(By.name("elementId")).sendKeys(trackorTourStep.getElementId());
        } else {
            throw new SeleniumUnexpectedException("Not support ElementType [" + trackorTourStep.getElementType() + "]");
        }

        seleniumSettings.getWebDriver().findElement(By.name("duration")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("duration")).sendKeys(trackorTourStep.getDuration());

        if ((trackorTourStep.getOrphaned().equals("YES") && !checkboxHelper.isCheckedByName("orphaned"))
                || (trackorTourStep.getOrphaned().equals("NO") && checkboxHelper.isCheckedByName("orphaned"))) {
            checkboxHelper.clickByName("orphaned");
        }

        if ((trackorTourStep.getBackdrop().equals("YES") && !checkboxHelper.isCheckedByName("backdrop"))
                || (trackorTourStep.getBackdrop().equals("NO") && checkboxHelper.isCheckedByName("backdrop"))) {
            checkboxHelper.clickByName("backdrop");
        }

        new Select(seleniumSettings.getWebDriver().findElement(By.name("placement"))).selectByVisibleText(trackorTourStep.getPlacement());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("actionId"))).selectByVisibleText(trackorTourStep.getAction());

        seleniumSettings.getWebDriver().findElement(By.name("formNumber")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("formNumber")).sendKeys(trackorTourStep.getFormNumber());

        seleniumSettings.getWebDriver().findElement(By.name("titleLabel")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("titleLabel")).sendKeys(trackorTourStep.getTitleLabel());

        wait.waitWebElement(By.id("contentLabel"));
        js.setValueToFCKEditor("contentLabel", trackorTourStep.getContentLabel());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(2L, 2L);
    }

    public void testOnForm(TrackorTourStep trackorTourStep) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + 2L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertHelper.AssertSelect("elementTypeId", trackorTourStep.getElementType());
        if (trackorTourStep.getElementType().equals("name=")) {
            assertHelper.AssertText("elementName", trackorTourStep.getElementName());
        } else if (trackorTourStep.getElementType().equals("id=")) {
            assertHelper.AssertText("elementId", trackorTourStep.getElementId());
        } else {
            throw new SeleniumUnexpectedException("Not support ElementType [" + trackorTourStep.getElementType() + "]");
        }
        assertHelper.AssertText("duration", trackorTourStep.getDuration());
        assertHelper.AssertCheckBoxNew("orphaned", trackorTourStep.getOrphaned());
        assertHelper.AssertCheckBoxNew("backdrop", trackorTourStep.getBackdrop());
        assertHelper.AssertSelect("placement", trackorTourStep.getPlacement());
        assertHelper.AssertSelect("actionId", trackorTourStep.getAction());
        assertHelper.AssertText("formNumber", trackorTourStep.getFormNumber());
        assertHelper.AssertText("titleLabel", trackorTourStep.getTitleLabel());

        wait.waitWebElement(By.id("contentLabel"));
        assertHelper.AssertFCKEditor("contentLabel", trackorTourStep.getContentLabel());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, TrackorTourStep trackorTourStep) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Step Title Label"), trackorTourStep.getTitleLabel());
        if (trackorTourStep.getElementType().equals("name=")) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "HTML Element"), trackorTourStep.getElementName());
        } else if (trackorTourStep.getElementType().equals("id=")) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "HTML Element"), trackorTourStep.getElementId());
        } else {
            throw new SeleniumUnexpectedException("Not support ElementType [" + trackorTourStep.getElementType() + "]");
        }
        gridVals.put(js.getColumnIndexByLabel(gridId, "Popover Placement"), trackorTourStep.getPlacement());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Form Number"), trackorTourStep.getFormNumber());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
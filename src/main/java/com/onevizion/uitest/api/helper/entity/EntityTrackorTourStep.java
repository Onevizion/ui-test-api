package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.form.Form;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.wiki.FckEditor;
import com.onevizion.uitest.api.vo.entity.TrackorTourStep;

@Component
public class EntityTrackorTourStep {

    private static final String ELEMENT_TYPE = "elementTypeId";
    private static final String ELEMENT_NAME = "elementName";
    private static final String ELEMENT_ID = "elementId";
    private static final String DURATION = "duration";
    private static final String ORPHANED = "orphaned";
    private static final String BACKDROP = "backdrop";
    private static final String PLACEMENT = "placement";
    private static final String ACTION = "actionId";
    private static final String FORM_NUMBER = "formNumber";
    private static final String TITLE_LABEL = "titleLabel";
    private static final String CONTENT_LABEL = "contentLabel";

    @Autowired
    private Window window;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Checkbox checkbox;

    @Autowired
    private Form form;

    @Autowired
    private Grid grid;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private Js js;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private FckEditor fckEditor;

    public void add(TrackorTourStep trackorTourStep) {
        form.openAddWithoutTabs(2);

        new Select(seleniumSettings.getWebDriver().findElement(By.name(ELEMENT_TYPE))).selectByVisibleText(trackorTourStep.getElementType());

        if (trackorTourStep.getElementType().equals("name=")) {
            seleniumSettings.getWebDriver().findElement(By.name(ELEMENT_NAME)).sendKeys(trackorTourStep.getElementName());
        } else if (trackorTourStep.getElementType().equals("id=")) {
            seleniumSettings.getWebDriver().findElement(By.name(ELEMENT_ID)).sendKeys(trackorTourStep.getElementId());
        } else {
            throw new SeleniumUnexpectedException("Not support ElementType [" + trackorTourStep.getElementType() + "]");
        }

        seleniumSettings.getWebDriver().findElement(By.name(DURATION)).sendKeys(trackorTourStep.getDuration());

        if ((trackorTourStep.getOrphaned().equals("YES") && !checkbox.isCheckedByName(ORPHANED))
                || (trackorTourStep.getOrphaned().equals("NO") && checkbox.isCheckedByName(ORPHANED))) {
            checkbox.clickByName(ORPHANED);
        }

        if ((trackorTourStep.getBackdrop().equals("YES") && !checkbox.isCheckedByName(BACKDROP))
                || (trackorTourStep.getBackdrop().equals("NO") && checkbox.isCheckedByName(BACKDROP))) {
            checkbox.clickByName(BACKDROP);
        }

        new Select(seleniumSettings.getWebDriver().findElement(By.name(PLACEMENT))).selectByVisibleText(trackorTourStep.getPlacement());

        new Select(seleniumSettings.getWebDriver().findElement(By.name(ACTION))).selectByVisibleText(trackorTourStep.getAction());

        seleniumSettings.getWebDriver().findElement(By.name(FORM_NUMBER)).sendKeys(trackorTourStep.getFormNumber());

        seleniumSettings.getWebDriver().findElement(By.name(TITLE_LABEL)).sendKeys(trackorTourStep.getTitleLabel());

        fckEditor.setValue(CONTENT_LABEL, trackorTourStep.getContentLabel());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad(2L);
    }

    public void edit(TrackorTourStep trackorTourStep) {
        form.openEditWithoutTabs(2);

        new Select(seleniumSettings.getWebDriver().findElement(By.name(ELEMENT_TYPE))).selectByVisibleText(trackorTourStep.getElementType());

        if (trackorTourStep.getElementType().equals("name=")) {
            seleniumSettings.getWebDriver().findElement(By.name(ELEMENT_NAME)).sendKeys(trackorTourStep.getElementName());
        } else if (trackorTourStep.getElementType().equals("id=")) {
            seleniumSettings.getWebDriver().findElement(By.name(ELEMENT_ID)).sendKeys(trackorTourStep.getElementId());
        } else {
            throw new SeleniumUnexpectedException("Not support ElementType [" + trackorTourStep.getElementType() + "]");
        }

        seleniumSettings.getWebDriver().findElement(By.name(DURATION)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(DURATION)).sendKeys(trackorTourStep.getDuration());

        if ((trackorTourStep.getOrphaned().equals("YES") && !checkbox.isCheckedByName(ORPHANED))
                || (trackorTourStep.getOrphaned().equals("NO") && checkbox.isCheckedByName(ORPHANED))) {
            checkbox.clickByName(ORPHANED);
        }

        if ((trackorTourStep.getBackdrop().equals("YES") && !checkbox.isCheckedByName(BACKDROP))
                || (trackorTourStep.getBackdrop().equals("NO") && checkbox.isCheckedByName(BACKDROP))) {
            checkbox.clickByName(BACKDROP);
        }

        new Select(seleniumSettings.getWebDriver().findElement(By.name(PLACEMENT))).selectByVisibleText(trackorTourStep.getPlacement());

        new Select(seleniumSettings.getWebDriver().findElement(By.name(ACTION))).selectByVisibleText(trackorTourStep.getAction());

        seleniumSettings.getWebDriver().findElement(By.name(FORM_NUMBER)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(FORM_NUMBER)).sendKeys(trackorTourStep.getFormNumber());

        seleniumSettings.getWebDriver().findElement(By.name(TITLE_LABEL)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(TITLE_LABEL)).sendKeys(trackorTourStep.getTitleLabel());

        fckEditor.setValue(CONTENT_LABEL, trackorTourStep.getContentLabel());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad(2L);
    }

    public void testOnForm(TrackorTourStep trackorTourStep) {
        form.openEditWithoutTabs(2);

        assertElement.assertSelect(ELEMENT_TYPE, trackorTourStep.getElementType());
        if (trackorTourStep.getElementType().equals("name=")) {
            assertElement.assertText(ELEMENT_NAME, trackorTourStep.getElementName());
        } else if (trackorTourStep.getElementType().equals("id=")) {
            assertElement.assertText(ELEMENT_ID, trackorTourStep.getElementId());
        } else {
            throw new SeleniumUnexpectedException("Not support ElementType [" + trackorTourStep.getElementType() + "]");
        }
        assertElement.assertText(DURATION, trackorTourStep.getDuration());
        assertElement.assertCheckbox(ORPHANED, trackorTourStep.getOrphaned());
        assertElement.assertCheckbox(BACKDROP, trackorTourStep.getBackdrop());
        assertElement.assertSelect(PLACEMENT, trackorTourStep.getPlacement());
        assertElement.assertSelect(ACTION, trackorTourStep.getAction());
        assertElement.assertText(FORM_NUMBER, trackorTourStep.getFormNumber());
        assertElement.assertText(TITLE_LABEL, trackorTourStep.getTitleLabel());
        fckEditor.checkValue(CONTENT_LABEL, trackorTourStep.getContentLabel());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, int rowIndex, TrackorTourStep trackorTourStep) {
        Map<Integer, String> gridVals = new HashMap<>();

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

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
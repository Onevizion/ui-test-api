package com.onevizion.uitest.api.helper.formdesigner;

import java.util.List;
import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementJs;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Listbox;
import com.onevizion.uitest.api.vo.FormDesignerField;
import com.onevizion.uitest.api.vo.ListboxElement;

@Component
public class FormDesigner {

    private static final String FIELD_LIST_SEARCH = "search_listBox";
    private static final String BUTTON_CLEAR_SEARCH = "clear_search_listBox";

    private static final String TOOLBAR = "toolbar";
    private static final String TOOLBAR_DELETE = "btnDelete";

    private static final String FORM = "formContent";
    private static final String FORM_ELEMENT = "item_form";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Element element;

    @Resource
    private Js js;

    @Resource
    private FormDesignerWait formDesignerWait;

    @Resource
    private FormDesignerJs formDesignerJs;

    @Resource
    private ElementJs elementJs;

    @Resource
    private Listbox listbox;

    public void fillSearch(String name) {
        element.moveToElementById(FIELD_LIST_SEARCH);
        seleniumSettings.getWebDriver().findElement(By.id(FIELD_LIST_SEARCH)).clear();
        seleniumSettings.getWebDriver().findElement(By.id(FIELD_LIST_SEARCH)).sendKeys(name);
    }

    public void clearSearch() {
        element.moveToElementById(FIELD_LIST_SEARCH);
        element.clickById(BUTTON_CLEAR_SEARCH);
    }

    public void addElementToForm(String fieldName, String elementId) {
        fillSearch(fieldName);
        WebElement listBoxfields = seleniumSettings.getWebDriver().findElement(By.id("listBox"));
        element.doubleClick(listBoxfields.findElement(By.id(elementId)));
        clearSearch();
    }

    public void addElementsToForm(List<String> elements) {
        addElementToFormById(elements.get(0)); //CHECKBOX
        addElementToFormById(elements.get(1)); //DATE
        addElementToFormById(elements.get(2)); //DB_DROP_DOWN
        addElementToFormById(elements.get(3)); //DB_SELECTOR
        addElementToFormById(elements.get(4)); //DROP_DOWN
        addElementToFormById(elements.get(5)); //ELECTRONIC_FILE
        addElementToFormById(elements.get(6)); //HYPERLINK
        addElementToFormById(elements.get(7)); //LATITUDE
        addElementToFormById(elements.get(8)); //LONGITUDE
        addElementToFormById(elements.get(9)); //MEMO
        addElementToFormById(elements.get(10)); //NUMBER
        addElementToFormById(elements.get(11)); //SELECTOR
        addElementToFormById(elements.get(12)); //TEXT
        addElementToFormById(elements.get(13)); //TRACKOR_SELECTOR
        addElementToFormById(elements.get(14)); //WIKI
        addElementToFormById(elements.get(15)); //MULTI_SELECTOR
        addElementToFormById(elements.get(16)); //DATE_TIME
        addElementToFormById(elements.get(17)); //TIME
        addElementToFormById(elements.get(18)); //TRACKOR_DROPDOWN
        addElementToFormById(elements.get(19)); //CALCULATED
        if (elements.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            addElementToFormById(elements.get(20)); //ROLLUP
        }
        addElementToFormById(elements.get(21)); //MULTI_TRACKOR_SELECTOR
    }

    private void addElementToFormById(String id) {
        element.doubleClickById(id);
        if (seleniumSettings.getBrowser().equals("chrome")) {
            AbstractSeleniumCore.sleep(400L); //TODO BUG Test-152076
        }
    }

    public void addElementToForm(String label) {
        fillSearch(label);

        List<ListboxElement> fields = listbox.getElements("listBox");
        for (ListboxElement field : fields) {
            if (label.equals(field.getLabel())) {
                element.doubleClick(field.getWebElement());
                break;
            }
        }

        clearSearch();
    }

    public void removeElementFromForm(String label) {
        boolean isElementRemoved = false;

        List<WebElement> fields = seleniumSettings.getWebDriver().findElement(By.id(FORM)).findElements(By.className(FORM_ELEMENT));
        for (WebElement field : fields) {
            List<WebElement> labels = field.findElements(By.tagName("label"));
            if (!labels.isEmpty()) {
                if (labels.get(0).getAttribute("innerText").trim().equals(label)) {
                    element.click(field);
                    clickDeleteButtonOnToolbar();
                    isElementRemoved = true;
                }
            } else {
                if ((field.getAttribute("title").equals("Blank Line") && field.getAttribute("innerText").trim().equals(label)) ||
                        (field.getAttribute("title").equals("Splitter") && "Splitter".equals(label))) {
                    element.click(field);
                    clickDeleteButtonOnToolbar();
                    isElementRemoved = true;
                }
            }

            if (isElementRemoved) {
                break;
            }
        }

        if (!isElementRemoved) {
            throw new SeleniumUnexpectedException("Element [" + label + "] not removed");
        }
    }

    public void removeElementsFromForm(List<String> elements) {
        WebElement formfields = seleniumSettings.getWebDriver().findElement(By.id(FORM));

        element.click(formfields.findElement(By.id(elements.get(0)))); //CHECKBOX
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(1)))); //DATE
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(2)))); //DB_DROP_DOWN
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(3)))); //DB_SELECTOR
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(4)))); //DROP_DOWN
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(5)))); //ELECTRONIC_FILE
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(6)))); //HYPERLINK
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(7)))); //LATITUDE
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(8)))); //LONGITUDE
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(9)))); //MEMO
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(10)))); //NUMBER
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(11)))); //SELECTOR
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(12)))); //TEXT
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(13)))); //TRACKOR_SELECTOR
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(14)))); //WIKI
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(15)))); //MULTI_SELECTOR
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(16)))); //DATE_TIME
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(17)))); //TIME
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(18)))); //TRACKOR_DROPDOWN
        clickDeleteButtonOnToolbar();
        element.click(formfields.findElement(By.id(elements.get(19)))); //CALCULATED
        clickDeleteButtonOnToolbar();
        if (elements.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            element.click(formfields.findElement(By.id(elements.get(20)))); //ROLLUP
            clickDeleteButtonOnToolbar();
        }
        element.click(formfields.findElement(By.id(elements.get(21)))); //MULTI_TRACKOR_SELECTOR
        clickDeleteButtonOnToolbar();
    }

    private void clickDeleteButtonOnToolbar() {
        WebElement deleteButton = seleniumSettings.getWebDriver().findElement(By.className(TOOLBAR)).findElement(By.id(TOOLBAR_DELETE));
        element.click(deleteButton);
    }

    public List<FormDesignerField> getElementsOnForm() {
        return formDesignerJs.getElementsOnForm();
    }

    public void checkElementOnFormByPositionAndName(List<FormDesignerField> fields, String row, String col, String fieldName) {
        Assert.assertEquals(fields.stream().filter(p -> p.getRow().equals(row) && p.getCol().equals(col) && p.getName().equals(fieldName)).count(), 1L, "Element with name [" + fieldName + "] not found");
    }

    public void checkElementOnFormByLabel(List<FormDesignerField> fields, String fieldLabel) {
        Assert.assertEquals(fields.stream().filter(p -> (p.getPrefix() + p.getLabel()).equals(fieldLabel)).count(), 1L, "Element with label [" + fieldLabel + "] not found");
    }

    public void checkElementOnFormById(List<FormDesignerField> fields, String fieldId) {
        Assert.assertEquals(fields.stream().filter(p -> p.getId().equals(fieldId)).count(), 1L, "Element with id [" + fieldId + "] not found");
    }

    public void checkElementsOnFormByIds(List<FormDesignerField> fields, List<String> fieldIds) {
        checkElementOnFormById(fields, fieldIds.get(0)); //CHECKBOX
        checkElementOnFormById(fields, fieldIds.get(1)); //DATE
        checkElementOnFormById(fields, fieldIds.get(2)); //DB_DROP_DOWN
        checkElementOnFormById(fields, fieldIds.get(3)); //DB_SELECTOR
        checkElementOnFormById(fields, fieldIds.get(4)); //DROP_DOWN
        checkElementOnFormById(fields, fieldIds.get(5)); //ELECTRONIC_FILE
        checkElementOnFormById(fields, fieldIds.get(6)); //HYPERLINK
        checkElementOnFormById(fields, fieldIds.get(7)); //LATITUDE
        checkElementOnFormById(fields, fieldIds.get(8)); //LONGITUDE
        checkElementOnFormById(fields, fieldIds.get(9)); //MEMO
        checkElementOnFormById(fields, fieldIds.get(10)); //NUMBER
        checkElementOnFormById(fields, fieldIds.get(11)); //SELECTOR
        checkElementOnFormById(fields, fieldIds.get(12)); //TEXT
        checkElementOnFormById(fields, fieldIds.get(13)); //TRACKOR_SELECTOR
        checkElementOnFormById(fields, fieldIds.get(14)); //WIKI
        checkElementOnFormById(fields, fieldIds.get(15)); //MULTI_SELECTOR
        checkElementOnFormById(fields, fieldIds.get(16)); //DATE_TIME
        checkElementOnFormById(fields, fieldIds.get(17)); //TIME
        checkElementOnFormById(fields, fieldIds.get(18)); //TRACKOR_DROPDOWN
        checkElementOnFormById(fields, fieldIds.get(19)); //CALCULATED
        if (fieldIds.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            checkElementOnFormById(fields, fieldIds.get(20)); //ROLLUP
        }
        checkElementOnFormById(fields, fieldIds.get(21)); //MULTI_TRACKOR_SELECTOR
    }

    public int getElementsCountOnForm() {
        List<WebElement> listBoxfields = seleniumSettings.getWebDriver().findElement(By.id(FORM)).findElements(By.className(FORM_ELEMENT));
        return listBoxfields.size();
    }

    public void switchToRootSubgroup() {
        listbox.switchToRootSubgroup("listBox");
    }

    public void switchToParentSubgroup() {
        listbox.switchToParentSubgroup("listBox");
    }

    public void switchToSubgroupInList(String label) {
        listbox.switchToSubgroupInList("listBox", label);
    }

    public void switchToFieldGroup() {
        listbox.switchToFieldGroup("listBox");
    }

    public void switchToTaskGroup() {
        listbox.switchToTaskGroup("listBox");
    }

    public void switchToDrillDownGroup() {
        listbox.switchToDrillDownGroup("listBox");
    }

    public void switchToMarkupGroup() {
        listbox.switchToMarkupGroup("listBox");
    }

    public void scrollFormToRight() {
        js.scrollNewDropDownLeft("form1", 2000L);
    }

    public void scrollFormToLeft() {
        js.scrollNewDropDownLeft("form1", 0L);
    }

    public void waitListBoxReady() {
        listbox.waitIsReadyListbox("listBox");
    }

    public void waitFormDesignerLoad() {
        formDesignerWait.waitFormDesignerLoad();
    }

    public void moveFieldToEndForm1(String sourceId) {
        moveFieldToEndForm(sourceId, "locatOption1");
    }

    public void moveFieldToEndForm2(String sourceId) {
        moveFieldToEndForm(sourceId, "locatOption2");
    }

    private void moveFieldToEndForm(String sourceId, String targetId) {
        WebElement source = seleniumSettings.getWebDriver().findElement(By.id(sourceId));
        WebElement target = seleniumSettings.getWebDriver().findElement(By.id(targetId));

        elementJs.dragAndDropPrepare();
        elementJs.dragAndDropDragStartTop(source);

        //in all elements except source and target
        //dragenter
        //dragover
        //dragleave

        elementJs.dragAndDropDragEnterTop(target);
        elementJs.dragAndDropDragOverTop(target);
        elementJs.dragAndDropDragOverBottom(target);
        String coord = elementJs.dragAndDropDropBottom(target);
        elementJs.dragAndDropDragEndBottom(source, coord);
    }

    public void moveFieldToList(String sourceId, String targetId) {
        WebElement source = seleniumSettings.getWebDriver().findElement(By.id(sourceId));
        WebElement target = seleniumSettings.getWebDriver().findElement(By.id(targetId));

        elementJs.dragAndDropPrepare();
        elementJs.dragAndDropDragStartTop(source);

        //in all elements except source and target
        //dragenter
        //dragover
        //dragleave

        elementJs.dragAndDropDragEnterTop(target);
        elementJs.dragAndDropDragOverTop(target);
        elementJs.dragAndDropDragOverBottom(target);
        String coord = elementJs.dragAndDropDropBottom(target);
        elementJs.dragAndDropDragEndBottom(source, coord);
    }

}
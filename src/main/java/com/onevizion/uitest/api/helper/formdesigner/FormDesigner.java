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
import com.onevizion.uitest.api.vo.FormDesignerField;

@Component
public class FormDesigner {

    private static final String FIELD_LIST_SEARCH = "searchField";
    private static final String BUTTON_CLEAR_SEARCH = "wrapperClearSearch";
    private static final String BUTTON_DELETE_ELEMENT = "btnDelElem";

    private static final String BUTTON_GROUP_FIELD = "cfg";
    private static final String BUTTON_GROUP_TASK = "tsg";
    private static final String BUTTON_GROUP_DRILLDOWN = "ddg";
    private static final String BUTTON_GROUP_MARKUP= "mug";

    private static final String FORM_ID = "formContent";

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
        WebElement listBoxfields = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent"));
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
            AbstractSeleniumCore.sleep(300L); //TODO BUG in chrome 75 and chromedriver 75. works without sleep in chrome 74 and chromedriver 74
        }
    }

    public void addElementToForm(String label) {
        fillSearch(label);
        List<WebElement> fields = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent")).findElements(By.className("record"));
        for (WebElement field : fields) {
            if (field.findElement(By.className("labelField")).getAttribute("innerText").trim().equals(label)) {
                element.doubleClick(field);
                break;
            }
        }
        clearSearch();
    }

    public void removeElementFromForm(String label) {
        boolean isElementRemoved = false;

        List<WebElement> fields = seleniumSettings.getWebDriver().findElement(By.id(FORM_ID)).findElements(By.xpath("div[contains(@class, 'cf')]"));
        for (WebElement field : fields) {
            List<WebElement> labels = field.findElements(By.tagName("label"));
            if (!labels.isEmpty()) {
                if (labels.get(0).getAttribute("innerText").trim().equals(label)) {
                    element.click(field);
                    element.click(field.findElement(By.id(BUTTON_DELETE_ELEMENT)));
                    isElementRemoved = true;
                }
            } else {
                if ((field.getAttribute("Title").equals("BlankLine") && field.getAttribute("innerText").trim().equals(label)) ||
                        (field.getAttribute("Title").equals("Splitter") && "Splitter".equals(label))) {
                    element.click(field);
                    element.click(field.findElement(By.id(BUTTON_DELETE_ELEMENT)));
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
        WebElement formfields = seleniumSettings.getWebDriver().findElement(By.id(FORM_ID));

        element.click(formfields.findElement(By.id(elements.get(0)))); //CHECKBOX
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(1)))); //DATE
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(2)))); //DB_DROP_DOWN
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(3)))); //DB_SELECTOR
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(4)))); //DROP_DOWN
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(5)))); //ELECTRONIC_FILE
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(6)))); //HYPERLINK
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(7)))); //LATITUDE
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(8)))); //LONGITUDE
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(9)))); //MEMO
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(10)))); //NUMBER
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(11)))); //SELECTOR
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(12)))); //TEXT
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(13)))); //TRACKOR_SELECTOR
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(14)))); //WIKI
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(15)))); //MULTI_SELECTOR
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(16)))); //DATE_TIME
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(17)))); //TIME
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(18)))); //TRACKOR_DROPDOWN
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(elements.get(19)))); //CALCULATED
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        if (elements.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            element.click(formfields.findElement(By.id(elements.get(20)))); //ROLLUP
            element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        }
        element.click(formfields.findElement(By.id(elements.get(21)))); //MULTI_TRACKOR_SELECTOR
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
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
        List<WebElement> listBoxfields = seleniumSettings.getWebDriver().findElement(By.id(FORM_ID)).findElements(By.xpath("div[contains(@class, 'cf')]"));
        return listBoxfields.size();
    }

    public void switchToRootSubgroup() {
        //elementHelper.click(seleniumSettings.getWebDriver().findElement(By.id("navPanel")).findElements(By.className("navLink")).get(0));
        element.click(seleniumSettings.getWebDriver().findElement(By.id("navPanel")).findElement(By.tagName("input")));
        waitListBoxReady();
    }

    public void switchToParentSubgroup() {
        List<WebElement> links = seleniumSettings.getWebDriver().findElement(By.id("navPanel")).findElements(By.className("navLink"));
        element.click(links.get(links.size() - 2));
        waitListBoxReady();
    }

    public void switchToSubgroup(String subgroupName) {
        element.click(seleniumSettings.getWebDriver().findElement(By.id("navPanel")).findElement(By.name(subgroupName)));
        waitListBoxReady();
    }

    public void switchToSubgroupInList(String label) {
        List<WebElement> subgroups = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent")).findElements(By.className("groupRecord"));
        for (WebElement subgroup : subgroups) {
            if (subgroup.getAttribute("innerText").trim().equals(label)) {
                element.click(subgroup);
                break;
            }
        }
        waitListBoxReady();
    }

    public void switchToFieldGroup() {
        element.clickById(BUTTON_GROUP_FIELD);
        waitListBoxReady();
    }

    public void switchToTaskGroup() {
        element.clickById(BUTTON_GROUP_TASK);
        waitListBoxReady();
    }

    public void switchToDrillDownGroup() {
        element.clickById(BUTTON_GROUP_DRILLDOWN);
        waitListBoxReady();
    }

    public void switchToMarkupGroup() {
        element.clickById(BUTTON_GROUP_MARKUP);
        waitListBoxReady();
    }

    public void scrollFormToRight() {
        js.scrollNewDropDownLeft("form1", 2000L);
    }

    public void scrollFormToLeft() {
        js.scrollNewDropDownLeft("form1", 0L);
    }

    public void waitListBoxReady() {
        formDesignerWait.waitListBoxReady();
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
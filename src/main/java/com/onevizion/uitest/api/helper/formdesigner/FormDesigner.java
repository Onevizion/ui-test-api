package com.onevizion.uitest.api.helper.formdesigner;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

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

    public void addElementsToForm(List<String> elements, String drillDownPrefix) {
        element.doubleClickById(drillDownPrefix + elements.get(0)); //CHECKBOX
        element.doubleClickById(drillDownPrefix + elements.get(1)); //DATE
        element.doubleClickById(drillDownPrefix + elements.get(2)); //DB_DROP_DOWN
        element.doubleClickById(drillDownPrefix + elements.get(3)); //DB_SELECTOR
        element.doubleClickById(drillDownPrefix + elements.get(4)); //DROP_DOWN
        element.doubleClickById(drillDownPrefix + elements.get(5)); //ELECTRONIC_FILE
        element.doubleClickById(drillDownPrefix + elements.get(6)); //HYPERLINK
        element.doubleClickById(drillDownPrefix + elements.get(7)); //LATITUDE
        element.doubleClickById(drillDownPrefix + elements.get(8)); //LONGITUDE
        element.doubleClickById(drillDownPrefix + elements.get(9)); //MEMO
        element.doubleClickById(drillDownPrefix + elements.get(10)); //NUMBER
        element.doubleClickById(drillDownPrefix + elements.get(11)); //SELECTOR
        element.doubleClickById(drillDownPrefix + elements.get(12)); //TEXT
        element.doubleClickById(drillDownPrefix + elements.get(13)); //TRACKOR_SELECTOR
        element.doubleClickById(drillDownPrefix + elements.get(14)); //WIKI
        element.doubleClickById(drillDownPrefix + elements.get(15)); //MULTI_SELECTOR
        element.doubleClickById(drillDownPrefix + elements.get(16)); //DATE_TIME
        element.doubleClickById(drillDownPrefix + elements.get(17)); //TIME
        element.doubleClickById(drillDownPrefix + elements.get(18)); //TRACKOR_DROPDOWN
        element.doubleClickById(drillDownPrefix + elements.get(19)); //CALCULATED
        if (elements.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            element.doubleClickById(drillDownPrefix + elements.get(20)); //ROLLUP
        }
        element.doubleClickById(drillDownPrefix + elements.get(21)); //MULTI_TRACKOR_SELECTOR
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

    public void removeElementsFromForm(List<String> elements, String drillDownPrefix) {
        WebElement formfields = seleniumSettings.getWebDriver().findElement(By.id(FORM_ID));

        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(0)))); //CHECKBOX
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(1)))); //DATE
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(2)))); //DB_DROP_DOWN
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(3)))); //DB_SELECTOR
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(4)))); //DROP_DOWN
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(5)))); //ELECTRONIC_FILE
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(6)))); //HYPERLINK
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(7)))); //LATITUDE
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(8)))); //LONGITUDE
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(9)))); //MEMO
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(10)))); //NUMBER
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(11)))); //SELECTOR
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(12)))); //TEXT
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(13)))); //TRACKOR_SELECTOR
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(14)))); //WIKI
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(15)))); //MULTI_SELECTOR
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(16)))); //DATE_TIME
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(17)))); //TIME
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(18)))); //TRACKOR_DROPDOWN
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(19)))); //CALCULATED
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        if (elements.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(20)))); //ROLLUP
            element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        }
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(21)))); //MULTI_TRACKOR_SELECTOR
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

    public void checkElementsOnFormByIds(List<FormDesignerField> fields, List<String> fieldIds, String drillDownIdx) {
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(0)); //CHECKBOX
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(1)); //DATE
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(2)); //DB_DROP_DOWN
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(3)); //DB_SELECTOR
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(4)); //DROP_DOWN
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(5)); //ELECTRONIC_FILE
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(6)); //HYPERLINK
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(7)); //LATITUDE
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(8)); //LONGITUDE
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(9)); //MEMO
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(10)); //NUMBER
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(11)); //SELECTOR
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(12)); //TEXT
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(13)); //TRACKOR_SELECTOR
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(14)); //WIKI
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(15)); //MULTI_SELECTOR
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(16)); //DATE_TIME
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(17)); //TIME
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(18)); //TRACKOR_DROPDOWN
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(19)); //CALCULATED
        if (fieldIds.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            checkElementOnFormById(fields, drillDownIdx + fieldIds.get(20)); //ROLLUP
        }
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(21)); //MULTI_TRACKOR_SELECTOR
    }

    public List<String> getElementsInList() {
        List<String> elements = new ArrayList<>();
        List<WebElement> fields = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent")).findElements(By.className("record"));
        for (WebElement field : fields) {
            elements.add(field.findElement(By.className("labelField")).getAttribute("innerText"));
        }
        return elements;
    }

    public void checkElementInList(List<String> elements, String text) {
        Assert.assertEquals(elements.stream().filter(p -> p.equals(text)).count(), 1L, "Element with text [" + text + "] not found in list");
    }

    public List<String> getElementsInRightList() {
        List<String> elements = new ArrayList<>();
        List<WebElement> fields = seleniumSettings.getWebDriver().findElement(By.id("rightListBox")).findElement(By.id("listBoxContent")).findElements(By.className("record"));
        for (WebElement field : fields) {
            elements.add(field.findElement(By.className("labelField")).getAttribute("innerText"));
        }
        return elements;
    }

    public void checkElementInRightList(List<String> elements, String text) {
        Assert.assertEquals(elements.stream().filter(p -> p.equals(text)).count(), 1L, "Element with text [" + text + "] not found in right list");
    }

    public void checkElementsInList(List<String> elements, String drillDownPrefix) {
        WebElement listBox = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent"));
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(0))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(0) + "] not found"); //CHECKBOX
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(1))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(1) + "] not found"); //DATE
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(2))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(2) + "] not found"); //DB_DROP_DOWN
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(3))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(3) + "] not found"); //DB_SELECTOR
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(4))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(4) + "] not found"); //DROP_DOWN
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(5))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(5) + "] not found"); //ELECTRONIC_FILE
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(6))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(6) + "] not found"); //HYPERLINK
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(7))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(7) + "] not found"); //LATITUDE
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(8))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(8) + "] not found"); //LONGITUDE
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(9))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(9) + "] not found"); //MEMO
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(10))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(10) + "] not found"); //NUMBER
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(11))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(11) + "] not found"); //SELECTOR
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(12))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(12) + "] not found"); //TEXT
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(13))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(13) + "] not found"); //TRACKOR_SELECTOR
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(14))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(14) + "] not found"); //WIKI
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(15))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(15) + "] not found"); //MULTI_SELECTOR
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(16))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(16) + "] not found"); //DATE_TIME
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(17))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(17) + "] not found"); //TIME
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(18))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(18) + "] not found"); //TRACKOR_DROPDOWN
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(19))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(19) + "] not found"); //CALCULATED
        if (elements.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(20))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(20) + "] not found"); //ROLLUP
        }
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(21))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(21) + "] not found"); //MULTI_TRACKOR_SELECTOR
    }

    public void checkSubgroupInList(String text) {
        boolean isExist = false;
        List<WebElement> subgroups = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent")).findElements(By.className("groupRecord"));
        for (WebElement subgroup : subgroups) {
            if (subgroup.getAttribute("innerText").trim().equals(text)) {
                if (isExist) {
                    throw new SeleniumUnexpectedException("Subgroup with text[" + text + "] found many times");
                }
                isExist = true;
            }
        }

        Assert.assertEquals(isExist, true);
    }

    public void checkElementsCountInList(int size) {
        List<WebElement> listBoxfields = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent")).findElements(By.className("record"));
        Assert.assertEquals(listBoxfields.size(), size);
    }

    public void checkElementsCountInRightList(int size) {
        List<WebElement> listBoxfields = seleniumSettings.getWebDriver().findElement(By.id("rightListBox")).findElement(By.id("listBoxContent")).findElements(By.className("record"));
        Assert.assertEquals(listBoxfields.size(), size);
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
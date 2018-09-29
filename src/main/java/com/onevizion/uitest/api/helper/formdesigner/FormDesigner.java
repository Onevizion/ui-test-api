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

    public void addElementsToForm(List<String> elements, String drillDownPrefix) {
        element.doubleClickById(drillDownPrefix + elements.get(0));
        element.doubleClickById(drillDownPrefix + elements.get(1));
        element.doubleClickById(drillDownPrefix + elements.get(2));
        element.doubleClickById(drillDownPrefix + elements.get(3));
        element.doubleClickById(drillDownPrefix + elements.get(4));
        element.doubleClickById(drillDownPrefix + elements.get(5));
        element.doubleClickById(drillDownPrefix + elements.get(6));
        element.doubleClickById(drillDownPrefix + elements.get(7));
        element.doubleClickById(drillDownPrefix + elements.get(8));
        element.doubleClickById(drillDownPrefix + elements.get(9));
        element.doubleClickById(drillDownPrefix + elements.get(10));
        element.doubleClickById(drillDownPrefix + elements.get(11));
        element.doubleClickById(drillDownPrefix + elements.get(12));
        element.doubleClickById(drillDownPrefix + elements.get(13));
        element.doubleClickById(drillDownPrefix + elements.get(14));
        element.doubleClickById(drillDownPrefix + elements.get(15));
        element.doubleClickById(drillDownPrefix + elements.get(16));
        element.doubleClickById(drillDownPrefix + elements.get(17));
        element.doubleClickById(drillDownPrefix + elements.get(18));
        element.doubleClickById(drillDownPrefix + elements.get(19));
        if (elements.get(20) != null) {
            element.doubleClickById(drillDownPrefix + elements.get(20));
        }
    }

    public void addElementToForm(String label) {
        fillSearch(label);
        //List<WebElement> fields = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent")).findElements(By.className("fieldRecord"));
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
            if (labels.size() > 0) {
                if (labels.get(0).getAttribute("innerText").trim().equals(label)) {
                    element.click(field);
                    element.click(field.findElement(By.id(BUTTON_DELETE_ELEMENT)));
                    isElementRemoved = true;
                }
            } else {
                if (field.getAttribute("Title").equals("BlankLine")) {
                    if (field.getAttribute("innerText").trim().equals(label)) {
                        element.click(field);
                        element.click(field.findElement(By.id(BUTTON_DELETE_ELEMENT)));
                        isElementRemoved = true;
                    }
                } else if (field.getAttribute("Title").equals("Splitter")) {
                    if (field.getAttribute("Title").equals(label)) {
                        element.click(field);
                        element.click(field.findElement(By.id(BUTTON_DELETE_ELEMENT)));
                        isElementRemoved = true;
                    }
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

        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(0))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(1))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(2))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(3))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(4))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(5))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(6))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(7))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(8))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(9))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(10))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(11))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(12))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(13))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(14))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(15))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(16))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(17))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(18))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(19))));
        element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        if (elements.get(20) != null) {
            element.click(formfields.findElement(By.id(drillDownPrefix + elements.get(20))));
            element.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        }
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
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(0));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(1));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(2));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(3));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(4));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(5));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(6));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(7));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(8));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(9));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(10));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(11));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(12));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(13));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(14));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(15));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(16));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(17));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(18));
        checkElementOnFormById(fields, drillDownIdx + fieldIds.get(19));
        if (fieldIds.get(20) != null) {
            checkElementOnFormById(fields, drillDownIdx + fieldIds.get(20));
        }
    }

    public void checkElementInList(String text) {
        boolean isExist = false;
        List<WebElement> fields = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent")).findElements(By.className("record"));
        for (WebElement field : fields) {
            if (field.findElement(By.className("labelField")).getAttribute("innerText").trim().equals(text)) {
                if (isExist) {
                    throw new SeleniumUnexpectedException("Element with text[" + text + "] found many times");
                }
                isExist = true;
            }
        }

        Assert.assertEquals(isExist, true);
    }

    public void checkElementInRightList(String text) {
        boolean isExist = false;
        List<WebElement> fields = seleniumSettings.getWebDriver().findElement(By.id("rightListBox")).findElement(By.id("listBoxContent")).findElements(By.className("record"));
        for (WebElement field : fields) {
            if (field.findElement(By.className("labelField")).getAttribute("innerText").trim().equals(text)) {
                if (isExist) {
                    throw new SeleniumUnexpectedException("Element with text[" + text + "] found many times");
                }
                isExist = true;
            }
        }

        Assert.assertEquals(isExist, true);
    }

    public void checkElementsInList(List<String> elements, String drillDownPrefix) {
        WebElement listBox = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent"));
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(0))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(0) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(1))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(1) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(2))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(2) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(3))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(3) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(4))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(4) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(5))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(5) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(6))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(6) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(7))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(7) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(8))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(8) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(9))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(9) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(10))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(10) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(11))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(11) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(12))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(12) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(13))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(13) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(14))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(14) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(15))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(15) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(16))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(16) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(17))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(17) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(18))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(18) + "] not found");
        Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(19))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(19) + "] not found");
        if (elements.get(20) != null) {
            Assert.assertEquals(listBox.findElements(By.id(drillDownPrefix + elements.get(20))).size(), 1, "Element with id [" + drillDownPrefix + elements.get(20) + "] not found");
        }
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

        elementJs.dragAndDropPrepare();

        elementJs.dragAndDropDragStart(source);
        AbstractSeleniumCore.sleep(100L);

        //in all elements except source and target
        //dragenter
        //dragover
        //dragleave

        WebElement target = seleniumSettings.getWebDriver().findElement(By.id(targetId));

        elementJs.dragAndDropDragEnter(target);
        AbstractSeleniumCore.sleep(100L);
        elementJs.dragAndDropDragOver(target);
        AbstractSeleniumCore.sleep(100L);
        elementJs.dragAndDropDrop(target);
        AbstractSeleniumCore.sleep(100L);

        elementJs.dragAndDropDragEnd(source);
        AbstractSeleniumCore.sleep(100L);
    }

    public void moveFieldToList(String sourceId, String targetId) {
        WebElement source = seleniumSettings.getWebDriver().findElement(By.id(sourceId));

        elementJs.dragAndDropPrepare();

        elementJs.dragAndDropDragStart(source);
        AbstractSeleniumCore.sleep(100L);

        //in all elements except source and target
        //dragenter
        //dragover
        //dragleave

        WebElement target = seleniumSettings.getWebDriver().findElement(By.id(targetId));

        elementJs.dragAndDropDragEnter(target);
        AbstractSeleniumCore.sleep(100L);
        elementJs.dragAndDropDragOver(target);
        AbstractSeleniumCore.sleep(100L);
        elementJs.dragAndDropDrop(target);
        AbstractSeleniumCore.sleep(100L);

        elementJs.dragAndDropDragEnd(source);
        AbstractSeleniumCore.sleep(100L);
    }

}
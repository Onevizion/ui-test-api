package com.onevizion.uitest.api.helper.formdesigner;

import java.util.List;
import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.ElementHelper;
import com.onevizion.uitest.api.helper.JsHelper;
import com.onevizion.uitest.api.vo.FormDesignerField;

@Component
public class FormDesigner {

    private final static String FIELD_LIST_SEARCH = "searchField";
    private final static String BUTTON_CLEAR_SEARCH = "wrappClearSearch";
    private final static String BUTTON_DELETE_ELEMENT = "btnDelElem";

    private final static String BUTTON_GROUP_FIELD = "cfg";
    private final static String BUTTON_GROUP_TASK = "tsg";
    private final static String BUTTON_GROUP_DRILLDOWN = "ddg";
    private final static String BUTTON_GROUP_MARKUP= "mug";

    private final static String FORM_ID = "formContent";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementHelper elementHelper;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private FormDesignerWait formDesignerWait;

    @Resource
    private FormDesignerJs formDesignerJs;

    public void fillSearch(String name) {
        elementHelper.moveToElementById(FIELD_LIST_SEARCH);
        seleniumSettings.getWebDriver().findElement(By.id(FIELD_LIST_SEARCH)).clear();
        seleniumSettings.getWebDriver().findElement(By.id(FIELD_LIST_SEARCH)).sendKeys(name);
    }

    public void clearSearch() {
        elementHelper.moveToElementById(FIELD_LIST_SEARCH);
        elementHelper.clickById(BUTTON_CLEAR_SEARCH);
    }

    public void addElementToForm(String fieldName, String elementId) {
        fillSearch(fieldName);
        WebElement listBoxfields = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent"));
        elementHelper.doubleClick(listBoxfields.findElement(By.id(elementId)));
        clearSearch();
    }

    public void addElementsToForm(List<String> elements, String drillDownPrefix) {
        elementHelper.doubleClickById(drillDownPrefix + elements.get(0));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(1));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(2));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(3));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(4));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(5));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(6));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(7));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(8));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(9));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(10));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(11));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(12));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(13));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(14));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(15));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(16));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(17));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(18));
        elementHelper.doubleClickById(drillDownPrefix + elements.get(19));
        if (elements.get(20) != null) {
            elementHelper.doubleClickById(drillDownPrefix + elements.get(20));
        }
    }

    public void addElementToForm(String label) {
        fillSearch(label);
        //List<WebElement> fields = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent")).findElements(By.className("fieldRecord"));
        List<WebElement> fields = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent")).findElements(By.className("record"));
        for (WebElement field : fields) {
            if (field.findElement(By.className("labelField")).getAttribute("innerText").trim().equals(label)) {
                elementHelper.doubleClick(field);
                break;
            }
        }
        clearSearch();
    }

    public void removeElementFromForm(String label) {
        List<WebElement> fields = seleniumSettings.getWebDriver().findElement(By.id(FORM_ID)).findElements(By.xpath("div[contains(@class, 'cf')]"));
        for (WebElement field : fields) {
            List<WebElement> labels = field.findElements(By.tagName("label"));
            if (labels.size() > 0) {
                if (labels.get(0).getAttribute("innerText").trim().equals(label)) {
                    elementHelper.click(field);
                    elementHelper.click(field.findElement(By.id(BUTTON_DELETE_ELEMENT)));
                    break;
                }
            } else {
                if (field.getAttribute("Title").equals("BlankLine")) {
                    if (field.getAttribute("innerText").trim().equals(label)) {
                        elementHelper.click(field);
                        elementHelper.click(field.findElement(By.id(BUTTON_DELETE_ELEMENT)));
                        break;
                    }
                } else if (field.getAttribute("Title").equals("Splitter")) {
                    if (field.getAttribute("Title").equals(label)) {
                        elementHelper.click(field);
                        elementHelper.click(field.findElement(By.id(BUTTON_DELETE_ELEMENT)));
                        break;
                    }
                }
            }
        }
    }

    public void removeElementsFromForm(List<String> elements, String drillDownPrefix) {
        WebElement formfields = seleniumSettings.getWebDriver().findElement(By.id(FORM_ID));

        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(0))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(1))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(2))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(3))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(4))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(5))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(6))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(7))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(8))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(9))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(10))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(11))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(12))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(13))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(14))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(15))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(16))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(17))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(18))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(19))));
        elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
        if (elements.get(20) != null) {
            elementHelper.click(formfields.findElement(By.id(drillDownPrefix + elements.get(20))));
            elementHelper.click(formfields.findElement(By.id(BUTTON_DELETE_ELEMENT)));
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
                if (isExist == true) {
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
                if (isExist == true) {
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
                if (isExist == true) {
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
        elementHelper.click(seleniumSettings.getWebDriver().findElement(By.id("navPanel")).findElement(By.tagName("input")));
        waitListBoxReady();
    }

    public void switchToParentSubgroup() {
        List<WebElement> links = seleniumSettings.getWebDriver().findElement(By.id("navPanel")).findElements(By.className("navLink"));
        elementHelper.click(links.get(links.size() - 2));
        waitListBoxReady();
    }

    public void switchToSubgroup(String subgroupName) {
        elementHelper.click(seleniumSettings.getWebDriver().findElement(By.id("navPanel")).findElement(By.name(subgroupName)));
        waitListBoxReady();
    }

    public void switchToSubgroupInList(String label) {
        List<WebElement> subgroups = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent")).findElements(By.className("groupRecord"));
        for (WebElement subgroup : subgroups) {
            if (subgroup.getAttribute("innerText").trim().equals(label)) {
                elementHelper.click(subgroup);
                break;
            }
        }
        waitListBoxReady();
    }

    public void switchToFieldGroup() {
        elementHelper.clickById(BUTTON_GROUP_FIELD);
        waitListBoxReady();
    }

    public void switchToTaskGroup() {
        elementHelper.clickById(BUTTON_GROUP_TASK);
        waitListBoxReady();
    }

    public void switchToDrillDownGroup() {
        elementHelper.clickById(BUTTON_GROUP_DRILLDOWN);
        waitListBoxReady();
    }

    public void switchToMarkupGroup() {
        elementHelper.clickById(BUTTON_GROUP_MARKUP);
        waitListBoxReady();
    }

    public void scrollFormToRight() {
        jsHelper.scrollNewDropDownLeft("form1", 2000L);
    }

    public void scrollFormToLeft() {
        jsHelper.scrollNewDropDownLeft("form1", 0L);
    }

    public void waitListBoxReady() {
        formDesignerWait.waitListBoxReady();
    }

    public void waitFormDesignerLoad() {
        formDesignerWait.waitFormDesignerLoad();
    }

}
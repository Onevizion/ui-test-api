package com.onevizion.uitest.api.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.vo.ListboxElement;

@Component
public class Listbox {

    private static final String NAV_PANEL = "navigation";
    private static final String BUTTON_GROUP_FIELD = "configfields";
    private static final String BUTTON_GROUP_TASK = "tasksfields";
    private static final String BUTTON_GROUP_DRILLDOWN = "drilldownfields";
    private static final String BUTTON_GROUP_DATE_PAIR = "datepairs";
    private static final String BUTTON_GROUP_MARKUP= "markup";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ListboxJs listboxJs;

    @Resource
    private ListboxWait listboxWait;

    @Resource
    private ElementWait elementWait;

    @Resource
    private Element element;

    public List<ListboxElement> getElements(String listboxId) {
        return getItems(listboxId);
    }

    public List<ListboxElement> getGroups(String listboxId) {
        return getItems(listboxId);
    }

    private List<ListboxElement> getItems(String listboxId) {
        List<ListboxElement> elements = new ArrayList<>();

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> webElements = seleniumSettings.getWebDriver().findElement(By.id(listboxId)).findElements(By.className("record"));
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        for (WebElement webElement : webElements) {
            if (webElement.getAttribute("class").contains("hidden")) {
                continue;
            }

            ListboxElement listboxElement = new ListboxElement();
            listboxElement.setWebElement(webElement);
            listboxElement.setId(webElement.getAttribute("id"));
            listboxElement.setLabel(webElement.findElement(By.className("labelField")).getAttribute("innerText"));
            elements.add(listboxElement);
        }

        return elements;
    }

    public void checkElementsCount(List<ListboxElement> elements, int size) {
        Assert.assertEquals(elements.size(), size);
    }

    public void checkElementByLabel(List<ListboxElement> elements, String label) {
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getLabel().equals(label)).count(), 1L, "Element with label [" + label + "] not found in Listbox");
    }

    public void checkElementByLabel(List<ListboxElement> elements, int position, String label) {
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getLabel().equals(label)).count(), 1L, "Element with label [" + label + "] not found in Listbox");
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getLabel().equals(label) && p == position - 1).count(), 1L, "Element with label [" + label + "] not found in position [" + position + "] in Listbox");
    }

    public void checkElementById(List<ListboxElement> elements, String id) {
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getId().equals(id)).count(), 1L, "Element with id [" + id + "] not found in Listbox");
    }

    public void checkElementById(List<ListboxElement> elements, int position, String id) {
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getId().equals(id)).count(), 1L, "Element with id [" + id + "] not found in Listbox");
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getId().equals(id) && p == position - 1).count(), 1L, "Element with id [" + id + "] not found in position [" + position + "] in Listbox");
    }

    public void checkElementsById(List<ListboxElement> elements, List<String> ids) {
        checkElementById(elements, ids.get(0)); //CHECKBOX
        checkElementById(elements, ids.get(1)); //DATE
        checkElementById(elements, ids.get(2)); //DB_DROP_DOWN
        checkElementById(elements, ids.get(3)); //DB_SELECTOR
        checkElementById(elements, ids.get(4)); //DROP_DOWN
        checkElementById(elements, ids.get(5)); //ELECTRONIC_FILE
        checkElementById(elements, ids.get(6)); //HYPERLINK
        checkElementById(elements, ids.get(7)); //LATITUDE
        checkElementById(elements, ids.get(8)); //LONGITUDE
        checkElementById(elements, ids.get(9)); //MEMO
        checkElementById(elements, ids.get(10)); //NUMBER
        checkElementById(elements, ids.get(11)); //SELECTOR
        checkElementById(elements, ids.get(12)); //TEXT
        checkElementById(elements, ids.get(13)); //TRACKOR_SELECTOR
        checkElementById(elements, ids.get(14)); //WIKI
        checkElementById(elements, ids.get(15)); //MULTI_SELECTOR
        checkElementById(elements, ids.get(16)); //DATE_TIME
        checkElementById(elements, ids.get(17)); //TIME
        checkElementById(elements, ids.get(18)); //TRACKOR_DROPDOWN
        checkElementById(elements, ids.get(19)); //CALCULATED
        if (ids.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            checkElementById(elements, ids.get(20)); //ROLLUP
        }
        checkElementById(elements, ids.get(21)); //MULTI_TRACKOR_SELECTOR
    }

    private ListboxElement getElementByLabel(List<ListboxElement> elements, String label) {
        checkElementByLabel(elements, label);
        Optional<ListboxElement> optional = elements.stream().filter(p -> p.getLabel().equals(label)).findFirst();
        return optional.orElseThrow(() -> new SeleniumUnexpectedException());
    }

    private ListboxElement getElementById(List<ListboxElement> elements, String id) {
        checkElementById(elements, id);
        Optional<ListboxElement> optional = elements.stream().filter(p -> p.getId().equals(id)).findFirst();
        return optional.orElseThrow(() -> new SeleniumUnexpectedException());
    }

    public void moveElementByLabel(List<ListboxElement> elements, String label, String buttonId) {
        ListboxElement listboxElement = getElementByLabel(elements, label);
        selectElement(listboxElement);

        seleniumSettings.getWebDriver().findElement(By.id(buttonId)).click();
    }

    public void moveElementById(List<ListboxElement> elements, String id, String buttonId) {
        ListboxElement listboxElement = getElementById(elements, id);
        selectElement(listboxElement);

        seleniumSettings.getWebDriver().findElement(By.id(buttonId)).click();
    }

    public void moveElementsById(List<ListboxElement> elements, List<String> ids, String buttonId) {
        moveElementById(elements, ids.get(0), buttonId); //CHECKBOX
        moveElementById(elements, ids.get(1), buttonId); //DATE
        moveElementById(elements, ids.get(2), buttonId); //DB_DROP_DOWN
        moveElementById(elements, ids.get(3), buttonId); //DB_SELECTOR
        moveElementById(elements, ids.get(4), buttonId); //DROP_DOWN
        moveElementById(elements, ids.get(5), buttonId); //ELECTRONIC_FILE
        moveElementById(elements, ids.get(6), buttonId); //HYPERLINK
        moveElementById(elements, ids.get(7), buttonId); //LATITUDE
        moveElementById(elements, ids.get(8), buttonId); //LONGITUDE
        moveElementById(elements, ids.get(9), buttonId); //MEMO
        moveElementById(elements, ids.get(10), buttonId); //NUMBER
        moveElementById(elements, ids.get(11), buttonId); //SELECTOR
        moveElementById(elements, ids.get(12), buttonId); //TEXT
        moveElementById(elements, ids.get(13), buttonId); //TRACKOR_SELECTOR
        moveElementById(elements, ids.get(14), buttonId); //WIKI
        moveElementById(elements, ids.get(15), buttonId); //MULTI_SELECTOR
        moveElementById(elements, ids.get(16), buttonId); //DATE_TIME
        moveElementById(elements, ids.get(17), buttonId); //TIME
        moveElementById(elements, ids.get(18), buttonId); //TRACKOR_DROPDOWN
        moveElementById(elements, ids.get(19), buttonId); //CALCULATED
        if (ids.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            moveElementById(elements, ids.get(20), buttonId); //ROLLUP
        }
        moveElementById(elements, ids.get(21), buttonId); //MULTI_TRACKOR_SELECTOR
    }

    public void selectElement(ListboxElement element) {
        listboxJs.scrollToElementInListbox(element.getWebElement());
        elementWait.waitElementVisible(element.getWebElement());
        element.getWebElement().click();
    }

    public void selectElementByLabel(List<ListboxElement> elements, String label) {
        ListboxElement listboxElement = getElementByLabel(elements, label);
        listboxJs.scrollToElementInListbox(listboxElement.getWebElement());
        elementWait.waitElementVisible(listboxElement.getWebElement());
        listboxElement.getWebElement().click();
    }

    public void switchToRootSubgroup(String listboxId) {
        element.click(seleniumSettings.getWebDriver().findElement(By.id(NAV_PANEL)).findElement(By.tagName("input")));
        listboxWait.waitIsReadyListbox(listboxId);
    }

    public void switchToParentSubgroup(String listboxId) {
        List<WebElement> links = seleniumSettings.getWebDriver().findElement(By.id(NAV_PANEL)).findElements(By.tagName("input"));
        element.click(links.get(links.size() - 2));
        listboxWait.waitIsReadyListbox(listboxId);
    }

    public void switchToSubgroupInList(String listboxId, String label) {
        List<ListboxElement> groups = getGroups(listboxId);
        ListboxElement group = getElementByLabel(groups, label);
        element.click(group.getWebElement());
        listboxWait.waitIsReadyListbox(listboxId);
    }

    public void switchToFieldGroup(String listboxId) {
        element.clickById(BUTTON_GROUP_FIELD);
        listboxWait.waitIsReadyListbox(listboxId);
    }

    public void switchToTaskGroup(String listboxId) {
        element.clickById(BUTTON_GROUP_TASK);
        listboxWait.waitIsReadyListbox(listboxId);
    }

    public void switchToDrillDownGroup(String listboxId) {
        element.clickById(BUTTON_GROUP_DRILLDOWN);
        listboxWait.waitIsReadyListbox(listboxId);
    }

    public void switchToDatePairGroup(String listboxId) {
        element.clickById(BUTTON_GROUP_DATE_PAIR);
        listboxWait.waitIsReadyListbox(listboxId);
    }

    public void switchToMarkupGroup(String listboxId) {
        element.clickById(BUTTON_GROUP_MARKUP);
        listboxWait.waitIsReadyListbox(listboxId);
    }

    public void waitIsReadyListbox(String listboxId) {
        listboxWait.waitIsReadyListbox(listboxId);
    }

}
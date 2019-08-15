package com.onevizion.uitest.api.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.vo.ListboxElement;

@Component
public class Listbox {

    @Resource
    private SeleniumSettings seleniumSettings;

    public List<ListboxElement> getElements(String listboxId) {
        List<ListboxElement> elements = new ArrayList<>();

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> webElements = seleniumSettings.getWebDriver().findElement(By.id(listboxId)).findElements(By.className("record"));
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        for (WebElement webElement : webElements) {
            ListboxElement listboxElement = new ListboxElement();
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

}
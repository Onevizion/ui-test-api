package com.onevizion.uitest.api.helper;

import java.util.ArrayList;
import java.util.List;
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

    public List<ListboxElement> getElements() {
        List<ListboxElement> elements = new ArrayList<>();

        List<WebElement> webElements = seleniumSettings.getWebDriver().findElement(By.id("rightListBox")).findElement(By.id("listBoxContent")).findElements(By.className("record"));
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
        Assert.assertEquals(IntStream.range(0, elements.size() - 1).filter(p -> elements.get(p).getLabel().equals(label)).count(), 1L, "Element with label [" + label + "] not found in Listbox");
    }

    public void checkElementByLabel(List<ListboxElement> elements, int position, String label) {
        Assert.assertEquals(IntStream.range(0, elements.size() - 1).filter(p -> elements.get(p).getLabel().equals(label)).count(), 1L, "Element with label [" + label + "] not found in Listbox");
        Assert.assertEquals(IntStream.range(0, elements.size() - 1).filter(p -> elements.get(p).getLabel().equals(label) && p == position - 1).count(), 1L, "Element with label [" + label + "] not found in position [" + position + "] in Listbox");
    }

    public void checkElementById(List<ListboxElement> elements, String id) {
        Assert.assertEquals(IntStream.range(0, elements.size() - 1).filter(p -> elements.get(p).getId().equals(id)).count(), 1L, "Element with id [" + id + "] not found in Listbox");
    }

    public void checkElementById(List<ListboxElement> elements, int position, String id) {
        Assert.assertEquals(IntStream.range(0, elements.size() - 1).filter(p -> elements.get(p).getId().equals(id)).count(), 1L, "Element with id [" + id + "] not found in Listbox");
        Assert.assertEquals(IntStream.range(0, elements.size() - 1).filter(p -> elements.get(p).getId().equals(id) && p == position - 1).count(), 1L, "Element with id [" + id + "] not found in position [" + position + "] in Listbox");
    }

}
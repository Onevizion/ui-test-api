package com.onevizion.uitest.api.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.vo.DropDownElement;

@Component
public class DropDown {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private DropDownJs dropDownJs;

    public List<DropDownElement> getElements(String dropDownId) {
        return getItems(dropDownId);
    }

    private List<DropDownElement> getItems(String dropDownId) {
        List<DropDownElement> elements = new ArrayList<>();

        WebElement dropDown = seleniumSettings.getWebDriver().findElement(By.id(dropDownId));

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> webElements = dropDown.findElements(By.className("item_select"));
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        for (WebElement webElement : webElements) {
            DropDownElement dropDownElement = new DropDownElement();
            dropDownElement.setWebElement(webElement);
            dropDownElement.setLabel(webElement.getAttribute("innerText"));
            elements.add(dropDownElement);
        }

        return elements;
    }

    public void checkElementsCount(List<DropDownElement> elements, int size) {
        Assert.assertEquals(elements.size(), size);
    }

    public void checkElementByLabel(List<DropDownElement> elements, String label) {
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getLabel().equals(label)).count(), 1L, "Element with label [" + label + "] not found in DropDown");
    }

    public void checkElementByLabel(List<DropDownElement> elements, int position, String label) {
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getLabel().equals(label)).count(), 1L, "Element with label [" + label + "] not found in DropDown");
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getLabel().equals(label) && p == position - 1).count(), 1L, "Element with label [" + label + "] not found in position [" + position + "] in DropDown");
    }

    private DropDownElement getElementByLabel(List<DropDownElement> elements, String label) {
        checkElementByLabel(elements, label);
        Optional<DropDownElement> optional = elements.stream().filter(p -> p.getLabel().equals(label)).findFirst();
        return optional.orElseThrow(() -> new SeleniumUnexpectedException());
    }

    public void selectElementByLabel(List<DropDownElement> elements, String label) {
        DropDownElement dropDownElement = getElementByLabel(elements, label);
        dropDownJs.scrollToElementInDropDown(dropDownElement.getWebElement());
        elementWait.waitElementVisible(dropDownElement.getWebElement());
        dropDownElement.getWebElement().click();
    }

    public void open(String dropDownId) {
        seleniumSettings.getWebDriver().findElement(By.id(dropDownId)).click();
    }

}
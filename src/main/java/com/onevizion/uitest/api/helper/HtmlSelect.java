package com.onevizion.uitest.api.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.vo.HtmlSelectElement;

@Component
public class HtmlSelect {

    @Autowired
    private SeleniumSettings seleniumSettings;

    public boolean isSelectOptionPresent(Select select, String visibleText) {
        boolean isException = true;
        while (isException) {
            try {
                for (WebElement option : select.getOptions()) {
                    if (option.getText().equals(visibleText)) {
                        return true;
                    }
                }
                isException = false;
            } catch (StaleElementReferenceException e) {
                isException = true;
            }
        }

        return false;
    }

    public List<HtmlSelectElement> getElements(String selectId) {
        List<HtmlSelectElement> elements = new ArrayList<>();

        List<WebElement> webElements = (new Select(seleniumSettings.getWebDriver().findElement(By.id(selectId)))).getOptions();
        for (WebElement webElement : webElements) {
            HtmlSelectElement htmlSelectElement = new HtmlSelectElement();
            htmlSelectElement.setLabel(webElement.getText());
            elements.add(htmlSelectElement);
        }

        return elements;
    }

    public void checkElementsCount(List<HtmlSelectElement> elements, int size) {
        Assert.assertEquals(elements.size(), size);
    }

    public void checkElementByLabel(List<HtmlSelectElement> elements, String label) {
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getLabel().equals(label)).count(), 1L, "Element with label [" + label + "] not found in Select");
    }

    /**
     * @deprecated (we should use listbox.checkElementByLabel when UI will be updated. fields on admin wf step form)
     */
    @Deprecated
    public boolean isSelectOptionPresent(WebElement select, String visibleText) {
        boolean isException = true;
        while (isException) {
            try {
                for (WebElement option : select.findElements(By.tagName("div"))) {
                    if (getOptionText(option, true).equals(visibleText)) {
                        return true;
                    }
                }
                isException = false;
            } catch (StaleElementReferenceException e) {
                isException = true;
            }
        }

        return false;
    }

    /**
     * @deprecated (we should use listbox.checkElementByLabel when UI will be updated. fields on admin wf step form and applets on reorder form)
     */
    @Deprecated
    public String getOptionText(final WebElement option, final boolean isNew) {
        if (StringUtils.isNotEmpty(option.findElement(By.tagName("span")).getAttribute("innerText"))) {
            return option.findElement(By.tagName("span")).getAttribute("innerText");
        } else {
            return option.findElement(By.tagName("span")).getAttribute("textContent");
        }
    }

}
package com.onevizion.uitest.api.helper.tab;

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
import com.onevizion.uitest.api.helper.ElementWait;

@Component
public class ListboxTab {

    //TODO need create AbstractListbox

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ListboxTabJs listboxTabJs;

    @Autowired
    private ElementWait elementWait;

    public List<ListboxElementTab> getTabs() {
        List<ListboxElementTab> tabs = new ArrayList<>();

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> webElements = seleniumSettings.getWebDriver().findElement(By.id("formMenu")).findElements(By.className("item_tab"));
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        for (WebElement webElement : webElements) {
            ListboxElementTab listboxElementTab = new ListboxElementTab();
            listboxElementTab.setWebElement(webElement);
            listboxElementTab.setId(webElement.getAttribute("id"));
            listboxElementTab.setLabel(webElement.findElement(By.className("tab_prefix")).getAttribute("innerText") + webElement.findElement(By.className("tab_label")).getAttribute("innerText"));
            listboxElementTab.setRowsCount(webElement.findElement(By.className("tab_counter")).getAttribute("innerText"));
            tabs.add(listboxElementTab);
        }

        return tabs;
    }

    public void checkTabsCount(List<ListboxElementTab> tabs, int size) {
        Assert.assertEquals(tabs.size(), size);
    }

    public void checkTab(List<ListboxElementTab> tabs, int position, String label, String rowsCount) {
        Assert.assertEquals(IntStream.range(0, tabs.size()).filter(p -> tabs.get(p).getLabel().equals(label)).count(), 1L, "Element with label [" + label + "] not found in Listbox");
        Assert.assertEquals(IntStream.range(0, tabs.size()).filter(p -> tabs.get(p).getLabel().equals(label) && p == position - 1).count(), 1L, "Element with label [" + label + "] not found in position [" + position + "] in Listbox");
        Assert.assertEquals(tabs.get(position - 1).getRowsCount(), rowsCount, "Element with rowsCount [" + rowsCount + "] not found in Listbox");
    }

    public ListboxElementTab getTabByLabel(List<ListboxElementTab> tabs, String label) {
        Assert.assertEquals(IntStream.range(0, tabs.size()).filter(p -> tabs.get(p).getLabel().equals(label)).count(), 1L, "Element with label [" + label + "] not found in Listbox");
        Optional<ListboxElementTab> optional = tabs.stream().filter(p -> p.getLabel().equals(label)).findFirst();
        return optional.orElseThrow(() -> new SeleniumUnexpectedException());
    }

    public ListboxElementTab getTabByPosition(List<ListboxElementTab> tabs, int position) {
        Assert.assertEquals(IntStream.range(0, tabs.size()).filter(p -> p == position - 1).count(), 1L, "Element with position [" + position + "] not found in Listbox");
        return tabs.get(position - 1);
    }

    public void selectTab(ListboxElementTab tab) {
        listboxTabJs.scrollToElementInListbox(tab.getWebElement());
        elementWait.waitElementVisible(tab.getWebElement());
        tab.getWebElement().click();
    }

}
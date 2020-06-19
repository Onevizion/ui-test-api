package com.onevizion.uitest.api.helper.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class ListboxChatSearchMessage {

    //TODO need create AbstractListbox

    @Autowired
    private SeleniumSettings seleniumSettings;

    public List<ListboxElementChatSearchMessage> getSearchMessagesOnForm() {
        return getItems(Chat.ID_MAIN_PANEL);
    }

    public List<ListboxElementChatSearchMessage> getSearchMessagesInGrid() {
        return getItems(Chat.ID_MAIN_PANEL + AbstractSeleniumCore.getGridIdx());
    }

    private List<ListboxElementChatSearchMessage> getItems(String listboxId) {
        List<ListboxElementChatSearchMessage> elements = new ArrayList<>();

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> webElements = seleniumSettings.getWebDriver().findElement(By.id(listboxId)).findElements(By.className("filterComment_item"));
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        for (WebElement webElement : webElements) {
            ListboxElementChatSearchMessage listboxElementChatSearchMessage = new ListboxElementChatSearchMessage();
            listboxElementChatSearchMessage.setWebElement(webElement);
            listboxElementChatSearchMessage.setId(webElement.getAttribute("id"));
            listboxElementChatSearchMessage.setText(webElement.findElement(By.className("fci_text")).getAttribute("innerText"));
            listboxElementChatSearchMessage.setDate(webElement.findElement(By.className("fci_date")).getAttribute("innerText"));
            listboxElementChatSearchMessage.setAuthor(webElement.findElement(By.className("fci_author")).getAttribute("innerText"));
            elements.add(listboxElementChatSearchMessage);
        }

        return elements;
    }

    public void checkElementsCount(List<ListboxElementChatSearchMessage> elements, int size) {
        Assert.assertEquals(elements.size(), size);
    }

    public void checkElementByText(List<ListboxElementChatSearchMessage> elements, int position, String text) {
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getText().equals(text) && p == position - 1).count(), 1L, "Element with text [" + text + "] not found in position [" + position + "] in Listbox");
    }

    public void checkElementByAuthor(List<ListboxElementChatSearchMessage> elements, int position, String author) {
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getAuthor().equals(author) && p == position - 1).count(), 1L, "Element with author [" + author + "] not found in position [" + position + "] in Listbox");
    }

    public void checkElementByDate(List<ListboxElementChatSearchMessage> elements, int position, String date) {
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getDate().equals(date) && p == position - 1).count(), 1L, "Element with date [" + date + "] not found in position [" + position + "] in Listbox");
    }

}
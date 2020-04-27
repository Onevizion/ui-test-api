package com.onevizion.uitest.api.helper.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class ListboxChatMessage {

    //TODO need create AbstractListbox

    @Resource
    private SeleniumSettings seleniumSettings;

    public List<ListboxElementChatMessage> getMessagesOnForm() {
        return getItems(Chat.ID_MAIN_PANEL);
    }

    public List<ListboxElementChatMessage> getMessagesInGrid() {
        return getItems(Chat.ID_MAIN_PANEL + AbstractSeleniumCore.getGridIdx());
    }

    private List<ListboxElementChatMessage> getItems(String listboxId) {
        List<ListboxElementChatMessage> elements = new ArrayList<>();

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> webElements = seleniumSettings.getWebDriver().findElement(By.id(listboxId)).findElements(By.className("comment_item"));
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        for (WebElement webElement : webElements) {
            ListboxElementChatMessage listboxElementChatMessage = new ListboxElementChatMessage();
            listboxElementChatMessage.setWebElement(webElement);
            listboxElementChatMessage.setId(webElement.getAttribute("id"));
            listboxElementChatMessage.setText(webElement.findElement(By.className("ci_textMessage")).getAttribute("innerText"));
            listboxElementChatMessage.setDate(webElement.findElement(By.className("ci_date")).getAttribute("innerText"));
            listboxElementChatMessage.setAuthor(webElement.findElement(By.className("ci_author")).getAttribute("innerText"));
            elements.add(listboxElementChatMessage);
        }

        return elements;
    }

    public void checkElementsCount(List<ListboxElementChatMessage> elements, int size) {
        Assert.assertEquals(elements.size(), size);
    }

    public void checkElementByText(List<ListboxElementChatMessage> elements, int position, String text) {
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getText().equals(text) && p == position - 1).count(), 1L, "Element with text [" + text + "] not found in position [" + position + "] in Listbox");
    }

    public void checkElementByAuthor(List<ListboxElementChatMessage> elements, int position, String author) {
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getAuthor().equals(author) && p == position - 1).count(), 1L, "Element with author [" + author + "] not found in position [" + position + "] in Listbox");
    }

}
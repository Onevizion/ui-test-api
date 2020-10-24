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
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.jquery.Jquery;

@Component
public class ListboxChatMessage {

    //TODO need create AbstractListbox

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private ChatJs chatJs;

    @Autowired
    private Jquery jquery;

    @Autowired
    private ListboxChatMessage listboxChatMessage;
    
    @Autowired
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

    public void checkElementByDate(List<ListboxElementChatMessage> elements, int position, String date) {
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getDate().equals(date) && p == position - 1).count(), 1L, "Element with date [" + date + "] not found in position [" + position + "] in Listbox");
    }

    public void scrollUp() {
        chatJs.scrollUp();

        List<ListboxElementChatMessage> loadedMessages = listboxChatMessage.getMessagesInGrid();
        String FirstMessageId = loadedMessages.get(0).getId().toString();
        WebElement FirstMessage = seleniumSettings.getWebDriver().findElement(By.id("chatPanel0")).findElement(By.id(FirstMessageId));
        elementWait.waitElementVisible(FirstMessage);

        jquery.waitLoad();
    }

    public void scrollDown() {
        chatJs.scrollDown();

        List<ListboxElementChatMessage> loadedMessages = listboxChatMessage.getMessagesInGrid();
        String LastMessageId = loadedMessages.get(loadedMessages.size() - 1).getId().toString();
        WebElement LastMessage = seleniumSettings.getWebDriver().findElement(By.id("chatPanel0")).findElement(By.id(LastMessageId));
        elementWait.waitElementVisible(LastMessage);

        jquery.waitLoad();
    }

}
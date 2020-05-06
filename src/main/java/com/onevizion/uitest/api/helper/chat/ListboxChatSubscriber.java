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
public class ListboxChatSubscriber {

    //TODO need create AbstractListbox

    @Resource
    private SeleniumSettings seleniumSettings;

    public List<ListboxElementChatSubscriber> getSubscribersOnForm() {
        return getItems(Chat.ID_MAIN_PANEL);
    }

    public List<ListboxElementChatSubscriber> getSubscribersInGrid() {
        return getItems(Chat.ID_MAIN_PANEL + AbstractSeleniumCore.getGridIdx());
    }

    private List<ListboxElementChatSubscriber> getItems(String listboxId) {
        List<ListboxElementChatSubscriber> elements = new ArrayList<>();

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> webElements = seleniumSettings.getWebDriver().findElement(By.id(listboxId)).findElements(By.className("user_item"));
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        for (WebElement webElement : webElements) {
            ListboxElementChatSubscriber listboxElementChatSubscriber = new ListboxElementChatSubscriber();
            listboxElementChatSubscriber.setWebElement(webElement);
            listboxElementChatSubscriber.setId(webElement.getAttribute("id"));
            listboxElementChatSubscriber.setName(webElement.findElement(By.className("ui_name")).getAttribute("innerText"));
            elements.add(listboxElementChatSubscriber);
        }

        return elements;
    }

    public void checkElementsCount(List<ListboxElementChatSubscriber> elements, int size) {
        Assert.assertEquals(elements.size(), size);
    }

    public void checkElementByName(List<ListboxElementChatSubscriber> elements, int position, String text) {
        Assert.assertEquals(IntStream.range(0, elements.size()).filter(p -> elements.get(p).getName().equals(text) && p == position - 1).count(), 1L, "Element with name [" + text + "] not found in position [" + position + "] in Listbox");
    }

}
package com.onevizion.uitest.api.helper.clipboard;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class Clipboard {

    private static final String ID = "textarea for qs";

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ClipboardJs clipboardJs;

    public void pasteTextToClipboard(String text) {
        createTextareaForQs();

        seleniumSettings.getWebDriver().findElement(By.id(ID)).clear();
        seleniumSettings.getWebDriver().findElement(By.id(ID)).sendKeys(text);

        Actions action = new Actions(seleniumSettings.getWebDriver());
        action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
        action.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).perform();

        deleteTextareaForQs();
    }

    private void createTextareaForQs() {
        clipboardJs.createTextareaForQs(ID);
    }

    private void deleteTextareaForQs() {
        clipboardJs.deleteTextareaForQs(ID);
    }

}
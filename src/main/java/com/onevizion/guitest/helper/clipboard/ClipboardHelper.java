package com.onevizion.guitest.helper.clipboard;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.SeleniumSettings;

@Component
public class ClipboardHelper {

    private final static String ID = "textarea for qs";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ClipboardJsHelper clipboardJsHelper;

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
        clipboardJsHelper.createTextareaForQs(ID);
    }

    private void deleteTextareaForQs() {
        clipboardJsHelper.deleteTextareaForQs(ID);
    }

}
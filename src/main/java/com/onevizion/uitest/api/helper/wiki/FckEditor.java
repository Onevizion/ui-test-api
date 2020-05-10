package com.onevizion.uitest.api.helper.wiki;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.clipboard.Clipboard;

@Component
public class FckEditor {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Wait wait;

    @Resource
    private Element element;

    @Resource
    private FckEditorWait fckEditorWait;

    @Resource
    private Clipboard clipboard;

    public void setValue(String name, String value) {
        if (value.startsWith("<p>") && value.endsWith("</p>")) {
            value = value.substring(3, value.length() - 4);
        }

        wait.waitWebElement(By.id(name));
        wait.waitWebElement(By.id("cke_" + name));
        element.moveToElementById("cke_" + name);
        fckEditorWait.waitReady(name);
        WebElement div = seleniumSettings.getWebDriver().findElement(By.id("cke_" + name));
        WebElement iframe = div.findElement(By.tagName("iframe"));
        seleniumSettings.getWebDriver().switchTo().frame(iframe);

        WebElement body = seleniumSettings.getWebDriver().findElement(By.tagName("body"));
        body.click();
        String prevVal = body.getAttribute("innerHTML");
        Actions actionObject = new Actions(seleniumSettings.getWebDriver());
        for (int i = 0; i < prevVal.length(); i++) {
            body.click();
            actionObject.sendKeys(Keys.ARROW_RIGHT).perform();
            actionObject.sendKeys(Keys.BACK_SPACE).perform();
        }
        if (value.length() > 0) {
            actionObject.sendKeys(value).perform();
            actionObject.sendKeys(" ").perform();
            actionObject.sendKeys(Keys.BACK_SPACE).perform();
        }

        seleniumSettings.getWebDriver().switchTo().parentFrame();
    }

    public void setValueFromClipboard(String name, String value) {
        clipboard.pasteTextToClipboard(value);

        wait.waitWebElement(By.id(name));
        wait.waitWebElement(By.id("cke_" + name));
        element.moveToElementById("cke_" + name);
        fckEditorWait.waitReady(name);
        WebElement div = seleniumSettings.getWebDriver().findElement(By.id("cke_" + name));
        WebElement iframe = div.findElement(By.tagName("iframe"));
        seleniumSettings.getWebDriver().switchTo().frame(iframe);

        seleniumSettings.getWebDriver().findElement(By.tagName("body")).click();
        Actions action = new Actions(seleniumSettings.getWebDriver());
        action.keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();

        seleniumSettings.getWebDriver().switchTo().parentFrame();
    }

    public void checkValue(String name, String expectedValue) {
        if (expectedValue.startsWith("<p>") && expectedValue.endsWith("</p>")) {
            expectedValue = expectedValue.substring(3, expectedValue.length() - 4);
        }

        wait.waitWebElement(By.id(name));
        wait.waitWebElement(By.id("cke_" + name));
        element.moveToElementById("cke_" + name);
        fckEditorWait.waitReady(name);
        WebElement div = seleniumSettings.getWebDriver().findElement(By.id("cke_" + name));
        WebElement iframe = div.findElement(By.tagName("iframe"));
        seleniumSettings.getWebDriver().switchTo().frame(iframe);
        String actualValue = seleniumSettings.getWebDriver().findElement(By.tagName("body")).getAttribute("innerHTML").trim();
        seleniumSettings.getWebDriver().switchTo().parentFrame();

        actualValue = actualValue.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_1, AbstractSeleniumCore.SPECIAL_CHARACTERS_1);
        actualValue = actualValue.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_2, AbstractSeleniumCore.SPECIAL_CHARACTERS_2);
        actualValue = actualValue.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_3, AbstractSeleniumCore.SPECIAL_CHARACTERS_3);
        actualValue = actualValue.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_4, AbstractSeleniumCore.SPECIAL_CHARACTERS_4);

        if ("<br>".equals(actualValue) || "<p><br></p>".equals(actualValue)) {
            actualValue = "";
        }

        if (actualValue.startsWith("<p>") && actualValue.endsWith("<br></p>")) {
            actualValue = actualValue.substring(3, actualValue.length() - 8);
        }

        if (actualValue.startsWith("<p>") && actualValue.endsWith("</p>")) {
            actualValue = actualValue.substring(3, actualValue.length() - 4);
        }

        Assert.assertEquals(actualValue, expectedValue, "Element with name=[" + name + "] has wrong value");
    }

    public void checkValueReadOnly(String name, String expectedValue) {
        seleniumSettings.getWebDriver().switchTo().frame(name);
        String actualValue = seleniumSettings.getWebDriver().findElement(By.tagName("body")).getAttribute("innerHTML").trim();
        seleniumSettings.getWebDriver().switchTo().parentFrame();

        actualValue = actualValue.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_1, AbstractSeleniumCore.SPECIAL_CHARACTERS_1);
        actualValue = actualValue.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_2, AbstractSeleniumCore.SPECIAL_CHARACTERS_2);
        actualValue = actualValue.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_3, AbstractSeleniumCore.SPECIAL_CHARACTERS_3);
        actualValue = actualValue.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_4, AbstractSeleniumCore.SPECIAL_CHARACTERS_4);
        Assert.assertEquals(actualValue, expectedValue, "Element with name=[" + name + "] has wrong value");
    }

}
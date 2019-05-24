package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumLogger;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class FckEditor {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private SeleniumLogger seleniumLogger;

    @Resource
    private Wait wait;

    @Resource
    private FckEditorWait fckEditorWait;

    public void setValue(String name, String value) {
        if (value.startsWith("<p>") && value.endsWith("</p>")) {
            value = value.substring(3, value.length() - 4);
        }

        wait.waitWebElement(By.id(name));
        wait.waitWebElement(By.id("cke_" + name));
        fckEditorWait.waitReady(name);
        WebElement div = seleniumSettings.getWebDriver().findElement(By.id("cke_" + name));
        WebElement iframe = div.findElement(By.tagName("iframe"));
        seleniumSettings.getWebDriver().switchTo().frame(iframe);
        //WebElement body = seleniumSettings.getWebDriver().findElement(By.tagName("body"));
        //body.clear();
        //if (value.length() > 0) {
        //    body.sendKeys(value);
        //} else {
        //    body.click();
        //    body.sendKeys(" ");
        //    Actions actionObject = new Actions(seleniumSettings.getWebDriver());
        //    actionObject.sendKeys(Keys.BACK_SPACE).perform();
        //}
        setValue(value);
        seleniumSettings.getWebDriver().switchTo().parentFrame();
    }

    public void checkValue(String name, String expectedValue) {
      wait.waitWebElement(By.id(name));
      wait.waitWebElement(By.id("cke_" + name));
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

    private void setValue(String value) {
        boolean isException = true;
        while (isException) {
            WebElement body = seleniumSettings.getWebDriver().findElement(By.tagName("body"));

            body.click();
            String prevVal = body.getAttribute("innerHTML");
            Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            for (int i = 0; i < prevVal.length(); i++) {
                actionObject.sendKeys(Keys.ARROW_RIGHT).perform();
            }
            for (int i = 0; i < prevVal.length(); i++) {
                actionObject.sendKeys(Keys.BACK_SPACE).perform();
            }
            actionObject.sendKeys(value).perform();
            //body.clear();
            //if (value.length() > 0) {
            //    body.sendKeys(value);
            //} else {
            //    body.click();
            //    body.sendKeys(" ");
            //    Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            //    actionObject.sendKeys(Keys.BACK_SPACE).perform();
            //}

            String actualValue = body.getAttribute("innerHTML").trim();

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

            if (value.equals(actualValue)) {
                isException = false;
            } else {
                seleniumLogger.warn(seleniumSettings.getTestName() + " FckEditor.setValue works wrong. value[" + value + "] actualValue[" + actualValue + "]");
            }
        }
    }

}
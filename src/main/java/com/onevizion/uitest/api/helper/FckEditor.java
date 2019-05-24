package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class FckEditor {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Wait wait;

    public void setValue(String name, String value) {
        if (value.startsWith("<p>") && value.endsWith("</p>")) {
            value = value.substring(3, value.length() - 4);
        }

        wait.waitWebElement(By.id(name));
        wait.waitWebElement(By.id("cke_" + name));
        WebElement div = seleniumSettings.getWebDriver().findElement(By.id("cke_" + name));
        WebElement iframe = div.findElement(By.tagName("iframe"));
        seleniumSettings.getWebDriver().switchTo().frame(iframe);
        WebElement body = seleniumSettings.getWebDriver().findElement(By.tagName("body"));
        body.clear();
        body.sendKeys(value);
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
      Assert.assertEquals(actualValue, expectedValue, "Element with name=[" + name + "] has wrong value");
    }

    public void checkValueReadOnly(String name, String expectedValue) {
        seleniumSettings.getWebDriver().switchTo().frame(name);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.tagName("body")).getAttribute("innerHTML").trim();
        seleniumSettings.getWebDriver().switchTo().parentFrame();

        actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_1, AbstractSeleniumCore.SPECIAL_CHARACTERS_1);
        actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_2, AbstractSeleniumCore.SPECIAL_CHARACTERS_2);
        actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_3, AbstractSeleniumCore.SPECIAL_CHARACTERS_3);
        actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_4, AbstractSeleniumCore.SPECIAL_CHARACTERS_4);
        Assert.assertEquals(actualVal, expectedValue, "Element with name=[" + name + "] has wrong value");
    }

}
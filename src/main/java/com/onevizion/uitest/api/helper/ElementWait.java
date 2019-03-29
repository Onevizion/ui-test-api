package com.onevizion.uitest.api.helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class ElementWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementJs elementJs;

    /*public void waitElement(WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Element [" + element + "] is failed")
            .ignoring(NullPointerException.class)
            .until(webdriver -> {
                return element;
            });
    }*/

    public void waitElementByName(String name) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Element name=[" + name + "] is failed")
            .ignoring(NullPointerException.class)
            .until(webdriver -> webdriver.findElement(By.name(name)));
    }

    public void waitElementById(String id) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Element id=[" + id + "] is failed")
            .ignoring(NullPointerException.class)
            .until(webdriver -> webdriver.findElement(By.id(id)));
    }

    @Deprecated
    public void waitElementByBy(By by) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Element by=[" + by + "] is failed")
            .ignoring(NullPointerException.class)
            .until(webdriver -> webdriver.findElement(by));
    }

    public void waitElementVisible(WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element [" + element + "] visibility failed")
            .ignoring(StaleElementReferenceException.class)
            .until(ExpectedConditions.visibilityOf(element));
    }

    public void waitElementVisibleByName(String name) {
        waitElementByName(name);

        WebElement element = seleniumSettings.getWebDriver().findElement(By.name(name));

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element name=[" + name + "] visibility failed")
            .ignoring(StaleElementReferenceException.class)
            .until(ExpectedConditions.visibilityOf(element));
    }

    public void waitElementVisibleById(String id) {
        waitElementById(id);

        WebElement element = seleniumSettings.getWebDriver().findElement(By.id(id));

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element id=[" + id + "] visibility failed")
            .ignoring(StaleElementReferenceException.class)
            .until(ExpectedConditions.visibilityOf(element));
    }

    public void waitElementNotVisible(WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element [" + element + "] invisibility failed")
            .ignoring(StaleElementReferenceException.class)
            .until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitElementNotVisibleByName(String name) {
        waitElementByName(name);

        WebElement element = seleniumSettings.getWebDriver().findElement(By.name(name));

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element name=[" + name + "] invisibility failed")
            .ignoring(StaleElementReferenceException.class)
            .until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitElementNotVisibleById(String id) {
        waitElementById(id);

        WebElement element = seleniumSettings.getWebDriver().findElement(By.id(id));

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element id=[" + id + "] invisibility failed")
            .ignoring(StaleElementReferenceException.class)
            .until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitElementDisplay(WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element [" + element + "] not displayed")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> element.isDisplayed());
    }

    public void waitElementDisplayByName(String name) {
        waitElementByName(name);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element name=[" + name + "] not displayed")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> webdriver.findElement(By.name(name)).isDisplayed());
    }

    public void waitElementDisplayById(String id) {
        waitElementById(id);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element id=[" + id + "] not displayed")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> webdriver.findElement(By.id(id)).isDisplayed());
    }

    public void waitElementNotDisplayById(String id) {
        waitElementById(id);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element id=[" + id + "] displayed")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !webdriver.findElement(By.id(id)).isDisplayed());
    }

    public void waitElementAttribute(WebElement element, String attribute, String attributeValue) {
        Supplier<String> actualValueSupplier = ()-> element.getAttribute(attribute);
        Supplier<String> messageSupplier = ()-> "Waiting for Element [" + element + "] attribute=[" + attribute + "] expectedVal=[" + attributeValue + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> actualValueSupplier.get().equals(attributeValue));
    }

    public void waitElementAttributeByName(String name, String attribute, String attributeValue) {
        waitElementByName(name);

        Supplier<String> actualValueSupplier = ()-> seleniumSettings.getWebDriver().findElement(By.name(name)).getAttribute(attribute);
        Supplier<String> messageSupplier = ()-> "Waiting for Element name=[" + name + "] attribute=[" + attribute + "] expectedVal=[" + attributeValue + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> actualValueSupplier.get().equals(attributeValue));
    }

    public void waitElementAttributeById(String id, String attribute, String attributeValue) {
        waitElementById(id);

        Supplier<String> actualValueSupplier = ()-> seleniumSettings.getWebDriver().findElement(By.id(id)).getAttribute(attribute);
        Supplier<String> messageSupplier = ()-> "Waiting for Element id=[" + id + "] attribute=[" + attribute + "] expectedVal=[" + attributeValue + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> actualValueSupplier.get().equals(attributeValue));
    }

    public void waitElementAttributeById(String id, String attribute, List<String> attributeValue, String attributeSeparator) {
        waitElementById(id);

        Collections.sort(attributeValue);

        Supplier<List<String>> actualValueSupplier = ()-> {
            List<String> actualAttributeValue = Arrays.asList(seleniumSettings.getWebDriver().findElement(By.id(id)).getAttribute(attribute).split(attributeSeparator));
            for (int i = 0; i < actualAttributeValue.size(); i++) {
                actualAttributeValue.set(i, actualAttributeValue.get(i).trim());
            }
            Collections.sort(actualAttributeValue);
            return actualAttributeValue;
        };
        Supplier<String> messageSupplier = ()-> "Waiting for Element id=[" + id + "] attribute=[" + attribute + "] expectedVal=[" + attributeValue + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> actualValueSupplier.get().equals(attributeValue));
    }

    public void waitElementDisabled(WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element [" + element + "] is enabled")
            .until(webdriver -> !element.isEnabled());
    }

    public void waitElementDisabledByName(String name) {
        waitElementByName(name);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element name=[" + name + "] is enabled")
            .until(webdriver -> !webdriver.findElement(By.name(name)).isEnabled());
    }

    public void waitElementDisabledById(String id) {
        waitElementById(id);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element id=[" + id + "] is enabled")
            .until(webdriver -> !webdriver.findElement(By.id(id)).isEnabled());
    }

    public void waitElementEnabled(WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element [" + element + "] is disabled.")
            .until(webdriver -> element.isEnabled());
    }

    public void waitElementEnabledByName(String name) {
        waitElementByName(name);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element name=[" + name + "] is disabled.")
            .until(webdriver -> webdriver.findElement(By.name(name)).isEnabled());
    }

    public void waitElementEnabledById(String id) {
        waitElementById(id);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element id=[" + id + "] is disabled.")
            .until(webdriver -> webdriver.findElement(By.id(id)).isEnabled());
    }

    @Deprecated
    public void waitElementEnabledByBy(By by) {
        waitElementByBy(by);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element by=[" + by + "] is disabled.")
            .until(webdriver -> webdriver.findElement(by).isEnabled());
    }

    public void waitElementAnimatedFinish(WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element [" + element + "] is animated.")
            .until(webdriver -> elementJs.isElementAnimatedFinish(element));
    }

    public void waitElementAnimatedFinishByName(String name) {
        waitElementByName(name);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element name=[" + name + "] is animated.")
            .until(webdriver -> elementJs.isElementAnimatedFinishByName(name));
    }

    public void waitElementAnimatedFinishById(String id) {
        waitElementById(id);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element id=[" + id + "] is animated.")
            .until(webdriver -> elementJs.isElementAnimatedFinishById(id));
    }

    public void waitElementVelocityAnimatedFinish(WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element [" + element + "] Velocity is animated.")
            .until(webdriver -> elementJs.isElementVelocityAnimatedFinish(element));
    }

    public void waitElementVelocityAnimatedFinishByName(String name) {
        waitElementByName(name);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element name=[" + name + "] Velocity is animated.")
            .until(webdriver -> elementJs.isElementVelocityAnimatedFinishByName(name));
    }

    public void waitElementVelocityAnimatedFinishById(String id) {
        waitElementById(id);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element id=[" + id + "] Velocity is animated.")
            .until(webdriver -> elementJs.isElementVelocityAnimatedFinishById(id));
    }

    public void waitElementNotExist(WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element [" + element + "] exist.")
            .until(ExpectedConditions.stalenessOf(element));
    }

}
package com.onevizion.uitest.api.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumLogger;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

@Component
public class Element {

    @Autowired
    private ElementJs elementJs;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private SeleniumLogger seleniumLogger;

    public void moveToElement(WebElement element) {
        elementJs.moveToElement(element);

        try {
            Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            actionObject.moveToElement(element).perform();
        } catch (WebDriverException e) { //firefox 59 throw WebDriverException instead of MoveTargetOutOfBoundsException or instead success execution
            seleniumLogger.warn("Exception in moveToElement");
        }

        if (seleniumSettings.getBrowser().equals("firefox")) {
            elementJs.mouseMove(element);
        }
    }

    public void moveToElementByName(String name) {
        elementWait.waitElementByName(name);
        elementJs.moveToElementByName(name);

        try {
            Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            actionObject.moveToElement(seleniumSettings.getWebDriver().findElement(By.name(name))).perform();
        } catch (WebDriverException e) { //firefox 59 throw WebDriverException instead of MoveTargetOutOfBoundsException or instead success execution
            seleniumLogger.warn("Exception in moveToElementByName");
        }

        if (seleniumSettings.getBrowser().equals("firefox")) {
            elementJs.mouseMoveByName(name);
        }
    }

    public void moveToElementById(String id) {
        elementWait.waitElementById(id);
        elementJs.moveToElementById(id);

        try {
            Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            actionObject.moveToElement(seleniumSettings.getWebDriver().findElement(By.id(id))).perform();
        } catch (WebDriverException e) { //firefox 59 throw WebDriverException instead of MoveTargetOutOfBoundsException or instead success execution
            seleniumLogger.warn("Exception in moveToElementById");
        }

        if (seleniumSettings.getBrowser().equals("firefox")) {
            elementJs.mouseMoveById(id);
        }
    }

    public void setFocusOnElement(WebElement element) {
        elementJs.setFocusOnElement(element);
    }

    public void setFocusOnElementByName(String name) {
        elementJs.setFocusOnElementByName(name);
    }

    public void setFocusOnElementById(String id) {
        elementJs.setFocusOnElementById(id);
    }

    public void click(WebElement element) {
        moveToElement(element);

        element.click();
    }

    public void clickByName(String name) {
        WebElement element = seleniumSettings.getWebDriver().findElement(By.name(name));
        click(element);
    }

    public void clickById(String id) {
        WebElement element = seleniumSettings.getWebDriver().findElement(By.id(id));
        click(element);
    }

    public void clickByClass(String className) {
        WebElement element = seleniumSettings.getWebDriver().findElement(By.className(className));
        click(element);
    }

    public void doubleClick(WebElement element) {
        moveToElement(element);

        if (seleniumSettings.getBrowser().equals("chrome")) {
            Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            actionObject.doubleClick(element).perform();
        } else if (seleniumSettings.getBrowser().equals("firefox")) { //TODO BUG firefox https://bugzilla.mozilla.org/show_bug.cgi?id=1430851
            elementJs.doubleClick(element);
        } else {
            throw new SeleniumUnexpectedException("Not support browser[" + seleniumSettings.getBrowser() + "]");
        }
    }

    public void doubleClickByName(String name) {
        WebElement element = seleniumSettings.getWebDriver().findElement(By.name(name));
        doubleClick(element);
    }

    public void doubleClickById(String id) {
        WebElement element = seleniumSettings.getWebDriver().findElement(By.id(id));
        doubleClick(element);
    }

    public void doubleClickByClass(String className) {
        WebElement element = seleniumSettings.getWebDriver().findElement(By.className(className));
        doubleClick(element);
    }

    public void checkBackgroundColor(WebElement element, String backgroundColor) {
        String actualBackgroundColor = elementJs.getBackgroundColor(element);
        Assert.assertEquals(actualBackgroundColor, backgroundColor);
    }

    public void checkBackgroundColorByName(String name, String backgroundColor) {
        String actualBackgroundColor = elementJs.getBackgroundColorByName(name);
        Assert.assertEquals(actualBackgroundColor, backgroundColor);
    }

    public void checkBackgroundColorById(String id, String backgroundColor) {
        String actualBackgroundColor = elementJs.getBackgroundColorById(id);
        Assert.assertEquals(actualBackgroundColor, backgroundColor);
    }

    public void checkFontColor(WebElement element, String fontColor) {
        String actualFontColor = elementJs.getFontColor(element);
        Assert.assertEquals(actualFontColor, fontColor);
    }

    public void checkFontColorByName(String name, String fontColor) {
        String actualFontColor = elementJs.getFontColorByName(name);
        Assert.assertEquals(actualFontColor, fontColor);
    }

    public void checkFontColorById(String id, String fontColor) {
        String actualFontColor = elementJs.getFontColorById(id);
        Assert.assertEquals(actualFontColor, fontColor);
    }

    public void remove(WebElement element) {
        elementJs.remove(element);
    }

    public void removeByName(String name) {
        elementJs.removeByName(name);
    }

    public void removeById(String id) {
        elementJs.removeById(id);
    }

    public void removeByClass(String className) {
        elementJs.removeByClass(className);
    }

    public void clearViaBackspace(WebElement element) {
        click(element);
        String val = element.getAttribute("value");

        Actions actionObject = new Actions(seleniumSettings.getWebDriver());
        for (int i = 0; i < val.length(); i++) {
            actionObject.sendKeys(Keys.ARROW_RIGHT).perform();
        }
        for (int i = 0; i < val.length(); i++) {
            actionObject.sendKeys(Keys.BACK_SPACE).perform();
        }
    }

    public void clearViaBackspaceByName(String name) {
        WebElement element = seleniumSettings.getWebDriver().findElement(By.name(name));
        click(element);
        String val = element.getAttribute("value");

        Actions actionObject = new Actions(seleniumSettings.getWebDriver());
        for (int i = 0; i < val.length(); i++) {
            actionObject.sendKeys(Keys.ARROW_RIGHT).perform();
        }
        for (int i = 0; i < val.length(); i++) {
            actionObject.sendKeys(Keys.BACK_SPACE).perform();
        }
    }

    public void clearViaBackspaceById(String id) {
        WebElement element = seleniumSettings.getWebDriver().findElement(By.id(id));
        click(element);
        String val = element.getAttribute("value");

        Actions actionObject = new Actions(seleniumSettings.getWebDriver());
        for (int i = 0; i < val.length(); i++) {
            actionObject.sendKeys(Keys.ARROW_RIGHT).perform();
        }
        for (int i = 0; i < val.length(); i++) {
            actionObject.sendKeys(Keys.BACK_SPACE).perform();
        }
    }

    public void clearViaBackspaceByClass(String className) {
         WebElement element = seleniumSettings.getWebDriver().findElement(By.className(className));
         click(element);
         String val = element.getAttribute("value");

         Actions actionObject = new Actions(seleniumSettings.getWebDriver());
         for (int i = 0; i < val.length(); i++) {
             actionObject.sendKeys(Keys.ARROW_RIGHT).perform();
         }
         for (int i = 0; i < val.length(); i++) {
             actionObject.sendKeys(Keys.BACK_SPACE).perform();
         }
    }
}
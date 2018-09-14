package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

@Component
public class ElementHelper {

    private final Logger logger = LoggerFactory.getLogger(ElementHelper.class);

    @Resource
    private ElementJsHelper elementJsHelper;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    public void moveToElement(WebElement element) {
        elementJsHelper.moveToElement(element);

        try {
            Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            actionObject.moveToElement(element).perform();
        } catch (MoveTargetOutOfBoundsException e) {
            logger.warn("Exception in moveToElement", e);
        } catch (WebDriverException e) { //TODO firefox 59 throw WebDriverException instead of MoveTargetOutOfBoundsException or instead success execution
            logger.warn("Exception in moveToElementByName", e);
        }
    }

    public void moveToElementByName(String name) {
        elementWaitHelper.waitElementByName(name);
        elementJsHelper.moveToElementByName(name);

        try {
            Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            //actionObject.moveToElement(seleniumSettings.getWebDriver().findElement(By.name(name))).perform();
            actionObject.moveToElement(seleniumSettings.getWebDriver().findElement(By.xpath("//*[string(@submitName)='" + name + "'] | //*[string(@name)='" + name + "']"))).perform();
        } catch (MoveTargetOutOfBoundsException e) {
            logger.warn("Exception in moveToElementByName", e);
        } catch (WebDriverException e) { //TODO firefox 59 throw WebDriverException instead of MoveTargetOutOfBoundsException or instead success execution
            logger.warn("Exception in moveToElementByName", e);
        }
    }

    public void moveToElementById(String id) {
        elementWaitHelper.waitElementById(id);
        elementJsHelper.moveToElementById(id);

        try {
            Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            actionObject.moveToElement(seleniumSettings.getWebDriver().findElement(By.id(id))).perform();
        } catch (MoveTargetOutOfBoundsException e) {
            logger.warn("Exception in moveToElementById", e);
        } catch (WebDriverException e) { //TODO firefox 59 throw WebDriverException instead of MoveTargetOutOfBoundsException or instead success execution
            logger.warn("Exception in moveToElementByName", e);
        }
    }

    public void setFocusOnElement(WebElement element) {
        elementJsHelper.setFocusOnElement(element);
    }

    public void setFocusOnElementByName(String name) {
        elementJsHelper.setFocusOnElementByName(name);
    }

    public void setFocusOnElementById(String id) {
        elementJsHelper.setFocusOnElementById(id);
    }

    public void click(WebElement element) {
        moveToElement(element);

        element.click();
    }

    public void clickByName(String name) {
        moveToElementByName(name);

        //seleniumSettings.getWebDriver().findElement(By.name(name)).click();
        seleniumSettings.getWebDriver().findElement(By.xpath("//*[string(@submitName)='" + name + "'] | //*[string(@name)='" + name + "']")).click();
    }

    public void clickById(String id) {
        moveToElementById(id);

        seleniumSettings.getWebDriver().findElement(By.id(id)).click();
    }

    public void doubleClick(WebElement element) {
        moveToElement(element);

        if (seleniumSettings.getBrowser().equals("chrome")) {
            Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            actionObject.doubleClick(element).perform();
        } else if (seleniumSettings.getBrowser().equals("firefox")) { //TODO BUG firefox https://bugzilla.mozilla.org/show_bug.cgi?id=1430851
            elementJsHelper.doubleClick(element);
        } else {
            throw new SeleniumUnexpectedException("Not support browser[" + seleniumSettings.getBrowser() + "]");
        }
    }

    public void doubleClickByName(String name) {
        WebElement element = seleniumSettings.getWebDriver().findElement(By.name(name));

        moveToElement(element);

        if (seleniumSettings.getBrowser().equals("chrome")) {
            Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            actionObject.doubleClick(element).perform();
        } else if (seleniumSettings.getBrowser().equals("firefox")) { //TODO BUG firefox https://bugzilla.mozilla.org/show_bug.cgi?id=1430851
            elementJsHelper.doubleClick(element);
        } else {
            throw new SeleniumUnexpectedException("Not support browser[" + seleniumSettings.getBrowser() + "]");
        }
    }

    public void doubleClickById(String id) {
        WebElement element = seleniumSettings.getWebDriver().findElement(By.id(id));

        moveToElement(element);

        if (seleniumSettings.getBrowser().equals("chrome")) {
            Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            actionObject.doubleClick(element).perform();
        } else if (seleniumSettings.getBrowser().equals("firefox")) { //TODO BUG firefox https://bugzilla.mozilla.org/show_bug.cgi?id=1430851
            elementJsHelper.doubleClick(element);
        } else {
            throw new SeleniumUnexpectedException("Not support browser[" + seleniumSettings.getBrowser() + "]");
        }
    }

}
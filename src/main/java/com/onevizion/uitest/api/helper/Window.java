package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.document.Document;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.helper.tree.Tree;

@Component
public class Window {

    private final Logger logger = LoggerFactory.getLogger(Window.class);

    @Resource
    private Wait wait;

    @Resource
    private Tree tree;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementWait elementWait;

    @Resource
    private Document document;

    @Resource
    private Jquery jquery;

    public void openModal(final By elemenLocator) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Can't find button " + elemenLocator.toString() + " to open modal window.")
            .ignoring(NoSuchElementException.class)
            .until(webDriver -> webDriver.findElement(elemenLocator));

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Button " + elemenLocator.toString() + " is disabled to open new modal window.")
            .until(webdriver -> {
                WebElement webElement = webdriver.findElement(elemenLocator);
                return !webElement.getAttribute("class").toLowerCase().contains("disable") && StringUtils.isEmpty(webElement.getAttribute("disabled"));
            });

        final int openedWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();
        elementWait.waitElementEnabledByBy(elemenLocator);

        try {
            Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            actionObject.moveToElement(seleniumSettings.getWebDriver().findElement(elemenLocator)).perform();
        } catch (MoveTargetOutOfBoundsException e) {
            logger.warn("", e);
        }

        seleniumSettings.getWebDriver().findElement(elemenLocator).click();
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting new modal window opened by [" + elemenLocator.toString() + "] is failed.")
            .until(webdriver -> webdriver.getWindowHandles().size() == openedWindowsCount + 1);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting new modal window opened by [" + elemenLocator.toString() + "] is failed.")
            .until(webdriver -> {
                Boolean hasNulls = false;
                for (String handle : webdriver.getWindowHandles()) {
                    if (handle == null || handle.length() == 0) {
                        hasNulls = true;
                        break;
                    }
                }
                return !hasNulls;
            });

        for (String windowHandle : seleniumSettings.getWebDriver().getWindowHandles()) {
            if (!seleniumSettings.getWindows().contains(windowHandle)) {
                seleniumSettings.getWindows().add(windowHandle);
            }
        }

        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));
        seleniumSettings.getWebDriver().manage().window().maximize();

        document.waitReadyStateComplete();
        jquery.waitLoad();
    }

    public void openModal(final WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Can't find button " + element.toString() + " to open modal window.")
            .ignoring(NoSuchElementException.class)
            .until(webdriver -> element);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Button " + element.toString() + " is disabled to open new modal window.")
            .until(webdriver -> !element.getAttribute("class").toLowerCase().contains("disable") && StringUtils.isEmpty(element.getAttribute("disabled")));

        final int openedWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();

        element.click();
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting new modal window opened by [" + element.toString() + "] is failed.")
            .until(webdriver -> webdriver.getWindowHandles().size() == openedWindowsCount + 1);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting new modal window opened by [" + element.toString() + "] is failed.")
            .until(webdriver -> {
                Boolean hasNulls = false;
                for (String handle : webdriver.getWindowHandles()) {
                    if (handle == null || handle.length() == 0) {
                        hasNulls = true;
                        break;
                    }
                }
                return !hasNulls;
            });

        for (String windowHandle : seleniumSettings.getWebDriver().getWindowHandles()) {
            if (!seleniumSettings.getWindows().contains(windowHandle)) {
                seleniumSettings.getWindows().add(windowHandle);
            }
        }

        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));
        seleniumSettings.getWebDriver().manage().window().maximize();

        document.waitReadyStateComplete();
        jquery.waitLoad();
    }

    public void closeModalCtrlEnter() {
        final int currentWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();
        String title = seleniumSettings.getWebDriver().getTitle();

        Actions actionObject = new Actions(seleniumSettings.getWebDriver());
        actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.ENTER).keyUp(Keys.CONTROL).perform();

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for closing modal window with title=[" + title + "] failed.")
            .until(webdriver -> webdriver.getWindowHandles().size() == currentWindowsCount - 1);

        seleniumSettings.getWindows().remove(seleniumSettings.getWindows().size() - 1);
        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));
        wait.waitIsWindowClosed();
    }

    public void closeModalCtrlEnterAndWaitGridLoad() {
        closeModalCtrlEnter();
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void closeModal(final By elementClick) {
        final int currentWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();
        String title = seleniumSettings.getWebDriver().getTitle();

        if (elementClick != null) {
            wait.waitWebElement(elementClick);
            seleniumSettings.getWebDriver().findElement(elementClick).click();
        } else {
            //https://bugs.chromium.org/p/chromedriver/issues/detail?id=538
            //BUG window.onbeforeunload not fired
            seleniumSettings.getWebDriver().get("about:blank");
            seleniumSettings.getWebDriver().close();
        }

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for closing modal window with title=[" + title + "] failed.")
            .until(webdriver -> webdriver.getWindowHandles().size() == currentWindowsCount - 1);

        seleniumSettings.getWindows().remove(seleniumSettings.getWindows().size() - 1);
        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));
        wait.waitIsWindowClosed();
    }

    public void closeModalAndWaitGridLoad(By elementClick) {
        closeModal(elementClick);
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void closeModalWithAlert(final By elementClick, final String message) {
        final int currentWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();
        wait.waitWebElement(elementClick);
        String title = seleniumSettings.getWebDriver().getTitle();

        seleniumSettings.getWebDriver().findElement(elementClick).click();

        wait.waitAlert();

        if (message != null) {
            Assert.assertEquals(seleniumSettings.getWebDriver().switchTo().alert().getText(), message, "Alert have wrong message");
        }

        try {
            seleniumSettings.getWebDriver().switchTo().alert().accept();
        } catch (WebDriverException e){
            logger.warn("Exception in closeModalWithAlert alert", e);
        }

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for closing modal window with title=[" + title + "] failed.")
            .until(webdriver -> webdriver.getWindowHandles().size() == currentWindowsCount - 1);

        seleniumSettings.getWindows().remove(seleniumSettings.getWindows().size() - 1);
        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));
        wait.waitIsWindowClosed();
    }

    public void closeModalAndWaitTreeLoad(By elementClick) {
        closeModal(elementClick);
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
    }

    public void closeModalFormButtonRule(final By elementClick, final String message, final String message2) {
        final int currentWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();
        wait.waitWebElement(elementClick);
        String title = seleniumSettings.getWebDriver().getTitle();

        seleniumSettings.getWebDriver().findElement(elementClick).click();

        wait.waitAlert();

        Assert.assertEquals(seleniumSettings.getWebDriver().switchTo().alert().getText(), message, "Alert have wrong message");

        try {
            seleniumSettings.getWebDriver().switchTo().alert().accept();
        } catch (WebDriverException e) {
            logger.warn("Exception in closeModalFormButtonRule alert1", e);
        }

        wait.waitAlert();

        Assert.assertEquals(seleniumSettings.getWebDriver().switchTo().alert().getText(), message2, "Apply alert have wrong message");

        try {
            seleniumSettings.getWebDriver().switchTo().alert().accept();
        } catch (WebDriverException e){
            logger.warn("Exception in closeModalFormButtonRule alert2", e);
        }

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for closing modal window with title=[" + title + "] failed.")
            .until(webdriver -> webdriver.getWindowHandles().size() == currentWindowsCount - 1);

        seleniumSettings.getWindows().remove(seleniumSettings.getWindows().size() - 1);
        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));
        wait.waitIsWindowClosed();
    }

    public void closeModalFormButtonRuleMassAssign(final By elementClick) {
        final int currentWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();
        wait.waitWebElement(elementClick);
        String title = seleniumSettings.getWebDriver().getTitle();

        seleniumSettings.getWebDriver().findElement(elementClick).click();

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for closing modal window with title=[" + title + "] failed.")
            .until(webdriver -> webdriver.getWindowHandles().size() == currentWindowsCount - 2);

        seleniumSettings.getWindows().remove(seleniumSettings.getWindows().size() - 1);
        seleniumSettings.getWindows().remove(seleniumSettings.getWindows().size() - 1);
        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));
        wait.waitIsWindowClosed();
    }

}
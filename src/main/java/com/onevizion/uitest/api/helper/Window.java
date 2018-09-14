package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.tree.TreeWait;

@Component
public class Window {

    private final Logger logger = LoggerFactory.getLogger(Window.class);

    @Resource
    private Wait wait;

    @Resource
    private TreeWait treeWait;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    public void openModal(final By elemenLocator) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Can't find button " + elemenLocator.toString() + " to open modal window.")
            .ignoring(NoSuchElementException.class)
            .until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver webdriver) {
                    return seleniumSettings.getWebDriver().findElement(elemenLocator);
                }
            });

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Button " + elemenLocator.toString() + " is disabled to open new modal window.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    WebElement webElement = seleniumSettings.getWebDriver().findElement(elemenLocator);
                    return !webElement.getAttribute("class").toLowerCase().contains("disable") && StringUtils.isEmpty(webElement.getAttribute("disabled"));
                }
            });

        final int openedWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();
        elementWaitHelper.waitElementEnabledByBy(elemenLocator);

        try {
            Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            actionObject.moveToElement(seleniumSettings.getWebDriver().findElement(elemenLocator)).perform();
        } catch (MoveTargetOutOfBoundsException e) {
            logger.warn("", e);
        }

        boolean failOpenWindow = true;
        int attemptsCnt = 0; //protection from the endless cycle
        do {
            try {
                seleniumSettings.getWebDriver().findElement(elemenLocator).click();
                new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
                    .withMessage("Waiting new modal window opened by [" + elemenLocator.toString() + "] is failed.")
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver webdriver) {
                            return seleniumSettings.getWebDriver().getWindowHandles().size() == openedWindowsCount + 1;
                        }
                    });
                failOpenWindow = false;
            } catch (TimeoutException e) {
                logger.warn("", e);
            } catch (ElementNotVisibleException e) {
                logger.warn("", e);
            }
            attemptsCnt = attemptsCnt + 1;
        } while (failOpenWindow && attemptsCnt <= 10);

        if (failOpenWindow && attemptsCnt > 10) {
            throw new SeleniumUnexpectedException("Failed to open window by [" + elemenLocator.toString() + "]");
        }

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting new modal window opened by [" + elemenLocator.toString() + "] is failed.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    Boolean hasNulls = false;
                    for (String handle : seleniumSettings.getWebDriver().getWindowHandles()) {
                        if (handle == null || handle.length() == 0) {
                            hasNulls = true;
                            break;
                        }
                    }
                    return !hasNulls;
                }
            });

        for (String windowHandle : seleniumSettings.getWebDriver().getWindowHandles()) {
            if (!seleniumSettings.getWindows().contains(windowHandle)) {
                seleniumSettings.getWindows().add(windowHandle);
            }
        }

        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));
        seleniumSettings.getWebDriver().manage().window().maximize();
    }

    public void openModal(final WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Can't find button " + element.toString() + " to open modal window.")
            .ignoring(NoSuchElementException.class)
            .until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver webdriver) {
                    return element;
                }
            });

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Button " + element.toString() + " is disabled to open new modal window.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return !element.getAttribute("class").toLowerCase().contains("disable") && StringUtils.isEmpty(element.getAttribute("disabled"));
                }
            });

        final int openedWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();

        boolean failOpenWindow = true;
        int attemptsCnt = 0; //protection from the endless cycle
        do {
            try {
                element.click();
                new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
                    .withMessage("Waiting new modal window opened by [" + element.toString() + "] is failed.")
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver webdriver) {
                            return seleniumSettings.getWebDriver().getWindowHandles().size() == openedWindowsCount + 1;
                        }
                    });
                failOpenWindow = false;
            } catch (TimeoutException e) {
                logger.warn("", e);
            } catch (ElementNotVisibleException e) {
                logger.warn("", e);
            }
            attemptsCnt = attemptsCnt + 1;
        } while (failOpenWindow && attemptsCnt <= 10);

        if (failOpenWindow && attemptsCnt > 10) {
            throw new SeleniumUnexpectedException("Failed to open window by [" + element.toString() + "]");
        }

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting new modal window opened by [" + element.toString() + "] is failed.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    Boolean hasNulls = false;
                    for (String handle : seleniumSettings.getWebDriver().getWindowHandles()) {
                        if (handle == null || handle.length() == 0) {
                            hasNulls = true;
                            break;
                        }
                    }
                    return !hasNulls;
                }
            });

        for (String windowHandle : seleniumSettings.getWebDriver().getWindowHandles()) {
            if (!seleniumSettings.getWindows().contains(windowHandle)) {
                seleniumSettings.getWindows().add(windowHandle);
            }
        }

        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));
        seleniumSettings.getWebDriver().manage().window().maximize();
    }

    public void closeModalCtrlEnter() {
        final int currentWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();
        String title = seleniumSettings.getWebDriver().getTitle();

        Actions actionObject = new Actions(seleniumSettings.getWebDriver());
        actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.ENTER).keyUp(Keys.CONTROL).perform();

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for closing modal window with title=[" + title + "] failed.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return seleniumSettings.getWebDriver().getWindowHandles().size() == currentWindowsCount - 1;
                }
            });

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
            try {
                seleniumSettings.getWebDriver().findElement(elementClick).click();
            } catch (WebDriverException e){
                logger.warn("Exception in closeModal", e);
            }
        } else {
            //https://bugs.chromium.org/p/chromedriver/issues/detail?id=538
            //BUG window.onbeforeunload not fired
            seleniumSettings.getWebDriver().close();
        }

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage(
                "Waiting for closing modal window with title=[" + title + "] failed.")
                .until(
                        new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver webdriver) {
                                return seleniumSettings.getWebDriver().getWindowHandles().size() == currentWindowsCount - 1;
                            }
                        });

        seleniumSettings.getWindows().remove(seleniumSettings.getWindows().size() - 1);
        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));
        wait.waitIsWindowClosed();
    }

    @Deprecated
    public void closeModalDeprecated(final By elementClick) {
        final int currentWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();
        String title = seleniumSettings.getWebDriver().getTitle();

        if (elementClick != null) {
            wait.waitWebElement(elementClick);
            try {
                seleniumSettings.getWebDriver().findElement(elementClick).click();
            } catch (WebDriverException e){
                logger.warn("Exception in closeModal", e);
            }
        } else {
            //https://bugs.chromium.org/p/chromedriver/issues/detail?id=538
            //BUG window.onbeforeunload not fired
            seleniumSettings.getWebDriver().close();
        }

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage(
                "Waiting for closing modal window with title=[" + title + "] failed.")
                .until(
                        new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver webdriver) {
                                return seleniumSettings.getWebDriver().getWindowHandles().size() == currentWindowsCount - 1;
                            }
                        });

        seleniumSettings.getWindows().remove(seleniumSettings.getWindows().size() - 1);
        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));

        seleniumSettings.getWebDriver().findElement(By.id("DIVx")).click(); //TODO DIVx should be removed automatically after close window
    }

    public void closeModalAndWaitGridLoad(By elementClick) {
        closeModal(elementClick);
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void closeModalWithAlert(final By elementClick, final String message) {
        final int currentWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();
        wait.waitWebElement(elementClick);
        String title = seleniumSettings.getWebDriver().getTitle();

        try {
            seleniumSettings.getWebDriver().findElement(elementClick).click();
        } catch (WebDriverException e) {
            logger.warn("Exception in closeModalWithAlert", e);
        }

        wait.waitAlert();

        if (message != null) {
            Assert.assertEquals(seleniumSettings.getWebDriver().switchTo().alert().getText(), message, "Alert have wrong message");
        }

        try {
            seleniumSettings.getWebDriver().switchTo().alert().accept();
        } catch (WebDriverException e){
            
        }

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage(
                "Waiting for closing modal window with title=[" + title + "] failed.")
                .until(
                        new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver webdriver) {
                                return seleniumSettings.getWebDriver().getWindowHandles().size() == currentWindowsCount - 1;
                            }
                        });

        seleniumSettings.getWindows().remove(seleniumSettings.getWindows().size() - 1);
        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));
        wait.waitIsWindowClosed();
    }

    public void closeModalAndWaitTreeLoad(By elementClick) {
        closeModal(elementClick);
        treeWait.waitTreeLoad(AbstractSeleniumCore.getTreeIdx());
    }

    public void closeModalFormButtonRule(final By elementClick, final String message,
            final String message2) {
        final int currentWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();
        wait.waitWebElement(elementClick);
        String title = seleniumSettings.getWebDriver().getTitle();

        try {
            seleniumSettings.getWebDriver().findElement(elementClick).click();
        } catch (WebDriverException e) {
            logger.warn("Exception in closeModalFormButtonRule", e);
        }

        wait.waitAlert();

        Assert.assertEquals(seleniumSettings.getWebDriver().switchTo().alert().getText(), message, "Alert have wrong message");

        try {
            seleniumSettings.getWebDriver().switchTo().alert().accept();
        } catch (WebDriverException e){
            
        }

        wait.waitAlert();

        Assert.assertEquals(seleniumSettings.getWebDriver().switchTo().alert().getText(), message2, "Apply alert have wrong message");

        try {
            seleniumSettings.getWebDriver().switchTo().alert().accept();
        } catch (WebDriverException e){
            
        }

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage(
                "Waiting for closing modal window with title=[" + title + "] failed.")
                .until(
                        new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver webdriver) {
                                return seleniumSettings.getWebDriver().getWindowHandles().size() == currentWindowsCount - 1;
                            }
                        });

        seleniumSettings.getWindows().remove(seleniumSettings.getWindows().size() - 1);
        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));
        wait.waitIsWindowClosed();
    }

    public void closeModalFormButtonRuleMassAssign(final By elementClick) {
        final int currentWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();
        wait.waitWebElement(elementClick);
        String title = seleniumSettings.getWebDriver().getTitle();

        try {
            seleniumSettings.getWebDriver().findElement(elementClick).click();
        } catch (WebDriverException e) {
            logger.warn("Exception in closeModalFormButtonRuleMassAssign", e);
        }

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage(
                "Waiting for closing modal window with title=[" + title + "] failed.")
                .until(
                        new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver webdriver) {
                                return seleniumSettings.getWebDriver().getWindowHandles().size() == currentWindowsCount - 2;
                            }
                        });

        seleniumSettings.getWindows().remove(seleniumSettings.getWindows().size() - 1);
        seleniumSettings.getWindows().remove(seleniumSettings.getWindows().size() - 1);
        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));
        wait.waitIsWindowClosed();
    }

    public String closeModal2(final By elementClick) {
        final int currentWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();
        String title = seleniumSettings.getWebDriver().getTitle();

        if (elementClick != null) {
            wait.waitWebElement(elementClick);
            try {
                seleniumSettings.getWebDriver().findElement(elementClick).click();
            } catch (WebDriverException e){
                logger.warn("Exception in closeModal2", e);
            }
        } else {
            //https://bugs.chromium.org/p/chromedriver/issues/detail?id=538
            //BUG window.onbeforeunload not fired
            seleniumSettings.getWebDriver().close();
        }

        String alertMsg = "";
        try {
            Alert alert = seleniumSettings.getWebDriver().switchTo().alert();
            if (alert != null && alert.getText() != null) {
                alertMsg = alertMsg + alert.getText();
            }
            alert.accept();
            alert = seleniumSettings.getWebDriver().switchTo().alert();
            if (alert != null && alert.getText() != null) {
                alertMsg = alertMsg + alert.getText();
            }
            alert.accept();
            alert = seleniumSettings.getWebDriver().switchTo().alert();
            if (alert != null && alert.getText() != null) {
                alertMsg = alertMsg + alert.getText();
            }
            alert.accept();
            alert = seleniumSettings.getWebDriver().switchTo().alert();
            if (alert != null && alert.getText() != null) {
                alertMsg = alertMsg + alert.getText();
            }
            alert.accept();
            alert = seleniumSettings.getWebDriver().switchTo().alert();
            if (alert != null && alert.getText() != null) {
                alertMsg = alertMsg + alert.getText();
            }
            alert.accept();
        } catch (NoAlertPresentException e){

        } catch (WebDriverException e){

        }

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage(
                "Waiting for closing modal window with title=[" + title + "] failed.")
                .until(
                        new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver webdriver) {
                                return seleniumSettings.getWebDriver().getWindowHandles().size() == currentWindowsCount - 1;
                            }
                        });

        seleniumSettings.getWindows().remove(seleniumSettings.getWindows().size() - 1);
        seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));
        wait.waitIsWindowClosed();

        return alertMsg;
    }

}
package com.onevizion.uitest.api.helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
            .ignoring(NoSuchElementException.class)
            .ignoring(NullPointerException.class)
            .ignoring(WebDriverException.class)
            .until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver webdriver) {
                    return element;
                }
            });
    }*/

    public void waitElementByName(String name) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Element name=[" + name + "] is failed")
            .ignoring(NoSuchElementException.class)
            .ignoring(NullPointerException.class)
            .ignoring(WebDriverException.class)
            .until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver webdriver) {
                    //return seleniumSettings.getWebDriver().findElement(By.name(name));
                    return seleniumSettings.getWebDriver().findElement(By.xpath("//*[string(@submitName)='" + name + "'] | //*[string(@name)='" + name + "']"));
                }
            });
    }

    public void waitElementById(String id) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Element id=[" + id + "] is failed")
            .ignoring(NoSuchElementException.class)
            .ignoring(NullPointerException.class)
            .ignoring(WebDriverException.class)
            .until(
                new ExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver webdriver) {
                        return seleniumSettings.getWebDriver().findElement(By.id(id));
                    }
                });
    }

    @Deprecated
    public void waitElementByBy(By by) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Element by=[" + by + "] is failed")
            .ignoring(NoSuchElementException.class)
            .ignoring(NullPointerException.class)
            .ignoring(WebDriverException.class)
            .until(
                new ExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver webdriver) {
                        return seleniumSettings.getWebDriver().findElement(by);
                    }
                });
    }

    public void waitElementVisible(WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element [" + element + "] visibility failed")
            .ignoring(StaleElementReferenceException.class)
            .until(ExpectedConditions.visibilityOf(element));
    }

    public void waitElementVisibleByName(String name) {
        waitElementByName(name);

        //WebElement element = seleniumSettings.getWebDriver().findElement(By.name(name));
        WebElement element = seleniumSettings.getWebDriver().findElement(By.xpath("//*[string(@submitName)='" + name + "'] | //*[string(@name)='" + name + "']"));

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

    public void waitElementNotVisibleByNName(String name) {
        waitElementByName(name);

        //WebElement element = seleniumSettings.getWebDriver().findElement(By.name(name));
        WebElement element = seleniumSettings.getWebDriver().findElement(By.xpath("//*[string(@submitName)='" + name + "'] | //*[string(@name)='" + name + "']"));

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
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return element.isDisplayed();
                }
            });
    }

    public void waitElementDisplayByName(String name) {
        waitElementByName(name);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element name=[" + name + "] not displayed")
            .ignoring(StaleElementReferenceException.class)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    //return seleniumSettings.getWebDriver().findElement(By.name(name)).isDisplayed();
                    return seleniumSettings.getWebDriver().findElement(By.xpath("//*[string(@submitName)='" + name + "'] | //*[string(@name)='" + name + "']")).isDisplayed();
                }
            });
    }

    public void waitElementDisplayById(String id) {
        waitElementById(id);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element id=[" + id + "] not displayed")
            .ignoring(StaleElementReferenceException.class)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return seleniumSettings.getWebDriver().findElement(By.id(id)).isDisplayed();
                }
            });
    }

    public void waitElementNotDisplayById(String id) {
        waitElementById(id);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element id=[" + id + "] displayed")
            .ignoring(StaleElementReferenceException.class)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return !seleniumSettings.getWebDriver().findElement(By.id(id)).isDisplayed();
                }
            });
    }

    public void waitElementAttribute(WebElement element, String attribute, String attributeValue) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Element [" + element + "] attribute [" + attribute + "] value [" + attributeValue + "] is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return element.getAttribute(attribute).equals(attributeValue);
                }
            });
    }

    public void waitElementAttributeByName(String name, String attribute, String attributeValue) {
        waitElementByName(name);

        //Supplier<String> actualValueSupplier = ()-> seleniumSettings.getWebDriver().findElement(By.name(name)).getAttribute(attribute);
        Supplier<String> actualValueSupplier = ()-> seleniumSettings.getWebDriver().findElement(By.xpath("//*[string(@submitName)='" + name + "'] | //*[string(@name)='" + name + "']")).getAttribute(attribute);
        Supplier<String> messageSupplier = ()-> "Waiting for Element name=[" + name + "] attribute=[" + attribute + "] expectedVal=[" + attributeValue + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return actualValueSupplier.get().equals(attributeValue);
                }
            });
    }

    public void waitElementAttributeById(String id, String attribute, String attributeValue) {
        waitElementById(id);

        Supplier<String> actualValueSupplier = ()-> seleniumSettings.getWebDriver().findElement(By.id(id)).getAttribute(attribute);
        Supplier<String> messageSupplier = ()-> "Waiting for Element id=[" + id + "] attribute=[" + attribute + "] expectedVal=[" + attributeValue + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return actualValueSupplier.get().equals(attributeValue);
                }
            });
    }

    public void waitElementAttributeById(String id, String attribute, List<String> attributeValue) {
        waitElementById(id);

        Supplier<String> supplier = ()-> "Waiting for Element id=[" + id + "] attribute=[" + attribute + "] expectedVal=[" + attributeValue + "] actualVal=[" + seleniumSettings.getWebDriver().findElement(By.id(id)).getAttribute(attribute) + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(supplier)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    List<String> actualAttributeValue = Arrays.asList(seleniumSettings.getWebDriver().findElement(By.id(id)).getAttribute(attribute).split(";"));
                    for (int i = 0; i < actualAttributeValue.size(); i++) {
                        actualAttributeValue.set(i, actualAttributeValue.get(i).trim());
                    }
                    Collections.sort(attributeValue);
                    Collections.sort(actualAttributeValue);
                    return attributeValue.equals(actualAttributeValue);
                }
            });
    }

    public void waitElementDisabled(WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element [" + element + "] is enabled")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return element.isEnabled() == false;
                }
            });
    }

    public void waitElementDisabledByName(String name) {
        waitElementByName(name);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element name=[" + name + "] is enabled")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    //return seleniumSettings.getWebDriver().findElement(By.name(name)).isEnabled() == false;
                    return seleniumSettings.getWebDriver().findElement(By.xpath("//*[string(@submitName)='" + name + "'] | //*[string(@name)='" + name + "']")).isEnabled() == false;
                }
            });
    }

    public void waitElementDisabledById(String id) {
        waitElementById(id);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element id=[" + id + "] is enabled")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return seleniumSettings.getWebDriver().findElement(By.id(id)).isEnabled() == false;
                }
            });
    }

    public void waitElementEnabled(WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element [" + element + "] is disabled.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return element.isEnabled() == true;
                }
            });
    }

    public void waitElementEnabledByName(String name) {
        waitElementByName(name);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element name=[" + name + "] is disabled.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    //return seleniumSettings.getWebDriver().findElement(By.name(name)).isEnabled() == true;
                    return seleniumSettings.getWebDriver().findElement(By.xpath("//*[string(@submitName)='" + name + "'] | //*[string(@name)='" + name + "']")).isEnabled() == true;
                }
            });
    }

    public void waitElementEnabledById(String id) {
        waitElementById(id);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element id=[" + id + "] is disabled.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return seleniumSettings.getWebDriver().findElement(By.id(id)).isEnabled() == true;
                }
            });
    }

    @Deprecated
    public void waitElementEnabledByBy(By by) {
        waitElementByBy(by);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element by=[" + by + "] is disabled.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return seleniumSettings.getWebDriver().findElement(by).isEnabled() == true;
                }
            });
    }

    public void waitElementAnimatedFinish(WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element [" + element + "] is animated.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return elementJs.isElementAnimatedFinish(element);
                }
            });
    }

    public void waitElementAnimatedFinishByName(String name) {
        waitElementByName(name);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element name=[" + name + "] is animated.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return elementJs.isElementAnimatedFinishByName(name);
                }
            });
    }

    public void waitElementAnimatedFinishById(String id) {
        waitElementById(id);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element id=[" + id + "] is animated.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return elementJs.isElementAnimatedFinishById(id);
                }
            });
    }

    public void waitElementVelocityAnimatedFinish(WebElement element) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element [" + element + "] Velocity is animated.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return elementJs.isElementVelocityAnimatedFinish(element);
                }
            });
    }

    public void waitElementVelocityAnimatedFinishByName(String name) {
        waitElementByName(name);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element name=[" + name + "] Velocity is animated.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return elementJs.isElementVelocityAnimatedFinishByName(name);
                }
            });
    }

    public void waitElementVelocityAnimatedFinishById(String id) {
        waitElementById(id);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Element id=[" + id + "] Velocity is animated.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return elementJs.isElementVelocityAnimatedFinishById(id);
                }
            });
    }

}
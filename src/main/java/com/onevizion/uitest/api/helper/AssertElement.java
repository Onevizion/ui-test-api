package com.onevizion.uitest.api.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

@Component
public class AssertElement {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Js js;

    @Resource
    private Wait wait;

    @Resource
    private PsSelector psSelector;

    @Resource
    private Element element;

    @Resource
    private Checkbox checkbox;

    @Resource
    private Tb tb;

    public void assertLink(String id, String expectedVal) {
        element.moveToElementById(id);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.id(id)).getText();
        Assert.assertEquals(actualVal, expectedVal, "Element with id=[" + id + "] has wrong value");
    }

    public void assertText(WebElement webElement, String expectedVal) {
        element.moveToElement(webElement);
        String actualVal = webElement.getAttribute("value");
        Assert.assertEquals(actualVal, expectedVal, "Element=[" + webElement + "] has wrong value");
    }

    public void assertText(String name, String expectedVal) {
        element.moveToElementByName(name);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.name(name)).getAttribute("value");
        Assert.assertEquals(actualVal, expectedVal, "Element with name=[" + name + "] has wrong value");
    }

    public void assertTextById(String id, String expectedVal) {
        element.moveToElementById(id);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.id(id)).getAttribute("value");
        Assert.assertEquals(actualVal, expectedVal, "Element with id=[" + id + "] has wrong value");
    }

    /*firefox throw org.openqa.selenium.WebDriverException: TypeError: rect is undefined*/
    /*from elementHelper.moveToElementByName(name)*/
    /*if element is hidden*/
    @Deprecated
    public void assertCheckBox(String name, String expectedVal) {
        element.moveToElementByName(name);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.name(name)).isSelected() ? "YES" : "NO";
        Assert.assertEquals(actualVal, expectedVal, "Element with name=[" + name + "] has wrong value");
    }

    public void assertCheckBoxNew(String name, String expectedVal) {
        element.moveToElementByName("lbl" + name);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.name(name)).isSelected() ? "YES" : "NO";
        Assert.assertEquals(actualVal, expectedVal, "Element with name=[" + name + "] has wrong value");
    }

    /*firefox throw org.openqa.selenium.WebDriverException: TypeError: rect is undefined*/
    /*from elementHelper.moveToElementById(id)*/
    /*if element is hidden*/
    @Deprecated
    public void assertCheckBoxById(String id, String expectedVal) {
        element.moveToElementById(id);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.id(id)).isSelected() ? "YES" : "NO";
        Assert.assertEquals(actualVal, expectedVal, "Element with id=[" + id + "] has wrong value");
    }

    public void assertCheckBoxByIdNew(String id, String expectedVal) {
        element.moveToElementById("lbl" + id);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.id(id)).isSelected() ? "YES" : "NO";
        Assert.assertEquals(actualVal, expectedVal, "Element with id=[" + id + "] has wrong value");
    }

    public void assertSelect(String name, String expectedVal) {
        element.moveToElementByName(name);
        String actualVal = new Select(seleniumSettings.getWebDriver().findElement(By.name(name))).getFirstSelectedOption().getText();
        Assert.assertEquals(actualVal, expectedVal, "Element with name=[" + name + "] has wrong value");
    }

    public void assertSelectWithFolder(String name, String expectedVal) {
        element.moveToElementByName(name);
        String actualVal = new Select(seleniumSettings.getWebDriver().findElement(By.name(name))).getFirstSelectedOption().getText();
        Assert.assertTrue(actualVal.contains(expectedVal), "Element with name=[" + name + "] has wrong value");
    }

    public void assertSelectById(String id, String expectedVal) {
        element.moveToElementById(id);
        String actualVal = new Select(seleniumSettings.getWebDriver().findElement(By.id(id))).getFirstSelectedOption().getText();
        Assert.assertEquals(actualVal, expectedVal, "Element with id=[" + id + "] has wrong value");
    }

    public void assertRadioPsSelector(String fieldName, String btnOpenName, String btnCloseName, String expectedVal, Long filterFieldNum, boolean isOpenSelector) {
        element.moveToElementByName(fieldName);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.name(fieldName)).getAttribute("value");
        Assert.assertEquals(actualVal, expectedVal, "Element with name=[" + fieldName + "] has wrong value");
        if (isOpenSelector && !"".equals(expectedVal)) {
            boolean isChecked = psSelector.checkValue(By.name(btnOpenName), btnCloseName, expectedVal, filterFieldNum);
            Assert.assertEquals(isChecked, true, "Radiobutton not selected");
        }
    }

    public void assertRadioPsSelectorById(String fieldId, String btnOpenId, String btnCloseName, String expectedVal, Long filterFieldNum, boolean isOpenSelector) {
        element.moveToElementById(fieldId);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.id(fieldId)).getAttribute("value");
        Assert.assertEquals(actualVal, expectedVal, "Element with id=[" + fieldId + "] has wrong value");
        if (isOpenSelector && !"".equals(expectedVal)) {
            boolean isChecked = psSelector.checkValue(By.id(btnOpenId), btnCloseName, expectedVal, filterFieldNum);
            Assert.assertEquals(isChecked, true, "Radiobutton not selected");
        }
    }

    public void assertCheckboxPsSelector(String fieldName, String btnOpenName, String btnCloseName, List<String> expectedVals, Long filterFieldNum, boolean isOpenSelector) {
        element.moveToElementByName(fieldName);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.name(fieldName)).getAttribute("value");
        for (String expectedVal : expectedVals) {
            Assert.assertEquals(actualVal.contains(expectedVal.trim()), true, "Element with name=[" + fieldName + "] has wrong value");
        }
        if (isOpenSelector) {
            List<String> val = new ArrayList<>();
            for (String expectedVal : expectedVals) {
                if (!"".equals(expectedVal)) {
                    val.add(expectedVal);
                }
            }
            boolean isChecked = psSelector.checkMultipleValues(By.name(btnOpenName), btnCloseName, val, filterFieldNum);
            Assert.assertEquals(isChecked, true, "Radiobutton not selected");
        }
    }

    public void assertCheckboxPsSelectorById(String fieldId, String btnOpenId, String btnCloseName, List<String> expectedVals, Long filterFieldNum, boolean isOpenSelector) {
        element.moveToElementById(fieldId);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.id(fieldId)).getAttribute("value");
        for (String expectedVal : expectedVals) {
            Assert.assertEquals(actualVal.contains(expectedVal.trim()), true, "Element with id=[" + fieldId + "] has wrong value");
        }
        if (isOpenSelector) {
            List<String> val = new ArrayList<>();
            for (String expectedVal : expectedVals) {
                if (!"".equals(expectedVal)) {
                    val.add(expectedVal);
                }
            }
            By btnOpen = By.id(btnOpenId);
            boolean isChecked = psSelector.checkMultipleValues(btnOpen, btnCloseName, val, filterFieldNum);
            Assert.assertEquals(isChecked, true, "Radiobutton not selected");
        }
    }

    public void assertFCKEditor(String name, String expectedVal) {
        String actualVal = js.getValueFromFCKEditor(name);
        actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_1, AbstractSeleniumCore.SPECIAL_CHARACTERS_1);
        actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_2, AbstractSeleniumCore.SPECIAL_CHARACTERS_2);
        actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_3, AbstractSeleniumCore.SPECIAL_CHARACTERS_3);
        actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_4, AbstractSeleniumCore.SPECIAL_CHARACTERS_4);
        Assert.assertEquals(actualVal, expectedVal, "Element with name=[" + name + "] has wrong value");
    }

    public void assertCodeMirror(String elementId, String expectedVal) {
        wait.waitCodeMirrorLoad(elementId);
        String actualVal = js.getValueFromCodeMirror(elementId);
        Assert.assertEquals(actualVal, expectedVal, "CodeMirror editor for element with id=[" + elementId + "] has wrong value");
    }

    public void assertElementEnabled(WebElement element, boolean expectedIsEnabled) {
        boolean actualIsEnabled;
        String errorMessage = "Element with name=[" + element.getAttribute("name") + "] should be " + (expectedIsEnabled ? "enabled" : "disabled");
        if ("textarea".equals(element.getTagName())) {
            actualIsEnabled = !"true".equals(element.getAttribute("readonly"));
        } else if ("input".equals(element.getTagName()) && "text".equals(element.getAttribute("type"))) {
            actualIsEnabled = !"true".equals(element.getAttribute("readonly"));
        } else {
            actualIsEnabled = element.isEnabled();
        }
        Assert.assertEquals(actualIsEnabled, expectedIsEnabled, errorMessage);
    }

    public void assertFieldEnabled(String fieldName, int elementPosition) {
        WebElement webElement;
        String errorMessage;
        if (elementPosition > 1) {
            String idx = tb.getLastFieldIndex(fieldName, elementPosition);
            webElement = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx));
            errorMessage = "Element with id=[idx" + idx + "] should be enabled";
        } else {
            webElement = seleniumSettings.getWebDriver().findElement(By.name(fieldName));
            errorMessage = "Element with name=[" + webElement.getAttribute("name") + "] should be enabled";
        }

        boolean isEnabled;
        if ("textarea".equals(webElement.getTagName())) {
            isEnabled = !"true".equals(webElement.getAttribute("readonly"));
        } else if ("iframe".equals(webElement.getTagName())) {
            isEnabled = false;
        } else if ("input".equals(webElement.getTagName()) && ("text".equals(webElement.getAttribute("type")) || "hidden".equals(webElement.getAttribute("type")) )) {
            isEnabled = !"true".equals(webElement.getAttribute("readonly"));
        } else {
            isEnabled = webElement.isEnabled();
        }

        Assert.assertEquals(isEnabled, true, errorMessage);
    }

    public void assertFieldDisabled(String fieldName, int elementPosition) {
        WebElement webElement;
        String errorMessage;
        if (elementPosition > 1) {
            String idx = tb.getLastFieldIndex(fieldName, elementPosition);
            webElement = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx));
            errorMessage = "Element with id=[idx" + idx + "] should be disabled";
        } else {
            webElement = seleniumSettings.getWebDriver().findElement(By.name(fieldName));
            errorMessage = "Element with name=[" + webElement.getAttribute("name") + "] should be disabled";
        }

        boolean isDisabled;
        if ("textarea".equals(webElement.getTagName())) {
            isDisabled = "true".equals(webElement.getAttribute("readonly"));
        } else if ("iframe".equals(webElement.getTagName())) {
            isDisabled = true;
        } else if ("input".equals(webElement.getTagName()) && ("text".equals(webElement.getAttribute("type")) || "hidden".equals(webElement.getAttribute("type")))) {
            isDisabled = "true".equals(webElement.getAttribute("readonly"));
        } else {
            isDisabled = !webElement.isEnabled();
        }

        Assert.assertEquals(isDisabled, true, errorMessage);
    }

}
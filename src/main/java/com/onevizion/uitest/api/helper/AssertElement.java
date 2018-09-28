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

    public void AssertLink(String id, String expectedVal) {
        element.moveToElementById(id);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.id(id)).getText();
        Assert.assertEquals(actualVal, expectedVal, "Element with id=[" + id + "] has wrong value");
    }

    public void AssertText(WebElement webElement, String expectedVal) {
        element.moveToElement(webElement);
        String actualVal = webElement.getAttribute("value");
        Assert.assertEquals(actualVal, expectedVal, "Element=[" + webElement + "] has wrong value");
    }

    public void AssertText(String name, String expectedVal) {
        element.moveToElementByName(name);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.name(name)).getAttribute("value");
        Assert.assertEquals(actualVal, expectedVal, "Element with name=[" + name + "] has wrong value");
    }

    public void AssertTextById(String id, String expectedVal) {
        element.moveToElementById(id);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.id(id)).getAttribute("value");
        Assert.assertEquals(actualVal, expectedVal, "Element with id=[" + id + "] has wrong value");
    }

    /*firefox throw org.openqa.selenium.WebDriverException: TypeError: rect is undefined*/
    /*from elementHelper.moveToElementByName(name)*/
    /*if element is hidden*/
    @Deprecated
    public void AssertCheckBox(String name, String expectedVal) {
        element.moveToElementByName(name);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.name(name)).isSelected() == true ? "YES" : "NO";
        Assert.assertEquals(actualVal, expectedVal, "Element with name=[" + name + "] has wrong value");
    }

    public void AssertCheckBoxNew(String name, String expectedVal) {
        element.moveToElementByName("lbl" + name);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.name(name)).isSelected() == true ? "YES" : "NO";
        Assert.assertEquals(actualVal, expectedVal, "Element with name=[" + name + "] has wrong value");
    }

    /*firefox throw org.openqa.selenium.WebDriverException: TypeError: rect is undefined*/
    /*from elementHelper.moveToElementById(id)*/
    /*if element is hidden*/
    @Deprecated
    public void AssertCheckBoxById(String id, String expectedVal) {
        element.moveToElementById(id);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.id(id)).isSelected() == true ? "YES" : "NO";
        Assert.assertEquals(actualVal, expectedVal, "Element with id=[" + id + "] has wrong value");
    }

    public void AssertCheckBoxByIdNew(String id, String expectedVal) {
        element.moveToElementById("lbl" + id);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.id(id)).isSelected() == true ? "YES" : "NO";
        Assert.assertEquals(actualVal, expectedVal, "Element with id=[" + id + "] has wrong value");
    }

    public void AssertSelect(String name, String expectedVal) {
        element.moveToElementByName(name);
        String actualVal = new Select(seleniumSettings.getWebDriver().findElement(By.name(name))).getFirstSelectedOption().getText();
        Assert.assertEquals(actualVal, expectedVal, "Element with name=[" + name + "] has wrong value");
    }

    public void AssertSelectWithFolder(String name, String expectedVal) {
        element.moveToElementByName(name);
        String actualVal = new Select(seleniumSettings.getWebDriver().findElement(By.name(name))).getFirstSelectedOption().getText();
        Assert.assertTrue(actualVal.contains(expectedVal), "Element with name=[" + name + "] has wrong value");
    }

    public void AssertSelectById(String id, String expectedVal) {
        element.moveToElementById(id);
        String actualVal = new Select(seleniumSettings.getWebDriver().findElement(By.id(id))).getFirstSelectedOption().getText();
        Assert.assertEquals(actualVal, expectedVal, "Element with id=[" + id + "] has wrong value");
    }

    public void AssertRadioPsSelector(String fieldName, String btnOpenName, String btnCloseName, String expectedVal, Long filterFieldNum, boolean isOpenSelector) {
        element.moveToElementByName(fieldName);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.name(fieldName)).getAttribute("value");
        Assert.assertEquals(actualVal, expectedVal, "Element with name=[" + fieldName + "] has wrong value");
        if (isOpenSelector) {
            if (!"".equals(expectedVal)) {
                boolean isChecked = psSelector.checkValue(By.name(btnOpenName), btnCloseName, expectedVal, filterFieldNum);
                Assert.assertEquals(isChecked, true, "Radiobutton not selected");
            }
        }
    }

    public void AssertRadioPsSelectorById(String fieldId, String btnOpenId, String btnCloseName, String expectedVal, Long filterFieldNum, boolean isOpenSelector) {
        element.moveToElementById(fieldId);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.id(fieldId)).getAttribute("value");
        Assert.assertEquals(actualVal, expectedVal, "Element with id=[" + fieldId + "] has wrong value");
        if (isOpenSelector) {
            if (!"".equals(expectedVal)) {
                By btnOpen = By.id(btnOpenId);
                boolean isChecked = psSelector.checkValue(btnOpen, btnCloseName, expectedVal, filterFieldNum);
                Assert.assertEquals(isChecked, true, "Radiobutton not selected");
            }
        }
    }

    public void AssertCheckboxPsSelector(String fieldName, String btnOpenName, String btnCloseName, List<String> expectedVals, Long filterFieldNum, boolean isOpenSelector) {
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

    public void AssertCheckboxPsSelectorById(String fieldId, String btnOpenId, String btnCloseName, List<String> expectedVals, Long filterFieldNum, boolean isOpenSelector) {
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

    public void AssertFCKEditor(String name, String expectedVal) {
        String actualVal = js.getValueFromFCKEditor(name);
        actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_1, AbstractSeleniumCore.SPECIAL_CHARACTERS_1);
        actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_2, AbstractSeleniumCore.SPECIAL_CHARACTERS_2);
        actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_3, AbstractSeleniumCore.SPECIAL_CHARACTERS_3);
        actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_4, AbstractSeleniumCore.SPECIAL_CHARACTERS_4);
        Assert.assertEquals(actualVal, expectedVal, "Element with name=[" + name + "] has wrong value");
    }

    public void AssertCodeMirror(String elementId, String expectedVal) {
        wait.waitCodeMirrorLoad(elementId);
        String actualVal = js.getValueFromCodeMirror(elementId);
        Assert.assertEquals(actualVal, expectedVal, "CodeMirror editor for element with id=[" + elementId + "] has wrong value");
    }

    public void AssertElementEnabled(WebElement element, boolean expectedIsEnabled) {
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

    public void AssertFieldEnabled(String fieldName, int elementPosition) {
        WebElement element;
        String errorMessage;
        if (elementPosition > 1) {
            String idx = tb.getLastFieldIndex(fieldName, elementPosition);
            element = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx));
            errorMessage = "Element with id=[idx" + idx + "] should be enabled";
        } else {
            element = seleniumSettings.getWebDriver().findElement(By.name(fieldName));
            errorMessage = "Element with name=[" + element.getAttribute("name") + "] should be enabled";
        }

        boolean isEnabled;
        if ("textarea".equals(element.getTagName())) {
            isEnabled = !"true".equals(element.getAttribute("readonly"));
        } else if ("iframe".equals(element.getTagName())) {
            isEnabled = false;
        } else if ("input".equals(element.getTagName()) && ("text".equals(element.getAttribute("type")) || "hidden".equals(element.getAttribute("type")) )) {
            isEnabled = !"true".equals(element.getAttribute("readonly"));
        } else {
            isEnabled = element.isEnabled();
        }

        Assert.assertEquals(isEnabled, true, errorMessage);
    }

    public void AssertFieldDisabled(String fieldName, int elementPosition) {
        WebElement element;
        String errorMessage;
        if (elementPosition > 1) {
            String idx = tb.getLastFieldIndex(fieldName, elementPosition);
            element = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx));
            errorMessage = "Element with id=[idx" + idx + "] should be disabled";
        } else {
            element = seleniumSettings.getWebDriver().findElement(By.name(fieldName));
            errorMessage = "Element with name=[" + element.getAttribute("name") + "] should be disabled";
        }

        boolean isDisabled;
        if ("textarea".equals(element.getTagName())) {
            isDisabled = "true".equals(element.getAttribute("readonly"));
        } else if ("iframe".equals(element.getTagName())) {
            isDisabled = true;
        } else if ("input".equals(element.getTagName()) && ("text".equals(element.getAttribute("type")) || "hidden".equals(element.getAttribute("type")))) {
            isDisabled = "true".equals(element.getAttribute("readonly"));
        } else {
            isDisabled = !element.isEnabled();
        }

        Assert.assertEquals(isDisabled, true, errorMessage);
    }

}
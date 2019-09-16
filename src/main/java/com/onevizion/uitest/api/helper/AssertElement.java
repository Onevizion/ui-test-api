package com.onevizion.uitest.api.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

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

    public void assertCheckbox(String name, String expectedVal) {
        element.moveToElementByName("lbl" + name);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.name(name)).isSelected() ? "YES" : "NO";
        Assert.assertEquals(actualVal, expectedVal, "Element with name=[" + name + "] has wrong value");
    }

    public void assertCheckboxById(String id, String expectedVal) {
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
            psSelector.checkRadio(By.name(btnOpenName), btnCloseName, expectedVal, filterFieldNum);
        }
    }

    public void assertRadioPsSelectorById(String fieldId, String btnOpenId, String btnCloseName, String expectedVal, Long filterFieldNum, boolean isOpenSelector) {
        element.moveToElementById(fieldId);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.id(fieldId)).getAttribute("value");
        Assert.assertEquals(actualVal, expectedVal, "Element with id=[" + fieldId + "] has wrong value");
        if (isOpenSelector && !"".equals(expectedVal)) {
            psSelector.checkRadio(By.id(btnOpenId), btnCloseName, expectedVal, filterFieldNum);
        }
    }

    public void assertCheckboxPsSelector(String fieldName, String btnOpenName, String btnCloseName, List<String> expectedVals, Long filterFieldNum, boolean isOpenSelector) {
        element.moveToElementByName(fieldName);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.name(fieldName)).getAttribute("value");
        for (String expectedVal : expectedVals) {
            Assert.assertEquals(actualVal.contains(expectedVal.trim()), true, "Element with name=[" + fieldName + "] has wrong value");
        }
        if (isOpenSelector) {
            List<String> vals = new ArrayList<>();
            for (String expectedVal : expectedVals) {
                if (!"".equals(expectedVal)) {
                    vals.add(expectedVal);
                }
            }
            psSelector.checkCheckbox(By.name(btnOpenName), btnCloseName, vals, filterFieldNum);
        }
    }

    public void assertCheckboxPsSelectorById(String fieldId, String btnOpenId, String btnCloseName, List<String> expectedVals, Long filterFieldNum, boolean isOpenSelector) {
        element.moveToElementById(fieldId);
        String actualVal = seleniumSettings.getWebDriver().findElement(By.id(fieldId)).getAttribute("value");
        for (String expectedVal : expectedVals) {
            Assert.assertEquals(actualVal.contains(expectedVal.trim()), true, "Element with id=[" + fieldId + "] has wrong value");
        }
        if (isOpenSelector) {
            List<String> vals = new ArrayList<>();
            for (String expectedVal : expectedVals) {
                if (!"".equals(expectedVal)) {
                    vals.add(expectedVal);
                }
            }
            psSelector.checkCheckbox(By.id(btnOpenId), btnCloseName, vals, filterFieldNum);
        }
    }

    public void assertCodeMirror(String elementId, String expectedVal) {
        wait.waitCodeMirrorLoad(elementId);
        String actualVal = js.getValueFromCodeMirror(elementId);
        Assert.assertEquals(actualVal, expectedVal, "CodeMirror editor for element with id=[" + elementId + "] has wrong value");
    }

    public void assertElementEnabled(WebElement webElement) {
        String tag = webElement.getTagName();

        boolean isEnabled;
        if ("textarea".equals(tag)) {
            isEnabled = !"true".equals(webElement.getAttribute("readonly"));
        } else if ("iframe".equals(tag)) {
            isEnabled = false;
        } else if ("select".equals(tag)) {
            isEnabled = webElement.isEnabled();
        } else if ("input".equals(tag)) {
            String type = webElement.getAttribute("type");
            if ("checkbox".equals(type)) {
                isEnabled = webElement.isEnabled();
            } else if ("text".equals(type)) {
                isEnabled = !"true".equals(webElement.getAttribute("readonly"));
            } else if ("button".equals(type)) {
                isEnabled = webElement.isEnabled();
            } else {
                throw new SeleniumUnexpectedException("Not support type[" + type + "]");
            }
        } else {
            throw new SeleniumUnexpectedException("Not support tag[" + tag + "]");
        }

        Assert.assertEquals(isEnabled, true, "Element should be enabled");
    }

    public void assertElementDisabled(WebElement webElement) {
        String tag = webElement.getTagName();

        boolean isDisabled;
        if ("textarea".equals(tag)) {
            isDisabled = "true".equals(webElement.getAttribute("readonly"));
        } else if ("iframe".equals(tag)) {
            isDisabled = true;
        } else if ("select".equals(tag)) {
            isDisabled = !webElement.isEnabled();
        } else if ("input".equals(tag)) {
            String type = webElement.getAttribute("type");
            if ("checkbox".equals(type)) {
                isDisabled = !webElement.isEnabled();
            } else if ("text".equals(type)) {
                isDisabled = "true".equals(webElement.getAttribute("readonly"));
            } else if ("button".equals(type)) {
                isDisabled = !webElement.isEnabled();
            } else {
                throw new SeleniumUnexpectedException("Not support type[" + type + "]");
            }
        } else {
            throw new SeleniumUnexpectedException("Not support tag[" + tag + "]");
        }

        Assert.assertEquals(isDisabled, true, "Element should be disabled");
    }

}
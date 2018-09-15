package com.onevizion.uitest.api.helper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class Qs {

    private final Logger logger = LoggerFactory.getLogger(Qs.class);

    @Resource
    private Js js;

    @Resource
    private Wait wait;

    @Resource
    private ElementWait elementWait;

    @Resource
    private SeleniumSettings seleniumSettings;

    public void checkFieldsCount(Long gridIdx, int size) {
        int actualSize = seleniumSettings.getWebDriver().findElement(By.id("qsField" + gridIdx)).findElements(By.className("item_select")).size();
        Assert.assertEquals(actualSize, size);
    }

    public void checkFieldText(Long gridIdx, int index, String fieldText) {
        WebElement qsField = seleniumSettings.getWebDriver().findElement(By.id("qsField" + gridIdx)).findElements(By.className("item_select")).get(index);
        String actualFieldText;
        if (StringUtils.isNotEmpty(qsField.getAttribute("innerText"))) {
            actualFieldText = qsField.getAttribute("innerText");
        } else {
            actualFieldText = qsField.getAttribute("textContent");
        }
        Assert.assertEquals(actualFieldText, fieldText);
    }

    public void checkCurrentFieldText(Long gridIdx, String fieldText) {
        String actualFieldText = seleniumSettings.getWebDriver().findElement(By.id("qsField" + gridIdx)).findElement(By.className("dl_selected")).findElement(By.tagName("input")).getAttribute("value");
        Assert.assertEquals(actualFieldText, fieldText);
    }

    public boolean isExistQs(Long gridIdx) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = seleniumSettings.getWebDriver().findElements(By.id("qsContent" + gridIdx)).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        if (count > 0) {
            return true;
        }

        return false;
    }

    public boolean isTextQs(Long gridIdx) {
        if (seleniumSettings.getWebDriver().findElement(By.id("qsValue" + gridIdx)).isDisplayed()) {
            return true;
        }
        return false;
    }

    public boolean isBooleanQs(Long gridIdx) {
        if (seleniumSettings.getWebDriver().findElement(By.id("qsBooleanValue" + gridIdx)).isDisplayed()) {
            return true;
        }
        return false;
    }

    public void waitQsActive(Long gridIdx) {
        if (seleniumSettings.getWebDriver().findElement(By.id("qsValue" + gridIdx)).isDisplayed()) {
            try {
                elementWait.waitElementAttributeById("qsContent" + gridIdx, "class", "component quickSearch active");
            } catch (TimeoutException e) {
                logger.warn("Exception in waitQsActive", e);
            }
        }
    }

    public void selectQsFieldByName(Long gridIdx, String fieldName) {
        seleniumSettings.getWebDriver().findElement(By.id("qsField" + gridIdx)).click();
        WebElement qsElem = (WebElement) js.getNewDropDownElement("qsField" + gridIdx, "customscroll", "item_select", fieldName);
        Long position = js.getNewDropDownElementPosition("qsField" + gridIdx, "customscroll", "item_select", fieldName);
        js.scrollNewDropDownTop("qsField" + gridIdx, "customscroll", position * 28L);
        elementWait.waitElementVisible(qsElem);
        qsElem.click();
    }

    public void selectQsFieldByIdx(Long gridIdx, Long index) {
        seleniumSettings.getWebDriver().findElement(By.id("qsField" + gridIdx)).click();
        js.scrollNewDropDownTop("qsField" + gridIdx, "customscroll", (index - 1L) * 28L);
        seleniumSettings.getWebDriver().findElement(By.id("qsField" + gridIdx)).findElement(By.className("customscroll")).findElements(By.className("item_select")).get(index.intValue() - 1).click();
    }

    public void fillQsValue(Long gridIdx, String value) {
        seleniumSettings.getWebDriver().findElement(By.id("qsValue" + gridIdx)).clear();
        seleniumSettings.getWebDriver().findElement(By.id("qsValue" + gridIdx)).sendKeys(value);
    }

    public void fillQsValueFromClipboard(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id("qsValue" + gridIdx)).clear();
        seleniumSettings.getWebDriver().findElement(By.id("qsValue" + gridIdx)).click();
        Actions action = new Actions(seleniumSettings.getWebDriver());
        action.keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();
    }

    public void fillBooleanQsValue(Long gridIdx, String value) {
        seleniumSettings.getWebDriver().findElement(By.id("qsBooleanValue" + gridIdx)).click();
        List<WebElement> items = seleniumSettings.getWebDriver().findElement(By.id("qsBooleanValue" + gridIdx)).findElement(By.className("customscroll")).findElements(By.className("item_select"));
        for (WebElement item : items) {
            if (value.equals(item.getText())) {
                item.click();
            }
        }
        wait.waitGridLoad(gridIdx, gridIdx);
    }

    public void clickSearchQs(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id("btnSearch" + gridIdx)).click();
        wait.waitGridLoad(gridIdx, gridIdx);
    }

    public void clickClearQs(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id("btnSearchClear" + gridIdx)).click();
        wait.waitGridLoad(gridIdx, gridIdx);
    }

    public void searchValue(Long gridIdx, String fieldName, String search) {
        selectQsFieldByName(gridIdx, fieldName);
        fillQsValue(gridIdx, search);
        clickSearchQs(gridIdx);
    }

    public void searchValueFromClipboard(Long gridIdx, String fieldName) {
        selectQsFieldByName(gridIdx, fieldName);
        fillQsValueFromClipboard(gridIdx);
        clickSearchQs(gridIdx);
    }

    public void searchBooleanValue(Long gridIdx, String fieldName, String search) {
        selectQsFieldByName(gridIdx, fieldName);
        fillBooleanQsValue(gridIdx, search);
    }

    public void searchValue(Long gridIdx, Long fieldIndex, String search) {
        selectQsFieldByIdx(gridIdx, fieldIndex);
        fillQsValue(gridIdx, search);
        clickSearchQs(gridIdx);
    }

    public void searchBooleanValue(Long gridIdx, Long fieldIndex, String search) {
        selectQsFieldByIdx(gridIdx, fieldIndex);
        fillBooleanQsValue(gridIdx, search);
    }

}
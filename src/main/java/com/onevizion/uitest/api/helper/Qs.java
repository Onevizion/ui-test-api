package com.onevizion.uitest.api.helper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumLogger;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class Qs {

    @Autowired
    private Js js;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private Grid grid;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private SeleniumLogger seleniumLogger;

    public void checkFieldsCount(int size) {
        checkFieldsCount(AbstractSeleniumCore.getGridIdx(), size);
    }

    public void checkFieldsCount(Long gridIdx, int size) {
        int actualSize = seleniumSettings.getWebDriver().findElement(By.id("qsField" + gridIdx)).findElements(By.className("item_select")).size();
        Assert.assertEquals(actualSize, size);
    }

    public void checkFieldText(int index, String fieldText) {
        checkFieldText(AbstractSeleniumCore.getGridIdx(), index, fieldText);
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

    public void checkCurrentFieldText(String fieldText) {
        checkCurrentFieldText(AbstractSeleniumCore.getGridIdx(), fieldText);
    }

    public void checkCurrentFieldText(Long gridIdx, String fieldText) {
        String actualFieldText = seleniumSettings.getWebDriver().findElement(By.id("qsField" + gridIdx)).findElement(By.className("dl_selected")).findElement(By.tagName("input")).getAttribute("value");
        Assert.assertEquals(actualFieldText, fieldText);
    }

    public boolean isExistQs(Long gridIdx) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = seleniumSettings.getWebDriver().findElements(By.id("qsContent" + gridIdx)).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return count > 0;
    }

    public boolean isTextQs(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id("qsValue" + gridIdx)).isDisplayed();
    }

    public boolean isBooleanQs(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id("qsBooleanValue" + gridIdx)).isDisplayed();
    }

    public void waitQsActive(Long gridIdx) {
        if (seleniumSettings.getWebDriver().findElement(By.id("qsValue" + gridIdx)).isDisplayed()) {
            try {
                elementWait.waitElementAttributeById("qsContent" + gridIdx, "class", Arrays.asList("component", "quickSearch", "active"), " ");
            } catch (TimeoutException e) {
                seleniumLogger.warn("Exception in waitQsActive");
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
        grid2.waitLoad(gridIdx);
    }

    public void clickSearchQs(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id("btnSearch" + gridIdx)).click();
        grid2.waitLoad(gridIdx);
    }

    public void clickClearQs(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id("btnSearchClear" + gridIdx)).click();
        grid2.waitLoad(gridIdx);
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

    public void searchValueAndCheck(String fieldName, String search, Long rowsCnt, List<String> expectedValues) {
        searchValueAndCheck(AbstractSeleniumCore.getGridIdx(), fieldName, search, rowsCnt, expectedValues);
    }

    public void searchValueAndCheck(Long gridIdx, String fieldName, String search, Long rowsCnt, List<String> expectedValues) {
        Long columnIndex = js.getColumnIndexByLabel(gridIdx, fieldName);

        searchValue(gridIdx, fieldName, search);

        String gridPageName = grid2.getPageName(gridIdx);
        if ("TRACKOR_BROWSER".equals(gridPageName) || "WORKFLOW".equals(gridPageName)
                || "ADMIN_WF".equals(gridPageName) || "TASKS".equals(gridPageName)
                || "SUMMARY".equals(gridPageName)) {
            Assert.assertEquals(grid.getGridRowsCount(gridIdx), rowsCnt, "Grid have wrong rows count");
            checkUserpageGridTextColumnEquals(gridIdx, columnIndex, expectedValues);
        } else if ("TASKS_OVERVIEW".equals(gridPageName)) {
            Assert.assertEquals(grid.getTOGridRowsCount(gridIdx), rowsCnt, "Grid have wrong rows count");
            checkToGridTextColumnEquals(gridIdx, columnIndex, expectedValues);
        } else {
            Assert.assertEquals(grid.getGridRowsCount(gridIdx), rowsCnt, "Grid have wrong rows count");
            checkAdminpageGridTextColumnEquals(gridIdx, columnIndex, expectedValues);
        }

        clickClearQs(gridIdx);
    }

    public void searchBooleanValueAndCheck(String fieldName, String search, Long rowsCnt, List<String> expectedValues) {
        searchBooleanValueAndCheck(AbstractSeleniumCore.getGridIdx(), fieldName, search, rowsCnt, expectedValues);
    }

    public void searchBooleanValueAndCheck(Long gridIdx, String fieldName, String search, Long rowsCnt, List<String> expectedValues) {
        Long columnIndex = js.getColumnIndexByLabel(gridIdx, fieldName);

        searchBooleanValue(gridIdx, fieldName, search);

        String gridPageName = grid2.getPageName(gridIdx);
        if ("TRACKOR_BROWSER".equals(gridPageName) || "WORKFLOW".equals(gridPageName)
                || "ADMIN_WF".equals(gridPageName) || "TASKS".equals(gridPageName)
                || "SUMMARY".equals(gridPageName)) {
            Assert.assertEquals(grid.getGridRowsCount(gridIdx), rowsCnt, "Grid have wrong rows count");
            checkUserpageGridTextColumnEquals(gridIdx, columnIndex, expectedValues);
        } else if ("TASKS_OVERVIEW".equals(gridPageName)) {
            Assert.assertEquals(grid.getTOGridRowsCount(gridIdx), rowsCnt, "Grid have wrong rows count");
            checkToGridTextColumnEquals(gridIdx, columnIndex, expectedValues);
        } else {
            Assert.assertEquals(grid.getGridRowsCount(gridIdx), rowsCnt, "Grid have wrong rows count");
            checkAdminpageGridTextColumnEquals(gridIdx, columnIndex, expectedValues);
        }

        fillBooleanQsValue(gridIdx, "Search for");
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

    /*
     * Not finish. Need think in future after create many tests.
     * checkUserpageGridTextColumnEquals and checkAdminpageGridTextColumnEquals similar as userpageFilter.checkGridTextColumnEquals
     */
    private void checkUserpageGridTextColumnEquals(Long gridId, Long columnIndex, List<String> expectedValues) {
        Long rowsCnt = grid.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        for (int i = 0; i < rowsCnt; i++) {
            boolean isError = true;
            String gridValue = vals.get(i);
            for (String expectedValue : expectedValues) {
                if (expectedValue.equalsIgnoreCase(gridValue)) {
                    isError = false;
                    break;
                }
            }

            if (isError) {
                throw new SeleniumUnexpectedException("Check fails at column [" + columnIndex + "] row [" + i + "]. Cell value in grid [" + gridValue +"]");
            }
        }
    }

    /*
     * Not finish. Need think in future after create many tests.
     * checkUserpageGridTextColumnEquals and checkAdminpageGridTextColumnEquals similar as userpageFilter.checkGridTextColumnEquals
     */
    private void checkToGridTextColumnEquals(Long gridId, Long columnIndex, List<String> expectedValues) {
        int differenceInRows = js.getToGridDatePairCount(gridId);

        Long rowsCnt = grid.getTOGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt * differenceInRows, columnIndex);

        for (int i = 0; i < rowsCnt; i++) {
            boolean isError = true;
            String gridValue = vals.get(i * differenceInRows);
            for (String expectedValue : expectedValues) {
                if (expectedValue.equalsIgnoreCase(gridValue)) {
                    isError = false;
                    break;
                }
            }

            if (isError) {
                throw new SeleniumUnexpectedException("Check fails at column [" + columnIndex + "] row [" + i + "]. Cell value in grid [" + gridValue +"]");
            }
        }
    }

    /*
     * Not finish. Need think in future after create many tests.
     * checkUserpageGridTextColumnEquals and checkAdminpageGridTextColumnEquals similar as userpageFilter.checkGridTextColumnEquals
     */
    private void checkAdminpageGridTextColumnEquals(Long gridId, Long columnIndex, List<String> expectedValues) {
        Long rowsCnt = grid.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesForColumnByColIndexNew(gridId, rowsCnt, columnIndex);

        for (int i = 0; i < rowsCnt; i++) {
            boolean isError = true;
            String gridValue = vals.get(i);
            for (String expectedValue : expectedValues) {
                if (expectedValue.equalsIgnoreCase(gridValue)) {
                    isError = false;
                    break;
                }
            }

            if (isError) {
                throw new SeleniumUnexpectedException("Check fails at column [" + columnIndex + "] row [" + i + "]. Cell value in grid [" + gridValue +"]");
            }
        }
    }

}
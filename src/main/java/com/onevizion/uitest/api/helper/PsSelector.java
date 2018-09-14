package com.onevizion.uitest.api.helper;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.OnevizionUtils;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

@Component
public class PsSelector {

    @Resource
    private JsHelper jsHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private CheckboxHelper checkboxHelper;

    @Resource
    private ElementHelper elementHelper;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    @Resource
    private Qs qs;

    public String selectValue(String buttonName, Long romNum, Long colNum) {
        window.openModal(By.name(buttonName));
        wait.waitGridLoad(0L, 0L);
        String ret = null;

        for (Long i = romNum; i <= romNum; i++) {
            String value = jsHelper.getGridCellValueByRowIndexAndColIndex(0L, romNum, colNum);
            value = OnevizionUtils.removeHTMLTags(value);
            value = StringUtils.substringBefore(value, "\n");
            String compareValue = value;
            compareValue = compareValue.toUpperCase();
            compareValue = compareValue.replaceAll("FIELD", "");

            seleniumSettings.getWebDriver().findElements(By.name("rb0")).get(romNum.intValue()).click();
            ret = value;
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L));
        return ret;
    }

    public List<String> selectMultipleValues(String buttonName, Long firstRowNum, Long lastRowNum, Long colNum) {
        List<String> ret = new ArrayList<String>();
        window.openModal(By.name(buttonName));
        wait.waitGridLoad(0L, 0L);
        List<WebElement> webElements = seleniumSettings.getWebDriver().findElements(By.name("cb0_0"));
        if (firstRowNum != null && lastRowNum != null) {
            for (Long i = firstRowNum; i <= lastRowNum; i++) {
                String value = jsHelper.getGridCellValueByRowIndexAndColIndex(0L, i, colNum);
                value = OnevizionUtils.removeHTMLTags(value);
                value = StringUtils.substringBefore(value, "\n");
                String compareValue = value;
                compareValue = compareValue.toUpperCase();
                compareValue = compareValue.replaceAll("FIELD", "");

                checkboxHelper.clickByElement(webElements.get(i.intValue()));
                ret.add(jsHelper.getGridCellValueByRowIndexAndColIndex(0L, i, colNum));
            }
        } else {
            Long cnt = jsHelper.getGridRowsCount(0L);
            if (cnt.compareTo(new Long(5L)) > 0) {
                cnt = 5L;
            }
            if (webElements.size() > 0) {
                for (Long i = 0L; i < cnt; i++) {
                    String value = jsHelper.getGridCellValueByRowIndexAndColIndex(0L, i, colNum);
                    value = OnevizionUtils.removeHTMLTags(value);
                    value = StringUtils.substringBefore(value, "\n");

                    checkboxHelper.clickByElement(webElements.get(i.intValue()));
                    ret.add(jsHelper.getGridCellValueByRowIndexAndColIndex(0L, i, colNum));
                }
            }
        }
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L));
        return ret;
    }

    public String selectSpecificValue(By btnOpen, By btnClose, Long colNum, String value, Long filterFiledNum) {
        window.openModal(btnOpen);
        wait.waitWebElement(btnClose);
        wait.waitGridLoad(0L, 0L);

        if (qs.isExistQs(0L)) {
            qs.waitQsActive(0L);
            if (qs.isTextQs(0L)) {
                qs.searchValue(0L, filterFiledNum, "\"" + value + "\"");
            } else if (qs.isBooleanQs(0L)) {
                qs.searchBooleanValue(0L, filterFiledNum, value);
            } else {
                throw new SeleniumUnexpectedException("Not support QS type");
            }
            List<WebElement> webElement = seleniumSettings.getWebDriver().findElements(By.name("rb0"));
            webElement.get(0).click();
        } else {
            Long cnt = jsHelper.getGridRowsCount(0L);
            for (Long i = 0L; i < cnt; i++) {
                if (jsHelper.getGridCellValueByRowIndexAndColIndex(0L, i, colNum).equals(value)) {
                    WebElement rb = (WebElement)jsHelper.getGridCellByRowIndexAndColIndex(0L, i, 0L);
                    elementHelper.moveToElement(rb);
                    rb.click();
                    break;
                }
            }
        }

        String rowId = jsHelper.getGridSelectedRowId(0L);
        String ret = jsHelper.getGridCellValueByRowIdAndColIndex(0L, rowId, colNum);
        window.closeModal(btnClose);
        return ret;
    }

    public List<String> selectMultipleSpecificValues(By btnOpen, Long colNum, List<String> values, Long filterFiledNum) {
        List<String> ret = new ArrayList<String>();
        window.openModal(btnOpen);
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L));
        wait.waitGridLoad(0L, 0L);

        if (qs.isExistQs(0L)) {
            qs.waitQsActive(0L);
            for (String value : values) {
                if (qs.isTextQs(0L)) {
                    qs.searchValue(0L, filterFiledNum, "\"" + value + "\"");
                } else if (qs.isBooleanQs(0L)) {
                    qs.searchBooleanValue(0L, filterFiledNum, value);
                } else {
                    throw new SeleniumUnexpectedException("Not support QS type");
                }
                checkboxHelper.findLabelsByName("cb0_0").get(0).click();
                String rowId = jsHelper.getGridSelectedRowId(0L);
                ret.add(jsHelper.getGridCellValueByRowIdAndColIndex(0L, rowId, colNum));
            }
        } else {
            Long cnt = jsHelper.getGridRowsCount(0L);
            for (String value : values) {
                for (Long i = 0L; i < cnt; i++) {
                    if (jsHelper.getGridCellValueByRowIndexAndColIndex(0L, i, colNum).equals(value)) {
                        WebElement cell = (WebElement)jsHelper.getGridCellByRowIndexAndColIndex(0L, i, 0L);
                        WebElement cb = cell.findElement(By.name("cb0_0"));
                        WebElement label = checkboxHelper.findLabelByElement(cb);
                        elementHelper.moveToElement(label);
                        label.click();
                        String rowId = jsHelper.getGridSelectedRowId(0L);
                        ret.add(jsHelper.getGridCellValueByRowIdAndColIndex(0L, rowId, colNum));
                        break;
                    }
                }
                
            }
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L));
        return ret;
    }

    public List<String> selectMultipleSpecificValues2(String buttonName, Long colNum, List<String> values, Long filterFiledNum) {
        List<String> ret = new ArrayList<String>();
        window.openModal(By.name(buttonName));
        wait.waitGridLoad(0L, 0L);

        for (String value : values) {
            if (qs.isTextQs(0L)) {
                qs.searchValue(0L, filterFiledNum, value);
            } else if (qs.isBooleanQs(0L)) {
                qs.searchBooleanValue(0L, filterFiledNum, value);
            } else {
                throw new SeleniumUnexpectedException("Not support QS type");
            }
            List<WebElement> webElements = checkboxHelper.findLabelsByName("cb0_0");
            for (WebElement webElement : webElements) {
                webElement.click();
                String rowId = jsHelper.getGridSelectedRowId(0L);
                ret.add(jsHelper.getGridCellValueByRowIdAndColIndex(0L, rowId, colNum));
            }
        }

        if (ret.size() > 0) { 
            window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L));
        } else {
            window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L));
        }
        return ret;
    }

    public boolean checkValue(By btnOpen, String btnCloseName, String value, Long filterFiledNum) {
        boolean ret = false;
        window.openModal(btnOpen);
        wait.waitWebElement(By.id(btnCloseName));
        wait.waitGridLoad(0L, 0L);

        if (qs.isExistQs(0L)) {
            qs.waitQsActive(0L);
            qs.searchValue(0L, filterFiledNum, "*" + value + "*");
            List<WebElement> webElements = seleniumSettings.getWebDriver().findElements(By.name("rb0"));
            for (Long i = 0L; i < webElements.size(); i = i + 1L) {
                String checked = webElements.get(i.intValue()).getAttribute("checked");
                if (checked != null && checked.equals("true")) {
                    ret = true;
                    break;
                }
            }
        } else {
            Long cnt = jsHelper.getGridRowsCount(0L);
            WebElement rb;
            for (Long i = 0L; i < cnt; i++) {
                if (jsHelper.getGridCellValueByRowIndexAndColIndex(0L, i, 1L).equals(value)) {
                    rb = ((WebElement)jsHelper.getGridCellByRowIndexAndColIndex(0L, i, 0L)).findElement(By.name("rb0"));
                    String checked = rb.getAttribute("checked");
                    if (checked != null && checked.equals("true")) {
                        ret = true;
                    }
                    break;
                }
            }
        }

        window.closeModal(By.id(btnCloseName));
        return ret;
    }

    public boolean checkMultipleValues(By btnOpen, String btnCloseName, List<String> values, Long filterFiledNum) {
        window.openModal(btnOpen);
        wait.waitWebElement(By.name(btnCloseName));
        wait.waitGridLoad(0L, 0L);

        if (qs.isExistQs(0L)) {
            for (String value : values) {
                qs.searchValue(0L, filterFiledNum, "*" + value + "*");
                List<WebElement> webElements = checkboxHelper.findCheckboxesByName("cb0_0");
                boolean ret = false;
                for (Long i = 0L; i < webElements.size(); i = i + 1L) {
                    if (checkboxHelper.isElementChecked(webElements.get(i.intValue()))) {
                        ret = true;
                        break;
                    }
                }
                if (ret == false) {
                    window.closeModal(By.name(btnCloseName));
                    return ret;
                }
            }
        } else {
            Long cnt = jsHelper.getGridRowsCount(0L);
            for (String value : values) {
                boolean ret = false;
                for (Long i = 0L; i < cnt; i++) {
                    if (jsHelper.getGridCellValueByRowIndexAndColIndex(0L, i, 1L).equals(value)) {
                        WebElement cell = (WebElement)jsHelper.getGridCellByRowIndexAndColIndex(0L, i, 0L);
                        WebElement cb = cell.findElement(By.name("cb0_0"));
                        if (checkboxHelper.isElementChecked(cb)) {
                            ret = true;
                        }
                        break;
                    }
                }
                if (ret == false) {
                    window.closeModal(By.name(btnCloseName));
                    return ret;
                }
            }
        }

        window.closeModal(By.name(btnCloseName));
        return true;
    }

}
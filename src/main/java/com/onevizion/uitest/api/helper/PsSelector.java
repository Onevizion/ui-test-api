package com.onevizion.uitest.api.helper;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class PsSelector {

    @Resource
    private Js js;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private Checkbox checkbox;

    @Resource
    private Element element;

    @Resource
    private Qs qs;

    @Resource
    private Grid2 grid2;

    public void selectValue(String buttonName, Long romNum) {
        window.openModal(By.name(buttonName));
        grid2.waitLoad();

        seleniumSettings.getWebDriver().findElements(By.name("rb0")).get(romNum.intValue()).click();

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L));
    }

    public void selectMultipleValues(String buttonName, Long firstRowNum, Long lastRowNum) {
        window.openModal(By.name(buttonName));
        grid2.waitLoad();

        List<WebElement> webElements = seleniumSettings.getWebDriver().findElements(By.name("cb0_0"));
        for (Long i = firstRowNum; i <= lastRowNum; i++) {
            checkbox.clickByElement(webElements.get(i.intValue()));
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L));
    }

    public void selectSpecificValue(By btnOpen, By btnClose, Long colNum, String value, Long filterFiledNum) {
        window.openModal(btnOpen);
        wait.waitWebElement(btnClose);
        grid2.waitLoad();

        if (qs.isExistQs(0L)) {
            qs.waitQsActive(0L);
            if (qs.isTextQs(0L)) {
                qs.searchValue(0L, filterFiledNum, "\"" + value + "\"");
            } else if (qs.isBooleanQs(0L)) {
                qs.searchBooleanValue(0L, filterFiledNum, value);
            } else {
                throw new SeleniumUnexpectedException("Not support QS type");
            }
            seleniumSettings.getWebDriver().findElements(By.name("rb0")).get(0).click();
        } else {
            Long cnt = js.getGridRowsCount(0L);
            for (Long i = 0L; i < cnt; i++) {
                if (js.getGridCellValueByRowIndexAndColIndex(0L, i, colNum).equals(value)) {
                    WebElement rb = (WebElement) js.getGridCellByRowIndexAndColIndex(0L, i, 0L);
                    element.moveToElement(rb);
                    rb.click();
                    break;
                }
            }
        }

        window.closeModal(btnClose);
    }

    public void selectMultipleSpecificValues(By btnOpen, Long colNum, List<String> values, Long filterFiledNum) {
        window.openModal(btnOpen);
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L));
        grid2.waitLoad();

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
                checkbox.findLabelsByName("cb0_0").get(0).click();
            }
        } else {
            Long cnt = js.getGridRowsCount(0L);
            for (String value : values) {
                for (Long i = 0L; i < cnt; i++) {
                    if (js.getGridCellValueByRowIndexAndColIndex(0L, i, colNum).equals(value)) {
                        WebElement cell = (WebElement) js.getGridCellByRowIndexAndColIndex(0L, i, 0L);
                        WebElement cb = cell.findElement(By.name("cb0_0"));
                        WebElement label = checkbox.findLabelByElement(cb);
                        element.moveToElement(label);
                        label.click();
                        break;
                    }
                }
            }
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L));
    }

    public boolean checkValue(By btnOpen, String btnCloseName, String value, Long filterFiledNum) {
        boolean ret = false;
        window.openModal(btnOpen);
        wait.waitWebElement(By.id(btnCloseName));
        grid2.waitLoad();

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
            Long cnt = js.getGridRowsCount(0L);
            WebElement rb;
            for (Long i = 0L; i < cnt; i++) {
                if (js.getGridCellValueByRowIndexAndColIndex(0L, i, 1L).equals(value)) {
                    rb = ((WebElement)js.getGridCellByRowIndexAndColIndex(0L, i, 0L)).findElement(By.name("rb0"));
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
        grid2.waitLoad();

        if (qs.isExistQs(0L)) {
            for (String value : values) {
                qs.searchValue(0L, filterFiledNum, "*" + value + "*");
                List<WebElement> webElements = checkbox.findCheckboxesByName("cb0_0");
                boolean ret = false;
                for (Long i = 0L; i < webElements.size(); i = i + 1L) {
                    if (checkbox.isElementChecked(webElements.get(i.intValue()))) {
                        ret = true;
                        break;
                    }
                }
                if (!ret) {
                    window.closeModal(By.name(btnCloseName));
                    return ret;
                }
            }
        } else {
            Long cnt = js.getGridRowsCount(0L);
            for (String value : values) {
                boolean ret = false;
                for (Long i = 0L; i < cnt; i++) {
                    if (js.getGridCellValueByRowIndexAndColIndex(0L, i, 1L).equals(value)) {
                        WebElement cell = (WebElement)js.getGridCellByRowIndexAndColIndex(0L, i, 0L);
                        WebElement cb = cell.findElement(By.name("cb0_0"));
                        if (checkbox.isElementChecked(cb)) {
                            ret = true;
                        }
                        break;
                    }
                }
                if (!ret) {
                    window.closeModal(By.name(btnCloseName));
                    return ret;
                }
            }
        }

        window.closeModal(By.name(btnCloseName));
        return true;
    }

}
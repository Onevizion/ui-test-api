package com.onevizion.uitest.api.helper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class Selector {

    @Autowired
    private Js js;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private Checkbox checkbox;

    @Autowired
    private Element element;

    @Autowired
    private Qs qs;

    @Autowired
    private Grid2 grid2;

    public void selectRadio(By btnOpen, By btnClose, int colNum, String value, Long filterFiledNum) {
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
                    WebElement rb = (WebElement) js.getGridCellByRowIndexAndColIndex(0L, i, 0);
                    element.moveToElement(rb);
                    rb.click();
                    break;
                }
            }
        }

        window.closeModal(btnClose);
    }

    public void selectCheckbox(By btnOpen, int colNum, List<String> values, Long filterFiledNum) {
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
                        WebElement cell = (WebElement) js.getGridCellByRowIndexAndColIndex(0L, i, 0);
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

    public void checkRadio(By btnOpen, String btnCloseName, String value, Long filterFiledNum) {
        window.openModal(btnOpen);
        wait.waitWebElement(By.id(btnCloseName));
        grid2.waitLoad();

        boolean isChecked = false;

        if (qs.isExistQs(0L)) {
            qs.waitQsActive(0L);
            qs.searchValue(0L, filterFiledNum, "*" + value + "*");
            List<WebElement> webElements = seleniumSettings.getWebDriver().findElements(By.name("rb0"));
            for (Long i = 0L; i < webElements.size(); i = i + 1L) {
                String checked = webElements.get(i.intValue()).getAttribute("checked");
                if (checked != null && checked.equals("true")) {
                    isChecked = true;
                    break;
                }
            }
        } else {
            Long cnt = js.getGridRowsCount(0L);
            for (Long i = 0L; i < cnt; i++) {
                if (js.getGridCellValueByRowIndexAndColIndex(0L, i, 1).equals(value)) {
                    WebElement rb = ((WebElement) js.getGridCellByRowIndexAndColIndex(0L, i, 0)).findElement(By.name("rb0"));
                    String checked = rb.getAttribute("checked");
                    if (checked != null && checked.equals("true")) {
                        isChecked = true;
                        break;
                    }
                }
            }
        }

        Assert.assertEquals(isChecked, true, "value not selected");

        window.closeModal(By.id(btnCloseName));
    }

    public void checkCheckbox(By btnOpen, String btnCloseName, List<String> values, Long filterFiledNum) {
        window.openModal(btnOpen);
        wait.waitWebElement(By.name(btnCloseName));
        grid2.waitLoad();

        if (qs.isExistQs(0L)) {
            for (String value : values) {
                boolean isChecked = false;

                qs.searchValue(0L, filterFiledNum, "*" + value + "*");
                List<WebElement> webElements = checkbox.findCheckboxesByName("cb0_0");
                for (Long i = 0L; i < webElements.size(); i = i + 1L) {
                    if (checkbox.isElementChecked(webElements.get(i.intValue()))) {
                        isChecked = true;
                        break;
                    }
                }

                Assert.assertEquals(isChecked, true, "value not selected");
            }
        } else {
            Long cnt = js.getGridRowsCount(0L);
            for (String value : values) {
                boolean isChecked = false;

                for (Long i = 0L; i < cnt; i++) {
                    if (js.getGridCellValueByRowIndexAndColIndex(0L, i, 1).equals(value)) {
                        WebElement cell = (WebElement) js.getGridCellByRowIndexAndColIndex(0L, i, 0);
                        WebElement cb = cell.findElement(By.name("cb0_0"));
                        if (checkbox.isElementChecked(cb)) {
                            isChecked = true;
                            break;
                        }
                    }
                }

                Assert.assertEquals(isChecked, true, "value not selected");
            }
        }

        window.closeModal(By.name(btnCloseName));
    }

}
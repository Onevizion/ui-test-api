package com.onevizion.uitest.api.helper.userpage.filter;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.OnevizionUtils;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.PsSelector;
import com.onevizion.uitest.api.helper.Tb;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.filter.Filter;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.FilterOperatorType;

@Component
public class UserpageFilter {

    public static final String BUTTON_OPEN = "btnFilter";
    public static final String BUTTON_CLEAR = "btnClear";
    public static final String BUTTON_ADD = "btnAdd";
    public static final String BUTTON_OK_ID_BASE = "btnOK";

    public static final int FIRST_MONTH_OF_FISCAL_YEAR = 1;

    private static final String FILTER_ROW_ATTRIB_TEXT = "txtWPAttrib";
    private static final String FILTER_ROW_ATTRIB_BUTTON = "btnWPAttrib";
    private static final String FILTER_ROW_OPER_TASK = "tdWPOperator";
    private static final String FILTER_ROW_OPER = "WPOperator";
    private static final String FILTER_ROW_VALUE_FIELD_TEXT = "fSelWPAttribValue";
    public static final String FILTER_ROW_VALUE_FIELD_BUTTON = "btnFSelWPAttribValue";
    private static final String FILTER_ROW_VALUE_TRACKOR_SELECTOR_TEXT = "trSelWPAttribValue";
    private static final String FILTER_ROW_VALUE_TRACKOR_SELECTOR_BUTTON = "btnTrSelWPAttribValue";
    private static final String FILTER_ROW_VALUE_MULTI_SELECTOR_TEXT = "multSelWPAttribValue";
    private static final String FILTER_ROW_VALUE_MULTI_SELECTOR_BUTTON = "btnMultSelWPAttribValue";
    private static final String FILTER_ROW_VALUE_TRACKOR_DROP_DOWN_TEXT = "trDropDownWPAttribValue";
    private static final String FILTER_ROW_VALUE_TRACKOR_DROP_DOWN_BUTTON = "btnTrDropDownWPAttribValue";
    private static final String FILTER_ROW_VALUE_SELECTOR_TEXT = "selWPAttribValue";
    public static final String FILTER_ROW_VALUE_SELECTOR_BUTTON = "btnSelWPAttribValue";
    public static final String FILTER_ROW_VALUE_DATE_TEXT = "dateWPAttribValue";
    public static final String FILTER_ROW_VALUE_DATETIME_TEXT = "dateTimeWPAttribValue";
    private static final String FILTER_ROW_VALUE_TIME_TEXT = "timeWPAttribValue";
    private static final String FILTER_ROW_VALUE_NUMBER_TEXT = "numWPAttribValue";
    private static final String FILTER_ROW_VALUE_LATLONG_TEXT = "latlongWPAttribValue";
    public static final String FILTER_ROW_VALUE_CHECKBOX_TEXT = "ynWPAttribValue";
    public static final String FILTER_ROW_VALUE_TEXT_TEXT = "txtWPAttribValue";

    @Resource
    private Js js;

    @Resource
    private Wait wait;

    @Resource
    private Window window;

    @Resource
    private PsSelector psSelector;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Grid grid;

    @Resource
    private Filter filter;

    @Resource
    private Tb tb;

    @Resource
    private SeleniumSettings seleniumSettings;

    public void checkFilterOperators(String fieldName, List<String> dateTypes, List<FilterOperatorType> operators) {
        window.openModal(By.id(UserpageFilter.BUTTON_OPEN + 0L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        psSelector.selectSpecificValue(By.name(FILTER_ROW_ATTRIB_BUTTON + 1), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1L, fieldName, 1L);
        if (dateTypes != null) {
            List<WebElement> options = new Select(seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_OPER_TASK + 1))).getOptions();
            Assert.assertEquals(options.size(), dateTypes.size());
            for (int i = 0; i < dateTypes.size(); i++) {
                Assert.assertEquals(options.get(i).getText(), dateTypes.get(i));
            }
        }
        List<WebElement> options = new Select(seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_OPER + 1))).getOptions();
        Assert.assertEquals(options.size(), operators.size());
        for (int i = 0; i < operators.size(); i++) {
            Assert.assertEquals(options.get(i).getText(), operators.get(i).getValue());
        }
        window.closeModalWithAlert(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE), null);
    }

    @SuppressWarnings("unchecked")
    public void checkFilter(String fieldName, String fieldName2, String value, String dateType,
            FilterOperatorType operator, ConfigFieldType fieldDataType, Long columnIndex, Long columnIndex2,
            List<String> cellVals, List<String> cellVals2, List<String> ... cellValsKeys) {
        Long rowsCntBefore = grid.getGridRowsCount(0L);

        int randomIndex = fillFilter(fieldName, fieldName2, value, dateType, operator, fieldDataType);

        if ((fieldName.equals("FTB:FTB ID") || fieldName.equals("FT:FT ID") || fieldName.equals("FR:FR ID")
                || fieldName.equals("FTB:Rollup") || fieldName.equals("FTBP:Rollup") || fieldName.equals("FTBC:Rollup")
                || fieldName.equals("FT:Rollup") || fieldName.equals("FTP:Rollup") || fieldName.equals("FTC:Rollup")
                || fieldName.equals("FR:Rollup") || fieldName.equals("FRP:Rollup") || fieldName.equals("FRC:Rollup")) && operator.equals(FilterOperatorType.NULL)) {
            
        } else {
            Long rowsCntAfter = grid.getGridRowsCount(0L);
            if (rowsCntAfter.equals(Long.valueOf(0L))) {
                throw new SeleniumUnexpectedException("Grid have wrong rows count");
            }
        }

        if (fieldDataType.equals(ConfigFieldType.TEXT) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.WIKI) || fieldDataType.equals(ConfigFieldType.SELECTOR)
                || fieldDataType.equals(ConfigFieldType.MEMO) || fieldDataType.equals(ConfigFieldType.ELECTRONIC_FILE)
                || fieldDataType.equals(ConfigFieldType.DB_SELECTOR) || fieldDataType.equals(ConfigFieldType.DB_DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.HYPERLINK) || fieldDataType.equals(ConfigFieldType.DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.CALCULATED) || fieldDataType.equals(ConfigFieldType.ROLLUP)
                || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR) || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
            if (operator.equals(FilterOperatorType.EQUAL)) {
                checkGridRowsCountEquals(fieldDataType, cellVals, value);
                checkGridTextColumnEquals(0L, columnIndex, Arrays.asList(value));
            } else if (operator.equals(FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER)) {
                checkGridRowsCountEqualsOrNull(fieldDataType, cellVals, value, rowsCntBefore, cellValsKeys);
                checkGridTextColumnEqualsOrNull(0L, columnIndex, value);
            } else if (operator.equals(FilterOperatorType.NOT_EQUAL)) {
                checkGridRowsCountNotEquals(cellVals, value);
                checkGridTextColumnNotEquals(0L, columnIndex, value);
            } else if (operator.equals(FilterOperatorType.NOT_EQUAL_AND_EMPTY_FOR_OTHER)) {
                checkGridRowsCountNotEqualsOrNull(fieldDataType, cellVals, value, rowsCntBefore, cellValsKeys);
                checkGridTextColumnNotEqualsOrNull(0L, columnIndex, value);
            } else if (operator.equals(FilterOperatorType.NULL)) {
                checkGridRowsCountIsNull(fieldDataType, cellVals);
                checkGridColumnIsNull(0L, columnIndex);
            } else if (operator.equals(FilterOperatorType.NOT_NULL)) {
                checkGridRowsCountIsNotNull(fieldDataType, cellVals);
                checkGridColumnIsNotNull(0L, columnIndex);
            } else if (operator.equals(FilterOperatorType.EQUAL_FIELD)) {
                checkGridRowsCountEqualsField(cellVals, cellVals2);
                checkGridColumnEqualsField(0L, columnIndex, columnIndex2);
            } else if (operator.equals(FilterOperatorType.NOT_EQUAL_FIELD)) {
                checkGridRowsCountNotEqualsField(cellVals, cellVals2);
                checkGridColumnNotEqualsField(0L, columnIndex, columnIndex2);
            } else {
                throw new SeleniumUnexpectedException("Not support operation");
            }
        } else if (fieldDataType.equals(ConfigFieldType.CHECKBOX)) {
            if (operator.equals(FilterOperatorType.EQUAL)) {
                checkGridRowsCountEquals(fieldDataType, cellVals, value);
                checkGridBooleanColumnEquals(0L, columnIndex, Arrays.asList(value.toUpperCase()));
            } else if (operator.equals(FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER)) {
                checkGridRowsCountEqualsOrNull(fieldDataType, cellVals, value, rowsCntBefore, cellValsKeys);
                checkGridBooleanColumnEqualsOrNull(0L, columnIndex, value.toUpperCase());
            } else {
                throw new SeleniumUnexpectedException("Not support operation");
            }
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            if (operator.equals(FilterOperatorType.EQUAL)) {
                checkGridRowsCountEquals(fieldDataType, cellVals, value);
                checkGridTextColumnEquals(0L, columnIndex, Arrays.asList(value));
            } else if (operator.equals(FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER)) {
                checkGridRowsCountEqualsOrNull(fieldDataType, cellVals, value, rowsCntBefore, cellValsKeys);
                checkGridTextColumnEqualsOrNull(0L, columnIndex, value);
            } else if (operator.equals(FilterOperatorType.MORE)) {
                checkGridRowsCountMore(fieldDataType, cellVals, value);
                checkGridColumnMore(0L, columnIndex, value, fieldDataType);
            } else if (operator.equals(FilterOperatorType.LESS)) {
                checkGridRowsCountLess(fieldDataType, cellVals, value);
                checkGridColumnLess(0L, columnIndex, value, fieldDataType);
            } else if (operator.equals(FilterOperatorType.MORE_AND_EQUAL)) {
                checkGridRowsCountMoreEquals(fieldDataType, cellVals, value);
                checkGridColumnMoreEquals(0L, columnIndex, value, fieldDataType);
            } else if (operator.equals(FilterOperatorType.LESS_AND_EQUAL)) {
                checkGridRowsCountLessEquals(fieldDataType, cellVals, value);
                checkGridColumnLessEquals(0L, columnIndex, value, fieldDataType);
            } else if (operator.equals(FilterOperatorType.NOT_EQUAL)) {
                checkGridRowsCountNotEquals(cellVals, value);
                checkGridTextColumnNotEquals(0L, columnIndex, value);
            } else if (operator.equals(FilterOperatorType.NOT_EQUAL_AND_EMPTY_FOR_OTHER)) {
                checkGridRowsCountNotEqualsOrNull(fieldDataType, cellVals, value, rowsCntBefore, cellValsKeys);
                checkGridTextColumnNotEqualsOrNull(0L, columnIndex, value);
            } else if (operator.equals(FilterOperatorType.NULL)) {
                checkGridRowsCountIsNull(fieldDataType, cellVals);
                checkGridColumnIsNull(0L, columnIndex);
            } else if (operator.equals(FilterOperatorType.NOT_NULL)) {
                checkGridRowsCountIsNotNull(fieldDataType, cellVals);
                checkGridColumnIsNotNull(0L, columnIndex);
            } else if (operator.equals(FilterOperatorType.EQUAL_FIELD)) {
                checkGridRowsCountEqualsField(cellVals, cellVals2);
                checkGridColumnEqualsField(0L, columnIndex, columnIndex2);
            } else if (operator.equals(FilterOperatorType.NOT_EQUAL_FIELD)) {
                checkGridRowsCountNotEqualsField(cellVals, cellVals2);
                checkGridColumnNotEqualsField(0L, columnIndex, columnIndex2);
            } else if (operator.equals(FilterOperatorType.MORE_FIELD)) {
                checkGridRowsCountMoreField(fieldDataType, cellVals, cellVals2);
                checkGridColumnMoreField(0L, columnIndex, columnIndex2, fieldDataType);
            } else if (operator.equals(FilterOperatorType.LESS_FIELD)) {
                checkGridRowsCountLessField(fieldDataType, cellVals, cellVals2);
                checkGridColumnLessField(0L, columnIndex, columnIndex2, fieldDataType);
            } else if (operator.equals(FilterOperatorType.MORE_AND_EQUAL_FIELD)) {
                checkGridRowsCountMoreEqualsField(fieldDataType, cellVals, cellVals2);
                checkGridColumnMoreEqualsField(0L, columnIndex, columnIndex2, fieldDataType);
            } else if (operator.equals(FilterOperatorType.LESS_AND_EQUAL_FIELD)) {
                checkGridRowsCountLessEqualsField(fieldDataType, cellVals, cellVals2);
                checkGridColumnLessEqualsField(0L, columnIndex, columnIndex2, fieldDataType);
            } else if (operator.equals(FilterOperatorType.MORE_AND_EQUAL_TODAY)) {
                checkGridRowsCountMoreEqualsToday(fieldDataType, cellVals, cellValsKeys);
                checkGridColumnMoreEqualsToday(0L, columnIndex, Integer.parseInt((cellValsKeys[0].get(0) + cellValsKeys[0].get(1)).replace(" ", "")), fieldDataType);
            } else if (operator.equals(FilterOperatorType.LESS_AND_EQUAL_TODAY)) {
                checkGridRowsCountLessEqualsToday(fieldDataType, cellVals, cellValsKeys);
                checkGridColumnLessEqualsToday(0L, columnIndex, Integer.parseInt((cellValsKeys[0].get(0) + cellValsKeys[0].get(1)).replace(" ", "")), fieldDataType);
            } else if (operator.equals(FilterOperatorType.WITHIN)) {
                checkGridRowsCountWithin(fieldDataType, cellVals, cellValsKeys);
                checkGridColumnWithin(0L, columnIndex, Integer.parseInt(cellValsKeys[0].get(0)), Integer.parseInt(cellValsKeys[0].get(1)), fieldDataType);
            } else if (operator.equals(FilterOperatorType.THIS_WK)) {
                checkGridRowsCountThisWk(fieldDataType, cellVals, cellValsKeys);
                checkGridColumnThisWk(0L, columnIndex, Integer.parseInt((cellValsKeys[0].get(0) + cellValsKeys[0].get(1)).replace(" ","")), fieldDataType);
            } else if (operator.equals(FilterOperatorType.THIS_MO)) {
                checkGridRowsCountThisMo(fieldDataType, cellVals, cellValsKeys);
                checkGridColumnThisMo(0L, columnIndex, Integer.parseInt((cellValsKeys[0].get(0) + cellValsKeys[0].get(1)).replace(" ","")), fieldDataType);
            } else if (operator.equals(FilterOperatorType.THIS_FQ)) {
                checkGridRowsCountThisFQ(fieldDataType, cellVals, cellValsKeys);
                checkGridColumnThisFQ(0L, columnIndex, Integer.parseInt((cellValsKeys[0].get(0) + cellValsKeys[0].get(1)).replace(" ","")), fieldDataType);
            } else if (operator.equals(FilterOperatorType.THIS_FY)) {
                checkGridRowsCountThisFY(fieldDataType, cellVals, cellValsKeys);
                checkGridColumnThisFY(0L, columnIndex, Integer.parseInt((cellValsKeys[0].get(0) + cellValsKeys[0].get(1)).replace(" ","")), fieldDataType);
            } else if (operator.equals(FilterOperatorType.THIS_WK_TO_DT)) {
                checkGridRowsCountThisWkToDt(fieldDataType, cellVals);
                checkGridColumnThisWkToDt(0L, columnIndex, fieldDataType);
            } else if (operator.equals(FilterOperatorType.THIS_MO_TO_DT)) {
                checkGridRowsCountThisMoToDt(fieldDataType, cellVals);
                checkGridColumnThisMoToDt(0L, columnIndex, fieldDataType);
            } else if (operator.equals(FilterOperatorType.THIS_FQ_TO_DT)) {
                checkGridRowsCountThisFQToDt(fieldDataType, cellVals);
                checkGridColumnThisFQToDt(0L, columnIndex, fieldDataType);
            } else if (operator.equals(FilterOperatorType.THIS_FY_TO_DT)) {
                checkGridRowsCountThisFYToDt(fieldDataType, cellVals);
                checkGridColumnThisFYToDt(0L, columnIndex, fieldDataType);
            } else {
                throw new SeleniumUnexpectedException("Not support operation");
            }
        } else {
            throw new SeleniumUnexpectedException("Not support field data type");
        }

        checkAndClearFilter(fieldName, fieldName2, value, dateType, operator, fieldDataType, randomIndex);
    }

    public void checkFilterIsNew(String fieldName, List<String> cellVals, List<String> newTrackors) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.NEW);

        checkGridRowsCountIsNew(cellVals, newTrackors);
        checkGridColumnIsNew(0L, fieldName, newTrackors);

        checkAndClearFilter(fieldName, FilterOperatorType.NEW, randomIndex);
    }

    public void checkFilterIsNotNew(String fieldName, List<String> cellVals, List<String> newTrackors) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.NOT_NEW);

        checkGridRowsCountIsNotNew(cellVals, newTrackors);
        checkGridColumnIsNotNew(0L, fieldName, newTrackors);

        checkAndClearFilter(fieldName, FilterOperatorType.NOT_NEW, randomIndex);
    }

    private int fillFilter(String fieldName, FilterOperatorType operator) {
        SecureRandom generator = new SecureRandom();
        int randomIndex = generator.nextInt(2);

        if (randomIndex == 1) {
            selectFilterAttributeAndOperatorAndValue(fieldName, operator);
        } else if (randomIndex == 0) {
            filter.selectByVisibleText("G:" + fieldName + " " + operator.getValue(), 0L);
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }

        return randomIndex;
    }

    private int fillFilter(String fieldName, String fieldName2, String value, String dateType, FilterOperatorType operator, ConfigFieldType fieldDataType) {
        SecureRandom generator = new SecureRandom();
        int randomIndex = generator.nextInt(2);

        if (randomIndex == 1) {
            selectFilterAttributeAndOperatorAndValue(1, fieldName, fieldName2, value, dateType, operator, fieldDataType);
        } else if (randomIndex == 0) {
            if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
                filter.selectByVisibleText("G:" + fieldName + " " + operator.getValue() + " " + value, 0L);
            } else if (dateType != null) {
                filter.selectByVisibleText("G:" + fieldName + " " + operator.getValue() + " " + dateType, 0L);
            } else {
                filter.selectByVisibleText("G:" + fieldName + " " + operator.getValue(), 0L);
            }
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }

        return randomIndex;
    }

    private void checkAndClearFilter(String fieldName, FilterOperatorType operator, int randomIndex) {
        if (randomIndex == 1) {
            checkFilterAttributeAndOperatorAndValue(fieldName, operator);
            clearFilter();
        } else if (randomIndex == 0) {
            filter.selectByVisibleText("Unsaved Filter", 0L);
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }
    }

    private void checkAndClearFilter(String fieldName, String fieldName2, String value, String dateType, FilterOperatorType operator, ConfigFieldType fieldDataType, int randomIndex) {
        if (randomIndex == 1) {
            checkFilterAttributeAndOperatorAndValue(1, fieldName, fieldName2, value, dateType, operator, fieldDataType);
            clearFilter();
        } else if (randomIndex == 0) {
            filter.selectByVisibleText("Unsaved Filter", 0L);
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }
    }

    private void clearFilter() {
        window.openModal(By.id(UserpageFilter.BUTTON_OPEN + 0L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(UserpageFilter.BUTTON_CLEAR)).click();

        window.closeModalAndWaitGridLoad(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    private void selectFilterAttributeAndOperatorAndValue(String fieldName, FilterOperatorType operator) {
        window.openModal(By.id(UserpageFilter.BUTTON_OPEN + 0L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        psSelector.selectSpecificValue(By.name(FILTER_ROW_ATTRIB_BUTTON + 1), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1L, fieldName, 1L);

        new Select(seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_OPER + 1))).selectByVisibleText(operator.getValue());

        window.closeModalAndWaitGridLoad(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    public void selectFilterAttributeAndOperatorAndValue(int row, String fieldName, String fieldName2, String value, String dateType, FilterOperatorType operator, ConfigFieldType fieldDataType) {
        window.openModal(By.id(UserpageFilter.BUTTON_OPEN + 0L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        psSelector.selectSpecificValue(By.name(FILTER_ROW_ATTRIB_BUTTON + row), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1L, fieldName, 1L);
        if (dateType != null) {
            new Select(seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_OPER_TASK + row))).selectByVisibleText(dateType);
        }
        new Select(seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_OPER + row))).selectByVisibleText(operator.getValue());
        if (fieldDataType.equals(ConfigFieldType.DROP_DOWN) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.SELECTOR) || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
            if (operator.equals(FilterOperatorType.EQUAL_FIELD) || operator.equals(FilterOperatorType.NOT_EQUAL_FIELD)) {
                psSelector.selectSpecificValue(By.name(FILTER_ROW_VALUE_FIELD_BUTTON + row), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1L, fieldName2, 1L);
            } else if (!operator.equals(FilterOperatorType.NULL) && !operator.equals(FilterOperatorType.NOT_NULL)
                    && !operator.equals(FilterOperatorType.NEW) && !operator.equals(FilterOperatorType.NOT_NEW)) {
                if (fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)) {
                    psSelector.selectMultipleSpecificValues(By.name(FILTER_ROW_VALUE_TRACKOR_SELECTOR_BUTTON + row), 0L, Arrays.asList(value), 1L);
                } else if (fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR)) {
                    psSelector.selectMultipleSpecificValues(By.name(FILTER_ROW_VALUE_MULTI_SELECTOR_BUTTON + row), 0L, Arrays.asList(value), 1L);
                } else if (fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
                    psSelector.selectSpecificValue(By.name(FILTER_ROW_VALUE_TRACKOR_DROP_DOWN_BUTTON + row), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 0L, value, 1L);
                } else {
                    psSelector.selectMultipleSpecificValues(By.name(FILTER_ROW_VALUE_SELECTOR_BUTTON + row), 0L, Arrays.asList(value), 1L);
                }
            }
        } else if (fieldDataType.equals(ConfigFieldType.CHECKBOX)) {
            new Select(seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_CHECKBOX_TEXT + row))).selectByVisibleText(value);
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            if (operator.equals(FilterOperatorType.EQUAL_FIELD) || operator.equals(FilterOperatorType.NOT_EQUAL_FIELD)
                    || operator.equals(FilterOperatorType.MORE_FIELD) || operator.equals(FilterOperatorType.LESS_FIELD)
                    || operator.equals(FilterOperatorType.MORE_AND_EQUAL_FIELD) || operator.equals(FilterOperatorType.LESS_AND_EQUAL_FIELD)) {
                psSelector.selectSpecificValue(By.name(FILTER_ROW_VALUE_FIELD_BUTTON + row), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1L, fieldName2, 1L);
            //} else if (operator.equals(FilterOperatorType.MORE_AND_EQUAL_TODAY) || operator.equals(FilterOperatorType.LESS_AND_EQUAL_TODAY) //TODO check those lines
            //        || operator.equals(FilterOperatorType.THIS_WK) || operator.equals(FilterOperatorType.THIS_MO)
            //        || operator.equals(FilterOperatorType.THIS_FQ) || operator.equals(FilterOperatorType.THIS_FY)) {
            //    if (cellValsKeys != null) {
            //        new Select(seleniumSettings.getWebDriver().findElement(By.name("todayDirWPAttribValue1"))).selectByVisibleText(cellValsKeys[0].get(0));
            //        if (!"0".equals(cellValsKeys[0].get(1))) {
            //            seleniumSettings.getWebDriver().findElement(By.name("todayValWPAttribValue1")).clear();
            //            seleniumSettings.getWebDriver().findElement(By.name("todayValWPAttribValue1")).sendKeys(cellValsKeys[0].get(1));
            //        }
            //    }
            //} else if (operator.equals(FilterOperatorType.WITHIN)) {
            //    if ((cellValsKeys != null) && ((!"0".equals(cellValsKeys[0].get(0)))||(!"0".equals(cellValsKeys[0].get(1))))) {
            //        seleniumSettings.getWebDriver().findElement(By.name("withinBeforeWPAttribValue1")).clear();
            //        seleniumSettings.getWebDriver().findElement(By.name("withinBeforeWPAttribValue1")).sendKeys(cellValsKeys[0].get(0));
            //        seleniumSettings.getWebDriver().findElement(By.name("withinAfterWPAttribValue1")).clear();
            //        seleniumSettings.getWebDriver().findElement(By.name("withinAfterWPAttribValue1")).sendKeys(cellValsKeys[0].get(1));
            //    }
            } else if (!operator.equals(FilterOperatorType.NULL) && !operator.equals(FilterOperatorType.NOT_NULL)) {
                    //&& !operator.equals(FilterOperatorType.THIS_WK_TO_DT) && !operator.equals(FilterOperatorType.THIS_MO_TO_DT) //TODO check those lines
                    //&& !operator.equals(FilterOperatorType.THIS_FQ_TO_DT) && !operator.equals(FilterOperatorType.THIS_FY_TO_DT)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_DATE_TEXT + row)).sendKeys(value);
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_DATETIME_TEXT + row)).sendKeys(value);
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_TIME_TEXT + row)).sendKeys(value);
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER)) {
                    seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_NUMBER_TEXT + row)).sendKeys(value);
                } else if (fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_LATLONG_TEXT + row)).sendKeys(value);
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        } else {
            if (operator.equals(FilterOperatorType.EQUAL_FIELD) || operator.equals(FilterOperatorType.NOT_EQUAL_FIELD)) {
                psSelector.selectSpecificValue(By.name(FILTER_ROW_VALUE_FIELD_BUTTON + row), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1L, fieldName2, 1L);
            } else if (!operator.equals(FilterOperatorType.NULL) && !operator.equals(FilterOperatorType.NOT_NULL)) {
                seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_TEXT_TEXT + row)).sendKeys("*" + value + "*");
            }
        }
        window.closeModalAndWaitGridLoad(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    private void checkFilterAttributeAndOperatorAndValue(String fieldName, FilterOperatorType operator) {
        window.openModal(By.id(UserpageFilter.BUTTON_OPEN + 0L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertRadioPsSelector(FILTER_ROW_ATTRIB_TEXT + 1, FILTER_ROW_ATTRIB_BUTTON + 1, AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE, fieldName, 1L, true);

        assertElement.assertSelect(FILTER_ROW_OPER + 1, operator.getValue());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void checkFilterAttributeAndOperatorAndValue(int row, String fieldName, String fieldName2, String value, String dateType, FilterOperatorType operator, ConfigFieldType fieldDataType) {
        window.openModal(By.id(UserpageFilter.BUTTON_OPEN + 0L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        assertElement.assertRadioPsSelector(FILTER_ROW_ATTRIB_TEXT + row, FILTER_ROW_ATTRIB_BUTTON + row, AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE, fieldName, 1L, true);
        if (dateType != null) {
            assertElement.assertSelect(FILTER_ROW_OPER_TASK + row, dateType);
        }
        assertElement.assertSelect(FILTER_ROW_OPER + row, operator.getValue());
        if (fieldDataType.equals(ConfigFieldType.DROP_DOWN) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.SELECTOR) || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
            if (operator.equals(FilterOperatorType.EQUAL_FIELD) || operator.equals(FilterOperatorType.NOT_EQUAL_FIELD)) {
                assertElement.assertRadioPsSelector(FILTER_ROW_VALUE_FIELD_TEXT + row, FILTER_ROW_VALUE_FIELD_BUTTON + row, AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE, fieldName2, 1L, true);
            } else if (!operator.equals(FilterOperatorType.NULL) && !operator.equals(FilterOperatorType.NOT_NULL)
                    && !operator.equals(FilterOperatorType.NEW) && !operator.equals(FilterOperatorType.NOT_NEW)) {
                if (fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)) {
                    assertElement.assertCheckboxPsSelector(FILTER_ROW_VALUE_TRACKOR_SELECTOR_TEXT + row, FILTER_ROW_VALUE_TRACKOR_SELECTOR_BUTTON + row, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(value), 1L, true);
                } else if (fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR)) {
                    assertElement.assertCheckboxPsSelector(FILTER_ROW_VALUE_MULTI_SELECTOR_TEXT + row, FILTER_ROW_VALUE_MULTI_SELECTOR_BUTTON + row, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(value), 1L, true);
                } else if (fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
                    assertElement.assertRadioPsSelector(FILTER_ROW_VALUE_TRACKOR_DROP_DOWN_TEXT + row, FILTER_ROW_VALUE_TRACKOR_DROP_DOWN_BUTTON + row, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, value, 1L, true);
                } else if (fieldDataType.equals(ConfigFieldType.DROP_DOWN) || fieldDataType.equals(ConfigFieldType.SELECTOR)) {
                    assertElement.assertCheckboxPsSelector(FILTER_ROW_VALUE_SELECTOR_TEXT + row, FILTER_ROW_VALUE_SELECTOR_BUTTON + row, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(value), 1L, true);
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            } else {
                if (fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)) {
                    assertElement.assertText(FILTER_ROW_VALUE_TRACKOR_SELECTOR_TEXT + row, "");
                } else if (fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR)) {
                    assertElement.assertText(FILTER_ROW_VALUE_MULTI_SELECTOR_TEXT + row, "");
                } else if (fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
                    assertElement.assertText(FILTER_ROW_VALUE_TRACKOR_DROP_DOWN_TEXT + row, "");
                } else if (fieldDataType.equals(ConfigFieldType.DROP_DOWN) || fieldDataType.equals(ConfigFieldType.SELECTOR)) {
                    assertElement.assertText(FILTER_ROW_VALUE_SELECTOR_TEXT + row, "");
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        } else if (fieldDataType.equals(ConfigFieldType.CHECKBOX)) {
            assertElement.assertSelect(FILTER_ROW_VALUE_CHECKBOX_TEXT + row, value);
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            if (operator.equals(FilterOperatorType.EQUAL_FIELD) || operator.equals(FilterOperatorType.NOT_EQUAL_FIELD)
                    || operator.equals(FilterOperatorType.MORE_FIELD) || operator.equals(FilterOperatorType.LESS_FIELD)
                    || operator.equals(FilterOperatorType.MORE_AND_EQUAL_FIELD) || operator.equals(FilterOperatorType.LESS_AND_EQUAL_FIELD)) {
                assertElement.assertRadioPsSelector(FILTER_ROW_VALUE_FIELD_TEXT + row, FILTER_ROW_VALUE_FIELD_BUTTON + row, AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE, fieldName2, 1L, true);
            } else if (!operator.equals(FilterOperatorType.NULL) && !operator.equals(FilterOperatorType.NOT_NULL)) {
                    //&& !operator.equals(FilterOperatorType.THIS_WK_TO_DT) && !operator.equals(FilterOperatorType.THIS_MO_TO_DT) //TODO check those lines
                    //&& !operator.equals(FilterOperatorType.THIS_FQ_TO_DT) && !operator.equals(FilterOperatorType.THIS_FY_TO_DT)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    assertElement.assertText(FILTER_ROW_VALUE_DATE_TEXT + row, value);
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    assertElement.assertText(FILTER_ROW_VALUE_DATETIME_TEXT + row, value);
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    assertElement.assertText(FILTER_ROW_VALUE_TIME_TEXT + row, value);
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER)) {
                    assertElement.assertText(FILTER_ROW_VALUE_NUMBER_TEXT + row, value);
                } else if (fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    assertElement.assertText(FILTER_ROW_VALUE_LATLONG_TEXT + row, value);
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            } else {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    assertElement.assertText(FILTER_ROW_VALUE_DATE_TEXT + row, "");
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    assertElement.assertText(FILTER_ROW_VALUE_DATETIME_TEXT + row, "");
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    assertElement.assertText(FILTER_ROW_VALUE_TIME_TEXT + row, "");
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER)) {
                    assertElement.assertText(FILTER_ROW_VALUE_NUMBER_TEXT + row, "");
                } else if (fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    assertElement.assertText(FILTER_ROW_VALUE_LATLONG_TEXT + row, "");
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        } else {
            if (operator.equals(FilterOperatorType.EQUAL_FIELD) || operator.equals(FilterOperatorType.NOT_EQUAL_FIELD)) {
                assertElement.assertRadioPsSelector(FILTER_ROW_VALUE_FIELD_TEXT + row, FILTER_ROW_VALUE_FIELD_BUTTON + row, AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE, fieldName2, 1L, true);
            } else if (!operator.equals(FilterOperatorType.NULL) && !operator.equals(FilterOperatorType.NOT_NULL)) {
                assertElement.assertText(FILTER_ROW_VALUE_TEXT_TEXT + row, "*" + value + "*");
            } else {
                assertElement.assertText(FILTER_ROW_VALUE_TEXT_TEXT + row, "");
            }
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void checkGridTextColumnEquals(Long gridId, Long columnIndex, List<String> values) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        boolean isError = true;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = "";
            }
            gridValue = OnevizionUtils.removeHTMLTags(gridValue);
            for (String value : values) {
                if (gridValue.toLowerCase().contains(value.toLowerCase())) {
                    isError = false;
                }
            }

            if (isError) {
                throw new SeleniumUnexpectedException("Check fails at column [" + columnIndex + "] row [" + i + "]. Cell value in grid [" + gridValue +"]");
            }
        }
    }

    private void checkGridTextColumnEqualsOrNull(Long gridId, Long columnIndex, String value) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = "";
            }
            gridValue = OnevizionUtils.removeHTMLTags(gridValue);
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [%s]", columnIndex, i, gridValue, value);
            if ("".equals(gridValue)) {
                Assert.assertEquals(gridValue, "", failMessage);
            } else {
                Assert.assertEquals(gridValue.toLowerCase().contains(value.toLowerCase()), true, failMessage);
            }
        }
    }

    public void checkGridBooleanColumnEquals(Long gridId, Long columnIndex, List<String> values) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        boolean isError = false;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = "NO";
            }
            gridValue = OnevizionUtils.removeHTMLTags(gridValue);
            for (String value : values) {
                if (!gridValue.equalsIgnoreCase(value)) {
                    isError = true;
                }
            }

            if (isError) {
                throw new SeleniumUnexpectedException("Check fails at column [" + columnIndex + "] row [" + i + "]. Cell value in grid [" + gridValue +"]");
            }
        }
    }

    //it is temporary  solution
    //remove this method
    private void checkGridBooleanColumnEqualsOrNull(Long gridId, Long columnIndex, String value) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue) || "NO".equals(gridValue)) {
                gridValue = "";
            }
            gridValue = OnevizionUtils.removeHTMLTags(gridValue);
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [%s]", columnIndex, i, gridValue, value);
            if ("".equals(gridValue)) {
                Assert.assertEquals(gridValue, "", failMessage);
            } else {
                Assert.assertEquals(gridValue.toLowerCase().contains(value.toLowerCase()), true, failMessage);
            }
        }
    }

    private void checkGridTextColumnNotEquals(Long gridId, Long columnIndex, String value) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = "";
            }
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [%s]", columnIndex, i, gridValue, value);
            Assert.assertEquals(gridValue.toLowerCase().contains(value.toLowerCase()), false, failMessage);
        }
    }

    private void checkGridTextColumnNotEqualsOrNull(Long gridId, Long columnIndex, String value) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = "";
            }
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [%s]", columnIndex, i, gridValue, value);
            if ("".equals(gridValue)) {
                Assert.assertEquals(gridValue, "", failMessage);
            } else {
                Assert.assertEquals(gridValue.toLowerCase().contains(value.toLowerCase()), false, failMessage);
            }
        }
    }

    private void checkGridColumnIsNull(Long gridId, Long columnIndex) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue) || "N/A".equals(gridValue) || "Not Exist".equals(gridValue)) {
                gridValue = "";
            }
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [Is Null]", columnIndex, i, gridValue);
            Assert.assertEquals(gridValue, "", failMessage);
        }
    }

    private void checkGridColumnIsNotNull(Long gridId, Long columnIndex) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = "";
            }
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [Is Not Null]", columnIndex, i, gridValue);
            Assert.assertNotEquals(gridValue, "", failMessage);
        }
    }

    private void checkGridColumnIsNew(Long gridId, String columnLabel, List<String> newTrackors) {
        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel);
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. NewTrackors[%s]. Value in filter [Is New]", columnIndex, i, gridValue, newTrackors);
            Assert.assertTrue(newTrackors.contains(gridValue), failMessage);
        }
    }

    private void checkGridColumnIsNotNew(Long gridId, String columnLabel, List<String> newTrackors) {
        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel);
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. NewTrackors[%s]. Value in filter [Is Not New]", columnIndex, i, gridValue, newTrackors);
            Assert.assertTrue(!newTrackors.contains(gridValue), failMessage);
        }
    }

    private void checkGridColumnEqualsField(Long gridId, Long columnIndex, Long columnIndex2) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);
        @SuppressWarnings("unchecked")
        List<String> vals2 = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex2);

        String failMessage = null;
        String gridValue = null;
        String gridValue2 = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = "";
            }
            gridValue2 = vals2.get(i);
            if ("&nbsp;".equals(gridValue2) || "".equals(gridValue2)) {
                gridValue2 = "";
            }
            failMessage = String.format("Check fails at columns [%s][%s] row [%s]. Cell value in grid column [%s]. Cell value in grid column2 [%s]", columnIndex, columnIndex2, i, gridValue, gridValue2);
            Assert.assertEquals(gridValue.toLowerCase(), gridValue2.toLowerCase(), failMessage);
        }
    }

    private void checkGridColumnNotEqualsField(Long gridId, Long columnIndex, Long columnIndex2) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);
        @SuppressWarnings("unchecked")
        List<String> vals2 = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex2);

        String failMessage = null;
        String gridValue = null;
        String gridValue2 = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = "";
            }
            gridValue2 = vals2.get(i);
            if ("&nbsp;".equals(gridValue2) || "".equals(gridValue2)) {
                gridValue2 = "";
            }
            failMessage = String.format("Check fails at columns [%s][%s] row [%s]. Cell value in grid column [%s]. Cell value in grid column2 [%s]", columnIndex, columnIndex2, i, gridValue, gridValue2);
            Assert.assertNotEquals(gridValue.toLowerCase(), gridValue2.toLowerCase(), failMessage);
        }
    }

    private void checkGridColumnMore(Long gridId, Long columnIndex, String value, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [%s]", columnIndex, i, gridValue, value);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() > OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getDateFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.DATE_TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() > OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() > OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime(), true, failMessage);
            } else {
                Assert.assertEquals(NumberUtils.createDouble(gridValue) > NumberUtils.createDouble(value), true, failMessage);
            }
        }
    }

    private void checkGridColumnLess(Long gridId, Long columnIndex, String value, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [%s]", columnIndex, i, gridValue, value);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() < OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getDateFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.DATE_TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() < OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() < OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime(), true, failMessage);
            } else {
                Assert.assertEquals(NumberUtils.createDouble(gridValue) < NumberUtils.createDouble(value), true, failMessage);
            }
        }
    }

    private void checkGridColumnMoreEquals(Long gridId, Long columnIndex, String value, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [%s]", columnIndex, i, gridValue, value);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getDateFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.DATE_TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() >= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() >= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime(), true, failMessage);
            } else {
                Assert.assertEquals(NumberUtils.createDouble(gridValue) >= NumberUtils.createDouble(value), true, failMessage);
            }
        }
    }

    private void checkGridColumnLessEquals(Long gridId, Long columnIndex, String value, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [%s]", columnIndex, i, gridValue, value);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getDateFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.DATE_TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() <= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() <= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime(), true, failMessage);
            } else {
                Assert.assertEquals(NumberUtils.createDouble(gridValue) <= NumberUtils.createDouble(value), true, failMessage);
            }
        }
    }

    private void checkGridColumnMoreField(Long gridId, Long columnIndex, Long columnIndex2, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);
        @SuppressWarnings("unchecked")
        List<String> vals2 = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex2);

        String failMessage = null;
        String gridValue = null;
        String gridValue2 = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            gridValue2 = vals2.get(i);
            if ("&nbsp;".equals(gridValue2)) {
                gridValue2 = null;
            }
            failMessage = String.format("Check fails at columns [%s][%s] row [%s]. Cell value in grid column [%s]. Cell value in grid column2 [%s]", columnIndex, columnIndex2, i, gridValue, gridValue2);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() > OnevizionUtils.strToDate(gridValue2, seleniumSettings.getUserProperties().getDateFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.DATE_TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() > OnevizionUtils.strToDate(gridValue2, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() > OnevizionUtils.strToDate(gridValue2, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime(), true, failMessage);
            } else {
                Assert.assertEquals(NumberUtils.createDouble(gridValue) > NumberUtils.createDouble(gridValue2), true, failMessage);
            }
        }
    }

    private void checkGridColumnLessField(Long gridId, Long columnIndex, Long columnIndex2, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);
        @SuppressWarnings("unchecked")
        List<String> vals2 = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex2);

        String failMessage = null;
        String gridValue = null;
        String gridValue2 = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            gridValue2 = vals2.get(i);
            if ("&nbsp;".equals(gridValue2) || "".equals(gridValue2)) {
                gridValue2 = null;
            }
            failMessage = String.format("Check fails at columns [%s][%s] row [%s]. Cell value in grid column [%s]. Cell value in grid column2 [%s]", columnIndex, columnIndex2, i, gridValue, gridValue2);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() < OnevizionUtils.strToDate(gridValue2, seleniumSettings.getUserProperties().getDateFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.DATE_TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() < OnevizionUtils.strToDate(gridValue2, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() < OnevizionUtils.strToDate(gridValue2, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime(), true, failMessage);
            } else {
                Assert.assertEquals(NumberUtils.createDouble(gridValue) < NumberUtils.createDouble(gridValue2), true, failMessage);
            }
        }
    }

    private void checkGridColumnMoreEqualsField(Long gridId, Long columnIndex, Long columnIndex2, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);
        @SuppressWarnings("unchecked")
        List<String> vals2 = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex2);

        String failMessage = null;
        String gridValue = null;
        String gridValue2 = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            gridValue2 = vals2.get(i);
            if ("&nbsp;".equals(gridValue2) || "".equals(gridValue2)) {
                gridValue2 = null;
            }
            failMessage = String.format("Check fails at columns [%s][%s] row [%s]. Cell value in grid column [%s]. Cell value in grid column2 [%s]", columnIndex, columnIndex2, i, gridValue, gridValue2);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= OnevizionUtils.strToDate(gridValue2, seleniumSettings.getUserProperties().getDateFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.DATE_TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() >= OnevizionUtils.strToDate(gridValue2, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() >= OnevizionUtils.strToDate(gridValue2, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime(), true, failMessage);
            } else {
                Assert.assertEquals(NumberUtils.createDouble(gridValue) >= NumberUtils.createDouble(gridValue2), true, failMessage);
            }
        }
    }

    private void checkGridColumnLessEqualsField(Long gridId, Long columnIndex, Long columnIndex2, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);
        @SuppressWarnings("unchecked")
        List<String> vals2 = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex2);

        String failMessage = null;
        String gridValue = null;
        String gridValue2 = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            gridValue2 = vals2.get(i);
            if ("&nbsp;".equals(gridValue2) || "".equals(gridValue2)) {
                gridValue2 = null;
            }
            failMessage = String.format("Check fails at columns [%s][%s] row [%s]. Cell value in grid column [%s]. Cell value in grid column2 [%s]", columnIndex, columnIndex2, i, gridValue, gridValue2);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= OnevizionUtils.strToDate(gridValue2, seleniumSettings.getUserProperties().getDateFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.DATE_TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() <= OnevizionUtils.strToDate(gridValue2, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime(), true, failMessage);
            } else if (ConfigFieldType.TIME.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() <= OnevizionUtils.strToDate(gridValue2, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime(), true, failMessage);
            } else {
                Assert.assertEquals(NumberUtils.createDouble(gridValue) <= NumberUtils.createDouble(gridValue2), true, failMessage);
            }
        }
    }

    private void checkGridColumnMoreEqualsToday(Long gridId, Long columnIndex, int days, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            Date thisDay =  getToday(days);

            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [%s] [%s] days", columnIndex, i, gridValue, thisDay, days);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= thisDay.getTime(), true, failMessage);
            }
        }
    }

    private void checkGridColumnLessEqualsToday(Long gridId, Long columnIndex, int days, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            Date thisDay = getToday(days);

            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [%s] [%s] days", columnIndex, i, gridValue, thisDay, days);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals(OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= thisDay.getTime(), true, failMessage);
            }
        }
    }

    private void checkGridColumnWithin(Long gridId, Long columnIndex, int startDays, int endDays, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            Date startDay = getToday(-startDays);
            Date endDay = getToday(endDays);
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter from [%s] to [%s] days", columnIndex, i, gridValue, startDay.toString(), endDay.toString());
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals((OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDay.getTime()) && (OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= endDay.getTime()), true, failMessage);
            }
        }
    }

    private void checkGridColumnThisWk(Long gridId, Long columnIndex, int weeks, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            Date startDayOfWeek = null;
            Date endDayOfWeek = null;
            startDayOfWeek = getFirstDayOfThisWeek(weeks);
            endDayOfWeek = getLastDayOfThisWeek(weeks);

            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter from [%s] to [%s]", columnIndex, i, gridValue, startDayOfWeek, endDayOfWeek);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals((OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfWeek.getTime()) && 
                        (OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= endDayOfWeek.getTime()), true, failMessage);
            }
        }
    }

    private void checkGridColumnThisMo(Long gridId, Long columnIndex, int months, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            Date startDayOfMo = null;
            Date endDayOfMo = null;
            startDayOfMo = getFirstDayOfThisMo(months);
            endDayOfMo = getLastDayOfThisMo(months);

            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter from [%s] to [%s]", columnIndex, i, gridValue, startDayOfMo, endDayOfMo);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals((OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfMo.getTime()) && 
                        (OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= endDayOfMo.getTime()), true, failMessage);
            }
        }
    }

    private void checkGridColumnThisFQ(Long gridId, Long columnIndex, int fq, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            Date startDayOfFQ = null;
            Date endDayOfFQ = null;
            startDayOfFQ = getFirstDayOfThisFQ(fq);
            endDayOfFQ = getLastDayOfThisFQ(fq);

            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter from [%s] to [%s]", columnIndex, i, gridValue, startDayOfFQ, endDayOfFQ);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals((OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfFQ.getTime()) && 
                        (OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= endDayOfFQ.getTime()), true, failMessage);
            }
        }
    }

    private void checkGridColumnThisFY(Long gridId, Long columnIndex, int fy, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            Date startDayOfFY = null;
            Date endDayOfFY = null;
            startDayOfFY = getFirstDayOfThisFY(fy);
            endDayOfFY = getLastDayOfThisFY(fy);

            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter from [%s] to [%s]", columnIndex, i, gridValue, startDayOfFY, endDayOfFY);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals((OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfFY.getTime()) && 
                        (OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= endDayOfFY.getTime()), true, failMessage);
            }
        }
    }

    private void checkGridColumnThisWkToDt(Long gridId, Long columnIndex, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            Date startDayOfWeek = getFirstDayOfThisWeek(0);
            Date today = getToday(0);
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter from [%s] to [%s]", columnIndex, i, gridValue, startDayOfWeek, today);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals((OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfWeek.getTime()) && 
                        (OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= today.getTime()), true, failMessage);
            }
        }
    }

    private void checkGridColumnThisMoToDt(Long gridId, Long columnIndex, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            Date startDayOfMo = getFirstDayOfThisMo(0);
            Date today = getToday(0);
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter from [%s] to [%s]", columnIndex, i, gridValue, startDayOfMo, today);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals((OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfMo.getTime()) && 
                        (OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= today.getTime()), true, failMessage);
            }
        }
    }

    private void checkGridColumnThisFQToDt(Long gridId, Long columnIndex, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            Date startDayOfFQ = getFirstDayOfThisFQ(0);
            Date today = getToday(0);
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter from [%s] to [%s]", columnIndex, i, gridValue, startDayOfFQ, today);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals((OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfFQ.getTime()) && 
                        (OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= today.getTime()), true, failMessage);
            }
        }
    }

    private void checkGridColumnThisFYToDt(Long gridId, Long columnIndex, ConfigFieldType fieldDataType) {
        Long rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        String gridValue = null;
        for (int i = 0; i < rowsCnt; i++) {
            gridValue = vals.get(i);
            if ("&nbsp;".equals(gridValue) || "".equals(gridValue)) {
                gridValue = null;
            }
            Date startDayOfFY = getFirstDayOfThisFY(0);
            Date today = getToday(0);
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter from [%s] to [%s]", columnIndex, i, gridValue, startDayOfFY, today);
            if (ConfigFieldType.DATE.equals(fieldDataType)) {
                Assert.assertEquals((OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfFY.getTime()) && 
                        (OnevizionUtils.strToDate(gridValue, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= today.getTime()), true, failMessage);
            }
        }
    }

    public void checkGridRowsCountEquals(ConfigFieldType fieldDataType, List<String> cellVals, String value) {
        if (fieldDataType.equals(ConfigFieldType.TEXT) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.WIKI) || fieldDataType.equals(ConfigFieldType.SELECTOR)
                || fieldDataType.equals(ConfigFieldType.MEMO) || fieldDataType.equals(ConfigFieldType.ELECTRONIC_FILE)
                || fieldDataType.equals(ConfigFieldType.DB_SELECTOR) || fieldDataType.equals(ConfigFieldType.DB_DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.HYPERLINK) || fieldDataType.equals(ConfigFieldType.DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.CALCULATED) || fieldDataType.equals(ConfigFieldType.ROLLUP)
                || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR) || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
            Long cnt = 0L;
            for (String cellVal : cellVals) {
                if (cellVal.contains(value)) {
                    cnt = cnt + 1L;
                }
            }
            Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
        } else if (fieldDataType.equals(ConfigFieldType.CHECKBOX)) {
            Long cnt = 0L;
            for (String cellVal : cellVals) {
                if ("YES".equalsIgnoreCase(value)) {
                    if (cellVal.equals("YES")) {
                        cnt = cnt + 1L;
                    }
                } else {
                    if (!cellVal.equals("YES")) {
                        cnt = cnt + 1L;
                    }
                }
            }
            Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            Long cnt = 0L;
            for (String cellVal : cellVals) {
                if (cellVal.equals(value)) {
                    cnt = cnt + 1L;
                }
            }
            Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    private void checkGridRowsCountNotEquals(List<String> cellVals, String value) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!cellVal.contains(value)) {
                cnt = cnt + 1L;
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountEqualsOrNull(ConfigFieldType fieldDataType, List<String> cellVals, String value, Long rowsCntBefore, @SuppressWarnings("unchecked") List<String> ... cellValsKeys) {
        if (fieldDataType.equals(ConfigFieldType.TEXT) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.WIKI) || fieldDataType.equals(ConfigFieldType.SELECTOR)
                || fieldDataType.equals(ConfigFieldType.MEMO) || fieldDataType.equals(ConfigFieldType.ELECTRONIC_FILE)
                || fieldDataType.equals(ConfigFieldType.DB_SELECTOR) || fieldDataType.equals(ConfigFieldType.DB_DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.HYPERLINK) || fieldDataType.equals(ConfigFieldType.DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.CALCULATED) || fieldDataType.equals(ConfigFieldType.ROLLUP)
                || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR) || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
            Long cnt = 0L;
            Map<String, List<Long>> equalsKeyMap = new HashMap<>();

            for (int i = 0; i < rowsCntBefore ;i++) {
                StringBuilder bld = new StringBuilder("");
                for (List<String> cellValsKey : cellValsKeys) {
                    bld.append(cellValsKey.get(i));
                }

                if (equalsKeyMap.containsKey(bld.toString())) {
                    equalsKeyMap.get(bld.toString()).add(Long.valueOf(i));
                } else {
                    List<Long> vals = new ArrayList<>();
                    vals.add(Long.valueOf(i));
                    equalsKeyMap.put(bld.toString(), vals);
                }
            }

            for (Entry<String, List<Long>> equalsKey: equalsKeyMap.entrySet()) {
                if (equalsKey.getValue().size() > 1) {
                    boolean isAddRow = false;
                    for (Long value2 : equalsKey.getValue()) {
                        if (cellVals.get(value2.intValue()).contains(value)) {
                            isAddRow = true;
                            cnt = cnt + 1L;
                        }
                    }
                    if (!isAddRow) {
                        cnt = cnt + 1L;
                    }
                } else {
                    cnt = cnt + 1L;
                }
            }

            Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
        } else if (fieldDataType.equals(ConfigFieldType.CHECKBOX)) {
            Long cnt = 0L;
            Map<String, List<Long>> equalsKeyMap = new HashMap<>();

            for (int i = 0; i < rowsCntBefore ;i++) {
                StringBuilder bld = new StringBuilder("");
                for (List<String> cellValsKey : cellValsKeys) {
                    bld.append(cellValsKey.get(i));
                }

                if (equalsKeyMap.containsKey(bld.toString())) {
                    equalsKeyMap.get(bld.toString()).add(Long.valueOf(i));
                } else {
                    List<Long> vals = new ArrayList<>();
                    vals.add(Long.valueOf(i));
                    equalsKeyMap.put(bld.toString(), vals);
                }
            }

            for (Entry<String, List<Long>> equalsKey: equalsKeyMap.entrySet()) {
                if (equalsKey.getValue().size() > 1) {
                    boolean isAddRow = false;
                    for (Long value2 : equalsKey.getValue()) {
                        if (cellVals.get(value2.intValue()).contains(value.toUpperCase())) {
                            isAddRow = true;
                            cnt = cnt + 1L;
                        }
                    }
                    if (!isAddRow) {
                        cnt = cnt + 1L;
                    }
                } else {
                    cnt = cnt + 1L;
                }
            }

            Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            Long cnt = 0L;
            Map<String, List<Long>> equalsKeyMap = new HashMap<>();

            for (int i = 0; i < rowsCntBefore ;i++) {
                StringBuilder bld = new StringBuilder("");
                for (List<String> cellValsKey : cellValsKeys) {
                    bld.append(cellValsKey.get(i));
                }

                if (equalsKeyMap.containsKey(bld.toString())) {
                    equalsKeyMap.get(bld.toString()).add(Long.valueOf(i));
                } else {
                    List<Long> vals = new ArrayList<>();
                    vals.add(Long.valueOf(i));
                    equalsKeyMap.put(bld.toString(), vals);
                }
            }

            for (Entry<String, List<Long>> equalsKey: equalsKeyMap.entrySet()) {
                if (equalsKey.getValue().size() > 1) {
                    boolean isAddRow = false;
                    for (Long value2 : equalsKey.getValue()) {
                        if (cellVals.get(value2.intValue()).equals(value)) {
                            isAddRow = true;
                            cnt = cnt + 1L;
                        }
                    }
                    if (!isAddRow) {
                        cnt = cnt + 1L;
                    }
                } else {
                    cnt = cnt + 1L;
                }
            }

            Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    private void checkGridRowsCountNotEqualsOrNull(ConfigFieldType fieldDataType, List<String> cellVals, String value, Long rowsCntBefore, @SuppressWarnings("unchecked") List<String> ... cellValsKeys) {
        if (fieldDataType.equals(ConfigFieldType.TEXT) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.WIKI) || fieldDataType.equals(ConfigFieldType.SELECTOR)
                || fieldDataType.equals(ConfigFieldType.MEMO) || fieldDataType.equals(ConfigFieldType.ELECTRONIC_FILE)
                || fieldDataType.equals(ConfigFieldType.DB_SELECTOR) || fieldDataType.equals(ConfigFieldType.DB_DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.HYPERLINK) || fieldDataType.equals(ConfigFieldType.DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.CALCULATED) || fieldDataType.equals(ConfigFieldType.ROLLUP)
                || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR) || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
            Long cnt = 0L;
            Map<String, List<Long>> equalsKeyMap = new HashMap<>();

            for (int i = 0; i < rowsCntBefore ;i++) {
                StringBuilder bld = new StringBuilder("");
                for (List<String> cellValsKey : cellValsKeys) {
                    bld.append(cellValsKey.get(i));
                }

                if (equalsKeyMap.containsKey(bld.toString())) {
                    equalsKeyMap.get(bld.toString()).add(Long.valueOf(i));
                } else {
                    List<Long> vals = new ArrayList<>();
                    vals.add(Long.valueOf(i));
                    equalsKeyMap.put(bld.toString(), vals);
                }
            }

            for (Entry<String, List<Long>> equalsKey: equalsKeyMap.entrySet()) {
                if (equalsKey.getValue().size() > 1) {
                    //isNullInAnyRows it is temporary  solution
                    boolean isNullInAnyRows = false;
                    for (Long value2 : equalsKey.getValue()) {
                        if (StringUtils.isEmpty(cellVals.get(value2.intValue()))) {
                            isNullInAnyRows = true;
                        }
                    }

                    if (!isNullInAnyRows) {
                        boolean isAddRow = false;
                        for (Long value2 : equalsKey.getValue()) {
                            if (!cellVals.get(value2.intValue()).contains(value)) {
                                isAddRow = true;
                                cnt = cnt + 1L;
                            }
                        }
                        if (!isAddRow) {
                            cnt = cnt + 1L;
                        }
                    } else {
                        cnt = cnt + 1L;
                    }
                } else {
                    cnt = cnt + 1L;
                }
            }

            Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            Long cnt = 0L;
            Map<String, List<Long>> equalsKeyMap = new HashMap<>();

            for (int i = 0; i < rowsCntBefore ;i++) {
                StringBuilder bld = new StringBuilder("");
                for (List<String> cellValsKey : cellValsKeys) {
                    bld.append(cellValsKey.get(i));
                }

                if (equalsKeyMap.containsKey(bld.toString())) {
                    equalsKeyMap.get(bld.toString()).add(Long.valueOf(i));
                } else {
                    List<Long> vals = new ArrayList<>();
                    vals.add(Long.valueOf(i));
                    equalsKeyMap.put(bld.toString(), vals);
                }
            }

            for (Entry<String, List<Long>> equalsKey: equalsKeyMap.entrySet()) {
                if (equalsKey.getValue().size() > 1) {
                    //isNullInAnyRows it is temporary  solution
                    boolean isNullInAnyRows = false;
                    for (Long value2 : equalsKey.getValue()) {
                        if (StringUtils.isEmpty(cellVals.get(value2.intValue())) || "&nbsp;".equals(cellVals.get(value2.intValue()))) {
                            isNullInAnyRows = true;
                        }
                    }

                    if (!isNullInAnyRows) {
                        boolean isAddRow = false;
                        for (Long value2 : equalsKey.getValue()) {
                            if (!cellVals.get(value2.intValue()).contains(value)) {
                                isAddRow = true;
                                cnt = cnt + 1L;
                            }
                        }
                        if (!isAddRow) {
                            cnt = cnt + 1L;
                        }
                    } else {
                        cnt = cnt + 1L;
                    }
                } else {
                    cnt = cnt + 1L;
                }
            }

            Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    private void checkGridRowsCountIsNull(ConfigFieldType fieldDataType, List<String> cellVals) {
        if (fieldDataType.equals(ConfigFieldType.TEXT) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.WIKI) || fieldDataType.equals(ConfigFieldType.SELECTOR)
                || fieldDataType.equals(ConfigFieldType.MEMO) || fieldDataType.equals(ConfigFieldType.ELECTRONIC_FILE)
                || fieldDataType.equals(ConfigFieldType.DB_SELECTOR) || fieldDataType.equals(ConfigFieldType.DB_DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.HYPERLINK) || fieldDataType.equals(ConfigFieldType.DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.CALCULATED) || fieldDataType.equals(ConfigFieldType.ROLLUP)
                || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR) || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
            Long cnt = 0L;
            for (String cellVal : cellVals) {
                if ("&nbsp;".equals(cellVal) || "".equals(cellVal)) {
                    cnt = cnt + 1L;
                }
            }
            Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            Long cnt = 0L;
            for (String cellVal : cellVals) {
                if ("&nbsp;".equals(cellVal) || "".equals(cellVal) || "Not Exist".equals(cellVal)) {
                    cnt = cnt + 1L;
                }
            }
            Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    private void checkGridRowsCountIsNotNull(ConfigFieldType fieldDataType, List<String> cellVals) {
        if (fieldDataType.equals(ConfigFieldType.TEXT) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
            || fieldDataType.equals(ConfigFieldType.WIKI) || fieldDataType.equals(ConfigFieldType.SELECTOR)
            || fieldDataType.equals(ConfigFieldType.MEMO) || fieldDataType.equals(ConfigFieldType.ELECTRONIC_FILE)
            || fieldDataType.equals(ConfigFieldType.DB_SELECTOR) || fieldDataType.equals(ConfigFieldType.DB_DROP_DOWN)
            || fieldDataType.equals(ConfigFieldType.HYPERLINK) || fieldDataType.equals(ConfigFieldType.DROP_DOWN)
            || fieldDataType.equals(ConfigFieldType.CALCULATED) || fieldDataType.equals(ConfigFieldType.ROLLUP)
            || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR) || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
            Long cnt = 0L;
            for (String cellVal : cellVals) {
                if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal)) {
                    cnt = cnt + 1L;
                }
            }
            Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            Long cnt = 0L;
            for (String cellVal : cellVals) {
                if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                    cnt = cnt + 1L;
                }
            }
            Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    private void checkGridRowsCountIsNew(List<String> cellVals, List<String> newTrackors) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) &&
                    newTrackors.contains(cellVal)) {
                cnt = cnt + 1L;
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountIsNotNew(List<String> cellVals, List<String> newTrackors) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) &&
                    !newTrackors.contains(cellVal)) {
                cnt = cnt + 1L;
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountEqualsField(List<String> cellVals, List<String> cellVals2) {
        Long cnt = 0L;
        for (int i = 0; i < cellVals.size(); i++) {
            if (cellVals.get(i).equals(cellVals2.get(i))) {
                cnt = cnt + 1L;
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountNotEqualsField(List<String> cellVals, List<String> cellVals2) {
        Long cnt = 0L;
        for (int i = 0; i < cellVals.size(); i++) {
            if (!cellVals.get(i).equals(cellVals2.get(i))) {
                cnt = cnt + 1L;
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountMore(ConfigFieldType fieldDataType, List<String> cellVals, String value) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() > OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() > OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() > OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVal).compareTo(NumberUtils.createDouble(value)) > 0) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountLess(ConfigFieldType fieldDataType, List<String> cellVals, String value) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() < OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() < OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() < OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVal).compareTo(NumberUtils.createDouble(value)) < 0) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountMoreEquals(ConfigFieldType fieldDataType, List<String> cellVals, String value) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() >= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() >= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVal).compareTo(NumberUtils.createDouble(value)) >= 0) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountLessEquals(ConfigFieldType fieldDataType, List<String> cellVals, String value) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() <= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() <= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVal).compareTo(NumberUtils.createDouble(value)) <= 0) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountMoreField(ConfigFieldType fieldDataType, List<String> cellVals, List<String> cellVals2) {
        Long cnt = 0L;
        for (int i = 0; i < cellVals.size(); i++) {
            if (!"&nbsp;".equals(cellVals.get(i)) && !"".equals(cellVals.get(i)) && !"&nbsp;".equals(cellVals2.get(i)) && !"".equals(cellVals2.get(i))) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime() > OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() > OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() > OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVals.get(i)).compareTo(NumberUtils.createDouble(cellVals2.get(i))) > 0) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountLessField(ConfigFieldType fieldDataType, List<String> cellVals, List<String> cellVals2) {
        Long cnt = 0L;
        for (int i = 0; i < cellVals.size(); i++) {
            if (!"&nbsp;".equals(cellVals.get(i)) && !"".equals(cellVals.get(i)) && !"&nbsp;".equals(cellVals2.get(i)) && !"".equals(cellVals2.get(i))) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime() < OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() < OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() < OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVals.get(i)).compareTo(NumberUtils.createDouble(cellVals2.get(i))) < 0) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountMoreEqualsField(ConfigFieldType fieldDataType, List<String> cellVals, List<String> cellVals2) {
        Long cnt = 0L;
        for (int i = 0; i < cellVals.size(); i++) {
            if (!"&nbsp;".equals(cellVals.get(i)) && !"".equals(cellVals.get(i)) && !"&nbsp;".equals(cellVals2.get(i)) && !"".equals(cellVals2.get(i))) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime() >= OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() >= OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() >= OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVals.get(i)).compareTo(NumberUtils.createDouble(cellVals2.get(i))) >= 0) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountLessEqualsField(ConfigFieldType fieldDataType, List<String> cellVals, List<String> cellVals2) {
        Long cnt = 0L;
        for (int i = 0; i < cellVals.size(); i++) {
            if (!"&nbsp;".equals(cellVals.get(i)) && !"".equals(cellVals.get(i)) && !"&nbsp;".equals(cellVals2.get(i)) && !"".equals(cellVals2.get(i))) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime() <= OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() <= OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() <= OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1L;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVals.get(i)).compareTo(NumberUtils.createDouble(cellVals2.get(i))) <= 0) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountMoreEqualsToday(ConfigFieldType fieldDataType, List<String> cellVals, @SuppressWarnings("unchecked") List<String> ... cellValsKeys) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    Date thisDay = null;
                    if (" - ".equals(cellValsKeys[0].get(0))) {
                        thisDay =  getToday(-Integer.parseInt(cellValsKeys[0].get(1)));
                    } else if (" + ".equals(cellValsKeys[0].get(0))) {
                        thisDay =  getToday(Integer.parseInt(cellValsKeys[0].get(1)));
                    }
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= thisDay.getTime()) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountLessEqualsToday(ConfigFieldType fieldDataType, List<String> cellVals, @SuppressWarnings("unchecked") List<String> ... cellValsKeys) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    Date thisDay = null;
                    if (" - ".equals(cellValsKeys[0].get(0))) {
                        thisDay =  getToday(-Integer.parseInt(cellValsKeys[0].get(1)));
                    } else if (" + ".equals(cellValsKeys[0].get(0))) {
                        thisDay =  getToday(Integer.parseInt(cellValsKeys[0].get(1)));
                    }
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= thisDay.getTime()) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountWithin(ConfigFieldType fieldDataType, List<String> cellVals, @SuppressWarnings("unchecked") List<String> ... cellValsKeys) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    Date startDay = getToday(-Integer.parseInt(cellValsKeys[0].get(0)));
                    Date endDay = getToday(Integer.parseInt(cellValsKeys[0].get(1)));
                    if ((OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDay.getTime()) && 
                            (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= endDay.getTime())) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountThisWk(ConfigFieldType fieldDataType, List<String> cellVals, @SuppressWarnings("unchecked") List<String> ... cellValsKeys) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    Date startDayOfWeek = null;
                    Date endDayOfWeek = null;
                    if (" - ".equals(cellValsKeys[0].get(0))) {
                        startDayOfWeek = getFirstDayOfThisWeek(-Integer.parseInt(cellValsKeys[0].get(1)));
                        endDayOfWeek = getLastDayOfThisWeek(-Integer.parseInt(cellValsKeys[0].get(1)));
                    } else if (" + ".equals(cellValsKeys[0].get(0))) {
                        startDayOfWeek = getFirstDayOfThisWeek(Integer.parseInt(cellValsKeys[0].get(1)));
                        endDayOfWeek = getLastDayOfThisWeek(Integer.parseInt(cellValsKeys[0].get(1)));
                    }
                    if ((OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfWeek.getTime()) && 
                            (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= endDayOfWeek.getTime())) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountThisMo(ConfigFieldType fieldDataType, List<String> cellVals, @SuppressWarnings("unchecked") List<String> ... cellValsKeys) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    Date startDayOfMo = null;
                    Date endDayOfMo = null;
                    if (" - ".equals(cellValsKeys[0].get(0))) {
                        startDayOfMo = getFirstDayOfThisMo(-Integer.parseInt(cellValsKeys[0].get(1)));
                        endDayOfMo= getLastDayOfThisMo(-Integer.parseInt(cellValsKeys[0].get(1)));
                    } else if (" + ".equals(cellValsKeys[0].get(0))) {
                        startDayOfMo = getFirstDayOfThisMo(Integer.parseInt(cellValsKeys[0].get(1)));
                        endDayOfMo = getLastDayOfThisMo(Integer.parseInt(cellValsKeys[0].get(1)));
                    }
                    if ((OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfMo.getTime()) && 
                            (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= endDayOfMo.getTime())) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountThisFQ(ConfigFieldType fieldDataType, List<String> cellVals, @SuppressWarnings("unchecked") List<String> ... cellValsKeys) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    Date startDayOfFQ = null;
                    Date endDayOfFQ = null;
                    if (" - ".equals(cellValsKeys[0].get(0))) {
                        startDayOfFQ = getFirstDayOfThisFQ(-Integer.parseInt(cellValsKeys[0].get(1)));
                        endDayOfFQ = getLastDayOfThisFQ(-Integer.parseInt(cellValsKeys[0].get(1)));
                    } else if (" + ".equals(cellValsKeys[0].get(0))) {
                        startDayOfFQ = getFirstDayOfThisFQ(Integer.parseInt(cellValsKeys[0].get(1)));
                        endDayOfFQ = getLastDayOfThisFQ(Integer.parseInt(cellValsKeys[0].get(1)));
                    }
                    if ((OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfFQ.getTime()) && 
                            (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= endDayOfFQ.getTime())) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountThisFY(ConfigFieldType fieldDataType, List<String> cellVals, @SuppressWarnings("unchecked") List<String> ... cellValsKeys) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    Date startDayOfFY = null;
                    Date endDayOfFY = null;
                    if (" - ".equals(cellValsKeys[0].get(0))) {
                        startDayOfFY = getFirstDayOfThisFY(-Integer.parseInt(cellValsKeys[0].get(1)));
                        endDayOfFY= getLastDayOfThisFY(-Integer.parseInt(cellValsKeys[0].get(1)));
                    } else if (" + ".equals(cellValsKeys[0].get(0))) {
                        startDayOfFY = getFirstDayOfThisFY(Integer.parseInt(cellValsKeys[0].get(1)));
                        endDayOfFY = getLastDayOfThisFY(Integer.parseInt(cellValsKeys[0].get(1)));
                    }
                    if ((OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfFY.getTime()) && 
                            (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= endDayOfFY.getTime())) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountThisWkToDt(ConfigFieldType fieldDataType, List<String> cellVals) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    Date startDayOfWeek = getFirstDayOfThisWeek(0);
                    Date today = getToday(0);
                    if ((OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfWeek.getTime()) && 
                            (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= today.getTime())) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountThisMoToDt(ConfigFieldType fieldDataType, List<String> cellVals) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    Date startDayOfMo = getFirstDayOfThisMo(0);
                    Date today = getToday(0);
                    if ((OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfMo.getTime()) && 
                            (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= today.getTime())) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountThisFQToDt(ConfigFieldType fieldDataType, List<String> cellVals) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    Date startDayOfFQ = getFirstDayOfThisFQ(0);
                    Date today = getToday(0);
                    if ((OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfFQ.getTime()) && 
                            (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= today.getTime())) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountThisFYToDt(ConfigFieldType fieldDataType, List<String> cellVals) {
        Long cnt = 0L;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    Date startDayOfFY = getFirstDayOfThisFY(0);
                    Date today = getToday(0);
                    if ((OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= startDayOfFY.getTime()) && 
                            (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= today.getTime())) {
                        cnt = cnt + 1L;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
    }

    private Date getToday(int daysCount){
        Calendar cal = Calendar.getInstance(/*TimeZone.getTimeZone("GMT+00:00")*/);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.add(Calendar.DAY_OF_WEEK, daysCount);
        return cal.getTime();
    }

    private Date getFirstDayOfThisWeek(int weeksCount){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.add(Calendar.WEEK_OF_YEAR, weeksCount);
        return cal.getTime();
    }

    private Date getLastDayOfThisWeek(int weeksCount){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.add(Calendar.WEEK_OF_YEAR, weeksCount + 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    private Date getFirstDayOfThisMo(int moCount){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, moCount);
        return cal.getTime();
    }

    private Date getLastDayOfThisMo(int moCount){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, moCount + 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    private Date getFirstDayOfThisFQ(int fqCount){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int quarter = cal.get(Calendar.MONTH) / 3;
        cal.set(Calendar.MONTH, quarter * 3);
        cal.add(Calendar.MONTH, fqCount * 3 + FIRST_MONTH_OF_FISCAL_YEAR - 1);
        return cal.getTime();
    }

    private Date getLastDayOfThisFQ(int fqCount){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int quarter = cal.get(Calendar.MONTH) / 3;
        cal.set(Calendar.MONTH, quarter * 3);
        cal.add(Calendar.MONTH, (fqCount + 1) * 3 + FIRST_MONTH_OF_FISCAL_YEAR - 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    private Date getFirstDayOfThisFY(int fyCount){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, FIRST_MONTH_OF_FISCAL_YEAR - 1);
        cal.add(Calendar.YEAR, fyCount);
        return cal.getTime();
    }

    private Date getLastDayOfThisFY(int fyCount){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, FIRST_MONTH_OF_FISCAL_YEAR - 1);
        cal.add(Calendar.YEAR, fyCount + 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

}
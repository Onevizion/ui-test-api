package com.onevizion.uitest.api.helper.userpage.filter;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.OnevizionUtils;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Selector;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.filter.Filter;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.FilterOperatorType;

@Component
public class UserpageFilter {

    private static final String FILTER_ROW_ATTRIB_TEXT = "txtWPAttrib";
    private static final String FILTER_ROW_ATTRIB_BUTTON = "btnWPAttrib";
    private static final String FILTER_ROW_OPER_TASK = "tdWPOperator";
    private static final String FILTER_ROW_OPER = "WPOperator";
    private static final String FILTER_ROW_VALUE_FIELD_TEXT = "fSelWPAttribValue";
    public static final String FILTER_ROW_VALUE_FIELD_BUTTON = "btnFSelWPAttribValue";
    private static final String FILTER_ROW_VALUE_TRACKOR_SELECTOR_TEXT = "trSelWPAttribValue";
    public static final String FILTER_ROW_VALUE_TRACKOR_SELECTOR_BUTTON = "btnTrSelWPAttribValue";
    private static final String FILTER_ROW_VALUE_MULTI_SELECTOR_TEXT = "multSelWPAttribValue";
    public static final String FILTER_ROW_VALUE_MULTI_SELECTOR_BUTTON = "btnMultSelWPAttribValue";
    private static final String FILTER_ROW_VALUE_TRACKOR_DROP_DOWN_TEXT = "trDropDownWPAttribValue";
    public static final String FILTER_ROW_VALUE_TRACKOR_DROP_DOWN_BUTTON = "btnTrDropDownWPAttribValue";
    private static final String FILTER_ROW_VALUE_SELECTOR_TEXT = "selWPAttribValue";
    public static final String FILTER_ROW_VALUE_SELECTOR_BUTTON = "btnSelWPAttribValue";
    public static final String FILTER_ROW_VALUE_DATE_TEXT = "dateWPAttribValue";
    public static final String FILTER_ROW_VALUE_DATETIME_TEXT = "dateTimeWPAttribValue";
    public static final String FILTER_ROW_VALUE_TIME_TEXT = "timeWPAttribValue";
    public static final String FILTER_ROW_VALUE_NUMBER_TEXT = "numWPAttribValue";
    public static final String FILTER_ROW_VALUE_LATLONG_TEXT = "latlongWPAttribValue";
    public static final String FILTER_ROW_VALUE_CHECKBOX_TEXT = "ynWPAttribValue";
    public static final String FILTER_ROW_VALUE_TEXT_TEXT = "txtWPAttribValue";
    private static final String FILTER_ROW_VALUE_DATE_DIRECTION = "todayDirWPAttribValue";
    private static final String FILTER_ROW_VALUE_DATE_VALUE = "todayValWPAttribValue";
    private static final String FILTER_ROW_VALUE_DATE_BEFORE = "withinBeforeWPAttribValue";
    private static final String FILTER_ROW_VALUE_DATE_AFTER = "withinAfterWPAttribValue";

    @Autowired
    private Js js;

    @Autowired
    private Window window;

    @Autowired
    private Selector selector;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Grid grid;

    @Autowired
    private Filter filter;

    @Autowired
    private SeleniumSettings seleniumSettings;

    public void checkFilterOperators(String fieldName, List<String> dateTypes, List<FilterOperatorType> operators) {
        filter.openFilterForm(AbstractSeleniumCore.getGridIdx());

        selector.selectRadio(By.name(FILTER_ROW_ATTRIB_BUTTON + 1), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1, fieldName, 1L);

        if (dateTypes != null) {
            List<WebElement> options = new Select(seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_OPER_TASK + 1))).getOptions();
            Assert.assertEquals(options.size(), dateTypes.size());
            for (int i = 0; i < dateTypes.size(); i++) {
                Assert.assertEquals(options.get(i).getText(), dateTypes.get(i));
            }
        }

        List<WebElement> options = new Select(seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_OPER + 1))).getOptions();
        options = options.stream()
                .filter(option -> !FilterOperatorType.FIELD_LOCK.getValue().equals(option.getText()))
                .filter(option -> !FilterOperatorType.FIELD_UNLOCK.getValue().equals(option.getText()))
                .filter(option -> !FilterOperatorType.RELATION_LOCK.getValue().equals(option.getText()))
                .filter(option -> !FilterOperatorType.RELATION_UNLOCK.getValue().equals(option.getText()))
                .filter(option -> !FilterOperatorType.EQUAL_COLOR.getValue().equals(option.getText()))
                .filter(option -> !FilterOperatorType.NOT_EQUAL_COLOR.getValue().equals(option.getText()))
                .filter(option -> !FilterOperatorType.COLOR.getValue().equals(option.getText()))
                .filter(option -> !FilterOperatorType.NOT_COLOR.getValue().equals(option.getText()))
                .collect(Collectors.toList());
        Assert.assertEquals(options.size(), operators.size());
        for (int i = 0; i < operators.size(); i++) {
            Assert.assertEquals(options.get(i).getText(), operators.get(i).getValue());
        }

        window.closeModalWithAlert(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE), null);
        filter.closeMainPanel(AbstractSeleniumCore.getGridIdx());
    }

    //we can use fieldName instead of columnIndex.
    //but columnIndex need for check filter by finish task date
    @SuppressWarnings("unchecked")
    public void checkFilter(String fieldName, String value, String dateType,
            FilterOperatorType operator, ConfigFieldType fieldDataType, int columnIndex,
            List<String> cellVals, List<String> ... cellValsKeys) {
        int rowsCntBefore = grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx());

        int randomIndex = fillFilter(fieldName, null, value, dateType, operator, fieldDataType);

        if ((fieldName.equals("FTB:FTB ID") || fieldName.equals("FT:FT ID")
                || fieldName.equals("FTB:Rollup") || fieldName.equals("FT:Rollup")) && operator.equals(FilterOperatorType.NULL)) {
            int rowsCntAfter = grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx());
            if (rowsCntAfter != 0) {
                throw new SeleniumUnexpectedException("Grid have wrong rows count. Should be 0 rows.");
            }
        } else {
            int rowsCntAfter = grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx());
            if (rowsCntAfter == 0) {
                throw new SeleniumUnexpectedException("Grid have wrong rows count. Should be more than 0 rows.");
            }
        }

        if (fieldDataType.equals(ConfigFieldType.TEXT) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.WIKI) || fieldDataType.equals(ConfigFieldType.SELECTOR)
                || fieldDataType.equals(ConfigFieldType.MEMO) || fieldDataType.equals(ConfigFieldType.ELECTRONIC_FILE)
                || fieldDataType.equals(ConfigFieldType.DB_SELECTOR) || fieldDataType.equals(ConfigFieldType.DB_DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.HYPERLINK) || fieldDataType.equals(ConfigFieldType.DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.CALCULATED) || fieldDataType.equals(ConfigFieldType.ROLLUP)
                || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR) || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.MULTI_TRACKOR_SELECTOR)) {
            if (operator.equals(FilterOperatorType.EQUAL)) {
                checkGridRowsCountEquals(fieldDataType, cellVals, value);
                checkGridTextColumnEquals(AbstractSeleniumCore.getGridIdx(), columnIndex, Arrays.asList(value));
            } else if (operator.equals(FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER)) {
                checkGridRowsCountEqualsOrNull(fieldDataType, cellVals, value, rowsCntBefore, cellValsKeys);
                checkGridTextColumnEqualsOrNull(AbstractSeleniumCore.getGridIdx(), columnIndex, value);
            } else if (operator.equals(FilterOperatorType.NOT_EQUAL)) {
                checkGridRowsCountNotEquals(cellVals, value);
                checkGridTextColumnNotEquals(AbstractSeleniumCore.getGridIdx(), columnIndex, value);
            } else if (operator.equals(FilterOperatorType.NOT_EQUAL_AND_EMPTY_FOR_OTHER)) {
                checkGridRowsCountNotEqualsOrNull(fieldDataType, cellVals, value, rowsCntBefore, cellValsKeys);
                checkGridTextColumnNotEqualsOrNull(AbstractSeleniumCore.getGridIdx(), columnIndex, value);
            } else if (operator.equals(FilterOperatorType.NULL)) {
                checkGridRowsCountIsNull(fieldDataType, cellVals);
                checkGridColumnIsNull(AbstractSeleniumCore.getGridIdx(), columnIndex);
            } else if (operator.equals(FilterOperatorType.NOT_NULL)) {
                checkGridRowsCountIsNotNull(fieldDataType, cellVals);
                checkGridColumnIsNotNull(AbstractSeleniumCore.getGridIdx(), columnIndex);
            } else {
                throw new SeleniumUnexpectedException("Not support operation");
            }
        } else if (fieldDataType.equals(ConfigFieldType.CHECKBOX)) {
            if (operator.equals(FilterOperatorType.EQUAL)) {
                checkGridRowsCountEquals(fieldDataType, cellVals, value);
                checkGridBooleanColumnEquals(AbstractSeleniumCore.getGridIdx(), columnIndex, Arrays.asList(value.toUpperCase()));
            } else if (operator.equals(FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER)) {
                checkGridRowsCountEqualsOrNull(fieldDataType, cellVals, value, rowsCntBefore, cellValsKeys);
                checkGridBooleanColumnEqualsOrNull(AbstractSeleniumCore.getGridIdx(), columnIndex, value.toUpperCase());
            } else {
                throw new SeleniumUnexpectedException("Not support operation");
            }
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            if (operator.equals(FilterOperatorType.EQUAL)) {
                checkGridRowsCountEquals(fieldDataType, cellVals, value);
                checkGridTextColumnEquals(AbstractSeleniumCore.getGridIdx(), columnIndex, Arrays.asList(value));
            } else if (operator.equals(FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER)) {
                checkGridRowsCountEqualsOrNull(fieldDataType, cellVals, value, rowsCntBefore, cellValsKeys);
                checkGridTextColumnEqualsOrNull(AbstractSeleniumCore.getGridIdx(), columnIndex, value);
            } else if (operator.equals(FilterOperatorType.MORE)) {
                checkGridRowsCountMore(fieldDataType, cellVals, value);
                checkGridColumnMore(AbstractSeleniumCore.getGridIdx(), columnIndex, value, fieldDataType);
            } else if (operator.equals(FilterOperatorType.LESS)) {
                checkGridRowsCountLess(fieldDataType, cellVals, value);
                checkGridColumnLess(AbstractSeleniumCore.getGridIdx(), columnIndex, value, fieldDataType);
            } else if (operator.equals(FilterOperatorType.MORE_AND_EQUAL)) {
                checkGridRowsCountMoreEquals(fieldDataType, cellVals, value);
                checkGridColumnMoreEquals(AbstractSeleniumCore.getGridIdx(), columnIndex, value, fieldDataType);
            } else if (operator.equals(FilterOperatorType.LESS_AND_EQUAL)) {
                checkGridRowsCountLessEquals(fieldDataType, cellVals, value);
                checkGridColumnLessEquals(AbstractSeleniumCore.getGridIdx(), columnIndex, value, fieldDataType);
            } else if (operator.equals(FilterOperatorType.NOT_EQUAL)) {
                checkGridRowsCountNotEquals(cellVals, value);
                checkGridTextColumnNotEquals(AbstractSeleniumCore.getGridIdx(), columnIndex, value);
            } else if (operator.equals(FilterOperatorType.NOT_EQUAL_AND_EMPTY_FOR_OTHER)) {
                checkGridRowsCountNotEqualsOrNull(fieldDataType, cellVals, value, rowsCntBefore, cellValsKeys);
                checkGridTextColumnNotEqualsOrNull(AbstractSeleniumCore.getGridIdx(), columnIndex, value);
            } else if (operator.equals(FilterOperatorType.NULL)) {
                checkGridRowsCountIsNull(fieldDataType, cellVals);
                checkGridColumnIsNull(AbstractSeleniumCore.getGridIdx(), columnIndex);
            } else if (operator.equals(FilterOperatorType.NOT_NULL)) {
                checkGridRowsCountIsNotNull(fieldDataType, cellVals);
                checkGridColumnIsNotNull(AbstractSeleniumCore.getGridIdx(), columnIndex);
            } else {
                throw new SeleniumUnexpectedException("Not support operation");
            }
        } else {
            throw new SeleniumUnexpectedException("Not support field data type");
        }

        checkAndClearFilter(fieldName, null, value, dateType, operator, fieldDataType, randomIndex);
    }

    //we can use fieldName instead of columnIndex.
    //but columnIndex need for check filter by finish task date
    public void checkFilterWithFieldOperation(String fieldName, String fieldName2, String value, String dateType,
            FilterOperatorType operator, ConfigFieldType fieldDataType, int columnIndex, int columnIndex2,
            List<String> cellVals, List<String> cellVals2) {
        int randomIndex = fillFilter(fieldName, fieldName2, value, dateType, operator, fieldDataType);

        int rowsCntAfter = grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx());
        if (rowsCntAfter == 0) {
            throw new SeleniumUnexpectedException("Grid have wrong rows count. Should be more than 0 rows.");
        }

        if (fieldDataType.equals(ConfigFieldType.TEXT) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.SELECTOR) || fieldDataType.equals(ConfigFieldType.DROP_DOWN)) {
            if (operator.equals(FilterOperatorType.EQUAL_FIELD)) {
                checkGridRowsCountEqualsField(cellVals, cellVals2);
                checkGridColumnEqualsField(AbstractSeleniumCore.getGridIdx(), columnIndex, columnIndex2);
            } else if (operator.equals(FilterOperatorType.NOT_EQUAL_FIELD)) {
                checkGridRowsCountNotEqualsField(cellVals, cellVals2);
                checkGridColumnNotEqualsField(AbstractSeleniumCore.getGridIdx(), columnIndex, columnIndex2);
            } else {
                throw new SeleniumUnexpectedException("Not support operation");
            }
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            if (operator.equals(FilterOperatorType.EQUAL_FIELD)) {
                checkGridRowsCountEqualsField(cellVals, cellVals2);
                checkGridColumnEqualsField(AbstractSeleniumCore.getGridIdx(), columnIndex, columnIndex2);
            } else if (operator.equals(FilterOperatorType.NOT_EQUAL_FIELD)) {
                checkGridRowsCountNotEqualsField(cellVals, cellVals2);
                checkGridColumnNotEqualsField(AbstractSeleniumCore.getGridIdx(), columnIndex, columnIndex2);
            } else if (operator.equals(FilterOperatorType.MORE_FIELD)) {
                checkGridRowsCountMoreField(fieldDataType, cellVals, cellVals2);
                checkGridColumnMoreField(AbstractSeleniumCore.getGridIdx(), columnIndex, columnIndex2, fieldDataType);
            } else if (operator.equals(FilterOperatorType.LESS_FIELD)) {
                checkGridRowsCountLessField(fieldDataType, cellVals, cellVals2);
                checkGridColumnLessField(AbstractSeleniumCore.getGridIdx(), columnIndex, columnIndex2, fieldDataType);
            } else if (operator.equals(FilterOperatorType.MORE_AND_EQUAL_FIELD)) {
                checkGridRowsCountMoreEqualsField(fieldDataType, cellVals, cellVals2);
                checkGridColumnMoreEqualsField(AbstractSeleniumCore.getGridIdx(), columnIndex, columnIndex2, fieldDataType);
            } else if (operator.equals(FilterOperatorType.LESS_AND_EQUAL_FIELD)) {
                checkGridRowsCountLessEqualsField(fieldDataType, cellVals, cellVals2);
                checkGridColumnLessEqualsField(AbstractSeleniumCore.getGridIdx(), columnIndex, columnIndex2, fieldDataType);
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
        checkGridColumnIsNew(AbstractSeleniumCore.getGridIdx(), fieldName, newTrackors);

        checkAndClearFilter(fieldName, FilterOperatorType.NEW, randomIndex);
    }

    public void checkFilterMultiTrackorSelectorIsNew(String fieldName, List<String> cellVals, List<String> newTrackors) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.NEW);

        checkGridRowsCountMultiTrackorSelectorIsNew(cellVals, newTrackors);
        checkGridColumnMultiTrackorSelectorIsNew(AbstractSeleniumCore.getGridIdx(), fieldName, newTrackors);

        checkAndClearFilter(fieldName, FilterOperatorType.NEW, randomIndex);
    }

    public void checkFilterIsNotNew(String fieldName, List<String> cellVals, List<String> newTrackors) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.NOT_NEW);

        checkGridRowsCountIsNotNew(cellVals, newTrackors);
        checkGridColumnIsNotNew(AbstractSeleniumCore.getGridIdx(), fieldName, newTrackors);

        checkAndClearFilter(fieldName, FilterOperatorType.NOT_NEW, randomIndex);
    }

    public void checkFilterMultiTrackorSelectorIsNotNew(String fieldName, List<String> cellVals, List<String> oldTrackors) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.NOT_NEW);

        checkGridRowsCountMultiTrackorSelectorIsNotNew(cellVals, oldTrackors);
        checkGridColumnMultiTrackorSelectorIsNotNew(AbstractSeleniumCore.getGridIdx(), fieldName, oldTrackors);

        checkAndClearFilter(fieldName, FilterOperatorType.NOT_NEW, randomIndex);
    }

    public void checkFilterIsFieldLocked(String fieldName, String trackorFieldName, List<String> trackorCellVals, List<String> trackors) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.FIELD_LOCK);

        checkGridRowsCountIsFieldLocked(trackorCellVals, trackors);
        checkGridColumnIsNew(AbstractSeleniumCore.getGridIdx(), trackorFieldName, trackors);

        checkAndClearFilter(fieldName, FilterOperatorType.FIELD_LOCK, randomIndex);
    }

    public void checkFilterIsFieldUnlocked(String fieldName, String trackorFieldName, List<String> trackorCellVals, List<String> trackors) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.FIELD_UNLOCK);

        checkGridRowsCountIsFieldUnlocked(trackorCellVals, trackors);
        checkGridColumnIsNotNew(AbstractSeleniumCore.getGridIdx(), trackorFieldName, trackors);

        checkAndClearFilter(fieldName, FilterOperatorType.FIELD_UNLOCK, randomIndex);
    }

    public void checkFilterIsRelationLocked(String fieldName, String trackorFieldName, List<String> trackorCellVals, List<String> trackors) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.RELATION_LOCK);

        checkGridRowsCountIsFieldLocked(trackorCellVals, trackors);
        checkGridColumnIsNew(AbstractSeleniumCore.getGridIdx(), trackorFieldName, trackors);

        checkAndClearFilter(fieldName, FilterOperatorType.RELATION_LOCK, randomIndex);
    }

    public void checkFilterIsRelationUnlocked(String fieldName, String trackorFieldName, List<String> trackorCellVals, List<String> trackors) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.RELATION_UNLOCK);

        checkGridRowsCountIsFieldUnlocked(trackorCellVals, trackors);
        checkGridColumnIsNotNew(AbstractSeleniumCore.getGridIdx(), trackorFieldName, trackors);

        checkAndClearFilter(fieldName, FilterOperatorType.RELATION_UNLOCK, randomIndex);
    }

    public void checkFilterIsFieldColor(String fieldName, String trackorFieldName, List<String> trackorCellVals, Map<String, List<String>> colors) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.COLOR);

        List<String> trackors = new ArrayList<>();
        for (List<String> color : colors.values()) {
            trackors.addAll(color);
        }

        checkGridRowsCountIsFieldLocked(trackorCellVals, trackors);
        checkGridColumnIsNew(AbstractSeleniumCore.getGridIdx(), trackorFieldName, trackors);

        checkAndClearFilter(fieldName, FilterOperatorType.COLOR, randomIndex);
    }

    public void checkFilterIsFieldNotColor(String fieldName, String trackorFieldName, List<String> trackorCellVals, Map<String, List<String>> colors) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.NOT_COLOR);

        List<String> trackors = new ArrayList<>();
        for (List<String> color : colors.values()) {
            trackors.addAll(color);
        }

        checkGridRowsCountIsFieldUnlocked(trackorCellVals, trackors);
        checkGridColumnIsNotNew(AbstractSeleniumCore.getGridIdx(), trackorFieldName, trackors);

        checkAndClearFilter(fieldName, FilterOperatorType.NOT_COLOR, randomIndex);
    }

    public void checkFilterEqualColor(String fieldName, String trackorFieldName, String value, List<String> trackorCellVals, Map<String, List<String>> colors) {
        int randomIndex = fillFilter(fieldName, null, value, null, FilterOperatorType.EQUAL_COLOR, ConfigFieldType.DROP_DOWN);

        checkGridRowsCountIsFieldLocked(trackorCellVals, colors.get(value));
        checkGridColumnIsNew(AbstractSeleniumCore.getGridIdx(), trackorFieldName, colors.get(value));

        checkAndClearFilter(fieldName, null, value, null, FilterOperatorType.EQUAL_COLOR, ConfigFieldType.DROP_DOWN, randomIndex);
    }

    public void checkFilterNotEqualColor(String fieldName, String trackorFieldName, String value, List<String> trackorCellVals, Map<String, List<String>> colors) {
        int randomIndex = fillFilter(fieldName, null, value, null, FilterOperatorType.NOT_EQUAL_COLOR, ConfigFieldType.DROP_DOWN);

        checkGridRowsCountIsFieldUnlocked(trackorCellVals, colors.get(value));
        checkGridColumnIsNotNew(AbstractSeleniumCore.getGridIdx(), trackorFieldName, colors.get(value));

        checkAndClearFilter(fieldName, null, value, null, FilterOperatorType.NOT_EQUAL_COLOR, ConfigFieldType.DROP_DOWN, randomIndex);
    }

    public void checkFilterHasComments(String fieldName, int expectedRowsCnt) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.HAS_COMMENTS);

        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), expectedRowsCnt, "Grid have wrong rows count");

        checkAndClearFilter(fieldName, FilterOperatorType.HAS_COMMENTS, randomIndex);
    }

    public void checkFilterEqualMyselfWithoutTrackor(String fieldName, int expectedRowsCnt) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.EQUAL_MYSELF);

        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), expectedRowsCnt, "Grid have wrong rows count");

        checkAndClearFilter(fieldName, FilterOperatorType.EQUAL_MYSELF, randomIndex);
    }

    public void checkFilterNotEqualMyselfWithoutTrackor(String fieldName, int expectedRowsCnt) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.NOT_EQUAL_MYSELF);

        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), expectedRowsCnt, "Grid have wrong rows count");

        checkAndClearFilter(fieldName, FilterOperatorType.NOT_EQUAL_MYSELF, randomIndex);
    }

    public void checkFilterEqualMyselfWithTrackor(String fieldName, String trackorFieldName, List<String> trackorCellVals, List<String> trackors) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.EQUAL_MYSELF);

        checkGridRowsCountIsFieldLocked(trackorCellVals, trackors);
        checkGridColumnIsNew(AbstractSeleniumCore.getGridIdx(), trackorFieldName, trackors);

        checkAndClearFilter(fieldName, FilterOperatorType.EQUAL_MYSELF, randomIndex);
    }

    public void checkFilterNotEqualMyselfWithTrackor(String fieldName, String trackorFieldName, List<String> trackorCellVals, List<String> trackors) {
        int randomIndex = fillFilter(fieldName, FilterOperatorType.NOT_EQUAL_MYSELF);

        checkGridRowsCountIsFieldUnlocked(trackorCellVals, trackors);
        checkGridColumnIsNotNew(AbstractSeleniumCore.getGridIdx(), trackorFieldName, trackors);

        checkAndClearFilter(fieldName, FilterOperatorType.NOT_EQUAL_MYSELF, randomIndex);
    }

    @SuppressWarnings("unchecked")
    public void checkFilterTrackorSelectorByText(String fieldName, FilterOperatorType operator, String value, int columnIndex, List<String> cellVals, List<String> ... cellValsKeys) {
        int rowsCntBefore = grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx());

        int randomIndex = fillFilter(fieldName, operator, value);

        if (operator.equals(FilterOperatorType.EQUAL)) {
            checkGridRowsCountEquals(ConfigFieldType.TRACKOR_SELECTOR, cellVals, value);
            checkGridTextColumnEquals(AbstractSeleniumCore.getGridIdx(), columnIndex, Arrays.asList(value));
        } else if (operator.equals(FilterOperatorType.NOT_EQUAL)) {
            checkGridRowsCountNotEquals(cellVals, value);
            checkGridTextColumnNotEquals(AbstractSeleniumCore.getGridIdx(), columnIndex, value);
        } else if (operator.equals(FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER)) {
            checkGridRowsCountEqualsOrNull(ConfigFieldType.TRACKOR_SELECTOR, cellVals, value, rowsCntBefore, cellValsKeys);
            checkGridTextColumnEqualsOrNull(AbstractSeleniumCore.getGridIdx(), columnIndex, value);
        } else if (operator.equals(FilterOperatorType.NOT_EQUAL_AND_EMPTY_FOR_OTHER)) {
            checkGridRowsCountNotEqualsOrNull(ConfigFieldType.TRACKOR_SELECTOR, cellVals, value, rowsCntBefore, cellValsKeys);
            checkGridTextColumnNotEqualsOrNull(AbstractSeleniumCore.getGridIdx(), columnIndex, value);
        } else {
            throw new SeleniumUnexpectedException("Not support operation");
        }

        checkAndClearFilter(fieldName, operator, value, randomIndex);
    }

    public void checkFilterMultiTrackorSelectorByText(String fieldName, FilterOperatorType operator, String value, int columnIndex, List<String> cellVals) {
        int randomIndex = fillFilter(fieldName, operator, value);

        if (operator.equals(FilterOperatorType.EQUAL)) {
            checkGridRowsCountEquals(ConfigFieldType.MULTI_TRACKOR_SELECTOR, cellVals, value);
            checkGridTextColumnEquals(AbstractSeleniumCore.getGridIdx(), columnIndex, Arrays.asList(value));
        } else if (operator.equals(FilterOperatorType.NOT_EQUAL)) {
            checkGridRowsCountNotEquals(cellVals, value);
            checkGridTextColumnNotEquals(AbstractSeleniumCore.getGridIdx(), columnIndex, value);
        } else {
            throw new SeleniumUnexpectedException("Not support operation");
        }

        checkAndClearFilter(fieldName, operator, value, randomIndex);
    }

    public void checkFilterMoreAndEqualToday(String fieldName, String value, String dateType, ConfigFieldType fieldDataType) {
        int randomIndex = fillFilter(fieldName, null, value, dateType, FilterOperatorType.MORE_AND_EQUAL_TODAY, fieldDataType);

        //TODO

        checkAndClearFilter(fieldName, null, value, dateType, FilterOperatorType.MORE_AND_EQUAL_TODAY, fieldDataType, randomIndex);
    }

    public void checkFilterLessAndEqualToday(String fieldName, String value, String dateType, ConfigFieldType fieldDataType) {
        int randomIndex = fillFilter(fieldName, null, value, dateType, FilterOperatorType.LESS_AND_EQUAL_TODAY, fieldDataType);

        //TODO

        checkAndClearFilter(fieldName, null, value, dateType, FilterOperatorType.LESS_AND_EQUAL_TODAY, fieldDataType, randomIndex);
    }

    public void checkFilterWithin(String fieldName, String value, String dateType, ConfigFieldType fieldDataType) {
        int randomIndex = fillFilter(fieldName, null, value, dateType, FilterOperatorType.WITHIN, fieldDataType);

        //TODO

        checkAndClearFilter(fieldName, null, value, dateType, FilterOperatorType.WITHIN, fieldDataType, randomIndex);
    }

    public void checkFilterThisWeek(String fieldName, String value, String dateType, ConfigFieldType fieldDataType) {
        int randomIndex = fillFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_WK, fieldDataType);

        //TODO

        checkAndClearFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_WK, fieldDataType, randomIndex);
    }

    public void checkFilterThisWeekToDate(String fieldName, String value, String dateType, ConfigFieldType fieldDataType) {
        int randomIndex = fillFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_WK_TO_DT, fieldDataType);

        //TODO

        checkAndClearFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_WK_TO_DT, fieldDataType, randomIndex);
    }

    public void checkFilterThisMonth(String fieldName, String value, String dateType, ConfigFieldType fieldDataType) {
        int randomIndex = fillFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_MO, fieldDataType);

        //TODO

        checkAndClearFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_MO, fieldDataType, randomIndex);
    }

    public void checkFilterThisMonthToDate(String fieldName, String value, String dateType, ConfigFieldType fieldDataType) {
        int randomIndex = fillFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_MO_TO_DT, fieldDataType);

        //TODO

        checkAndClearFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_MO_TO_DT, fieldDataType, randomIndex);
    }

    public void checkFilterThisQuarter(String fieldName, String value, String dateType, ConfigFieldType fieldDataType) {
        int randomIndex = fillFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_FQ, fieldDataType);

        //TODO

        checkAndClearFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_FQ, fieldDataType, randomIndex);
    }

    public void checkFilterThisQuarterToDate(String fieldName, String value, String dateType, ConfigFieldType fieldDataType) {
        int randomIndex = fillFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_FQ_TO_DT, fieldDataType);

        //TODO

        checkAndClearFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_FQ_TO_DT, fieldDataType, randomIndex);
    }

    public void checkFilterThisYear(String fieldName, String value, String dateType, ConfigFieldType fieldDataType) {
        int randomIndex = fillFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_FY, fieldDataType);

        //TODO

        checkAndClearFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_FY, fieldDataType, randomIndex);
    }

    public void checkFilterThisYearToDate(String fieldName, String value, String dateType, ConfigFieldType fieldDataType) {
        int randomIndex = fillFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_FY_TO_DT, fieldDataType);

        //TODO

        checkAndClearFilter(fieldName, null, value, dateType, FilterOperatorType.THIS_FY_TO_DT, fieldDataType, randomIndex);
    }

    private int fillFilter(String fieldName, FilterOperatorType operator) {
        SecureRandom generator = new SecureRandom();
        int randomIndex = generator.nextInt(2);

        if (randomIndex == 1) {
            filter.openFilterForm(AbstractSeleniumCore.getGridIdx());
            selectFilterAttributeAndOperatorAndValue(1, fieldName, operator);
            filter.closeFilterFormOk(AbstractSeleniumCore.getGridIdx());
        } else if (randomIndex == 0) {
            filter.selectByVisibleText("G:" + fieldName + " " + operator.getValue(), AbstractSeleniumCore.getGridIdx());
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }

        return randomIndex;
    }

    private int fillFilter(String fieldName, FilterOperatorType operator, String value) {
        SecureRandom generator = new SecureRandom();
        int randomIndex = generator.nextInt(2);

        if (randomIndex == 1) {
            filter.openFilterForm(AbstractSeleniumCore.getGridIdx());
            selectFilterAttributeAndOperatorAndValue(1, fieldName, operator, value);
            filter.closeFilterFormOk(AbstractSeleniumCore.getGridIdx());
        } else if (randomIndex == 0) {
            filter.selectByVisibleText("G:" + fieldName + " " + "ByText " + operator.getValue(), AbstractSeleniumCore.getGridIdx());
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }

        return randomIndex;
    }

    private int fillFilter(String fieldName, String fieldName2, String value, String dateType, FilterOperatorType operator, ConfigFieldType fieldDataType) {
        SecureRandom generator = new SecureRandom();
        int randomIndex = generator.nextInt(2);

        if (randomIndex == 1) {
            filter.openFilterForm(AbstractSeleniumCore.getGridIdx());
            selectFilterAttributeAndOperatorAndValue(1, fieldName, fieldName2, value, dateType, operator, fieldDataType);
            filter.closeFilterFormOk(AbstractSeleniumCore.getGridIdx());
        } else if (randomIndex == 0) {
            if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
                filter.selectByVisibleText("G:" + fieldName + " " + operator.getValue() + " " + value, AbstractSeleniumCore.getGridIdx());
            } else if (dateType != null) {
                filter.selectByVisibleText("G:" + fieldName + " " + operator.getValue() + " " + dateType, AbstractSeleniumCore.getGridIdx());
            } else {
                filter.selectByVisibleText("G:" + fieldName + " " + operator.getValue(), AbstractSeleniumCore.getGridIdx());
            }
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }

        return randomIndex;
    }

    private void checkAndClearFilter(String fieldName, FilterOperatorType operator, int randomIndex) {
        filter.openFilterForm(AbstractSeleniumCore.getGridIdx());
        checkFilterAttributeAndOperatorAndValue(1, fieldName, operator);
        filter.closeFilterFormCancel(AbstractSeleniumCore.getGridIdx());

        if (randomIndex == 1) {
            filter.clearFilter(AbstractSeleniumCore.getGridIdx());
        } else if (randomIndex == 0) {
            filter.selectByVisibleText("Unsaved Filter", AbstractSeleniumCore.getGridIdx());
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }
    }

    private void checkAndClearFilter(String fieldName, FilterOperatorType operator, String value, int randomIndex) {
        filter.openFilterForm(AbstractSeleniumCore.getGridIdx());
        checkFilterAttributeAndOperatorAndValue(1, fieldName, operator, value);
        filter.closeFilterFormCancel(AbstractSeleniumCore.getGridIdx());

        if (randomIndex == 1) {
            filter.clearFilter(AbstractSeleniumCore.getGridIdx());
        } else if (randomIndex == 0) {
            filter.selectByVisibleText("Unsaved Filter", AbstractSeleniumCore.getGridIdx());
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }
    }

    private void checkAndClearFilter(String fieldName, String fieldName2, String value, String dateType, FilterOperatorType operator, ConfigFieldType fieldDataType, int randomIndex) {
        filter.openFilterForm(AbstractSeleniumCore.getGridIdx());
        checkFilterAttributeAndOperatorAndValue(1, fieldName, fieldName2, value, dateType, operator, fieldDataType);
        filter.closeFilterFormCancel(AbstractSeleniumCore.getGridIdx());

        if (randomIndex == 1) {
            filter.clearFilter(AbstractSeleniumCore.getGridIdx());
        } else if (randomIndex == 0) {
            filter.selectByVisibleText("Unsaved Filter", AbstractSeleniumCore.getGridIdx());
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }
    }

    public void selectFilterAttributeAndOperatorAndValue(int row, String fieldName, FilterOperatorType operator) {
        selector.selectRadio(By.name(FILTER_ROW_ATTRIB_BUTTON + row), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1, fieldName, 1L);

        new Select(seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_OPER + row))).selectByVisibleText(operator.getValue());
    }

    private void selectFilterAttributeAndOperatorAndValue(int row, String fieldName, FilterOperatorType operator, String value) {
        selector.selectRadio(By.name(FILTER_ROW_ATTRIB_BUTTON + row), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1, fieldName, 1L);

        new Select(seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_OPER + row))).selectByVisibleText(operator.getValue());

        seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_TRACKOR_SELECTOR_TEXT + row)).sendKeys(value);
    }

    public void selectFilterAttributeAndOperatorAndValue(int row, String fieldName, String fieldName2, String value, String dateType, FilterOperatorType operator, ConfigFieldType fieldDataType) {
        selector.selectRadio(By.name(FILTER_ROW_ATTRIB_BUTTON + row), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1, fieldName, 1L);

        if (dateType != null) {
            new Select(seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_OPER_TASK + row))).selectByVisibleText(dateType);
        }

        new Select(seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_OPER + row))).selectByVisibleText(operator.getValue());

        if (fieldDataType.equals(ConfigFieldType.DROP_DOWN) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.SELECTOR) || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN) || fieldDataType.equals(ConfigFieldType.MULTI_TRACKOR_SELECTOR)) {
            if (operator.equals(FilterOperatorType.EQUAL_FIELD) || operator.equals(FilterOperatorType.NOT_EQUAL_FIELD)) {
                selector.selectRadio(By.name(FILTER_ROW_VALUE_FIELD_BUTTON + row), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1, fieldName2, 1L);
            } else if (!operator.equals(FilterOperatorType.NULL) && !operator.equals(FilterOperatorType.NOT_NULL)
                    && !operator.equals(FilterOperatorType.NEW) && !operator.equals(FilterOperatorType.NOT_NEW)) {
                if (fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR) || fieldDataType.equals(ConfigFieldType.MULTI_TRACKOR_SELECTOR)) {
                    selector.selectCheckbox(By.name(FILTER_ROW_VALUE_TRACKOR_SELECTOR_BUTTON + row), 0, Arrays.asList(value), 1L);
                } else if (fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR)) {
                    selector.selectCheckbox(By.name(FILTER_ROW_VALUE_MULTI_SELECTOR_BUTTON + row), 0, Arrays.asList(value), 1L);
                } else if (fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
                    selector.selectRadio(By.name(FILTER_ROW_VALUE_TRACKOR_DROP_DOWN_BUTTON + row), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 0, value, 1L);
                } else {
                    selector.selectCheckbox(By.name(FILTER_ROW_VALUE_SELECTOR_BUTTON + row), 0, Arrays.asList(value), 1L);
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
                selector.selectRadio(By.name(FILTER_ROW_VALUE_FIELD_BUTTON + row), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1, fieldName2, 1L);
            } else if (operator.equals(FilterOperatorType.MORE_AND_EQUAL_TODAY) || operator.equals(FilterOperatorType.LESS_AND_EQUAL_TODAY)
                    || operator.equals(FilterOperatorType.THIS_WK) || operator.equals(FilterOperatorType.THIS_MO)
                    || operator.equals(FilterOperatorType.THIS_FQ) || operator.equals(FilterOperatorType.THIS_FY)) {
                String[] values = value.split("::::");
                new Select(seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_DATE_DIRECTION + row))).selectByValue(values[0]);
                seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_DATE_VALUE + row)).clear();
                seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_DATE_VALUE + row)).sendKeys(values[1]);
            } else if (operator.equals(FilterOperatorType.WITHIN)) {
                String[] values = value.split("::::");
                seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_DATE_BEFORE + row)).clear();
                seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_DATE_BEFORE + row)).sendKeys(values[0]);
                seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_DATE_AFTER + row)).clear();
                seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_DATE_AFTER + row)).sendKeys(values[1]);
            } else if (!operator.equals(FilterOperatorType.NULL) && !operator.equals(FilterOperatorType.NOT_NULL)
                    && !operator.equals(FilterOperatorType.THIS_WK_TO_DT) && !operator.equals(FilterOperatorType.THIS_MO_TO_DT)
                    && !operator.equals(FilterOperatorType.THIS_FQ_TO_DT) && !operator.equals(FilterOperatorType.THIS_FY_TO_DT)) {
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
                selector.selectRadio(By.name(FILTER_ROW_VALUE_FIELD_BUTTON + row), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1, fieldName2, 1L);
            } else if (!operator.equals(FilterOperatorType.NULL) && !operator.equals(FilterOperatorType.NOT_NULL)) {
                seleniumSettings.getWebDriver().findElement(By.name(FILTER_ROW_VALUE_TEXT_TEXT + row)).sendKeys("*" + value + "*");
            }
        }
    }

    public void checkFilterAttributeAndOperatorAndValue(int row, String fieldName, FilterOperatorType operator) {
        assertElement.assertRadioPsSelector(FILTER_ROW_ATTRIB_TEXT + row, FILTER_ROW_ATTRIB_BUTTON + row, AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE, fieldName, 1L, true);

        assertElement.assertSelect(FILTER_ROW_OPER + row, operator.getValue());
    }

    private void checkFilterAttributeAndOperatorAndValue(int row, String fieldName, FilterOperatorType operator, String value) {
        assertElement.assertRadioPsSelector(FILTER_ROW_ATTRIB_TEXT + row, FILTER_ROW_ATTRIB_BUTTON + row, AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE, fieldName, 1L, true);

        assertElement.assertSelect(FILTER_ROW_OPER + row, operator.getValue());

        assertElement.assertText(FILTER_ROW_VALUE_TRACKOR_SELECTOR_TEXT + row, value);
    }

    public void checkFilterAttributeAndOperatorAndValue(int row, String fieldName, String fieldName2, String value, String dateType, FilterOperatorType operator, ConfigFieldType fieldDataType) {
        assertElement.assertRadioPsSelector(FILTER_ROW_ATTRIB_TEXT + row, FILTER_ROW_ATTRIB_BUTTON + row, AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE, fieldName, 1L, true);

        if (dateType != null) {
            assertElement.assertSelect(FILTER_ROW_OPER_TASK + row, dateType);
        }

        assertElement.assertSelect(FILTER_ROW_OPER + row, operator.getValue());

        if (fieldDataType.equals(ConfigFieldType.DROP_DOWN) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.SELECTOR) || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN) || fieldDataType.equals(ConfigFieldType.MULTI_TRACKOR_SELECTOR)) {
            if (operator.equals(FilterOperatorType.EQUAL_FIELD) || operator.equals(FilterOperatorType.NOT_EQUAL_FIELD)) {
                assertElement.assertRadioPsSelector(FILTER_ROW_VALUE_FIELD_TEXT + row, FILTER_ROW_VALUE_FIELD_BUTTON + row, AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE, fieldName2, 1L, true);
            } else if (!operator.equals(FilterOperatorType.NULL) && !operator.equals(FilterOperatorType.NOT_NULL)
                    && !operator.equals(FilterOperatorType.NEW) && !operator.equals(FilterOperatorType.NOT_NEW)) {
                if (fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR) || fieldDataType.equals(ConfigFieldType.MULTI_TRACKOR_SELECTOR)) {
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
                if (fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR) || fieldDataType.equals(ConfigFieldType.MULTI_TRACKOR_SELECTOR)) {
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
            } else if (operator.equals(FilterOperatorType.MORE_AND_EQUAL_TODAY) || operator.equals(FilterOperatorType.LESS_AND_EQUAL_TODAY)
                    || operator.equals(FilterOperatorType.THIS_WK) || operator.equals(FilterOperatorType.THIS_MO)
                    || operator.equals(FilterOperatorType.THIS_FQ) || operator.equals(FilterOperatorType.THIS_FY)) {
                String[] values = value.split("::::");
                assertElement.assertSelect(FILTER_ROW_VALUE_DATE_DIRECTION + row, " " + values[0] + " ");
                assertElement.assertText(FILTER_ROW_VALUE_DATE_VALUE + row, values[1]);
            } else if (operator.equals(FilterOperatorType.WITHIN)) {
                String[] values = value.split("::::");
                assertElement.assertText(FILTER_ROW_VALUE_DATE_BEFORE + row, values[0]);
                assertElement.assertText(FILTER_ROW_VALUE_DATE_AFTER + row, values[1]);
            } else if (!operator.equals(FilterOperatorType.NULL) && !operator.equals(FilterOperatorType.NOT_NULL)
                    && !operator.equals(FilterOperatorType.THIS_WK_TO_DT) && !operator.equals(FilterOperatorType.THIS_MO_TO_DT)
                    && !operator.equals(FilterOperatorType.THIS_FQ_TO_DT) && !operator.equals(FilterOperatorType.THIS_FY_TO_DT)) {
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
    }

    public void checkGridTextColumnEquals(Long gridId, int columnIndex, List<String> values) {
        int rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        for (int i = 0; i < rowsCnt; i++) {
            boolean isError = true;
            String gridValue = vals.get(i);
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
                throw new SeleniumUnexpectedException("Check fails at column [" + columnIndex + "] row [" + i + "]. Cell value in grid [" + gridValue +"]. Value in filter [" + values + "]");
            }
        }
    }

    private void checkGridTextColumnEqualsOrNull(Long gridId, int columnIndex, String value) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    public void checkGridBooleanColumnEquals(Long gridId, int columnIndex, List<String> values) {
        int rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        for (int i = 0; i < rowsCnt; i++) {
            boolean isError = false;
            String gridValue = vals.get(i);
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
                throw new SeleniumUnexpectedException("Check fails at column [" + columnIndex + "] row [" + i + "]. Cell value in grid [" + gridValue +"]. Value in filter [" + values + "]");
            }
        }
    }

    //it is temporary  solution
    //remove this method
    private void checkGridBooleanColumnEqualsOrNull(Long gridId, int columnIndex, String value) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridTextColumnNotEquals(Long gridId, int columnIndex, String value) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridTextColumnNotEqualsOrNull(Long gridId, int columnIndex, String value) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridColumnIsNull(Long gridId, int columnIndex) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridColumnIsNotNull(Long gridId, int columnIndex) {
        int rowsCnt = js.getGridRowsCount(gridId);
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
        int columnIndex = js.getColumnIndexByLabel(gridId, columnLabel);
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridColumnMultiTrackorSelectorIsNew(Long gridId, String columnLabel, List<String> newTrackors) {
        int columnIndex = js.getColumnIndexByLabel(gridId, columnLabel);
        int rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        for (int i = 0; i < rowsCnt; i++) {
            String gridValue = vals.get(i);
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. NewTrackors[%s]. Value in filter [Is New]", columnIndex, i, gridValue, newTrackors);
            Assert.assertTrue(newTrackors.stream().anyMatch(s -> gridValue.contains(s)), failMessage);
        }
    }

    private void checkGridColumnIsNotNew(Long gridId, String columnLabel, List<String> newTrackors) {
        int columnIndex = js.getColumnIndexByLabel(gridId, columnLabel);
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridColumnMultiTrackorSelectorIsNotNew(Long gridId, String columnLabel, List<String> oldTrackors) {
        int columnIndex = js.getColumnIndexByLabel(gridId, columnLabel);
        int rowsCnt = js.getGridRowsCount(gridId);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        for (int i = 0; i < rowsCnt; i++) {
            String gridValue = vals.get(i);
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. OldTrackors[%s]. Value in filter [Is Not New]", columnIndex, i, gridValue, oldTrackors);
            Assert.assertTrue(oldTrackors.stream().anyMatch(s -> gridValue.contains(s)), failMessage);
        }
    }

    private void checkGridColumnEqualsField(Long gridId, int columnIndex, int columnIndex2) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridColumnNotEqualsField(Long gridId, int columnIndex, int columnIndex2) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridColumnMore(Long gridId, int columnIndex, String value, ConfigFieldType fieldDataType) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridColumnLess(Long gridId, int columnIndex, String value, ConfigFieldType fieldDataType) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridColumnMoreEquals(Long gridId, int columnIndex, String value, ConfigFieldType fieldDataType) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridColumnLessEquals(Long gridId, int columnIndex, String value, ConfigFieldType fieldDataType) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridColumnMoreField(Long gridId, int columnIndex, int columnIndex2, ConfigFieldType fieldDataType) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridColumnLessField(Long gridId, int columnIndex, int columnIndex2, ConfigFieldType fieldDataType) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridColumnMoreEqualsField(Long gridId, int columnIndex, int columnIndex2, ConfigFieldType fieldDataType) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    private void checkGridColumnLessEqualsField(Long gridId, int columnIndex, int columnIndex2, ConfigFieldType fieldDataType) {
        int rowsCnt = js.getGridRowsCount(gridId);
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

    public void checkGridRowsCountEquals(ConfigFieldType fieldDataType, List<String> cellVals, String value) {
        if (fieldDataType.equals(ConfigFieldType.TEXT) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.WIKI) || fieldDataType.equals(ConfigFieldType.SELECTOR)
                || fieldDataType.equals(ConfigFieldType.MEMO) || fieldDataType.equals(ConfigFieldType.ELECTRONIC_FILE)
                || fieldDataType.equals(ConfigFieldType.DB_SELECTOR) || fieldDataType.equals(ConfigFieldType.DB_DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.HYPERLINK) || fieldDataType.equals(ConfigFieldType.DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.CALCULATED) || fieldDataType.equals(ConfigFieldType.ROLLUP)
                || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR) || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.MULTI_TRACKOR_SELECTOR)) {
            int cnt = 0;
            for (String cellVal : cellVals) {
                if (cellVal.contains(value)) {
                    cnt = cnt + 1;
                }
            }
            Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
        } else if (fieldDataType.equals(ConfigFieldType.CHECKBOX)) {
            int cnt = 0;
            for (String cellVal : cellVals) {
                if ("YES".equalsIgnoreCase(value)) {
                    if (cellVal.equals("YES")) {
                        cnt = cnt + 1;
                    }
                } else {
                    if (!cellVal.equals("YES")) {
                        cnt = cnt + 1;
                    }
                }
            }
            Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            int cnt = 0;
            for (String cellVal : cellVals) {
                if (cellVal.equals(value)) {
                    cnt = cnt + 1;
                }
            }
            Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    private void checkGridRowsCountNotEquals(List<String> cellVals, String value) {
        int cnt = 0;
        for (String cellVal : cellVals) {
            if (!cellVal.contains(value)) {
                cnt = cnt + 1;
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountEqualsOrNull(ConfigFieldType fieldDataType, List<String> cellVals, String value, int rowsCntBefore, @SuppressWarnings("unchecked") List<String> ... cellValsKeys) {
        if (fieldDataType.equals(ConfigFieldType.TEXT) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.WIKI) || fieldDataType.equals(ConfigFieldType.SELECTOR)
                || fieldDataType.equals(ConfigFieldType.MEMO) || fieldDataType.equals(ConfigFieldType.ELECTRONIC_FILE)
                || fieldDataType.equals(ConfigFieldType.DB_SELECTOR) || fieldDataType.equals(ConfigFieldType.DB_DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.HYPERLINK) || fieldDataType.equals(ConfigFieldType.DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.CALCULATED) || fieldDataType.equals(ConfigFieldType.ROLLUP)
                || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR) || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
            int cnt = 0;
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
                            cnt = cnt + 1;
                        }
                    }
                    if (!isAddRow) {
                        cnt = cnt + 1;
                    }
                } else {
                    cnt = cnt + 1;
                }
            }

            Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
        } else if (fieldDataType.equals(ConfigFieldType.CHECKBOX)) {
            int cnt = 0;
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
                            cnt = cnt + 1;
                        }
                    }
                    if (!isAddRow) {
                        cnt = cnt + 1;
                    }
                } else {
                    cnt = cnt + 1;
                }
            }

            Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            int cnt = 0;
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
                            cnt = cnt + 1;
                        }
                    }
                    if (!isAddRow) {
                        cnt = cnt + 1;
                    }
                } else {
                    cnt = cnt + 1;
                }
            }

            Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    private void checkGridRowsCountNotEqualsOrNull(ConfigFieldType fieldDataType, List<String> cellVals, String value, int rowsCntBefore, @SuppressWarnings("unchecked") List<String> ... cellValsKeys) {
        if (fieldDataType.equals(ConfigFieldType.TEXT) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.WIKI) || fieldDataType.equals(ConfigFieldType.SELECTOR)
                || fieldDataType.equals(ConfigFieldType.MEMO) || fieldDataType.equals(ConfigFieldType.ELECTRONIC_FILE)
                || fieldDataType.equals(ConfigFieldType.DB_SELECTOR) || fieldDataType.equals(ConfigFieldType.DB_DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.HYPERLINK) || fieldDataType.equals(ConfigFieldType.DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.CALCULATED) || fieldDataType.equals(ConfigFieldType.ROLLUP)
                || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR) || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
            int cnt = 0;
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
                                cnt = cnt + 1;
                            }
                        }
                        if (!isAddRow) {
                            cnt = cnt + 1;
                        }
                    } else {
                        cnt = cnt + 1;
                    }
                } else {
                    cnt = cnt + 1;
                }
            }

            Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            int cnt = 0;
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
                                cnt = cnt + 1;
                            }
                        }
                        if (!isAddRow) {
                            cnt = cnt + 1;
                        }
                    } else {
                        cnt = cnt + 1;
                    }
                } else {
                    cnt = cnt + 1;
                }
            }

            Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
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
                || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR) || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.MULTI_TRACKOR_SELECTOR)) {
            int cnt = 0;
            for (String cellVal : cellVals) {
                if ("&nbsp;".equals(cellVal) || "".equals(cellVal)) {
                    cnt = cnt + 1;
                }
            }
            Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            int cnt = 0;
            for (String cellVal : cellVals) {
                if ("&nbsp;".equals(cellVal) || "".equals(cellVal) || "Not Exist".equals(cellVal)) {
                    cnt = cnt + 1;
                }
            }
            Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
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
            || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR) || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)
            || fieldDataType.equals(ConfigFieldType.MULTI_TRACKOR_SELECTOR)) {
            int cnt = 0;
            for (String cellVal : cellVals) {
                if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal)) {
                    cnt = cnt + 1;
                }
            }
            Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            int cnt = 0;
            for (String cellVal : cellVals) {
                if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                    cnt = cnt + 1;
                }
            }
            Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    private void checkGridRowsCountIsNew(List<String> cellVals, List<String> newTrackors) {
        int cnt = 0;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) &&
                    newTrackors.contains(cellVal)) {
                cnt = cnt + 1;
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountMultiTrackorSelectorIsNew(List<String> cellVals, List<String> newTrackors) {
        int cnt = 0;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) &&
                    newTrackors.stream().anyMatch(s -> cellVal.contains(s))) {
                cnt = cnt + 1;
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountIsNotNew(List<String> cellVals, List<String> newTrackors) {
        int cnt = 0;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) &&
                    !newTrackors.contains(cellVal)) {
                cnt = cnt + 1;
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountMultiTrackorSelectorIsNotNew(List<String> cellVals, List<String> oldTrackors) {
        int cnt = 0;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) &&
                    oldTrackors.stream().anyMatch(s -> cellVal.contains(s))) {
                cnt = cnt + 1;
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountIsFieldLocked(List<String> trackorCellVals, List<String> trackors) {
        int cnt = 0;
        for (String trackorCellVal : trackorCellVals) {
            if (trackors.contains(trackorCellVal)) {
                cnt = cnt + 1;
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountIsFieldUnlocked(List<String> trackorCellVals, List<String> trackors) {
        int cnt = 0;
        for (String trackorCellVal : trackorCellVals) {
            if (!trackors.contains(trackorCellVal)) {
                cnt = cnt + 1;
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountEqualsField(List<String> cellVals, List<String> cellVals2) {
        int cnt = 0;
        for (int i = 0; i < cellVals.size(); i++) {
            if (cellVals.get(i).equals(cellVals2.get(i))) {
                cnt = cnt + 1;
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountNotEqualsField(List<String> cellVals, List<String> cellVals2) {
        int cnt = 0;
        for (int i = 0; i < cellVals.size(); i++) {
            if (!cellVals.get(i).equals(cellVals2.get(i))) {
                cnt = cnt + 1;
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountMore(ConfigFieldType fieldDataType, List<String> cellVals, String value) {
        int cnt = 0;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() > OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() > OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() > OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVal).compareTo(NumberUtils.createDouble(value)) > 0) {
                        cnt = cnt + 1;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountLess(ConfigFieldType fieldDataType, List<String> cellVals, String value) {
        int cnt = 0;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() < OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() < OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() < OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVal).compareTo(NumberUtils.createDouble(value)) < 0) {
                        cnt = cnt + 1;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountMoreEquals(ConfigFieldType fieldDataType, List<String> cellVals, String value) {
        int cnt = 0;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() >= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() >= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() >= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVal).compareTo(NumberUtils.createDouble(value)) >= 0) {
                        cnt = cnt + 1;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountLessEquals(ConfigFieldType fieldDataType, List<String> cellVals, String value) {
        int cnt = 0;
        for (String cellVal : cellVals) {
            if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getDateFormat()).getTime() <= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() <= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVal, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() <= OnevizionUtils.strToDate(value, seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVal).compareTo(NumberUtils.createDouble(value)) <= 0) {
                        cnt = cnt + 1;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountMoreField(ConfigFieldType fieldDataType, List<String> cellVals, List<String> cellVals2) {
        int cnt = 0;
        for (int i = 0; i < cellVals.size(); i++) {
            if (!"&nbsp;".equals(cellVals.get(i)) && !"".equals(cellVals.get(i)) && !"&nbsp;".equals(cellVals2.get(i)) && !"".equals(cellVals2.get(i))) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime() > OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() > OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() > OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVals.get(i)).compareTo(NumberUtils.createDouble(cellVals2.get(i))) > 0) {
                        cnt = cnt + 1;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountLessField(ConfigFieldType fieldDataType, List<String> cellVals, List<String> cellVals2) {
        int cnt = 0;
        for (int i = 0; i < cellVals.size(); i++) {
            if (!"&nbsp;".equals(cellVals.get(i)) && !"".equals(cellVals.get(i)) && !"&nbsp;".equals(cellVals2.get(i)) && !"".equals(cellVals2.get(i))) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime() < OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() < OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() < OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVals.get(i)).compareTo(NumberUtils.createDouble(cellVals2.get(i))) < 0) {
                        cnt = cnt + 1;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountMoreEqualsField(ConfigFieldType fieldDataType, List<String> cellVals, List<String> cellVals2) {
        int cnt = 0;
        for (int i = 0; i < cellVals.size(); i++) {
            if (!"&nbsp;".equals(cellVals.get(i)) && !"".equals(cellVals.get(i)) && !"&nbsp;".equals(cellVals2.get(i)) && !"".equals(cellVals2.get(i))) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime() >= OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() >= OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() >= OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVals.get(i)).compareTo(NumberUtils.createDouble(cellVals2.get(i))) >= 0) {
                        cnt = cnt + 1;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

    private void checkGridRowsCountLessEqualsField(ConfigFieldType fieldDataType, List<String> cellVals, List<String> cellVals2) {
        int cnt = 0;
        for (int i = 0; i < cellVals.size(); i++) {
            if (!"&nbsp;".equals(cellVals.get(i)) && !"".equals(cellVals.get(i)) && !"&nbsp;".equals(cellVals2.get(i)) && !"".equals(cellVals2.get(i))) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime() <= OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getDateFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime() <= OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaDateTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    if (OnevizionUtils.strToDate(cellVals.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime() <= OnevizionUtils.strToDate(cellVals2.get(i), seleniumSettings.getUserProperties().getJavaTimeFormat()).getTime()) {
                        cnt = cnt + 1;
                    }
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER) || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    if (NumberUtils.createDouble(cellVals.get(i)).compareTo(NumberUtils.createDouble(cellVals2.get(i))) <= 0) {
                        cnt = cnt + 1;
                    }
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        }
        Assert.assertEquals(grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx()), cnt, "Grid have wrong rows count");
    }

}
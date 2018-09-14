package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
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
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.FilterHelper;
import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.PsSelector;
import com.onevizion.uitest.api.helper.Tb;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.vo.ConfigFieldType;

@Component
public class UserpageFilter {

    public final static String BUTTON_OPEN = "btnFilter";
    public final static String BUTTON_CLEAR = "btnClear";
    public final static String BUTTON_ADD = "btnAdd";
    public final static String BUTTON_OK_ID_BASE = "btnOK";

    public final static int FIRST_MONTH_OF_FISCAL_YEAR = 1;

    @Resource
    private Js js;

    @Resource
    private Wait wait;

    @Resource
    private Window window;

    @Resource
    private PsSelector psSelector;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private FilterHelper filterHelper;

    @Resource
    private Tb tb;

    @Resource
    private SeleniumSettings seleniumSettings;

    public void checkFilterOperators(String fieldName, List<String> dateTypes, List<String> operators) {
        window.openModal(By.id(UserpageFilter.BUTTON_OPEN + 0L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        psSelector.selectSpecificValue(By.name("btnWPAttrib1"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1L, fieldName, 1L);
        if (dateTypes != null) {
            List<WebElement> options = new Select(seleniumSettings.getWebDriver().findElement(By.name("tdWPOperator1"))).getOptions();
            Assert.assertEquals(options.size(), dateTypes.size());
            for (int i = 0; i < dateTypes.size(); i++) {
                Assert.assertEquals(options.get(i).getText(), dateTypes.get(i));
            }
        }
        List<WebElement> options = new Select(seleniumSettings.getWebDriver().findElement(By.name("WPOperator1"))).getOptions();
        Assert.assertEquals(options.size(), operators.size());
        for (int i = 0; i < operators.size(); i++) {
            Assert.assertEquals(options.get(i).getText(), operators.get(i));
        }
        window.closeModalWithAlert(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE), null);
    }

    @SuppressWarnings("unchecked")
    public void checkFilterAttributeAndOperatorAndValue(String fieldName, String fieldName2, String value, String dateType,
            String operator, ConfigFieldType fieldDataType, Long columnIndex, Long columnIndex2,
            List<String> cellVals, List<String> cellVals2, List<String> ... cellValsKeys) {
        Random generator = new Random();
        int randomIndex = generator.nextInt(2);

        Long rowsCntBefore = gridHelper.getGridRowsCount(0L);
        if (randomIndex == 1) {
            selectFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, dateType, operator, fieldDataType);
        } else if (randomIndex == 0) {
            if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
                filterHelper.selectByVisibleText("G:" + fieldName + " " + operator + " " + value, 0L);
            } else if (dateType != null) {
                filterHelper.selectByVisibleText("G:" + fieldName + " " + operator + " " + dateType, 0L);
            } else {
                filterHelper.selectByVisibleText("G:" + fieldName + " " + operator, 0L);
            }
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }

        if ((fieldName.equals("FTB:FTB ID") || fieldName.equals("FT:FT ID") || fieldName.equals("FR:FR ID")
                || fieldName.equals("FTB:Rollup") || fieldName.equals("FTBP:Rollup") || fieldName.equals("FTBC:Rollup")
                || fieldName.equals("FT:Rollup") || fieldName.equals("FTP:Rollup") || fieldName.equals("FTC:Rollup")
                || fieldName.equals("FR:Rollup") || fieldName.equals("FRP:Rollup") || fieldName.equals("FRC:Rollup")) && operator.equals("Is Null")) {
            
        } else {
            Long rowsCntAfter = gridHelper.getGridRowsCount(0L);
            if (rowsCntAfter.equals(new Long(0L))) {
                throw new SeleniumUnexpectedException("Grid have wrong rows count");
            }
        }
        Long cnt = 0L;
        if (fieldDataType.equals(ConfigFieldType.TEXT) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.WIKI) || fieldDataType.equals(ConfigFieldType.SELECTOR)
                || fieldDataType.equals(ConfigFieldType.MEMO) || fieldDataType.equals(ConfigFieldType.ELECTRONIC_FILE)
                || fieldDataType.equals(ConfigFieldType.DB_SELECTOR) || fieldDataType.equals(ConfigFieldType.DB_DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.HYPERLINK) || fieldDataType.equals(ConfigFieldType.DROP_DOWN)
                || fieldDataType.equals(ConfigFieldType.CALCULATED) || fieldDataType.equals(ConfigFieldType.ROLLUP)
                || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR) || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
            if (operator.equals("=")) {
                for (String cellVal : cellVals) {
                    if (cellVal.contains(value)) {
                        cnt = cnt + 1L;
                    }
                }
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridTextColumnEquals(0L, columnIndex, Arrays.asList(value));
            } else if (operator.equals("(+)=")) {
                Map<String, List<Long>> equalsKeyMap = new HashMap<String, List<Long>>();

                for (int i = 0; i < rowsCntBefore ;i++) {
                    String str = "";
                    for (List<String> cellValsKey : cellValsKeys) {
                        str = str + cellValsKey.get(i);
                    }

                    if (equalsKeyMap.containsKey(str)) {
                        equalsKeyMap.get(str).add(new Long(i));
                    } else {
                        List<Long> vals = new ArrayList<Long>();
                        vals.add(new Long(i));
                        equalsKeyMap.put(str, vals);
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

                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridTextColumnEqualsOrNull(0L, columnIndex, value);
            } else if (operator.equals("<>")) {
                for (String cellVal : cellVals) {
                    if (!cellVal.contains(value)) {
                        cnt = cnt + 1L;
                    }
                }
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridTextColumnNotEquals(0L, columnIndex, value);
            } else if (operator.equals("(+)<>")) {
                Map<String, List<Long>> equalsKeyMap = new HashMap<String, List<Long>>();

                for (int i = 0; i < rowsCntBefore ;i++) {
                    String str = "";
                    for (List<String> cellValsKey : cellValsKeys) {
                        str = str + cellValsKey.get(i);
                    }

                    if (equalsKeyMap.containsKey(str)) {
                        equalsKeyMap.get(str).add(new Long(i));
                    } else {
                        List<Long> vals = new ArrayList<Long>();
                        vals.add(new Long(i));
                        equalsKeyMap.put(str, vals);
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

                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridTextColumnNotEqualsOrNull(0L, columnIndex, value);
            } else if (operator.equals("Is Null")) {
                for (String cellVal : cellVals) {
                    if ("&nbsp;".equals(cellVal) || "".equals(cellVal)) {
                        cnt = cnt + 1L;
                    }
                }
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnIsNull(0L, columnIndex);
            } else if (operator.equals("Is Not Null")) {
                for (String cellVal : cellVals) {
                    if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal)) {
                        cnt = cnt + 1L;
                    }
                }
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnIsNotNull(0L, columnIndex);
            } else if (operator.equals("=Field")) {
                for (int i = 0; i < cellVals.size(); i++) {
                    if (cellVals.get(i).equals(cellVals2.get(i))) {
                        cnt = cnt + 1L;
                    }
                }
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnEqualsField(0L, columnIndex, columnIndex2);
            } else if (operator.equals("<>Field")) {
                for (int i = 0; i < cellVals.size(); i++) {
                    if (!cellVals.get(i).equals(cellVals2.get(i))) {
                        cnt = cnt + 1L;
                    }
                }
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnNotEqualsField(0L, columnIndex, columnIndex2);
            } else {
                throw new SeleniumUnexpectedException("Not support operation");
            }
        } else if (fieldDataType.equals(ConfigFieldType.CHECKBOX)) {
            if (operator.equals("=")) {
                for (String cellVal : cellVals) {
                    if ("YES".equals(value.toUpperCase())) {
                        if (cellVal.equals("YES")) {
                            cnt = cnt + 1L;
                        }
                    } else {
                        if (!cellVal.equals("YES")) {
                            cnt = cnt + 1L;
                        }
                    }
                }
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridBooleanColumnEquals(0L, columnIndex, Arrays.asList(value.toUpperCase()));
            } else if (operator.equals("(+)=")) {
                Map<String, List<Long>> equalsKeyMap = new HashMap<String, List<Long>>();

                for (int i = 0; i < rowsCntBefore ;i++) {
                    String str = "";
                    for (List<String> cellValsKey : cellValsKeys) {
                        str = str + cellValsKey.get(i);
                    }

                    if (equalsKeyMap.containsKey(str)) {
                        equalsKeyMap.get(str).add(new Long(i));
                    } else {
                        List<Long> vals = new ArrayList<Long>();
                        vals.add(new Long(i));
                        equalsKeyMap.put(str, vals);
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

                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridBooleanColumnEqualsOrNull(0L, columnIndex, value.toUpperCase());
            } else {
                throw new SeleniumUnexpectedException("Not support operation");
            }
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            if (operator.equals("=")) {
                for (String cellVal : cellVals) {
                    if (cellVal.equals(value)) {
                        cnt = cnt + 1L;
                    }
                }
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridTextColumnEquals(0L, columnIndex, Arrays.asList(value));
            } else if (operator.equals("(+)=")) {
                Map<String, List<Long>> equalsKeyMap = new HashMap<String, List<Long>>();

                for (int i = 0; i < rowsCntBefore ;i++) {
                    String str = "";
                    for (List<String> cellValsKey : cellValsKeys) {
                        str = str + cellValsKey.get(i);
                    }

                    if (equalsKeyMap.containsKey(str)) {
                        equalsKeyMap.get(str).add(new Long(i));
                    } else {
                        List<Long> vals = new ArrayList<Long>();
                        vals.add(new Long(i));
                        equalsKeyMap.put(str, vals);
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

                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridTextColumnEqualsOrNull(0L, columnIndex, value);
            } else if (operator.equals(">")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnMore(0L, columnIndex, value, fieldDataType);
            } else if (operator.equals("<")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnLess(0L, columnIndex, value, fieldDataType);
            } else if (operator.equals(">=")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnMoreEquals(0L, columnIndex, value, fieldDataType);
            } else if (operator.equals("<=")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnLessEquals(0L, columnIndex, value, fieldDataType);
            } else if (operator.equals("<>")) {
                for (String cellVal : cellVals) {
                    if (!cellVal.equals(value)) {
                        cnt = cnt + 1L;
                    }
                }
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridTextColumnNotEquals(0L, columnIndex, value);
            } else if (operator.equals("(+)<>")) {
                Map<String, List<Long>> equalsKeyMap = new HashMap<String, List<Long>>();

                for (int i = 0; i < rowsCntBefore ;i++) {
                    String str = "";
                    for (List<String> cellValsKey : cellValsKeys) {
                        str = str + cellValsKey.get(i);
                    }

                    if (equalsKeyMap.containsKey(str)) {
                        equalsKeyMap.get(str).add(new Long(i));
                    } else {
                        List<Long> vals = new ArrayList<Long>();
                        vals.add(new Long(i));
                        equalsKeyMap.put(str, vals);
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

                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridTextColumnNotEqualsOrNull(0L, columnIndex, value);
            } else if (operator.equals("Is Null")) {
                for (String cellVal : cellVals) {
                    if ("&nbsp;".equals(cellVal) || "".equals(cellVal) || "Not Exist".equals(cellVal)) {
                        cnt = cnt + 1L;
                    }
                }
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnIsNull(0L, columnIndex);
            } else if (operator.equals("Is Not Null")) {
                for (String cellVal : cellVals) {
                    if (!"&nbsp;".equals(cellVal) && !"".equals(cellVal) && !"Not Exist".equals(cellVal)) {
                        cnt = cnt + 1L;
                    }
                }
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnIsNotNull(0L, columnIndex);
            } else if (operator.equals("=Field")) {
                for (int i = 0; i < cellVals.size(); i++) {
                    if (cellVals.get(i).equals(cellVals2.get(i))) {
                        cnt = cnt + 1L;
                    }
                }
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnEqualsField(0L, columnIndex, columnIndex2);
            } else if (operator.equals("<>Field")) {
                for (int i = 0; i < cellVals.size(); i++) {
                    if (!cellVals.get(i).equals(cellVals2.get(i))) {
                        cnt = cnt + 1L;
                    }
                }
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnNotEqualsField(0L, columnIndex, columnIndex2);
            } else if (operator.equals(">Field")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnMoreField(0L, columnIndex, columnIndex2, fieldDataType);
            } else if (operator.equals("<Field")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnLessField(0L, columnIndex, columnIndex2, fieldDataType);
            } else if (operator.equals(">=Field")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnMoreEqualsField(0L, columnIndex, columnIndex2, fieldDataType);
            } else if (operator.equals("<=Field")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnLessEqualsField(0L, columnIndex, columnIndex2, fieldDataType);
            } else if (operator.equals(">=Today")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnMoreEqualsToday(0L, columnIndex, Integer.parseInt((cellValsKeys[0].get(0) + cellValsKeys[0].get(1)).replace(" ", "")), fieldDataType);
            } else if (operator.equals("<=Today")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnLessEqualsToday(0L, columnIndex, Integer.parseInt((cellValsKeys[0].get(0) + cellValsKeys[0].get(1)).replace(" ", "")), fieldDataType);
            } else if (operator.equals("Within")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnWithin(0L, columnIndex, Integer.parseInt(cellValsKeys[0].get(0)), Integer.parseInt(cellValsKeys[0].get(1)), fieldDataType);
            } else if (operator.equals("This Wk")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnThisWk(0L, columnIndex, Integer.parseInt((cellValsKeys[0].get(0) + cellValsKeys[0].get(1)).replace(" ","")), fieldDataType);
            } else if (operator.equals("This Mo")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnThisMo(0L, columnIndex, Integer.parseInt((cellValsKeys[0].get(0) + cellValsKeys[0].get(1)).replace(" ","")), fieldDataType);
            } else if (operator.equals("This FQ")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnThisFQ(0L, columnIndex, Integer.parseInt((cellValsKeys[0].get(0) + cellValsKeys[0].get(1)).replace(" ","")), fieldDataType);
            } else if (operator.equals("This FY")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnThisFY(0L, columnIndex, Integer.parseInt((cellValsKeys[0].get(0) + cellValsKeys[0].get(1)).replace(" ","")), fieldDataType);
            } else if (operator.equals("This Wk to Dt")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnThisWkToDt(0L, columnIndex, fieldDataType);
            } else if (operator.equals("This Mo to Dt")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnThisMoToDt(0L, columnIndex, fieldDataType);
            } else if (operator.equals("This FQ to Dt")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnThisFQToDt(0L, columnIndex, fieldDataType);
            } else if (operator.equals("This FY to Dt")) {
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
                Assert.assertEquals(gridHelper.getGridRowsCount(0L), cnt, "Grid have wrong rows count");
                checkGridColumnThisFYToDt(0L, columnIndex, fieldDataType);
            } else {
                throw new SeleniumUnexpectedException("Not support operation");
            }
        } else {
            throw new SeleniumUnexpectedException("Not support field data type");
        }

        if (randomIndex == 1) {
            clearFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, dateType, operator, fieldDataType);
        } else if (randomIndex == 0) {
            filterHelper.selectByVisibleText("Unsaved Filter", 0L);
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }
    }

    private void selectFilterAttributeAndOperatorAndValue(String fieldName, String fieldName2, String value, String dateType, String operator, ConfigFieldType fieldDataType) {
        window.openModal(By.id(UserpageFilter.BUTTON_OPEN + 0L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        psSelector.selectSpecificValue(By.name("btnWPAttrib1"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1L, fieldName, 1L);
        if (dateType != null) {
            new Select(seleniumSettings.getWebDriver().findElement(By.name("tdWPOperator1"))).selectByVisibleText(dateType);
        }
        new Select(seleniumSettings.getWebDriver().findElement(By.name("WPOperator1"))).selectByVisibleText(operator);
        if (fieldDataType.equals(ConfigFieldType.DROP_DOWN) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.SELECTOR) || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
            if (operator.equals("=Field") || operator.equals("<>Field")) {
                psSelector.selectSpecificValue(By.name("btnFSelWPAttribValue1"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1L, fieldName2, 1L);
            } else if (!operator.equals("Is Null") && !operator.equals("Is Not Null")) {
                if (fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)) {
                    By btnOpen = By.xpath("//*[string(@submitName)='btnTrSelWPAttribValue1'] | //*[string(@name)='btnTrSelWPAttribValue1']");
                    psSelector.selectMultipleSpecificValues(btnOpen, 0L, Arrays.asList(value), 1L);
                } else if (fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR)) {
                    By btnOpen = By.xpath("//*[string(@submitName)='btnMultSelWPAttribValue1'] | //*[string(@name)='btnMultSelWPAttribValue1']");
                    psSelector.selectMultipleSpecificValues(btnOpen, 0L, Arrays.asList(value), 1L);
                } else if (fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
                    By btnOpen = By.xpath("//*[string(@submitName)='btnTrDropDownWPAttribValue1'] | //*[string(@name)='btnTrDropDownWPAttribValue1']");
                    psSelector.selectSpecificValue(btnOpen, By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 0L, value, 1L);
                } else {
                    By btnOpen = By.xpath("//*[string(@submitName)='btnSelWPAttribValue1'] | //*[string(@name)='btnSelWPAttribValue1']");
                    psSelector.selectMultipleSpecificValues(btnOpen, 0L, Arrays.asList(value), 1L);
                }
            }
        } else if (fieldDataType.equals(ConfigFieldType.CHECKBOX)) {
            new Select(seleniumSettings.getWebDriver().findElement(By.name("ynWPAttribValue1"))).selectByVisibleText(value);
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            if (operator.equals("=Field") || operator.equals("<>Field")
                    || operator.equals(">Field") || operator.equals("<Field")
                    || operator.equals(">=Field") || operator.equals("<=Field")) {
                psSelector.selectSpecificValue(By.name("btnFSelWPAttribValue1"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1L, fieldName2, 1L);
            //} else if (operator.equals(">=Today") || operator.equals("<=Today") //TODO check those lines
            //        || operator.equals("This Wk") || operator.equals("This Mo")
            //        || operator.equals("This FQ") || operator.equals("This FY")) {
            //    if (cellValsKeys != null) {
            //        new Select(seleniumSettings.getWebDriver().findElement(By.name("todayDirWPAttribValue1"))).selectByVisibleText(cellValsKeys[0].get(0));
            //        if (!"0".equals(cellValsKeys[0].get(1))) {
            //            seleniumSettings.getWebDriver().findElement(By.name("todayValWPAttribValue1")).clear();
            //            seleniumSettings.getWebDriver().findElement(By.name("todayValWPAttribValue1")).sendKeys(cellValsKeys[0].get(1));
            //        }
            //    }
            //} else if (operator.equals("Within")) {
            //    if ((cellValsKeys != null) && ((!"0".equals(cellValsKeys[0].get(0)))||(!"0".equals(cellValsKeys[0].get(1))))) {
            //        seleniumSettings.getWebDriver().findElement(By.name("withinBeforeWPAttribValue1")).clear();
            //        seleniumSettings.getWebDriver().findElement(By.name("withinBeforeWPAttribValue1")).sendKeys(cellValsKeys[0].get(0));
            //        seleniumSettings.getWebDriver().findElement(By.name("withinAfterWPAttribValue1")).clear();
            //        seleniumSettings.getWebDriver().findElement(By.name("withinAfterWPAttribValue1")).sendKeys(cellValsKeys[0].get(1));
            //    }
            } else if (!operator.equals("Is Null") && !operator.equals("Is Not Null")) {
                    //&& !operator.equals("This Wk to Dt") && !operator.equals("This Mo to Dt") //TODO check those lines
                    //&& !operator.equals("This FQ to Dt") && !operator.equals("This FY to Dt")) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    seleniumSettings.getWebDriver().findElement(By.name("dateWPAttribValue1")).sendKeys(value);
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    seleniumSettings.getWebDriver().findElement(By.name("dateTimeWPAttribValue1")).sendKeys(value);
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    seleniumSettings.getWebDriver().findElement(By.name("timeWPAttribValue1")).sendKeys(value);
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER)) {
                    seleniumSettings.getWebDriver().findElement(By.name("numWPAttribValue1")).sendKeys(value);
                } else if (fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    seleniumSettings.getWebDriver().findElement(By.name("latlongWPAttribValue1")).sendKeys(value);
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        } else {
            if (operator.equals("=Field") || operator.equals("<>Field")) {
                psSelector.selectSpecificValue(By.name("btnFSelWPAttribValue1"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), 1L, fieldName2, 1L);
            } else if (!operator.equals("Is Null") && !operator.equals("Is Not Null")) {
                seleniumSettings.getWebDriver().findElement(By.name("txtWPAttribValue1")).sendKeys("*" + value + "*");
            }
        }
        window.closeModalAndWaitGridLoad(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    private void clearFilterAttributeAndOperatorAndValue(String fieldName, String fieldName2, String value, String dateType, String operator, ConfigFieldType fieldDataType) {
        window.openModal(By.id(UserpageFilter.BUTTON_OPEN + 0L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        assertHelper.AssertRadioPsSelector("txtWPAttrib1", "btnWPAttrib1", AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE, fieldName, 1L, true);
        if (dateType != null) {
            assertHelper.AssertSelect("tdWPOperator1", dateType);
        }
        assertHelper.AssertSelect("WPOperator1", operator);
        if (fieldDataType.equals(ConfigFieldType.DROP_DOWN) || fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.SELECTOR) || fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR)
                || fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
            if (operator.equals("=Field") || operator.equals("<>Field")) {
                assertHelper.AssertRadioPsSelector("fSelWPAttribValue1", "btnFSelWPAttribValue1", AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE, fieldName2, 1L, true);
            } else if (!operator.equals("Is Null") && !operator.equals("Is Not Null")) {
                if (fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)) {
                    assertHelper.AssertCheckboxPsSelector("trSelWPAttribValue1", "btnTrSelWPAttribValue1", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(value), 1L, true);
                } else if (fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR)) {
                    assertHelper.AssertCheckboxPsSelector("multSelWPAttribValue1", "btnMultSelWPAttribValue1", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(value), 1L, true);
                } else if (fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
                    assertHelper.AssertRadioPsSelector("trDropDownWPAttribValue1", "btnTrDropDownWPAttribValue1", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, value, 1L, true);
                } else if (fieldDataType.equals(ConfigFieldType.DROP_DOWN) || fieldDataType.equals(ConfigFieldType.SELECTOR)) {
                    assertHelper.AssertCheckboxPsSelector("selWPAttribValue1", "btnSelWPAttribValue1", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(value), 1L, true);
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            } else {
                if (fieldDataType.equals(ConfigFieldType.TRACKOR_SELECTOR)) {
                    assertHelper.AssertText("trSelWPAttribValue1", "");
                } else if (fieldDataType.equals(ConfigFieldType.MULTI_SELECTOR)) {
                    assertHelper.AssertText("multSelWPAttribValue1", "");
                } else if (fieldDataType.equals(ConfigFieldType.TRACKOR_DROP_DOWN)) {
                    assertHelper.AssertText("trDropDownWPAttribValue1", "");
                } else if (fieldDataType.equals(ConfigFieldType.DROP_DOWN) || fieldDataType.equals(ConfigFieldType.SELECTOR)) {
                    assertHelper.AssertText("selWPAttribValue1", "");
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        } else if (fieldDataType.equals(ConfigFieldType.CHECKBOX)) {
            assertHelper.AssertSelect("ynWPAttribValue1", value);
        } else if (fieldDataType.equals(ConfigFieldType.DATE) || fieldDataType.equals(ConfigFieldType.DATE_TIME)
                || fieldDataType.equals(ConfigFieldType.TIME) || fieldDataType.equals(ConfigFieldType.NUMBER)
                || fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
            if (operator.equals("=Field") || operator.equals("<>Field")
                    || operator.equals(">Field") || operator.equals("<Field")
                    || operator.equals(">=Field") || operator.equals("<=Field")) {
                assertHelper.AssertRadioPsSelector("fSelWPAttribValue1", "btnFSelWPAttribValue1", AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE, fieldName2, 1L, true);
            } else if (!operator.equals("Is Null") && !operator.equals("Is Not Null")) {
                    //&& !operator.equals("This Wk to Dt") && !operator.equals("This Mo to Dt") //TODO check those lines
                    //&& !operator.equals("This FQ to Dt") && !operator.equals("This FY to Dt")) {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    assertHelper.AssertText("dateWPAttribValue1", value);
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    assertHelper.AssertText("dateTimeWPAttribValue1", value);
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    assertHelper.AssertText("timeWPAttribValue1", value);
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER)) {
                    assertHelper.AssertText("numWPAttribValue1", value);
                } else if (fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    assertHelper.AssertText("latlongWPAttribValue1", value);
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            } else {
                if (fieldDataType.equals(ConfigFieldType.DATE)) {
                    assertHelper.AssertText("dateWPAttribValue1", "");
                } else if (fieldDataType.equals(ConfigFieldType.DATE_TIME)) {
                    assertHelper.AssertText("dateTimeWPAttribValue1", "");
                } else if (fieldDataType.equals(ConfigFieldType.TIME)) {
                    assertHelper.AssertText("timeWPAttribValue1", "");
                } else if (fieldDataType.equals(ConfigFieldType.NUMBER)) {
                    assertHelper.AssertText("numWPAttribValue1", "");
                } else if (fieldDataType.equals(ConfigFieldType.LATITUDE) || fieldDataType.equals(ConfigFieldType.LONGITUDE)) {
                    assertHelper.AssertText("latlongWPAttribValue1", "");
                } else {
                    throw new SeleniumUnexpectedException("Not support field data type");
                }
            }
        } else {
            if (operator.equals("=Field") || operator.equals("<>Field")) {
                assertHelper.AssertRadioPsSelector("fSelWPAttribValue1", "btnFSelWPAttribValue1", AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE, fieldName2, 1L, true);
            } else if (!operator.equals("Is Null") && !operator.equals("Is Not Null")) {
                assertHelper.AssertText("txtWPAttribValue1", "*" + value + "*");
            } else {
                assertHelper.AssertText("txtWPAttribValue1", "");
            }
        }
        seleniumSettings.getWebDriver().findElement(By.name(UserpageFilter.BUTTON_CLEAR)).click();

        window.closeModalAndWaitGridLoad(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
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
                if (!gridValue.toLowerCase().equals(value.toLowerCase())) {
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
            failMessage = String.format(
                    "Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [%s]",
                    columnIndex, i, gridValue, value);
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
            failMessage = String.format(
                    "Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [Is Null]",
                    columnIndex, i, gridValue);
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
            failMessage = String.format(
                    "Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [Is Not Null]",
                    columnIndex, i, gridValue);
            Assert.assertNotEquals(gridValue, "", failMessage);
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
            failMessage = String.format(
                    "Check fails at columns [%s][%s] row [%s]. Cell value in grid column [%s]. Cell value in grid column2 [%s]",
                    columnIndex, columnIndex2, i, gridValue, gridValue2);
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
            failMessage = String.format(
                    "Check fails at columns [%s][%s] row [%s]. Cell value in grid column [%s]. Cell value in grid column2 [%s]",
                    columnIndex, columnIndex2, i, gridValue, gridValue2);
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

    private Date getToday(int daysCount){
        Calendar cal = Calendar.getInstance(/*TimeZone.getTimeZone("GMT+00:00")*/);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.add(Calendar.DAY_OF_WEEK, daysCount);
        Date desiredDate = cal.getTime();
        return desiredDate;
    }

    private Date getFirstDayOfThisWeek(int weeksCount){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.add(Calendar.WEEK_OF_YEAR, weeksCount);
        Date desiredDate = cal.getTime();
        return desiredDate;
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
        Date desiredDate = cal.getTime();
        return desiredDate;
    }

    private Date getFirstDayOfThisMo(int moCount){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, moCount);
        Date desiredDate = cal.getTime();
        return desiredDate;
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
        Date desiredDate = cal.getTime();
        return desiredDate;
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
        Date desiredDate = cal.getTime();
        return desiredDate;
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
        Date desiredDate = cal.getTime();
        return desiredDate;
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
        Date desiredDate = cal.getTime();
        return desiredDate;
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
        Date desiredDate = cal.getTime();
        return desiredDate;
    }

    @SuppressWarnings("unchecked")
    public void createTrackorForDateTest(String idFieldName, String dateFieldName, String operator, List<String> ... cellValsKeys){
        window.openModal(By.id(UserpageFilter.BUTTON_ADD + 0L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        int count1 = 0;
        int count2 = 0;

        if (operator.equals(">=Today") || operator.equals("<=Today")
                || operator.equals("This Wk") || operator.equals("This Mo")
                || operator.equals("This FQ") || operator.equals("This FY")) {
            if (cellValsKeys != null) {
                seleniumSettings.getWebDriver().findElement(By.name(idFieldName)).clear();
                seleniumSettings.getWebDriver().findElement(By.name(idFieldName)).sendKeys();
                /*tbHelper.editField(FieldDataType.TEXT,
                        "dateFilter " + operator + cellValsKeys[0].get(0) + cellValsKeys[0].get(1),
                        "s_" + ttid + "_0",
                        "fe" + cfXitorKey + "_1_0",
                        vals1,
                        grid1Vals,
                        1);*/
                count1 = Integer.parseInt((cellValsKeys[0].get(0) + cellValsKeys[0].get(1)).replace(" ",""));
            }
        } else if (operator.equals("Within")) {
            if (cellValsKeys != null) {
                seleniumSettings.getWebDriver().findElement(By.name(idFieldName)).clear();
                seleniumSettings.getWebDriver().findElement(By.name(idFieldName)).sendKeys("dateFilter " + operator + " - " + cellValsKeys[0].get(0) + " + " + cellValsKeys[0].get(1));
                count1 = Integer.parseInt(cellValsKeys[0].get(0));
                count2 = Integer.parseInt(cellValsKeys[0].get(1));
            }
        } else if (operator.equals("This Wk to Dt") || operator.equals("This Mo to Dt") 
                || operator.equals("This FQ to Dt") || operator.equals("This FY to Dt")) {
            seleniumSettings.getWebDriver().findElement(By.name(idFieldName)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(idFieldName)).sendKeys("dateFilter " + operator);
        }

        seleniumSettings.getWebDriver().findElement(By.name(dateFieldName)).clear();

        if (operator.equals(">=Today")) {
           seleniumSettings.getWebDriver().findElement(By.name(dateFieldName)).sendKeys(OnevizionUtils.dateFmt(getToday(count1), seleniumSettings.getUserProperties().getDateFormat()));
        } else if (operator.equals("<=Today")) {
            seleniumSettings.getWebDriver().findElement(By.name(dateFieldName)).sendKeys(OnevizionUtils.dateFmt(getToday(count1), seleniumSettings.getUserProperties().getDateFormat()));
        } else if (operator.equals("This Wk")) {
            seleniumSettings.getWebDriver().findElement(By.name(dateFieldName)).sendKeys(OnevizionUtils.dateFmt(getFirstDayOfThisWeek(count1), seleniumSettings.getUserProperties().getDateFormat()));
        } else if (operator.equals("This Mo")) {
            seleniumSettings.getWebDriver().findElement(By.name(dateFieldName)).sendKeys(OnevizionUtils.dateFmt(getFirstDayOfThisMo(count1), seleniumSettings.getUserProperties().getDateFormat()));
        } else if (operator.equals("This FQ")) {
            seleniumSettings.getWebDriver().findElement(By.name(dateFieldName)).sendKeys(OnevizionUtils.dateFmt(getFirstDayOfThisFQ(count1), seleniumSettings.getUserProperties().getDateFormat()));
        } else if (operator.equals("This FY")) {
            seleniumSettings.getWebDriver().findElement(By.name(dateFieldName)).sendKeys(OnevizionUtils.dateFmt(getFirstDayOfThisFY(count1), seleniumSettings.getUserProperties().getDateFormat()));
        } else if (operator.equals("Within")) {
            seleniumSettings.getWebDriver().findElement(By.name(dateFieldName)).sendKeys(OnevizionUtils.dateFmt(getToday(0), seleniumSettings.getUserProperties().getDateFormat()));
        } else if (operator.equals("This Wk to Dt")) {
            seleniumSettings.getWebDriver().findElement(By.name(dateFieldName)).sendKeys(OnevizionUtils.dateFmt(getFirstDayOfThisWeek(count1), seleniumSettings.getUserProperties().getDateFormat()));
        } else if (operator.equals("This Mo to Dt")) {
            seleniumSettings.getWebDriver().findElement(By.name(dateFieldName)).sendKeys(OnevizionUtils.dateFmt(getFirstDayOfThisMo(count1), seleniumSettings.getUserProperties().getDateFormat()));
        } else if (operator.equals("This FQ to Dt")) {
            seleniumSettings.getWebDriver().findElement(By.name(dateFieldName)).sendKeys(OnevizionUtils.dateFmt(getFirstDayOfThisFQ(count1), seleniumSettings.getUserProperties().getDateFormat()));
        } else if (operator.equals("This FY to Dt")) {
            seleniumSettings.getWebDriver().findElement(By.name(dateFieldName)).sendKeys(OnevizionUtils.dateFmt(getFirstDayOfThisFY(count1), seleniumSettings.getUserProperties().getDateFormat()));
        }

        window.closeModal(By.id(BUTTON_OK_ID_BASE));
        wait.waitGridLoad(0L, 0L);
    }

}
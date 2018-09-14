package com.onevizion.uitest.api.helper;

import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.vo.GridColumnType;
import com.onevizion.uitest.api.vo.SortType;

@Component
public class SortHelper {

    @Resource
    private JsHelper jsHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private WaitHelper waitHelper;

    public void checkSortColumnByName(Long gridId, String columnName, GridColumnType gridColumnType) {
        Long columnIdx = jsHelper.getColumnIndexByLabel(gridId, columnName);
        String columnId = jsHelper.getGridColIdByIndex(gridId, columnIdx);

        checkSortColumn(gridId, columnId, gridColumnType);
    }

    public void checkSortColumn(Long gridId, String columnId, GridColumnType gridColumnType) {
        waitHelper.waitGridLoad(gridId, gridId);

        Long columnIdx = jsHelper.getGridColIndexById(gridId, columnId);
        Long scrollLeft = jsHelper.getGridScrollLeft(gridId, columnIdx);

        jsHelper.gridScrollLeft(gridId, scrollLeft);
        sortColumn(gridId, columnId, SortType.ASC);
        checkColumn(gridId, columnIdx, gridColumnType, SortType.ASC);

        jsHelper.gridScrollLeft(gridId, scrollLeft);
        sortColumn(gridId, columnId, SortType.DESC);
        checkColumn(gridId, columnIdx, gridColumnType, SortType.DESC);
    }

    public void sortColumnByName(Long gridId, String columnName, SortType sortType) {
        Long columnIdx = jsHelper.getColumnIndexByLabel(gridId, columnName);
        String columnId = jsHelper.getGridColIdByIndex(gridId, columnIdx);
        sortColumn(gridId, columnId, sortType);
    }

    public void sortColumn(Long gridId, String columnId, SortType sortType) {
        Long columnIdx = jsHelper.getGridColIndexById(gridId, columnId);
        String sortColumnId = jsHelper.getGridSortColumnIdByGridId(gridId);

        Long scrollLeft = jsHelper.getGridScrollLeft(gridId, columnIdx);
        jsHelper.gridScrollLeft(gridId, scrollLeft);

        if (sortType.equals(SortType.ASC)) {
            if (!columnId.equals(sortColumnId)) {
                seleniumSettings.getWebDriver().findElements(By.className("hdrcell")).get(columnIdx.intValue()).click();
            } else {
                String gridSortType = jsHelper.getGridSortTypeByGridId(gridId);
                if (!"0".equals(gridSortType)) {
                    seleniumSettings.getWebDriver().findElements(By.className("hdrcell")).get(columnIdx.intValue()).click();
                }
            }
        } else {
            if (!columnId.equals(sortColumnId)) {
                seleniumSettings.getWebDriver().findElements(By.className("hdrcell")).get(columnIdx.intValue()).click();
                waitHelper.waitGridLoad(gridId, gridId);
                seleniumSettings.getWebDriver().findElements(By.className("hdrcell")).get(columnIdx.intValue()).click();
            } else {
                String gridSortType = jsHelper.getGridSortTypeByGridId(gridId);
                if (!"1".equals(gridSortType)) {
                    seleniumSettings.getWebDriver().findElements(By.className("hdrcell")).get(columnIdx.intValue()).click();
                }
            }
        }

        waitHelper.waitGridLoad(gridId, gridId);

        sortColumnId = jsHelper.getGridSortColumnIdByGridId(gridId);
        Assert.assertEquals(sortColumnId, columnId);
        String gridSortType = jsHelper.getGridSortTypeByGridId(gridId);
        if (SortType.ASC.equals(sortType)) {
            Assert.assertEquals(gridSortType, "0");
        } else if (SortType.DESC.equals(sortType)) {
            Assert.assertEquals(gridSortType, "1");
        } else {
            throw new SeleniumUnexpectedException("Not support SortType. SortType=" + sortType);
        }
    }

    private void checkColumn(Long gridId, Long columnIdx, GridColumnType gridColumnType, SortType sortType) {
        Long rowsNum = jsHelper.getGridRowsCount(gridId);

        if (rowsNum <= 1L) {
            return; //Nothing to check
            //TODO may be log with error or throw exception?
        }

        Long curRowNum = 1L;
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) jsHelper.getGridCellsValuesForColumnByColIndex(gridId, rowsNum, columnIdx);
        while (curRowNum < rowsNum) {
            String curVal = vals.get(curRowNum.intValue());
            if (StringUtils.isNotBlank(curVal)) {
                curVal = curVal.replaceAll("^<[aA].*?>", "").replaceAll("</[aA]>$", ""); /*Example: condition for link*/
                curVal = StringUtils.substringBefore(curVal, "\n"); /*Example: condition for pl/sql block where value may have character of new line*/
            }

            String prevVal = vals.get(curRowNum.intValue() - 1);
            if (StringUtils.isNotBlank(prevVal)) {
                prevVal = prevVal.replaceAll("^<[aA].*?>", "").replaceAll("</[aA]>$", ""); /*Example: condition for link*/
                prevVal = StringUtils.substringBefore(prevVal, "\n"); /*Example: condition for pl/sql block where value may have character of new line*/
            }

            if ("".equals(curVal) || "".equals(prevVal)) {
                
            } else {
                if (GridColumnType.NUMBER.equals(gridColumnType)) {
                    checkColumnNumber(curVal, prevVal, sortType, columnIdx, curRowNum);
                } else if (GridColumnType.TIMESTAMP.equals(gridColumnType)) {
                    checkColumnTimestamp(curVal, prevVal, sortType, columnIdx, curRowNum);
                } else if (GridColumnType.BOOLEAN.equals(gridColumnType)) {
                    checkColumnBoolean(curVal, prevVal, sortType, columnIdx, curRowNum);
                } else if (GridColumnType.TEXT.equals(gridColumnType)) {
                    //TODO check sort by text column not working. because oracle and java sort string by different logic
                } else if (GridColumnType.DATE.equals(gridColumnType)) {
                    checkColumnDate(curVal, prevVal, sortType, columnIdx, curRowNum);
                } else {
                    throw new SeleniumUnexpectedException("Not support GridColumnType. GridColumnType=" + gridColumnType);
                }
            }

            curRowNum = curRowNum + 1L;
        }
    }

    private void checkColumnNumber(String curVal, String prevVal, SortType sortType, Long columnIdx, Long curRowNum) {
        Long curValNum = Long.valueOf(curVal);
        Long prevValNum = Long.valueOf(prevVal);

        if (SortType.ASC.equals(sortType)) {
            Assert.assertEquals(prevValNum.compareTo(curValNum) <= 0, true, "Sort asc for [" + columnIdx + "] column is wrong. Cur row num [" + curRowNum +"] val [" + curValNum + "], prev row num [" + (curRowNum - 1) + "] val [" + prevValNum +"]");
        } else if (SortType.DESC.equals(sortType)) {
            Assert.assertEquals(curValNum.compareTo(prevValNum) <= 0, true, "Sort desc for [" + columnIdx + "] column is wrong. Cur row num [" + curRowNum +"] val [" + curValNum + "], prev row num [" + (curRowNum - 1) + "] val [" + prevValNum +"]");
        } else {
            throw new SeleniumUnexpectedException("Not support SortType. SortType=" + sortType);
        }
    }

    private void checkColumnTimestamp(String curVal, String prevVal, SortType sortType, Long columnIdx, Long curRowNum) {
        //TODO Date format should depend on user Settings
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date curValDate;
        Date prevValDate;
        try {
            curValDate = dateFormat.parse(curVal);
            prevValDate = dateFormat.parse(prevVal);
        } catch (ParseException e) {
            throw new SeleniumUnexpectedException("Exception while parsing date value for [" + columnIdx + "] column. Cur row num [" + curRowNum +"] val [" + curVal + "], prev row num [" + (curRowNum - 1) + "] val [" + prevVal +"]");
        }

        if (SortType.ASC.equals(sortType)) {
            Assert.assertEquals(prevValDate.compareTo(curValDate) <= 0, true, "Sort asc for [" + columnIdx + "] column is wrong. Cur row num [" + curRowNum +"] val [" + curValDate + "], prev row num [" + (curRowNum - 1) + "] val [" + prevValDate +"]");
        } else if (SortType.DESC.equals(sortType)) {
            Assert.assertEquals(curValDate.compareTo(prevValDate) <= 0, true, "Sort desc for [" + columnIdx + "] column is wrong. Cur row num [" + curRowNum +"] val [" + curValDate + "], prev row num [" + (curRowNum - 1) + "] val [" + prevValDate +"]");
        } else {
            throw new SeleniumUnexpectedException("Not support SortType. SortType=" + sortType);
        }
    }

    private void checkColumnDate(String curVal, String prevVal, SortType sortType, Long columnIdx, Long curRowNum) {
        //TODO Date format should depend on user Settings
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        Date curValDate;
        Date prevValDate;
        try {
            curValDate = dateFormat.parse(curVal);
            prevValDate = dateFormat.parse(prevVal);
        } catch (ParseException e) {
            throw new SeleniumUnexpectedException("Exception while parsing date value for [" + columnIdx + "] column. Cur row num [" + curRowNum +"] val [" + curVal + "], prev row num [" + (curRowNum - 1) + "] val [" + prevVal +"]");
        }

        if (SortType.ASC.equals(sortType)) {
            Assert.assertEquals(prevValDate.compareTo(curValDate) <= 0, true, "Sort asc for [" + columnIdx + "] column is wrong. Cur row num [" + curRowNum +"] val [" + curValDate + "], prev row num [" + (curRowNum - 1) + "] val [" + prevValDate +"]");
        } else if (SortType.DESC.equals(sortType)) {
            Assert.assertEquals(curValDate.compareTo(prevValDate) <= 0, true, "Sort desc for [" + columnIdx + "] column is wrong. Cur row num [" + curRowNum +"] val [" + curValDate + "], prev row num [" + (curRowNum - 1) + "] val [" + prevValDate +"]");
        } else {
            throw new SeleniumUnexpectedException("Not support SortType. SortType=" + sortType);
        }
    }

    private void checkColumnBoolean(String curVal, String prevVal, SortType sortType, Long columnIdx, Long curRowNum) {
        if (SortType.ASC.equals(sortType)) {
            Assert.assertEquals(prevVal.compareTo(curVal) <= 0, true, "Sort asc for [" + columnIdx + "] column is wrong. Cur row num [" + curRowNum +"] val [" + curVal + "], prev row num [" + (curRowNum - 1) + "] val [" + prevVal +"]");
        } else if (SortType.DESC.equals(sortType)) {
            Assert.assertEquals(curVal.compareTo(prevVal) <= 0, true, "Sort desc for [" + columnIdx + "] column is wrong. Cur row num [" + curRowNum +"] val [" + curVal + "], prev row num [" + (curRowNum - 1) + "] val [" + prevVal +"]");
        } else {
            throw new SeleniumUnexpectedException("Not support SortType. SortType=" + sortType);
        }
    }

}
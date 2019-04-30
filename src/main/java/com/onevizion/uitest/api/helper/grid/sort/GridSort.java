package com.onevizion.uitest.api.helper.grid.sort;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.vo.GridColumnType;
import com.onevizion.uitest.api.vo.SortType;

@Component
public class GridSort {

    private static final String COLUMN_SORT_TYPE_NA = "na";

    @Resource
    private GridSortJs gridSortJs;

    @Resource
    private GridSortWait gridSortWait;

    @Resource
    private Js js;

    @Resource
    private Element element;

    @Resource
    private ElementWait elementWait;

    @Resource
    private Wait wait;

    @Resource
    private Grid2 grid2;

    @Resource
    private SeleniumSettings seleniumSettings;

    public void sortColumn(SortType sortType, String columnLabel) {
        sortColumn(AbstractSeleniumCore.getGridIdx(), sortType, columnLabel);
    }

    public void sortColumn(Long gridId, SortType sortType, String columnLabel) {
        checkColumnSortAvailable(gridId, columnLabel);

        Long columnFirstRowIndex = js.getColumnFirstRowIndex(gridId, columnLabel);

        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel);
        String columnId = js.getGridColIdByIndex(gridId, columnIndex);

        WebElement elem = seleniumSettings.getWebDriver().findElement(By.className("hdr")).findElement(By.tagName("tbody"))
                .findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(columnFirstRowIndex.intValue()).findElement(By.tagName("div"));

        Long scrollLeft = js.getGridScrollLeft(gridId, columnIndex);

        js.gridScrollLeft(gridId, scrollLeft);
        elementWait.waitElementVisible(elem);
        element.moveToElement(elem);
        elem.click();

        gridSortWait.waitSortMenuIsDisplayed();

        WebElement sortButton = null;
        List<WebElement> menus = seleniumSettings.getWebDriver().findElements(By.className("contextSort"));
        for (WebElement menu : menus) {
            if (menu.isDisplayed()) {
                List<WebElement> menuItems = menu.findElements(By.className("ic_container"));
                for (WebElement menuItem : menuItems) {
                    String menuItemText = menuItem.getAttribute("innerText");
                    if (sortType.getName().equals(menuItemText)) {
                        if (sortButton != null) {
                            throw new SeleniumUnexpectedException("Sort button [" + sortType.getName() + "] found many times");
                        }
                        sortButton = menuItem;
                    }
                }
            }
        }

        if (sortButton == null) {
            throw new SeleniumUnexpectedException("Sort button [" + sortType.getName() + "] not found");
        }

        sortButton.click();

        grid2.waitLoad(gridId);

        checkCurrentGridSort(gridId, sortType, columnIndex, columnId);
    }

    public void sortColumn(SortType sortType, String columnLabel, String columnLabel2) {
        sortColumn(AbstractSeleniumCore.getGridIdx(), sortType, columnLabel, columnLabel2);
    }

    public void sortColumn(Long gridId, SortType sortType, String columnLabel, String columnLabel2) {
        checkColumnSortAvailable(gridId, columnLabel, columnLabel2);

        Long columnSecondRowIndex = js.getColumnSecondRowIndex(gridId, columnLabel, columnLabel2);

        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel, columnLabel2);
        String columnId = js.getGridColIdByIndex(gridId, columnIndex);

        WebElement elem = seleniumSettings.getWebDriver().findElement(By.className("hdr")).findElement(By.tagName("tbody"))
                .findElements(By.tagName("tr")).get(2).findElements(By.tagName("td")).get(columnSecondRowIndex.intValue()).findElement(By.tagName("div"));

        Long scrollLeft = js.getGridScrollLeft(gridId, columnIndex);

        js.gridScrollLeft(gridId, scrollLeft);
        elementWait.waitElementVisible(elem);
        element.moveToElement(elem);
        elem.click();

        gridSortWait.waitSortMenuIsDisplayed();

        WebElement sortButton = null;
        List<WebElement> menus = seleniumSettings.getWebDriver().findElements(By.className("contextSort"));
        for (WebElement menu : menus) {
            if (menu.isDisplayed()) {
                List<WebElement> menuItems = menu.findElements(By.className("ic_container"));
                for (WebElement menuItem : menuItems) {
                    String menuItemText = menuItem.getAttribute("innerText");
                    if (sortType.getName().equals(menuItemText)) {
                        if (sortButton != null) {
                            throw new SeleniumUnexpectedException("Sort button [" + sortType.getName() + "] found many times");
                        }
                        sortButton = menuItem;
                    }
                }
            }
        }

        if (sortButton == null) {
            throw new SeleniumUnexpectedException("Sort button [" + sortType.getName() + "] not found");
        }

        sortButton.click();

        grid2.waitLoad(gridId);

        checkCurrentGridSort(gridId, sortType, columnIndex, columnId);
    }

    public void checkCurrentGridSort(SortType sortType, String columnLabel) {
        checkCurrentGridSort(AbstractSeleniumCore.getGridIdx(), sortType, columnLabel);
    }

    public void checkCurrentGridSort(Long gridId, SortType sortType, String columnLabel) {
        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel);
        String columnId = js.getGridColIdByIndex(gridId, columnIndex);

        checkCurrentGridSort(gridId, sortType, columnIndex, columnId);
    }

    public void checkCurrentGridSort(SortType sortType, String columnLabel, String columnLabel2) {
        checkCurrentGridSort(AbstractSeleniumCore.getGridIdx(), sortType, columnLabel, columnLabel2);
    }

    public void checkCurrentGridSort(Long gridId, SortType sortType, String columnLabel, String columnLabel2) {
        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel, columnLabel2);
        String columnId = js.getGridColIdByIndex(gridId, columnIndex);

        checkCurrentGridSort(gridId, sortType, columnIndex, columnId);
    }

    private void checkCurrentGridSort(Long gridId, SortType sortType, Long columnIndex, String columnId) {
        gridSortWait.checkGridSort(gridId, columnIndex, sortType.getTypeString());
        gridSortWait.checkGridSortColumnId(gridId, columnId);
        gridSortWait.checkGridSortTypeNumber(gridId, sortType.getTypeNumber());
    }

    public void checkColumnSortAvailable(String columnLabel) {
        checkColumnSortAvailable(AbstractSeleniumCore.getGridIdx(), columnLabel);
    }

    public void checkColumnSortAvailable(Long gridId, String columnLabel) {
        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel);
        String columnId = js.getGridColIdByIndex(gridId, columnIndex);

        String columnSortType = gridSortJs.getColumnSortType(gridId, columnIndex);
        Boolean isSortColumn = gridSortJs.getIsSortColumn(gridId, columnId, 1L);

        Assert.assertTrue(!columnSortType.equals(COLUMN_SORT_TYPE_NA) && isSortColumn);
    }

    public void checkColumnSortAvailable(String columnLabel, String columnLabel2) {
        checkColumnSortAvailable(AbstractSeleniumCore.getGridIdx(), columnLabel, columnLabel2);
    }

    public void checkColumnSortAvailable(Long gridId, String columnLabel, String columnLabel2) {
        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel, columnLabel2);
        String columnId = js.getGridColIdByIndex(gridId, columnIndex);

        String columnSortType = gridSortJs.getColumnSortType(gridId, columnIndex);
        Boolean isSortColumn = gridSortJs.getIsSortColumn(gridId, columnId, 2L);

        Assert.assertTrue(!columnSortType.equals(COLUMN_SORT_TYPE_NA) && isSortColumn);
    }

    public void checkColumnSortNotAvailable(String columnLabel) {
        checkColumnSortNotAvailable(AbstractSeleniumCore.getGridIdx(), columnLabel);
    }

    public void checkColumnSortNotAvailable(Long gridId, String columnLabel) {
        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel);
        String columnId = js.getGridColIdByIndex(gridId, columnIndex);

        String columnSortType = gridSortJs.getColumnSortType(gridId, columnIndex);
        Boolean isSortColumn = gridSortJs.getIsSortColumn(gridId, columnId, 1L);

        Assert.assertFalse(!columnSortType.equals(COLUMN_SORT_TYPE_NA) && isSortColumn);
    }

    public void checkColumnSortNotAvailable(String columnLabel, String columnLabel2) {
        checkColumnSortNotAvailable(AbstractSeleniumCore.getGridIdx(), columnLabel, columnLabel2);
    }

    public void checkColumnSortNotAvailable(Long gridId, String columnLabel, String columnLabel2) {
        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel, columnLabel2);
        String columnId = js.getGridColIdByIndex(gridId, columnIndex);

        String columnSortType = gridSortJs.getColumnSortType(gridId, columnIndex);
        Boolean isSortColumn = gridSortJs.getIsSortColumn(gridId, columnId, 2L);

        Assert.assertFalse(!columnSortType.equals(COLUMN_SORT_TYPE_NA) && isSortColumn);
    }







    public void checkColumn(SortType sortType, GridColumnType gridColumnType, String columnLabel) {
        checkColumn(AbstractSeleniumCore.getGridIdx(), sortType, gridColumnType, columnLabel);
    }

    public void checkColumn(Long gridId, SortType sortType, GridColumnType gridColumnType, String columnLabel) {
        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel);

        Long rowsNum = js.getGridRowsCount(gridId);

        if (rowsNum <= 1L) {
            return; //Nothing to check
            //TODO may be log with error or throw exception?
        }

        Long curRowNum = 1L;
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) js.getGridCellsValuesForColumnByColIndex(gridId, rowsNum, columnIndex);
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
                    checkColumnNumber(curVal, prevVal, sortType, columnIndex, curRowNum);
                } else if (GridColumnType.TIMESTAMP.equals(gridColumnType)) {
                    checkColumnTimestamp(curVal, prevVal, sortType, columnIndex, curRowNum);
                } else if (GridColumnType.BOOLEAN.equals(gridColumnType)) {
                    checkColumnBoolean(curVal, prevVal, sortType, columnIndex, curRowNum);
                } else if (GridColumnType.TEXT.equals(gridColumnType)) {
                    //TODO check sort by text column not working. because oracle and java sort string by different logic
                } else if (GridColumnType.DATE.equals(gridColumnType)) {
                    checkColumnDate(curVal, prevVal, sortType, columnIndex, curRowNum);
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
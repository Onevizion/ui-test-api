package com.onevizion.uitest.api.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class GridHelper {

    @Resource
    private JsHelper jsHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private CheckboxHelper checkboxHelper;

    @Resource
    private ElementHelper elementHelper;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    public boolean isGridEmpty(Long gridId) {
        Long rowsCnt = jsHelper.getGridRowsCount(gridId);
        if (rowsCnt.equals(1L)) {
            String rowId = jsHelper.getGridSelectedRowId(gridId);
            if (rowId.equals("0")) {
                return true;
            }
        }

        return false;
    }

    public Long getGridRowsCount(Long gridId) {
        Long rowsCnt = jsHelper.getGridRowsCount(gridId);
        if (rowsCnt.equals(1L)) {
            String rowId = jsHelper.getGridSelectedRowId(gridId);
            if ("0".equals(rowId)) {
                return 0L;
            }
        }

        return rowsCnt;
    }

    public Long getTOGridRowsCount(Long gridId) {
        Double rowsCnt = jsHelper.getTOGridRowsCount(gridId);
        if (rowsCnt.equals(0.3333333333333333D)) {
            String rowId = jsHelper.getGridSelectedRowId(gridId);
            if ("0".equals(rowId)) {
                return 0L;
            }
        }

        return rowsCnt.longValue();
    }

    public void selectLastRow(Long gridId) {
        jsHelper.selectGridRow(gridId, getGridRowsCount(gridId) - 1L);
    }

    public void checkTbGridRowByRowIndex(Long gridId, Long rowIndex, Map<String, String> vals) {
        for (Entry<String, String> val : vals.entrySet()) {
            Long columnIndex = jsHelper.getGridColIndexById(gridId, val.getKey());
            //String columnType = jsHelper.getGridColumnType(gridId, columnIndex);
            //String value = jsHelper.getGridCellValueTxtByRowIndexAndColIndex(gridId, rowIndex, columnIndex);
            //if ("&nbsp;".equals(value)) {
            //    value = "";
            //}
            //value = value.replaceAll("^<[aA].*?>", "").replaceAll("</[aA]>$", "");
            //value = StringUtils.substringBefore(value, "\n");
            //if ("RichMemo".equals(columnType)) {
            //    value = value.replaceAll(AbstractSeleniumTest.SPECIAL_CHARACTERS_ENCODED, AbstractSeleniumTest.SPECIAL_CHARACTERS);
            //}
            //Assert.assertEquals(value, val.getValue(), "Check of grid row " + rowIndex + " is failed on " + columnIndex + " column.");

            waitHelper.waitGridCellTxtValue(gridId, columnIndex, rowIndex, val.getValue());
        }
    }

    public void checkGridRowByRowIndexAndColIndex(Long gridId, Long rowIndex, Map<Long, String> vals) {
        for (Entry<Long, String> val : vals.entrySet()) {
            String value = jsHelper.getGridCellValueByRowIndexAndColIndex(gridId, rowIndex, val.getKey());
            value = value.replaceAll("<[aA].*?>", "").replaceAll("</[aA]>", "");
            value = value.replaceAll("<[tT][aA][bB][lL][eE].*?>", "").replaceAll("</[tT][aA][bB][lL][eE]>", "");
            value = value.replaceAll("<[tT][bB][oO][dD][yY].*?>", "").replaceAll("</[tT][bB][oO][dD][yY]>", "");
            value = value.replaceAll("<[tT][rR].*?>", "").replaceAll("</[tT][rR]>", "");
            value = value.replaceAll("<[tT][dD].*?>", "").replaceAll("</[tT][dD]>", "");
            value = value.replaceAll("<[dD][iI][vV].*?>", "").replaceAll("</[dD][iI][vV]>", "");
            value = value.replaceAll("<[sS][vV][gG].*?>", "").replaceAll("</[sS][vV][gG]>", "");
            value = value.replaceAll("<[uU][sS][eE].*?>", "").replaceAll("</[uU][sS][eE]>", "");
            value = value.trim();
            value = StringUtils.substringBefore(value, "\n");
            Assert.assertEquals(value, val.getValue(), "Check of grid row " + rowIndex + " is failed on columnIndex=[" + val.getKey() + "] columnId=[" + val.getKey() + "].");
        }
    }

    public void checkGridRowByRowIndexAndWait(Long gridId, Long rowIndex, Map<String, String> vals) {
        for (Entry<String, String> val : vals.entrySet()) {
            Long columnIndex = jsHelper.getGridColIndexById(gridId, val.getKey());
            waitHelper.waitGridCellValue(gridId, columnIndex, rowIndex, val.getValue());
        }
    }

    public void checkGridRowByRowIndex(Long gridId, Long rowIndex, Map<String, String> vals) {
        for (Entry<String, String> val : vals.entrySet()) {
            Long columnIndex = jsHelper.getGridColIndexById(gridId, val.getKey());
            String value = jsHelper.getGridCellValueByRowIndexAndColIndex(gridId, rowIndex, columnIndex);
            value = value.replaceAll("^<[aA].*?>", "").replaceAll("</[aA]>$", "");
            value = StringUtils.substringBefore(value, "\n");
            Assert.assertEquals(value, val.getValue(), "Check of grid row " + rowIndex + " is failed on columnIndex=[" + columnIndex + "] columnId=[" + val.getKey() + "].");
        }
    }

    public void checkGridRowsByRowIndex(Long gridId, Long rowIndexStart, Long rowIndexEnd, Map<String, String> vals) {
        for (Long i = rowIndexStart; i <= rowIndexEnd; i++) {
            checkGridRowByRowIndex(gridId, i, vals);
        }
    }

    public void checkGridRowByRowId(Long gridId, String rowId, Map<String, String> vals) {
        for (Entry<String, String> val : vals.entrySet()) {
            Long columnIndex = jsHelper.getGridColIndexById(gridId, val.getKey());
            String value = jsHelper.getGridCellValueByRowIdAndColIndex(gridId, rowId, columnIndex);
            value = value.replaceAll("^<[aA].*?>", "").replaceAll("</[aA]>$", "");
            value = StringUtils.substringBefore(value, "\n");
            Assert.assertEquals(value, val.getValue(), "Check of grid row with id[" + rowId + "] is failed on columnIndex=[" + columnIndex + "] columnId=[" + val.getKey() + "].");
        }
    }

    public void deleteCurrentRow(Long gridId, Long parentGridId) {
        Long oldCnt = jsHelper.getGridRowsCount(gridId);
        if (oldCnt > 1) {
            oldCnt = oldCnt - 1L;
        }
        elementWaitHelper.waitElementEnabledById(AbstractSeleniumCore.BUTTON_DELETE_ID_BASE + gridId);
        elementHelper.click(seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_DELETE_ID_BASE + gridId)));
        waitHelper.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();
        waitHelper.waitGridLoad(gridId, parentGridId);
        Long newCnt = jsHelper.getGridRowsCount(gridId);
        Assert.assertEquals(newCnt, oldCnt, "Delete row is wrong");
    }

    public List<String> selectPrivilegieGridColumn(Long gridId, Long colIdx, Long firstRowIndex, Long lastRowIndex, String val) {
        List<String> ret = new ArrayList<String>();
        for (Long i = firstRowIndex; i <= lastRowIndex; i++) {
            jsHelper.selectGridCellByRowIndexAndColIndex(gridId, i, colIdx);
            new Select(seleniumSettings.getWebDriver().findElement(By.id("lbpriv"))).selectByVisibleText(val);
            ret.add(jsHelper.getGridSelectedRowId(gridId));
        }
        return ret;
    }

    public List<String> selectAssignmentGridColumn(Long gridId, Long colIdx, Long firstRowIndex, Long lastRowIndex) {
        List<String> ret = new ArrayList<String>();
        for (Long i = firstRowIndex; i <= lastRowIndex; i++) {
            jsHelper.selectGridCellByRowIndexAndColIndex(gridId, i, colIdx);
            WebElement element = (WebElement) jsHelper.getGridCellCheckboxByRowIndexAndColIndex(gridId, i, colIdx);
            checkboxHelper.clickByElement(element);
            ret.add(jsHelper.getGridSelectedRowId(gridId));
        }
        return ret;
    }

    public void selectAssignmentGridColumnNew(Long gridId, Long colIdxCheckbox, Long colIdxName, Map<String, String> values, String priv) {
        Long cnt = jsHelper.getGridRowsCount(gridId);
        boolean isGridEmpty = cnt.equals(1L) && jsHelper.getGridSelectedRowId(gridId).equals("0");

        if (!isGridEmpty) {
            @SuppressWarnings("unchecked")
            List<String> names = (List<String>) jsHelper.getGridCellsValuesForColumnByColIndexNew(gridId, cnt, colIdxName);
            for (Long i = 0L; i < cnt; i++) {
                if (values.containsKey(names.get(i.intValue())) /*&& priv.contains(values.get(names.get(i.intValue())))*/ && values.get(names.get(i.intValue())).contains(priv)) {
                    jsHelper.selectGridCellByRowIndexAndColIndex(gridId, i, colIdxCheckbox);
                    WebElement element = (WebElement) jsHelper.getGridCellCheckboxByRowIndexAndColIndex(gridId, i, colIdxCheckbox);
                    checkboxHelper.clickByElement(element);
                }
            }
        }
    }

    //TODO remove when getValue will be return 1 and 0 instead of html
    public List<String> selectAssignmentGridColumn2(Long gridId, Long colIdx, Long firstRowIndex, Long lastRowIndex) {
        List<String> ret = new ArrayList<String>();
        for (Long i = firstRowIndex; i <= lastRowIndex; i++) {
            jsHelper.selectGridCellByRowIndexAndColIndex(gridId, i, colIdx);
            WebElement element = (WebElement) jsHelper.getGridCellCheckboxByRowIndexAndColIndex(gridId, i, colIdx);
            checkboxHelper.clickByElement(element);
            ret.add("cb" + jsHelper.getGridSelectedRowId(gridId));
        }
        return ret;
    }

    public void selectAssignmentGridColumn2New(Long gridId, Long colIdxCheckbox, Long colIdxName, List<String> values) {
        Long cnt = jsHelper.getGridRowsCount(gridId);
        boolean isGridEmpty = cnt.equals(1L) && jsHelper.getGridSelectedRowId(gridId).equals("0");

        if (!isGridEmpty) {
            @SuppressWarnings("unchecked")
            List<String> names = (List<String>) jsHelper.getGridCellsValuesForColumnByColIndexNew(gridId, cnt, colIdxName);
            for (Long i = 0L; i < cnt; i++) {
                if (values.contains(names.get(i.intValue()))) {
                    jsHelper.selectGridCellByRowIndexAndColIndex(gridId, i, colIdxCheckbox);
                    WebElement element = (WebElement) jsHelper.getGridCellCheckboxByRowIndexAndColIndex(gridId, i, colIdxCheckbox);
                    checkboxHelper.clickByElement(element);
                }
            }
        }
    }

    public void clearPrivilegieGridColumn(Long gridId, Long colIdx) {
        Long cnt = jsHelper.getGridRowsCount(gridId);
        boolean isGridEmpty = cnt.equals(1L) && jsHelper.getGridSelectedRowId(gridId).equals("0");

        if (!isGridEmpty) {
            @SuppressWarnings("unchecked")
            List<String> vals = (List<String>) jsHelper.getGridCellsValuesForColumnByColIndex(gridId, cnt, colIdx);
            for (Long i = 0L; i < cnt; i++) {
                if (!"".equals(vals.get(i.intValue()))) {
                    jsHelper.selectGridCellByRowIndexAndColIndex(gridId, i, colIdx);
                    new Select(seleniumSettings.getWebDriver().findElement(By.id("lbpriv"))).selectByVisibleText("");
                }
            }
        }
    }

    public void clearAssignmentGridColumn(Long gridId, Long colIdx) {
        Long cnt = jsHelper.getGridRowsCount(gridId);
        boolean isGridEmpty = cnt.equals(1L) && jsHelper.getGridSelectedRowId(gridId).equals("0");

        if (!isGridEmpty) {
            @SuppressWarnings("unchecked")
            List<String> vals = (List<String>) jsHelper.getGridCellsValuesForColumnByColIndex(gridId, cnt, colIdx);
            for (Long i = 0L; i < cnt; i++) {
                if ("1".equals(vals.get(i.intValue()))) {
                    jsHelper.selectGridCellByRowIndexAndColIndex(gridId, i, colIdx);
                    WebElement element = (WebElement) jsHelper.getGridCellCheckboxByRowIndexAndColIndex(gridId, i, colIdx);
                    checkboxHelper.clickByElement(element);
                }
            }
        }
    }

    //TODO remove when getValue will be return 1 and 0 instead of html
    public void clearAssignmentGridColumn2(Long gridId, Long colIdx) {
        Long cnt = jsHelper.getGridRowsCount(gridId);
        boolean isGridEmpty = cnt.equals(1L) && jsHelper.getGridSelectedRowId(gridId).equals("0");

        if (!isGridEmpty) {
            List<WebElement> checkboxes = seleniumSettings.getWebDriver().findElements(By.name("cb" + gridId + "_" + colIdx));
            for (Long i = 0L; i < checkboxes.size(); i++) {
                if (checkboxes.get(i.intValue()).isSelected() && checkboxes.get(i.intValue()).isEnabled()) {
                    checkboxHelper.clickByElement(checkboxes.get(i.intValue()));
                }
            }
        }
    }

    public void checkPrivilegieGridColumn(Long gridId, Long colIdx, List<String> vals, String val) {
        Long cnt = jsHelper.getGridRowsCount(gridId);
        boolean isGridEmpty = cnt.equals(1L) && jsHelper.getGridSelectedRowId(gridId).equals("0");

        if (!isGridEmpty) {
            @SuppressWarnings("unchecked")
            List<String> gridVals = (List<String>) jsHelper.getGridCellsValuesForColumnByColIndex(gridId, cnt, colIdx);
            for (Long i = 0L; i < cnt; i++) {
                String rowId = jsHelper.getGridRowIdByIndex(gridId, i);
                if (vals.contains(rowId)) {
                    Assert.assertEquals(gridVals.get(i.intValue()), val, "Check priv for row id=[" + rowId + "] is failed");
                } else {
                    Assert.assertEquals(gridVals.get(i.intValue()), "", "Check priv for row id=[" + rowId + "] is failed");
                }
            }
        }
    }

    public void checkAssignmentGridColumn(Long gridId, Long colIdx, List<String> vals) {
        Long cnt = jsHelper.getGridRowsCount(gridId);
        boolean isGridEmpty = cnt.equals(1L) && jsHelper.getGridSelectedRowId(gridId).equals("0");

        if (!isGridEmpty) {
            @SuppressWarnings("unchecked")
            List<String> gridVals = (List<String>) jsHelper.getGridCellsValuesForColumnByColIndex(gridId, cnt, colIdx);
            for (Long i = 0L; i < cnt; i++) {
                String rowId = jsHelper.getGridRowIdByIndex(gridId, i);
                if (vals.contains(rowId)) {
                    Assert.assertEquals(gridVals.get(i.intValue()), "1", "Check priv for row id=[" + rowId + "] is failed");
                } else {
                    Assert.assertEquals(gridVals.get(i.intValue()), "0", "Check priv for row id=[" + rowId + "] is failed");
                }
            }
        }
    }

    public void checkAssignmentGridColumnNew(Long gridId, Long colIdxCheckbox, Long colIdxName, Map<String, String> values, String priv) {
        Long cnt = jsHelper.getGridRowsCount(gridId);
        boolean isGridEmpty = cnt.equals(1L) && jsHelper.getGridSelectedRowId(gridId).equals("0");

        if (!isGridEmpty) {
            @SuppressWarnings("unchecked")
            List<String> names = (List<String>) jsHelper.getGridCellsValuesForColumnByColIndexNew(gridId, cnt, colIdxName);
            @SuppressWarnings("unchecked")
            List<String> gridVals = (List<String>) jsHelper.getGridCellsValuesForColumnByColIndex(gridId, cnt, colIdxCheckbox);
            for (Long i = 0L; i < cnt; i++) {
                if (values.containsKey(names.get(i.intValue())) /*&& priv.contains(values.get(names.get(i.intValue())))*/ && values.get(names.get(i.intValue())).contains(priv)) {
                    Assert.assertEquals(gridVals.get(i.intValue()), "1", "Check priv for row=[" + names.get(i.intValue()) + "] is failed");
                } else {
                    Assert.assertEquals(gridVals.get(i.intValue()), "0", "Check priv for row=[" + names.get(i.intValue()) + "] is failed");
                }
            }
        }
    }

    //TODO remove when getValue will be return 1 and 0 instead of html
    public void checkAssignmentGridColumn2(Long gridId, Long colIdx, List<String> vals) {
        Long cnt = jsHelper.getGridRowsCount(gridId);
        boolean isGridEmpty = cnt.equals(1L) && jsHelper.getGridSelectedRowId(gridId).equals("0");

        if (!isGridEmpty) {
            List<WebElement> checkboxes = seleniumSettings.getWebDriver().findElements(By.name("cb" + gridId + "_" + colIdx));
            for (Long i = 0L; i < cnt; i++) {
                boolean selected = checkboxes.get(i.intValue()).isSelected();
                String id = checkboxes.get(i.intValue()).getAttribute("id");
                if (vals.contains(id)) {
                    Assert.assertEquals(selected, true, "Check checkbox with id=[" + id + "] is failed");
                } else {
                    Assert.assertEquals(selected, false, "Check checkbox with id=[" + id + "] is failed");
                }
            }
        }
    }

    public void checkAssignmentGridColumn2New(Long gridId, Long colIdxCheckbox, Long colIdxName, List<String> values) {
        Long cnt = jsHelper.getGridRowsCount(gridId);
        boolean isGridEmpty = cnt.equals(1L) && jsHelper.getGridSelectedRowId(gridId).equals("0");

        if (!isGridEmpty) {
            @SuppressWarnings("unchecked")
            List<String> names = (List<String>) jsHelper.getGridCellsValuesForColumnByColIndexNew(gridId, cnt, colIdxName);
            List<WebElement> checkboxes = seleniumSettings.getWebDriver().findElements(By.name("cb" + gridId + "_" + colIdxCheckbox));
            for (Long i = 0L; i < cnt; i++) {
                boolean selected = checkboxes.get(i.intValue()).isSelected();
                if (values.contains(names.get(i.intValue()))) {
                    Assert.assertEquals(selected, true, "Check checkbox with id=[" + names.get(i.intValue()) + "] is failed");
                } else {
                    Assert.assertEquals(selected, false, "Check checkbox with id=[" + names.get(i.intValue()) + "] is failed");
                }
            }
        }
    }

    public Long checkColumns(Long gridIndex, Long columnIndex, List<String> columnNames) {
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(0)); //CHECKBOX
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(1)); //DATE
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(2)); //DB_DROP_DOWN
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(3)); //DB_SELECTOR
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(4)); //DROP_DOWN
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(5).replaceAll("d_", "fNamed_").replaceAll("w_", "fNamew_")); //ELECTRONIC_FILE
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(5).replaceAll("d_", "fSized_").replaceAll("w_", "fSizew_")); //ELECTRONIC_FILE
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(5).replaceAll("d_", "fThumbd_").replaceAll("w_", "fThumbw_")); //ELECTRONIC_FILE
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(6)); //HYPERLINK
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(7)); //LATITUDE
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(8)); //LONGITUDE
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(9)); //MEMO
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(10)); //NUMBER
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(11)); //SELECTOR
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(12)); //TEXT
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(13)); //TRACKOR_SELECTOR
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(14)); //WIKI
        if (columnNames.get(15) != null) { //Multiple fields not support
            columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(15)); //MULTI_SELECTOR
        }
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(16)); //DATE_TIME
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(17)); //TIME
        columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(18)); //TRACKOR_DROPDOWN
        if (columnNames.get(19) != null) { //Multiple fields not support
            columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(19)); //CALCULATED
        }
        if (columnNames.get(20) != null) { //Multiple fields not support Workplan and Tasks trackor types not support
            columnIndex = checkColumnByIndex(gridIndex, columnIndex, columnNames.get(20)); //ROLLUP
        }

        return columnIndex;
    }

    public Long checkColumnByIndex(Long gridIndex, Long columnIndex, String columnIdx) {
        columnIndex = columnIndex + 1L;
        Assert.assertEquals(jsHelper.isGridColumnHidden(gridIndex, columnIndex).booleanValue(), false, "Grid have wrong columns");
        Assert.assertEquals(jsHelper.getGridColIdByIndex(gridIndex, columnIndex), columnIdx, "Grid have wrong columns");
        return columnIndex;
    }

    public Long checkColumnByName(Long gridIndex, Long columnIndex, String columnName) {
        columnIndex = columnIndex + 1L;
        Assert.assertEquals(jsHelper.isGridColumnHidden(gridIndex, columnIndex).booleanValue(), false, "Grid have wrong columns");
        Assert.assertEquals(jsHelper.getGridColumnLabelByColIndex(gridIndex, columnIndex, 0L), columnName, "Grid have wrong columns");
        return columnIndex;
    }

}
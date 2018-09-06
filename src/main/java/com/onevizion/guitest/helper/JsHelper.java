package com.onevizion.guitest.helper;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.AbstractSeleniumCore;
import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.exception.SeleniumUnexpectedException;

@Component
public class JsHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    protected String execJs(String script) {
        try {
            Object result = ((JavascriptExecutor) seleniumSettings.getWebDriver()).executeScript(script);
            if (result != null) {
                String res = result.toString();
                if (res.length() > 0) {
                    if ("'".equals(res.substring(0, 1)) || "\"".equals(res.substring(0, 1))) {
                        res = res.substring(1);
                    }
                    if ("'".equals(res.substring(res.length() - 1)) || "\"".equals(res.substring(res.length() - 1))) {
                        res = res.substring(0, res.length() - 1);
                    }
                }
                return res;
            } else {
                return null;
            }
        } catch (WebDriverException e) {
            throw new SeleniumUnexpectedException("Error while executing JavaScript with code: " + script, e);
        }
    }

    protected String execJs(AbstractSeleniumCore test, String script) {
        try {
            Object result = ((JavascriptExecutor) test.seleniumSettings.getWebDriver()).executeScript(script);
            if (result != null) {
                String res = result.toString();
                if (res.length() > 0) {
                    if ("'".equals(res.substring(0, 1)) || "\"".equals(res.substring(0, 1))) {
                        res = res.substring(1);
                    }
                    if ("'".equals(res.substring(res.length() - 1)) || "\"".equals(res.substring(res.length() - 1))) {
                        res = res.substring(0, res.length() - 1);
                    }
                }
                return res;
            } else {
                return null;
            }
        } catch (WebDriverException e) {
            throw new SeleniumUnexpectedException("Error while executing JavaScript with code: " + script, e);
        }
    }

    protected Object execJs2(String script) {
        try {
            return ((JavascriptExecutor) seleniumSettings.getWebDriver()).executeScript(script);
        } catch (WebDriverException e) {
            throw new SeleniumUnexpectedException("Error while executing JavaScript with code: " + script, e);
        }
    }

    protected void execJs3(String script, WebElement element) {
        try {
            ((JavascriptExecutor) seleniumSettings.getWebDriver()).executeScript(script, element);
        } catch (WebDriverException e) {
            throw new SeleniumUnexpectedException("Error while executing JavaScript with code: " + script, e);
        }
    }

    protected String execJs4(String script, WebElement element) {
        try {
            Object result = ((JavascriptExecutor) seleniumSettings.getWebDriver()).executeScript(script, element);
            if (result != null) {
                String res = result.toString();
                if (res.length() > 0) {
                    if ("'".equals(res.substring(0, 1)) || "\"".equals(res.substring(0, 1))) {
                        res = res.substring(1);
                    }
                    if ("'".equals(res.substring(res.length() - 1)) || "\"".equals(res.substring(res.length() - 1))) {
                        res = res.substring(0, res.length() - 1);
                    }
                }
                return res;
            } else {
                return null;
            }
        } catch (WebDriverException e) {
            throw new SeleniumUnexpectedException("Error while executing JavaScript with code: " + script, e);
        }
    }

    public Object getGridCellByRowIndexAndColIndex(Long gridId, Long rowIndex, Long columnIndex) {
        return execJs2("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell;");
    }

    // TODO remove trim
    public String getGridCellValueByRowIndexAndColIndex(Long gridId, Long rowIndex, Long columnIndex) {
        return execJs("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").getValue().trim();");
    }

    // TODO remove trim
    public String getGridCellValueByRowIndexAndColIndex2(WebElement element, Long rowIndex, Long columnIndex) {
        return execJs4("return arguments[0].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").getValue().trim();", element);
    }

    // TODO remove trim
    public String getGridCellValueTxtByRowIndexAndColIndex(Long gridId, Long rowIndex, Long columnIndex) {
        return execJs("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").getTxtValue().trim();");
    }

    public String getGridCellFontSizeByRowIndexAndColIndex(Long gridId, Long rowIndex, Long columnIndex) {
        return execJs("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.style.fontSize;");
    }

    // TODO remove trim
    public String getGridCellValueByRowIdAndColIndex(Long gridId, String rowId, Long colIndex) {
        return execJs("return gridArr[" + gridId + "].grid.cellById('" + rowId + "', " + colIndex + ").getValue().trim();");
    }

    public void selectGridRow(Long gridId, Long id) {
        execJs("gridArr[" + gridId + "].grid.selectRow(" + id + ", true, false, true);");
    }

    public void selectGridRowById(Long gridId, Long rowId) {
        execJs("gridArr[" + gridId + "].grid.selectRowById('" + rowId + "', false, true, true);");
    }

    public void gotoTab(String name, Long index) {
        execJs("document.getElementsByName('" + name + "')[" + index + "].click();");
    }

    public Long getGridCurTid(Long gridId) {
        return NumberUtils.createLong(execJs("return gridArr[" + gridId + "].curTid;"));
    }

    public Object getGridRowTids(Long gridId, String rowId) {
        return execJs2("var obj = getRowTrackors(gridArr[" + gridId + "], '" + rowId + "');"
                + "var tids = []; var i = 0;"
                + "for (key in obj) {"
                + "    tids[i] = obj[key];"
                + "    i++;"
                + "}"
                + "return tids;");
    }

    public Long getGridColIndexById(Long gridId, String columnId) {
        return NumberUtils.createLong(execJs("return gridArr[" + gridId + "].grid.getColIndexById('" + columnId + "');"));
    }

    public String getGridColIdByIndex(Long gridId, Long columnIdx) {
        return execJs("return gridArr[" + gridId + "].grid.getColumnId('" + columnIdx + "');");
    }

    public Long getGridRowsCount(Long gridId) {
        return NumberUtils.createLong(execJs("return gridArr[" + gridId + "].grid.getRowsNum();"));
    }

    public Double getTOGridRowsCount(Long gridId) {
        return NumberUtils.createDouble(execJs("return gridArr[" + gridId + "].grid.getRowsNum();"));
    }

    public Long getGridAllRowsCount(Long gridId) {
        return NumberUtils.createLong(StringUtils.defaultIfBlank(execJs("return gridArr[" + gridId + "].grid.getUserData(null, 'TotalRows');"), null));
    }

    public Long getGridColumnsCount(Long gridId) {
        return NumberUtils.createLong(execJs("return gridArr[" + gridId + "].grid.getColumnsNum();"));
    }

    public Boolean isGridColumnHidden(Long gridIdx, Long colIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].grid.isColumnHidden(" + colIdx + ");"));
    }

    public String getGridTtid(Long gridId) {
        return execJs("return gridArr[" + gridId + "].ttid;");
    }

    public String getGridColumnLabelByColIndex(Long gridId, Long columnIndex, Long headerRowIndex) {
        return execJs("return gridArr[" + gridId + "].grid.getColLabel(" + columnIndex + "," + headerRowIndex + ");");
    }
    
    public String getValueFromCodeMirror(String id) {
        return execJs("return sqlEditorArr['" + id + "'][1].getValue();");
    }

    public void setValueToCodeMirror(String id, String value) {
        execJs("sqlEditorArr['" + id + "'][1].setValue(\"" + value + "\");");
    }

    public String getGridSelectedRowId(Long gridId) {
        return execJs("return gridArr[" + gridId + "].grid.getSelectedRowId();");
    }

    public Long getGridSelectedRowIndex(Long gridId) {
        return NumberUtils.createLong(execJs("return gridArr[" + gridId + "].grid.getRowIndex('" + getGridSelectedRowId(gridId) + "');"));
    }

    public Long getGridScrollLeft(Long gridId, Long colIndex) {
        return NumberUtils.createLong(execJs("var scrollLeft = 0;"
                + "for (var i = 0; i < " + colIndex + "; i++) {"
                + "    scrollLeft = scrollLeft + gridArr[" + gridId + "].grid.cellByIndex(0, i).cell.offsetWidth;"
                + "}"
                + "return scrollLeft;"));
    }

    public Long getGridScrollTop(Long gridId, Long rowIndex) {
        return NumberUtils.createLong(execJs("var scrollTop = 0;"
                + "for (var i = 0; i < " + rowIndex + "; i++) {"
                + "    scrollTop = scrollTop + gridArr[" + gridId + "].grid.cellByIndex(i, 0).cell.offsetHeight;"
                + "}"
                + "return scrollTop;"));
    }

    // TODO remove trim
    public String getValueFromFCKEditor(String name) {
        return execJs("return CKEDITOR.instances." + name + ".getData().trim();");
    }

    public void setValueToFCKEditor(String name, String value) {
        execJs("CKEDITOR.instances." + name + ".setData('" + value + "');");
    }

    public void gridScrollLeft(Long gridId, Long left) {
        execJs("document.getElementById('gridbox" + gridId + "').getElementsByClassName('objbox')[0].scrollLeft = '" + left + "';");
    }

    public void gridFrozenScrollLeft(Long gridId, Long left) {
        execJs("document.getElementById('gridbox" + gridId + "').getElementsByClassName('objbox')[1].scrollLeft = '" + left + "';");
    }

    public void gridScrollTop(Long gridId, Long top) {
        execJs("document.getElementById('gridbox" + gridId + "').getElementsByClassName('objbox')[0].scrollTop = '" + top + "';");
    }

    public Long getNewDropDownScrollLeft(String id) {
        return NumberUtils.createLong(execJs("return ocument.getElementById('" + id + "').getElementsByClassName('scrollContainer')[0].scrollLeft;"));
    }

    public Long getNewDropDownScrollTop(String id) {
        return NumberUtils.createLong(execJs("return ocument.getElementById('" + id + "').getElementsByClassName('scrollContainer')[0].scrollTop;"));
    }

    public void scrollNewDropDownLeft(String id, Long left) {
        execJs("document.getElementById('" + id + "').getElementsByClassName('scrollContainer')[0].scrollLeft = '" + left + "';");
    }

    public void scrollNewDropDownTop(String id, String containerClass, Long top) {
        execJs("document.getElementById('" + id + "').getElementsByClassName('" + containerClass + "')[0].scrollTop = '" + top + "';");
    }

    public String isGridLoaded(Long gridId) {
        return execJs("return gridArr[" + gridId + "].PageLoaded;");
    }

    public Boolean isGridDataLoaded(Long gridId) {
        return Boolean.valueOf(execJs("return gridArr[" + gridId + "].gridDataLoaded;"));
    }

    public String isTreeLoaded(Long treeId) {
        return execJs("return treeArr[" + treeId + "].PageLoaded;");
    }

    public String isTreeLoaded(String treeId) {
        return execJs("return treeArr['" + treeId + "'].PageLoaded;");
    }

    public Boolean isCodeMirrorLoaded(String name) {
        return Boolean.valueOf(execJs("return sqlEditorArr.hasOwnProperty('" + name + "') && sqlEditorArr['" + name + "'][1].getTextArea() == document.getElementById('" + name + "');"));
    }
    
    public Long getCodeMirrorUndoSize(String name) {
        return NumberUtils.createLong(execJs("return sqlEditorArr['" + name + "'][1].historySize().undo;"));
    }
    
    public Long getCodeMirrorRedoSize(String name) {
        return NumberUtils.createLong(execJs("return sqlEditorArr['" + name + "'][1].historySize().redo;"));
    }

    public String getGridSortColumnIdByGridId(Long gridId) {
        return execJs("return gridArr[" + gridId + "].SortColumnIndx;");
    }

    public Boolean getGridIsSupportSortByGridId(Long gridId) {
        return Boolean.valueOf(execJs("return gridArr[" + gridId + "].isSupportSort();"));
    }

    public String getGridSortTypeByGridId(Long gridId) {
        return execJs("return gridArr[" + gridId + "].desc;");
    }

    public String getChildrenAttributeInGridCellByRowIndexAndColIndex(Long gridId, Long rowIndex, Long columnIndex, Long childrenIndex, String attributeName) {
        return execJs("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.children[" + childrenIndex + "].getAttribute('" + attributeName + "');");
    }

    public String getChildrenAttributeInGridCellByRowIndexAndColIndex(Long gridId, Long rowIndex, Long columnIndex, Long firstChildrenIndex, Long secondChildrenIndex, String attributeName) {
        return execJs("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.children[" + firstChildrenIndex + "].children[" + secondChildrenIndex + "].getAttribute('" + attributeName + "');");
    }

    public String selectGridCellByRowIndexAndColIndex(Long gridId, Long rowIndex, Long columnIndex) {
        return execJs("return gridArr[" + gridId + "].grid.selectCell(" + rowIndex + ", " + columnIndex + ", true, false, true);");
    }

    public String selectGridCellByRowIndexAndColIndex2(Long gridId, Long rowIndex, Long columnIndex) {
        return execJs("return gridArr[" + gridId + "].grid.selectCell(" + rowIndex + ", " + columnIndex + ", false, false, false, false);");
    }

    public String getGridColumnType(Long gridId, Long columnIndex) {
        return execJs("return gridArr[" + gridId + "].grid.getColType(" + columnIndex + ");");
    }

    public void openSubGrid(Long gridId, Long rowIndex, Long columnIndex) {
        execJs("gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.children[0].click();");
    }

    public void openSubGridByRowIdAndColumnIndex(Long gridId, Long rowId, Long columnIndex) {
        execJs("gridArr[" + gridId + "].grid.cellById(" + rowId + ", " + columnIndex + ").cell.children[0].click();");
    }

    public Object getChildrenInGridCellByRowIndexAndColIndex(Long gridId, Long rowIndex, Long columnIndex) {
        return execJs2("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.children[0];");
    }

    public Long getChildrensSizeInGridCellByRowIndexAndColIndex(Long gridId, Long rowIndex, Long columnIndex) {
        return NumberUtils.createLong(execJs("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.children.length;"));
    }

    public Long getRowsSizeInHtmlGrid(String gridName) {
        return NumberUtils.createLong(execJs("return document.getElementById('T2" + gridName + "').rows.length;"));
    }

    public Object getHtmlGridCellByRowIndexAndColIndex(String gridName, Long rowIndex, Long colIndex) {
        return execJs2("return document.getElementById('T2" + gridName + "').children[0].children[" + rowIndex + "].children[" + colIndex + "];");
    }

    public Object getChildrenInHtmlGridCellByRowIndexAndColIndex(String gridName, Long rowIndex, Long colIndex, Long childrenIndex) {
        return execJs2("return document.getElementById('T2" + gridName + "').children[0].children[" + rowIndex + "].children[" + colIndex + "].children[" + childrenIndex + "];");
    }

    public String getGridRowIdByIndex(Long gridId, Long rowIndex) {
        return execJs("return gridArr[" + gridId + "].grid.getRowId(" + rowIndex + ");");
    }

    public String getGridRowIds(Long gridId) {
        return execJs("return gridArr[" + gridId + "].grid.getAllRowIds();");
    }

    public Object getChildrenInMouthByRowIndexAndColIndex(Long mouthIndex, Long rowIndex, Long columnIndex) {
        return execJs2("return document.getElementById('dayList" + mouthIndex + "').children[" + rowIndex + "].children[" + columnIndex + "];");
    }

    public void showInputForFile(String inputId, String divId) {
        execJs("document.getElementById('" + divId + "').style.overflow = 'visible';"
                + "document.getElementById('" + inputId + "').style.opacity = 1;"
                + "document.getElementById('" + inputId + "').style.borderWidth = '0';"
                + "document.getElementById('" + inputId + "').style.transform = 'none';");
    }

    public void showInputForFile2(String inputId) {
        execJs("document.getElementById('" + inputId + "').style.visibility = 'visible';"
                + "document.getElementById('" + inputId + "').style.borderWidth = '0';"
                + "document.getElementById('" + inputId + "').style.transform = 'none';");
    }

    public void showInputForFileTb(String inputId) {
        execJs("document.getElementById('ifrmHideForm').parentNode.style.visibility = 'visible';"
                + "document.getElementById('ifrmHideForm').parentNode.style.zIndex = 1000000;"
                + "document.getElementById('ifrmHideForm').parentNode.style.top = '0px';"
                + "document.getElementById('ifrmHideForm').parentNode.style.left = '0px';"
                // + "document.getElementById('ifrmHideForm').parentNode.style.position = 'absolute';"
                + "document.getElementById('ifrmHideForm').contentWindow.document.getElementById('" + inputId + "').style.top = '0px';");
    }

    public void hideInputForFileTb(String inputId) {
        execJs("document.getElementById('ifrmHideForm').parentNode.style.visibility = 'hidden';"
                + "document.getElementById('ifrmHideForm').parentNode.style.zIndex = -100000;"
                + "document.getElementById('ifrmHideForm').parentNode.style.top = null;"
                + "document.getElementById('ifrmHideForm').parentNode.style.left = null;"
                // + "document.getElementById('ifrmHideForm').parentNode.style.position = null;"
                + "document.getElementById('ifrmHideForm').contentWindow.document.getElementById('" + inputId + "').style.top = '-100px';");
    }

    public Object getFrameForFileTbGrid(Long gridIndex) {
        return execJs2("var elems = document.getElementsByName('ifrmHideFormGrid" + gridIndex + "');"
                + "var elem = null;"
                + "for (var i = 0; i < elems.length; i++) {"
                + "    if (elems[i].contentWindow.document.getElementById('formg" + gridIndex + "') != null &&"
                + "            elems[i].contentWindow.document.getElementById('formg" + gridIndex + "') != undefined) {"
                + "        elem = elems[i];"
                + "    }"
                + "}"
                + "return elem;");
    }

    public Object showInputForFileTbGrid(Long gridIndex, String inputId) {
        return execJs2("var elems = document.getElementsByName('ifrmHideFormGrid0');"
                + "var elem = null;"
                + "for (var i = 0; i < elems.length; i++) {"
                + "    if (elems[i].contentWindow.document.getElementById('" + inputId + "') != null &&"
                + "            elems[i].contentWindow.document.getElementById('" + inputId + "') != undefined) {"
                + "        elem = elems[i];"
                + "    }"
                + "}"
                + "elem.parentNode.style.visibility = 'visible';"
                + "elem.parentNode.style.zIndex = 100000;"
                + "elem.parentNode.style.top = '0px';"
                + "elem.parentNode.style.left = '0px';"
                + "elem.parentNode.style.position = 'absolute';"
                + "elem.contentWindow.document.getElementById('formg" + gridIndex + "').style.display = 'inline';"
                + "elem.contentWindow.document.getElementById('" + inputId + "').style.top = '0px';"
                + "return elem;");
    }

    public void showInputForFileTbGrid2(Long gridIndex, WebElement element, String inputId) {
        execJs3("arguments[0].parentNode.style.visibility = 'visible';"
                + "arguments[0].parentNode.style.zIndex = 100000;"
                + "arguments[0].parentNode.style.top = '0px';"
                + "arguments[0].parentNode.style.left = '0px';"
                + "arguments[0].parentNode.style.position = 'absolute';"
                + "arguments[0].contentWindow.document.getElementById('formg" + gridIndex + "').style.display = 'inline';"
                + "arguments[0].contentWindow.document.getElementById('" + inputId + "').style.top = '0px';", element);
    }

    public void hideInputForFileTbGrid(Long gridIndex, String inputId) {
        execJs("var elems = document.getElementsByName('ifrmHideFormGrid" + gridIndex + "');"
                + "var elem = null;"
                + "for (var i = 0; i < elems.length; i++) {"
                + "    if (elems[i].contentWindow.document.getElementById('" + inputId + "') != null &&"
                + "            elems[i].contentWindow.document.getElementById('" + inputId + "') != undefined) {"
                + "        elem = elems[i];"
                + "    }"
                + "}"
                + "elem.parentNode.style.visibility = 'hidden';"
                + "elem.parentNode.style.zIndex = -100000;"
                + "elem.parentNode.style.top = null;"
                + "elem.parentNode.style.left = null;"
                + "elem.parentNode.style.position = null;"
                + "elem.contentWindow.document.getElementById('formg" + gridIndex + "').style.display = 'none';"
                + "elem.contentWindow.document.getElementById('" + inputId + "').style.top = '-100px';");
    }

    public void hideInputForFileTbGrid2(Long gridIndex, WebElement element, String inputId) {
        execJs3("arguments[0].parentNode.style.visibility = 'hidden';"
                + "arguments[0].parentNode.style.zIndex = -100000;"
                + "arguments[0].parentNode.style.top = null;"
                + "arguments[0].parentNode.style.left = null;"
                + "arguments[0].parentNode.style.position = null;"
                + "arguments[0].contentWindow.document.getElementById('formg" + gridIndex + "').style.display = 'none';"
                + "arguments[0].contentWindow.document.getElementById('" + inputId + "').style.top = '-100px';", element);
    }

    public void removeDivx() {
        execJs("if (window.document.getElementById('DIVx') != null) {window.document.body.removeChild(window.document.getElementById('DIVx'));}");
    }

    public String getWikiIdx(Long wikiFieldId, Long line) {
        return execJs("var i = 0;"
                + "for (var elem in elemsArr) {"
                + "    if (elemsArr[elem].cfid == '" + wikiFieldId + "') {"
                + "        i++;"
                + "        if (i == " + line + ") {"
                + "            return elemsArr[elem].idx;"
                + "        }"
                + "    }"
                + "}"
                + "return '';");
    }

    public Long getElemsArrLength() {
        return NumberUtils.createLong(execJs("return elemsArr.length;"));
    }

    public Object getGridCellsValuesForColumnByColIndex(Long gridId, Long rowsCnt, Long columnIndex) {
        return execJs2("var array = [];"
                + "for (var i = 0; i < " + rowsCnt + "; i++) {"
                + "    array[i] = gridArr[" + gridId + "].grid.cellByIndex(i, " + columnIndex + ").getValue();"
                + "}"
                + "return array;");
    }

    public Object getGridCellsValuesForColumnByColIndexNew(Long gridId, Long rowsCnt, Long columnIndex) {
        return execJs2("var array = [];"
                + "for (var i = 0; i < " + rowsCnt + "; i++) {"
                + "    array[i] = gridArr[" + gridId + "].grid.cellByIndex(i, " + columnIndex + ").getValue();"
                + "    array[i] = array[i].replace(/<\\/?[^>]+(>|$)/g, \"\");" //https://stackoverflow.com/questions/5002111/javascript-how-to-strip-html-tags-from-string
                + "}"
                + "return array;");
    }

    public Object getGridCellsValuesTxtForColumnByColIndex(Long gridId, Long rowsCnt, Long columnIndex) {
        return execJs2("var array = [];"
                + "for (var i = 0; i < " + rowsCnt + "; i++) {"
                + "    array[i] = gridArr[" + gridId + "].grid.cellByIndex(i, " + columnIndex + ").getTxtValue();"
                + "    array[i] = array[i].replace(/<\\/?[^>]+(>|$)/g, \"\");" //https://stackoverflow.com/questions/5002111/javascript-how-to-strip-html-tags-from-string
                + "}"
                + "return array;");
    }

    public Object getGridSort(Long gridId) {
        return execJs2("return gridArr[" + gridId + "].grid.getSortingState();");
    }

    public Boolean isWindowClosed() {
        return Boolean.valueOf(execJs("return wModal;"));
    }

    public Boolean isWindowClosed(AbstractSeleniumCore test) {
        return Boolean.valueOf(execJs(test, "return wModal;"));
    }

    public Boolean isDxtmlxWindowOpened(String windowName) {
        return Boolean.valueOf(execJs("return dhxWinsLog.isWindow('" + windowName + "');"));
    }

    public void closeDhtmlxWindow(String windowName) {
        execJs("dhxWinsLog.window('" + windowName + "').close();");
    }

    public Object getSelectedAndEnabledCheckboxes(Long gridId, Long sec3Idx) {
        return execJs2("var array = [];"
                + "var j = 0;"
                + "var elements = document.getElementsByName('cb" + gridId + "_" + sec3Idx + "');"
                + "for (var i = 0; i < elements.length; i++) {"
                + "    if (elements[i].checked && !elements[i].disabled) {"
                + "        array[j] = elements[i].id;"
                + "        j++;"
                + "    }"
                + "}"
                + "return array;");
    }

    public Object getSelectedCheckboxes(Long gridId, Long sec3Idx) {
        return execJs2("var array = [];"
                + "var j = 0;"
                + "var elements = document.getElementsByName('cb" + gridId + "_" + sec3Idx + "');"
                + "for (var i = 0; i < elements.length; i++) {"
                + "    if (elements[i].checked) {"
                + "        array[j] = elements[i].id;"
                + "        j++;"
                + "    }"
                + "}"
                + "return array;");
    }

    public Object getGridCellCheckboxByRowIndexAndColIndex(Long gridId, Long rowIndex, Long columnIndex) {
        return execJs2("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.children[0].children[0];");
    }

    public Object getCheckboxes(Long gridId, Long sec3Idx) {
        return execJs2("var array = [];"
                + "var j = 0;"
                + "var elements = document.getElementsByName('cb" + gridId + "_" + sec3Idx + "');"
                + "for (var i = 0; i < elements.length; i++) {"
                + "    array[j] = elements[i].id;"
                + "    j++;"
                + "}"
                + "return array;");
    }

    public Object getNewDropDownElement(String id, String containerClass, String containerItemClass, String text) {
        return execJs2("var elem = null;"
                + "var elements = document.getElementById('" + id + "').getElementsByClassName('" + containerClass + "')[0].getElementsByClassName('" + containerItemClass + "');"
                + "for (var i = 0; i < elements.length; i++) {"
                + "    if (elements[i].innerText == '" + text +"' || elements[i].textContent == '" + text +"') {"
                + "        elem = elements[i];"
                + "        break;"
                + "    }"
                + "}"
                + "return elem;");
    }

    public Object getNewDropDownElementNew(String id, String containerClass, String text) {
        return execJs2("var elem = null;"
                + "var elements = document.getElementById('" + id + "').getElementsByClassName('scrollContainer')[0].getElementsByClassName('" + containerClass + "');"
                + "for (var i = 0; i < elements.length; i++) {"
                + "    if (elements[i].innerText.trim() == '" + text +"' || elements[i].textContent.trim() == '" + text +"') {"
                + "        elem = elements[i];"
                + "        break;"
                + "    }"
                + "}"
                + "return elem;");
    }

    public Long getNewDropDownElementPosition(String id, String containerClass, String containerItemClass, String text) {
        return NumberUtils.createLong(execJs("var j = 0;"
                + "var elements = document.getElementById('" + id + "').getElementsByClassName('" + containerClass + "')[0].getElementsByClassName('" + containerItemClass + "');"
                + "for (var i = 0; i < elements.length; i++) {"
                + "    if (elements[i].innerText == '" + text +"' || elements[i].textContent == '" + text +"') {"
                + "        j = i;"
                + "        break;"
                + "    }"
                + "}"
                + "return j;"));
    }

    public Long getNewDropDownElementPositionNew(String id, String id2, String text) {
        return NumberUtils.createLong(execJs("var j = 0;"
                + "var elements = document.getElementById('" + id + "').getElementsByClassName('scrollContainer')[0].getElementsByClassName('" + id2 + "');"
                + "for (var i = 0; i < elements.length; i++) {"
                + "    if (elements[i].innerText.trim() == '" + text +"' || elements[i].textContent.trim() == '" + text +"') {"
                + "        j = i;"
                + "        break;"
                + "    }"
                + "}"
                + "return j;"));
    }

    @Deprecated
    public Object getElementsValuesFromDualListBox(String selectId) {
        return execJs2("var array = [];"
                + "var j = 0;"
                + "var elements = document.getElementById('" + selectId + "').getElementsByTagName('div');"
                + "for (var i = 0; i < elements.length; i++) {"
                + "    array[j] = elements[i].getAttribute('dhx_f_id');"
                + "    j++;"
                + "}"
                + "return array;");
    }

    public Object getElementsValuesFromDualListBoxNew(String selectId) {
        return execJs2("var array = [];"
                + "var j = 0;"
                + "var elements = document.getElementById('" + selectId + "').getElementsByClassName('listBoxContent')[0].getElementsByClassName('record');"
                + "for (var i = 0; i < elements.length; i++) {"
                + "    array[j] = elements[i].getAttribute('id');"
                + "    j++;"
                + "}"
                + "return array;");
    }

    public Object getChangedFieldsOnForm() {
        return execJs2("return PsUtils.getChangedCfs(elemsArr);");
    }

    public Object getChangedFieldsInGrid(Long gridIdx) {
        return execJs2("return getCfChanges(" + gridIdx + ");");
    }

    public WebElement getParentElement(WebElement element) {
        try {
            return (WebElement) ((JavascriptExecutor) seleniumSettings.getWebDriver()).executeScript("return arguments[0].parentNode;", element);
        } catch (WebDriverException e) {
            throw new SeleniumUnexpectedException("Error while executing JavaScript with code", e);
        }
    }

    public Boolean isDropGridVerificationFinish() {
        return Boolean.valueOf(execJs(
                  "$.get('/wizard/dropgrid/Verification/GetConfigItems.do?id=' + wizard.entityId, {}).done(function (source) {"
                + "    var configGroups = source.configGroups;"
                + "    for (var i = 0; i < configGroups.length; i++) {"
                + "        var items = configGroups[i].items;"
                + "        for (var j = 0; j < items.length; j++) {"
                + "            var objectStatus = items[j].objectStatus;"
                + "            if ((objectStatus != 2) && (objectStatus != 3) && (objectStatus != 4)) {"
                + "                return false;"
                + "            }"
                + "        }"
                + "    }"
                + "    return true;"
                + "});"
        ));
    }

    public Long getColumnIndexByLabel(Long gridId, String columnLabel) {
        columnLabel = columnLabel.replaceAll("'", "\\\\'");

        return NumberUtils.createLong(execJs(""
                + "var columnIdx;"
                + "var columnsCount = gridArr['" + gridId + "'].grid.getColumnsNum();"
                + "for (var i = 0; i < columnsCount; i++) {"
                + "    var columnLabel = gridArr['" + gridId + "'].grid.getColLabel(i);"
                + "    if ('" + columnLabel + "' == columnLabel) {"
                + "        columnIdx = i;"
                + "        break;"
                + "    }"
                + "}"
                + "return columnIdx;"));
    }

    public void resetFormChange() {
        //TODO firefox 59 bug
        //https://github.com/mozilla/geckodriver/issues/1067
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1420923
        execJs("if (typeof window.bFormChanged !== 'undefined') {window.bFormChanged = false;}");
    }

    public void resetFormChange(AbstractSeleniumCore test) {
        //TODO firefox 59 bug
        //https://github.com/mozilla/geckodriver/issues/1067
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1420923
        execJs(test, "if (typeof window.bFormChanged !== 'undefined') {window.bFormChanged = false;}");
    }

    public void resetGridChange() {
        execJs(""
                + "var buttons = document.getElementsByClassName('btnSaveChanges');"
                + "for (var i = 0; i < buttons.length; i++) {"
                + "    buttons[i].classList.remove('btnSaveChanges');"
                + "}");
    }

    public void resetGridChange(AbstractSeleniumCore test) {
        execJs(test, ""
                + "var buttons = document.getElementsByClassName('btnSaveChanges');"
                + "for (var i = 0; i < buttons.length; i++) {"
                + "    buttons[i].classList.remove('btnSaveChanges');"
                + "}");
    }

    public Boolean bplImportFileSubmitDone() {
        return Boolean.valueOf(execJs("return bplImportFileSubmitDone;"));
    }

    public Boolean getIsSubGrid(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].IsSubGrid;"));
    }

    public Long getParentGridIdx(Long gridIdx) {
        return NumberUtils.createLong(execJs("return gridArr[" + gridIdx + "].parentGridIdx;"));
    }

}
package com.onevizion.uitest.api.helper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumAlertException;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

@Component
public class Js {

    @Autowired
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
        } catch (UnhandledAlertException e) {
            throw new SeleniumAlertException("Error while executing JavaScript with code: " + script, e);
        } catch (WebDriverException e) {
            throw new SeleniumUnexpectedException("Error while executing JavaScript with code: " + script, e);
        }
    }

    protected Object execJs2(String script) {
        try {
            return ((JavascriptExecutor) seleniumSettings.getWebDriver()).executeScript(script);
        } catch (UnhandledAlertException e) {
            throw new SeleniumAlertException("Error while executing JavaScript with code: " + script, e);
        } catch (WebDriverException e) {
            throw new SeleniumUnexpectedException("Error while executing JavaScript with code: " + script, e);
        }
    }

    protected void execJs3(String script, WebElement element) {
        try {
            ((JavascriptExecutor) seleniumSettings.getWebDriver()).executeScript(script, element);
        } catch (UnhandledAlertException e) {
            throw new SeleniumAlertException("Error while executing JavaScript with code: " + script, e);
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
        } catch (UnhandledAlertException e) {
            throw new SeleniumAlertException("Error while executing JavaScript with code: " + script, e);
        } catch (WebDriverException e) {
            throw new SeleniumUnexpectedException("Error while executing JavaScript with code: " + script, e);
        }
    }

    public Object getGridCellByRowIndexAndColIndex(Long gridId, int rowIndex, int columnIndex) {
        return execJs2("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell;");
    }

    // TODO remove trim
    public String getGridCellValueByRowIndexAndColIndex(Long gridId, int rowIndex, int columnIndex) {
        return execJs("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").getValue().trim();");
    }

    // TODO remove trim
    public String getGridCellValueByRowIndexAndColIndex2(WebElement element, int rowIndex, int columnIndex) {
        return execJs4("return arguments[0].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").getValue().trim();", element);
    }

    // TODO remove trim
    public String getGridCellValueTxtByRowIndexAndColIndex(Long gridId, int rowIndex, int columnIndex) {
        return execJs("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").getTxtValue().trim();");
    }

    public String getGridCellFontSizeByRowIndexAndColIndex(Long gridId, int rowIndex, int columnIndex) {
        return execJs("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.style.fontSize;");
    }

    // TODO remove trim
    public String getGridCellValueByRowIdAndColIndex(Long gridId, String rowId, int colIndex) {
        return execJs("return gridArr[" + gridId + "].grid.cellById('" + rowId + "', " + colIndex + ").getValue().trim();");
    }

    public void selectGridRow(Long gridId, int rowIndex) {
        execJs("gridArr[" + gridId + "].grid.selectRow(" + rowIndex + ", true, false, true);");
    }

    public void selectGridRowById(Long gridId, Long rowId) {
        execJs("gridArr[" + gridId + "].grid.selectRowById('" + rowId + "', false, true, true);");
    }

    public String getGridColIdByIndex(Long gridId, int columnIdx) {
        return execJs("return gridArr[" + gridId + "].grid.getColumnId('" + columnIdx + "');");
    }

    public int getGridRowsCount(Long gridId) {
        return Integer.parseInt(execJs("return gridArr[" + gridId + "].grid.getRowsNum();"));
    }

    public Double getTOGridRowsCount(Long gridId) {
        return NumberUtils.createDouble(execJs("return gridArr[" + gridId + "].grid.getRowsNum();"));
    }

    public Long getGridAllRowsCount(Long gridId) {
        return NumberUtils.createLong(StringUtils.defaultIfBlank(execJs("return gridArr[" + gridId + "].grid.getUserData(null, 'TotalRows');"), null));
    }

    public int getGridColumnsCount(Long gridId) {
        return Integer.parseInt(execJs("return gridArr[" + gridId + "].grid.getColumnsNum();"));
    }

    public Boolean isGridColumnHidden(Long gridIdx, int colIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].grid.isColumnHidden(" + colIdx + ");"));
    }

    public String getGridTtid(Long gridId) {
        return execJs("return gridArr[" + gridId + "].ttid;");
    }

    public String getGridColumnLabelByColIndex(Long gridId, int columnIndex, int headerRowIndex) {
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

    public int getGridSelectedRowIndex(Long gridId) {
        return Integer.parseInt(execJs("return gridArr[" + gridId + "].grid.getRowIndex('" + getGridSelectedRowId(gridId) + "');"));
    }

    public Long getGridScrollLeft(Long gridId, int colIndex) {
        return NumberUtils.createLong(execJs("var scrollLeft = 0;"
                + "for (var i = 0; i < " + colIndex + "; i++) {"
                + "    scrollLeft = scrollLeft + gridArr[" + gridId + "].grid.cellByIndex(0, i).cell.offsetWidth;"
                + "}"
                + "return scrollLeft;"));
    }

    public Long getGridScrollTop(Long gridId, int rowIndex) {
        return NumberUtils.createLong(execJs("var columnIndex = 0;"
                + "var columnsCount = gridArr[" + gridId + "].grid.getColumnsNum();"
                + "for (var i = 0; i < columnsCount; i++) {"
                + "    var isHidden = gridArr[" + gridId + "].grid.isColumnHidden(i);"
                + "    if (!isHidden) {"
                + "        columnIndex = i;"
                + "        break;"
                + "    }"
                + "}"
                + ""
                + "var scrollTop = 0;"
                + "for (var i = 0; i < " + rowIndex + "; i++) {"
                + "    scrollTop = scrollTop + gridArr[" + gridId + "].grid.cellByIndex(i, columnIndex).cell.offsetHeight;"
                + "}"
                + "return scrollTop;"));
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

    public void scrollNewDropDownTop(WebElement element, String containerClass, Long top) {
        execJs3("return arguments[0].getElementsByClassName('" + containerClass + "')[0].scrollTop = '" + top + "';", element);
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

    public Boolean getGridIsSupportSortByGridId(Long gridId) {
        return Boolean.valueOf(execJs("return gridArr[" + gridId + "].isSupportSort();"));
    }

    public String getChildrenAttributeInGridCellByRowIndexAndColIndex(Long gridId, int rowIndex, int columnIndex, Long childrenIndex, String attributeName) {
        return execJs("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.children[" + childrenIndex + "].getAttribute('" + attributeName + "');");
    }

    public String getChildrenAttributeInGridCellByRowIndexAndColIndex(Long gridId, int rowIndex, int columnIndex, Long firstChildrenIndex, Long secondChildrenIndex, String attributeName) {
        return execJs("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.children[" + firstChildrenIndex + "].children[" + secondChildrenIndex + "].getAttribute('" + attributeName + "');");
    }

    public String selectGridCellByRowIndexAndColIndex(Long gridId, int rowIndex, int columnIndex) {
        return execJs("return gridArr[" + gridId + "].grid.selectCell(" + rowIndex + ", " + columnIndex + ", true, false, true);");
    }

    public String selectGridCellByRowIndexAndColIndex2(Long gridId, int rowIndex, int columnIndex) {
        return execJs("return gridArr[" + gridId + "].grid.selectCell(" + rowIndex + ", " + columnIndex + ", false, false, false, false);");
    }

    public String getGridColumnType(Long gridId, int columnIndex) {
        return execJs("return gridArr[" + gridId + "].grid.getColType(" + columnIndex + ");");
    }

    public void openSubGrid(Long gridId, int rowIndex, int columnIndex) {
        execJs("gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.children[0].click();");
    }

    public void openSubGridByRowIdAndColumnIndex(Long gridId, Long rowId, int columnIndex) {
        execJs("gridArr[" + gridId + "].grid.cellById(" + rowId + ", " + columnIndex + ").cell.children[0].click();");
    }

    public Object getChildrenInGridCellByRowIndexAndColIndex(Long gridId, int rowIndex, int columnIndex) {
        return execJs2("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.children[0];");
    }

    public Long getChildrensSizeInGridCellByRowIndexAndColIndex(Long gridId, int rowIndex, int columnIndex) {
        return NumberUtils.createLong(execJs("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.children.length;"));
    }

    public Long getRowsSizeInHtmlGrid(String gridName) {
        return NumberUtils.createLong(execJs("return document.getElementById('T2" + gridName + "').rows.length;"));
    }

    public Object getHtmlGridCellByRowIndexAndColIndex(String gridName, int rowIndex, int colIndex) {
        return execJs2("return document.getElementById('T2" + gridName + "').children[0].children[" + rowIndex + "].children[" + colIndex + "];");
    }

    public Object getChildrenInHtmlGridCellByRowIndexAndColIndex(String gridName, int rowIndex, int colIndex, Long childrenIndex) {
        return execJs2("return document.getElementById('T2" + gridName + "').children[0].children[" + rowIndex + "].children[" + colIndex + "].children[" + childrenIndex + "];");
    }

    public String getGridRowIdByIndex(Long gridId, int rowIndex) {
        return execJs("return gridArr[" + gridId + "].grid.getRowId(" + rowIndex + ");");
    }

    public String getGridRowIds(Long gridId) {
        return execJs("return gridArr[" + gridId + "].grid.getAllRowIds();");
    }

    public Object getChildrenInMouthByRowIndexAndColIndex(Long mouthIndex, int rowIndex, int columnIndex) {
        return execJs2("return document.getElementById('dayList" + mouthIndex + "').children[" + rowIndex + "].children[" + columnIndex + "];");
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

    public Object getGridCellsValuesForColumnByColIndex(Long gridId, int rowsCnt, int columnIndex) {
        return execJs2("var array = [];"
                + "for (var i = 0; i < " + rowsCnt + "; i++) {"
                + "    array[i] = gridArr[" + gridId + "].grid.cellByIndex(i, " + columnIndex + ").getValue();"
                + "}"
                + "return array;");
    }

    public Object getGridCellsValuesForColumnByColIndexNew(Long gridId, int rowsCnt, int columnIndex) {
        return execJs2("var array = [];"
                + "for (var i = 0; i < " + rowsCnt + "; i++) {"
                + "    array[i] = gridArr[" + gridId + "].grid.cellByIndex(i, " + columnIndex + ").getValue();"
                + "    array[i] = array[i].replace(/<\\/?[^>]+(>|$)/g, \"\");" //https://stackoverflow.com/questions/5002111/javascript-how-to-strip-html-tags-from-string
                + "}"
                + "return array;");
    }

    public Object getGridCellsValuesTxtForColumnByColIndex(Long gridId, int rowsCnt, int columnIndex) {
        return execJs2("var array = [];"
                + "for (var i = 0; i < " + rowsCnt + "; i++) {"
                + "    array[i] = gridArr[" + gridId + "].grid.cellByIndex(i, " + columnIndex + ").getTxtValue();"
                + "    array[i] = array[i].replace(/<\\/?[^>]+(>|$)/g, \"\");" //https://stackoverflow.com/questions/5002111/javascript-how-to-strip-html-tags-from-string
                + "}"
                + "return array;");
    }

    public Object getGridCellsValuesHTMLForColumnByColIndex(Long gridId, int rowsCnt, int columnIndex) {
        return execJs2("var array = [];"
                + "for (var i = 0; i < " + rowsCnt + "; i++) {"
                + "    array[i] = gridArr[" + gridId + "].grid.cellByIndex(i, " + columnIndex + ").cell.innerHTML;"
                + "}"
                + "return array;");
    }

    public Boolean isWindowClosed() {
        return Boolean.valueOf(execJs(""
                + "if (typeof wModal !== 'undefined') {"
                + "    return wModal;"
                + "} else {"
                + "    return true;"
                + "}"));
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

    public Object getGridCellCheckboxByRowIndexAndColIndex(Long gridId, int rowIndex, int columnIndex) {
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
        text = text.replaceAll("'", "\\\\'");

        return execJs2("var elem = null;"
                + "var allElements = document.getElementById('" + id + "').getElementsByClassName('" + containerClass + "')[0].getElementsByClassName('" + containerItemClass + "');"
                + "var visibleElements = [];"
                + "for (var i = 0; i < allElements.length; i++) {"
                + "    if (!allElements[i].className.includes('hidden')) {"
                + "        visibleElements.push(allElements[i]);"
                + "    }"
                + "}"
                + "for (var i = 0; i < visibleElements.length; i++) {"
                + "    if (visibleElements[i].innerText == '" + text +"' || visibleElements[i].textContent == '" + text +"') {"
                + "        elem = visibleElements[i];"
                + "        break;"
                + "    }"
                + "}"
                + "return elem;");
    }

    public Long getNewDropDownElementPosition(String id, String containerClass, String containerItemClass, String text) {
        text = text.replaceAll("'", "\\\\'");

        return NumberUtils.createLong(execJs("var j = 0;"
                + "var allElements = document.getElementById('" + id + "').getElementsByClassName('" + containerClass + "')[0].getElementsByClassName('" + containerItemClass + "');"
                + "var visibleElements = [];"
                + "for (var i = 0; i < allElements.length; i++) {"
                + "    if (!allElements[i].className.includes('hidden')) {"
                + "        visibleElements.push(allElements[i]);"
                + "    }"
                + "}"
                + "for (var i = 0; i < visibleElements.length; i++) {"
                + "    if (visibleElements[i].innerText == '" + text +"' || visibleElements[i].textContent == '" + text +"') {"
                + "        j = i;"
                + "        break;"
                + "    }"
                + "}"
                + "return j;"));
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
        } catch (UnhandledAlertException e) {
            throw new SeleniumAlertException("Error while executing JavaScript with code", e);
        } catch (WebDriverException e) {
            throw new SeleniumUnexpectedException("Error while executing JavaScript with code", e);
        }
    }

    public int getColumnIndexById(Long gridId, String columnId) {
        return Integer.parseInt(execJsColumnIndexById(gridId, columnId));
    }

    public boolean existColumnIndexById(Long gridId, String columnId) {
        return NumberUtils.isDigits(execJsColumnIndexById(gridId, columnId));
    }

    public String execJsColumnIndexById(Long gridId, String columnId) {
        return execJs("return gridArr[" + gridId + "].grid.getColIndexById('" + columnId + "');");
    }

    public int getColumnIndexByLabel(Long gridId, String columnLabel) {
        return Integer.parseInt(execJsColumnIndexByLabel(gridId, columnLabel));
    }

    public boolean existColumnIndexByLabel(Long gridId, String columnLabel) {
        return NumberUtils.isDigits(execJsColumnIndexByLabel(gridId, columnLabel));
    }

    private String execJsColumnIndexByLabel(Long gridId, String columnLabel) {
        columnLabel = columnLabel.replaceAll("'", "\\\\'");

        return execJs(""
                + "var columnIdx = null;"
                + "var columnsCount = gridArr['" + gridId + "'].grid.getColumnsNum();"
                + "for (var i = 0; i < columnsCount; i++) {"
                + "    var columnLabel = gridArr['" + gridId + "'].grid.getColLabel(i);"
                + "    if ('" + columnLabel + "' == columnLabel) {"
                + "        columnIdx = i;"
                + "        break;"
                + "    }"
                + "}"
                + "return columnIdx;");
    }

    public int getColumnIndexByLabel(Long gridId, String columnLabel, String columnLabel2) {
        return Integer.parseInt(execJsColumnIndexByLabel(gridId, columnLabel, columnLabel2));
    }

    public boolean existColumnIndexByLabel(Long gridId, String columnLabel, String columnLabel2) {
        return NumberUtils.isDigits(execJsColumnIndexByLabel(gridId, columnLabel, columnLabel2));
    }

    private String execJsColumnIndexByLabel(Long gridId, String columnLabel, String columnLabel2) {
        columnLabel = columnLabel.replaceAll("'", "\\\\'");

        return execJs(""
                + "var columnIdx = null;"
                + "var columnIdxStart = null;"
                + "var columnIdxFinish = null;"
                + "var columnsCount = gridArr['" + gridId + "'].grid.getColumnsNum();"
                + "for (var i = 0; i < columnsCount; i++) {"
                + "    var columnLabel1 = gridArr['" + gridId + "'].grid.getColLabel(i, 0);"
                + "    if ('" + columnLabel + "' == columnLabel1) {"
                + "        columnIdxStart = i;"
                + "    }"
                + "    if (columnIdxStart != null) {"
                + "        if ('" + columnLabel + "' != columnLabel1 && '' != columnLabel1) {"
                + "            columnIdxFinish = i;"
                + "            break;"
                + "        }"
                + "    }"
                + "    columnIdxFinish = i + 1;"
                + "}"
                + "if (columnIdxStart == null || columnIdxFinish == null) {"
                + "    return columnIdx;"
                + "}"
                + "for (var i = columnIdxStart; i < columnIdxFinish; i++) {"
                + "    var columnLabel2 = gridArr['" + gridId + "'].grid.getColLabel(i, 1);"
                + "    if ('" + columnLabel2 + "' == columnLabel2) {"
                + "        columnIdx = i;"
                + "        break;"
                + "    }"
                + "}"
                + "return columnIdx;");
    }

    @Deprecated
    public void setColumnLabel(Long gridId, int columnIndex, String columnLabel) {
        execJs("gridArr['" + gridId + "'].grid.setColLabel('" + columnIndex + "', '" + columnLabel + "');");
    }

    public Long getColumnFirstRowIndex(Long gridId, String columnLabel) {
        columnLabel = columnLabel.replaceAll("'", "\\\\'");

        return NumberUtils.createLong(execJs(""
                + "var columnIdx = 0;"
                + "var columnsCount = gridArr['" + gridId + "'].grid.getColumnsNum();"
                + "for (var i = 0; i < columnsCount; i++) {"
                + "    var columnLabel = gridArr['" + gridId + "'].grid.getColLabel(i);"
                + "    if ('' == columnLabel) {"
                + "        continue;"
                + "    }"
                + "    if ('" + columnLabel + "' == columnLabel) {"
                + "        break;"
                + "    }"
                + "    columnIdx = columnIdx + 1;"
                + "}"
                + "return columnIdx;"));
    }

    public Long getColumnSecondRowIndex(Long gridId, String columnLabel, String columnLabel2) {
        columnLabel = columnLabel.replaceAll("'", "\\\\'");

        return NumberUtils.createLong(execJs(""
                + "var columnIdx = 0;"
                + "var columnIdxStart = null;"
                + "var columnIdxFinish = null;"
                + "var columnsCount = gridArr['" + gridId + "'].grid.getColumnsNum();"
                + "for (var i = 0; i < columnsCount; i++) {"
                + "    var columnLabel1 = gridArr['" + gridId + "'].grid.getColLabel(i, 0);"
                + "    var columnLabel2 = gridArr['" + gridId + "'].grid.getColLabel(i, 1);"
                + "    if ('" + columnLabel + "' == columnLabel1) {"
                + "        columnIdxStart = i;"
                + "    }"
                + "    if (columnIdxStart == null) {"
                + "        if ('' != columnLabel2) {"
                + "            columnIdx = columnIdx + 1;"
                + "        }"
                + "    }"
                + "    if (columnIdxStart != null) {"
                + "        if ('" + columnLabel + "' != columnLabel1 && '' != columnLabel1) {"
                + "            columnIdxFinish = i;"
                + "            break;"
                + "        }"
                + "    }"
                + "    columnIdxFinish = i + 1;"
                + "}"
                + "for (var i = columnIdxStart; i < columnIdxFinish; i++) {"
                + "    var columnLabel2 = gridArr['" + gridId + "'].grid.getColLabel(i, 1);"
                + "    if ('" + columnLabel2 + "' == columnLabel2) {"
                + "        break;"
                + "    }"
                + "    columnIdx = columnIdx + 1;"
                + "}"
                + "return columnIdx;"));
    }

    public void resetFormChange() {
        execJs("if (typeof ov !== 'undefined' && typeof ov.bFormChanged !== 'undefined') {ov.bFormChanged = false;}");
    }

    public void resetCommentChange() {
        execJs("if (typeof ov !== 'undefined' && typeof ov.unsavedComment !== 'undefined') {ov.unsavedComment = false;}");
    }

    public void resetGridChange() {
        execJs("if (typeof gridArr !== 'undefined') {for (gridIdx in gridArr) {if (typeof gridArr[gridIdx].btnSave !== 'undefined') {gridArr[gridIdx].btnSave.state.isDisabled = true;}}}");
    }

    public Boolean bplImportFileSubmitDone() {
        return Boolean.valueOf(execJs("return bplImportFileSubmitDone;"));
    }

    public Boolean allImagesLoad() {
        return Boolean.valueOf(execJs(""
                + "var images = document.getElementsByTagName('IMG');"
                + "for (var i = 0; i < images.length; i++) {"
                + "    if (images[i].complete == false) {"
                + "        return false;"
                + "    }"
                + "}"
                + "return true;"));
    }

    public Long getDate() {
        return NumberUtils.createLong(execJs("return (new Date()).getTime();"));
    }

    public String getPageText() {
        return execJs("return document.documentElement.innerText;");
    }

    public int getToGridDatePairCount(Long gridIdx) {
        return Integer.parseInt(execJs("return gridArr[" + gridIdx + "].grid.getUserData(null, 'datesCnt');"));
    }

}
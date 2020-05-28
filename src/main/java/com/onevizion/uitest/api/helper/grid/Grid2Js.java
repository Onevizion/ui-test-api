package com.onevizion.uitest.api.helper.grid;

import java.util.List;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.LockType;

@Component
class Grid2Js extends Js {

    Boolean isUpdateDone(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].isUpdating == false;"));
    }

    Boolean isLoadAllRowsDone(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].grid.isLoadingParsing == false;"));
    }

    LockType getGridCellLockTypeByRowIndexAndColIndex(Long gridId, Long rowIndex, Long columnIndex) {
        String lockType = execJs("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.children[0].className;");
        return LockType.getByGridCellClass(lockType);
    }

    String getPageName(Long gridId) {
        return execJs("return gridArr[" + gridId + "].PageName;");
    }

    String getGridCellLastChildFontColor(Long gridIdx, Long rowIndex, Long columnIndex) {
        return execJs(
                "var lastChild = gridArr[" + gridIdx + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell;" + 
                "while (0 < lastChild.children.length) {" + 
                "    lastChild = lastChild.children[0];" + 
                "}" + 
                "return window.getComputedStyle(lastChild, null).getPropertyValue('color');");
    }

    //TODO need implement more correct solution
    String getGridCellTaskDateLastChildFontColor(Long gridIdx, Long rowIndex, Long columnIndex) {
        return execJs(
                "var lastChild = gridArr[" + gridIdx + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell;" + 
                "while (0 < lastChild.children.length) {" + 
                "    if (lastChild.children[0].outerHTML == '<div class=\"divBc2 hiddenContent\">BC</div>') {" + 
                "        break;" + 
                "    }" + 
                "    lastChild = lastChild.children[0];" + 
                "}" + 
                "return window.getComputedStyle(lastChild, null).getPropertyValue('color');");
    }

    @SuppressWarnings("unchecked")
    List<String> getGridCellAllChildsBackgroundColor(Long gridIdx, Long rowIndex, Long columnIndex) {
        return (List<String>) execJs2(
                "var colors = [];" + 
                "var element = gridArr[" + gridIdx + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell;" + 
                "colors.push(window.getComputedStyle(element, null).getPropertyValue('background-color'));" + 
                "while (0 < element.children.length) {" + 
                "    element = element.children[0];" + 
                "    colors.push(window.getComputedStyle(element, null).getPropertyValue('background-color'));" + 
                "}" + 
                "return colors;");
    }

}
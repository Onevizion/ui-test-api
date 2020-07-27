package com.onevizion.uitest.api.helper.grid;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.LockType;

@Component
class Grid2Js extends Js {

    Boolean getIsSubGrid(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].IsSubGrid;"));
    }

    Long getParentGridIdx(Long gridIdx) {
        return NumberUtils.createLong(execJs("return gridArr[" + gridIdx + "].parentGridIdx;"));
    }

    Boolean isGridLoaded(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].PageLoaded == 1;"));
    }

    Boolean isGridDataLoaded(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].gridDataLoaded == true;"));
    }

    Boolean isGridUpdated(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].isUpdating == false;"));
    }

    Boolean isLoadAllRowsDone(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].grid.isLoadingParsing == false;"));
    }

    LockType getGridCellLockTypeByRowIndexAndColIndex(Long gridIdx, Long rowIndex, Long columnIndex) {
        String lockType = execJs("return gridArr[" + gridIdx + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.children[0].className;");
        return LockType.getByGridCellClass(lockType);
    }

    String getPageName(Long gridIdx) {
        return execJs("return gridArr[" + gridIdx + "].PageName;");
    }

    @SuppressWarnings("unchecked")
    List<String> getGridCellAllChildsFontColor(Long gridIdx, Long rowIndex, Long columnIndex) {
        return (List<String>) execJs2(
                "var colors = [];" + 
                "var element = gridArr[" + gridIdx + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell;" + 
                "colors.push(window.getComputedStyle(element, null).getPropertyValue('color'));" + 
                "var childs = element.getElementsByTagName('*');" + 
                "for (var i = 0; i < childs.length; i++) {" + 
                "    colors.push(window.getComputedStyle(childs[i], null).getPropertyValue('color'));" + 
                "}" + 
                "return colors;");
    }

    @SuppressWarnings("unchecked")
    List<String> getGridCellAllChildsBackgroundColor(Long gridIdx, Long rowIndex, Long columnIndex) {
        return (List<String>) execJs2(
                "var colors = [];" + 
                "var element = gridArr[" + gridIdx + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell;" + 
                "colors.push(window.getComputedStyle(element, null).getPropertyValue('background-color'));" + 
                "var childs = element.getElementsByTagName('*');" + 
                "for (var i = 0; i < childs.length; i++) {" + 
                "    colors.push(window.getComputedStyle(childs[i], null).getPropertyValue('background-color'));" + 
                "}" + 
                "return colors;");
    }

}
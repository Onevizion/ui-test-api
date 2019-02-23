package com.onevizion.uitest.api.helper.grid.sort;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class GridSortJs extends Js {

    String getColumnSortType(Long gridIdx, Long columnIndex) {
        return execJs("return gridArr[" + gridIdx + "].grid.fldSort[" + columnIndex + "];");
    }

    Boolean getIsSortColumn(Long gridIdx, String columnId, Long rowIndex) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].grid.isSortColumn('" + columnId + "', " + rowIndex + ");"));
    }

    String getGridSortTypeByGridId(Long gridId) {
        return execJs("return gridArr[" + gridId + "].desc;");
    }

    String getGridSortColumnIdByGridId(Long gridId) {
        return execJs("return gridArr[" + gridId + "].SortColumnIndx;");
    }

    public Object getGridSort(Long gridId) {
        return execJs2("return gridArr[" + gridId + "].grid.getSortingState();");
    }

}
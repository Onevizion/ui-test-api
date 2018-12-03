package com.onevizion.uitest.api.helper.grid.sort;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class GridSortJs extends Js {

    String getColumnSortType(Long gridIdx, Long columnIndex) {
        return execJs("return gridArr[" + gridIdx + "].grid.fldSort[" + columnIndex + "];");
    }

}
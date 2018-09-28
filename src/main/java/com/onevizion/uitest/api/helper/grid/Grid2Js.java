package com.onevizion.uitest.api.helper.grid;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.LockType;

@Component
class Grid2Js extends Js {

    Boolean isLoadAllRowsDone(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].grid.isLoadingParsing == false;"));
    }

    LockType getGridCellLockTypeByRowIndexAndColIndex(Long gridId, Long rowIndex, Long columnIndex) {
        String lockType = execJs("return gridArr[" + gridId + "].grid.cellByIndex(" + rowIndex + ", " + columnIndex + ").cell.childNodes[0].className;");
        return LockType.getByGridCellClass(lockType);
    }

}
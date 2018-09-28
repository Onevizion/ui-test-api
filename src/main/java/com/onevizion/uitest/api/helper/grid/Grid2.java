package com.onevizion.uitest.api.helper.grid;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.vo.LockType;

@Component
public class Grid2 {

    @Resource
    private Grid2Wait grid2Wait;

    public void waitLoadAllRows(Long gridIdx) {
        grid2Wait.waitLoadAllRows(gridIdx);
    }

    public void waitGridCellLockType(Long gridId, Long columnIndex, Long rowIndex, LockType lockType) {
        grid2Wait.waitGridCellLockType(gridId, columnIndex, rowIndex, lockType);
    }

}
package com.onevizion.guitest.helper.grid;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class Grid2Helper {

    @Resource
    private Grid2WaitHelper grid2WaitHelper;

    public void waitLoadAllRows(Long gridIdx) {
        grid2WaitHelper.waitLoadAllRows(gridIdx);
    }

}
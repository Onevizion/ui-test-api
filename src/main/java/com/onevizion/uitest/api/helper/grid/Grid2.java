package com.onevizion.uitest.api.helper.grid;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class Grid2 {

    @Resource
    private Grid2Wait grid2Wait;

    public void waitLoadAllRows(Long gridIdx) {
        grid2Wait.waitLoadAllRows(gridIdx);
    }

}
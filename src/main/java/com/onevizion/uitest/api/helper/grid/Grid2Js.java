package com.onevizion.uitest.api.helper.grid;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class Grid2Js extends Js {

    Boolean isLoadAllRowsDone(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].grid.isLoadingParsing == false;"));
    }

}
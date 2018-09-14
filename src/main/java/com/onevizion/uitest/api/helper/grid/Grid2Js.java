package com.onevizion.uitest.api.helper.grid;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.JsHelper;

@Component
class Grid2Js extends JsHelper {

    Boolean isLoadAllRowsDone(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].grid.isLoadingParsing == false;"));
    }

}
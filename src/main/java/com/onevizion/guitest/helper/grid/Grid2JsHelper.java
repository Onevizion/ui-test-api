package com.onevizion.guitest.helper.grid;

import org.springframework.stereotype.Component;

import com.onevizion.guitest.helper.JsHelper;

@Component
class Grid2JsHelper extends JsHelper {

    Boolean isLoadAllRowsDone(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].grid.isLoadingParsing == false;"));
    }

}
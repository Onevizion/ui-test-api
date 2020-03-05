package com.onevizion.uitest.api.helper;

import org.springframework.stereotype.Component;

@Component
class ShowSqlJs extends Js {

    Boolean isUsageLogUpdated(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].isUsageLogUpdated == true;"));
    }

}
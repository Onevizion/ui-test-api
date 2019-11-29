package com.onevizion.uitest.api.helper;

import org.springframework.stereotype.Component;

@Component
public class ShowSqlJs extends Js {

    public Boolean isUsageLogUpdated(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].isUsageLogUpdated;"));
    }

}
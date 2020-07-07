package com.onevizion.uitest.api.helper.tab;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class TabJs extends Js {

    boolean isTabMenuHidden() {
        return Boolean.valueOf(execJs("return newGui.getHideFormMenuParam();"));
    }

}
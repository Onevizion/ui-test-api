package com.onevizion.uitest.api.helper.tab;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Js;

@Component
class TabJs extends Js {

    boolean isTabMenuHidden() {
        String tabMenuStatus = execJs("return newGui.getHideFormMenuParam();");

        if ("0".equals(tabMenuStatus)) {
            return false;
        } else if ("1".equals(tabMenuStatus)) {
            return true;
        } else {
            throw new SeleniumUnexpectedException("Not support tabMenuStatus[" + tabMenuStatus + "]");
        }
    }

}
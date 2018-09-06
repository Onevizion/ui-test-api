package com.onevizion.guitest.helper;

import org.springframework.stereotype.Component;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

@Component
public class TabJsHelper extends JsHelper {

    public boolean isTabMenuHidden() {
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
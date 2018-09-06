package com.onevizion.guitest.helper.colorpicker;

import org.springframework.stereotype.Component;

import com.onevizion.guitest.helper.JsHelper;

@Component
class ColorPickerJsHelper extends JsHelper {

    void setValue(String value) {
        //TODO firefox 59 bug
        //https://github.com/mozilla/geckodriver/issues/1067
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1420923
        execJs("window.z.setColor('" + value + "');");
    }

    Boolean isColorPickerExist() {
        //TODO firefox 59 bug
        //https://github.com/mozilla/geckodriver/issues/1067
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1420923
        return Boolean.valueOf(execJs("return typeof window.z !== 'undefined';"));
    }

}
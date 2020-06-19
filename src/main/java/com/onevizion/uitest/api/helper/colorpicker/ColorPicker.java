package com.onevizion.uitest.api.helper.colorpicker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ColorPicker {

    @Autowired
    private ColorPickerWait colorPickerWait;

    @Autowired
    private ColorPickerJs colorPickerJs;

    public void setValue(String value) {
        colorPickerWait.waitColorPicker();
        colorPickerJs.setValue(value);
    }

}
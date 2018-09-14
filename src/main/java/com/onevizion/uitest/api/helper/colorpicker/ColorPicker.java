package com.onevizion.uitest.api.helper.colorpicker;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class ColorPicker {

    @Resource
    private ColorPickerWait colorPickerWait;

    @Resource
    private ColorPickerJs colorPickerJs;

    public void setValue(String value) {
        colorPickerWait.waitColorPicker();
        colorPickerJs.setValue(value);
    }

}
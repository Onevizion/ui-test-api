package com.onevizion.uitest.api.helper.colorpicker;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class ColorPickerHelper {

    @Resource
    private ColorPickerWaitHelper colorPickerWaitHelper;

    @Resource
    private ColorPickerJsHelper colorPickerJsHelper;

    public void setValue(String value) {
        colorPickerWaitHelper.waitColorPicker();
        colorPickerJsHelper.setValue(value);
    }

}
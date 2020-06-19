package com.onevizion.uitest.api.helper.colorpicker;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class ColorPickerWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ColorPickerJs colorPickerJs;

    void waitColorPicker() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for ColorPicke is failed")
            .until(webdriver -> colorPickerJs.isColorPickerExist());
    }

}
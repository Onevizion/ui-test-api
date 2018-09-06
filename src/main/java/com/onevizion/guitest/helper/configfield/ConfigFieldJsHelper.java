package com.onevizion.guitest.helper.configfield;

import org.springframework.stereotype.Component;

import com.onevizion.guitest.helper.JsHelper;

@Component
class ConfigFieldJsHelper extends JsHelper {

    Boolean isFieldNameUpdated() {
        return Boolean.valueOf(execJs("return fieldNameUpdatedCount == 0;"));
    }

}
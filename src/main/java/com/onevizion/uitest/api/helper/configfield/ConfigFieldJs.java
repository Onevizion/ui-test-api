package com.onevizion.uitest.api.helper.configfield;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.JsHelper;

@Component
class ConfigFieldJs extends JsHelper {

    Boolean isFieldNameUpdated() {
        return Boolean.valueOf(execJs("return fieldNameUpdatedCount == 0;"));
    }

}
package com.onevizion.uitest.api.helper.configfield;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class ConfigFieldJs extends Js {

    Boolean isFieldNameUpdated() {
        return Boolean.valueOf(execJs("return fieldNameUpdatedCount == 0;"));
    }

}
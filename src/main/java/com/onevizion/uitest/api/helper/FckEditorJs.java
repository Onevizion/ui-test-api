package com.onevizion.uitest.api.helper;

import org.springframework.stereotype.Component;

@Component
class FckEditorJs extends Js {

    Boolean isReady(String name) {
        return Boolean.valueOf(execJs("return CKEDITOR.instances." + name + ".status == 'ready'"));
    }

}
package com.onevizion.uitest.api.helper.wiki;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class FckEditorJs extends Js {

    Boolean isReady(String name) {
        return Boolean.valueOf(execJs("return CKEDITOR.instances." + name + ".status == 'ready'"));
    }

}
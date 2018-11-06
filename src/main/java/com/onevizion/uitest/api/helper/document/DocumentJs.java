package com.onevizion.uitest.api.helper.document;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class DocumentJs extends Js {

    Boolean isReadyStateComplete() {
        return Boolean.valueOf(execJs("return document.readyState == 'complete'"));
    }

}
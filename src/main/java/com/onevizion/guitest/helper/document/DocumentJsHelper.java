package com.onevizion.guitest.helper.document;

import org.springframework.stereotype.Component;

import com.onevizion.guitest.helper.JsHelper;

@Component
class DocumentJsHelper extends JsHelper {

    Boolean isReadyStateComplete() {
        return Boolean.valueOf(execJs("return document.readyState").equals("complete"));
    }

}
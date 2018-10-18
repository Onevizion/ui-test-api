package com.onevizion.uitest.api.helper.html.input.file;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class HtmlInputFileJs extends Js {

    void showOnBplImport(String id) {
        execJs("document.getElementById('" + id + "').style.display = 'inline';");
    }

    void hideOnBplImport(String id) {
        execJs("document.getElementById('" + id + "').style.display = 'none';");
    }

}
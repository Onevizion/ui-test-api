package com.onevizion.uitest.api.helper.clipboard;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.JsHelper;

@Component
class ClipboardJsHelper extends JsHelper {

    void createTextareaForQs(String id) {
        execJs("var textArea = document.createElement('textarea');" + 
                "  textArea.setAttribute('id', '" + id + "');" + 
                "  textArea.style.position = 'absolute';" + 
                "  textArea.style.top = 0;" + 
                "  textArea.style.left = 0;" + 
                "  textArea.style.width = '100%';" + 
                "  textArea.style.height = '100%';" + 
                "  textArea.style.zIndex = 1000000;" + 
                "  document.body.appendChild(textArea);");
    }

    void deleteTextareaForQs(String id) {
        execJs("document.body.removeChild(document.getElementById('" + id + "'));");
    }

}
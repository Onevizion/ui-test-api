package com.onevizion.guitest.helper.export;

import org.springframework.stereotype.Component;

import com.onevizion.guitest.helper.JsHelper;

@Component
class ExportJsHelper extends JsHelper {

    void showNotification() {
        execJs("processEventNotification.showNotification();");
    }

}
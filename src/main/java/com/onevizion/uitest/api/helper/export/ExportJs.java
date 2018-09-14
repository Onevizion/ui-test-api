package com.onevizion.uitest.api.helper.export;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.JsHelper;

@Component
class ExportJs extends JsHelper {

    void showNotification() {
        execJs("processEventNotification.showNotification();");
    }

}
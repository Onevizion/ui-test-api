package com.onevizion.uitest.api.helper.export;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class ExportJs extends Js {

    void showNotification() {
        execJs("processEventNotification.showNotification();");
    }

}
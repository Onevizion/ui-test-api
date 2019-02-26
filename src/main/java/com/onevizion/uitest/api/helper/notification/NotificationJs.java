package com.onevizion.uitest.api.helper.notification;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class NotificationJs extends Js {

    Boolean isTreeLoaded() {
        return Boolean.valueOf(execJs("return treeLoaded;"));
    }

}
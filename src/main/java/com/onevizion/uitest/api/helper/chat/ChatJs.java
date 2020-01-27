package com.onevizion.uitest.api.helper.chat;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class ChatJs extends Js {

    Boolean isReadySubscribePanel() {
        return Boolean.valueOf(execJs("return isUsersLoaded == true;"));
    }

}
package com.onevizion.uitest.api.helper.chat;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class ChatJs extends Js {

    Boolean isReadySubscribePanel() {
        return Boolean.valueOf(execJs("return isUsersLoaded == true;"));
    }

    void scrollUp() {
        execJs3("document.getElementsByClassName('ch_list')[0].parentElement.parentElement.parentElement.scrollTop = 0;", null);
    }

    void scrollDown() {
        execJs3("document.getElementsByClassName('ch_list')[0].parentElement.parentElement.parentElement.scrollTop = document.getElementsByClassName('ch_list')[0].parentElement.parentElement.parentElement.scrollHeight", null);
    }

}
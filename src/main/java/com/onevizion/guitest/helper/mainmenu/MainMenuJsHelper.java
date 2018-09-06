package com.onevizion.guitest.helper.mainmenu;

import org.springframework.stereotype.Component;

import com.onevizion.guitest.helper.JsHelper;

@Component
class MainMenuJsHelper extends JsHelper {

    Boolean isLeftMenuSearchUpdated() {
        return Boolean.valueOf(execJs("return leftMenuSearchUpdatedCount == 0;"));
    }

}
package com.onevizion.uitest.api.helper.mainmenu;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.JsHelper;

@Component
class MainMenuJs extends JsHelper {

    Boolean isLeftMenuSearchUpdated() {
        return Boolean.valueOf(execJs("return leftMenuSearchUpdatedCount == 0;"));
    }

}
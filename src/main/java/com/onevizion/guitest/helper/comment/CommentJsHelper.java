package com.onevizion.guitest.helper.comment;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.helper.JsHelper;

@Component
class CommentJsHelper extends JsHelper {

    WebElement getIframe() {
        return (WebElement) execJs2("return commentWindow.window('comment').getFrame();");
    }

}
package com.onevizion.uitest.api.helper.comment;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.JsHelper;

@Component
class CommentJsHelper extends JsHelper {

    WebElement getIframe() {
        return (WebElement) execJs2("return commentWindow.window('comment').getFrame();");
    }

}
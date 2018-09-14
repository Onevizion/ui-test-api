package com.onevizion.uitest.api.helper.comment;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class CommentJs extends Js {

    WebElement getIframe() {
        return (WebElement) execJs2("return commentWindow.window('comment').getFrame();");
    }

}
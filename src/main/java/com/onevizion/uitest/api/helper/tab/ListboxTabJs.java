package com.onevizion.uitest.api.helper.tab;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class ListboxTabJs extends Js {

    void scrollToElementInListbox(WebElement webElement) {
        execJs3("arguments[0].parentNode.parentNode.parentNode.parentNode.scrollTop = arguments[0].offsetTop;", webElement);
    }

}
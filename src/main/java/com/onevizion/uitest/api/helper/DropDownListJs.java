package com.onevizion.uitest.api.helper;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
class DropDownListJs extends Js {

    void scrollToElementInDropDownList(WebElement webElement) {
        execJs3("arguments[0].parentNode.parentNode.parentNode.parentNode.scrollTop = arguments[0].offsetTop;", webElement);
    }

}
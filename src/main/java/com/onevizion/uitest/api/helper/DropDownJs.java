package com.onevizion.uitest.api.helper;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
class DropDownJs extends Js {

    void scrollToElementInDropDown(WebElement webElement) {
        execJs3("arguments[0].parentNode.parentNode.parentNode.parentNode.scrollTop = arguments[0].offsetTop;", webElement);
    }

}
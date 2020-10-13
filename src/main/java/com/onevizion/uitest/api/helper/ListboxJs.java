package com.onevizion.uitest.api.helper;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
class ListboxJs extends Js {

    void scrollToElementInListbox(WebElement webElement) {
        execJs3("arguments[0].parentNode.parentNode.scrollTop = arguments[0].offsetTop;", webElement);
    }

    void scrollToElementInListbox2(WebElement webElement) {
        execJs3("arguments[0].scrollIntoView({block: \"center\"});", webElement);
    }

    Boolean isReadyListbox(String listboxId) {
        return Boolean.valueOf(execJs("return " + listboxId + ".listBox.isReady == true;"));
    }

}
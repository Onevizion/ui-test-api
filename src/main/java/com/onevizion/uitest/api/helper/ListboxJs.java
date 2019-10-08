package com.onevizion.uitest.api.helper;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
class ListboxJs extends Js {

    void scrollToElementInListbox(WebElement webElement) {
        execJs3("arguments[0].parentNode.parentNode.scrollTop = arguments[0].offsetTop;", webElement);
    }

    Boolean isReadyListbox(String listboxId) {
        //TODO firefox 59 bug
        //https://github.com/mozilla/geckodriver/issues/1067
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1420923
        return Boolean.valueOf(execJs("return listboxArr['" + listboxId + "'].listBox.isReady == true;"));
    }

}
package com.onevizion.guitest.helper.jquery;

import org.springframework.stereotype.Component;

import com.onevizion.guitest.helper.JsHelper;

@Component
public class JqueryJsHelper extends JsHelper {

    public Boolean isJQueryNotActive() {
        //TODO firefox 59 bug
        //https://github.com/mozilla/geckodriver/issues/1067
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1420923
        return Boolean.valueOf(execJs("return window.jQuery.active == 0;"));
    }

    public Boolean isJqueryExist() {
        //TODO firefox 59 bug
        //https://github.com/mozilla/geckodriver/issues/1067
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1420923
        return Boolean.valueOf(execJs("return typeof window.jQuery !== 'undefined';"));
    }

}
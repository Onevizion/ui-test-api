package com.onevizion.uitest.api.helper.jquery;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class JqueryJs extends Js {

    Boolean isJQueryNotActive() {
        //TODO firefox 59 bug
        //https://github.com/mozilla/geckodriver/issues/1067
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1420923

        return Boolean.valueOf(execJs(""
                + "if (typeof window.jQuery !== 'undefined') {"
                + "    return window.jQuery.active == 0;"
                + "} else {"
                + "    return false;"
                + "}"));
    }

}
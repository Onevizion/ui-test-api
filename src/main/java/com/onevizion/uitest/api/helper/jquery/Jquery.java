package com.onevizion.uitest.api.helper.jquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Jquery {

    @Autowired
    private JqueryWait jqueryWait;

    public void waitLoad() {
        jqueryWait.waitJQueryLoad();
    }

}
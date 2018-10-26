package com.onevizion.uitest.api.helper.jquery;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class Jquery {

    @Resource
    private JqueryWait jqueryWait;

    public void waitLoad() {
        jqueryWait.waitJQueryLoad();
    }

}
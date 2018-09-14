package com.onevizion.uitest.api.helper.configfield;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class ConfigField {

    @Resource
    private ConfigFieldWait configFieldWait;

    public void waitFieldNameUpdated() {
        configFieldWait.waitFieldNameUpdated();
    }

}
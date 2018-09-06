package com.onevizion.guitest.helper.configfield;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class ConfigFieldHelper {

    @Resource
    private ConfigFieldWaitHelper configFieldWaitHelper;

    public void waitFieldNameUpdated() {
        configFieldWaitHelper.waitFieldNameUpdated();
    }

}
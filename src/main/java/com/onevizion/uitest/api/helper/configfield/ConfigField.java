package com.onevizion.uitest.api.helper.configfield;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigField {

    @Autowired
    private ConfigFieldWait configFieldWait;

    public void waitFieldNameUpdated() {
        configFieldWait.waitFieldNameUpdated();
    }

}
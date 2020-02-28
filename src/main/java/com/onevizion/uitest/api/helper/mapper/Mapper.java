package com.onevizion.uitest.api.helper.mapper;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.ElementWait;

@Component
public class Mapper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementWait elementWait;

    @Resource
    protected MapperWait mapperWait;

    public void loadMap() {
        elementWait.waitElementNotDisplayById("processing");
        mapperWait.waitIsMapLoaded();
    }

}
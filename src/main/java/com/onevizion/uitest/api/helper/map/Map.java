package com.onevizion.uitest.api.helper.map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.ElementWait;

@Component
public class Map {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementWait elementWait;

    @Resource
    private MapWait mapWait;

    public void waitLoad() {
        elementWait.waitElementNotDisplayById("processing");
        mapWait.waitIsMapLoaded();
    }

}
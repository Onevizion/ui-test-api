package com.onevizion.uitest.api.helper.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.ElementWait;

@Component
public class Map {

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private MapWait mapWait;

    public void waitLoad() {
        elementWait.waitElementNotDisplayById("processing");
        mapWait.waitIsMapLoaded();
    }

}
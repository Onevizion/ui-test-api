package com.onevizion.uitest.api.helper.gantt;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;

@Component
public class Gantt {

    public void waitLoad() {
        //TODO need create correct code after JS will be changed
        //at this time it is low priority
        //need create dev request for this in future
        //gantt redrawn many times in some cases
        //gantt redrawn when grid loading already hided
        AbstractSeleniumCore.sleep(1000L);
    }

}
package com.onevizion.uitest.api.helper.mapper;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
public class MapperJs extends Js {

    Boolean isMapLoaded() {
        return Boolean.valueOf(execJs("return isMapIdle == true;"));
    }

}
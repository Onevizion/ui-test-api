package com.onevizion.guitest.helper.document;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class DocumentHelper {

    @Resource
    private DocumentWaitHelper documentWaitHelper;

    public void waitReadyStateComplete() {
        documentWaitHelper.waitReadyStateComplete();
    }

}
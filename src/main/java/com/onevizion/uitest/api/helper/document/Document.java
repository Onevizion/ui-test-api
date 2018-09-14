package com.onevizion.uitest.api.helper.document;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class Document {

    @Resource
    private DocumentWait documentWait;

    public void waitReadyStateComplete() {
        documentWait.waitReadyStateComplete();
    }

}
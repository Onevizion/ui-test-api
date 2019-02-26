package com.onevizion.uitest.api.helper.notification;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class Notification {

    @Resource
    private NotificationWait notificationWait;

    public void waitTreeLoad() {
        notificationWait.waitTreeLoad();
    }

}
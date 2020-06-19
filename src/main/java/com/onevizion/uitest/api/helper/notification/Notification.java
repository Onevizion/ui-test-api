package com.onevizion.uitest.api.helper.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Notification {

    @Autowired
    private NotificationWait notificationWait;

    public void waitTreeLoad() {
        notificationWait.waitTreeLoad();
    }

}
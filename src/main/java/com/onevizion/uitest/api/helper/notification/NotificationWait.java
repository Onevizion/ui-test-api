package com.onevizion.uitest.api.helper.notification;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class NotificationWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private NotificationJs notificationJs;

    void waitTreeLoad() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for treeLoaded is failed.")
            .until(webdriver -> notificationJs.isTreeLoaded());
    }

}
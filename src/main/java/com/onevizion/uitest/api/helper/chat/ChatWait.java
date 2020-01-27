package com.onevizion.uitest.api.helper.chat;

import javax.annotation.Resource;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class ChatWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ChatJs chatJs;

    void waitIsReadySubscribePanel() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Subscribe Panel loading is failed")
            .until(webdriver -> chatJs.isReadySubscribePanel());
    }

}
package com.onevizion.uitest.api.helper.chat;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class ChatWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ChatJs chatJs;

    @Autowired
    private Chat chat;

    void waitIsReadySubscribePanel() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Subscribe Panel loading is failed")
            .until(webdriver -> chatJs.isReadySubscribePanel());
    }

    void waitSubscribedUsersCountOnForm(int subscribedUsersCount) {
        IntSupplier actualValueSupplier = ()-> chat.getSubscribedUsersCountOnForm();

        Supplier<String> messageSupplier = ()-> "Waiting subscribed users count expectedVal=[" + subscribedUsersCount + "] actualVal=[" + actualValueSupplier.getAsInt() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> subscribedUsersCount == actualValueSupplier.getAsInt());
    }

    void waitSubscribedUsersCountInGrid(int subscribedUsersCount) {
        IntSupplier actualValueSupplier = ()-> chat.getSubscribedUsersCountInGrid();

        Supplier<String> messageSupplier = ()-> "Waiting subscribed users count expectedVal=[" + subscribedUsersCount + "] actualVal=[" + actualValueSupplier.getAsInt() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> subscribedUsersCount == actualValueSupplier.getAsInt());
    }

}
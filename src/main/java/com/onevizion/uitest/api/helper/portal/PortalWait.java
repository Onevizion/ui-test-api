package com.onevizion.uitest.api.helper.portal;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class PortalWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private PortalJs portalJs;

    void waitPortal(String name) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for portal loading is failed")
            .until(webdriver -> portalJs.isPortalExists(name));
    }

    void waitFramesCount(int framesCount) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for portal frames count is failed")
            .until(webdriver -> {
                int size = webdriver.findElements(By.className("dhx_cell_layout")).size();
                return size == framesCount;
            });
    }

    void waitFrame(int frameNumber) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
        .withMessage("Waiting for portal frame loading is failed")
        .until(webdriver -> webdriver.findElements(By.className("dhx_cell_layout")).get(frameNumber).findElement(By.tagName("iframe")));
    }

}
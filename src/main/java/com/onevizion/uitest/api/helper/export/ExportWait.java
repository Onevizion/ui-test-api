package com.onevizion.uitest.api.helper.export;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class ExportWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    void waitExport() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for export is failed")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> {
                WebElement panel = webdriver.findElement(By.id("processEventList"));
                List<WebElement> processes = panel.findElements(By.className("group_event"));
                return !processes.isEmpty();
            });
    }

    void waitExportDone() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for export done is failed")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> {
                WebElement panel = webdriver.findElement(By.id("processEventList"));
                List<WebElement> statuses = panel.findElements(By.className("ie_status"));
                for (WebElement status : statuses) {
                    if ("Executed without Errors".equals(status.getAttribute("textContent"))) {
                        return true;
                    }
                }
                return false;
            });
    }

}
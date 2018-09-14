package com.onevizion.uitest.api.helper.export;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class ExportWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    void waitExport() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Element name=[" + null + "] is failed")
            .ignoring(StaleElementReferenceException.class)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    WebElement panel = seleniumSettings.getWebDriver().findElement(By.id("processEventList"));

                    List<WebElement> processes = panel.findElements(By.className("group_event"));
                    if (processes.size() > 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
    }

    void waitExportDone() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Element name=[" + null + "] is failed")
            .ignoring(StaleElementReferenceException.class)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    boolean isDisplayed = seleniumSettings.getWebDriver().findElement(By.id("processStatusNotification")).isDisplayed();
                    if (isDisplayed) {
                        seleniumSettings.getWebDriver().findElement(By.id("closeNotification")).click();
                    }

                    isDisplayed = seleniumSettings.getWebDriver().findElement(By.id("processEventList")).isDisplayed();
                    if (!isDisplayed) {
                        WebElement processListButton = seleniumSettings.getWebDriver().findElement(By.id("topPanelProcessContainer")).findElement(By.className("btn_input"));
                        processListButton.click();
                    }

                    WebElement panel = seleniumSettings.getWebDriver().findElement(By.id("processEventList"));
                    List<WebElement> statuses = panel.findElements(By.className("ie_status"));
                    for (WebElement status : statuses) {
                        if ("Executed without Errors".equals(status.getText())) {
                            return true;
                        }
                    }
                    return false;
                }
            });
    }

}
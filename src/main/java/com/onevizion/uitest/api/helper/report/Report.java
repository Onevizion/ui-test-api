package com.onevizion.uitest.api.helper.report;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Alert;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementWait;

@Component
public class Report {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Element element;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private ReportWait reportWait;

    @Autowired
    private Alert alert;

    public String waitReportDone() {
        WebElement processListButton = seleniumSettings.getWebDriver().findElement(By.id("topPanelProcessContainer")).findElement(By.className("btn_input"));
        processListButton.click();

        reportWait.waitReport();

        reportWait.waitReportDone();

        WebElement panel = seleniumSettings.getWebDriver().findElement(By.id("processEventList"));
        WebElement link = panel.findElement(By.className("ge_link"));
        String processId = link.getAttribute("onclick").replace("showRepDetails(", "").replace(")", "");

        element.moveToElementById("drop_down_overflow_" + processId);

        elementWait.waitElementById("ge_delete_" + processId);
        elementWait.waitElementVisibleById("ge_delete_" + processId);
        elementWait.waitElementDisplayById("ge_delete_" + processId);

        seleniumSettings.getWebDriver().findElement(By.id("ge_delete_" + processId)).click();
        alert.accept();
        processListButton.click();

        return processId;
    }

}
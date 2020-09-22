package com.onevizion.uitest.api.helper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

@Component
public class NewNewDropDown {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ElementWait elementWait;

    public void selectDashboard(String name) {
        selectEntity("dropDownDashboards", name);
    }

    public void selectPortal(String name) {
        selectEntity("dropDownPortals", name);
    }

    private void selectEntity(String id, String name) {
        WebElement currentPortal = seleniumSettings.getWebDriver().findElement(By.id(id)).findElement(By.className("dds_label"));
        String currentPortalName = currentPortal.getText();
        if (name.equals(currentPortalName)) {
            return;
        }

        seleniumSettings.getWebDriver().findElement(By.id(id)).findElement(By.className("dds_label")).click();
        //elementWait.waitElementById("dd_content_" + id);//TODO
        elementWait.waitElementVisible(seleniumSettings.getWebDriver().findElement(By.id(id)).findElement(By.className("dd_content")));
        elementWait.waitElementDisplay(seleniumSettings.getWebDriver().findElement(By.id(id)).findElement(By.className("dd_content")));

        seleniumSettings.getWebDriver().findElement(By.id("search_" + id)).clear();
        seleniumSettings.getWebDriver().findElement(By.id("search_" + id)).sendKeys(name);

        WebElement dropDownItem = null;
        List<WebElement> items = seleniumSettings.getWebDriver().findElement(By.id(id)).findElements(By.className("drop_down_item"));
        for (WebElement item : items) {
            if (name.equals(item.findElement(By.className("ddi_label")).getAttribute("textContent"))) {
                if (dropDownItem != null) {
                    throw new SeleniumUnexpectedException("DropDown [" + name + "] found many times");
                }
                dropDownItem = item;
            }
        }
        if (dropDownItem == null) {
            throw new SeleniumUnexpectedException("DropDown [" + name + "] not found");
        }
        dropDownItem.click();
    }

}
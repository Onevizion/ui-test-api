package com.onevizion.uitest.api.helper.dropgrid;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.ElementWait;

@Component
public class DropGrid {

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private SeleniumSettings seleniumSettings;

    public void checkDropGridGroupCount(int expectedCount) {
        int actualCount = seleniumSettings.getWebDriver().findElements(By.name("dgIconDiv")).size();
        Assert.assertEquals(actualCount, expectedCount);
    }

    public void waitDropGridGroupComplete(String groupId) {
        elementWait.waitElementAttributeById("dgIconDiv" + groupId, "status", "completed");
    }

}
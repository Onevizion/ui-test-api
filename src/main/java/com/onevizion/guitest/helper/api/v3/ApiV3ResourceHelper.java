package com.onevizion.guitest.helper.api.v3;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.exception.SeleniumUnexpectedException;
import com.onevizion.guitest.helper.ElementHelper;
import com.onevizion.guitest.helper.ElementWaitHelper;
import com.onevizion.guitest.vo.ApiV3ResourceType;

@Component
public class ApiV3ResourceHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementHelper elementHelper;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    public int getResourcesCount() {
        List<WebElement> resources = seleniumSettings.getWebDriver().findElements(By.className("resource"));
        return resources.size();
    }

    public WebElement findResource(ApiV3ResourceType apiV3ResourceType) {
        WebElement ret = null;
        int count = 0;

        List<WebElement> resources = seleniumSettings.getWebDriver().findElements(By.className("resource"));
        for (WebElement resource : resources) {
            elementHelper.moveToElement(resource);
            String actualName = resource.findElement(By.tagName("h2")).getText();

            if (apiV3ResourceType.getName().equals(actualName)) {
                count = count + 1;
                ret = resource;
            }
        }

        if (count == 0) {
            throw new SeleniumUnexpectedException("Resource with name[" + apiV3ResourceType.getName() + "] not found");
        } else if (count > 1) {
            throw new SeleniumUnexpectedException("Endpoint with name[" + apiV3ResourceType.getName() + "] found many times");
        }

        return ret;
    }

    public void openResource(WebElement resource) {
        resource.findElement(By.tagName("h2")).findElement(By.tagName("a")).click();

        WebElement endpoints = resource.findElement(By.className("endpoints"));
        elementWaitHelper.waitElementAnimatedFinish(endpoints);
    }

}
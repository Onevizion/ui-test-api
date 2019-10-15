package com.onevizion.uitest.api.helper.api.v3;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.vo.ApiV3EndpointType;

@Component
public class ApiV3Endpoint {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Element element;

    @Resource
    private ElementWait elementWait;

    public int getEndpointsCount(WebElement resource) {
        List<WebElement> endpoints = resource.findElements(By.className("endpoint"));
        return endpoints.size();
    }

    public WebElement findEndpoint(WebElement resource, ApiV3EndpointType apiV3EndpointType) {
        WebElement ret = null;
        int count = 0;

        List<WebElement> endpoints = resource.findElements(By.className("endpoint"));
        for (WebElement endpoint : endpoints) {
            element.moveToElement(endpoint);
            String actualMethod = endpoint.findElement(By.className("heading")).findElement(By.className("http_method")).getText();
            String actualPath = endpoint.findElement(By.className("heading")).findElement(By.className("path")).getText();
            String actualDescription = endpoint.findElement(By.className("heading")).findElement(By.tagName("li")).getText();

            if (apiV3EndpointType.getMethod().equals(actualMethod)
                    && apiV3EndpointType.getPath().equals(actualPath)
                    && apiV3EndpointType.getDescription().equals(actualDescription)) {
                count = count + 1;
                ret = endpoint;
            }
        }

        if (count == 0) {
            throw new SeleniumUnexpectedException("Endpoint not found");
        } else if (count > 1) {
            throw new SeleniumUnexpectedException("Endpoint found many times");
        }

        return ret;
    }

    /**
     * @deprecated (we should use findEndpoint with ApiV3EndpointType)
     */
    @Deprecated
    public WebElement findEndpoint(WebElement resource, String method, String path, String description) {
        WebElement ret = null;
        int count = 0;

        List<WebElement> endpoints = resource.findElements(By.className("endpoint"));
        for (WebElement endpoint : endpoints) {
            element.moveToElement(endpoint);
            String actualMethod = endpoint.findElement(By.className("heading")).findElement(By.className("http_method")).getText();
            String actualPath = endpoint.findElement(By.className("heading")).findElement(By.className("path")).getText();
            String actualDescription = endpoint.findElement(By.className("heading")).findElement(By.tagName("li")).getText();

            if (method.equals(actualMethod) && path.equals(actualPath) && description.equals(actualDescription)) {
                count = count + 1;
                ret = endpoint;
            }
        }

        if (count == 0) {
            throw new SeleniumUnexpectedException("Endpoint not found");
        } else if (count > 1) {
            throw new SeleniumUnexpectedException("Endpoint found many times");
        }

        return ret;
    }

    public void openEndpoint(WebElement endpoint) {
        endpoint.findElement(By.className("heading")).findElement(By.className("http_method")).findElement(By.tagName("a")).click();

        WebElement content = endpoint.findElement(By.className("content"));
        elementWait.waitElementAnimatedFinish(content);
    }

    public void submitEndpoint(WebElement endpoint) {
        WebElement hideButton = endpoint.findElement(By.className("response_hider"));
        WebElement submitButton = endpoint.findElement(By.className("submit"));
        WebElement response = endpoint.findElement(By.className("response"));

        if (hideButton.isDisplayed()) {
            hideButton.click();
            elementWait.waitElementAttribute(hideButton, "style", "display: none;");
            elementWait.waitElementAnimatedFinish(response);
        }
        elementWait.waitElementVisible(submitButton);
        submitButton.click();
        elementWait.waitElementVisible(response);
        elementWait.waitElementAnimatedFinish(response);
    }

    public <T extends Comparable<? super T>> T getResponseAsObject(WebElement endpoint, Class<T> clazz) {
        WebElement responseText = endpoint.findElement(By.className("response_body"));
        element.moveToElement(responseText);
        String actualResponseText = responseText.getText();

        T actualResponse = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            actualResponse = mapper.readValue(actualResponseText, clazz);
        } catch (IOException e) {
            throw new SeleniumUnexpectedException(e);
        }

        return actualResponse;
    }

    public <T extends Comparable<? super T>> List<T> getResponse(WebElement endpoint, Class<T> clazz) {
        WebElement responseText = endpoint.findElement(By.className("response_body"));
        element.moveToElement(responseText);
        String actualResponseText = responseText.getText();

        List<T> actualResponse = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            actualResponse = mapper.readValue(actualResponseText, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new SeleniumUnexpectedException(e);
        }

        return actualResponse;
    }

    public <T extends Comparable<? super T>> void checkResponseAsObject(WebElement endpoint, T expectedResponse, Class<T> clazz) {
        T actualResponse = getResponseAsObject(endpoint, clazz);
        Assert.assertEquals(actualResponse, expectedResponse);
    }

    public <T extends Comparable<? super T>> void checkResponse(WebElement endpoint, List<T> expectedResponse, Class<T> clazz) {
        List<T> actualResponse = getResponse(endpoint, clazz);
        Collections.sort(expectedResponse);
        Collections.sort(actualResponse);
        Assert.assertEquals(actualResponse, expectedResponse);
    }

    public void checkResponseText(WebElement endpoint, String expectedResponseText) {
        WebElement responseText = endpoint.findElement(By.className("response_body"));
        element.moveToElement(responseText);
        String actualResponseText = responseText.getText();
        Assert.assertEquals(actualResponseText, expectedResponseText);
    }

    public void checkResponseTextWithErrorReportId(WebElement endpoint, String expectedResponseText) {
        WebElement responseText = endpoint.findElement(By.className("response_body"));
        element.moveToElement(responseText);
        String actualResponseText = responseText.getText();
        actualResponseText = actualResponseText.substring(0, actualResponseText.length() - 36);
        Assert.assertEquals(actualResponseText, expectedResponseText);
    }

    public void checkResponseCode(WebElement endpoint, String expectedResponseCode) {
        WebElement responseCode = endpoint.findElement(By.className("response_code"));
        element.moveToElement(responseCode);
        String actualResponseCode = responseCode.getText();
        Assert.assertEquals(actualResponseCode, expectedResponseCode);
    }

}
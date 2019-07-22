package com.onevizion.uitest.api.helper.api.v3;

import java.util.List;
import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Element;

@Component
public class ApiV3Parameter {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Element element;

    public int getParametersCount(WebElement endpoint) {
        List<WebElement> parametersSections = endpoint.findElements(By.className("operation-params"));
        if (parametersSections.isEmpty()) {
            return 0;
        } else if (parametersSections.size() == 1) {
            WebElement parametersSection = parametersSections.get(0);
            List<WebElement> parameters = parametersSection.findElements(By.xpath("tr"));
            return parameters.size();
        } else {
            throw new SeleniumUnexpectedException("Found many sections for parameters");
        }
    }

    public WebElement findParameter(WebElement endpoint, String param, String dataType) {
        WebElement ret = null;
        int count = 0;

        List<WebElement> parameters = endpoint.findElement(By.className("operation-params")).findElements(By.xpath("tr"));
        for (WebElement parameter : parameters) {
            element.moveToElement(parameter);
            String actualParam = parameter.findElement(By.className("code")).findElement(By.tagName("label")).getText();
            String actualDataType = parameter.findElement(By.className("model-signature")).getText();

            if (param.equals(actualParam) && dataType.equals(actualDataType)) {
                count = count + 1;
                ret = parameter;
            }
        }

        if (count == 0) {
            throw new SeleniumUnexpectedException("Parameter not found");
        } else if (count > 1) {
            throw new SeleniumUnexpectedException("Parameter found many times");
        }

        return ret;
    }

    public WebElement findParameter(WebElement endpoint, String param) {
        WebElement ret = null;
        int count = 0;

        List<WebElement> parameters = endpoint.findElement(By.className("operation-params")).findElements(By.xpath("tr"));
        for (WebElement parameter : parameters) {
            element.moveToElement(parameter);
            String actualParam = parameter.findElement(By.className("code")).findElement(By.tagName("label")).getText();

            if (param.equals(actualParam)) {
                count = count + 1;
                ret = parameter;
            }
        }

        if (count == 0) {
            throw new SeleniumUnexpectedException("Parameter not found");
        } else if (count > 1) {
            throw new SeleniumUnexpectedException("Parameter found many times");
        }

        return ret;
    }

}
package com.onevizion.guitest.helper.api.v3;

import java.util.List;
import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.exception.SeleniumUnexpectedException;
import com.onevizion.guitest.helper.ElementHelper;

@Component
public class ApiV3ParameterHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementHelper elementHelper;

    public int getParametersCount(WebElement endpoint) {
        List<WebElement> parametersSections = endpoint.findElements(By.className("operation-params"));
        if (parametersSections.size() == 0) {
            return 0;
        } else if (parametersSections.size() == 1) {
            WebElement parametersSection = parametersSections.get(0);
            List<WebElement> parameters = parametersSection.findElements(By.tagName("tr"));
            return parameters.size();
        } else {
            throw new SeleniumUnexpectedException("Found many sections for parameters");
        }
    }

    public WebElement findParameter(WebElement endpoint, String param, String dataType) {
        WebElement ret = null;
        int count = 0;

        List<WebElement> parameters = endpoint.findElement(By.className("operation-params")).findElements(By.tagName("tr"));
        for (WebElement parameter : parameters) {
            elementHelper.moveToElement(parameter);
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

}
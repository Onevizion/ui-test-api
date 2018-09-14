package com.onevizion.uitest.api.helper;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class CheckboxHelper {

    @Resource
    private JsHelper jsHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Window window;

    @Resource
    private WaitHelper waitHelper;

    public void clickByName(String checkboxName) {
        WebElement checkbox = seleniumSettings.getWebDriver().findElement(By.name(checkboxName));
        WebElement label = jsHelper.getParentElement(checkbox);
        label.click();
    }

    public void clickById(String checkboxId) {
        WebElement checkbox = seleniumSettings.getWebDriver().findElement(By.id(checkboxId));
        WebElement label = jsHelper.getParentElement(checkbox);
        if (label.isDisplayed()) {
            label.click();
        }
    }

    public void clickByElement(WebElement checkbox) {
        WebElement label = jsHelper.getParentElement(checkbox);
        label.click();
    }

    public List<WebElement> findCheckboxesByName(String checkboxname) {
        return seleniumSettings.getWebDriver().findElements(By.name(checkboxname));
    }

    public List<WebElement> findLabelsByName(String checkboxname) {
        List<WebElement> checkboxes = seleniumSettings.getWebDriver().findElements(By.name(checkboxname));
        List<WebElement> labels = new ArrayList<WebElement>();
        for (WebElement checkbox : checkboxes) {
            WebElement label = jsHelper.getParentElement(checkbox);
            labels.add(label);
        }
        return labels;
    }

    public WebElement findLabelByName(String checkboxname) {
        WebElement checkbox = seleniumSettings.getWebDriver().findElement(By.name(checkboxname));
        WebElement label = jsHelper.getParentElement(checkbox);
        return label;
    }

    public WebElement findLabelByElement(WebElement checkbox) {
        WebElement label = jsHelper.getParentElement(checkbox);
        return label;
    }

    public boolean isElementChecked(WebElement webElement) {
        String checked = webElement.getAttribute("checked");
        if (checked != null && checked.equals("true")) {
            return true;
        }
        return false;
    }

    public boolean isCheckedByName(String checkboxName) {
        String checked = seleniumSettings.getWebDriver().findElement(By.name(checkboxName)).getAttribute("checked");
        if (checked != null && checked.equals("true")) {
            return true;
        }
        return false;
    }

    public boolean isCheckedById(String checkboxId) {
        String checked = seleniumSettings.getWebDriver().findElement(By.id(checkboxId)).getAttribute("checked");
        if (checked != null && checked.equals("true")) {
            return true;
        }
        return false;
    }
}
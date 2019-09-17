package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

@Component
public class DualListbox {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private HtmlSelect htmlSelect;

    public void addValueByText(Select select, String btnId, String text) {
        checkValueByTextIsPresent(select, text);
        deselectSelectOptions(select);
        select.selectByVisibleText(text);
        seleniumSettings.getWebDriver().findElement(By.id(btnId)).click();
    }

    /*new void to support new duallist box*/
    public void addValueByText(WebElement select, String btnId, String text) {
        checkValueByTextIsPresent(select, text);
        //deselectSelectOptions(select);
        for (WebElement option :select.findElements(By.tagName("div"))) {
            if (text.equals(htmlSelect.getOptionText(option, true))) {
                option.click();
                break;
            }
        }
        //select.selectByVisibleText(text);
        seleniumSettings.getWebDriver().findElement(By.id(btnId)).click();
    }

    private void checkValueByTextIsPresent(Select select, String text) {
        int attemptsCnt = 0; //protection from the endless cycle
        int i = 0;
        do {
            try{
                for (WebElement option : select.getOptions()) {
                    if (text.equals(option.getText())) {
                        i = i + 1;
                        break;
                    }
                }
            } catch (StaleElementReferenceException e) {
                i = 0;
            }
            attemptsCnt = attemptsCnt + 1;
        } while (i < 1 && attemptsCnt <= 10);

        if (i < 1 && attemptsCnt > 10) {
            throw new SeleniumUnexpectedException("Value not found in duallist box");
        }
    }

    /*new void to support new duallist box*/
    private void checkValueByTextIsPresent(WebElement select, String text) {
        int attemptsCnt = 0; //protection from the endless cycle
        int i = 0;
        do {
            try{
                for (WebElement option : select.findElements(By.tagName("div"))) {
                    if (text.equals(htmlSelect.getOptionText(option, true))) {
                        i = i + 1;
                        break;
                    }
                }
            } catch (StaleElementReferenceException e) {
                i = 0;
            }
            attemptsCnt = attemptsCnt + 1;
        } while (i < 1 && attemptsCnt <= 10);

        if (i < 1 && attemptsCnt > 10) {
            throw new SeleniumUnexpectedException("Value not found in duallist box");
        }
    }

    private void deselectSelectOptions(final Select select) {
        if (select.isMultiple()) {
            select.deselectAll();
        }
    }

}
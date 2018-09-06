package com.onevizion.guitest.helper;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class HtmlSelectHelper {

    public boolean isSelectOptionPresent(Select select, String visibleText) {
        boolean isException = true;
        while (isException) {
            try {
                for (WebElement option : select.getOptions()) {
                    if (option.getText().equals(visibleText)) {
                        return true;
                    }
                }
                isException = false;
            } catch (StaleElementReferenceException e) {
                isException = true;
            }
        }

        return false;
    }

    /* new void to support new duallist box */
    public boolean isSelectOptionPresent(WebElement select, String visibleText) {
        boolean isException = true;
        while (isException) {
            try {
                for (WebElement option : select.findElements(By.tagName("div"))) {
                    if (getOptionText(option, true).equals(visibleText)) {
                        return true;
                    }
                }
                isException = false;
            } catch (StaleElementReferenceException e) {
                isException = true;
            }
        }

        return false;
    }

    /* new void to support new duallist box */
    public boolean isSelectOptionPresentByValue(List<String> elementsValues, String value) {
        for (String elementValue : elementsValues) {
            if (elementValue.equals(value)) {
                return true;
            }
        }

        return false;
    }

    public String getOptionText(final Select select, final int num) {
        if (StringUtils.isNotEmpty(select.getOptions().get(num).getAttribute("innerText"))) {
            return select.getOptions().get(num).getAttribute("innerText");
        } else {
            return select.getOptions().get(num).getAttribute("textContent");
        }
    }

    /* new void to support new duallist box */
    public String getOptionDivText(final WebElement selectDiv, final int num) {
        if (StringUtils.isNotEmpty(selectDiv.findElements(By.tagName("div")).get(num).findElement(By.tagName("span")).getAttribute("innerText"))) {
            return selectDiv.findElements(By.tagName("div")).get(num).findElement(By.tagName("span")).getAttribute("innerText");
        } else {
            return selectDiv.findElements(By.tagName("div")).get(num).findElement(By.tagName("span")).getAttribute("textContent");
        }
    }

    public String getOptionText(final WebElement option) {
        if (StringUtils.isNotEmpty(option.getAttribute("innerText"))) {
            return option.getAttribute("innerText");
        } else {
            return option.getAttribute("textContent");
        }
    }

    /* new void to support new duallist box */
    public String getOptionText(final WebElement option, final boolean isNew) {
        if (StringUtils.isNotEmpty(option.findElement(By.tagName("span")).getAttribute("innerText"))) {
            return option.findElement(By.tagName("span")).getAttribute("innerText");
        } else {
            return option.findElement(By.tagName("span")).getAttribute("textContent");
        }
    }

    public String getOptionTextNew(final WebElement option) {
        if (StringUtils.isNotEmpty(option.getAttribute("innerText"))) {
            return option.getAttribute("innerText").trim();
        } else {
            return option.getAttribute("textContent").trim();
        }
    }

}
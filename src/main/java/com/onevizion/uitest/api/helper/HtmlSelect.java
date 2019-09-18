package com.onevizion.uitest.api.helper;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

@Component
public class HtmlSelect {

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

    /**
     * @deprecated (we should use listbox.checkElementByLabel when UI will be updated. fields on admin wf step form)
     */
    @Deprecated
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

    /**
     * @deprecated (we should use listbox.checkElementByLabel when UI will be updated. fields on admin wf step form and applets on reorder form)
     */
    @Deprecated
    public String getOptionText(final WebElement option, final boolean isNew) {
        if (StringUtils.isNotEmpty(option.findElement(By.tagName("span")).getAttribute("innerText"))) {
            return option.findElement(By.tagName("span")).getAttribute("innerText");
        } else {
            return option.findElement(By.tagName("span")).getAttribute("textContent");
        }
    }

}
package com.onevizion.uitest.api.helper.grid.sort;

import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class GridSortWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private GridSortJs gridSortJs;

    void waitSortIconIsDisplayed(WebElement elem) {
        Supplier<String> messageSupplier = ()-> "Sorting icon not displayed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> elem.findElement(By.className("hdr_sort")).isDisplayed());
    }

    void waitSortMenuIsDisplayed() {
        IntSupplier actualValueSupplier = ()-> {
            int count = 0;
            List<WebElement> menus = seleniumSettings.getWebDriver().findElements(By.className("contextSort"));
            for (WebElement menu : menus) {
                if (menu.isDisplayed()) {
                    count = count + 1;
                }
            }
            return count;
        };

        Supplier<String> messageSupplier = ()-> "Sorting menu not displayed expected [1] but found [" + actualValueSupplier.getAsInt() + "]";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> 1 == actualValueSupplier.getAsInt());
    }

    void checkGridSort(Long gridId, Long columnIndex, String sortTypeString) {
        @SuppressWarnings("unchecked")
        Supplier<List<Object>> actualValueSupplier = ()-> (List<Object>) gridSortJs.getGridSort(gridId);

        Supplier<String> messageSupplier = ()-> "Sorting working is not correct expected [" + columnIndex + "," + sortTypeString + "] but found [" + actualValueSupplier.get().get(0) + "," + actualValueSupplier.get().get(1) + "]";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> columnIndex.equals((Long) actualValueSupplier.get().get(0)) && sortTypeString.equals((String) actualValueSupplier.get().get(1)));
    }

    void checkGridSortColumnId(Long gridId, String columnId) {
        Supplier<String> actualValueSupplier = ()-> gridSortJs.getGridSortColumnIdByGridId(gridId);

        Supplier<String> messageSupplier = ()-> "Sorting working is not correct expected [" + columnId + "] but found [" + actualValueSupplier.get() + "]";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> columnId.equals(actualValueSupplier.get()));
    }

    void checkGridSortTypeNumber(Long gridId, String sortTypeNumber) {
        Supplier<String> actualValueSupplier = ()-> gridSortJs.getGridSortTypeByGridId(gridId);

        Supplier<String> messageSupplier = ()-> "Sorting working is not correct expected [" + sortTypeNumber + "] but found [" + actualValueSupplier.get() + "]";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> sortTypeNumber.equals(actualValueSupplier.get()));
    }

}
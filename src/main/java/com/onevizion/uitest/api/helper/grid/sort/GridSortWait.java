package com.onevizion.uitest.api.helper.grid.sort;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Resource;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class GridSortWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private GridSortJs gridSortJs;

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
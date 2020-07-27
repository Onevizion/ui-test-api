package com.onevizion.uitest.api.helper.grid;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.vo.LockType;

@Component
class Grid2Wait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Grid2Js grid2Js;

    void waitIsGridLoaded(Long gridIdx) {
        BooleanSupplier actualValueSupplier = ()-> grid2Js.isGridLoaded(gridIdx);

        Supplier<String> messageSupplier = ()-> "Waiting for grid with idx=[" + gridIdx + "] is loaded is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> actualValueSupplier.getAsBoolean());
    }

    void waitIsGridDataLoaded(Long gridIdx) {
        BooleanSupplier actualValueSupplier = ()-> grid2Js.isGridDataLoaded(gridIdx);

        Supplier<String> messageSupplier = ()-> "Waiting for grid with idx=[" + gridIdx + "] is data loaded is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> actualValueSupplier.getAsBoolean());
    }

    void waitIsGridUpdated(Long gridIdx) {
        BooleanSupplier actualValueSupplier = ()-> grid2Js.isGridUpdated(gridIdx);

        Supplier<String> messageSupplier = ()-> "Waiting for grid with idx=[" + gridIdx + "] is updated is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> actualValueSupplier.getAsBoolean());
    }

    void waitLoadAllRows(Long gridIdx) {
        BooleanSupplier actualValueSupplier = ()-> grid2Js.isLoadAllRowsDone(gridIdx);

        Supplier<String> messageSupplier = ()-> "Waiting for load all rows is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> actualValueSupplier.getAsBoolean());
    }

    void waitGridCellLockType(Long gridIdx, Long columnIndex, Long rowIndex, LockType lockType) {
        Supplier<LockType> actualValueSupplier = ()-> grid2Js.getGridCellLockTypeByRowIndexAndColIndex(gridIdx, rowIndex, columnIndex);

        Supplier<String> messageSupplier = ()-> "Waiting for Grid Cell LockType gridId=[" + gridIdx + "] columnIndex=[" + columnIndex + "] rowIndex=[" + rowIndex + "] expectedVal=[" + lockType + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> lockType.equals(actualValueSupplier.get()));
    }

}
package com.onevizion.uitest.api.helper.grid;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import javax.annotation.Resource;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.vo.LockType;

@Component
class Grid2Wait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Grid2Js grid2Js;

    void waitLoadAllRows(Long gridIdx) {
        BooleanSupplier actualValueSupplier = ()-> grid2Js.isLoadAllRowsDone(gridIdx);

        Supplier<String> messageSupplier = ()-> "Waiting for load all rows is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> actualValueSupplier.getAsBoolean());
    }

    void waitGridCellLockType(Long gridId, Long columnIndex, Long rowIndex, LockType lockType) {
        Supplier<LockType> actualValueSupplier = ()-> grid2Js.getGridCellLockTypeByRowIndexAndColIndex(gridId, rowIndex, columnIndex);

        Supplier<String> messageSupplier = ()-> "Waiting for Grid Cell LockType gridId=[" + gridId + "] columnIndex=[" + columnIndex + "] rowIndex=[" + rowIndex + "] expectedVal=[" + lockType + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> lockType.equals(actualValueSupplier.get()));
    }

}
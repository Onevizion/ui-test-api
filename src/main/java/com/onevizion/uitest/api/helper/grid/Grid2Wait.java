package com.onevizion.uitest.api.helper.grid;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.vo.LockType;

@Component
class Grid2Wait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Grid2Js grid2Js;

    @Autowired
    private Wait wait;

    void waitUpdate(Long gridIdx) {
        BooleanSupplier actualValueSupplier = ()-> grid2Js.isUpdateDone(gridIdx);

        Supplier<String> messageSupplier = ()-> "Waiting for grid update is failed";

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

    void waitGridCellLockType(Long gridId, Long columnIndex, Long rowIndex, LockType lockType) {
        Supplier<LockType> actualValueSupplier = ()-> grid2Js.getGridCellLockTypeByRowIndexAndColIndex(gridId, rowIndex, columnIndex);

        Supplier<String> messageSupplier = ()-> "Waiting for Grid Cell LockType gridId=[" + gridId + "] columnIndex=[" + columnIndex + "] rowIndex=[" + rowIndex + "] expectedVal=[" + lockType + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> lockType.equals(actualValueSupplier.get()));
    }

    void waitSavePanelHidden(Long gridId) {
        wait.waitWebElement(By.id("savePanel" + gridId));

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting save panel hidden is failed")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !webdriver.findElement(By.id("savePanel" + gridId)).isDisplayed());
    }

}
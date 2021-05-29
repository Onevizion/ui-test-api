package com.onevizion.uitest.api.helper.grid.panel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Js;

@Component
public class GridPanel {

    private static final String CLASS_GRID_PANEL = "dhxwin_active";
    private static final String CLASS_GRID_PANEL_TITLE = "dhxwin_text";
    private static final String CLASS_GRID_PANEL_GRID = "gridbox";
    private static final String CLASS_GRID_PANEL_CLOSE = "dhxwin_button_close";

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Js js;

    public void waitPanel(String title) {
        WebElement gridPanel = seleniumSettings.getWebDriver().findElement(By.className(CLASS_GRID_PANEL));
        WebElement gridPanelTitle = gridPanel.findElement(By.className(CLASS_GRID_PANEL_TITLE));

        elementWait.waitElementVisible(gridPanel);
        elementWait.waitElementDisplay(gridPanel);

        assertElement.assertLink(gridPanelTitle, title);
    }

    public void checkPanelRowsCount(int rowsCount) {
        WebElement gridPanel = seleniumSettings.getWebDriver().findElement(By.className(CLASS_GRID_PANEL));
        WebElement gridPanelGrid = gridPanel.findElement(By.className(CLASS_GRID_PANEL_GRID));

        Assert.assertEquals(js.getGridRowsCount(gridPanelGrid), rowsCount);
    }

    public void checkPanelRowExpectedMessage(int rowNumber, String lineNumber, String key, String message) {
        WebElement gridPanel = seleniumSettings.getWebDriver().findElement(By.className(CLASS_GRID_PANEL));
        WebElement gridPanelGrid = gridPanel.findElement(By.className(CLASS_GRID_PANEL_GRID));

        Assert.assertEquals(js.getGridCellValueByRowIndexAndColIndex(gridPanelGrid, rowNumber - 1, 0), lineNumber);
        Assert.assertEquals(js.getGridCellValueByRowIndexAndColIndex(gridPanelGrid, rowNumber - 1, 1), key);
        Assert.assertEquals(js.getGridCellValueByRowIndexAndColIndex(gridPanelGrid, rowNumber - 1, 2), message);
    }

    public void checkPanelRowUnexpectedMessage(int rowNumber, String lineNumber, String key, String message) {
        WebElement gridPanel = seleniumSettings.getWebDriver().findElement(By.className(CLASS_GRID_PANEL));
        WebElement gridPanelGrid = gridPanel.findElement(By.className(CLASS_GRID_PANEL_GRID));

        Assert.assertEquals(js.getGridCellValueByRowIndexAndColIndex(gridPanelGrid, rowNumber - 1, 0), lineNumber);
        Assert.assertEquals(js.getGridCellValueByRowIndexAndColIndex(gridPanelGrid, rowNumber - 1, 1), key);

        String actualErrorStr = js.getGridCellValueByRowIndexAndColIndex(gridPanelGrid, rowNumber - 1, 2);
        int errorReportIdOccurrenceIndex = actualErrorStr.indexOf("Error Report ID");
        if (errorReportIdOccurrenceIndex == -1) {
            throw new SeleniumUnexpectedException("Error without Error Report ID");
        }
        Assert.assertEquals(actualErrorStr.substring(0, errorReportIdOccurrenceIndex), message);
    }

    public void closePanel() {
        WebElement gridPanel = seleniumSettings.getWebDriver().findElement(By.className(CLASS_GRID_PANEL));
        WebElement gridPanelClose = gridPanel.findElement(By.className(CLASS_GRID_PANEL_CLOSE));

        gridPanelClose.click();

        elementWait.waitElementNotExist(gridPanel);
    }

}
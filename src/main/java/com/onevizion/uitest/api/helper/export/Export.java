package com.onevizion.uitest.api.helper.export;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumLogger;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.vo.entity.ExportRun;

@Component
public class Export {

    private static final String BUTTON_EXPORT_RUN_ID_BASE = "btnExport";
    private static final String BUTTON_EXPORT_HISTORY_ID_BASE = "btnExportHistory";

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private Element element;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private ExportWait exportWait;

    @Autowired
    private Grid grid;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Checkbox checkbox;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private SeleniumLogger seleniumLogger;

    public void export(Long gridIndex, ExportRun exportRun, List<Integer> uniqueColumns, CheckExportFile checkExportFile) {
        runExport(gridIndex, exportRun);
        String processId = waitExportDone();
        if (StringUtils.isNotEmpty(exportRun.getFilePath())) {
            if ("Grid to CSV".equals(exportRun.getMode())) {
                if (checkExportFile != null) {
                    checkExportFile.checkExportCsvFile(processId, exportRun.getFilePath(), uniqueColumns);
                } else {
                    seleniumLogger.error("export file not verified");
                }
            } else if ("Grid to Excel".equals(exportRun.getMode())) {
                if (checkExportFile != null) {
                    checkExportFile.checkExportExcelFile(processId, exportRun.getFilePath(), uniqueColumns);
                } else {
                    seleniumLogger.error("export file not verified");
                }
            } else {
                throw new SeleniumUnexpectedException("Not support Mode. Mode=" + exportRun.getMode());
            }
        }
        checkExport(gridIndex, exportRun);
        deleteExport(gridIndex, processId);
    }

    public void export(Long gridIndex, ExportRun exportRun, CheckExportFile checkExportFile) {
        runExport(gridIndex, exportRun);
        String processId = waitExportDone();
        if (StringUtils.isNotEmpty(exportRun.getFilePath())) {
            if ("Grid to CSV".equals(exportRun.getMode())) {
                if (checkExportFile != null) {
                    checkExportFile.checkExportCsvFile(processId, exportRun.getFilePath());
                } else {
                    seleniumLogger.error("export file not verified");
                }
            } else if ("Grid to Excel".equals(exportRun.getMode())) {
                if (checkExportFile != null) {
                    checkExportFile.checkExportExcelFile(processId, exportRun.getFilePath());
                } else {
                    seleniumLogger.error("export file not verified");
                }
            } else {
                throw new SeleniumUnexpectedException("Not support Mode. Mode=" + exportRun.getMode());
            }
        }
        checkExport(gridIndex, exportRun);
        deleteExport(gridIndex, processId);
    }

    private void runExport(Long gridIndex, ExportRun exportRun) {
        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_GRID_OPTIONS_ID_BASE + gridIndex)).click();
        elementWait.waitElementVisibleById(BUTTON_EXPORT_RUN_ID_BASE + gridIndex);
        elementWait.waitElementDisplayById(BUTTON_EXPORT_RUN_ID_BASE + gridIndex);
        window.openModal(By.id(BUTTON_EXPORT_RUN_ID_BASE + gridIndex));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        new Select(seleniumSettings.getWebDriver().findElement(By.name("GridExportMode"))).selectByVisibleText(exportRun.getMode());
        new Select(seleniumSettings.getWebDriver().findElement(By.name("GridExportDelivery"))).selectByVisibleText(exportRun.getDelivery());
        seleniumSettings.getWebDriver().findElement(By.name("GridExportComment")).sendKeys(exportRun.getComments());
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    private String waitExportDone() {
        WebElement processListButton = seleniumSettings.getWebDriver().findElement(By.id("topPanelProcessContainer")).findElement(By.className("btn_input"));
        processListButton.click();

        exportWait.waitExport();

        exportWait.waitExportDone();

        WebElement panel = seleniumSettings.getWebDriver().findElement(By.id("processEventList"));
        WebElement link = panel.findElement(By.className("ge_link"));
        String processId = link.getAttribute("onclick").replace("showExpDetails(", "").replace(")", "");

        element.moveToElementById("drop_down_overflow_" + processId);

        elementWait.waitElementById("ge_delete_" + processId);
        elementWait.waitElementVisibleById("ge_delete_" + processId);
        elementWait.waitElementDisplayById("ge_delete_" + processId);

        seleniumSettings.getWebDriver().findElement(By.id("ge_delete_" + processId)).click();

        wait.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();

        processListButton.click();

        return processId;
    }

    private void checkExport(Long gridIndex, ExportRun exportRun) {
        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_GRID_OPTIONS_ID_BASE + gridIndex)).click();
        elementWait.waitElementVisibleById(BUTTON_EXPORT_HISTORY_ID_BASE + gridIndex);
        elementWait.waitElementDisplayById(BUTTON_EXPORT_HISTORY_ID_BASE + gridIndex);
        window.openModal(By.id(BUTTON_EXPORT_HISTORY_ID_BASE + gridIndex));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        grid2.waitLoad();
        Long gridRows = grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx());
        Assert.assertEquals(gridRows, Long.valueOf(1L), "Grid have wrong rows count");

        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        wait.waitFormLoad();
        assertElement.assertLink("gridPageName", exportRun.getGridPageName() + " ");
        assertElement.assertLink("trackorTypeName", exportRun.getTrackorTypeName());
        assertElement.assertLink("statusName", exportRun.getStatusName() + " ");
        assertElement.assertLink("exportTypeName", exportRun.getMode() + " ");
        assertElement.assertLink("exportDeliveryName", exportRun.getDelivery());
        assertElement.assertLink("un", seleniumSettings.getTestUser());
        assertElement.assertTextById("errorMessage", exportRun.getErrorMessage());
        assertElement.assertTextById("comments", exportRun.getComments());
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
    }

    private void deleteExport(Long gridIndex, String processId) {
        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_GRID_OPTIONS_ID_BASE + gridIndex)).click();
        elementWait.waitElementVisibleById(BUTTON_EXPORT_HISTORY_ID_BASE + gridIndex);
        elementWait.waitElementDisplayById(BUTTON_EXPORT_HISTORY_ID_BASE + gridIndex);
        window.openModal(By.id(BUTTON_EXPORT_HISTORY_ID_BASE + gridIndex));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        grid2.waitLoad();
        Long gridRows = grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx());
        Assert.assertEquals(gridRows, Long.valueOf(1L), "Grid have wrong rows count");

        checkbox.clickById("lblcb" + processId);
        elementWait.waitElementEnabledById(AbstractSeleniumCore.BUTTON_DELETE_ID_BASE + AbstractSeleniumCore.getGridIdx());
        element.click(seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_DELETE_ID_BASE + AbstractSeleniumCore.getGridIdx())));
        wait.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();
        grid2.waitLoad();
        gridRows = grid.getGridRowsCount(AbstractSeleniumCore.getGridIdx());
        Assert.assertEquals(gridRows, Long.valueOf(0L), "Grid have wrong rows count");

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
    }

}
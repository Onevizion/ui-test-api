package com.onevizion.uitest.api.helper.export;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private Element element;

    @Resource
    private ElementWait elementWait;

    @Resource
    private ExportJs exportJs;

    @Resource
    private ExportWait exportWait;

    @Resource
    private Grid grid;

    @Resource
    private Grid2 grid2;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Checkbox checkbox;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private SeleniumLogger seleniumLogger;

    public void export(Long gridIndex, ExportRun exportRun, List<Integer> uniqueColumns, CheckExportFile checkExportFile) {
        ExportMenu exportMenu = getExportMenu();

        runExport(gridIndex, exportMenu, exportRun);
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
        checkExport(gridIndex, exportMenu, exportRun);
        deleteExport(gridIndex, exportMenu, processId);
    }

    public void export(Long gridIndex, ExportRun exportRun, CheckExportFile checkExportFile) {
        ExportMenu exportMenu = getExportMenu();

        runExport(gridIndex, exportMenu, exportRun);
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
        checkExport(gridIndex, exportMenu, exportRun);
        deleteExport(gridIndex, exportMenu, processId);
    }

    private void runExport(Long gridIndex, ExportMenu exportMenu, ExportRun exportRun) {
        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_EXPORT_ID_BASE + gridIndex)).click(); //TODO move AbstractSelenium.BUTTON_EXPORT_ID_BASE
        elementWait.waitElementVisible(exportMenu.getRun());
        elementWait.waitElementDisplay(exportMenu.getRun());
        window.openModal(exportMenu.getRun());
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

    private void checkExport(Long gridIndex, ExportMenu exportMenu, ExportRun exportRun) {
        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_EXPORT_ID_BASE + gridIndex)).click();
        elementWait.waitElementVisible(exportMenu.getHistory());
        elementWait.waitElementDisplay(exportMenu.getHistory());
        window.openModal(exportMenu.getHistory());
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

    private void deleteExport(Long gridIndex, ExportMenu exportMenu, String processId) {
        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_EXPORT_ID_BASE + gridIndex)).click();
        elementWait.waitElementVisible(exportMenu.getHistory());
        elementWait.waitElementDisplay(exportMenu.getHistory());
        window.openModal(exportMenu.getHistory());
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

    private ExportMenu getExportMenu() {
        ExportMenu exportMenu = new ExportMenu();

        List<WebElement> menus = seleniumSettings.getWebDriver().findElements(By.className("dhtmlxMenu_dhx_skyblue_SubLevelArea_Polygon"));
        for (WebElement menu : menus) {
            List<WebElement> menuItems = menu.findElements(By.className("sub_item"));
            for (WebElement menuItem : menuItems) {
                WebElement menuItemDiv = menuItem.findElement(By.tagName("div"));
                String menuItemDivText = menuItemDiv.getAttribute("innerText");
                if ("Run Export".equals(menuItemDivText)) {
                    exportMenu.setRun(menuItemDiv);
                } else if ("Export History".equals(menuItemDivText)) {
                    exportMenu.setHistory(menuItemDiv);
                }

                if (exportMenu.getRun() != null && exportMenu.getHistory() != null) {
                    return exportMenu;
                }
            }
        }

        throw new SeleniumUnexpectedException("Export menu not found");
    }

    private class ExportMenu {

        private WebElement run;
        private WebElement history;

        public WebElement getRun() {
            return run;
        }

        public void setRun(WebElement run) {
            this.run = run;
        }

        public WebElement getHistory() {
            return history;
        }

        public void setHistory(WebElement history) {
            this.history = history;
        }

    }

}
package com.onevizion.uitest.api.helper.export;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.ElementHelper;
import com.onevizion.uitest.api.helper.ElementWaitHelper;
import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.vo.entity.ExportRun;

@Component
public class Export {

    private final static Logger logger = LoggerFactory.getLogger(Export.class);

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private ElementHelper elementHelper;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    @Resource
    private ExportJs exportJs;

    @Resource
    private ExportWait exportWait;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private Checkbox checkbox;

    @Resource
    private SeleniumSettings seleniumSettings;

    public void export(Long gridIndex, ExportRun exportRun, List<Integer> uniqueColumns, CheckExportFile checkExportFile) {
        runExport(gridIndex, exportRun);
        String processId = waitExportDone();
        if (StringUtils.isNotEmpty(exportRun.getFilePath())) {
            if ("Grid to CSV".equals(exportRun.getMode())) {
                if (checkExportFile != null) {
                    checkExportFile.checkExportCsvFile(processId, exportRun.getFilePath(), uniqueColumns);
                } else {
                    logger.error("export file not verified");
                }
            } else if ("Grid to Excel".equals(exportRun.getMode())) {
                if (checkExportFile != null) {
                    checkExportFile.checkExportExcelFile(processId, exportRun.getFilePath(), uniqueColumns);
                } else {
                    logger.error("export file not verified");
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
                    logger.error("export file not verified");
                }
            } else if ("Grid to Excel".equals(exportRun.getMode())) {
                if (checkExportFile != null) {
                    checkExportFile.checkExportExcelFile(processId, exportRun.getFilePath());
                } else {
                    logger.error("export file not verified");
                }
            } else {
                throw new SeleniumUnexpectedException("Not support Mode. Mode=" + exportRun.getMode());
            }
        }
        checkExport(gridIndex, exportRun);
        deleteExport(gridIndex, processId);
    }

    private void runExport(Long gridIndex, ExportRun exportRun) {
        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_EXPORT_ID_BASE + gridIndex)).click(); //TODO move AbstractSelenium.BUTTON_EXPORT_ID_BASE
        window.openModal(getRunExportElement());
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        new Select(seleniumSettings.getWebDriver().findElement(By.name("GridExportMode"))).selectByVisibleText(exportRun.getMode());
        new Select(seleniumSettings.getWebDriver().findElement(By.name("GridExportDelivery"))).selectByVisibleText(exportRun.getDelivery());
        seleniumSettings.getWebDriver().findElement(By.name("GridExportComment")).sendKeys(exportRun.getComments());
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    private String waitExportDone() {
        exportWait.waitExport();

        exportWait.waitExportDone();

        WebElement panel = seleniumSettings.getWebDriver().findElement(By.id("processEventList"));
        WebElement link = panel.findElement(By.className("ge_link"));
        String processId = link.getAttribute("onclick").replace("showExpDetails(", "").replace(")", "");

        elementHelper.moveToElementById("drop_down_overflow_" + processId);

        elementWaitHelper.waitElementById("ge_delete_" + processId);
        elementWaitHelper.waitElementVisibleById("ge_delete_" + processId);
        elementWaitHelper.waitElementDisplayById("ge_delete_" + processId);

        seleniumSettings.getWebDriver().findElement(By.id("ge_delete_" + processId)).click();

        wait.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();

        WebElement processListButton = seleniumSettings.getWebDriver().findElement(By.id("topPanelProcessContainer")).findElement(By.className("btn_input"));
        processListButton.click();

        return processId;
    }

    private void checkExport(Long gridIndex, ExportRun exportRun) {
        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_EXPORT_ID_BASE + gridIndex)).click();
        window.openModal(getExportHistoryElement());
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
        Long gridRows = gridHelper.getGridRowsCount(AbstractSeleniumCore.getGridIdx());
        Assert.assertEquals(gridRows, new Long(1L), "Grid have wrong rows count");

        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        assertHelper.AssertLink("gridPageName", exportRun.getGridPageName() + " ");
        assertHelper.AssertLink("trackorTypeName", exportRun.getTrackorTypeName());
        assertHelper.AssertLink("statusName", exportRun.getStatusName() + " ");
        assertHelper.AssertLink("exportTypeName", exportRun.getMode() + " ");
        assertHelper.AssertLink("exportDeliveryName", exportRun.getDelivery());
        assertHelper.AssertLink("un", seleniumSettings.getTestUser());
        assertHelper.AssertTextById("errorMessage", exportRun.getErrorMessage());
        assertHelper.AssertTextById("comments", exportRun.getComments());
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
    }

    private void deleteExport(Long gridIndex, String processId) {
        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_EXPORT_ID_BASE + gridIndex)).click();
        window.openModal(getExportHistoryElement());
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
        Long gridRows = gridHelper.getGridRowsCount(AbstractSeleniumCore.getGridIdx());
        Assert.assertEquals(gridRows, new Long(1L), "Grid have wrong rows count");

        checkbox.clickById("lblcb" + processId);
        elementWaitHelper.waitElementEnabledById(AbstractSeleniumCore.BUTTON_DELETE_ID_BASE + AbstractSeleniumCore.getGridIdx());
        elementHelper.click(seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_DELETE_ID_BASE + AbstractSeleniumCore.getGridIdx())));
        wait.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
        gridRows = gridHelper.getGridRowsCount(AbstractSeleniumCore.getGridIdx());
        Assert.assertEquals(gridRows, new Long(0L), "Grid have wrong rows count");

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
    }

    private WebElement getRunExportElement() {
        WebElement runExportElement = null;

        int i = 0;
        do {
            try {
                List<WebElement> elements = seleniumSettings.getWebDriver().findElements(By.className("sub_item_text"));
                for (WebElement element : elements) {
                    if ("Run Export".equals(element.getText())) {
                        runExportElement = element;
                    }
                }
            } catch (StaleElementReferenceException e) {
                
            }
            i++;
        } while (i < 5);

        Assert.assertNotNull(runExportElement, "Run Export button not found");
        return runExportElement;
    }

    private WebElement getExportHistoryElement() {
        WebElement exportHistoryElement = null;

        int i = 0;
        do {
            try {
                List<WebElement> elements = seleniumSettings.getWebDriver().findElements(By.className("sub_item_text"));
                for (WebElement element : elements) {
                    if ("Export History".equals(element.getText())) {
                        exportHistoryElement = element;
                    }
                }
            } catch (StaleElementReferenceException e) {
                
            }
            i++;
        } while (i < 5);

        Assert.assertNotNull(exportHistoryElement, "Export History button not found");
        return exportHistoryElement;
    }

}
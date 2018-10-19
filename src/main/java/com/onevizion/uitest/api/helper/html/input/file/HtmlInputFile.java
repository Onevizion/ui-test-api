package com.onevizion.uitest.api.helper.html.input.file;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.ElementWait;

@Component
public class HtmlInputFile {

    private static final String INPUT_FILE_ID_ON_BPL_IMPORT = "dlgFile";
    private static final String INPUT_FILE_ID_ON_RUN_IMPORT = "impFile";
    private static final String PARENT_ID_ON_ADMIN_REPORT = "TemplateFile";
    private static final String INPUT_FILE_ID_ON_ADMIN_REPORT = "inputTemplateFile";
    private static final String PARENT_ID_ON_BPD_ITEM = "FileUploader";
    private static final String INPUT_FILE_ID_ON_BPD_ITEM = "inputFileUploader";
    private static final String PARENT_ID_ON_ADMIN_CLIENT_FILE = "FileUploader";
    private static final String INPUT_FILE_ID_ON_ADMIN_CLIENT_FILE = "inputFileUploader";
    private static final String INPUT_FILE_ID_ON_DROP_GRID = "excelFile";
    private static final String INPUT_FILE_ID_ON_CASCADE_FIELD = "txtFile";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private HtmlInputFileJs htmlInputFileJs;

    @Resource
    private ElementWait elementWait;

    public void uploadOnBplImport(String value) {
        showOnBplImport();
        seleniumSettings.getWebDriver().findElement(By.id(INPUT_FILE_ID_ON_BPL_IMPORT)).sendKeys(seleniumSettings.getUploadFilesPath() + value);
        hideOnBplImport();
    }

    private void showOnBplImport() {
        htmlInputFileJs.showOnBplImport(INPUT_FILE_ID_ON_BPL_IMPORT);
        elementWait.waitElementVisibleById(INPUT_FILE_ID_ON_BPL_IMPORT);
        elementWait.waitElementDisplayById(INPUT_FILE_ID_ON_BPL_IMPORT);
    }

    private void hideOnBplImport() {
        htmlInputFileJs.hideOnBplImport(INPUT_FILE_ID_ON_BPL_IMPORT);
        elementWait.waitElementNotVisibleById(INPUT_FILE_ID_ON_BPL_IMPORT);
        elementWait.waitElementNotDisplayById(INPUT_FILE_ID_ON_BPL_IMPORT);
    }

    public void uploadOnRunImport(String value) {
        seleniumSettings.getWebDriver().findElement(By.id(INPUT_FILE_ID_ON_RUN_IMPORT)).sendKeys(seleniumSettings.getUploadFilesPath() + value);
    }

    public void uploadOnAdminReport(String value) {
        htmlInputFileJs.showInputForFile(PARENT_ID_ON_ADMIN_REPORT, INPUT_FILE_ID_ON_ADMIN_REPORT);
        seleniumSettings.getWebDriver().findElement(By.id(INPUT_FILE_ID_ON_ADMIN_REPORT)).sendKeys(seleniumSettings.getUploadFilesPath() + value);
    }

    public void uploadOnBpdItem(String value) {
        htmlInputFileJs.showInputForFile(PARENT_ID_ON_BPD_ITEM, INPUT_FILE_ID_ON_BPD_ITEM);
        seleniumSettings.getWebDriver().findElement(By.id(INPUT_FILE_ID_ON_BPD_ITEM)).sendKeys(seleniumSettings.getUploadFilesPath() + value);
    }

    public void uploadOnDropGrid(String value) {
        elementWait.waitElementById(INPUT_FILE_ID_ON_DROP_GRID);
        seleniumSettings.getWebDriver().findElement(By.id(INPUT_FILE_ID_ON_DROP_GRID)).sendKeys(seleniumSettings.getUploadFilesPath() + value);
    }

    public void uploadOnAdminClientFile(String value) {
        htmlInputFileJs.showInputForFile(PARENT_ID_ON_ADMIN_CLIENT_FILE, INPUT_FILE_ID_ON_ADMIN_CLIENT_FILE);
        seleniumSettings.getWebDriver().findElement(By.id(INPUT_FILE_ID_ON_ADMIN_CLIENT_FILE)).sendKeys(seleniumSettings.getUploadFilesPath() + value);
    }

    public void uploadOnCascadeField(String value) {
        //htmlInputFileJs.showInputForFile2(INPUT_FILE_ID_ON_CASCADE_FIELD);
        seleniumSettings.getWebDriver().findElement(By.id(INPUT_FILE_ID_ON_CASCADE_FIELD)).sendKeys(seleniumSettings.getUploadFilesPath() + value);
    }

    public void uploadOnForm(String fieldName, String value) {
        htmlInputFileJs.showInputForFileTb(fieldName);
        seleniumSettings.getWebDriver().switchTo().frame("ifrmHideForm");
        seleniumSettings.getWebDriver().findElement(By.name(fieldName)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(fieldName)).sendKeys(seleniumSettings.getUploadFilesPath() + value);
        //webDriver.switchTo().window((String) webDriver.getWindowHandles().toArray()[webDriver.getWindowHandles().size() - 1]);
        seleniumSettings.getWebDriver().switchTo().parentFrame(); /* For selenium tests in ie8*/
        htmlInputFileJs.hideInputForFileTb(fieldName);
    }

    public void uploadOnGrid(Long gridIndex, String fieldName, String value) {
        WebElement frame = (WebElement) htmlInputFileJs.getFrameForFileTbGrid(gridIndex);
        seleniumSettings.getWebDriver().switchTo().frame(frame);
        elementWait.waitElementByName(fieldName);
        seleniumSettings.getWebDriver().switchTo().parentFrame();
        htmlInputFileJs.showInputForFileTbGrid2(gridIndex, frame, fieldName);
        seleniumSettings.getWebDriver().switchTo().frame(frame);
        elementWait.waitElementVisibleByName(fieldName);
        elementWait.waitElementDisplayByName(fieldName);
        seleniumSettings.getWebDriver().findElement(By.name(fieldName)).sendKeys(seleniumSettings.getUploadFilesPath() + value);
        seleniumSettings.getWebDriver().switchTo().parentFrame();
        htmlInputFileJs.hideInputForFileTbGrid2(gridIndex, frame, fieldName);
    }

}
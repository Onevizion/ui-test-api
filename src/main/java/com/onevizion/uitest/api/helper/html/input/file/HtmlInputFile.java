package com.onevizion.uitest.api.helper.html.input.file;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.ElementWait;

@Component
public class HtmlInputFile {

    private static final String INPUT_FILE_ID_ON_BPL_IMPORT = "dlgFile";

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

}
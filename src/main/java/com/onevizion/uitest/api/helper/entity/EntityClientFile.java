package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.vo.entity.ClientFile;

@Component
public class EntityClientFile {

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private Js js;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Grid grid;

    @Resource
    private SeleniumSettings seleniumSettings;

    public void add(ClientFile clientFile) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        new Select(seleniumSettings.getWebDriver().findElement(By.name("clientFileGroupId"))).selectByVisibleText(clientFile.getFileGroup());

        js.showInputForFile("inputFileUploader", "FileUploader");
        seleniumSettings.getWebDriver().findElement(By.name("oldFileFileUploader")).sendKeys(seleniumSettings.getUploadFilesPath() + clientFile.getFileName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(ClientFile clientFile) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertSelect("clientFileGroupId", clientFile.getFileGroup());

        js.showInputForFile("inputFileUploader", "FileUploader");
        seleniumSettings.getWebDriver().findElement(By.name("oldFileFileUploader")).sendKeys(seleniumSettings.getUploadFilesPath() + clientFile.getFileName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(ClientFile clientFile) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertSelect("clientFileGroupId", clientFile.getFileGroup());
        assertElement.assertText("txtFileUploader", clientFile.getFileName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, ClientFile clientFile) {
        Map<Long, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "File Group"), clientFile.getFileGroup());
        gridVals.put(js.getColumnIndexByLabel(gridId, "File Name"), clientFile.getFileName());
        //TODO Size (Kb)
        //TODO Preview

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
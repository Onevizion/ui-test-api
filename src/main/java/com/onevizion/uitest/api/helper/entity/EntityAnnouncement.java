package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.form.Form;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.vo.entity.Announcement;

@Component
public class EntityAnnouncement {

    private static final String START_DATE = "scheduledDeliveryTs";
    private static final String FINISH_DATE = "expirationTs";
    private static final String TEXT = "msg";

    @Autowired
    private Form form;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Window window;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    public void add(Announcement announcement) {
        form.openAddWithoutTabs();

        seleniumSettings.getWebDriver().findElement(By.name(START_DATE)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(START_DATE)).sendKeys(announcement.getStartDate());
        seleniumSettings.getWebDriver().findElement(By.name(FINISH_DATE)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(FINISH_DATE)).sendKeys(announcement.getFinishDate());
        seleniumSettings.getWebDriver().findElement(By.name(TEXT)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(TEXT)).sendKeys(announcement.getText());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(Announcement announcement) {
        form.openEdit();

        seleniumSettings.getWebDriver().findElement(By.name(START_DATE)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(START_DATE)).sendKeys(announcement.getStartDate());
        seleniumSettings.getWebDriver().findElement(By.name(FINISH_DATE)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(FINISH_DATE)).sendKeys(announcement.getFinishDate());
        seleniumSettings.getWebDriver().findElement(By.name(TEXT)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(TEXT)).sendKeys(announcement.getText());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testOnForm(Announcement announcement) {
        form.openEdit();

        assertElement.assertText(START_DATE, announcement.getStartDate());
        assertElement.assertText(FINISH_DATE, announcement.getFinishDate());
        assertElement.assertText(TEXT, announcement.getText());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, int rowIndex, Announcement announcement) {
        Map<Integer, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Scheduled Delivery"), announcement.getStartDate());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Expiration"), announcement.getFinishDate());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Announcement Text"), announcement.getText());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class UsersSettingsHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private Window window;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    public void openUserSettings() {
        elementWaitHelper.waitElementById("topPanelUserNameLbl");
        elementWaitHelper.waitElementVisibleById("topPanelUserNameLbl");
        elementWaitHelper.waitElementDisplayById("topPanelUserNameLbl");

        window.openModal(By.id("topPanelUserNameLbl"));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();
    }

    public void closeUserSettings() {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));

        AbstractSeleniumCore.sleep(1000L); //this sleep need because in js code we have setTimeout

        waitHelper.waitWebElement(By.id("mainContainer"));
        waitHelper.waitWebElement(By.id("Table1"));
        waitHelper.waitWebElement(By.id("messageInfoDivContainer"));
        waitHelper.waitWebElement(By.id("messageErrorDivContainer"));
    }

    public void changeLanguage(String language) {
        new Select(seleniumSettings.getWebDriver().findElement(By.name("LanguageID"))).selectByVisibleText(language);
    }

    public void changeTimeFormat(String timeFormat) {
        new Select(seleniumSettings.getWebDriver().findElement(By.name("TimeFormat"))).selectByVisibleText(timeFormat);
    }

    public void changeDateFormat(String dateFormat) {
        new Select(seleniumSettings.getWebDriver().findElement(By.name("DateFormat"))).selectByVisibleText(dateFormat);
    }

    public void changeExactQuickSearchForClipboard(String exactQuickSearchForClipboard) {
        new Select(seleniumSettings.getWebDriver().findElement(By.name("exactSearchClipboard"))).selectByVisibleText(exactQuickSearchForClipboard);
    }

}
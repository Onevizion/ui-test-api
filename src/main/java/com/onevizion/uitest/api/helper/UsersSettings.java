package com.onevizion.uitest.api.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class UsersSettings {

    private static final String ID_USER = "topPanelUserNameLbl";
    private static final String ID_USERMENU = "userPopupMenu";
    private static final String ID_USERMENU_USERSETTINGS = "itemUserSettings";
    private static final String ID_USERMENU_LOGOFF = "itemLogoff";

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Wait wait;

    @Autowired
    private Window window;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private Element element;

    @Autowired
    private AssertElement assertElement;

    private ThreadLocal<WebElement> page = new ThreadLocal<>();

    public void openUserSettings() {
        page.remove();

        WebElement html = seleniumSettings.getWebDriver().findElement(By.tagName("html"));
        page.set(html);

        elementWait.waitElementById(ID_USER);
        elementWait.waitElementVisibleById(ID_USER);
        elementWait.waitElementDisplayById(ID_USER);

        element.clickById(ID_USER);

        elementWait.waitElementById(ID_USERMENU);
        elementWait.waitElementVisibleById(ID_USERMENU);
        elementWait.waitElementDisplayById(ID_USERMENU);

        elementWait.waitElementById(ID_USERMENU_USERSETTINGS);
        elementWait.waitElementVisibleById(ID_USERMENU_USERSETTINGS);
        elementWait.waitElementDisplayById(ID_USERMENU_USERSETTINGS);

        window.openModal(By.id(ID_USERMENU_USERSETTINGS));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void closeUserSettingsOkWithReloadPage() {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));

        elementWait.waitElementNotExist(page.get());

        wait.waitWebElement(By.id("mainContainer"));
        wait.waitWebElement(By.id("messageInfoDivContainer"));
        wait.waitWebElement(By.id("messageErrorDivContainer"));

        elementWait.waitElementById(ID_USER);
        elementWait.waitElementVisibleById(ID_USER);
        elementWait.waitElementDisplayById(ID_USER);

        wait.waitWebElement(By.id(ID_USERMENU_LOGOFF));
    }

    public void closeUserSettingsOkWithoutReloadPage() {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    public void closeUserSettingsCancel() {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
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
        element.moveToElementByName("exactSearchClipboard");
        new Select(seleniumSettings.getWebDriver().findElement(By.name("exactSearchClipboard"))).selectByVisibleText(exactQuickSearchForClipboard);
    }

    public void checkHideStartTaskDates(String hideStartTaskDates) {
        assertElement.assertSelect("HideStart", hideStartTaskDates);
    }

}
package com.onevizion.uitest.api.helper.mainmenu;

import javax.annotation.Resource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Wait;

@Component
public class MainMenu {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Wait wait;

    @Resource
    private Js js;

    @Resource
    private ElementWait elementWait;

    @Resource
    private MainMenuWait mainMenuWait;

    public static final String SHOW_MENU_BUTTON_CLASS = "showMenuBtn";
    public static final String HIDE_MENU_BUTTON_CLASS = "hideMenuBtn";

    public void showMenu() {
        WebElement menuButton = seleniumSettings.getWebDriver().findElement(By.id("newGui")).findElement(By.className(SHOW_MENU_BUTTON_CLASS));
        elementWait.waitElementVisible(menuButton);
        menuButton.click();

        elementWait.waitElementVelocityAnimatedFinishById("leftMenu");

        //WebElement menu = seleniumSettings.getWebDriver().findElement(By.id("leftMenuContainer")).findElement(By.id("leftMenu"));
        //elementWaitHelper.waitElementVisible(menu);
        //elementWaitHelper.waitElementVisible(menu.findElement(By.className(HIDE_MENU_BUTTON_CLASS)));
        //elementWaitHelper.waitElementVisible(menu.findElement(By.id("leftSearchMenuInput")));
    }

    public void hideMenu() {
        WebElement menuButton = seleniumSettings.getWebDriver().findElement(By.id("leftMenuContainer")).findElement(By.className(HIDE_MENU_BUTTON_CLASS));
        elementWait.waitElementVisible(menuButton);
        menuButton.click();

        elementWait.waitElementVelocityAnimatedFinishById("leftMenu");

        //elementWaitHelper.waitElementVisible(seleniumSettings.getWebDriver().findElement(By.id("newGui")).findElement(By.className(SHOW_MENU_BUTTON_CLASS)));
    }

    public void selectMenuItem(String item) {
        showMenu();

        WebElement menuItem = findMenuItem(item);
        menuItem.click();
        elementWait.waitElementVelocityAnimatedFinishById("leftMenu");
        waitPageTitle(item);
        wait.waitGridLoad(0L, 0L);
    }

    public void selectMenuItemWithTree(String item, String treeItem) {
        showMenu();

        WebElement menuItem = findMenuItem(item);
        if (menuItem.findElement(By.className("trackorTypeLabel")).getText().equals(treeItem)) {
            menuItem.click();
        } else {
            menuItem.findElement(By.className("newBtnWrapper")).click();
            elementWait.waitElementVelocityAnimatedFinishById("leftMenuTrackorTree");
            WebElement menuTreeItem = findMenuTreeItem(menuItem, treeItem);
            menuTreeItem.click();
            elementWait.waitElementVelocityAnimatedFinishById("leftMenuTrackorTree");
        }
        elementWait.waitElementVelocityAnimatedFinishById("leftMenu");
        waitPageTitle(item + " - " + treeItem);
        wait.waitGridLoad(0L, 0L);
    }

    private WebElement findMenuItem(String item) {
        WebElement searchField = seleniumSettings.getWebDriver().findElement(By.id("leftSearchMenuInput"));

        elementWait.waitElementVisible(searchField);
        searchField.clear();
        searchField.sendKeys(item);

        mainMenuWait.waitLeftMenuSearchUpdated();

        return seleniumSettings.getWebDriver().findElement(By.xpath(
                "//div[contains(@class, 'newGuiMenuRowContainer') and string(@showing)='1']"
              + "//a[contains(@class, 'aMenu')]"
              + "//span[contains(@class, 'menuItemWidthWrapper') and text()='" + item + "']"
              + "/ancestor::*[contains(@class, 'newGuiMenuRowContainer')]"));
    }

    private WebElement findMenuTreeItem(WebElement item, String treeItem) {
        WebElement treeSearchField = seleniumSettings.getWebDriver().findElement(By.id("treeSearchMenuInput"));

        elementWait.waitElementVisible(treeSearchField);
        treeSearchField.clear();
        treeSearchField.sendKeys(treeItem);

        

        return seleniumSettings.getWebDriver().findElement(By.xpath(
                "//div[@id='leftMenuTrackorTree' and @menurowid='" + item.getAttribute("rowid") + "']"
              + "//span[contains(@class, 'standartTreeRow') and not(contains(@class, 'disabled'))]"
              + "//label[text()='" + treeItem + "']"));
    }

    public void waitPageTitle(String title) {
        wait.waitWebElement(By.id("ttlPage"));
        mainMenuWait.waitPageTitle(title);
    }

}
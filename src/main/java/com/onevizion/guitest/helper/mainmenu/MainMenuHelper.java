package com.onevizion.guitest.helper.mainmenu;

import javax.annotation.Resource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.helper.ElementWaitHelper;
import com.onevizion.guitest.helper.JsHelper;
import com.onevizion.guitest.helper.WaitHelper;

@Component
public class MainMenuHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    @Resource
    private MainMenuWaitHelper mainMenuWaitHelper;

    public final static String SHOW_MENU_BUTTON_CLASS = "showMenuBtn";
    public final static String HIDE_MENU_BUTTON_CLASS = "hideMenuBtn";

    public void showMenu() {
        WebElement menuButton = seleniumSettings.getWebDriver().findElement(By.id("newGui")).findElement(By.className(SHOW_MENU_BUTTON_CLASS));
        elementWaitHelper.waitElementVisible(menuButton);
        menuButton.click();

        elementWaitHelper.waitElementVelocityAnimatedFinishById("leftMenu");

        //WebElement menu = seleniumSettings.getWebDriver().findElement(By.id("leftMenuContainer")).findElement(By.id("leftMenu"));
        //elementWaitHelper.waitElementVisible(menu);
        //elementWaitHelper.waitElementVisible(menu.findElement(By.className(HIDE_MENU_BUTTON_CLASS)));
        //elementWaitHelper.waitElementVisible(menu.findElement(By.id("leftSearchMenuInput")));
    }

    public void hideMenu() {
        WebElement menuButton = seleniumSettings.getWebDriver().findElement(By.id("leftMenuContainer")).findElement(By.className(HIDE_MENU_BUTTON_CLASS));
        elementWaitHelper.waitElementVisible(menuButton);
        menuButton.click();

        elementWaitHelper.waitElementVelocityAnimatedFinishById("leftMenu");

        //elementWaitHelper.waitElementVisible(seleniumSettings.getWebDriver().findElement(By.id("newGui")).findElement(By.className(SHOW_MENU_BUTTON_CLASS)));
    }

    public void selectMenuItem(String item) {
        showMenu();

        WebElement menuItem = findMenuItem(item);
        menuItem.click();
        elementWaitHelper.waitElementVelocityAnimatedFinishById("leftMenu");
        waitPageTitle(item);
        waitHelper.waitGridLoad(0L, 0L);
    }

    public void selectMenuItemWithTree(String item, String treeItem) {
        showMenu();

        WebElement menuItem = findMenuItem(item);
        if (menuItem.findElement(By.className("trackorTypeLabel")).getText().equals(treeItem)) {
            menuItem.click();
        } else {
            menuItem.findElement(By.className("newBtnWrapper")).click();
            elementWaitHelper.waitElementVelocityAnimatedFinishById("leftMenuTrackorTree");
            WebElement menuTreeItem = findMenuTreeItem(menuItem, treeItem);
            menuTreeItem.click();
            elementWaitHelper.waitElementVelocityAnimatedFinishById("leftMenuTrackorTree");
        }
        elementWaitHelper.waitElementVelocityAnimatedFinishById("leftMenu");
        waitPageTitle(item + " - " + treeItem);
        waitHelper.waitGridLoad(0L, 0L);
    }

    private WebElement findMenuItem(String item) {
        WebElement searchField = seleniumSettings.getWebDriver().findElement(By.id("leftSearchMenuInput"));

        elementWaitHelper.waitElementVisible(searchField);
        searchField.clear();
        searchField.sendKeys(item);

        mainMenuWaitHelper.waitLeftMenuSearchUpdated();

        WebElement menuItem = seleniumSettings.getWebDriver().findElement(By.xpath(
                "//div[contains(@class, 'newGuiMenuRowContainer') and string(@showing)='1']"
              + "//a[contains(@class, 'aMenu')]"
              + "//span[contains(@class, 'menuItemWidthWrapper') and text()='" + item + "']"
              + "/ancestor::*[contains(@class, 'newGuiMenuRowContainer')]"));
        return menuItem;
    }

    private WebElement findMenuTreeItem(WebElement item, String treeItem) {
        WebElement treeSearchField = seleniumSettings.getWebDriver().findElement(By.id("treeSearchMenuInput"));

        elementWaitHelper.waitElementVisible(treeSearchField);
        treeSearchField.clear();
        treeSearchField.sendKeys(treeItem);

        

        WebElement treeMenuItem = seleniumSettings.getWebDriver().findElement(By.xpath(
                "//div[@id='leftMenuTrackorTree' and @menurowid='" + item.getAttribute("rowid") + "']"
              + "//span[contains(@class, 'standartTreeRow') and not(contains(@class, 'disabled'))]"
              + "//label[text()='" + treeItem + "']"));
        return treeMenuItem;
    }

    private void waitPageTitle(String title) {
        waitHelper.waitWebElement(By.id("ttlPage"));
        mainMenuWaitHelper.waitPageTitle(title);
    }

}
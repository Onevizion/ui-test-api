package com.onevizion.uitest.api.helper.mainmenu;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.tree.Tree;

@Component
public class MainMenu {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Wait wait;

    @Resource
    private Grid2 grid2;

    @Resource
    private ElementWait elementWait;

    @Resource
    private MainMenuWait mainMenuWait;

    @Resource
    private Tree tree;

    @Resource
    private Element element;

    private static final String ID_MENU_BUTTON = "mainLogo";
    private static final String ID_MENU = "menu";
    private static final String CLASS_MENU_SEARCH = "in_input";
    private static final String CLASS_MENU_ITEM = "item_menu";
    private static final String CLASS_MENU_ITEM_PAGE = "link";
    private static final String CLASS_MENU_ITEM_PAGE_HIDDEN = "hidden";
    private static final String CLASS_MENU_ITEM_PAGE_NAME = "im_name";
    private static final String CLASS_MENU_ITEM_PAGE_TT = "im_ttype";
    private static final String CLASS_MENU_ITEM_TREE = "btn_input";
    private static final String ID_TREE = "tree";
    private static final String CLASS_TREE_SEARCH = "in_input";

    public void showMenu() {
        WebElement menuButton = seleniumSettings.getWebDriver().findElement(By.id(ID_MENU_BUTTON));
        elementWait.waitElementVisible(menuButton);
        menuButton.click();

        elementWait.waitElementVelocityAnimatedFinishById(ID_MENU);
        elementWait.waitElementVisibleById(ID_MENU);
        elementWait.waitElementDisplayById(ID_MENU);
    }

    public void hideMenu() {
        WebElement menuButton = seleniumSettings.getWebDriver().findElement(By.id(ID_MENU_BUTTON));
        elementWait.waitElementVisible(menuButton);
        menuButton.click();

        elementWait.waitElementVelocityAnimatedFinishById(ID_MENU);
        elementWait.waitElementNotVisibleById(ID_MENU);
        elementWait.waitElementNotDisplayById(ID_MENU);
    }

    public void openMenuItemAndWaitGridLoad(String item) {
        openMenuItem(item);
        waitPageTitle(item);
        grid2.waitLoad();
    }

    public void openMenuItem(String item) {
        showMenu();

        WebElement menuItem = findMenuItem(item);
        element.moveToElement(menuItem);
        menuItem.click();
        elementWait.waitElementVelocityAnimatedFinishById(ID_MENU);
        elementWait.waitElementNotVisibleById(ID_MENU);
        elementWait.waitElementNotDisplayById(ID_MENU);
    }

    public void openMenuItemWithTreeAndWaitGridLoad(String item, String treeItem) {
        openMenuItemWithTree(item, treeItem);
        waitPageTitle(item + " - " + treeItem);
        grid2.waitLoad();
    }

    public void openMenuItemWithTree(String item, String treeItem) {
        showMenu();

        WebElement menuItem = findMenuItem(item);
        element.moveToElement(menuItem);
        String menuPageTt = menuItem.findElement(By.className(CLASS_MENU_ITEM_PAGE_TT)).getAttribute("textContent");
        if (treeItem.equals(menuPageTt)) {
            menuItem.click();
        } else {
            menuItem.findElement(By.className(CLASS_MENU_ITEM_TREE)).click();
            elementWait.waitElementVelocityAnimatedFinishById(ID_TREE);
            elementWait.waitElementVisibleById(ID_TREE);
            elementWait.waitElementDisplayById(ID_TREE);
            WebElement menuTreeItem = findMenuTreeItem(treeItem);
            element.moveToElement(menuTreeItem);
            menuTreeItem.click();
            elementWait.waitElementVelocityAnimatedFinishById(ID_TREE);
            elementWait.waitElementNotVisibleById(ID_TREE);
            elementWait.waitElementNotDisplayById(ID_TREE);
        }
        elementWait.waitElementVelocityAnimatedFinishById(ID_MENU);
        elementWait.waitElementNotVisibleById(ID_MENU);
        elementWait.waitElementNotDisplayById(ID_MENU);
        waitPageTitle(item + " - " + treeItem);
        grid2.waitLoad();
    }

    private WebElement findMenuItem(String item) {
        WebElement searchField = seleniumSettings.getWebDriver().findElement(By.id(ID_MENU)).findElement(By.className(CLASS_MENU_SEARCH));

        elementWait.waitElementVisible(searchField);
        searchField.clear();
        searchField.sendKeys(item);

        List<WebElement> menuPages = new ArrayList<WebElement>();
        List<WebElement> menuItems = seleniumSettings.getWebDriver().findElements(By.className(CLASS_MENU_ITEM));
        for (WebElement menuItem : menuItems) {
            String menuItemClass = menuItem.getAttribute("class");
            if (menuItemClass.contains(CLASS_MENU_ITEM_PAGE) && !menuItemClass.contains(CLASS_MENU_ITEM_PAGE_HIDDEN)) {
                menuPages.add(menuItem);
            }
        }

        WebElement result = null;
        for (WebElement menuPage : menuPages) {
            String menuPageName = menuPage.findElement(By.className(CLASS_MENU_ITEM_PAGE_NAME)).getAttribute("textContent");
            if (item.equals(menuPageName)) {
                result = menuPage;
            }
        }

        if (result == null) {
            throw new SeleniumUnexpectedException("Menu Page [" + item + "] not found");
        }

        return result;
    }

    private WebElement findMenuTreeItem(String treeItem) {
        WebElement treeSearchField = seleniumSettings.getWebDriver().findElement(By.id(ID_TREE)).findElement(By.className(CLASS_TREE_SEARCH));

        elementWait.waitElementVisible(treeSearchField);
        treeSearchField.clear();
        treeSearchField.sendKeys(treeItem);

        String webElementId = null;
        String itemsStr = tree.getAllSubItems("MT", "-1");
        String[] items = itemsStr.split(",");
        for (String item : items) {
            String itemText = tree.getItemTextById("MT", item);
            itemText = itemText.replaceAll("^<[iI][nN][pP][uU][tT].*?>", "");
            itemText = itemText.replaceAll("&nbsp;", "");
            itemText = itemText.replaceAll("^<[lL][aA][bB][eE][lL].*?>", "").replaceAll("</[lL][aA][bB][eE][lL]>$", "");
            if (treeItem.equals(itemText)) {
                if (webElementId == null) {
                    webElementId = item;
                }
            }
        }

        if (webElementId == null) {
            throw new SeleniumUnexpectedException("Trackor Type [" + treeItem + "] in Menu Page not found");
        }

        return seleniumSettings.getWebDriver().findElement(By.id("lblRdTree_" + webElementId));
    }

    public void waitPageAndTabTitle(String pageTitle, String tabTitle) {
        waitPageTitle(pageTitle);
        waitTabTitle(tabTitle);
    }

    public void waitPageTitle(String title) {
        wait.waitWebElement(By.id("ttlPage"));
        mainMenuWait.waitPageTitle(title);
    }

    public void waitTabTitle(String title) {
        wait.waitWebElement(By.id("ttlTab"));
        mainMenuWait.waitTabTitle(title);
    }

}
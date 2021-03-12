package com.onevizion.uitest.api.helper.mainmenu;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.tree.Tree;

@Component
public class MainMenu {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Wait wait;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private MainMenuWait mainMenuWait;

    @Autowired
    private Tree tree;

    @Autowired
    private Element element;

    public static final String MENU_FAVORITES = "Favorites";
    public static final String MENU_HELP = "Help";
    public static final String MENU_INFO_CENTER = "Info Center";
    public static final String MENU_DEV_CENTER = "Dev Center";
    public static final String MENU_ADMIN_CENTER = "Admin Center";

    private static final String ID_MENU_BUTTON = "mainLogo";
    private static final String NAME_MENU_BUTTON = "mainMenuButton";

    private static final String ID_MENU = "menu";
    private static final String CLASS_MENU_SEARCH = "in_input";
    private static final String ID_MENU_CLEAR_SEARCH_BUTTON = "clear_search_";
    private static final String NAME_MENU_ITEM = "menuItem";
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

    public void clearSearch() {
        showMenu();
        clearSearchMenuItem();
        hideMenu();
    }

    public void showMenu(String name) {
        WebElement result = null;

        List<WebElement> menus = seleniumSettings.getWebDriver().findElements(By.name(NAME_MENU_BUTTON));
        for (WebElement menu : menus) {
            if (name.equals(menu.getAttribute("title"))) {
                if (result != null) {
                    throw new SeleniumUnexpectedException("Menu with name [" + name + "] found many times");
                }
                result = menu;
            }
        }

        if (result == null) {
            throw new SeleniumUnexpectedException("Menu with name [" + name + "] not found");
        }

        result.click();

        elementWait.waitElementVelocityAnimatedFinishById(ID_MENU);
        elementWait.waitElementVisibleById(ID_MENU);
        elementWait.waitElementDisplayById(ID_MENU);
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

    public void checkMenuItemExist(String item) {
        showMenu();
        setSearchMenuItem(item);
        List<WebElement> menuItems = getMenuItems();
        boolean result = isMenuItemExist(menuItems, item);
        Assert.assertEquals(result, true);
        hideMenu();
    }

    public void checkMenuItemNotExist(String item) {
        showMenu();
        setSearchMenuItem(item);
        List<WebElement> menuItems = getMenuItems();
        boolean result = isMenuItemExist(menuItems, item);
        Assert.assertEquals(result, false);
        hideMenu();
    }

    public WebElement findMenuItem(String item) {
        setSearchMenuItem(item);
        List<WebElement> menuItems = getMenuItems();

        WebElement result = null;
        for (WebElement menuItem : menuItems) {
            String menuItemName = menuItem.findElement(By.className(CLASS_MENU_ITEM_PAGE_NAME)).getAttribute("textContent");
            if (item.equals(menuItemName)) {
                result = menuItem;
            }
        }

        if (result == null) {
            throw new SeleniumUnexpectedException("Menu Page [" + item + "] not found");
        }

        return result;
    }

    private void setSearchMenuItem(String item) {
        WebElement searchField = seleniumSettings.getWebDriver().findElement(By.id(ID_MENU)).findElement(By.className(CLASS_MENU_SEARCH));

        elementWait.waitElementVisible(searchField);
        searchField.clear();
        searchField.sendKeys(item);
    }

    private void clearSearchMenuItem() {
        WebElement clearSearchButton = seleniumSettings.getWebDriver().findElement(By.id(ID_MENU)).findElement(By.id(ID_MENU_CLEAR_SEARCH_BUTTON));

        elementWait.waitElementVisible(clearSearchButton);
        clearSearchButton.click();
    }

    public List<WebElement> getMenuItems() {
        List<WebElement> result = new ArrayList<WebElement>();

        List<WebElement> menuItems = seleniumSettings.getWebDriver().findElements(By.name(NAME_MENU_ITEM));
        for (WebElement menuItem : menuItems) {
            String menuItemClass = menuItem.getAttribute("class");
            if (!menuItemClass.contains(CLASS_MENU_ITEM_PAGE_HIDDEN)) {
                result.add(menuItem);
            }
        }

        return result;
    }

    private boolean isMenuItemExist(List<WebElement> menuItems, String item) {
        boolean result = false;

        for (WebElement menuItem : menuItems) {
            String menuItemName = menuItem.findElement(By.className(CLASS_MENU_ITEM_PAGE_NAME)).getAttribute("textContent");
            if (item.equals(menuItemName)) {
                result = true;
            }
        }

        return result;
    }

    private WebElement findMenuTreeItem(String treeItem) {
        searchMenuTreeItem(treeItem);

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

    private void searchMenuTreeItem(String treeItem) {
        WebElement treeSearchField = seleniumSettings.getWebDriver().findElement(By.id(ID_TREE)).findElement(By.className(CLASS_TREE_SEARCH));

        elementWait.waitElementVisible(treeSearchField);
        treeSearchField.clear();
        treeSearchField.sendKeys(treeItem);
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

    public int getDinamicMenusCount() {
        return seleniumSettings.getWebDriver().findElements(By.className("dynamic_menu")).size();
    }

    public void waitDinamicMenusCount(int dinamicMenusCount) {
        mainMenuWait.waitDinamicMenusCount(dinamicMenusCount);
    }

}
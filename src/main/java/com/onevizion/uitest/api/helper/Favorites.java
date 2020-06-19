package com.onevizion.uitest.api.helper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.helper.mainmenu.MainMenu;
import com.onevizion.uitest.api.helper.tree.Tree;

@Component
public class Favorites {

    private static final String ID_ADD = "addToFavoritesButton";
    private static final String ID_ORGANIZE = "btnOrganizeFavorites";

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private Element element;

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private Checkbox checkbox;

    @Autowired
    private Tree tree;

    @Autowired
    private Jquery jquery;

    @Autowired
    private MainMenu mainMenu;

    public void checkFavoritesCount(int expectedCount) {
        mainMenu.showMenu(MainMenu.MENU_FAVORITES);
        List<WebElement> favorites = mainMenu.getMenuItems();
        Assert.assertEquals(favorites.size(), expectedCount);
        mainMenu.hideMenu();
    }

    public void checkFavorite(int position, String expectedLabel) {
        mainMenu.showMenu(MainMenu.MENU_FAVORITES);
        List<WebElement> favorites = mainMenu.getMenuItems();
        String menuItemName = favorites.get(position - 1).findElement(By.className("im_name")).getAttribute("textContent");
        Assert.assertEquals(menuItemName, expectedLabel);
        mainMenu.hideMenu();
    }

    public void add(String name, boolean useView, boolean useFilter) {
        window.openModal(By.id(ID_ADD));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.id("name")).clear();
        seleniumSettings.getWebDriver().findElement(By.id("name")).sendKeys(name);

        if ((useView && !checkbox.isCheckedByName("cbAddView"))
                || (!useView && checkbox.isCheckedByName("cbAddView"))) {
            checkbox.clickByName("cbAddView");
        }

        if ((useFilter && !checkbox.isCheckedByName("cbAddFilter"))
                || (!useFilter && checkbox.isCheckedByName("cbAddFilter"))) {
            checkbox.clickByName("cbAddFilter");
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        jquery.waitLoad();
    }

    public void reorderMoveUp(String name) {
        mainMenu.showMenu(MainMenu.MENU_FAVORITES);
        window.openModal(By.id(ID_ORGANIZE));
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        wait.waitFormLoad();
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));

        selectFavoriteInOrganize(name);

        seleniumSettings.getWebDriver().findElement(By.name(AbstractSeleniumCore.BUTTON_UP_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx())).click();
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        jquery.waitLoad();
    }

    public void reorderMoveDown(String name) {
        mainMenu.showMenu(MainMenu.MENU_FAVORITES);
        window.openModal(By.id(ID_ORGANIZE));
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        wait.waitFormLoad();
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));

        selectFavoriteInOrganize(name);

        seleniumSettings.getWebDriver().findElement(By.name(AbstractSeleniumCore.BUTTON_DOWN_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx())).click();
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        jquery.waitLoad();
    }

    public void delete(String name) {
        mainMenu.showMenu(MainMenu.MENU_FAVORITES);
        window.openModal(By.id(ID_ORGANIZE));
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        wait.waitFormLoad();
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));

        selectFavoriteInOrganize(name);

        seleniumSettings.getWebDriver().findElement(By.name(AbstractSeleniumCore.BUTTON_DELETE_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx())).click();
        wait.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        jquery.waitLoad();
    }

    private void selectFavoriteInOrganize(String name) {
        boolean favoriteFound = false;

        String itemsStr = tree.getAllSubItems(AbstractSeleniumCore.getTreeIdx(), "-1");
        String[] items = itemsStr.split(",");
        for (String item : items) {
            if (name.equals(tree.getItemTextById(AbstractSeleniumCore.getTreeIdx(), item))) {
                favoriteFound = true;
                tree.selectItem(AbstractSeleniumCore.getTreeIdx(), item);
            }
        }

        if (!favoriteFound) {
            throw new SeleniumUnexpectedException("Favorite not found in organize");
        }
    }

}
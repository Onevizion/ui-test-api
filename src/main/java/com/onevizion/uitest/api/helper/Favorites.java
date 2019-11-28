package com.onevizion.uitest.api.helper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.helper.tree.Tree;

@Component
public class Favorites {

    private static final String ID_FAVORITES = "topPanelBtnFavorites";
    private static final String ID_FAVORITESMENU = "favoritesPopupMenu";
    private static final String ID_FAVORITESMENU_ADD = "btnAddPageToFavorites";
    private static final String ID_FAVORITESMENU_ORGANIZE = "btnOrganizeFavorites";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementWait elementWait;

    @Resource
    private Element element;

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private Checkbox checkbox;

    @Resource
    private Tree tree;

    @Resource
    private Jquery jquery;

    public void checkFavoritesCount(int expectedCount) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int actualCount = seleniumSettings.getWebDriver().findElements(By.className("item_favorites")).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        Assert.assertEquals(actualCount, expectedCount);
    }

    public void checkFavorite(int position, String expectedLabel) {
        String actualLabel = seleniumSettings.getWebDriver().findElements(By.className("item_favorites")).get(position - 1).getAttribute("innerText");

        Assert.assertEquals(actualLabel, expectedLabel);
    }

    public void add(String name, boolean useView, boolean useFilter) {
        elementWait.waitElementById(ID_FAVORITES);
        elementWait.waitElementVisibleById(ID_FAVORITES);
        elementWait.waitElementDisplayById(ID_FAVORITES);

        element.clickById(ID_FAVORITES);

        elementWait.waitElementById(ID_FAVORITESMENU);
        elementWait.waitElementVisibleById(ID_FAVORITESMENU);
        elementWait.waitElementDisplayById(ID_FAVORITESMENU);

        elementWait.waitElementById(ID_FAVORITESMENU_ADD);
        elementWait.waitElementVisibleById(ID_FAVORITESMENU_ADD);
        elementWait.waitElementDisplayById(ID_FAVORITESMENU_ADD);

        window.openModal(By.id(ID_FAVORITESMENU_ADD));
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

    public void open(String name) {
        elementWait.waitElementById(ID_FAVORITES);
        elementWait.waitElementVisibleById(ID_FAVORITES);
        elementWait.waitElementDisplayById(ID_FAVORITES);

        element.clickById(ID_FAVORITES);

        elementWait.waitElementById(ID_FAVORITESMENU);
        elementWait.waitElementVisibleById(ID_FAVORITESMENU);
        elementWait.waitElementDisplayById(ID_FAVORITESMENU);

        WebElement favorite = getFavorite(name);
        favorite.click();
    }

    public void reorderMoveUp(String name) {
        elementWait.waitElementById(ID_FAVORITES);
        elementWait.waitElementVisibleById(ID_FAVORITES);
        elementWait.waitElementDisplayById(ID_FAVORITES);

        element.clickById(ID_FAVORITES);

        elementWait.waitElementById(ID_FAVORITESMENU);
        elementWait.waitElementVisibleById(ID_FAVORITESMENU);
        elementWait.waitElementDisplayById(ID_FAVORITESMENU);

        elementWait.waitElementById(ID_FAVORITESMENU_ORGANIZE);
        elementWait.waitElementVisibleById(ID_FAVORITESMENU_ORGANIZE);
        elementWait.waitElementDisplayById(ID_FAVORITESMENU_ORGANIZE);

        window.openModal(By.id(ID_FAVORITESMENU_ORGANIZE));
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
        elementWait.waitElementById(ID_FAVORITES);
        elementWait.waitElementVisibleById(ID_FAVORITES);
        elementWait.waitElementDisplayById(ID_FAVORITES);

        element.clickById(ID_FAVORITES);

        elementWait.waitElementById(ID_FAVORITESMENU);
        elementWait.waitElementVisibleById(ID_FAVORITESMENU);
        elementWait.waitElementDisplayById(ID_FAVORITESMENU);

        elementWait.waitElementById(ID_FAVORITESMENU_ORGANIZE);
        elementWait.waitElementVisibleById(ID_FAVORITESMENU_ORGANIZE);
        elementWait.waitElementDisplayById(ID_FAVORITESMENU_ORGANIZE);

        window.openModal(By.id(ID_FAVORITESMENU_ORGANIZE));
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
        elementWait.waitElementById(ID_FAVORITES);
        elementWait.waitElementVisibleById(ID_FAVORITES);
        elementWait.waitElementDisplayById(ID_FAVORITES);

        element.clickById(ID_FAVORITES);

        elementWait.waitElementById(ID_FAVORITESMENU);
        elementWait.waitElementVisibleById(ID_FAVORITESMENU);
        elementWait.waitElementDisplayById(ID_FAVORITESMENU);

        elementWait.waitElementById(ID_FAVORITESMENU_ORGANIZE);
        elementWait.waitElementVisibleById(ID_FAVORITESMENU_ORGANIZE);
        elementWait.waitElementDisplayById(ID_FAVORITESMENU_ORGANIZE);

        window.openModal(By.id(ID_FAVORITESMENU_ORGANIZE));
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

    private WebElement getFavorite(String name) {
        List<WebElement> favorites = seleniumSettings.getWebDriver().findElements(By.className("item_favorites"));
        for (WebElement favorite : favorites) {
            if (name.equals(favorite.getAttribute("innerText"))) {
                return favorite;
            }
        }
        return null;
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
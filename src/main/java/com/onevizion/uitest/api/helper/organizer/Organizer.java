package com.onevizion.uitest.api.helper.organizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.page.button.PageButton;
import com.onevizion.uitest.api.helper.tree.Tree;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Wait;

@Component
public class Organizer {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Js js;

    @Autowired
    private Wait wait;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private Window window;

    @Autowired
    private Tree tree;

    @Autowired
    private PageButton pageButton;

    private static final String DIALOG_ID = "formOrganizeDialog";
    private static final String DIALOG_NAME_ID = "folderName";
    private static final String DIALOG_BUTTON_OK_ID = "btnDialogOk";

    public void openOrganizer(String id) {
        window.openModal(By.id(id));
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        wait.waitFormLoad();
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void closeOrganizer() {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void add(String folderName, String parentFolderName) {
        folderName = folderName.startsWith(AbstractSeleniumCore.PREFIX_GLOBAL) ? folderName.substring(AbstractSeleniumCore.PREFIX_GLOBAL.length()) : folderName;
        folderName = folderName.startsWith(AbstractSeleniumCore.PREFIX_LOCAL) ? folderName.substring(AbstractSeleniumCore.PREFIX_LOCAL.length()) : folderName;

        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_ADD_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx())).click();
        WebElement dialog = seleniumSettings.getWebDriver().findElement(By.id(DIALOG_ID));
        elementWait.waitElementVisible(dialog);
        elementWait.waitElementDisplay(dialog);

        dialog.findElement(By.id(DIALOG_NAME_ID)).clear();
        dialog.findElement(By.id(DIALOG_NAME_ID)).sendKeys(folderName);
        selectParentFolderInDialog(dialog, parentFolderName);
        dialog.findElement(By.id(DIALOG_BUTTON_OK_ID)).click();

        wait.waitLoadingLoad();
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
    }

    public void edit(String oldName, String newName, String parentFolderName) {
        newName = newName.startsWith(AbstractSeleniumCore.PREFIX_GLOBAL) ? newName.substring(AbstractSeleniumCore.PREFIX_GLOBAL.length()) : newName;
        newName = newName.startsWith(AbstractSeleniumCore.PREFIX_LOCAL) ? newName.substring(AbstractSeleniumCore.PREFIX_LOCAL.length()) : newName;

        selectItemInTree(oldName);

        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_EDIT_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx())).click();
        WebElement dialog = seleniumSettings.getWebDriver().findElement(By.id(DIALOG_ID));
        elementWait.waitElementVisible(dialog);
        elementWait.waitElementDisplay(dialog);

        dialog.findElement(By.id(DIALOG_NAME_ID)).clear();
        dialog.findElement(By.id(DIALOG_NAME_ID)).sendKeys(newName);
        selectParentFolderInDialog(dialog, parentFolderName);
        dialog.findElement(By.id(DIALOG_BUTTON_OK_ID)).click();

        wait.waitLoadingLoad();
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
    }

    public void delete(String itemName) {
        selectItemInTree(itemName);
        pageButton.clickDeleteTree(AbstractSeleniumCore.getTreeIdx());
    }

    public void moveUp(String itemName) {
        selectItemInTree(itemName);
        pageButton.clickUpTree(AbstractSeleniumCore.getTreeIdx());
    }

    public void moveDown(String itemName) {
        selectItemInTree(itemName);
        pageButton.clickDownTree(AbstractSeleniumCore.getTreeIdx());
    }

    public void promoteToGlobal(String oldtemName, String newItemName, String parentFolderName) {
        newItemName = newItemName.startsWith(AbstractSeleniumCore.PREFIX_GLOBAL) ? newItemName.substring(AbstractSeleniumCore.PREFIX_GLOBAL.length()) : newItemName;
        newItemName = newItemName.startsWith(AbstractSeleniumCore.PREFIX_LOCAL) ? newItemName.substring(AbstractSeleniumCore.PREFIX_LOCAL.length()) : newItemName;

        selectItemInTree(oldtemName);

        pageButton.clickSaveAsGlobalTree(AbstractSeleniumCore.getTreeIdx());
        WebElement dialog = seleniumSettings.getWebDriver().findElement(By.id(DIALOG_ID));
        elementWait.waitElementVisible(dialog);
        elementWait.waitElementDisplay(dialog);

        dialog.findElement(By.id(DIALOG_NAME_ID)).clear();
        dialog.findElement(By.id(DIALOG_NAME_ID)).sendKeys(newItemName);
        selectParentFolderInDialog(dialog, parentFolderName);
        dialog.findElement(By.id(DIALOG_BUTTON_OK_ID)).click();

        wait.waitLoadingLoad();
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
    }

    public void checkSubItems(String itemName, List<String> expectedItems) {
        List<String> actualItems = getSubItems(itemName);
        Assert.assertEquals(actualItems, expectedItems);
    }

    private List<String> getSubItems(String itemName) {
        String treeItemId = tree.getItemIdByText(AbstractSeleniumCore.getTreeIdx(), itemName);

        String treeItemsIdsStr = tree.getSubItems(AbstractSeleniumCore.getTreeIdx(), treeItemId);
        if (treeItemsIdsStr.equals("")) {
            return Collections.emptyList();
        }

        List<String> treeItemsIds = Arrays.asList(treeItemsIdsStr.split(","));
        List<String> treeItems = new ArrayList<String>();
        for (String treeItemsId : treeItemsIds) {
            treeItems.add(tree.getItemTextById(AbstractSeleniumCore.getTreeIdx(), treeItemsId));
        }
        return treeItems;
    }

    private void selectItemInTree(String itemText) {
        String itemId = tree.getItemIdByText(AbstractSeleniumCore.getTreeIdx(), itemText);
        tree.selectItem(AbstractSeleniumCore.getTreeIdx(), itemId);
    }

    private void selectParentFolderInDialog(WebElement dialog, String parentFolderName) {
        String selectedItem = dialog.findElement(By.className("newGenericDropDownLabel")).getText();
        if (parentFolderName.equals(selectedItem)) {
            return;
        }

        String dropDownId = dialog.findElement(By.className("newGenericDropDown")).getAttribute("id");

        dialog.findElement(By.id(dropDownId)).click();
        elementWait.waitElementVisibleById(dropDownId + "Container");
        elementWait.waitElementDisplayById(dropDownId + "Container");

        dialog.findElement(By.id(dropDownId + "Search")).sendKeys(parentFolderName);

        WebElement parentFolder = (WebElement) js.getNewDropDownElement(dropDownId, "newGenericDropDownContainer", "newGenericDropDownRow", parentFolderName);
        elementWait.waitElementVisible(parentFolder);
        parentFolder.click();
    }

}
package com.onevizion.uitest.api.helper.organizer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
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

    private static final String INPUT_FOLDER_NAME_ID = "folderName";
    private static final String FORM_DIALOG_ID = "formOrganizeDialogContainer";
    private static final String BUTTON_DIALOG_OK_ID = "btnDialogOk";

    public void openOrganizer(String id) {
        window.openModal(By.id(id));
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        wait.waitFormLoad();
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void closeOrganizer() {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void addFolder(String folderName, String folderParentName) {
        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_ADD_TREE_ID_BASE + AbstractSeleniumCore.getTreeIdx())).click();
        WebElement dialog = seleniumSettings.getWebDriver().findElement(By.id(FORM_DIALOG_ID));
        elementWait.waitElementVisible(dialog);
        elementWait.waitElementDisplay(dialog);

        dialog.findElement(By.id(INPUT_FOLDER_NAME_ID)).sendKeys(folderName);
        selectParentFolderByVisibleText(dialog, folderParentName);
        dialog.findElement(By.id(BUTTON_DIALOG_OK_ID)).click();

        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
    }

    private void selectParentFolderByVisibleText(WebElement dialog, String folderParentName) {
        String dropDownId = dialog.findElement(By.className("newGenericDropDown")).getAttribute("id");

        String selectedItem = dialog.findElement(By.className("newGenericDropDownLabel")).getText();
        if(folderParentName.equals(selectedItem)) {
            return;
        }

        dialog.findElement(By.className("newGenericDropDownLabel")).click(); //TODO bug? when clicking on drop-down by id - element is not interactable exception
        elementWait.waitElementVisibleById(dropDownId + "Container");
        elementWait.waitElementDisplayById(dropDownId + "Container");

        dialog.findElement(By.id(dropDownId + "Search")).sendKeys(folderParentName);

        WebElement parentFolder = (WebElement) js.getNewDropDownElement(dropDownId, "newGenericDropDownContainer", "newGenericDropDownRow", folderParentName);
        elementWait.waitElementVisible(parentFolder);
        parentFolder.click();
    }

    private void selectItem(String itemName) {
        boolean itemFound = false;

        String globalItemsStr = tree.getAllSubItems(0L, "-1");
        String[] globalItems = globalItemsStr.split(",");
        for (String globalItem : globalItems) {
            if (itemName.equals(tree.getItemTextById(0L, globalItem))) {
                itemFound = true;
                tree.selectItem(AbstractSeleniumCore.getTreeIdx(), globalItem);
            }
        }

        String localItemsStr = tree.getAllSubItems(0L, "-2");
        String[] localItems = localItemsStr.split(",");
        for (String localItem : localItems) {
            if (itemName.equals(tree.getItemTextById(0L, localItem))) {
                itemFound = true;
                tree.selectItem(AbstractSeleniumCore.getTreeIdx(), localItem);
            }
        }

        if (!itemFound) {
            throw new SeleniumUnexpectedException("Item [" + itemName + "] not found in organize");
        }
    }

    public void deleteItem(String itemName) {
        selectItem(itemName);
        pageButton.clickDeleteTree(AbstractSeleniumCore.getTreeIdx());
    }

}
package com.onevizion.uitest.api.helper.view;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Listbox;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.helper.page.button.PageButton;
import com.onevizion.uitest.api.helper.tree.Tree;
import com.onevizion.uitest.api.vo.ListboxElement;

@Component
public class View {

    public static final String GROUP_BY_EMPTY_VALUE = "Select column for grouping";
    public static final String GROUP_BY_DROPDOWN_ID = "groupByBox";

    public static final String VIEW_NAME = "TestViewOption";
    public static final String UNSAVED_VIEW_NAME = "Unsaved View";
    public static final String GENERAL_INFO_VIEW_NAME = "G:General Info";

////    private static final String VIEW_MAIN_ELEMENT_ID_BASE = "newDropdownView";

////    private static final String VIEW_SELECT = "ddView";
////    private static final String VIEW_CONTAINER = "ddViewContainer";
    private static final String VIEW_SEARCH = "search_viewSearch";
    private static final String BUTTON_CLEAR_SEARCH = "clear_search_viewSearch";
    private static final String BUTTON_ORGANIZE = "viewOrganizeButton";

////    private static final String BUTTON_OPEN = "btnView";
    private static final String FIELD_VIEW_NAME = "txtViewName";
    private static final String BUTTON_SAVE = "unsavedView";
////    private static final String UNSAVED_VIEW = "unsavedViewId";

    private static final String VIEW_DIALOG_CONTAINER = "dialogViewDialogContainer";
    private static final String VIEW_DIALOG_OK = "viewDialogOk";
    private static final String VIEW_DIALOG_CANCEL = "viewDialogCancel";

    public static final String FOLDER_LOCAL = "Local Views";
    public static final String FOLDER_GLOBAL = "Global Views";

    private static final String SCROLL_CONTAINER = "scrollContainer";
    private static final String EXISTING_VIEWS = "ddExistingViews";
    private static final String SAVE_CONTAINER = "ddViewFormSaveContainer";
    private static final String VIEW_TYPE = "lbViewType";

    private static final String LEFT_COLUMNS_DIV_ID = "leftListBox";
    private static final String RIGHT_COLUMNS_DIV_ID = "rightListBox";
    private static final String ADD_BUTTON_ID = "addItem";
    private static final String REMOVE_BUTTON_ID = "removeItem";





    private static final String ID_MAIN_BUTTON = "viewFilterArrow";
    private static final String ID_MAIN_PANEL = "viewFilterPopup";
    private static final String ID_APPLY_BUTTON = "applyVFTerminal";
    private static final String ID_EDIT_BUTTON = "viewEditButton";
    private static final String ID_CURRENT_NAME = "viewCaption";
    private static final String ID_TREE = "viewTree";
    private static final String CLASS_TREE_ITEM = "item_tree";

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private Tree tree;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private Js js;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private ViewWait viewWait;

    @Autowired
    private Listbox listbox;

    @Autowired
    private Jquery jquery;

    @Autowired
    private PageButton pageButton;

    public void openMainPanel(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(ID_MAIN_BUTTON + gridIdx)).click();
        elementWait.waitElementVisibleById(ID_MAIN_PANEL + gridIdx);
        elementWait.waitElementDisplayById(ID_MAIN_PANEL + gridIdx);
    }

    public void closeMainPanel(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(ID_MAIN_BUTTON + gridIdx)).click();
        elementWait.waitElementNotVisibleById(ID_MAIN_PANEL + gridIdx);
        elementWait.waitElementNotDisplayById(ID_MAIN_PANEL + gridIdx);
    }

    public void checkIsExistViewControl(Long gridIdx, boolean isExist) {
        boolean actualIsExist;

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = seleniumSettings.getWebDriver().findElements(By.id(ID_CURRENT_NAME + gridIdx)).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        if (count > 0) {
            actualIsExist = true;
        } else {
            actualIsExist = false;
        }
        Assert.assertEquals(actualIsExist, isExist);

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        count = seleniumSettings.getWebDriver().findElements(By.id(ID_TREE + gridIdx)).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        if (count > 0) {
            actualIsExist = true;
        } else {
            actualIsExist = false;
        }
        Assert.assertEquals(actualIsExist, isExist);
    }

    public int getViewsCount(Long gridIdx) {
        return getViews(gridIdx).size();
    }

    public List<WebElement> getViews(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id(ID_TREE + gridIdx)).findElements(By.className(CLASS_TREE_ITEM));
    }

    public WebElement getView(String viewName, Long gridIdx) {
        WebElement result = null;

        List<WebElement> views = getViews(gridIdx);
        for (WebElement view : views) {
            if (viewName.equals(view.getAttribute("textContent"))) {
                if (result != null) {
                    throw new SeleniumUnexpectedException("View [" + viewName + "] found many times");
                }
                result = view;
            }
        }

        if (result == null) {
            throw new SeleniumUnexpectedException("View [" + viewName + "] not found");
        }

        return result;
    }

    public String getCurrentViewName(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id(ID_CURRENT_NAME + gridIdx)).getText();
    }

    public void selectViewInOrganize(String viewName) {
        boolean viewFound = false;

        String globalItemsStr = tree.getAllSubItems(0L, "-1");
        String[] globalItems = globalItemsStr.split(",");
        for (String globalItem : globalItems) {
            if (viewName.equals(tree.getItemTextById(0L, globalItem))) {
                viewFound = true;
                tree.selectItem(0L, globalItem);
            }
        }

        String localItemsStr = tree.getAllSubItems(0L, "-2");
        String[] localItems = localItemsStr.split(",");
        for (String localItem : localItems) {
            if (viewName.equals(tree.getItemTextById(0L, localItem))) {
                viewFound = true;
                tree.selectItem(0L, localItem);
            }
        }

        if (!viewFound) {
            throw new SeleniumUnexpectedException("View not found in organize");
        }
    }

    public void selectByVisibleText(Long gridIdx, String entityPrefix) {
        openMainPanel(gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(VIEW_SEARCH + gridIdx)).sendKeys(entityPrefix);

        WebElement view = getView(entityPrefix, gridIdx);
        view.click();

        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_CLEAR_SEARCH + gridIdx)).click();

        seleniumSettings.getWebDriver().findElement(By.id(ID_APPLY_BUTTON + gridIdx)).click();
        grid2.waitLoad(gridIdx);
    }

    private void selectFolderForSaveViewByVisibleText(Long gridIdx, String folderName) {
        String currentFolderName = seleniumSettings.getWebDriver().findElement(By.id(EXISTING_VIEWS + gridIdx)).findElement(By.className("newGenericDropDownLabel")).getText();
        if (folderName.equals(currentFolderName)) {
            return;
        }

        seleniumSettings.getWebDriver().findElement(By.id(EXISTING_VIEWS + gridIdx)).click();

        elementWait.waitElementById(SAVE_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(SAVE_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(SAVE_CONTAINER + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id("ddViewFormSaveSearch" + gridIdx)).sendKeys(folderName);

        WebElement viewElem = (WebElement) js.getNewDropDownElement(SAVE_CONTAINER + gridIdx, SCROLL_CONTAINER, "newGenericDropDownRow", folderName);
        elementWait.waitElementVisible(viewElem);
        viewElem.click();

        seleniumSettings.getWebDriver().findElement(By.id(EXISTING_VIEWS + gridIdx)).click();

        elementWait.waitElementById(SAVE_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(SAVE_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(SAVE_CONTAINER + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id("ddViewFormSaveClearSearch" + gridIdx)).click();
        seleniumSettings.getWebDriver().findElement(By.id(EXISTING_VIEWS + gridIdx)).click();
    }

    private void isExistAndSelectedView(Long gridIdx, String entityPrefix) {
        openMainPanel(gridIdx);

        boolean isSavedView = false;
        for (WebElement view : getViews(gridIdx)) {
            if (entityPrefix.equals(view.getAttribute("textContent"))) {
                isSavedView = true;
            }
        }
        Assert.assertEquals(isSavedView, true, "View " + entityPrefix + " isn't saved");

        closeMainPanel(gridIdx);

        viewWait.waitCurrentViewName(gridIdx, entityPrefix);
        grid2.waitLoad(gridIdx);
    }

    public void openViewForm(Long gridIdx) {
        openMainPanel(gridIdx);

        window.openModal(By.id(ID_EDIT_BUTTON + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        waitLeftListBoxReady();
        waitRightListBoxReady();
    }

    public void closeViewFormOk(Long gridIdx) {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad(gridIdx);

        viewWait.waitCurrentViewName(gridIdx, UNSAVED_VIEW_NAME);
        grid2.waitLoad(gridIdx);
    }

    public void closeViewFormCancel(Long gridIdx) {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));

        closeMainPanel(gridIdx);
    }

    public void openSaveViewForm(Long gridIdx) {
        openMainPanel(gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_SAVE + gridIdx)).click();

        elementWait.waitElementById(VIEW_DIALOG_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(VIEW_DIALOG_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(VIEW_DIALOG_CONTAINER + gridIdx);

        wait.waitWebElement(By.id(VIEW_TYPE + gridIdx));
        wait.waitWebElement(By.id(FIELD_VIEW_NAME + gridIdx));
        wait.waitWebElement(By.id(VIEW_DIALOG_OK + gridIdx));
    }

    public void closeSaveViewFormOk(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(VIEW_DIALOG_OK + gridIdx)).click();
        elementWait.waitElementNotVisibleById(VIEW_DIALOG_CONTAINER + gridIdx);
        elementWait.waitElementNotDisplayById(VIEW_DIALOG_CONTAINER + gridIdx);
        jquery.waitLoad(); //TODO Dev Request 422
    }

    public void closeSaveViewFormCancel(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(VIEW_DIALOG_CANCEL + gridIdx)).click();
        elementWait.waitElementNotVisibleById(VIEW_DIALOG_CONTAINER + gridIdx);
        elementWait.waitElementNotDisplayById(VIEW_DIALOG_CONTAINER + gridIdx);
    }

    public void saveViewAsNewLocal(Long gridIdx, String viewName, String folderName) {
        int beforeSaveSize = getViewsCount(gridIdx);

        openSaveViewForm(gridIdx);
        new Select(seleniumSettings.getWebDriver().findElement(By.id(VIEW_TYPE + gridIdx))).selectByVisibleText("New");
        selectFolderForSaveViewByVisibleText(gridIdx, folderName);
        seleniumSettings.getWebDriver().findElement(By.id(FIELD_VIEW_NAME + gridIdx)).sendKeys(viewName);
        closeSaveViewFormOk(gridIdx);

        wait.waitViewsCount(gridIdx, beforeSaveSize + 1);

        isExistAndSelectedView(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + viewName);
    }

    public void saveViewAsNewGlobal(Long gridIdx, String viewName, String folderName) {
        int beforeSaveSize = getViewsCount(gridIdx);

        openSaveViewForm(gridIdx);
        new Select(seleniumSettings.getWebDriver().findElement(By.id(VIEW_TYPE + gridIdx))).selectByVisibleText("New");
        selectFolderForSaveViewByVisibleText(gridIdx, folderName);
        seleniumSettings.getWebDriver().findElement(By.id(FIELD_VIEW_NAME + gridIdx)).sendKeys(viewName);
        closeSaveViewFormOk(gridIdx);

        wait.waitViewsCount(gridIdx, beforeSaveSize + 1);

        isExistAndSelectedView(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + viewName);
    }

    public void saveViewAsExistingLocal(Long gridIdx, String viewName) {
        int beforeSaveSize = getViewsCount(gridIdx);

        openSaveViewForm(gridIdx);
        new Select(seleniumSettings.getWebDriver().findElement(By.id(VIEW_TYPE + gridIdx))).selectByVisibleText("Existing");
        selectFolderForSaveViewByVisibleText(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + viewName);
        closeSaveViewFormOk(gridIdx);

        wait.waitViewsCount(gridIdx, beforeSaveSize);

        isExistAndSelectedView(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + viewName);
    }

    public void saveViewAsExistingGlobal(Long gridIdx, String viewName) {
        int beforeSaveSize = getViewsCount(gridIdx);

        openSaveViewForm(gridIdx);
        new Select(seleniumSettings.getWebDriver().findElement(By.id(VIEW_TYPE + gridIdx))).selectByVisibleText("Existing");
        selectFolderForSaveViewByVisibleText(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + viewName);
        closeSaveViewFormOk(gridIdx);

        wait.waitViewsCount(gridIdx, beforeSaveSize);

        isExistAndSelectedView(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + viewName);
    }

    public void deleteView(Long gridIdx, String entityPrefix) {
        String currentViewName = getCurrentViewName(gridIdx);

        int beforeDeleteSize = getViewsCount(gridIdx);

        openMainPanel(gridIdx);

        window.openModal(By.id(BUTTON_ORGANIZE + gridIdx));
        tree.waitLoad(0L);
        wait.waitFormLoad();
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));

        selectViewInOrganize(entityPrefix);

        pageButton.clickDeleteTree(AbstractSeleniumCore.getTreeIdx());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        grid2.waitLoad(gridIdx);

        wait.waitViewsCount(gridIdx, beforeDeleteSize - 1);

        boolean isDeletedView = false;
        for (WebElement view : getViews(gridIdx)) {
            if (entityPrefix.equals(view.getAttribute("textContent"))) {
                isDeletedView = true;
            }
        }
        Assert.assertEquals(isDeletedView, false, "View " + entityPrefix + " isn't deleted");

        closeMainPanel(gridIdx);

        if (currentViewName.equals(entityPrefix)) {
            viewWait.waitCurrentViewName(gridIdx, UNSAVED_VIEW_NAME);
            grid2.waitLoad(gridIdx);
        } else {
            viewWait.waitCurrentViewName(gridIdx, currentViewName);
            grid2.waitLoad(gridIdx);
        }
    }

    public void selectLastRightColumn() {
        List<ListboxElement> actualRightColumns = listbox.getElements(RIGHT_COLUMNS_DIV_ID);
        listbox.selectElement(actualRightColumns.get(actualRightColumns.size() - 1));
    }

    public void selectAllColumns(Long gridIdx) {
        openViewForm(gridIdx);

        List<ListboxElement> actualRightColumns = listbox.getElements(RIGHT_COLUMNS_DIV_ID);
        listbox.selectElement(actualRightColumns.get(actualRightColumns.size() - 1));

        List<ListboxElement> actualLeftColumns = listbox.getElements(LEFT_COLUMNS_DIV_ID);
        for (ListboxElement actualLeftColumn : actualLeftColumns) {
            listbox.moveElementByLabel(actualLeftColumns, actualLeftColumn.getLabel(), ADD_BUTTON_ID);
        }

        closeViewFormOk(gridIdx);
    }

    public void removeAllColumns() {
        while (seleniumSettings.getWebDriver().findElement(By.id(REMOVE_BUTTON_ID)).isEnabled()) {
            seleniumSettings.getWebDriver().findElement(By.id(REMOVE_BUTTON_ID)).click();
        }
    }

    public void selectAndCheckColumns(Long gridIdx, int gridColumns, List<String> leftColumns, List<String> rightColumns) {
        selectColumns(gridIdx, rightColumns);
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumns);
        checkColumns(gridIdx, leftColumns, rightColumns);
    }

    public void selectColumns(Long gridIdx, List<String> rightColumns) {
        openViewForm(gridIdx);

        List<ListboxElement> actualRightColumns = listbox.getElements(RIGHT_COLUMNS_DIV_ID);
        for (ListboxElement actualRightColumn : actualRightColumns) {
            listbox.moveElementByLabel(actualRightColumns, actualRightColumn.getLabel(), REMOVE_BUTTON_ID);
        }

        actualRightColumns = listbox.getElements(RIGHT_COLUMNS_DIV_ID);
        List<ListboxElement> actualLeftColumns = listbox.getElements(LEFT_COLUMNS_DIV_ID);
        for (String rightColumn : rightColumns) {
            boolean alreadyInRightList = actualRightColumns.stream().anyMatch(p -> p.getLabel().equals(rightColumn));
            if (!alreadyInRightList) {
                listbox.moveElementByLabel(actualLeftColumns, rightColumn, ADD_BUTTON_ID);
            }
        }

        closeViewFormOk(gridIdx);
    }

    public void checkColumns(Long gridIdx, List<String> leftColumns, List<String> rightColumns) {
        openViewForm(gridIdx);

        List<ListboxElement> actualLeftColumns = listbox.getElements(LEFT_COLUMNS_DIV_ID);
        listbox.checkElementsCount(actualLeftColumns, leftColumns.size());
        for (int i = 0; i < leftColumns.size(); i++) {
            listbox.checkElementByLabel(actualLeftColumns, i + 1, leftColumns.get(i));
        }

        List<ListboxElement> actualRightColumns = listbox.getElements(RIGHT_COLUMNS_DIV_ID);
        listbox.checkElementsCount(actualRightColumns, rightColumns.size());
        for (int i = 0; i < rightColumns.size(); i++) {
            listbox.checkElementByLabel(actualRightColumns, i + 1, rightColumns.get(i));
        }

        closeViewFormCancel(gridIdx);
    }

    public void switchToRootSubgroup() {
        listbox.switchToRootSubgroup("leftListBox");
    }

    public void switchToParentSubgroup() {
        listbox.switchToParentSubgroup("leftListBox");
    }

    public void switchToSubgroupInList(String label) {
        listbox.switchToSubgroupInList("leftListBox", label);
    }

    public void switchToFieldGroup() {
        listbox.switchToFieldGroup("leftListBox");
    }

    public void switchToTaskGroup() {
        listbox.switchToTaskGroup("leftListBox");
    }

    public void switchToDrillDownGroup() {
        listbox.switchToDrillDownGroup("leftListBox");
    }

    public void switchToDatePairGroup() {
        listbox.switchToDatePairGroup("leftListBox");
    }

    public void waitCurrentViewName(Long gridIdx, String viewName) {
        viewWait.waitCurrentViewName(gridIdx, viewName);
    }

    public void waitLeftListBoxReady() {
        listbox.waitIsReadyListbox("leftListBox");
    }

    public void waitRightListBoxReady() {
        listbox.waitIsReadyListbox("rightListBox");
    }

    public void changeFrozenColumns(String newValue) {
        seleniumSettings.getWebDriver().findElement(By.id("frozen")).click();
        WebElement element = (WebElement) js.getNewDropDownElement("frozen", "customscroll", "item_select", newValue);
        Long elementPosition = js.getNewDropDownElementPosition("frozen", "customscroll", "item_select", newValue);
        js.scrollNewDropDownTop("frozen", "customscroll", elementPosition * 28L);
        elementWait.waitElementVisible(element);
        element.click();
    }

    public void checkFrozenColumns(String expectedValue) {
        String actualValue = seleniumSettings.getWebDriver().findElement(By.id("frozen")).findElement(By.className("dl_selected")).findElement(By.tagName("input")).getAttribute("value");
        Assert.assertEquals(actualValue, expectedValue);
    }

    public void checkFrozenColumnsNotExist() {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = seleniumSettings.getWebDriver().findElements(By.id("frozen")).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertEquals(count, 0);
    }

    public void changeHighlightFields(String newValue) {
        seleniumSettings.getWebDriver().findElement(By.id("highlight")).click();
        WebElement element = (WebElement) js.getNewDropDownElement("highlight", "customscroll", "item_select", newValue);
        Long elementPosition = js.getNewDropDownElementPosition("highlight", "customscroll", "item_select", newValue);
        js.scrollNewDropDownTop("highlight", "customscroll", elementPosition * 28L);
        elementWait.waitElementVisible(element);
        element.click();
    }

    public void checkHighlightFields(String expectedValue) {
        String actualValue = seleniumSettings.getWebDriver().findElement(By.id("highlight")).findElement(By.className("dl_selected")).findElement(By.tagName("input")).getAttribute("value");
        Assert.assertEquals(actualValue, expectedValue);
    }

    public void checkHighlightFieldsNotExist() {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = seleniumSettings.getWebDriver().findElements(By.id("highlight")).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertEquals(count, 0);
    }

    public void removeMultiSort(int position) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        boolean isExistMultiSort = seleniumSettings.getWebDriver().findElements(By.id("mult_sort" + position)).size() == 1;
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        if (isExistMultiSort) {
            seleniumSettings.getWebDriver().findElement(By.id("mult_sort" + position)).findElement(By.className("ms_removed")).findElement(By.tagName("input")).click();
        }
    }

    public void changeMultiSortColumn(int position, String newValue) {
        seleniumSettings.getWebDriver().findElement(By.id("fields" + position)).click();
        WebElement element = (WebElement) js.getNewDropDownElement("fields" + position, "customscroll", "item_select", newValue);
        Long elementPosition = js.getNewDropDownElementPosition("fields" + position, "customscroll", "item_select", newValue);
        js.scrollNewDropDownTop("fields" + position, "customscroll", elementPosition * 28L);
        elementWait.waitElementVisible(element);
        element.click();
    }

    public void changeMultiSortColumnTaskDateType(int position, String newValue) {
        seleniumSettings.getWebDriver().findElement(By.id("task" + position)).click();
        WebElement element = (WebElement) js.getNewDropDownElement("task" + position, "customscroll", "item_select", newValue);
        Long elementPosition = js.getNewDropDownElementPosition("task" + position, "customscroll", "item_select", newValue);
        js.scrollNewDropDownTop("task" + position, "customscroll", elementPosition * 28L);
        elementWait.waitElementVisible(element);
        element.click();
    }

    public void changeMultiSortColumnOrder(int position, String newValue) {
        String currentSortOrder1 = seleniumSettings.getWebDriver().findElement(By.id("mult_sort" + position)).findElement(By.className("ms_sort")).findElement(By.tagName("input")).getAttribute("title");
        if (!newValue.equals(currentSortOrder1)) {
            seleniumSettings.getWebDriver().findElement(By.id("mult_sort" + position)).findElement(By.className("ms_sort")).findElement(By.tagName("input")).click();
        }
    }

    public void checkMultiSortColumn(int position, String expectedValue) {
        String actualValue = seleniumSettings.getWebDriver().findElement(By.id("fields" + position)).findElement(By.className("dl_selected")).findElement(By.tagName("input")).getAttribute("value");
        Assert.assertEquals(actualValue, expectedValue);
    }

    public void checkMultiSortColumnTaskDateType(int position, String expectedValue) {
        String actualValue = seleniumSettings.getWebDriver().findElement(By.id("task" + position)).findElement(By.className("dl_selected")).findElement(By.tagName("input")).getAttribute("value");
        Assert.assertEquals(actualValue, expectedValue);
    }

    public void checkMultiSortColumnOrder(int position, String expectedValue) {
        String actualValue = seleniumSettings.getWebDriver().findElement(By.id("mult_sort" + position)).findElement(By.className("ms_sort")).findElement(By.tagName("input")).getAttribute("title");
        Assert.assertEquals(actualValue, expectedValue);
    }

    public void checkMultiSortNotExist(int position) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = seleniumSettings.getWebDriver().findElements(By.id("mult_sort" + position)).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertEquals(count, 0);
    }

}
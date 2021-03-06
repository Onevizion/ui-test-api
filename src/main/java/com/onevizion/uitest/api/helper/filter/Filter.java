package com.onevizion.uitest.api.helper.filter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Selector;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.grid.sort.GridSort;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.helper.organizer.Organizer;
import com.onevizion.uitest.api.vo.FilterFieldType;
import com.onevizion.uitest.api.vo.SortType;

@Component
public class Filter {

    public static final String FILTER_NAME = "TestFilter";
    public static final String UNSAVED_FILTER_NAME = "Unsaved Filter";
    public static final String ALL_FILTER_NAME = "G:All";

////    private static final String FILTER_MAIN_ELEMENT_ID_BASE = "newDropdownFilter";

////    private static final String FILTER_SELECT = "ddFilter";
////    private static final String FILTER_CONTAINER = "ddFilterContainer";
    private static final String FILTER_SEARCH = "search_filterSearch";
    private static final String BUTTON_CLEAR_SEARCH = "clear_search_filterSearch";
    private static final String BUTTON_ORGANIZE = "filterOrganizeButton";

////    private static final String BUTTON_OPEN = "btnFilter";
    private static final String FIELD_FILTER_NAME = "txtFilterName";
    private static final String BUTTON_SAVE = "unsavedFilter";
////    private static final String UNSAVED_FILTER = "unsavedFilterId";

    private static final String FILTER_DIALOG_CONTAINER = "dialogFilterDialogContainer";
    private static final String FILTER_DIALOG_OK = "filterDialogOk";
    private static final String FILTER_DIALOG_CANCEL = "filterDialogCancel";

    public static final String FOLDER_LOCAL = "Local Filters";
    public static final String FOLDER_GLOBAL = "Global Filters";

    private static final String SCROLL_CONTAINER = "scrollContainer";
    private static final String EXISTING_FILTERS = "ddExistingFilters";
    private static final String SAVE_CONTAINER = "ddFilterFormSaveContainer";
    private static final String FILTER_TYPE = "lbFilterType";

    private static final String BUTTON_CLEAR = "btnClear";





    private static final String ID_MAIN_BUTTON = "viewFilterArrow";
    private static final String ID_MAIN_PANEL = "viewFilterPopup";
    private static final String ID_APPLY_BUTTON = "applyVFTerminal";
    private static final String ID_EDIT_BUTTON = "filterEditButton";
    private static final String ID_CURRENT_NAME = "filterCaption";
    private static final String ID_TREE = "filterTree";
    private static final String CLASS_TREE_ITEM = "item_tree";

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Js js;

    @Autowired
    private Wait wait;

    @Autowired
    private Window window;

    @Autowired
    private Selector selector;

    @Autowired
    private Checkbox checkbox;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private FilterWait filterWait;

    @Autowired
    private GridSort gridSort;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private Jquery jquery;

    @Autowired
    private Organizer organizer;

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

    public void checkIsExistFilterControl(Long gridIdx, boolean isExist) {
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

    public int getFiltersCount(Long gridIdx) {
        return getFilters(gridIdx).size();
    }

    public List<WebElement> getFilters(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id(ID_TREE + gridIdx)).findElements(By.className(CLASS_TREE_ITEM));
    }

    public WebElement getFilter(String filterName, Long gridIdx) {
        WebElement result = null;

        List<WebElement> filters = getFilters(gridIdx);
        for (WebElement filter : filters) {
            if (filterName.equals(filter.getAttribute("textContent"))) {
                if (result != null) {
                    throw new SeleniumUnexpectedException("Filter [" + filterName + "] found many times");
                }
                result = filter;
            }
        }

        if (result == null) {
            throw new SeleniumUnexpectedException("Filter [" + filterName + "] not found");
        }

        return result;
    }

    public String getCurrentFilterName(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id(ID_CURRENT_NAME + gridIdx)).getText();
    }

    public void selectByVisibleText(String entityPrefix, Long gridIdx) {
        openMainPanel(gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(FILTER_SEARCH + gridIdx)).sendKeys(entityPrefix);

        WebElement filter = getFilter(entityPrefix, gridIdx);
        filter.click();

        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_CLEAR_SEARCH + gridIdx)).click();

        seleniumSettings.getWebDriver().findElement(By.id(ID_APPLY_BUTTON + gridIdx)).click();
        grid2.waitLoad(gridIdx);
    }

    public void saveFilterField(String fieldName, FilterFieldType filterFieldType, String cellValue, Long gridIdx) {
        saveFilterField(fieldName, filterFieldType, Arrays.asList(cellValue), gridIdx);
    }

    public void saveFilterField(String fieldName, FilterFieldType filterFieldType, List<String> cellsValues, Long gridIdx) {
        openFilterForm(gridIdx);
        if (filterFieldType.equals(FilterFieldType.TEXT)) {
            seleniumSettings.getWebDriver().findElement(By.name(fieldName)).sendKeys(cellsValues.get(0));
        } else if (filterFieldType.equals(FilterFieldType.SELECT)) {
            new Select(seleniumSettings.getWebDriver().findElement(By.name(fieldName))).selectByVisibleText(cellsValues.get(0));
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX)) {
            checkbox.clickByName(fieldName);
        } else if (filterFieldType.equals(FilterFieldType.RADIO_PS_SELECTOR)) {
            selector.selectRadio(By.name("btn" + fieldName), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1, cellsValues.get(0), 1L);
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX_PS_SELECTOR)) {
            selector.selectCheckbox(By.name("btn" + fieldName), 1, cellsValues, 1L);
        }
        closeFilterFormOk(gridIdx);
    }

    private void selectFolderForSaveFilterByVisibleText(Long gridIdx, String folderName) {
        String currentFolderName = seleniumSettings.getWebDriver().findElement(By.id(EXISTING_FILTERS + gridIdx)).findElement(By.className("newGenericDropDownLabel")).getText();
        if (folderName.equals(currentFolderName)) {
            return;
        }

        seleniumSettings.getWebDriver().findElement(By.id(EXISTING_FILTERS + gridIdx)).click();

        elementWait.waitElementById(SAVE_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(SAVE_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(SAVE_CONTAINER + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id("ddFilterFormSaveSearch" + gridIdx)).sendKeys(folderName);

        WebElement viewElem = (WebElement) js.getNewDropDownElement(SAVE_CONTAINER + gridIdx, SCROLL_CONTAINER, "newGenericDropDownRow", folderName);
        elementWait.waitElementVisible(viewElem);
        viewElem.click();

        seleniumSettings.getWebDriver().findElement(By.id(EXISTING_FILTERS + gridIdx)).click();

        elementWait.waitElementById(SAVE_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(SAVE_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(SAVE_CONTAINER + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id("ddFilterFormSaveClearSearch" + gridIdx)).click();
        seleniumSettings.getWebDriver().findElement(By.id(EXISTING_FILTERS + gridIdx)).click();
    }

    private void isExistAndSelectedFilter(Long gridIdx, String entityPrefix) {
        openMainPanel(gridIdx);

        boolean isSavedFilter = false;
        for (WebElement filter : getFilters(gridIdx)) {
            if (entityPrefix.equals(filter.getAttribute("textContent"))) {
                isSavedFilter = true;
            }
        }
        Assert.assertEquals(isSavedFilter, true, "Filter " + entityPrefix + " isn't saved");

        closeMainPanel(gridIdx);

        filterWait.waitCurrentFilterName(gridIdx, entityPrefix);
        grid2.waitLoad(gridIdx);
    }

    public void openFilterForm(Long gridIdx) {
        openMainPanel(gridIdx);

        window.openModal(By.id(ID_EDIT_BUTTON + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void closeFilterFormOk(Long gridIdx) {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad(gridIdx);

        filterWait.waitCurrentFilterName(gridIdx, UNSAVED_FILTER_NAME);
        grid2.waitLoad(gridIdx);
    }

    public void closeFilterFormCancel(Long gridIdx) {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));

        closeMainPanel(gridIdx);
    }

    public void openSaveFilterForm(Long gridIdx) {
        openMainPanel(gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_SAVE + gridIdx)).click();

        elementWait.waitElementById(FILTER_DIALOG_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(FILTER_DIALOG_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(FILTER_DIALOG_CONTAINER + gridIdx);

        wait.waitWebElement(By.id(FILTER_TYPE + gridIdx));
        wait.waitWebElement(By.id(FIELD_FILTER_NAME + gridIdx));
        wait.waitWebElement(By.id(FILTER_DIALOG_OK + gridIdx));
    }

    public void closeSaveFilterFormOk(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(FILTER_DIALOG_OK + gridIdx)).click();
        elementWait.waitElementNotVisibleById(FILTER_DIALOG_CONTAINER + gridIdx);
        elementWait.waitElementNotDisplayById(FILTER_DIALOG_CONTAINER + gridIdx);
        jquery.waitLoad(); //TODO Dev Request 422
    }

    public void closeSaveFilterFormCancel(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(FILTER_DIALOG_CANCEL + gridIdx)).click();
        elementWait.waitElementNotVisibleById(FILTER_DIALOG_CONTAINER + gridIdx);
        elementWait.waitElementNotDisplayById(FILTER_DIALOG_CONTAINER + gridIdx);
    }

    public void saveFilterAsNewLocal(Long gridIdx, String filterName, String folderName) {
        int beforeSaveSize = getFiltersCount(gridIdx);

        openSaveFilterForm(gridIdx);
        new Select(seleniumSettings.getWebDriver().findElement(By.id(FILTER_TYPE + gridIdx))).selectByVisibleText("New");
        selectFolderForSaveFilterByVisibleText(gridIdx, folderName);
        seleniumSettings.getWebDriver().findElement(By.id(FIELD_FILTER_NAME + gridIdx)).sendKeys(filterName);
        closeSaveFilterFormOk(gridIdx);

        wait.waitFiltersCount(gridIdx, beforeSaveSize + 1);

        isExistAndSelectedFilter(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + filterName);
    }

    public void saveFilterAsNewGlobal(Long gridIdx, String filterName, String folderName) {
        int beforeSaveSize = getFiltersCount(gridIdx);

        openSaveFilterForm(gridIdx);
        new Select(seleniumSettings.getWebDriver().findElement(By.id(FILTER_TYPE + gridIdx))).selectByVisibleText("New");
        selectFolderForSaveFilterByVisibleText(gridIdx, folderName);
        seleniumSettings.getWebDriver().findElement(By.id(FIELD_FILTER_NAME + gridIdx)).sendKeys(filterName);
        closeSaveFilterFormOk(gridIdx);

        wait.waitFiltersCount(gridIdx, beforeSaveSize + 1);

        isExistAndSelectedFilter(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + filterName);
    }

    public void saveFilterAsExistingLocal(Long gridIdx, String filterName) {
        int beforeSaveSize = getFiltersCount(gridIdx);

        openSaveFilterForm(gridIdx);
        new Select(seleniumSettings.getWebDriver().findElement(By.id(FILTER_TYPE + gridIdx))).selectByVisibleText("Existing");
        selectFolderForSaveFilterByVisibleText(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + filterName);
        closeSaveFilterFormOk(gridIdx);

        wait.waitFiltersCount(gridIdx, beforeSaveSize);

        isExistAndSelectedFilter(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + filterName);
    }

    public void saveFilterAsExistingGlobal(Long gridIdx, String filterName) {
        int beforeSaveSize = getFiltersCount(gridIdx);

        openSaveFilterForm(gridIdx);
        new Select(seleniumSettings.getWebDriver().findElement(By.id(FILTER_TYPE + gridIdx))).selectByVisibleText("Existing");
        selectFolderForSaveFilterByVisibleText(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + filterName);
        closeSaveFilterFormOk(gridIdx);

        wait.waitFiltersCount(gridIdx, beforeSaveSize);

        isExistAndSelectedFilter(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + filterName);
    }

    public void addFolder(Long gridIdx, String folderName, String folderParentName) {
        openMainPanel(gridIdx);
        organizer.openOrganizer(BUTTON_ORGANIZE + gridIdx);
        organizer.add(folderName, folderParentName);
        organizer.closeOrganizer();
        jquery.waitLoad();
        closeMainPanel(gridIdx);
    }

    public void editFolder(Long gridIdx, String oldfolderName, String newfolderName, String newfolderParentName) {
        openMainPanel(gridIdx);
        organizer.openOrganizer(BUTTON_ORGANIZE + gridIdx);
        organizer.edit(oldfolderName, newfolderName, newfolderParentName);
        organizer.closeOrganizer();
        jquery.waitLoad();
        closeMainPanel(gridIdx);
    }

    public void deleteFolder(Long gridIdx, String folderName) {
        openMainPanel(gridIdx);
        organizer.openOrganizer(BUTTON_ORGANIZE + gridIdx);
        organizer.delete(folderName);
        organizer.closeOrganizer();
        jquery.waitLoad();
        closeMainPanel(gridIdx);
    }

    public void moveUpFolder(Long gridIdx, String folderName) {
        openMainPanel(gridIdx);
        organizer.openOrganizer(BUTTON_ORGANIZE + gridIdx);
        organizer.moveUp(folderName);
        organizer.closeOrganizer();
        jquery.waitLoad();
        closeMainPanel(gridIdx);
    }

    public void moveDownFolder(Long gridIdx, String folderName) {
        openMainPanel(gridIdx);
        organizer.openOrganizer(BUTTON_ORGANIZE + gridIdx);
        organizer.moveDown(folderName);
        organizer.closeOrganizer();
        jquery.waitLoad();
        closeMainPanel(gridIdx);
    }

    public void checkSubItemsInFolder(Long gridIdx, String folderName, List<String> subItems) {
        openMainPanel(gridIdx);
        organizer.openOrganizer(BUTTON_ORGANIZE + gridIdx);
        organizer.checkSubItems(folderName, subItems);
        organizer.closeOrganizer();
        closeMainPanel(gridIdx);
    }

    public void promoteToGlobal(Long gridIdx, String oldFilterName, String newFilterName, String folderParentName) {
        openMainPanel(gridIdx);
        organizer.openOrganizer(BUTTON_ORGANIZE + gridIdx);
        organizer.promoteToGlobal(oldFilterName, newFilterName, folderParentName);
        organizer.closeOrganizer();
        jquery.waitLoad();
        closeMainPanel(gridIdx);
    }

    public void deleteFilter(Long gridIdx, String entityPrefix) {
        String currentFilterName = getCurrentFilterName(gridIdx);

        int beforeDeleteSize = getFiltersCount(gridIdx);

        openMainPanel(gridIdx);

        organizer.openOrganizer(BUTTON_ORGANIZE + gridIdx);
        organizer.delete(entityPrefix);
        organizer.closeOrganizer();

        grid2.waitLoad(gridIdx);

        wait.waitFiltersCount(gridIdx, beforeDeleteSize - 1);

        boolean isDeletedFilter = false;
        for (WebElement filter : getFilters(gridIdx)) {
            if (entityPrefix.equals(filter.getAttribute("textContent"))) {
                isDeletedFilter = true;
            }
        }
        Assert.assertEquals(isDeletedFilter, false, "Filter " + entityPrefix + " isn't deleted");

        closeMainPanel(gridIdx);

        if (currentFilterName.equals(entityPrefix)) {
            filterWait.waitCurrentFilterName(gridIdx, UNSAVED_FILTER_NAME);
            grid2.waitLoad(gridIdx);
        } else {
            filterWait.waitCurrentFilterName(gridIdx, currentFilterName);
            grid2.waitLoad(gridIdx);
        }
    }

    public void clearFilter(Long gridIdx) {
        openFilterForm(gridIdx);
        seleniumSettings.getWebDriver().findElement(By.name(BUTTON_CLEAR)).click();
        closeFilterFormOk(gridIdx);
    }

    public String getGridCellValueForFilterTest(Long gridId, String columnId, FilterFieldType filterFieldType) {
        int columnIndex = js.getColumnIndexById(gridId, columnId);
        String columnLabel = js.getGridColumnLabelByColIndex(gridId, columnIndex, 0);

        if (js.getGridIsSupportSortByGridId(gridId)) {
            gridSort.sortColumn(gridId, SortType.ASC, columnLabel);
        }

        if (filterFieldType.equals(FilterFieldType.CHECKBOX)) {
            return "YES";
        } else {
            for (int i = 0; i < js.getGridRowsCount(gridId); i = i + 1) {
                String value = js.getGridCellValueByRowIndexAndColIndex(gridId, i, columnIndex);
                if (StringUtils.isNotBlank(value)) {
                    value = value.replaceAll("^<[aA].*?>", "").replaceAll("</[aA]>$", ""); /*Example: condition for link*/
                    value = StringUtils.substringBefore(value, "\n"); /*Example: condition for pl/sql block where value may have character of new line*/
                    if (value.startsWith("<p>") && value.endsWith("</p>")) {
                        value = value.substring(3, value.length() - 4);
                    }
                    value = value.trim();
                    return value;
                }
            }
        }
        throw new SeleniumUnexpectedException("Column with index[" + columnIndex + "] no have data.");
    }

    public void checkGridColumnByFilterValue(Long gridId, String columnId, String value) {
        int columnIndex = js.getColumnIndexById(gridId, columnId);
        int rowsCnt = js.getGridRowsCount(gridId);

        @SuppressWarnings("unchecked")
        List<String> vals =  (List<String>) js.getGridCellsValuesForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        for (int i = 0; i < rowsCnt; i++) {
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [%s]", columnIndex, i, vals.get(i), value);
            Assert.assertEquals(vals.get(i).toLowerCase().contains(value.toLowerCase()), true, failMessage);
        }
    }

    public void assertEmptyFilterField(String fieldName, FilterFieldType filterFieldType, Long gridIdx) {
        openFilterForm(gridIdx);
        if (filterFieldType.equals(FilterFieldType.TEXT)) {
            assertElement.assertText(fieldName, "");
        } else if (filterFieldType.equals(FilterFieldType.SELECT)) {
            assertElement.assertSelect(fieldName, "");
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX)) {
            assertElement.assertCheckbox(fieldName, "NO");
        } else if (filterFieldType.equals(FilterFieldType.RADIO_PS_SELECTOR)) {
            assertElement.assertRadioPsSelector(fieldName, "btn" + fieldName, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, "", 1L, true);
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX_PS_SELECTOR)) {
            assertElement.assertCheckboxPsSelector(fieldName, "btn" + fieldName, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(""), 1L, true);
        }
        closeFilterFormCancel(gridIdx);
    }

    public void assertFilterField(String fieldName, FilterFieldType filterFieldType, String cellValue, Long gridIdx) {
        openFilterForm(gridIdx);
        if (filterFieldType.equals(FilterFieldType.TEXT)) {
            assertElement.assertText(fieldName, cellValue);
        } else if (filterFieldType.equals(FilterFieldType.SELECT)) {
            assertElement.assertSelect(fieldName, cellValue);
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX)) {
            assertElement.assertCheckbox(fieldName, cellValue);
        } else if (filterFieldType.equals(FilterFieldType.RADIO_PS_SELECTOR)) {
            assertElement.assertRadioPsSelector(fieldName, "btn" + fieldName, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, cellValue, 1L, true);
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX_PS_SELECTOR)) {
            assertElement.assertCheckboxPsSelector(fieldName, "btn" + fieldName, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(cellValue), 1L, true);
        }
        closeFilterFormCancel(gridIdx);
    }

    public void checkRowsInGrid(String rowIds, List<String> columnNames) {
        Assert.assertEquals(rowIds.contains(columnNames.get(0)), true, "Not found in grid"); //CHECKBOX
        Assert.assertEquals(rowIds.contains(columnNames.get(1)), true, "Not found in grid"); //DATE
        Assert.assertEquals(rowIds.contains(columnNames.get(2)), true, "Not found in grid"); //DB_DROP_DOWN
        Assert.assertEquals(rowIds.contains(columnNames.get(3)), true, "Not found in grid"); //DB_SELECTOR
        Assert.assertEquals(rowIds.contains(columnNames.get(4)), true, "Not found in grid"); //DROP_DOWN
        Assert.assertEquals(rowIds.contains(columnNames.get(5)), true, "Not found in grid"); //ELECTRONIC_FILE
        Assert.assertEquals(rowIds.contains(columnNames.get(6)), true, "Not found in grid"); //HYPERLINK
        Assert.assertEquals(rowIds.contains(columnNames.get(7)), true, "Not found in grid"); //LATITUDE
        Assert.assertEquals(rowIds.contains(columnNames.get(8)), true, "Not found in grid"); //LONGITUDE
        Assert.assertEquals(rowIds.contains(columnNames.get(9)), true, "Not found in grid"); //MEMO
        Assert.assertEquals(rowIds.contains(columnNames.get(10)), true, "Not found in grid"); //NUMBER
        Assert.assertEquals(rowIds.contains(columnNames.get(11)), true, "Not found in grid"); //SELECTOR
        Assert.assertEquals(rowIds.contains(columnNames.get(12)), true, "Not found in grid"); //TEXT
        Assert.assertEquals(rowIds.contains(columnNames.get(13)), true, "Not found in grid"); //TRACKOR_SELECTOR
        Assert.assertEquals(rowIds.contains(columnNames.get(14)), true, "Not found in grid"); //WIKI
        Assert.assertEquals(rowIds.contains(columnNames.get(15)), true, "Not found in grid"); //MULTI_SELECTOR
        Assert.assertEquals(rowIds.contains(columnNames.get(16)), true, "Not found in grid"); //DATE_TIME
        Assert.assertEquals(rowIds.contains(columnNames.get(17)), true, "Not found in grid"); //TIME
        Assert.assertEquals(rowIds.contains(columnNames.get(18)), true, "Not found in grid"); //TRACKOR_DROPDOWN
        Assert.assertEquals(rowIds.contains(columnNames.get(19)), true, "Not found in grid"); //CALCULATED
        if (columnNames.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            Assert.assertEquals(rowIds.contains(columnNames.get(20)), true, "Not found in grid"); //ROLLUP
        }
        Assert.assertEquals(rowIds.contains(columnNames.get(21)), true, "Not found in grid"); //MULTI_TRACKOR_SELECTOR
    }

    public void waitCurrentFilterName(Long gridIdx, String filterName) {
        filterWait.waitCurrentFilterName(gridIdx, filterName);
    }

}
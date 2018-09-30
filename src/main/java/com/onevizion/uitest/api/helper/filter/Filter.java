package com.onevizion.uitest.api.helper.filter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.PsSelector;
import com.onevizion.uitest.api.helper.Sort;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.jquery.JqueryWait;
import com.onevizion.uitest.api.helper.tree.Tree;
import com.onevizion.uitest.api.vo.FilterFieldType;
import com.onevizion.uitest.api.vo.SortType;

@Component
public class Filter {

    public static final String UNSAVED_FILTER_NAME = "Unsaved Filter";
    public static final String ALL_FILTER_NAME = "G:All";

    private static final String FILTER_MAIN_ELEMENT_ID_BASE = "newDropdownFilter";

    public static final String SELECT_FILTER = "ddFilter"; //TODO change from SELECT_FILTER to FILTER
    public static final String FILTER_CONTAINER = "ddFilterContainer";
    public static final String FILTER_SEARCH = "ddFilterSearch";
    public static final String BUTTON_CLEAR_SEARCH = "ddFilterClearSearch";
    public static final String BUTTON_ORGANIZE = "ddFilterBtnOrganize";

    public static final String FILTER_NAME = "TestFilter";
    public static final String BUTTON_SAVE = "btnSaveFilter";
    public static final String BUTTON_SAVE_NEW = "unsavedFilterIcon";
    public static final String BUTTON_DELETE = "btnDeleteFilter";
    public static final String FIELD_FILTER_NAME = "txtFilterName";
    public static final String BUTTON_OPEN = "btnFilter";
    public static final String BUTTON_CLEAR = "btnClear";
    public static final String BUTTON_CB_TEMPLATE = "cbShowTemplates";
    private static final String UNSAVED_FILTER = "unsavedFilterId";

    public static final String FILTER_DIALOG_CONTAINER = "dialogFilterDialogContainer";
    public static final String FILTER_DIALOG_OK = "filterDialogOk";
    public static final String FILTER_DIALOG_CANCEL = "filterDialogCancel";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Js js;

    @Resource
    private Tree tree;

    @Resource
    private Wait wait;

    @Resource
    private Window window;

    @Resource
    private Sort sort;

    @Resource
    private PsSelector psSelector;

    @Resource
    private Checkbox checkbox;

    @Resource
    private ElementWait elementWait;

    @Resource
    private FilterWait filterWait;

    @Resource
    private JqueryWait jqueryWait;

    public void checkIsExistFilterControl(Long gridIdx, boolean isExist) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = seleniumSettings.getWebDriver().findElements(By.id(FILTER_MAIN_ELEMENT_ID_BASE + gridIdx)).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        boolean actualIsExist;
        if (count > 0) {
            actualIsExist = true;
        } else {
            actualIsExist = false;
        }

        Assert.assertEquals(actualIsExist, isExist);
    }

    public int getFiltersCount(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).findElements(By.className("leaf")).size();
    }

    public List<WebElement> getFilters(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).findElements(By.className("leaf"));
    }

    public String getCurrentFilterName(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).findElement(By.className("newGenericDropDownLabel")).getText();
    }

    public void selectFilterInOrganize(String filterName) {
        boolean filterFound = false;

        String globalItemsStr = tree.getAllSubItems(0L, "-1");
        String[] globalItems = globalItemsStr.split(",");
        for (String globalItem : globalItems) {
            if (filterName.equals(tree.getItemTextById(0L, globalItem))) {
                filterFound = true;
                tree.selectItem(0L, globalItem);
            }
        }

        String localItemsStr = tree.getAllSubItems(0L, "-2");
        String[] localItems = localItemsStr.split(",");
        for (String localItem : localItems) {
            if (filterName.equals(tree.getItemTextById(0L, localItem))) {
                filterFound = true;
                tree.selectItem(0L, localItem);
            }
        }

        if (!filterFound) {
            throw new SeleniumUnexpectedException("Filter not found in organize");
        }
    }

    public void selectByVisibleText(String entityPrefix, Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();

        elementWait.waitElementById(FILTER_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(FILTER_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(FILTER_CONTAINER + gridIdx);

        if (entityPrefix.equals(UNSAVED_FILTER_NAME)) {
            seleniumSettings.getWebDriver().findElement(By.id(UNSAVED_FILTER + gridIdx)).click();
            wait.waitGridLoad(gridIdx, gridIdx);
        } else {
            seleniumSettings.getWebDriver().findElement(By.id(FILTER_SEARCH + gridIdx)).sendKeys(entityPrefix);

            WebElement filterElem = (WebElement) js.getNewDropDownElement(FILTER_CONTAINER + gridIdx, "scrollContainer", "newGenericDropDownRow", entityPrefix);
            elementWait.waitElementVisible(filterElem);
            filterElem.click();

            wait.waitGridLoad(gridIdx, gridIdx);

            seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();

            elementWait.waitElementById(FILTER_CONTAINER + gridIdx);
            elementWait.waitElementVisibleById(FILTER_CONTAINER + gridIdx);
            elementWait.waitElementDisplayById(FILTER_CONTAINER + gridIdx);

            seleniumSettings.getWebDriver().findElement(By.id(BUTTON_CLEAR_SEARCH + gridIdx)).click();
            seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();
        }
    }

    public void saveFilterField(String fieldName, FilterFieldType filterFieldType, String cellValue, Long gridIdx) {
        saveFilterField(fieldName, filterFieldType, Arrays.asList(cellValue), gridIdx);
    }

    public void saveFilterField(String fieldName, FilterFieldType filterFieldType, List<String> cellsValues, Long gridIdx) {
        window.openModal(By.id(BUTTON_OPEN + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        if (filterFieldType.equals(FilterFieldType.TEXT)) {
            seleniumSettings.getWebDriver().findElement(By.name(fieldName)).sendKeys(cellsValues.get(0));
        } else if (filterFieldType.equals(FilterFieldType.SELECT)) {
            new Select(seleniumSettings.getWebDriver().findElement(By.name(fieldName))).selectByVisibleText(cellsValues.get(0));
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX)) {
            checkbox.clickByName(fieldName);
        } else if (filterFieldType.equals(FilterFieldType.RADIO_PS_SELECTOR)) {
            psSelector.selectSpecificValue(By.name("btn" + fieldName), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, cellsValues.get(0), 1L);
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX_PS_SELECTOR)) {
            psSelector.selectMultipleSpecificValues(By.name("btn" + fieldName), 1L, cellsValues, 1L);
        }
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        jqueryWait.waitJQueryLoad(); //wait reload filters and grid
        wait.waitGridLoad(gridIdx, gridIdx);
        jqueryWait.waitJQueryLoad(); //wait reload filters and grid
    }

    public void openSaveFilterForm(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();

        elementWait.waitElementById(FILTER_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(FILTER_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(FILTER_CONTAINER + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_SAVE_NEW + gridIdx)).click();

        elementWait.waitElementById(FILTER_DIALOG_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(FILTER_DIALOG_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(FILTER_DIALOG_CONTAINER + gridIdx);

        wait.waitWebElement(By.id(FIELD_FILTER_NAME + gridIdx));
        wait.waitWebElement(By.id(FILTER_DIALOG_OK + gridIdx));
    }

    public void closeSaveFilterFormOk(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(FILTER_DIALOG_OK + gridIdx)).click();
    }

    public void closeSaveFilterFormCancel(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(FILTER_DIALOG_CANCEL + gridIdx)).click();
    }

    public void saveFilter(Long gridIdx, String entityPrefix) {
        int beforeSaveSize = getFiltersCount(gridIdx);

        openSaveFilterForm(gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(FIELD_FILTER_NAME + gridIdx)).sendKeys(entityPrefix + FILTER_NAME);

        closeSaveFilterFormOk(gridIdx);

        wait.waitFiltersCount(gridIdx, beforeSaveSize + 1);

        boolean isSavedFilter = false;
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();
        for (WebElement filter : getFilters(gridIdx)) {
            if (filter.getText().equals(AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + FILTER_NAME)) {
                isSavedFilter = true;
            }
        }
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();
        Assert.assertEquals(isSavedFilter, true, "Filter " + AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + FILTER_NAME + " isn't saved");

        filterWait.waitCurrentFilterName(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + FILTER_NAME);
        wait.waitGridLoad(gridIdx, gridIdx);
    }

    public void deleteFilter(Long gridIdx, String entityPrefix) {
        int beforeDeleteSize = getFiltersCount(gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();

        elementWait.waitElementById(FILTER_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(FILTER_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(FILTER_CONTAINER + gridIdx);

        window.openModal(By.id(BUTTON_ORGANIZE + gridIdx));
        tree.waitLoad(0L);
        wait.waitFormLoad();
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));

        selectFilterInOrganize(AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + FILTER_NAME);

        seleniumSettings.getWebDriver().findElement(By.name(AbstractSeleniumCore.BUTTON_DELETE_TREE_ID_BASE + 0L)).click();
        wait.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();
        tree.waitLoad(0L);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        wait.waitGridLoad(gridIdx, gridIdx);

        wait.waitFiltersCount(gridIdx, beforeDeleteSize - 1);

        boolean isDeletedFilter = false;
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();
        for (WebElement filter : getFilters(gridIdx)) {
            if (filter.getText().equals(AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + FILTER_NAME)) {
                isDeletedFilter = true;
            }
        }
        Assert.assertEquals(isDeletedFilter, false, "Filter " + AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + FILTER_NAME + " isn't deleted");

        filterWait.waitCurrentFilterName(gridIdx, UNSAVED_FILTER_NAME);
        wait.waitGridLoad(gridIdx, gridIdx);
    }

    public void clearFilter(Long gridIdx) {
        window.openModal(By.id(BUTTON_OPEN + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        seleniumSettings.getWebDriver().findElement(By.name(BUTTON_CLEAR)).click();
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        jqueryWait.waitJQueryLoad(); //wait reload filters and grid
        wait.waitGridLoad(gridIdx, gridIdx);
        jqueryWait.waitJQueryLoad(); //wait reload filters and grid

        filterWait.waitCurrentFilterName(gridIdx, UNSAVED_FILTER_NAME);
        wait.waitGridLoad(gridIdx, gridIdx);
    }

    public String getGridCellValueForFilterTest(Long gridId, String columnId, FilterFieldType filterFieldType) {
        if (js.getGridIsSupportSortByGridId(gridId)) {
            sort.sortColumn(gridId, columnId, SortType.ASC);
        }
        Long columnIndex = js.getGridColIndexById(gridId, columnId);
        if (filterFieldType.equals(FilterFieldType.CHECKBOX)) {
            return "YES";
        } else {
            for (Long i = 0L; i < js.getGridRowsCount(gridId); i = i + 1L) {
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
        Long columnIndex = js.getGridColIndexById(gridId, columnId);
        Long rowsCnt = js.getGridRowsCount(gridId);

        @SuppressWarnings("unchecked")
        List<String> vals =  (List<String>) js.getGridCellsValuesForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        for (int i = 0; i < rowsCnt.intValue(); i++) {
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [%s]", columnIndex, i, vals.get(i), value);
            Assert.assertEquals(vals.get(i).toLowerCase().contains(value.toLowerCase()), true, failMessage);
        }
    }

    public void assertEmptyFilterField(String fieldName, FilterFieldType filterFieldType, Long gridIdx) {
        window.openModal(By.id(BUTTON_OPEN + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        if (filterFieldType.equals(FilterFieldType.TEXT)) {
            assertElement.assertText(fieldName, "");
        } else if (filterFieldType.equals(FilterFieldType.SELECT)) {
            assertElement.assertSelect(fieldName, "");
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX)) {
            assertElement.assertCheckBoxNew(fieldName, "NO");
        } else if (filterFieldType.equals(FilterFieldType.RADIO_PS_SELECTOR)) {
            assertElement.assertRadioPsSelector(fieldName, "btn" + fieldName, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, "", 1L, true);
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX_PS_SELECTOR)) {
            assertElement.assertCheckboxPsSelector(fieldName, "btn" + fieldName, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(""), 1L, true);
        }
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void assertFilterField(String fieldName, FilterFieldType filterFieldType, String cellValue, Long gridIdx) {
        window.openModal(By.id(BUTTON_OPEN + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        if (filterFieldType.equals(FilterFieldType.TEXT)) {
            assertElement.assertText(fieldName, cellValue);
        } else if (filterFieldType.equals(FilterFieldType.SELECT)) {
            assertElement.assertSelect(fieldName, cellValue);
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX)) {
            assertElement.assertCheckBoxNew(fieldName, cellValue);
        } else if (filterFieldType.equals(FilterFieldType.RADIO_PS_SELECTOR)) {
            assertElement.assertRadioPsSelector(fieldName, "btn" + fieldName, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, cellValue, 1L, true);
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX_PS_SELECTOR)) {
            assertElement.assertCheckboxPsSelector(fieldName, "btn" + fieldName, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(cellValue), 1L, true);
        }
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
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
        if (columnNames.get(20) != null) { //Workplan and Tasks trackor types not support
            Assert.assertEquals(rowIds.contains(columnNames.get(20)), true, "Not found in grid"); //ROLLUP
        }
    }

    public void waitCurrentFilterName(Long gridIdx, String filterName) {
        filterWait.waitCurrentFilterName(gridIdx, filterName);
    }

}
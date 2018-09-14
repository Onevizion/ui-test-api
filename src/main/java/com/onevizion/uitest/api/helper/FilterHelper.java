package com.onevizion.uitest.api.helper;

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
import com.onevizion.uitest.api.helper.jquery.JqueryWaitHelper;
import com.onevizion.uitest.api.helper.tree.TreeJs;
import com.onevizion.uitest.api.helper.tree.TreeWait;
import com.onevizion.uitest.api.vo.FilterFieldType;
import com.onevizion.uitest.api.vo.SortType;

@Component
public class FilterHelper {

    public final static String UNSAVED_FILTER_NAME = "Unsaved Filter";
    public final static String ALL_FILTER_NAME = "G:All";

    private final static String FILTER = "newDropdownFilter";

    public final static String SELECT_FILTER = "ddFilter"; //TODO change from SELECT_FILTER to FILTER
    public final static String FILTER_CONTAINER = "ddFilterContainer";
    public final static String FILTER_SEARCH = "ddFilterSearch";
    public final static String BUTTON_CLEAR_SEARCH = "ddFilterClearSearch";
    public final static String BUTTON_ORGANIZE = "ddFilterBtnOrganize";

    public final static String FILTER_NAME = "TestFilter";
    public final static String BUTTON_SAVE = "btnSaveFilter";
    public final static String BUTTON_SAVE_NEW = "unsavedFilterIcon";
    public final static String BUTTON_DELETE = "btnDeleteFilter";
    public final static String FIELD_FILTER_NAME = "txtFilterName";
    public final static String BUTTON_OPEN = "btnFilter";
    public final static String BUTTON_CLEAR = "btnClear";
    public final static String BUTTON_CB_TEMPLATE = "cbShowTemplates";

    public final static String FILTER_DIALOG_CONTAINER = "dialogFilterDialogContainer";
    public final static String FILTER_DIALOG_OK = "filterDialogOk";
    public final static String FILTER_DIALOG_CANCEL = "filterDialogCancel";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private TreeJs treeJs;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private TreeWait treeWait;

    @Resource
    private WindowHelper windowHelper;

    @Resource
    private SortHelper sortHelper;

    @Resource
    private PsSelectorHelper psSelectorHelper;

    @Resource
    private CheckboxHelper checkboxHelper;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    @Resource
    private FilterWaitHelper filterWaitHelper;

    @Resource
    private JqueryWaitHelper jqueryWaitHelper;

    public void checkIsExistFilterControl(Long gridIdx, boolean isExist) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = seleniumSettings.getWebDriver().findElements(By.id(FILTER + gridIdx)).size();
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

        String globalItemsStr = treeJs.getTreeAllSubItems(0L, "-1");
        String[] globalItems = globalItemsStr.split(",");
        for (String globalItem : globalItems) {
            if (filterName.equals(treeJs.getItemTextInTreeById(0L, globalItem))) {
                filterFound = true;
                treeJs.selectItemInTree(0L, globalItem);
            }
        }

        String localItemsStr = treeJs.getTreeAllSubItems(0L, "-2");
        String[] localItems = localItemsStr.split(",");
        for (String localItem : localItems) {
            if (filterName.equals(treeJs.getItemTextInTreeById(0L, localItem))) {
                filterFound = true;
                treeJs.selectItemInTree(0L, localItem);
            }
        }

        if (!filterFound) {
            throw new SeleniumUnexpectedException("Filter not found in organize");
        }
    }

    public void selectByVisibleText(String entityPrefix, Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();

        elementWaitHelper.waitElementById(FILTER_CONTAINER + gridIdx);
        elementWaitHelper.waitElementVisibleById(FILTER_CONTAINER + gridIdx);
        elementWaitHelper.waitElementDisplayById(FILTER_CONTAINER + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(FILTER_SEARCH + gridIdx)).sendKeys(entityPrefix);

        WebElement filterElem = (WebElement) jsHelper.getNewDropDownElement(FILTER_CONTAINER + gridIdx, "scrollContainer", "newGenericDropDownRow", entityPrefix);
        elementWaitHelper.waitElementVisible(filterElem);
        filterElem.click();

        waitHelper.waitGridLoad(gridIdx, gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();

        elementWaitHelper.waitElementById(FILTER_CONTAINER + gridIdx);
        elementWaitHelper.waitElementVisibleById(FILTER_CONTAINER + gridIdx);
        elementWaitHelper.waitElementDisplayById(FILTER_CONTAINER + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_CLEAR_SEARCH + gridIdx)).click();
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();
    }

    public void saveFilterField(String fieldName, FilterFieldType filterFieldType, String cellValue, Long gridIdx) {
        saveFilterField(fieldName, filterFieldType, Arrays.asList(cellValue), gridIdx);
    }

    public void saveFilterField(String fieldName, FilterFieldType filterFieldType, List<String> cellsValues, Long gridIdx) {
        windowHelper.openModal(By.id(BUTTON_OPEN + gridIdx));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();
        if (filterFieldType.equals(FilterFieldType.TEXT)) {
            seleniumSettings.getWebDriver().findElement(By.name(fieldName)).sendKeys(cellsValues.get(0));
        } else if (filterFieldType.equals(FilterFieldType.SELECT)) {
            new Select(seleniumSettings.getWebDriver().findElement(By.name(fieldName))).selectByVisibleText(cellsValues.get(0));
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX)) {
            checkboxHelper.clickByName(fieldName);
        } else if (filterFieldType.equals(FilterFieldType.RADIO_PS_SELECTOR)) {
            By btnOpen = By.xpath("//*[string(@submitName)='btn" + fieldName + "'] | //*[string(@name)='btn" + fieldName + "']");
            psSelectorHelper.selectSpecificValue(btnOpen, By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, cellsValues.get(0), 1L);
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX_PS_SELECTOR)) {
            By btnOpen = By.xpath("//*[string(@submitName)='btn" + fieldName + "'] | //*[string(@name)='btn" + fieldName + "']");
            psSelectorHelper.selectMultipleSpecificValues(btnOpen, 1L, cellsValues, 1L);
        }
        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        jqueryWaitHelper.waitJQueryLoad(); //wait reload filters and grid
        waitHelper.waitGridLoad(gridIdx, gridIdx);
        jqueryWaitHelper.waitJQueryLoad(); //wait reload filters and grid
    }

    public void openSaveFilterForm(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();

        elementWaitHelper.waitElementById(FILTER_CONTAINER + gridIdx);
        elementWaitHelper.waitElementVisibleById(FILTER_CONTAINER + gridIdx);
        elementWaitHelper.waitElementDisplayById(FILTER_CONTAINER + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_SAVE_NEW + gridIdx)).click();

        elementWaitHelper.waitElementById(FILTER_DIALOG_CONTAINER + gridIdx);
        elementWaitHelper.waitElementVisibleById(FILTER_DIALOG_CONTAINER + gridIdx);
        elementWaitHelper.waitElementDisplayById(FILTER_DIALOG_CONTAINER + gridIdx);

        waitHelper.waitWebElement(By.id(FIELD_FILTER_NAME + gridIdx));
        waitHelper.waitWebElement(By.id(FILTER_DIALOG_OK + gridIdx));
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

        waitHelper.waitFiltersCount(gridIdx, beforeSaveSize + 1);

        boolean isSavedFilter = false;
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();
        for (WebElement filter : getFilters(gridIdx)) {
            if (filter.getText().equals(AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + FILTER_NAME)) {
                isSavedFilter = true;
            }
        }
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();
        Assert.assertEquals(isSavedFilter, true, "Filter " + AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + FILTER_NAME + " isn't saved");

        filterWaitHelper.waitCurrentFilterName(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + FILTER_NAME);
        waitHelper.waitGridLoad(gridIdx, gridIdx);
    }

    public void deleteFilter(Long gridIdx, String entityPrefix) {
        int beforeDeleteSize = getFiltersCount(gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();

        elementWaitHelper.waitElementById(FILTER_CONTAINER + gridIdx);
        elementWaitHelper.waitElementVisibleById(FILTER_CONTAINER + gridIdx);
        elementWaitHelper.waitElementDisplayById(FILTER_CONTAINER + gridIdx);

        windowHelper.openModal(By.id(BUTTON_ORGANIZE + gridIdx));
        treeWait.waitTreeLoad(0L);
        waitHelper.waitFormLoad();
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));

        selectFilterInOrganize(AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + FILTER_NAME);

        seleniumSettings.getWebDriver().findElement(By.name(AbstractSeleniumCore.BUTTON_DELETE_TREE_ID_BASE + 0L)).click();
        waitHelper.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();
        treeWait.waitTreeLoad(0L);

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        waitHelper.waitGridLoad(gridIdx, gridIdx);

        waitHelper.waitFiltersCount(gridIdx, beforeDeleteSize - 1);

        boolean isDeletedFilter = false;
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_FILTER + gridIdx)).click();
        for (WebElement filter : getFilters(gridIdx)) {
            if (filter.getText().equals(AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + FILTER_NAME)) {
                isDeletedFilter = true;
            }
        }
        Assert.assertEquals(isDeletedFilter, false, "Filter " + AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + FILTER_NAME + " isn't deleted");

        filterWaitHelper.waitCurrentFilterName(gridIdx, UNSAVED_FILTER_NAME);
        waitHelper.waitGridLoad(gridIdx, gridIdx);
    }

    public void clearFilter(Long gridIdx) {
        windowHelper.openModal(By.id(BUTTON_OPEN + gridIdx));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();
        seleniumSettings.getWebDriver().findElement(By.name(BUTTON_CLEAR)).click();
        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        jqueryWaitHelper.waitJQueryLoad(); //wait reload filters and grid
        waitHelper.waitGridLoad(gridIdx, gridIdx);
        jqueryWaitHelper.waitJQueryLoad(); //wait reload filters and grid

        filterWaitHelper.waitCurrentFilterName(gridIdx, UNSAVED_FILTER_NAME);
        waitHelper.waitGridLoad(gridIdx, gridIdx);
    }

    public String getGridCellValueForFilterTest(Long gridId, String columnId, FilterFieldType filterFieldType) {
        if (jsHelper.getGridIsSupportSortByGridId(gridId)) {
            sortHelper.sortColumn(gridId, columnId, SortType.ASC);
        }
        Long columnIndex = jsHelper.getGridColIndexById(gridId, columnId);
        if (filterFieldType.equals(FilterFieldType.CHECKBOX)) {
            return "YES";
        } else {
            for (Long i = 0L; i < jsHelper.getGridRowsCount(gridId); i = i + 1L) {
                String value = (String) jsHelper.getGridCellValueByRowIndexAndColIndex(gridId, i, columnIndex);
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
        Long columnIndex = jsHelper.getGridColIndexById(gridId, columnId);
        Long rowsCnt = jsHelper.getGridRowsCount(gridId);

        @SuppressWarnings("unchecked")
        List<String> vals =  (List<String>) jsHelper.getGridCellsValuesForColumnByColIndex(gridId, rowsCnt, columnIndex);

        String failMessage = null;
        for (int i = 0; i < rowsCnt.intValue(); i++) {
            failMessage = String.format("Check fails at column [%s] row [%s]. Cell value in grid [%s]. Value in filter [%s]", columnIndex, i, vals.get(i), value);
            Assert.assertEquals(vals.get(i).toLowerCase().contains(value.toLowerCase()), true, failMessage);
        }
    }

    public void assertEmptyFilterField(String fieldName, FilterFieldType filterFieldType, Long gridIdx) {
        windowHelper.openModal(By.id(BUTTON_OPEN + gridIdx));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();
        if (filterFieldType.equals(FilterFieldType.TEXT)) {
            assertHelper.AssertText(fieldName, "");
        } else if (filterFieldType.equals(FilterFieldType.SELECT)) {
            assertHelper.AssertSelect(fieldName, "");
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX)) {
            assertHelper.AssertCheckBoxNew(fieldName, "NO");
        } else if (filterFieldType.equals(FilterFieldType.RADIO_PS_SELECTOR)) {
            assertHelper.AssertRadioPsSelector(fieldName, "btn" + fieldName, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, "", 1L, true);
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX_PS_SELECTOR)) {
            assertHelper.AssertCheckboxPsSelector(fieldName, "btn" + fieldName, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(""), 1L, true);
        }
        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void assertFilterField(String fieldName, FilterFieldType filterFieldType, String cellValue, Long gridIdx) {
        windowHelper.openModal(By.id(BUTTON_OPEN + gridIdx));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();
        if (filterFieldType.equals(FilterFieldType.TEXT)) {
            assertHelper.AssertText(fieldName, cellValue);
        } else if (filterFieldType.equals(FilterFieldType.SELECT)) {
            assertHelper.AssertSelect(fieldName, cellValue);
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX)) {
            assertHelper.AssertCheckBoxNew(fieldName, cellValue);
        } else if (filterFieldType.equals(FilterFieldType.RADIO_PS_SELECTOR)) {
            assertHelper.AssertRadioPsSelector(fieldName, "btn" + fieldName, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, cellValue, 1L, true);
        } else if (filterFieldType.equals(FilterFieldType.CHECKBOX_PS_SELECTOR)) {
            assertHelper.AssertCheckboxPsSelector(fieldName, "btn" + fieldName, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(cellValue), 1L, true);
        }
        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
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
        filterWaitHelper.waitCurrentFilterName(gridIdx, filterName);
    }

}
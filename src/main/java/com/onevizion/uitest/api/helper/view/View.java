package com.onevizion.uitest.api.helper.view;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.ElementHelper;
import com.onevizion.uitest.api.helper.ElementWaitHelper;
import com.onevizion.uitest.api.helper.JsHelper;
import com.onevizion.uitest.api.helper.TabHelper;
import com.onevizion.uitest.api.helper.WaitHelper;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.jquery.JqueryWait;
import com.onevizion.uitest.api.helper.tree.TreeJs;
import com.onevizion.uitest.api.helper.tree.TreeWait;

@Component
public class View {

    public static final String UNSAVED_VIEW_NAME = "Unsaved View";
    public static final String GENERAL_INFO_VIEW_NAME = "G:General Info";

    private final static String VIEW = "newDropdownView";

    public static final String SELECT_VIEW = "ddView"; //TODO change from SELECT_FILTER to FILTER
    public static final String VIEW_CONTAINER = "ddViewContainer";
    public static final String VIEW_SEARCH = "ddViewSearch";
    public static final String BUTTON_CLEAR_SEARCH = "ddViewClearSearch";
    public static final String BUTTON_ORGANIZE = "ddViewBtnOrganize";

    public static final String BUTTON_OPEN = "btnView";
    public static final String FIELD_VIEW_NAME = "txtViewName";
    public static final String VIEW_NAME = "TestViewOption";
    public static final String BUTTON_DELETE = "btnDeleteView";
    public static final String BUTTON_SAVE = "btnSaveView";
    public static final String BUTTON_SAVE_NEW = "unsavedViewIcon";

    public static final String VIEW_DIALOG_CONTAINER = "dialogViewDialogContainer";
    public static final String VIEW_DIALOG_OK = "viewDialogOk";
    public static final String VIEW_DIALOG_CANCEL = "viewDialogCancel";

    public static final String FOLDER_LOCAL = "Local Views";
    public static final String FOLDER_GLOBAL = "Global Views";

    private static final String LEFT_COLUMNS_DIV_ID = "leftListBox";
    public static final String RIGHT_COLUMNS_DIV_ID = "rightListBox";
    private static final String ADD_BUTTON_ID = "addItem";
    public static final String REMOVE_BUTTON_ID = "removeItem";

    public static final Long COLUMN_DIV_HEIGHT = 28L;

    private final static String BUTTON_GROUP_FIELD = "cfg";
    private final static String BUTTON_GROUP_TASK = "tsg";
    private final static String BUTTON_GROUP_DRILLDOWN = "ddg";
    private final static String BUTTON_GROUP_MARKUP = "mug";
    private final static String BUTTON_GROUP_DATE_PAIR = "dp";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Window window;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private TreeWait treeWait;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private TreeJs treeJs;

    @Resource
    private TabHelper tabHelper;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    @Resource
    private ViewWait viewWait;

    @Resource
    private JqueryWait jqueryWait;

    @Resource
    private ElementHelper elementHelper;

    public void checkIsExistViewControl(Long gridIdx, boolean isExist) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = seleniumSettings.getWebDriver().findElements(By.id(VIEW + gridIdx)).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        boolean actualIsExist;
        if (count > 0) {
            actualIsExist = true;
        } else {
            actualIsExist = false;
        }

        Assert.assertEquals(actualIsExist, isExist);
    }

    public int getViewsCount(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id(SELECT_VIEW + gridIdx)).findElements(By.className("leaf")).size();
    }

    public List<WebElement> getViews(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id(SELECT_VIEW + gridIdx)).findElements(By.className("leaf"));
    }

    public String getCurrentViewName(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id(SELECT_VIEW + gridIdx)).findElement(By.className("newGenericDropDownLabel")).getText();
    }

    public void selectViewInOrganize(String viewName) {
        boolean viewFound = false;

        String globalItemsStr = treeJs.getTreeAllSubItems(0L, "-1");
        String[] globalItems = globalItemsStr.split(",");
        for (String globalItem : globalItems) {
            if (viewName.equals(treeJs.getItemTextInTreeById(0L, globalItem))) {
                viewFound = true;
                treeJs.selectItemInTree(0L, globalItem);
            }
        }

        String localItemsStr = treeJs.getTreeAllSubItems(0L, "-2");
        String[] localItems = localItemsStr.split(",");
        for (String localItem : localItems) {
            if (viewName.equals(treeJs.getItemTextInTreeById(0L, localItem))) {
                viewFound = true;
                treeJs.selectItemInTree(0L, localItem);
            }
        }

        if (!viewFound) {
            throw new SeleniumUnexpectedException("View not found in organize");
        }
    }

    public void selectByVisibleText(Long gridIdx, String entityPrefix) {
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_VIEW + gridIdx)).click();

        elementWaitHelper.waitElementById(VIEW_CONTAINER + gridIdx);
        elementWaitHelper.waitElementVisibleById(VIEW_CONTAINER + gridIdx);
        elementWaitHelper.waitElementDisplayById(VIEW_CONTAINER + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(VIEW_SEARCH + gridIdx)).sendKeys(entityPrefix);

        WebElement viewElem = (WebElement) jsHelper.getNewDropDownElement(VIEW_CONTAINER + gridIdx, "scrollContainer", "newGenericDropDownRow", entityPrefix);
        elementWaitHelper.waitElementVisible(viewElem);
        viewElem.click();

        waitHelper.waitGridLoad(gridIdx, gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(SELECT_VIEW + gridIdx)).click();

        elementWaitHelper.waitElementById(VIEW_CONTAINER + gridIdx);
        elementWaitHelper.waitElementVisibleById(VIEW_CONTAINER + gridIdx);
        elementWaitHelper.waitElementDisplayById(VIEW_CONTAINER + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_CLEAR_SEARCH + gridIdx)).click();
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_VIEW + gridIdx)).click();
    }

    public void selectFolderForSaveViewByVisibleText(Long gridIdx, String folderName) {
        String currentFolderName = seleniumSettings.getWebDriver().findElement(By.id("ddExistingViews"  + gridIdx)).findElement(By.className("newGenericDropDownLabel")).getText();
        if (folderName.equals(currentFolderName)) {
            return;
        }

        seleniumSettings.getWebDriver().findElement(By.id("ddExistingViews" + gridIdx)).click();

        elementWaitHelper.waitElementById("ddViewFormSaveContainer" + gridIdx);
        elementWaitHelper.waitElementVisibleById("ddViewFormSaveContainer" + gridIdx);
        elementWaitHelper.waitElementDisplayById("ddViewFormSaveContainer" + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id("ddViewFormSaveSearch" + gridIdx)).sendKeys(folderName);

        WebElement viewElem = (WebElement) jsHelper.getNewDropDownElement("ddViewFormSaveContainer" + gridIdx, "scrollContainer", "newGenericDropDownRow", folderName);
        elementWaitHelper.waitElementVisible(viewElem);
        viewElem.click();

        seleniumSettings.getWebDriver().findElement(By.id("ddExistingViews" + gridIdx)).click();

        elementWaitHelper.waitElementById("ddViewFormSaveContainer" + gridIdx);
        elementWaitHelper.waitElementVisibleById("ddViewFormSaveContainer" + gridIdx);
        elementWaitHelper.waitElementDisplayById("ddViewFormSaveContainer" + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id("ddViewFormSaveClearSearch" + gridIdx)).click();
        seleniumSettings.getWebDriver().findElement(By.id("ddExistingViews" + gridIdx)).click();
    }

    public void isExistAndSelectedView(Long gridIdx, String entityPrefix) {
        boolean isSavedView = false;
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_VIEW + gridIdx)).click();
        for (WebElement view : getViews(gridIdx)) {
            if (view.getText().equals(entityPrefix)) {
                isSavedView = true;
            }
        }
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_VIEW + gridIdx)).click();
        Assert.assertEquals(isSavedView, true, "View " + entityPrefix + " isn't saved");

        viewWait.waitCurrentViewName(gridIdx, entityPrefix);
        waitHelper.waitGridLoad(gridIdx, gridIdx);
    }

    public void openSaveViewForm(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_VIEW + gridIdx)).click();

        elementWaitHelper.waitElementById(VIEW_CONTAINER + gridIdx);
        elementWaitHelper.waitElementVisibleById(VIEW_CONTAINER + gridIdx);
        elementWaitHelper.waitElementDisplayById(VIEW_CONTAINER + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_SAVE_NEW + gridIdx)).click();

        elementWaitHelper.waitElementById(VIEW_DIALOG_CONTAINER + gridIdx);
        elementWaitHelper.waitElementVisibleById(VIEW_DIALOG_CONTAINER + gridIdx);
        elementWaitHelper.waitElementDisplayById(VIEW_DIALOG_CONTAINER + gridIdx);

        waitHelper.waitWebElement(By.id("lbViewType" + gridIdx));
        waitHelper.waitWebElement(By.id(FIELD_VIEW_NAME + gridIdx));
        waitHelper.waitWebElement(By.id(VIEW_DIALOG_OK + gridIdx));

        AbstractSeleniumCore.sleep(2000L); //TODO when form visible then all elements refreshing. we should first refresh elements and then show form
    }

    public void closeSaveViewFormOk(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(VIEW_DIALOG_OK + gridIdx)).click();
    }

    public void closeSaveViewFormCancel(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(VIEW_DIALOG_CANCEL + gridIdx)).click();
    }

    public void saveView(Long gridIdx, String entityPrefix, boolean isLocal, boolean isNew) {
        int beforeSaveSize = getViewsCount(gridIdx);

        openSaveViewForm(gridIdx);

        if (isNew) {
            new Select(seleniumSettings.getWebDriver().findElement(By.id("lbViewType" + gridIdx))).selectByVisibleText("New");
        } else {
            new Select(seleniumSettings.getWebDriver().findElement(By.id("lbViewType" + gridIdx))).selectByVisibleText("Existing");
        }
        if (isLocal) {
            selectFolderForSaveViewByVisibleText(gridIdx, FOLDER_LOCAL);
        } else {
            selectFolderForSaveViewByVisibleText(gridIdx, FOLDER_GLOBAL);
        }
        if (isNew) {
            seleniumSettings.getWebDriver().findElement(By.id(FIELD_VIEW_NAME + gridIdx)).sendKeys(entityPrefix);
        } else {
            if (isLocal) {//TODO
                new Select(seleniumSettings.getWebDriver().findElement(By.id("lbViewName"))).selectByVisibleText(AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix);//TODO
            } else {//TODO
                new Select(seleniumSettings.getWebDriver().findElement(By.id("lbGViewName"))).selectByVisibleText(AbstractSeleniumCore.PREFIX_GLOBAL + entityPrefix);//TODO
            }//TODO
        }

        closeSaveViewFormOk(gridIdx);

        if (isNew) {
            waitHelper.waitViewsCount(gridIdx, beforeSaveSize + 1);
        } else {
            waitHelper.waitViewsCount(gridIdx, beforeSaveSize);
        }

        if (isLocal) {
            isExistAndSelectedView(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix);
        } else {
            isExistAndSelectedView(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + entityPrefix);
        }
    }

    public void deleteView(Long gridIdx, String entityPrefix) {
        String currentViewName = getCurrentViewName(gridIdx);

        int beforeDeleteSize = getViewsCount(gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(SELECT_VIEW + gridIdx)).click();

        elementWaitHelper.waitElementById(VIEW_CONTAINER + gridIdx);
        elementWaitHelper.waitElementVisibleById(VIEW_CONTAINER + gridIdx);
        elementWaitHelper.waitElementDisplayById(VIEW_CONTAINER + gridIdx);

        window.openModal(By.id(BUTTON_ORGANIZE + gridIdx));
        treeWait.waitTreeLoad(0L);
        waitHelper.waitFormLoad();
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));

        selectViewInOrganize(entityPrefix);

        seleniumSettings.getWebDriver().findElement(By.name(AbstractSeleniumCore.BUTTON_DELETE_TREE_ID_BASE + 0L)).click();
        waitHelper.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();
        treeWait.waitTreeLoad(0L);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        waitHelper.waitGridLoad(gridIdx, gridIdx);

        waitHelper.waitViewsCount(gridIdx, beforeDeleteSize - 1);

        boolean isDeletedView = false;
        seleniumSettings.getWebDriver().findElement(By.id(SELECT_VIEW + gridIdx)).click();
        for (WebElement view : getViews(gridIdx)) {
            if (view.getText().equals(entityPrefix)) {
                isDeletedView = true;
            }
        }
        Assert.assertEquals(isDeletedView, false, "View " + entityPrefix + " isn't deleted");

        if (currentViewName.equals(entityPrefix)) {
            viewWait.waitCurrentViewName(gridIdx, UNSAVED_VIEW_NAME);
            waitHelper.waitGridLoad(gridIdx, gridIdx);
        } else {
            viewWait.waitCurrentViewName(gridIdx, currentViewName);
            waitHelper.waitGridLoad(gridIdx, gridIdx);
        }
    }

    public List<WebElement> getLeftColumns() {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> leftColumns = seleniumSettings.getWebDriver().findElement(By.id(LEFT_COLUMNS_DIV_ID)).findElements(By.className("record"));
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return leftColumns;
    }

    public List<WebElement> getRightColumns() {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> rightColumns = seleniumSettings.getWebDriver().findElement(By.id(RIGHT_COLUMNS_DIV_ID)).findElements(By.className("record"));
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return rightColumns;
    }

    public void checkViewOptionsNew(Long gridIdx, String entityPrefix,
            Long gridColumnsUnsavedView, List<String> leftColumnsUnsavedView, List<String> rightColumnsUnsavedView,
            Long gridColumnsLocalView1, List<String> leftColumnsLocalView1, List<String> rightColumnsLocalView1,
            Long gridColumnsGlobalView1, List<String> leftColumnsGlobalView1, List<String> rightColumnsGlobalView1,
            Long gridColumnsLocalView2, List<String> leftColumnsLocalView2, List<String> rightColumnsLocalView2,
            Long gridColumnsGlobalView2, List<String> leftColumnsGlobalView2, List<String> rightColumnsGlobalView2) {
        //Save Local View 1
        selectColumns(gridIdx, leftColumnsLocalView1, rightColumnsLocalView1);
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsLocalView1);
        checkColumns(gridIdx, leftColumnsLocalView1, rightColumnsLocalView1);
        saveView(gridIdx, entityPrefix + View.VIEW_NAME + "1", true, true);
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsLocalView1);
        checkColumns(gridIdx, leftColumnsLocalView1, rightColumnsLocalView1);

        //Save Global View 1
        selectColumns(gridIdx, leftColumnsGlobalView1, rightColumnsGlobalView1);
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsGlobalView1);
        checkColumns(gridIdx, leftColumnsGlobalView1, rightColumnsGlobalView1);
        saveView(gridIdx, entityPrefix + View.VIEW_NAME + "1", false, true);
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsGlobalView1);
        checkColumns(gridIdx, leftColumnsGlobalView1, rightColumnsGlobalView1);

        //Save Local View 2
        selectColumns(gridIdx, leftColumnsLocalView2, rightColumnsLocalView2);
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsLocalView2);
        checkColumns(gridIdx, leftColumnsLocalView2, rightColumnsLocalView2);
        saveView(gridIdx, entityPrefix + View.VIEW_NAME + "2", true, true);
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsLocalView2);
        checkColumns(gridIdx, leftColumnsLocalView2, rightColumnsLocalView2);

        //Save Global View 2
        selectColumns(gridIdx, leftColumnsGlobalView2, rightColumnsGlobalView2);
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsGlobalView2);
        checkColumns(gridIdx, leftColumnsGlobalView2, rightColumnsGlobalView2);
        saveView(gridIdx, entityPrefix + View.VIEW_NAME + "2", false, true);
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsGlobalView2);
        checkColumns(gridIdx, leftColumnsGlobalView2, rightColumnsGlobalView2);

        //Unsaved View
        selectColumns(gridIdx, leftColumnsUnsavedView, rightColumnsUnsavedView);
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsUnsavedView);
        checkColumns(gridIdx, leftColumnsUnsavedView, rightColumnsUnsavedView);

        //Select Local View 1
        selectByVisibleText(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + View.VIEW_NAME + "1");
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsLocalView1);
        checkColumns(gridIdx, leftColumnsLocalView1, rightColumnsLocalView1);

        //Select Global View 1
        selectByVisibleText(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + entityPrefix + View.VIEW_NAME + "1");
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsGlobalView1);
        checkColumns(gridIdx, leftColumnsGlobalView1, rightColumnsGlobalView1);

        //Select Unsaved View
        selectByVisibleText(gridIdx, View.UNSAVED_VIEW_NAME);
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsUnsavedView);
        checkColumns(gridIdx, leftColumnsUnsavedView, rightColumnsUnsavedView);

        //Select Local View 2
        selectByVisibleText(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + View.VIEW_NAME + "2");
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsLocalView2);
        checkColumns(gridIdx, leftColumnsLocalView2, rightColumnsLocalView2);

        //Select Global View 2
        selectByVisibleText(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + entityPrefix + View.VIEW_NAME + "2");
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsGlobalView2);
        checkColumns(gridIdx, leftColumnsGlobalView2, rightColumnsGlobalView2);

        //Delete Local 1
        deleteView(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + View.VIEW_NAME + "1");
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsGlobalView2);
        checkColumns(gridIdx, leftColumnsGlobalView2, rightColumnsGlobalView2);

        //Delete Global 1
        deleteView(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + entityPrefix + View.VIEW_NAME + "1");
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsGlobalView2);
        checkColumns(gridIdx, leftColumnsGlobalView2, rightColumnsGlobalView2);

        //Delete Local 2
        deleteView(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + View.VIEW_NAME + "2");
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsGlobalView2);
        checkColumns(gridIdx, leftColumnsGlobalView2, rightColumnsGlobalView2);

        //Delete Global 2
        deleteView(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + entityPrefix + View.VIEW_NAME + "2");
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumnsUnsavedView);
        checkColumns(gridIdx, leftColumnsUnsavedView, rightColumnsUnsavedView);
    }

    public Select selectApplet(Select apps, Select tabs, String appletName, int cntTabs) {
        apps.selectByVisibleText(appletName);
        jqueryWait.waitJQueryLoad(); //wait load tabs and fields
        waitHelper.waitListBoxLoad(tabs);
        jqueryWait.waitJQueryLoad(); //wait load tabs and fields
        Assert.assertEquals(apps.getFirstSelectedOption().getText(), appletName);
        jqueryWait.waitJQueryLoad(); //wait load tabs and fields
        waitHelper.waitListBoxLoadCnt(tabs, cntTabs);
        jqueryWait.waitJQueryLoad(); //wait load tabs and fields
        Assert.assertEquals(tabs.getOptions().size(), cntTabs, "Tabs have wrong cnt");
        return tabs;
    }

    public Select selectTab(Select tabs, Select allFields, String tabName, int cntFields) {
        tabs.selectByVisibleText(tabName);
        jqueryWait.waitJQueryLoad(); //wait load tabs and fields
        waitHelper.waitListBoxLoad(allFields);
        jqueryWait.waitJQueryLoad(); //wait load tabs and fields
        waitHelper.waitListBoxLoadCnt(allFields, cntFields);
        jqueryWait.waitJQueryLoad(); //wait load tabs and fields
        Assert.assertEquals(allFields.getOptions().size(), cntFields, "All Fields have wrong cnt");
        return allFields;
    }

    public WebElement selectTab(Select tabs, WebElement allFields, String tabName, int cntFields) {
        tabs.selectByVisibleText(tabName);
        jqueryWait.waitJQueryLoad(); //wait load tabs and fields
        waitHelper.waitListBoxLoad(allFields);
        jqueryWait.waitJQueryLoad(); //wait load tabs and fields
        waitHelper.waitListBoxLoadCnt(allFields, cntFields);
        jqueryWait.waitJQueryLoad(); //wait load tabs and fields
        Assert.assertEquals(allFields.findElements(By.tagName("div")).size(), cntFields, "All Fields have wrong cnt");
        return allFields;
    }

    public void selectAllColumns(Long gridIdx) {
        window.openModal(By.id(View.BUTTON_OPEN + gridIdx));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        List<WebElement> actualRightColumns = getRightColumns();
        jsHelper.scrollNewDropDownTop(RIGHT_COLUMNS_DIV_ID, "scrollContainer", (actualRightColumns.size() - 1) * COLUMN_DIV_HEIGHT);
        actualRightColumns.get(actualRightColumns.size() - 1).click();

        while (seleniumSettings.getWebDriver().findElement(By.id(ADD_BUTTON_ID)).isEnabled()) {
            seleniumSettings.getWebDriver().findElement(By.id(ADD_BUTTON_ID)).click();
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(gridIdx, gridIdx);

        viewWait.waitCurrentViewName(gridIdx, View.UNSAVED_VIEW_NAME);
        waitHelper.waitGridLoad(gridIdx, gridIdx);
    }

    public void selectAndCheckColumns(Long gridIdx, Long gridColumns, List<String> leftColumns, List<String> rightColumns) {
        selectColumns(gridIdx, leftColumns, rightColumns);
        Assert.assertEquals(jsHelper.getGridColumnsCount(gridIdx), gridColumns);
        checkColumns(gridIdx, leftColumns, rightColumns);
    }

    private void selectColumns(Long gridIdx, List<String> leftColumns, List<String> rightColumns) {
        window.openModal(By.id(View.BUTTON_OPEN + gridIdx));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        waitLeftListBoxReady();
        waitRightListBoxReady();

        List<WebElement> actualRightColumns = getRightColumns();
        jsHelper.scrollNewDropDownTop(RIGHT_COLUMNS_DIV_ID, "scrollContainer", (actualRightColumns.size() - 1) * COLUMN_DIV_HEIGHT);
        actualRightColumns.get(actualRightColumns.size() - 1).click();

        while (seleniumSettings.getWebDriver().findElement(By.id(REMOVE_BUTTON_ID)).isEnabled()) {
            seleniumSettings.getWebDriver().findElement(By.id(REMOVE_BUTTON_ID)).click();
        }

        for (String rightColumn : rightColumns) {
            List<WebElement> actualLeftColumns = getLeftColumns();
            for (int i = 0; i < actualLeftColumns.size(); i++) {
                jsHelper.scrollNewDropDownTop(LEFT_COLUMNS_DIV_ID, "scrollContainer", i * COLUMN_DIV_HEIGHT);
                if (actualLeftColumns.get(i).findElements(By.className("labelField")).get(0).getText().equals(rightColumn)) {
                    actualLeftColumns.get(i).click();
                    seleniumSettings.getWebDriver().findElement(By.id(ADD_BUTTON_ID)).click();
                    break;
                }
            }
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(gridIdx, gridIdx);

        viewWait.waitCurrentViewName(gridIdx, View.UNSAVED_VIEW_NAME);
        waitHelper.waitGridLoad(gridIdx, gridIdx);
    }

    private void checkColumns(Long gridIdx, List<String> leftColumns, List<String> rightColumns) {
        window.openModal(By.id(View.BUTTON_OPEN + gridIdx));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        waitLeftListBoxReady();
        waitRightListBoxReady();

        List<WebElement> actualLeftColumns = getLeftColumns();
        Assert.assertEquals(actualLeftColumns.size(), leftColumns.size());
        for (int i = 0; i < actualLeftColumns.size(); i++) {
            jsHelper.scrollNewDropDownTop(LEFT_COLUMNS_DIV_ID, "scrollContainer", i * COLUMN_DIV_HEIGHT);
            Assert.assertEquals(actualLeftColumns.get(i).findElements(By.className("labelField")).get(0).getText(), leftColumns.get(i));
        }

        List<WebElement> actualRightColumns = getRightColumns();
        Assert.assertEquals(actualRightColumns.size(), rightColumns.size());
        for (int i = 0; i < actualRightColumns.size(); i++) {
            jsHelper.scrollNewDropDownTop(RIGHT_COLUMNS_DIV_ID, "scrollContainer", i * COLUMN_DIV_HEIGHT);
            Assert.assertEquals(actualRightColumns.get(i).findElements(By.className("labelField")).get(0).getText(), rightColumns.get(i));
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void switchToRootSubgroup() {
        //elementHelper.click(seleniumSettings.getWebDriver().findElement(By.id("navPanel")).findElements(By.className("navLink")).get(0));
        elementHelper.click(seleniumSettings.getWebDriver().findElement(By.id("navPanel")).findElement(By.tagName("input")));
        waitLeftListBoxReady();
    }

    public void switchToParentSubgroup() {
        List<WebElement> links = seleniumSettings.getWebDriver().findElement(By.id("navPanel")).findElements(By.className("navLink"));
        elementHelper.click(links.get(links.size() - 2));
        waitLeftListBoxReady();
    }

    public void switchToSubgroup(String subgroupName) {
        elementHelper.click(seleniumSettings.getWebDriver().findElement(By.id("navPanel")).findElement(By.name(subgroupName)));
        waitLeftListBoxReady();
    }

    public void switchToSubgroupInList(String text) {
        WebElement element = null;
        List<WebElement> subgroups = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent")).findElements(By.className("groupRecord"));
        for (WebElement subgroup : subgroups) {
            if (subgroup.getAttribute("innerText").trim().equals(text)) {
                if (element != null) {
                    throw new SeleniumUnexpectedException("Subgroup with text[" + text + "] found many times");
                }
                element = subgroup;
            }
        }

        if (element == null) {
            throw new SeleniumUnexpectedException("Subgroup with text[" + text + "] not found");
        }

        elementHelper.click(element);
        waitLeftListBoxReady();
    }

    public void switchToFieldGroup() {
        elementHelper.clickById(BUTTON_GROUP_FIELD);
        waitLeftListBoxReady();
    }

    public void switchToTaskGroup() {
        elementHelper.clickById(BUTTON_GROUP_TASK);
        waitLeftListBoxReady();
    }

    public void switchToDrillDownGroup() {
        elementHelper.clickById(BUTTON_GROUP_DRILLDOWN);
        waitLeftListBoxReady();
    }

    public void switchToMarkupGroup() {
        elementHelper.clickById(BUTTON_GROUP_MARKUP);
        waitLeftListBoxReady();
    }

    public void switchToDatePairGroup() {
        elementHelper.clickById(BUTTON_GROUP_DATE_PAIR);
        waitLeftListBoxReady();
    }

    public void waitCurrentViewName(Long gridIdx, String viewName) {
        viewWait.waitCurrentViewName(gridIdx, viewName);
    }

    public void waitLeftListBoxReady() {
        viewWait.waitLeftListBoxReady();
    }

    public void waitRightListBoxReady() {
        viewWait.waitRightListBoxReady();
    }

}
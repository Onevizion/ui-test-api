package com.onevizion.uitest.api.helper;

import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.filter.Filter;
import com.onevizion.uitest.api.helper.tab.Tab;
import com.onevizion.uitest.api.helper.view.View;

@Component
public class Wait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Js js;

    @Resource
    private Grid grid;

    @Resource
    private Filter filter;

    @Resource
    private View view;

    @Resource
    private Tab tab;

    @Resource
    private HtmlSelect htmlSelect;

    public void waitWebElement(final By elementLocator) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for WebElement [" + elementLocator.toString() + "] is failed.")
            .until(webdriver -> webdriver.findElement(elementLocator));
    }

    public void waitIframeGridLoad(final Long gridId) {
        waitWebElement(By.id(AbstractSeleniumCore.GRID_ID_BASE + gridId));
        waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + gridId));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for grid with id=[" + gridId + "] is failed")
            .until(webdriver -> !webdriver.findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + gridId)).isDisplayed());
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for grid with id=[" + gridId + "] is failed")
            .until(webdriver -> js.isGridLoaded(gridId).equals("1"));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for grid with id=[" + gridId + "] is failed")
            .until(webdriver -> js.isGridDataLoaded(gridId));
    }

    public void waitGridRowsCount(final Long gridId, final Long rowsCount) {
        LongSupplier actualValueSupplier = ()-> grid.getGridRowsCount(gridId);

        Supplier<String> messageSupplier = ()-> "Waiting rows count for grid with id=[" + gridId + "] expectedVal=[" + rowsCount + "] actualVal=[" + actualValueSupplier.getAsLong() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> rowsCount.equals(actualValueSupplier.getAsLong()));
    }

    public void waitFiltersCount(final Long gridIdx, final int filtersCount) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting filters count=[" + filtersCount + "] for grid with idx=[" + gridIdx + "] is failed")
            .until(webdriver -> filtersCount == filter.getFiltersCount(gridIdx));
    }

    public void waitViewsCount(final Long gridIdx, final int viewsCount) {
        IntSupplier actualValueSupplier = ()-> view.getViewsCount(gridIdx);

        Supplier<String> messageSupplier = ()-> "Waiting views count for grid with idx=[" + gridIdx + "] expectedVal=[" + viewsCount + "] actualVal=[" + actualValueSupplier.getAsInt() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(webdriver -> viewsCount == actualValueSupplier.getAsInt());
    }

    public void waitNewDropDownCount(final By elemenLocator, final int afterCount) {
        waitWebElement(elemenLocator);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("DropDown=[" + elemenLocator.toString() + "] have wrong count. Expected count=[" + afterCount + "]")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> {
                int size = webdriver.findElement(elemenLocator).findElement(By.className("scrollContent")).findElements(By.className("newDropDownRow")).size();
                return size == afterCount;
            });
    }

    public void waitDropDownCount(final By elemenLocator, final int afterCount) {
        waitWebElement(elemenLocator);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("DropDown=[" + elemenLocator.toString() + "] have wrong count. Expected count=[" + afterCount + "]" + " but Actual count=[" + new Select(seleniumSettings.getWebDriver().findElement(elemenLocator)).getOptions().size() + "]")
            .until(webdriver -> new Select(webdriver.findElement(elemenLocator)).getOptions().size() == afterCount);
    }

    public void waitIsWindowClosed() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for closing modal window.")
            .until(webdriver -> !js.isWindowClosed());
    }

    public void waitFormLoad() {
        waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for form is failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !webdriver.findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE)).isDisplayed());
    }

    public void waitAlert() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Timed out after " + seleniumSettings.getDefaultTimeout() + " seconds waiting for alert.")
            .until(webdriver -> webdriver.switchTo().alert().getText() != null);
    }

    public void waitGridCurrentTid(final Long gridIndex, final Long previosTid) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Loading items in ListBox failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !previosTid.equals(js.getGridCurTid(gridIndex)));
    }

    public void waitBpDocHelpLoad(final String expectedValue, final boolean isPresent) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("BpDocHelp loading failed.")
            .until(webdriver -> {
                if (isPresent) {
                    return webdriver.getPageSource().toUpperCase().contains(expectedValue);
                } else {
                    return !webdriver.getPageSource().toUpperCase().contains(expectedValue);
                }
            });
    }

    public void waitElemsArrCount(final Long afterCount) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("ElemsArr have wrong count. Expected count=[" + afterCount + "] but Actual count=[" + js.getElemsArrLength() + "]")
            .until(webdriver -> afterCount.equals(js.getElemsArrLength()));
    }

    public void waitInputLoad(final WebElement webElement) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Loading input failed.")
            .until(webdriver -> !webElement.getAttribute("value").equals("loading..."));
    }

    public void waitListBoxLoadCnt(final Select select, final int cnt) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Loading items in ListBox failed. Expected count=[" + cnt + "] but Actual count=[" + select.getOptions().size() + "]")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> select.getOptions().size() == cnt);
    }

    /*new void to support new duallist box*/
    public void waitListBoxLoadCnt(final WebElement select, final int cnt) {
        Supplier<String> supplier = ()-> "Loading items in ListBox failed. Expected count=[" + cnt + "] but Actual count=[" + select.findElements(By.tagName("div")).size() + "]";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(supplier)
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> select.findElements(By.tagName("div")).size() == cnt);
    }

    public void waitListBoxLoad(final Select select) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Loading items in ListBox failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !select.getOptions().get(0).getText().equals("loading..."));
    }

    /*new void to support new duallist box*/
    public void waitListBoxLoad(final WebElement select) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Loading items in ListBox failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !select.findElements(By.tagName("div")).get(0).getText().equals("<span style=\"color:\">loading...</span>"));
    }

    public void waitListBoxLoad2(final Select select) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Loading items in ListBox failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> select.getOptions().size() > 1);
    }

    public void waitListBoxLoad3(final Select select) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Loading items in ListBox failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !select.getOptions().isEmpty());
    }

    /*new void to support new duallist box*/
    public void waitListBoxLoad3(final WebElement select) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Loading items in ListBox failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !select.findElements(By.tagName("div")).isEmpty());
    }

    public void waitListBoxLoad4(final Select select, final String text) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Loading items in ListBox failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> {
                for (WebElement option :select.getOptions()) {
                    if (htmlSelect.getOptionText(option).equals(text)) {
                        return true;
                    }
                }
                return false;
            });
    }

    public void waitReloadForm(final String str) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for form is failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> webdriver.getCurrentUrl().contains(str));
    }

    public void waitTabLoad(final Long tabIndex) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for tab with index=[" + tabIndex + "] is failed")
            .until(webdriver -> !webdriver.findElement(By.id("divPage" + tabIndex)).getAttribute("loaded").equals("0"));
    }

    public void waitConfigTabLoad(String tabLabel) {
        Long tabIndex = tab.getTabIndex(tabLabel);
        waitConfigTabLoad(tabIndex);
    }

    public void waitConfigTabLoad(Long tabIndex) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for tab with index=[" + tabIndex + "] is failed")
            .until(webdriver -> !webdriver.findElement(By.id("divPage" + tabIndex)).getAttribute("innerHTML").contains("Loading Tab. Please wait..."));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for tab with index=[" + tabIndex + "] is failed")
            .until(webdriver -> !webdriver.findElement(By.id("divPage" + tabIndex)).getAttribute("loaded").equals("0"));
    }

    public void waitGridRowEditorLoad() {
        waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Grid Row Editor is failed")
            .until(webdriver -> !webdriver.findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE)).isDisplayed());
        waitWebElement(By.id(AbstractSeleniumCore.SAVING_ID_BASE));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Grid Row Editor is failed")
            .until(webdriver -> !webdriver.findElement(By.id(AbstractSeleniumCore.SAVING_ID_BASE)).isDisplayed());
    }

    public void waitLoadingLoad() {
        waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting loading is failed")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !webdriver.findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE)).isDisplayed());
    }

    public void waitLoadingLoad(final Long gridId) {
        waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + gridId));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting loading is failed")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !webdriver.findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + gridId)).isDisplayed());
    }

    public void waitSavingLoad() {
        waitWebElement(By.id(AbstractSeleniumCore.SAVING_ID_BASE));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting saving is failed")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !webdriver.findElement(By.id(AbstractSeleniumCore.SAVING_ID_BASE)).isDisplayed());
    }

    public void waitSavingLoad(final Long gridId) {
        waitWebElement(By.id(AbstractSeleniumCore.SAVING_ID_BASE + gridId));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting saving is failed")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !webdriver.findElement(By.id(AbstractSeleniumCore.SAVING_ID_BASE + gridId)).isDisplayed());
    }

    public void waitSplitGridRightLoad(final Long gridId) {
        waitWebElement(By.id(AbstractSeleniumCore.LOADING_SPLIT_GRID_RIGHT_ID_BASE + gridId));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for split grid right with id=[" + gridId + "] is failed")
            .until(webdriver -> !webdriver.findElement(By.id(AbstractSeleniumCore.LOADING_SPLIT_GRID_RIGHT_ID_BASE + gridId)).isDisplayed());
    }

    public void waitGridCellValue(final Long gridId, final Long columnIndex, final Long rowIndex, final String val) {
        Supplier<String> actualValueSupplier = ()-> {
            String value = js.getGridCellValueByRowIndexAndColIndex(gridId, rowIndex, columnIndex);
            if ("&nbsp;".equals(value)) {
                value = "";
            }
            value = value.replaceAll("^<[aA].*?>", "");
            value = value.replaceAll("</[aA]>$", "");
            value = value.replaceAll("<[iI][mM][gG].*?>", "");
            value = value.replaceAll("<[sS][pP][aA][nN].*?>", "");
            value = value.replaceAll("</[sS][pP][aA][nN]>", "");
            value = value.replaceAll("<[sS][vV][gG].*?>.*</[sS][vV][gG]>", "");
            value = value.replaceAll("^<[aA].*?>", "");
            value = value.replaceAll("</[aA]>$", "");
            value = StringUtils.substringBefore(value, "\n");
            return value;
        };

        Supplier<String> messageSupplier = ()-> "Waiting for Grid Cell gridId=[" + gridId + "] columnIndex=[" + columnIndex + "] rowIndex=[" + rowIndex + "] expectedVal=[" + val + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .ignoring(SeleniumUnexpectedException.class)
            .until(webdriver -> val.equals(actualValueSupplier.get()));
    }

    public void waitGridCellTxtValue(final Long gridId, final Long columnIndex, final Long rowIndex, final String val) {
        Supplier<String> actualValueSupplier = ()-> {
            String columnType = js.getGridColumnType(gridId, columnIndex);
            String value = js.getGridCellValueTxtByRowIndexAndColIndex(gridId, rowIndex, columnIndex);
            if ("&nbsp;".equals(value)) {
                value = "";
            }
            value = value.replaceAll("^<[aA].*?>", "");
            value = value.replaceAll("</[aA]>$", "");
            value = StringUtils.substringBefore(value, "\n");
            if ("RichMemo".equals(columnType)) {
                value = value.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_1, AbstractSeleniumCore.SPECIAL_CHARACTERS_1);
                value = value.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_2, AbstractSeleniumCore.SPECIAL_CHARACTERS_2);
                value = value.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_3, AbstractSeleniumCore.SPECIAL_CHARACTERS_3);
                value = value.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_4, AbstractSeleniumCore.SPECIAL_CHARACTERS_4);
            }
            return value;
        };

        Supplier<String> messageSupplier = ()-> "Waiting for Grid Cell gridId=[" + gridId + "] columnIndex=[" + columnIndex + "] rowIndex=[" + rowIndex + "] expectedVal=[" + val + "] actualVal=[" + actualValueSupplier.get() + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .ignoring(SeleniumUnexpectedException.class)
            .until(webdriver -> val.equals(actualValueSupplier.get()));
    }

    public void waitDxtmlxWindowOpened(final String windowName) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("")
            .until(webdriver -> js.isDxtmlxWindowOpened(windowName));
    }

    public void waitCodeMirrorLoad(final String elementId) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Code Mirror for element with id=["+elementId+"] is failed")
            .until(webdriver -> js.isCodeMirrorLoaded(elementId));
    }

    public void waitCodeMirrorHistorySize(final String elementId, final Long undo, final Long redo) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Code Mirror for element with id=["+elementId+"] is failed")
            .until(webdriver -> {
                Long actUndo = js.getCodeMirrorUndoSize(elementId);
                Long actRedo = js.getCodeMirrorRedoSize(elementId);
                return actUndo.equals(undo) && actRedo.equals(redo);
            });
    }

    public void waitBplImportFileSubmitDone() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for DropGrid Verification is failed")
            .until(webdriver -> js.bplImportFileSubmitDone());
    }

}
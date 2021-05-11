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
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class RelationSelector {

    private static final String REL_SEL_BUTTON = "parentChildButton";

    private static final String REL_SEL_CONTAINER = "dd_content";
    private static final String REL_SEL_CONTAINER_ITEM = "drop_down_item";
    private static final String REL_SEL_CONTAINER_ITEM_TEXT = "ddc_label";
    private static final String REL_SEL_CONTAINER_ITEM_COUNT = "ddc_count";

    private static final String REL_SEL_MAIN_ELEMENT = "parentsChildrenDropDown";
    private static final String REL_SEL_MAIN_ELEMENT_CURRENT = "ddsc_click";
    private static final String REL_SEL_MAIN_ELEMENT_CURRENT_TEXT = "ddsc_label";
    private static final String REL_SEL_MAIN_ELEMENT_CURRENT_COUNT = "ddsc_count";

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Js js;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private Window window;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private RelationSelectorWait relationSelectorWait;

    @Autowired
    private RelationSelectorJs relationSelectorJs;

    public void checkRelationSelectorValuesCount(Long gridIdx, int count) {
        List<WebElement> rowNames = seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_MAIN_ELEMENT + gridIdx)).findElements(By.className(REL_SEL_CONTAINER_ITEM));
        Assert.assertEquals(rowNames.size(), count);
    }

    public void checkRelationSelectorValue(Long gridIdx, String trackorType, int count) {
        int cnt = 0;

        List<WebElement> rowNames = seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_MAIN_ELEMENT + gridIdx)).findElements(By.className(REL_SEL_CONTAINER_ITEM));
        for (WebElement rowName : rowNames) {
            String actualRowName = rowName.findElement(By.className(REL_SEL_CONTAINER_ITEM_TEXT)).getText();

            String actualCount = rowName.findElement(By.className(REL_SEL_CONTAINER_ITEM_COUNT)).getText();
            int intActualCount = Integer.parseInt(actualCount);

            if (trackorType.equals(actualRowName) && count == intActualCount) {
                cnt = cnt + 1;
            }
        }

        if (cnt == 0) {
            throw new SeleniumUnexpectedException("Trackor Type not found in Parent/Child Drop Down");
        } else if (cnt > 1) {
            throw new SeleniumUnexpectedException("Duplicate values in Parent/Child Drop Down");
        }
    }

    public void selectGridRow(Long gridIdx, int rowIndex) {
        relationSelectorJs.setIsReadyToFalse(gridIdx);
        js.selectGridRow(gridIdx, rowIndex);
        waitRelationSelector(gridIdx);
    }

    public void openRelationSelector(Long gridIdx) {
        waitRelationSelector(gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_MAIN_ELEMENT + gridIdx)).findElement(By.className(REL_SEL_MAIN_ELEMENT_CURRENT)).click();

        elementWait.waitElementByClassName(REL_SEL_CONTAINER);
        elementWait.waitElementVisibleByClassName(REL_SEL_CONTAINER);
        elementWait.waitElementDisplayByClassName(REL_SEL_CONTAINER);
    }

    public void closeRelationSelector(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_MAIN_ELEMENT + gridIdx)).findElement(By.className(REL_SEL_MAIN_ELEMENT_CURRENT)).click();

        elementWait.waitElementByClassName(REL_SEL_CONTAINER);
        elementWait.waitElementNotVisibleByClassName(REL_SEL_CONTAINER);
        elementWait.waitElementNotDisplayByClassName(REL_SEL_CONTAINER);
    }

    public void openRelationGrid(Long gridIdx) {
        waitRelationSelector(gridIdx);
        relationSelectorJs.setIsReadyToFalse(gridIdx);
        window.openModal(By.id(REL_SEL_BUTTON));
        grid2.waitLoad(gridIdx);
    }

    public void closeRelationGrid(Long gridIdx) {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + gridIdx));
        grid2.waitLoad(gridIdx);
        waitRelationSelector(gridIdx);
    }

    public void chooseParentChildTrackorType(Long gridIdx, String trackorType) {
        List<WebElement> rowNames = seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_MAIN_ELEMENT + gridIdx)).findElements(By.className(REL_SEL_CONTAINER_ITEM));
        for (WebElement rowName : rowNames) {
            String actualRowName = rowName.findElement(By.className(REL_SEL_CONTAINER_ITEM_TEXT)).getText();
            if (trackorType.equals(actualRowName)) {
                rowName.click();
            }
        }
    }

    public void checkCurrentValueInRelationSelector(Long gridIdx, String label, int count) {
        String actualLabel = seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_MAIN_ELEMENT + gridIdx)).findElement(By.className(REL_SEL_MAIN_ELEMENT_CURRENT_TEXT)).getText();
        String actualCount = seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_MAIN_ELEMENT + gridIdx)).findElement(By.className(REL_SEL_MAIN_ELEMENT_CURRENT_COUNT)).getText();
        Assert.assertEquals(actualLabel + actualCount, label + count);
    }

    public void checkCurrentValueInRelationSelector(Long gridIdx, String label) {
        String actualLabel = seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_MAIN_ELEMENT + gridIdx)).findElement(By.className(REL_SEL_MAIN_ELEMENT_CURRENT_TEXT)).getText();
        Assert.assertEquals(actualLabel, label);
    }

    private void waitRelationSelector(Long gridIdx) {
        relationSelectorWait.waitIsReadyRelationSelector(gridIdx);
    }

}
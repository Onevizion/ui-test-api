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

    private static final String REL_SEL_BUTTON = "btnParentsChildren";

    private static final String REL_SEL_CONTAINER = "new_rows_lbParentsChildren";
    private static final String REL_SEL_CONTAINER_ITEM = "newDropDownRowContainer";
    private static final String REL_SEL_CONTAINER_ITEM_TEXT = "newDropDownRow";
    private static final String REL_SEL_CONTAINER_ITEM_COUNT = "newDropDownCount";

    private static final String REL_SEL_MAIN_ELEMENT = "new_lbParentsChildren";
    private static final String REL_SEL_MAIN_ELEMENT_CURRENT = "newDropDown";
    private static final String REL_SEL_MAIN_ELEMENT_CURRENT_TEXT = "newDropDownLabel";

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
        List<WebElement> rowNames = seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_CONTAINER + gridIdx)).findElements(By.className(REL_SEL_CONTAINER_ITEM));
        Assert.assertEquals(rowNames.size(), count);
    }

    public void checkRelationSelectorValue(Long gridIdx, String trackorType, int count) {
        int cnt = 0;

        List<WebElement> rowNames = seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_CONTAINER + gridIdx)).findElements(By.className(REL_SEL_CONTAINER_ITEM));
        for (WebElement rowName : rowNames) {
            String actualRowName = rowName.findElement(By.className(REL_SEL_CONTAINER_ITEM_TEXT)).getText();

            if (actualRowName.equals("Relations:") || actualRowName.equals("-------------------------------------------")) {
                continue;
            }

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

    public void selectGridRow(Long gridIdx, Long rowIndex) {
        relationSelectorJs.setIsReadyToFalse(gridIdx);
        js.selectGridRow(gridIdx, rowIndex);
        waitRelationSelector(gridIdx);
    }

    public void openRelationSelector(Long gridIdx) {
        waitRelationSelector(gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_MAIN_ELEMENT + gridIdx)).findElement(By.className(REL_SEL_MAIN_ELEMENT_CURRENT)).click();

        elementWait.waitElementById(REL_SEL_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(REL_SEL_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(REL_SEL_CONTAINER + gridIdx);
    }

    public void closeRelationSelector(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_MAIN_ELEMENT + gridIdx)).findElement(By.className(REL_SEL_MAIN_ELEMENT_CURRENT)).click();

        elementWait.waitElementById(REL_SEL_CONTAINER + gridIdx);
        elementWait.waitElementNotVisibleById(REL_SEL_CONTAINER + gridIdx);
        elementWait.waitElementNotDisplayById(REL_SEL_CONTAINER + gridIdx);
    }

    public void openRelationGrid(Long gridIdx) {
        waitRelationSelector(gridIdx);
        relationSelectorJs.setIsReadyToFalse(gridIdx);
        window.openModal(By.id(REL_SEL_BUTTON + gridIdx));
        grid2.waitLoad(gridIdx);
    }

    public void closeRelationGrid(Long gridIdx) {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + gridIdx));
        grid2.waitLoad(gridIdx);
        waitRelationSelector(gridIdx);
    }

    public void chooseParentChildTrackorType(Long gridIdx, String trackorType) {
        List<WebElement> rowNames = seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_CONTAINER + gridIdx)).findElements(By.className(REL_SEL_CONTAINER_ITEM));
        for (WebElement rowName : rowNames) {
            String actualRowName = rowName.findElement(By.className(REL_SEL_CONTAINER_ITEM_TEXT)).getText();
            if (trackorType.equals(actualRowName)) {
                rowName.click();
            }
        }
    }

    public void checkCurrentValueInRelationSelector(Long gridIdx, String label, int count) {
        String actualLabel = seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_MAIN_ELEMENT + gridIdx)).findElement(By.className(REL_SEL_MAIN_ELEMENT_CURRENT_TEXT)).getText();
        Assert.assertEquals(actualLabel, label + count);
    }

    public void checkCurrentValueInRelationSelector(Long gridIdx, String label) {
        String actualLabel = seleniumSettings.getWebDriver().findElement(By.id(REL_SEL_MAIN_ELEMENT + gridIdx)).findElement(By.className(REL_SEL_MAIN_ELEMENT_CURRENT_TEXT)).getText();
        Assert.assertEquals(actualLabel, label);
    }

    private void waitRelationSelector(Long gridIdx) {
        relationSelectorWait.waitIsReadyRelationSelector(gridIdx);
        relationSelectorWait.waitIsReadyMutationObserver(gridIdx);
    }

}
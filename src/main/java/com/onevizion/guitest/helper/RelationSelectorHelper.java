package com.onevizion.guitest.helper;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.guitest.AbstractSeleniumCore;
import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.exception.SeleniumUnexpectedException;
import com.onevizion.guitest.helper.jquery.JqueryWaitHelper;

@Component
public class RelationSelectorHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    @Resource
    private JqueryWaitHelper jqueryWaitHelper;

    @Resource
    private WindowHelper windowHelper;

    public final static String RELATION_ID_BASE = "lbParentsChildren";
    public final static String BUTTON_RELATION_ID_BASE = "btnParentsChildren";

    public void checkRelationSelectorValuesCount(int count) {
        List<WebElement> rowNames = seleniumSettings.getWebDriver().findElement(By.id("new_rows_lbParentsChildren0")).findElements(By.className("newDropDownRowContainer"));
        Assert.assertEquals(rowNames.size(), count);
    }

    public void checkRelationSelectorValue(String trackorType, int count) {
        int cnt = 0;

        List<WebElement> rowNames = seleniumSettings.getWebDriver().findElement(By.id("new_rows_lbParentsChildren0")).findElements(By.className("newDropDownRowContainer"));
        for (WebElement rowName : rowNames) {
            String actualRowName = rowName.findElement(By.className("newDropDownRow")).getText();
            if (actualRowName.equals("Relations:") || actualRowName.equals("-------------------------------------------")) {
                continue;
            } else {
                String actualCount = rowName.findElement(By.className("newDropDownCount")).getText();
                int intActualCount = Integer.parseInt(actualCount);

                if (trackorType.equals(actualRowName) && count == intActualCount) {
                    cnt = cnt + 1;
                }
            }
        }

        if (cnt == 0) {
            throw new SeleniumUnexpectedException("Trackor Type not found in Parent/Child Drop Down");
        } else if (cnt > 1) {
            throw new SeleniumUnexpectedException("Duplicate values in Parent/Child Drop Down");
        }
    }

    public void selectGridRow(Long gridIdx, Long rowIndex) {
        jsHelper.selectGridRow(gridIdx, rowIndex);
        AbstractSeleniumCore.sleep(1000L);
        jqueryWaitHelper.waitJQueryLoad(); //wait reload relations
    }

    public void openRelationSelector(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id("new_lbParentsChildren" + gridIdx)).findElement(By.className("newDropDown")).click();

        elementWaitHelper.waitElementById("new_rows_lbParentsChildren" + gridIdx);
        elementWaitHelper.waitElementVisibleById("new_rows_lbParentsChildren" + gridIdx);
        elementWaitHelper.waitElementDisplayById("new_rows_lbParentsChildren" + gridIdx);
    }

    public void closeRelationSelector(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id("new_lbParentsChildren" + gridIdx)).findElement(By.className("newDropDown")).click();

        elementWaitHelper.waitElementById("new_rows_lbParentsChildren" + gridIdx);
        elementWaitHelper.waitElementNotVisibleById("new_rows_lbParentsChildren" + gridIdx);
        elementWaitHelper.waitElementNotDisplayById("new_rows_lbParentsChildren" + gridIdx);
    }

    public void openRelationGrid(Long gridIdx) {
        waitHelper.waitGridLoad(gridIdx, gridIdx);
        jqueryWaitHelper.waitJQueryLoad(); //wait reload relations
        windowHelper.openModal(By.id(BUTTON_RELATION_ID_BASE + gridIdx));
        waitHelper.waitGridLoad(gridIdx, gridIdx);
    }

    public void closeRelationGrid(Long gridIdx) {
        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + gridIdx));
        waitHelper.waitGridLoad(gridIdx, gridIdx);
        jqueryWaitHelper.waitJQueryLoad(); //wait reload relations
    }

    public void chooseParentChildTrackorType(String trackorType) {
        List<WebElement> rowNames = seleniumSettings.getWebDriver().findElement(By.id("new_rows_lbParentsChildren0")).findElements(By.className("newDropDownRowContainer"));
        for (WebElement rowName : rowNames) {
            String actualRowName = rowName.findElement(By.className("newDropDownRow")).getText();
            if (trackorType.equals(actualRowName)) {
                rowName.click();
            }
        }
    }

    public void checkCurrentValueInRelationSelector(String label, int count) {
        String actualLabel = seleniumSettings.getWebDriver().findElement(By.id("new_lbParentsChildren0")).findElement(By.className("newDropDownLabel")).getText();
        Assert.assertEquals(actualLabel, label + count);
    }

    public void checkCurrentValueInRelationSelector(String label) {
        String actualLabel = seleniumSettings.getWebDriver().findElement(By.id("new_lbParentsChildren0")).findElement(By.className("newDropDownLabel")).getText();
        Assert.assertEquals(actualLabel, label);
    }

}
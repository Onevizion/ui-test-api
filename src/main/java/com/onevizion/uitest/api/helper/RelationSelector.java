package com.onevizion.uitest.api.helper;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.jquery.JqueryWait;

@Component
public class RelationSelector {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Wait wait;

    @Resource
    private Js js;

    @Resource
    private ElementWait elementWait;

    @Resource
    private JqueryWait jqueryWait;

    @Resource
    private Window window;

    public static final String RELATION_ID_BASE = "lbParentsChildren";
    public static final String BUTTON_RELATION_ID_BASE = "btnParentsChildren";

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
            }

            String actualCount = rowName.findElement(By.className("newDropDownCount")).getText();
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
        js.selectGridRow(gridIdx, rowIndex);
        AbstractSeleniumCore.sleep(1000L);
        jqueryWait.waitJQueryLoad(); //wait reload relations
    }

    public void openRelationSelector(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id("new_lbParentsChildren" + gridIdx)).findElement(By.className("newDropDown")).click();

        elementWait.waitElementById("new_rows_lbParentsChildren" + gridIdx);
        elementWait.waitElementVisibleById("new_rows_lbParentsChildren" + gridIdx);
        elementWait.waitElementDisplayById("new_rows_lbParentsChildren" + gridIdx);
    }

    public void closeRelationSelector(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id("new_lbParentsChildren" + gridIdx)).findElement(By.className("newDropDown")).click();

        elementWait.waitElementById("new_rows_lbParentsChildren" + gridIdx);
        elementWait.waitElementNotVisibleById("new_rows_lbParentsChildren" + gridIdx);
        elementWait.waitElementNotDisplayById("new_rows_lbParentsChildren" + gridIdx);
    }

    public void openRelationGrid(Long gridIdx) {
        wait.waitGridLoad(gridIdx, gridIdx);
        jqueryWait.waitJQueryLoad(); //wait reload relations
        window.openModal(By.id(BUTTON_RELATION_ID_BASE + gridIdx));
        wait.waitGridLoad(gridIdx, gridIdx);
    }

    public void closeRelationGrid(Long gridIdx) {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + gridIdx));
        wait.waitGridLoad(gridIdx, gridIdx);
        jqueryWait.waitJQueryLoad(); //wait reload relations
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
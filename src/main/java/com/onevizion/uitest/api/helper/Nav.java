package com.onevizion.uitest.api.helper;

import java.security.SecureRandom;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class Nav {

    private static final String BUTTON_NEXT = "navNext";
    private static final String BUTTON_PREV = "navPrev";

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Js js;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private ElementWait elementWait;

    public int getAllRecordsCount(Long gridIdx) {
        grid2.waitLoad(gridIdx);
        String recordsLabel = seleniumSettings.getWebDriver().findElement(By.id("navTotal" + gridIdx)).getText();
        recordsLabel = recordsLabel.substring(recordsLabel.indexOf("of") + 2).trim();
        recordsLabel = recordsLabel.replace(",", "");
        return Integer.parseInt(recordsLabel);
    }

    private int getFirstRowNum(Long gridIdx) {
        grid2.waitLoad(gridIdx);
        String recordsLabel = seleniumSettings.getWebDriver().findElement(By.id("navRange" + gridIdx)).getText();
        recordsLabel = recordsLabel.substring(0, recordsLabel.indexOf("..")).trim();
        recordsLabel = recordsLabel.replace(",", "");
        return Integer.parseInt(recordsLabel);
    }

    private int getLastRowNum(Long gridIdx) {
        grid2.waitLoad(gridIdx);
        String recordsLabel = seleniumSettings.getWebDriver().findElement(By.id("navRange" + gridIdx)).getText();
        recordsLabel = recordsLabel.substring(recordsLabel.indexOf("..") + 2).trim();
        recordsLabel = recordsLabel.replace(",", "");
        return Integer.parseInt(recordsLabel);
    }

    public void checkNavigation(Long gridIdx) {
        grid2.waitLoad(gridIdx);

        int allRecordsCount = getAllRecordsCount(gridIdx);
        int actualVisibleRecordsCount = js.getGridRowsCount(gridIdx);

        Assert.assertEquals(allRecordsCount <= actualVisibleRecordsCount, true, "Visible records count more than all records count");

        int countPages = allRecordsCount / actualVisibleRecordsCount;

        Assert.assertEquals(getFirstRowNum(gridIdx), 1, "First num row in grid is wrong");
        Assert.assertEquals(getLastRowNum(gridIdx), actualVisibleRecordsCount, "Last num row in grid is wrong");
        checkCountRowsOnPage(gridIdx);

        for (int pageNum = 1; pageNum < countPages; pageNum++) {
            Assert.assertEquals(getFirstRowNum(gridIdx), (1 + (pageNum - 1) * actualVisibleRecordsCount), "First num row in grid is wrong");
            Assert.assertEquals(getLastRowNum(gridIdx), (pageNum * actualVisibleRecordsCount), "Last num row in grid is wrong");
            checkCountRowsOnPage(gridIdx);

            goToNextPage(gridIdx, pageNum + 1);
        }

        Assert.assertEquals(getFirstRowNum(gridIdx), (1 + (countPages - 1) * actualVisibleRecordsCount), "First num row in grid is wrong");
        Assert.assertEquals(getLastRowNum(gridIdx), allRecordsCount, "Last num row in grid is wrong");
        checkCountRowsOnPage(gridIdx);

        for (int pageNum = countPages; pageNum > 1; pageNum--) {
            goToPrevPage(gridIdx, pageNum - 1);

            Assert.assertEquals(getFirstRowNum(gridIdx), ((pageNum - 2) * actualVisibleRecordsCount + 1), "First num row in grid is wrong");
            Assert.assertEquals(getLastRowNum(gridIdx), ((pageNum - 1) * actualVisibleRecordsCount), "Last num row in grid is wrong");
            checkCountRowsOnPage(gridIdx);
        }

        Assert.assertEquals(getFirstRowNum(gridIdx), 1, "First num row in grid is wrong");
        Assert.assertEquals(getLastRowNum(gridIdx), actualVisibleRecordsCount, "Last num row in grid is wrong");
        checkCountRowsOnPage(gridIdx);
    }

    private void checkCountRowsOnPage(Long gridIdx) {
        int count = getLastRowNum(gridIdx) - getFirstRowNum(gridIdx) + 1;
        Assert.assertEquals(js.getGridRowsCount(gridIdx), count, "Actual records count not equals expected records count");
    }

    private void goToNextPage(Long gridIdx, int nextPageNum) {
        int randomIndex = new SecureRandom().nextInt(2);

        if (randomIndex == 0) {
            goToNextPageButton(gridIdx);
        } else if (randomIndex == 1) {
            goToNextPageList(gridIdx, nextPageNum);
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }
    }

    public void goToNextPageButton(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_NEXT + gridIdx)).click();
        grid2.waitLoad(gridIdx);
    }

    private void goToNextPageList(Long gridIdx, int nextPageNum) {
        List<WebElement> navRanges = seleniumSettings.getWebDriver().findElement(By.id("navSelect" + gridIdx)).findElement(By.className("scrollContent")).findElements(By.tagName("div"));

        seleniumSettings.getWebDriver().findElement(By.id("navPager" + gridIdx)).click();

        elementWait.waitElementById("navSelect" + gridIdx);
        elementWait.waitElementVisibleById("navSelect" + gridIdx);
        elementWait.waitElementDisplayById("navSelect" + gridIdx);

        js.scrollNewDropDownTop("navSelect" + gridIdx, "scrollContainer", (nextPageNum - 1) * 24L + ((nextPageNum - 1) / 10) * 14L);
        elementWait.waitElementVisible(navRanges.get(nextPageNum - 1));
        navRanges.get(nextPageNum - 1).click();
        grid2.waitLoad(gridIdx);
    }

    private void goToPrevPage(Long gridIdx, int prevPageNum) {
        int randomIndex = new SecureRandom().nextInt(2);

        if (randomIndex == 0) {
            goToPrevPageButton(gridIdx);
        } else if (randomIndex == 1) {
            goToPrevPageList(gridIdx, prevPageNum);
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }
    }

    public void goToPrevPageButton(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_PREV + gridIdx)).click();
        grid2.waitLoad(gridIdx);
    }

    private void goToPrevPageList(Long gridIdx, int prevPageNum) {
        List<WebElement> navRanges = seleniumSettings.getWebDriver().findElement(By.id("navSelect" + gridIdx)).findElement(By.className("scrollContent")).findElements(By.tagName("div"));

        seleniumSettings.getWebDriver().findElement(By.id("navPager" + gridIdx)).click();

        elementWait.waitElementById("navSelect" + gridIdx);
        elementWait.waitElementVisibleById("navSelect" + gridIdx);
        elementWait.waitElementDisplayById("navSelect" + gridIdx);

        js.scrollNewDropDownTop("navSelect" + gridIdx, "scrollContainer", (prevPageNum - 1) * 24L + ((prevPageNum - 1) / 10) * 14L);
        elementWait.waitElementVisible(navRanges.get(prevPageNum - 1));
        navRanges.get(prevPageNum - 1).click();
        grid2.waitLoad(gridIdx);
    }

}
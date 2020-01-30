package com.onevizion.uitest.api.helper;

import java.security.SecureRandom;
import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class Nav {

    private static final String BUTTON_NEXT = "navNext";
    private static final String BUTTON_PREV = "navPrev";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Js js;

    @Resource
    private Wait wait;

    @Resource
    private Grid2 grid2;

    @Resource
    private ElementWait elementWait;

    public Long getAllRecordsCount(Long gridIdx) {
        grid2.waitLoad(gridIdx);
        String recordsLabel = seleniumSettings.getWebDriver().findElement(By.id("navTotal" + gridIdx)).getText();
        recordsLabel = recordsLabel.substring(recordsLabel.indexOf("of") + 2).trim();
        recordsLabel = recordsLabel.replace(",", "");
        return Long.valueOf(recordsLabel);
    }

    private Long getFirstRowNum(Long gridIdx) {
        grid2.waitLoad(gridIdx);
        String recordsLabel = seleniumSettings.getWebDriver().findElement(By.id("navRange" + gridIdx)).getText();
        recordsLabel = recordsLabel.substring(0, recordsLabel.indexOf("..")).trim();
        recordsLabel = recordsLabel.replace(",", "");
        return Long.valueOf(recordsLabel);
    }

    private Long getLastRowNum(Long gridIdx) {
        grid2.waitLoad(gridIdx);
        String recordsLabel = seleniumSettings.getWebDriver().findElement(By.id("navRange" + gridIdx)).getText();
        recordsLabel = recordsLabel.substring(recordsLabel.indexOf("..") + 2).trim();
        recordsLabel = recordsLabel.replace(",", "");
        return Long.valueOf(recordsLabel);
    }

    public void checkNavigation(Long gridIdx) {
        grid2.waitLoad(gridIdx);

        Long allRecordsCount = getAllRecordsCount(gridIdx);
        Long actualVisibleRecordsCount = js.getGridRowsCount(gridIdx);

        Assert.assertEquals(actualVisibleRecordsCount.compareTo(allRecordsCount) <= 0, true, "Visible records count more than all records count");

        int countPages = (int) Math.ceil(allRecordsCount.doubleValue() / actualVisibleRecordsCount.doubleValue());

        Assert.assertEquals(getFirstRowNum(gridIdx), Long.valueOf(1L), "First num row in grid is wrong");
        Assert.assertEquals(getLastRowNum(gridIdx), actualVisibleRecordsCount, "Last num row in grid is wrong");
        checkCountRowsOnPage(gridIdx);

        for (int pageNum = 1; pageNum < countPages; pageNum++) {
            Assert.assertEquals(getFirstRowNum(gridIdx), Long.valueOf(1 + (pageNum - 1) * actualVisibleRecordsCount), "First num row in grid is wrong");
            Assert.assertEquals(getLastRowNum(gridIdx), Long.valueOf(pageNum * actualVisibleRecordsCount), "Last num row in grid is wrong");
            checkCountRowsOnPage(gridIdx);

            goToNextPage(gridIdx, pageNum + 1);
            grid2.waitLoad(gridIdx);
        }

        Assert.assertEquals(getFirstRowNum(gridIdx), Long.valueOf(1 + (countPages - 1) * actualVisibleRecordsCount), "First num row in grid is wrong");
        Assert.assertEquals(getLastRowNum(gridIdx), allRecordsCount, "Last num row in grid is wrong");
        checkCountRowsOnPage(gridIdx);

        for (int pageNum = countPages; pageNum > 1; pageNum--) {
            goToPrevPage(gridIdx, pageNum - 1);
            grid2.waitLoad(gridIdx);

            Assert.assertEquals(getFirstRowNum(gridIdx), Long.valueOf((pageNum - 2) * actualVisibleRecordsCount + 1), "First num row in grid is wrong");
            Assert.assertEquals(getLastRowNum(gridIdx), Long.valueOf((pageNum - 1) * actualVisibleRecordsCount), "Last num row in grid is wrong");
            checkCountRowsOnPage(gridIdx);
        }

        Assert.assertEquals(getFirstRowNum(gridIdx), Long.valueOf(1L), "First num row in grid is wrong");
        Assert.assertEquals(getLastRowNum(gridIdx), actualVisibleRecordsCount, "Last num row in grid is wrong");
        checkCountRowsOnPage(gridIdx);
    }

    private void checkCountRowsOnPage(Long gridIdx) {
        Long count = getLastRowNum(gridIdx) - getFirstRowNum(gridIdx) + 1;
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
        grid2.waitLoad();
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
        grid2.waitLoad();
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
    }

}
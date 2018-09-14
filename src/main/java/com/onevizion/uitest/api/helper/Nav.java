package com.onevizion.uitest.api.helper;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

@Component
public class Nav {

    private final static String BUTTON_NEXT = "navNext";
    private final static String BUTTON_PREV = "navPrev";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Js js;

    @Resource
    private Wait wait;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    public Long getAllRecordsCount(Long gridIdx) {
        wait.waitGridLoad(gridIdx, gridIdx);
        String recordsLabel = seleniumSettings.getWebDriver().findElement(By.id("navTotal" + gridIdx)).getText();
        recordsLabel = recordsLabel.substring(recordsLabel.indexOf("of") + 2).trim();
        recordsLabel = recordsLabel.replace(",", "");
        return Long.valueOf(recordsLabel);
    }

    private Long getFirstRowNum(Long gridIdx) {
        wait.waitGridLoad(gridIdx, gridIdx);
        String recordsLabel = seleniumSettings.getWebDriver().findElement(By.id("navRange" + gridIdx)).getText();
        recordsLabel = recordsLabel.substring(0, recordsLabel.indexOf("..")).trim();
        recordsLabel = recordsLabel.replace(",", "");
        return Long.valueOf(recordsLabel);
    }

    private Long getLastRowNum(Long gridIdx) {
        wait.waitGridLoad(gridIdx, gridIdx);
        String recordsLabel = seleniumSettings.getWebDriver().findElement(By.id("navRange" + gridIdx)).getText();
        recordsLabel = recordsLabel.substring(recordsLabel.indexOf("..") + 2).trim();
        recordsLabel = recordsLabel.replace(",", "");
        return Long.valueOf(recordsLabel);
    }

    public void checkNavigation(Long gridIdx) {
        wait.waitGridLoad(gridIdx, gridIdx);

        Long allRecordsCount = getAllRecordsCount(gridIdx);
        Long actualVisibleRecordsCount = js.getGridRowsCount(gridIdx);

        Assert.assertEquals(actualVisibleRecordsCount.compareTo(allRecordsCount) <= 0, true, "Visible records count more than all records count");

        int countPages = (int) Math.ceil(allRecordsCount.doubleValue() / actualVisibleRecordsCount.doubleValue());

        Assert.assertEquals(getFirstRowNum(gridIdx), new Long(1L), "First num row in grid is wrong");
        Assert.assertEquals(getLastRowNum(gridIdx), actualVisibleRecordsCount, "Last num row in grid is wrong");
        checkCountRowsOnPage(gridIdx);

        for (int pageNum = 1; pageNum < countPages; pageNum++) {
            Assert.assertEquals(getFirstRowNum(gridIdx), new Long(1 + (pageNum - 1) * actualVisibleRecordsCount), "First num row in grid is wrong");
            Assert.assertEquals(getLastRowNum(gridIdx), new Long(pageNum * actualVisibleRecordsCount), "Last num row in grid is wrong");
            checkCountRowsOnPage(gridIdx);

            goToNextPage(gridIdx, pageNum + 1);
            wait.waitGridLoad(gridIdx, gridIdx);
        }

        Assert.assertEquals(getFirstRowNum(gridIdx), new Long(1 + (countPages - 1) * actualVisibleRecordsCount), "First num row in grid is wrong");
        Assert.assertEquals(getLastRowNum(gridIdx), allRecordsCount, "Last num row in grid is wrong");
        checkCountRowsOnPage(gridIdx);

        for (int pageNum = countPages; pageNum > 1; pageNum--) {
            goToPrevPage(gridIdx, pageNum - 1);
            wait.waitGridLoad(gridIdx, gridIdx);

            Assert.assertEquals(getFirstRowNum(gridIdx), new Long((pageNum - 2) * actualVisibleRecordsCount + 1), "First num row in grid is wrong");
            Assert.assertEquals(getLastRowNum(gridIdx), new Long((pageNum - 1) * actualVisibleRecordsCount), "Last num row in grid is wrong");
            checkCountRowsOnPage(gridIdx);
        }

        Assert.assertEquals(getFirstRowNum(gridIdx), new Long(1L), "First num row in grid is wrong");
        Assert.assertEquals(getLastRowNum(gridIdx), actualVisibleRecordsCount, "Last num row in grid is wrong");
        checkCountRowsOnPage(gridIdx);
    }

    private void checkCountRowsOnPage(Long gridIdx) {
        Long count = getLastRowNum(gridIdx) - getFirstRowNum(gridIdx) + 1;
        Assert.assertEquals(js.getGridRowsCount(gridIdx), count, "Actual records count not equals expected records count");
    }

    private void goToNextPage(Long gridIdx, int nextPageNum) {
        int randomIndex = new Random().nextInt(2);

        if (randomIndex == 0) {
            goToNextPageButton(gridIdx);
        } else if (randomIndex == 1) {
            goToNextPageList(gridIdx, nextPageNum);
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }
    }

    private void goToNextPageButton(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_NEXT + gridIdx)).click();
    }

    private void goToNextPageList(Long gridIdx, int nextPageNum) {
        List<WebElement> navRanges = seleniumSettings.getWebDriver().findElement(By.id("navSelect" + gridIdx)).findElement(By.className("scrollContent")).findElements(By.tagName("div"));

        seleniumSettings.getWebDriver().findElement(By.id("navPager" + gridIdx)).click();

        elementWaitHelper.waitElementById("navSelect" + gridIdx);
        elementWaitHelper.waitElementVisibleById("navSelect" + gridIdx);
        elementWaitHelper.waitElementDisplayById("navSelect" + gridIdx);

        js.scrollNewDropDownTop("navSelect" + gridIdx, "scrollContainer", (nextPageNum - 1) * 24L + ((nextPageNum - 1) / 10) * 14L);
        elementWaitHelper.waitElementVisible(navRanges.get(nextPageNum - 1));
        navRanges.get(nextPageNum - 1).click();
    }

    private void goToPrevPage(Long gridIdx, int prevPageNum) {
        int randomIndex = new Random().nextInt(2);

        if (randomIndex == 0) {
            goToPrevPageButton(gridIdx);
        } else if (randomIndex == 1) {
            goToPrevPageList(gridIdx, prevPageNum);
        } else {
            throw new SeleniumUnexpectedException("Not support randomIndex. randomIndex=" + randomIndex);
        }
    }

    private void goToPrevPageButton(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_PREV + gridIdx)).click();
    }

    private void goToPrevPageList(Long gridIdx, int prevPageNum) {
        List<WebElement> navRanges = seleniumSettings.getWebDriver().findElement(By.id("navSelect" + gridIdx)).findElement(By.className("scrollContent")).findElements(By.tagName("div"));

        seleniumSettings.getWebDriver().findElement(By.id("navPager" + gridIdx)).click();

        elementWaitHelper.waitElementById("navSelect" + gridIdx);
        elementWaitHelper.waitElementVisibleById("navSelect" + gridIdx);
        elementWaitHelper.waitElementDisplayById("navSelect" + gridIdx);

        js.scrollNewDropDownTop("navSelect" + gridIdx, "scrollContainer", (prevPageNum - 1) * 24L + ((prevPageNum - 1) / 10) * 14L);
        elementWaitHelper.waitElementVisible(navRanges.get(prevPageNum - 1));
        navRanges.get(prevPageNum - 1).click();
    }

}
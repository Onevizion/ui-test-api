package com.onevizion.guitest.helper.tree;

import java.util.function.Supplier;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.AbstractSeleniumCore;
import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.helper.WaitHelper;

@Component
public class TreeWaitHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private TreeJsHelper treeJsHelper;

    @Resource
    private WaitHelper waitHelper;

    public void waitTreeLoad(final Long treeId) {
        waitTreeLoad(treeId.toString());
    }

    public void waitTreeLoad(String treeId) {
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.TREE_ID_BASE + treeId));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + treeId));

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for tree with id=[" + treeId + "] is failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return !seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + treeId)).isDisplayed();
                }
            });

        waitTreeArr();

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for PageLoaded with id=[" + treeId + "] is failed.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return treeJsHelper.isTreeLoaded(treeId).equals("1");
                }
            });
    }

    public void waitTreeLoadCnt(final int cnt) {
        Supplier<String> supplier = ()-> "Waiting for count items in tree id=[" + AbstractSeleniumCore.getTreeIdx() + "] expectedVal=[" + cnt + "] actualVal=[" + Long.valueOf(treeJsHelper.getTreeAllSubItems(AbstractSeleniumCore.getTreeIdx(), "-1").split(",").length) + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(supplier)
            .ignoring(StaleElementReferenceException.class)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return Long.valueOf(treeJsHelper.getTreeAllSubItems(AbstractSeleniumCore.getTreeIdx(), "-1").split(",").length) == cnt;
                }
            });
    }

    private void waitTreeArr() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for treeArr is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return treeJsHelper.isTreeArrExist();
                }
            });
    }

}
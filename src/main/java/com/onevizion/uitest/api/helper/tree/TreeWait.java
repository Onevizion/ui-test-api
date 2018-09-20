package com.onevizion.uitest.api.helper.tree;

import java.util.function.Supplier;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Wait;

@Component
class TreeWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private TreeJs treeJs;

    @Resource
    private Wait wait;

    void waitTreeLoad(Long treeId) {
        waitTreeLoad(treeId.toString());
    }

    void waitTreeLoad(String treeId) {
        wait.waitWebElement(By.id(AbstractSeleniumCore.TREE_ID_BASE + treeId));
        wait.waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + treeId));

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
                    return treeJs.isTreeLoaded(treeId).equals("1");
                }
            });
    }

    void waitTreeLoadCnt(int cnt) {
        Supplier<String> supplier = ()-> "Waiting for count items in tree id=[" + AbstractSeleniumCore.getTreeIdx() + "] expectedVal=[" + cnt + "] actualVal=[" + Long.valueOf(treeJs.getTreeAllSubItems(AbstractSeleniumCore.getTreeIdx(), "-1").split(",").length) + "] is failed";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(supplier)
            .ignoring(StaleElementReferenceException.class)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return Long.valueOf(treeJs.getTreeAllSubItems(AbstractSeleniumCore.getTreeIdx(), "-1").split(",").length) == cnt;
                }
            });
    }

    private void waitTreeArr() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for treeArr is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return treeJs.isTreeArrExist();
                }
            });
    }

}
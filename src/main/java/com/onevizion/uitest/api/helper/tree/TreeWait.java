package com.onevizion.uitest.api.helper.tree;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
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

    void waitLoad(Long treeId) {
        waitLoad(treeId.toString());
    }

    void waitLoad(String treeId) {
        wait.waitWebElement(By.id(AbstractSeleniumCore.TREE_ID_BASE + treeId));
        wait.waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + treeId));

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for tree with id=[" + treeId + "] is failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(webdriver -> !webdriver.findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + treeId)).isDisplayed());

        waitTreeArr();

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for PageLoaded with id=[" + treeId + "] is failed.")
            .until(webdriver -> treeJs.isLoaded(treeId).equals("1"));
    }

    private void waitTreeArr() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for treeArr is failed")
            .until(webdriver -> treeJs.isTreeArrExist());
    }

}
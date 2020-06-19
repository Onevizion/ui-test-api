package com.onevizion.uitest.api.helper.tree;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Wait;

@Component
class TreeWait {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private TreeJs treeJs;

    @Autowired
    private Wait wait;

    @Autowired
    private ElementWait elementWait;

    void waitLoad(Long treeId) {
        wait.waitWebElement(By.id(AbstractSeleniumCore.TREE_ID_BASE + treeId));

        elementWait.waitElementNotVisibleById(AbstractSeleniumCore.LOADING_ID_BASE + treeId);
        elementWait.waitElementNotDisplayById(AbstractSeleniumCore.LOADING_ID_BASE + treeId);

        waitTreeArr();

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for PageLoaded with id=[" + treeId + "] is failed.")
            .until(webdriver -> treeJs.isLoaded(treeId.toString()).equals("1"));
    }

    void waitLoad(String treeId) {
        wait.waitWebElement(By.id(AbstractSeleniumCore.TREE_ID_BASE + treeId));

        elementWait.waitElementNotVisibleById(AbstractSeleniumCore.LOADER_ID_BASE + treeId);
        elementWait.waitElementNotDisplayById(AbstractSeleniumCore.LOADER_ID_BASE + treeId);

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
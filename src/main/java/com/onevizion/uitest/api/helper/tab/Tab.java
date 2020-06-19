package com.onevizion.uitest.api.helper.tab;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Js;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class Tab {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Js js;

    @Autowired
    private TabJs tabJs;

    public void goToTab(String tabLabel) {
        Long tabIndex = getTabIndex(tabLabel);
        goToTab(tabIndex);
    }

    public void goToTab(Long tabIndex) {
        js.scrollNewDropDownTop("newFormMenu", "scrollContainer", tabIndex * 28L);

        List<WebElement> tabs = seleniumSettings.getWebDriver().findElement(By.id("formMenuTree")).findElement(By.className("scrollContent")).findElements(By.className("newGuiMenuRowContainer"));
        WebElement tab = tabs.get(tabIndex.intValue() - 1);
        tab.click();
    }

    public String getTabLabel(Long tabIndex) {
        String tabPrefix = "";
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int tabPrefixCount = seleniumSettings.getWebDriver().findElements(By.id("tabPrefix" + tabIndex.intValue())).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        if (tabPrefixCount > 0) {
            tabPrefix = seleniumSettings.getWebDriver().findElement(By.id("tabPrefix" + tabIndex.intValue())).getAttribute("textContent");
        }

        String tabLabel = seleniumSettings.getWebDriver().findElement(By.id("tabLabel" + tabIndex.intValue())).getAttribute("textContent");

        String tabRows = "";
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int tabRowsCount = seleniumSettings.getWebDriver().findElements(By.id("tabRows" + tabIndex.intValue())).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        if (tabRowsCount > 0) {
            tabRows = " (" + seleniumSettings.getWebDriver().findElement(By.id("tabRows" + tabIndex.intValue())).getAttribute("textContent") + ")";
        }

        return tabPrefix + tabLabel + tabRows;
    }

    public Long getTabIndex(String tabLabel) {
        Long tabIndex = null;
        for (int i = 1; i <= getTabsCnt(); i++) {
            if (tabLabel.equals(getTabLabel((long) i))) {
                if (tabIndex != null) {
                    throw new SeleniumUnexpectedException("Tab with text[" + tabLabel + "] found many times");
                }
                tabIndex = (long) i;
            }
        }

        if (tabIndex == null) {
            throw new SeleniumUnexpectedException("Tab with text[" + tabLabel + "] not found");
        }

        return tabIndex;
    }

    public void hideTabMenu() {
        if (!tabJs.isTabMenuHidden()) {
            seleniumSettings.getWebDriver().findElement(By.className("showMenuBtn")).click();
        }
    }

    public void showTabMenu() {
        if (tabJs.isTabMenuHidden()) {
            seleniumSettings.getWebDriver().findElement(By.className("showMenuBtn")).click();
        }
    }

    public int getTabsCnt() {
        List<WebElement> tabs = seleniumSettings.getWebDriver().findElement(By.id("formMenuTree")).findElement(By.className("scrollContent")).findElements(By.className("newGuiMenuRowContainer"));
        return tabs.size();
    }

}
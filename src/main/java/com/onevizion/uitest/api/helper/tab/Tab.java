package com.onevizion.uitest.api.helper.tab;

import java.util.List;

import javax.annotation.Resource;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Js;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class Tab {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Js js;

    @Resource
    private TabJs tabJs;

    public void goToTab(String tabLabel) {
        Long tabIndex = getTabIndex(tabLabel);
        goToTab(tabIndex);
    }

    public void goToTab(Long tabIndex) {
        js.scrollNewDropDownTop("newFormMenu", "scrollContainer", tabIndex * 28L);

        List<WebElement> tabs = seleniumSettings.getWebDriver().findElement(By.id("formMenuTree")).findElement(By.className("scrollContent")).findElements(By.className("newGuiMenuRowContainer"));
        WebElement tab = tabs.get(tabIndex.intValue() - 1);
        WebElement tabLabel = tab.findElement(By.className("menuItemWidthWrapper"));
        tabLabel.click();
    }

    public String getTabLabel(Long tabIndex) {
        return seleniumSettings.getWebDriver().findElement(By.name("tabLbl" + tabIndex.intValue())).getAttribute("textContent");
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
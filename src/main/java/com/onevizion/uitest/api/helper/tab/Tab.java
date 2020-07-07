package com.onevizion.uitest.api.helper.tab;

import java.util.List;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class Tab {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private TabJs tabJs;

    @Autowired
    private TabWait tabWait;

    @Autowired
    private ListboxTab listboxTab;

    public void goToTab(String label) {
        List<ListboxElementTab> tabs = listboxTab.getTabs();
        ListboxElementTab tab = listboxTab.getTabByLabel(tabs, label);
        listboxTab.selectTab(tab);
        tabWait.waitLoad(tabs.indexOf(tab) + 1);
    }

    public void goToTab(int position) {
        List<ListboxElementTab> tabs = listboxTab.getTabs();
        ListboxElementTab tab = listboxTab.getTabByPosition(tabs, position);
        listboxTab.selectTab(tab);
        tabWait.waitLoad(position);
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

    public void waitLoad(int position) {
        tabWait.waitLoad(position);
    }

}
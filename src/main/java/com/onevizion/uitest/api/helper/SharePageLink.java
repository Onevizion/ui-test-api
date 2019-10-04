package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class SharePageLink {

    private static final String ID_FAVORITES = "topPanelBtnFavorites";
    private static final String ID_FAVORITESMENU = "favoritesPopupMenu";
    private static final String ID_FAVORITESMENU_SHAREPAGELINK = "btnSharePageLink";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementWait elementWait;

    @Resource
    private Element element;

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    public void openSharePageLinkForm() {
        elementWait.waitElementById(ID_FAVORITES);
        elementWait.waitElementVisibleById(ID_FAVORITES);
        elementWait.waitElementDisplayById(ID_FAVORITES);

        element.clickById(ID_FAVORITES);

        elementWait.waitElementById(ID_FAVORITESMENU);
        elementWait.waitElementVisibleById(ID_FAVORITESMENU);
        elementWait.waitElementDisplayById(ID_FAVORITESMENU);

        elementWait.waitElementById(ID_FAVORITESMENU_SHAREPAGELINK);
        elementWait.waitElementVisibleById(ID_FAVORITESMENU_SHAREPAGELINK);
        elementWait.waitElementDisplayById(ID_FAVORITESMENU_SHAREPAGELINK);

        window.openModal(By.id(ID_FAVORITESMENU_SHAREPAGELINK));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void closeSharePageLinkFormOk() {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    public void closeSharePageLinkFormCancel() {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}
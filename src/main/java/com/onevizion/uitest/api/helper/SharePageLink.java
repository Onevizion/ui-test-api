package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class SharePageLink {

    private static final String ID_HELP = "topPanelBtnHelp";
    private static final String ID_HELPMENU = "helpPopupMenu";
    private static final String ID_HELPMENU_SHAREPAGELINK = "itemSharePageLink";

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
        elementWait.waitElementById(ID_HELP);
        elementWait.waitElementVisibleById(ID_HELP);
        elementWait.waitElementDisplayById(ID_HELP);

        element.clickById(ID_HELP);

        elementWait.waitElementById(ID_HELPMENU);
        elementWait.waitElementVisibleById(ID_HELPMENU);
        elementWait.waitElementDisplayById(ID_HELPMENU);

        elementWait.waitElementById(ID_HELPMENU_SHAREPAGELINK);
        elementWait.waitElementVisibleById(ID_HELPMENU_SHAREPAGELINK);
        elementWait.waitElementDisplayById(ID_HELPMENU_SHAREPAGELINK);

        window.openModal(By.id(ID_HELPMENU_SHAREPAGELINK));
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
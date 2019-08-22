package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class SharePageLink {

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
        elementWait.waitElementById("topPanelUserNameBtn");
        elementWait.waitElementVisibleById("topPanelUserNameBtn");
        elementWait.waitElementDisplayById("topPanelUserNameBtn");

        element.clickById("topPanelUserNameBtn");

        elementWait.waitElementById("userNameMenu");
        elementWait.waitElementVisibleById("userNameMenu");
        elementWait.waitElementDisplayById("userNameMenu");

        elementWait.waitElementById("userNameMenuItemfavorites");
        elementWait.waitElementVisibleById("userNameMenuItemfavorites");
        elementWait.waitElementDisplayById("userNameMenuItemfavorites");

        element.moveToElementById("userNameMenuItemfavorites");

        elementWait.waitElementById("userNameMenufavorites");
        elementWait.waitElementVisibleById("userNameMenufavorites");
        elementWait.waitElementDisplayById("userNameMenufavorites");

        elementWait.waitElementById("userNameMenuItemfav3");
        elementWait.waitElementVisibleById("userNameMenuItemfav3");
        elementWait.waitElementDisplayById("userNameMenuItemfav3");

        element.moveToElementById("userNameMenuItemfav1");

        element.moveToElementById("userNameMenuItemfav3");

        window.openModal(By.id("userNameMenuItemfav3"));
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
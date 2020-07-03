package com.onevizion.uitest.api.helper;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;

@Component
public class SharePageLink {

    private static final String ID_SHARE = "shareButton";

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    public void openSharePageLinkForm() {
        window.openModal(By.id(ID_SHARE));
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
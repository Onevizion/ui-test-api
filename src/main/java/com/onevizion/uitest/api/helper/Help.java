package com.onevizion.uitest.api.helper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

@Component
public class Help {

    private static final String BUTTON_OPEN = "btnHelp";
    private static final String BUTTON_OPEN_ON_FILTER_FORM = "btnHelpFilter";
    private static final String BUTTON_OPEN_ON_VIEW_FORM = "btnHelpView";
    private static final String SERIAL_NUMBER_ID = "SerialNumber";
    private static final String NAME = "BPD Help Editor for current Tab";

    private static final String INNER_TEXT = "innerText";

    private static final String HELP_NAME = "name";
    private static final String HELP_FORM_NUMBER = "formNumber";
    private static final String HELP_TYPE = "itemTypeId";

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private Element element;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private SeleniumSettings seleniumSettings;

    public void checkHelp(final String name, final String number, final String type) {
        elementWait.waitElementAttributeById(SERIAL_NUMBER_ID, INNER_TEXT, number);
        assertElement.assertLinkById(SERIAL_NUMBER_ID, number);

        element.clickById(BUTTON_OPEN);

        window.openModal(getHelpContainerItem(NAME));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        assertElement.assertText(HELP_NAME, name);
        assertElement.assertText(HELP_FORM_NUMBER, number);
        assertElement.assertSelect(HELP_TYPE, type);
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void checkHelpOnFilterForm(Long gridIdx, final String name, final String number, final String type) {
        elementWait.waitElementAttributeById(SERIAL_NUMBER_ID, INNER_TEXT, number);
        assertElement.assertLinkById(SERIAL_NUMBER_ID, number);

        element.clickById(BUTTON_OPEN_ON_FILTER_FORM + gridIdx);

        window.openModal(getHelpContainerItem(NAME));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        assertElement.assertText(HELP_NAME, name);
        assertElement.assertText(HELP_FORM_NUMBER, number);
        assertElement.assertSelect(HELP_TYPE, type);
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void checkHelpOnViewForm(Long gridIdx, final String name, final String number, final String type) {
        elementWait.waitElementAttributeById(SERIAL_NUMBER_ID, INNER_TEXT, number);
        assertElement.assertLinkById(SERIAL_NUMBER_ID, number);

        element.clickById(BUTTON_OPEN_ON_VIEW_FORM + gridIdx);

        window.openModal(getHelpContainerItem(NAME));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        assertElement.assertText(HELP_NAME, name);
        assertElement.assertText(HELP_FORM_NUMBER, number);
        assertElement.assertSelect(HELP_TYPE, type);
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    private WebElement getHelpContainerItem(String helpName) {
        List<WebElement> helpContainers = seleniumSettings.getWebDriver().findElements(By.className("dhtmlxMebu_SubLevelArea_Tbl"));
        for (WebElement helpContainer : helpContainers) {
            List<WebElement> helpItems = helpContainer.findElements(By.tagName("div"));
            for (WebElement helpItem : helpItems) {
                if (helpName.equals(helpItem.getText())) {
                    return helpItem;
                }
            }
        }

        throw new SeleniumUnexpectedException("");
    }

}
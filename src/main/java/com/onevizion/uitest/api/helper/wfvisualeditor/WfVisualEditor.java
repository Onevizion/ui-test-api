package com.onevizion.uitest.api.helper.wfvisualeditor;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;

@Component
public class WfVisualEditor {

    @Resource
    private Element element;

    @Resource
    private Wait wait;

    @Resource
    private Window window;

    @Resource
    private SeleniumSettings seleniumSettings;

    private WebElement getStepNode(String text) {
        WebElement result = null;

        List<WebElement> stepNodes = seleniumSettings.getWebDriver().findElements(By.className("node"));
        for (WebElement stepNode : stepNodes) {
            String innerText = stepNode.findElement(By.id("lbl" + stepNode.getAttribute("id"))).getAttribute("innerText");
            if (text.equals(innerText)) {
                if (result != null) {
                    throw new SeleniumUnexpectedException("Step [" + text + "] found many times");
                }
                result = stepNode;
            }
        }

        if (result == null) {
            throw new SeleniumUnexpectedException("Step [" + text + "] not found");
        }

        return result;
    }

    public void selectStepNode(String text) {
        WebElement stepNode = getStepNode(text);
        WebElement stepNodeLabel = stepNode.findElement(By.id("lbl" + stepNode.getAttribute("id")));
        element.click(stepNodeLabel);
    }

    public void openAddFormStepBefore() {
        window.openModal(By.id("btnAddStepBefore"));
        wait.waitFormLoad();
    }

    public void openAddFormStepAfter() {
        window.openModal(By.id("btnAddStep"));
        wait.waitFormLoad();
    }

    public void openStepEditForm(String text) {
        selectStepNode(text);
        window.openModal(By.id("btnEdit"));
        wait.waitFormLoad();
    }

    public void openStepNotificationsGrid(String text) {
        selectStepNode(text);
        window.openModal(By.id("btnNotifications"));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void openStepLinksGrid(String text) {
        selectStepNode(text);
        window.openModal(By.id("btnLinks"));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void deleteStep(String text) {
        selectStepNode(text);
        element.clickById("btnDelete");
        wait.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();
        wait.waitFormLoad();
    }

}
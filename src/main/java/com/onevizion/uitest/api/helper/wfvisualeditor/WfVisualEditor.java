package com.onevizion.uitest.api.helper.wfvisualeditor;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Alert;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class WfVisualEditor {

    @Autowired
    private Element element;

    @Autowired
    private Wait wait;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private Window window;

    @Autowired
    private Alert alert;

    @Autowired
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
        grid2.waitLoad();
    }

    public void openStepLinksGrid(String text) {
        selectStepNode(text);
        window.openModal(By.id("btnLinks"));
        grid2.waitLoad();
    }

    public void deleteStep(String text) {
        selectStepNode(text);
        element.clickById("btnDelete");
        alert.accept();
        wait.waitSavingLoad();
    }

}
package com.onevizion.uitest.api.helper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.dashboard.Dashboard;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.helper.tree.Tree;

@Component
public class NewNewDropDown {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private Wait wait;

    @Autowired
    private Window window;

    @Autowired
    private Jquery jquery;

    @Autowired
    private Tree tree;

    @Autowired
    private Dashboard dashboard;

    @Autowired
    private Element element;

    public void selectDashboard(String name) {
        selectEntity("dropDownDashboards", name);

        dashboard.waitDashboardLoad();
    }

    public void selectPortal(String name) {
        selectEntity("dropDownPortals", name);

        //TODO move waiting from different places to this place
    }

    public void selectMenu(String name) {
        selectEntity("dropDownMenu", name);

        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad();
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad();
    }

    private void selectEntity(String dropDownId, String name) {
        open(dropDownId);
        searchEntity(dropDownId, name);
        WebElement entity = getEntity(dropDownId, name);
        entity.click();
    }

    public void openAddDashboardForm() {
        openAddEntityForm("dropDownDashboards", "Dashboard");

        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void openAddMenuForm() {
        openAddEntityForm("dropDownMenu", "MenuApplication");

        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    private void openAddEntityForm(String dropDownId, String addButtonId) {
        open(dropDownId);
        window.openModal(By.id("add" + addButtonId));
    }

    public void openEditMenuForm(String name) {
        openEditEntityForm("dropDownMenu", "MenuApplication", name);

        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        jquery.waitLoad();
    }

    private void openEditEntityForm(String dropDownId, String editButtonId, String name) {
        openEntityForm(dropDownId, "edit" + editButtonId, name);
    }

    public void openCloneMenuForm(String name) {
        openCloneEntityForm("dropDownMenu", "MenuApplication", name);

        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        jquery.waitLoad();
    }

    private void openCloneEntityForm(String dropDownId, String cloneButtonId, String name) {
        openEntityForm(dropDownId, "clone" + cloneButtonId, name);
    }

    private void openEntityForm(String dropDownId, String buttonId, String name) {
        open(dropDownId);
        searchEntity(dropDownId, name);
        WebElement entity = getEntity(dropDownId, name);
        element.moveToElement(entity);

        WebElement entityOptions = entity.findElement(By.className("ddi_menu_button"));
        elementWait.waitElementVisible(entityOptions);
        entityOptions.click();

        elementWait.waitElementVisibleById(buttonId);
        window.openModal(By.id(buttonId));
    }

    public void deleteMenu(String name) {
        deleteEntity("dropDownMenu", "MenuApplication", name);

        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad();
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad();
    }

    private void deleteEntity(String dropDownId, String deleteButtonId, String name) {
        open(dropDownId);
        searchEntity(dropDownId, name);
        WebElement entity = getEntity(dropDownId, name);
        element.moveToElement(entity);

        WebElement entityOptions = entity.findElement(By.className("ddi_menu_button"));
        elementWait.waitElementVisible(entityOptions);
        entityOptions.click();

        seleniumSettings.getWebDriver().findElement(By.id("delete" + deleteButtonId)).click();
        wait.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();
    }

    private void open(String dropDownId) {
        seleniumSettings.getWebDriver().findElement(By.id(dropDownId)).findElement(By.className("dds_label")).click();
        //elementWait.waitElementById("dd_content_" + dropDownId); //TODO
        elementWait.waitElementVisible(seleniumSettings.getWebDriver().findElement(By.id(dropDownId)).findElement(By.className("dd_content")));
        elementWait.waitElementDisplay(seleniumSettings.getWebDriver().findElement(By.id(dropDownId)).findElement(By.className("dd_content")));
    }

    private void searchEntity(String dropDownId, String name) {
        WebElement searchField = seleniumSettings.getWebDriver().findElement(By.id("search_" + dropDownId));

        elementWait.waitElementVisible(searchField);
        element.moveToElement(searchField);
        searchField.clear();
        searchField.sendKeys(name);
    }

    private WebElement getEntity(String dropDownId, String name) {
        WebElement entity = null;

        List<WebElement> items = seleniumSettings.getWebDriver().findElement(By.id(dropDownId)).findElements(By.className("drop_down_item"));
        for (WebElement item : items) {
            if (name.equals(item.findElement(By.className("ddi_label")).getAttribute("textContent"))) {
                if (entity != null) {
                    throw new SeleniumUnexpectedException("DropDown [" + name + "] found many times");
                }
                entity = item;
            }
        }

        if (entity == null) {
            throw new SeleniumUnexpectedException("DropDown item [" + name + "] not found");
        }

        return entity;
    }

}
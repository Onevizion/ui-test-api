package com.onevizion.uitest.api.helper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

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
    private Element element;

    public void selectDashboard(String name) {
        select("dropDownDashboards", name);
    }

    public void selectPortal(String name) {
        select("dropDownPortals", name);
    }

    public void selectMenu(String name) {
        select("dropDownMenu", name);
    }

    private void select(String dropDownId, String name) {
        open(dropDownId);
        searchEntity(dropDownId, name);
        WebElement entity = getEntity(dropDownId, name);
        element.click(entity);
    }

    public void openFormAddDashboard() {
        openFormAdd("dropDownDashboards", "Dashboard");
    }

    public void openFormAddMenu() {
        openFormAdd("dropDownMenu", "MenuApplication");
    }

    private void openFormAdd(String dropDownId, String addButtonId) {
        open(dropDownId);
        window.openModal(By.id("add" + addButtonId));
    }

    public void openFormEditMenu(String name) {
        openFormEdit("dropDownMenu", "MenuApplication", name);
    }

    private void openFormEdit(String dropDownId, String editButtonId, String name) {
        openForm(dropDownId, "edit" + editButtonId, name);
    }

    public void openFormCloneMenu(String name) {
        openFormClone("dropDownMenu", "MenuApplication", name);
    }

    private void openFormClone(String dropDownId, String cloneButtonId, String name) {
        openForm(dropDownId, "clone" + cloneButtonId, name);
    }

    private void openForm(String dropDownId, String buttonId, String name) {
        open(dropDownId);
        searchEntity(dropDownId, name);
        WebElement entity = getEntity(dropDownId, name);
        element.moveToElement(entity);

        WebElement entityOptions = entity.findElement(By.className("ddi_menu_button"));
        elementWait.waitElementVisible(entityOptions);
        element.click(entityOptions);

        elementWait.waitElementVisibleById(buttonId);
        window.openModal(By.id(buttonId));
    }

    public void deleteMenu(String name) {
        delete("dropDownMenu", "MenuApplication", name);
    }

    private void delete(String dropDownId, String deleteButtonId, String name) {
        open(dropDownId);
        searchEntity(dropDownId, name);
        WebElement entity = getEntity(dropDownId, name);
        element.moveToElement(entity);

        WebElement entityOptions = entity.findElement(By.className("ddi_menu_button"));
        elementWait.waitElementVisible(entityOptions);
        element.click(entityOptions);

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
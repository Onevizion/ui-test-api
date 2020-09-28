package com.onevizion.uitest.api.helper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

    public void selectDashboard(String name) {
        selectEntity("dropDownDashboards", name);

        dashboard.waitDashboardLoad();
    }

    public void selectPortal(String name) {
        selectEntity("dropDownPortals", name);
    }

    public void selectMenu(String name) {
        selectEntity("dropDownMenu", name);

        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad(); //wait load new application
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad(); //wait load new application
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

    public void openEditMenuForm(String name) {
        openEditEntityForm("dropDownMenu", "MenuApplication", name);

        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        jquery.waitLoad();
    }

    public void openCloneMenuForm(String name) {
        openCloneEntityForm("dropDownMenu", "MenuApplication", name);

        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        jquery.waitLoad();
    }

    public void deleteMenu(String name) {
        deleteEntity("dropDownMenu", "MenuApplication", name);

        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad();
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad();
    }

    private void openEditEntityForm(String id, String buttonId, String name) {
        openEntityForm(id, "edit" + buttonId, name);
    }

    private void openCloneEntityForm(String id, String buttonId, String name) {
        openEntityForm(id, "clone" + buttonId, name);
    }

    private void openAddEntityForm(String id, String buttonId) {
        boolean failOpenWindow = true;
        int failOpenWindowCnt = 1;
        do {
            try {
                openDropDown(id);
                window.openModal(By.id("add" + buttonId));

                failOpenWindow = false;
            } catch (ElementNotVisibleException | StaleElementReferenceException | IndexOutOfBoundsException | TimeoutException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id(id)).findElement(By.className("dd_content")).isDisplayed()) {
                    openDropDown(id);
                }
            }
        } while (failOpenWindow && failOpenWindowCnt <= 10);

        if (failOpenWindow) {
            throw new SeleniumUnexpectedException("");
        }
    }

    private void openEntityForm(String id, String buttonId, String name) {
        boolean failOpenWindow = true;
        int failOpenWindowCnt = 1;
        do {
            try {
                openDropDown(id);

                WebElement dropDownItem = getDropDownItem(id, name);

                Actions act = new Actions(seleniumSettings.getWebDriver());
                act.moveToElement(dropDownItem).perform();

                elementWait.waitElementVisible(dropDownItem.findElement(By.className("ddi_menu_button")));
                dropDownItem.findElement(By.className("ddi_menu_button")).click();

                elementWait.waitElementVisibleById(buttonId);
                window.openModal(By.id(buttonId));

                failOpenWindow = false;
            } catch (ElementNotVisibleException | StaleElementReferenceException | IndexOutOfBoundsException | TimeoutException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id(id)).findElement(By.className("dd_content")).isDisplayed()) {
                    openDropDown(id);
                }
            }
        } while (failOpenWindow && failOpenWindowCnt <= 10);

        if (failOpenWindow) {
            throw new SeleniumUnexpectedException("");
        }
    }

    private void selectEntity(String id, String name) {
        WebElement currentEntity = seleniumSettings.getWebDriver().findElement(By.id(id)).findElement(By.className("dds_label"));
        String currentEntityName = currentEntity.getText();
        if (name.equals(currentEntityName)) {
            return;
        }

        boolean failOpenWindow = true;
        int failOpenWindowCnt = 1;
        do {
            try {
                openDropDown(id);

                WebElement dropDownItem = getDropDownItem(id, name);
                dropDownItem.click();

                failOpenWindow = false;
            } catch (ElementNotVisibleException | StaleElementReferenceException | IndexOutOfBoundsException | TimeoutException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id(id)).findElement(By.className("dd_content")).isDisplayed()) {
                    openDropDown(id);
                }
            }
        } while (failOpenWindow && failOpenWindowCnt <= 10);

        if (failOpenWindow) {
            throw new SeleniumUnexpectedException("");
        }
    }

    private void deleteEntity(String id, String buttonId, String name) {
        boolean failOpenWindow = true;
        int failOpenWindowCnt = 1;
        do {
            try {
                openDropDown(id);

                WebElement dropDownItem = getDropDownItem(id, name);

                Actions act = new Actions(seleniumSettings.getWebDriver());
                act.moveToElement(dropDownItem).perform();

                elementWait.waitElementVisible(dropDownItem.findElement(By.className("ddi_menu_button")));
                dropDownItem.findElement(By.className("ddi_menu_button")).click();

                seleniumSettings.getWebDriver().findElement(By.id("delete" + buttonId)).click();
                wait.waitAlert();
                seleniumSettings.getWebDriver().switchTo().alert().accept();

                failOpenWindow = false;
            } catch (ElementNotVisibleException | StaleElementReferenceException | IndexOutOfBoundsException | TimeoutException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id(id)).findElement(By.className("dd_content")).isDisplayed()) {
                    openDropDown(id);
                }
            }
        } while (failOpenWindow && failOpenWindowCnt <= 10);

        if (failOpenWindow) {
            throw new SeleniumUnexpectedException("");
        }
    }

    private void openDropDown(String id) {
        seleniumSettings.getWebDriver().findElement(By.id(id)).findElement(By.className("dds_label")).click();
        //elementWait.waitElementById("dd_content_" + id);//TODO
        elementWait.waitElementVisible(seleniumSettings.getWebDriver().findElement(By.id(id)).findElement(By.className("dd_content")));
        elementWait.waitElementDisplay(seleniumSettings.getWebDriver().findElement(By.id(id)).findElement(By.className("dd_content")));
    }

    private WebElement getDropDownItem(String dropDownId, String dropDownItemName) {
        seleniumSettings.getWebDriver().findElement(By.id("search_" + dropDownId)).clear();
        seleniumSettings.getWebDriver().findElement(By.id("search_" + dropDownId)).sendKeys(dropDownItemName);

        WebElement dropDownItem = null;
        List<WebElement> items = seleniumSettings.getWebDriver().findElement(By.id(dropDownId)).findElements(By.className("drop_down_item"));
        for (WebElement item : items) {
            if (dropDownItemName.equals(item.findElement(By.className("ddi_label")).getAttribute("textContent"))) {
                if (dropDownItem != null) {
                    throw new SeleniumUnexpectedException("DropDown [" + dropDownItemName + "] found many times");
                }
                dropDownItem = item;
            }
        }
        if (dropDownItem == null) {
            throw new SeleniumUnexpectedException("DropDown item [" + dropDownItemName + "] not found");
        }

        return dropDownItem;
    }

}
package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.helper.tree.Tree;

@Component
public class NewDropDown {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementWait elementWait;

    @Resource
    private Js js;

    @Resource
    private Tree tree;

    @Resource
    private Wait wait;

    @Resource
    private Window window;

    @Resource
    private Jquery jquery;

    public void selectMenu(String name) {
        selectEntity("lbApplication", name);
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad();
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad();
    }

    public void selectDashboard(String name) {
        selectEntity("lbDashboard", name);
    }

    public void selectPortal(String name) {
        selectEntity("lbPortal0", name);
    }

    public void selectComponentPackage(String name) {
        selectEntity("lbCompPkg0", name);
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    private void selectEntity(String id, String name) {
        WebElement currentEntity = seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).findElement(By.className("newDropDownLabel"));
        String currentEntityName = currentEntity.getText();

        if (name.equals(currentEntityName)) {
            return;
        }

        boolean failOpenWindow = true;
        int failOpenWindowCnt = 1;
        do {
            try {
                seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                elementWait.waitElementById("new_rows_" + id);
                elementWait.waitElementVisibleById("new_rows_" + id);
                elementWait.waitElementDisplayById("new_rows_" + id);

                Long position = js.getNewDropDownElementPosition("new_rows_" + id, "scrollContent", "newDropDownRowContainer", name);
                js.scrollNewDropDownTop("new_rows_" + id, "scrollContainer", position * 28L);

                WebElement entityElem = (WebElement) js.getNewDropDownElement("new_rows_" + id, "scrollContainer", "newDropDownRowContainer", name);
                elementWait.waitElementVisible(entityElem);

                entityElem.click();

                failOpenWindow = false;
            } catch (ElementNotVisibleException | StaleElementReferenceException | IndexOutOfBoundsException | TimeoutException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id("new_rows_" + id)).isDisplayed()) {
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                }
            }
        } while (failOpenWindow && failOpenWindowCnt <= 10);

        if (failOpenWindow) {
            throw new SeleniumUnexpectedException("");
        }
    }

    public void deleteMenu(String name) {
        deleteEntity("lbApplication", "Application", 3, name);
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad();
        tree.waitLoad(AbstractSeleniumCore.getTreeIdx());
        jquery.waitLoad();
        closeDropDown("lbApplication");
    }

    public void deleteComponentPackage(String name) {
        deleteEntity("lbCompPkg0", "CompPkg0", 2, name);
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    private void deleteEntity(String id, String buttonId, int buttonsCnt, String name) {
        boolean failOpenWindow = true;
        int failOpenWindowCnt = 1;
        do {
            try {
                seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                elementWait.waitElementById("new_rows_" + id);
                elementWait.waitElementVisibleById("new_rows_" + id);
                elementWait.waitElementDisplayById("new_rows_" + id);

                Long position = js.getNewDropDownElementPosition("new_rows_" + id, "scrollContent", "newDropDownRowContainer", name);
                js.scrollNewDropDownTop("new_rows_" + id, "scrollContainer", position * 28L);

                WebElement entityElem = (WebElement) js.getNewDropDownElement("new_rows_" + id, "scrollContainer", "newDropDownRowContainer", name);

                Actions act = new Actions(seleniumSettings.getWebDriver());
                act.moveToElement(entityElem).perform();

                wait.waitWebElement(By.className("newDropDownRowBtnWrapper"));
                elementWait.waitElementVisible(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(0));
                act.moveToElement(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(0)).perform();
                elementWait.waitElementAnimatedFinish(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(0));
                if (buttonsCnt > 1) {
                    elementWait.waitElementVisible(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(1));
                    elementWait.waitElementAnimatedFinish(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(1));
                    if (buttonsCnt > 2) {
                        elementWait.waitElementVisible(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(2));
                        elementWait.waitElementAnimatedFinish(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(2));
                    }
                }

                seleniumSettings.getWebDriver().findElement(By.id("btnDelete" + buttonId)).click();
                wait.waitAlert();
                seleniumSettings.getWebDriver().switchTo().alert().accept();

                failOpenWindow = false;
            } catch (ElementNotVisibleException | StaleElementReferenceException | IndexOutOfBoundsException | TimeoutException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id("new_rows_" + id)).isDisplayed()) {
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                }
            }
        } while (failOpenWindow && failOpenWindowCnt <= 10);

        if (failOpenWindow) {
            throw new SeleniumUnexpectedException("");
        }
    }

    public void openEditMenuForm(String name) {
        openEditEntityForm("lbApplication", "Application", 3, name);
    }

    public void openEditComponentPackageForm(String name) {
        openEditEntityForm("lbCompPkg0", "CompPkg0", 2, name);
    }

    private void openEditEntityForm(String id, String buttonId, int buttonsCnt, String name) {
        openEntityForm(id, "btnEdit" + buttonId, buttonsCnt, name);
    }

    public void openCloneMenuForm(String name) {
        openCloneEntityForm("lbApplication", "Application", 3, name);
    }

    private void openCloneEntityForm(String id, String buttonId, int buttonsCnt, String name) {
        openEntityForm(id, "btnClone" + buttonId, buttonsCnt, name);
    }

    private void openEntityForm(String id, String buttonId, int buttonsCnt, String name) {
        boolean failOpenWindow = true;
        int failOpenWindowCnt = 1;
        do {
            try {
                seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                elementWait.waitElementById("new_rows_" + id);
                elementWait.waitElementVisibleById("new_rows_" + id);
                elementWait.waitElementDisplayById("new_rows_" + id);

                Long position = js.getNewDropDownElementPosition("new_rows_" + id, "scrollContent", "newDropDownRowContainer", name);
                js.scrollNewDropDownTop("new_rows_" + id, "scrollContainer", position * 28L);

                WebElement entityElem = (WebElement) js.getNewDropDownElement("new_rows_" + id, "scrollContainer", "newDropDownRowContainer", name);

                Actions act = new Actions(seleniumSettings.getWebDriver());
                act.moveToElement(entityElem).perform();

                wait.waitWebElement(By.className("newDropDownRowBtnWrapper"));
                elementWait.waitElementVisible(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(0));
                act.moveToElement(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(0)).perform();
                elementWait.waitElementAnimatedFinish(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(0));
                if (buttonsCnt > 1) {
                    elementWait.waitElementVisible(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(1));
                    elementWait.waitElementAnimatedFinish(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(1));
                    if (buttonsCnt > 2) {
                        elementWait.waitElementVisible(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(2));
                        elementWait.waitElementAnimatedFinish(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(2));
                    }
                }

                window.openModal(By.id(buttonId));

                failOpenWindow = false;
            } catch (ElementNotVisibleException | StaleElementReferenceException | IndexOutOfBoundsException | TimeoutException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id("new_rows_" + id)).isDisplayed()) {
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                }
            }
        } while (failOpenWindow && failOpenWindowCnt <= 10);

        if (failOpenWindow) {
            throw new SeleniumUnexpectedException("");
        }
    }

    private void closeDropDown(String id) {
        seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
        elementWait.waitElementById("new_rows_" + id);
        elementWait.waitElementNotVisibleById("new_rows_" + id);
        elementWait.waitElementNotDisplayById("new_rows_" + id);
    }

}
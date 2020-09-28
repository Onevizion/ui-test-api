package com.onevizion.uitest.api.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class NewDropDown {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private Js js;

    @Autowired
    private Wait wait;

    @Autowired
    private Window window;

    @Autowired
    private Grid2 grid2;

    public void selectComponentPackage(String name) {
        selectEntity("lbCompPkg0", name);
        grid2.waitLoad();
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
                seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).findElement(By.className("newDropDownLabel")).click();
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
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).findElement(By.className("newDropDownLabel")).click();
                }
            }
        } while (failOpenWindow && failOpenWindowCnt <= 10);

        if (failOpenWindow) {
            throw new SeleniumUnexpectedException("");
        }
    }

    public void deleteComponentPackage(String name) {
        deleteEntity("lbCompPkg0", "CompPkg0", 2, name);
        grid2.waitLoad();
    }

    private void deleteEntity(String id, String buttonId, int buttonsCnt, String name) {
        boolean failOpenWindow = true;
        int failOpenWindowCnt = 1;
        do {
            try {
                seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).findElement(By.className("newDropDownLabel")).click();
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
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).findElement(By.className("newDropDownLabel")).click();
                }
            }
        } while (failOpenWindow && failOpenWindowCnt <= 10);

        if (failOpenWindow) {
            throw new SeleniumUnexpectedException("");
        }
    }

    public void openEditComponentPackageForm(String name) {
        openEditEntityForm("lbCompPkg0", "CompPkg0", 2, name);
    }

    private void openEditEntityForm(String id, String buttonId, int buttonsCnt, String name) {
        openEntityForm(id, "btnEdit" + buttonId, buttonsCnt, name);
    }

    private void openEntityForm(String id, String buttonId, int buttonsCnt, String name) {
        boolean failOpenWindow = true;
        int failOpenWindowCnt = 1;
        do {
            try {
                seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).findElement(By.className("newDropDownLabel")).click();
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
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).findElement(By.className("newDropDownLabel")).click();
                }
            }
        } while (failOpenWindow && failOpenWindowCnt <= 10);

        if (failOpenWindow) {
            throw new SeleniumUnexpectedException("");
        }
    }

}
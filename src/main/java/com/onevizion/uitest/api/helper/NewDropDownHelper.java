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
import com.onevizion.uitest.api.helper.jquery.JqueryWait;
import com.onevizion.uitest.api.helper.tree.TreeWait;

@Component
public class NewDropDownHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private TreeWait treeWait;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private Window window;

    @Resource
    private JqueryWait jqueryWait;

    public void selectMenu(String name) {
        selectEntity("lbApplication", name);
        treeWait.waitTreeLoad(AbstractSeleniumCore.getTreeIdx());
        jqueryWait.waitJQueryLoad();
        treeWait.waitTreeLoad(AbstractSeleniumCore.getTreeIdx());
        jqueryWait.waitJQueryLoad();
    }

    public void selectDashboard(String name) {
        selectEntity("lbDashboard", name);
    }

    public void selectComponentPackage(String name) {
        selectEntity("lbCompPkg0", name);
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
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
                elementWaitHelper.waitElementById("new_rows_" + id);
                elementWaitHelper.waitElementVisibleById("new_rows_" + id);
                elementWaitHelper.waitElementDisplayById("new_rows_" + id);

                Long position = jsHelper.getNewDropDownElementPosition("new_rows_" + id, "scrollContent", "newDropDownRowContainer", name);
                jsHelper.scrollNewDropDownTop("new_rows_" + id, "scrollContainer", position * 28L);

                WebElement entityElem = (WebElement) jsHelper.getNewDropDownElement("new_rows_" + id, "scrollContainer", "newDropDownRowContainer", name);
                elementWaitHelper.waitElementVisible(entityElem);

                entityElem.click();

                failOpenWindow = false;
            } catch (ElementNotVisibleException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id("new_rows_" + id)).isDisplayed()) {
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                }
            } catch (StaleElementReferenceException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id("new_rows_" + id)).isDisplayed()) {
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                }
            } catch (IndexOutOfBoundsException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id("new_rows_" + id)).isDisplayed()) {
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                }
            } catch (TimeoutException e) {
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
        treeWait.waitTreeLoad(AbstractSeleniumCore.getTreeIdx());
        jqueryWait.waitJQueryLoad();
        treeWait.waitTreeLoad(AbstractSeleniumCore.getTreeIdx());
        jqueryWait.waitJQueryLoad();
    }

    public void deleteComponentPackage(String name) {
        deleteEntity("lbCompPkg0", "CompPkg0", 2, name);
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    private void deleteEntity(String id, String buttonId, int buttonsCnt, String name) {
        boolean failOpenWindow = true;
        int failOpenWindowCnt = 1;
        do {
            try {
                seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                elementWaitHelper.waitElementById("new_rows_" + id);
                elementWaitHelper.waitElementVisibleById("new_rows_" + id);
                elementWaitHelper.waitElementDisplayById("new_rows_" + id);

                Long position = jsHelper.getNewDropDownElementPosition("new_rows_" + id, "scrollContent", "newDropDownRowContainer", name);
                jsHelper.scrollNewDropDownTop("new_rows_" + id, "scrollContainer", position * 28L);

                WebElement entityElem = (WebElement) jsHelper.getNewDropDownElement("new_rows_" + id, "scrollContainer", "newDropDownRowContainer", name);

                Actions act = new Actions(seleniumSettings.getWebDriver());
                act.moveToElement(entityElem).perform();

                waitHelper.waitWebElement(By.className("newDropDownRowBtnWrapper"));
                elementWaitHelper.waitElementVisible(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(0));
                act.moveToElement(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(0)).perform();
                elementWaitHelper.waitElementAnimatedFinish(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(0));
                if (buttonsCnt > 1) {
                    elementWaitHelper.waitElementVisible(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(1));
                    elementWaitHelper.waitElementAnimatedFinish(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(1));
                    if (buttonsCnt > 2) {
                        elementWaitHelper.waitElementVisible(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(2));
                        elementWaitHelper.waitElementAnimatedFinish(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(2));
                    }
                }

                seleniumSettings.getWebDriver().findElement(By.id("btnDelete" + buttonId)).click();
                waitHelper.waitAlert();
                seleniumSettings.getWebDriver().switchTo().alert().accept();

                failOpenWindow = false;
            } catch (ElementNotVisibleException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id("new_rows_" + id)).isDisplayed()) {
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                }
            } catch (StaleElementReferenceException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id("new_rows_" + id)).isDisplayed()) {
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                }
            } catch (IndexOutOfBoundsException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id("new_rows_" + id)).isDisplayed()) {
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                }
            } catch (TimeoutException e) {
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
                elementWaitHelper.waitElementById("new_rows_" + id);
                elementWaitHelper.waitElementVisibleById("new_rows_" + id);
                elementWaitHelper.waitElementDisplayById("new_rows_" + id);

                Long position = jsHelper.getNewDropDownElementPosition("new_rows_" + id, "scrollContent", "newDropDownRowContainer", name);
                jsHelper.scrollNewDropDownTop("new_rows_" + id, "scrollContainer", position * 28L);

                WebElement entityElem = (WebElement) jsHelper.getNewDropDownElement("new_rows_" + id, "scrollContainer", "newDropDownRowContainer", name);

                Actions act = new Actions(seleniumSettings.getWebDriver());
                act.moveToElement(entityElem).perform();

                waitHelper.waitWebElement(By.className("newDropDownRowBtnWrapper"));
                elementWaitHelper.waitElementVisible(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(0));
                act.moveToElement(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(0)).perform();
                elementWaitHelper.waitElementAnimatedFinish(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(0));
                if (buttonsCnt > 1) {
                    elementWaitHelper.waitElementVisible(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(1));
                    elementWaitHelper.waitElementAnimatedFinish(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(1));
                    if (buttonsCnt > 2) {
                        elementWaitHelper.waitElementVisible(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(2));
                        elementWaitHelper.waitElementAnimatedFinish(entityElem.findElements(By.className("newDropDownRowBtnWrapper")).get(2));
                    }
                }

                window.openModal(By.id(buttonId));

                failOpenWindow = false;
            } catch (ElementNotVisibleException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id("new_rows_" + id)).isDisplayed()) {
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                }
            } catch (StaleElementReferenceException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id("new_rows_" + id)).isDisplayed()) {
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                }
            } catch (IndexOutOfBoundsException e) {
                failOpenWindowCnt++;
                if (seleniumSettings.getWebDriver().findElement(By.id("new_rows_" + id)).isDisplayed()) {
                    seleniumSettings.getWebDriver().findElement(By.id("new_" + id)).click();
                }
            } catch (TimeoutException e) {
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

}
package com.onevizion.uitest.api.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumLogger;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumAlertException;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.html.input.file.HtmlInputFile;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.helper.wiki.FckEditor;
import com.onevizion.uitest.api.vo.ConfigFieldType;

@Component
public class Tb {

    private static final String EFILE_EDIT_BUTTON = "btnEditEfile";
    private static final String EFILE_DELETE_BUTTON = "btnDeleteEfile";

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private SeleniumLogger seleniumLogger;

    @Autowired
    private Js js;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Wait wait;

    @Autowired
    private Selector selector;

    @Autowired
    private Element element;

    @Autowired
    private Checkbox checkbox;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private HtmlInputFile htmlInputFile;

    @Autowired
    private FckEditor fckEditor;

    @Autowired
    private Jquery jquery;

    String getLastFieldIndex(String name, int elementPosition) {
        List<WebElement> elems = seleniumSettings.getWebDriver().findElements(By.name(name));
        List<Integer> idx = new ArrayList<>();
        String suffix = "";
        if (name.contains("_start")) {
            suffix = "_start";
        } else if (name.contains("_finish")) {
            suffix = "_finish";
        } else if (name.contains("_but")) {
            suffix = "_but";
        } else if (name.contains("_disp")) {
            suffix = "_disp";
        }
        for (WebElement elem : elems) {
            String id = elem.getAttribute("id");
            id = id.replace("_disp", "").replace("_but", ""); //for efile field
            id = id.replace("_start", "").replace("_finish", ""); //for task date
            idx.add(Integer.parseInt(id.substring(3)));
        }
        Collections.sort(idx, (Integer o1, Integer o2) -> {
            if (o1.compareTo(o2) < 0) {
                return -1;
            } else {
                return 1;
            }
        });
        return idx.get(elementPosition - 1) + suffix;
    }

    public int getColumnCount(Long gridIdx) {
        int actualColumnsCnt = 0;
        int columnsCnt = js.getGridColumnsCount(gridIdx);
        for (int i = 0; i < columnsCnt; i++) {
            if (!js.isGridColumnHidden(gridIdx, i) && !js.getGridColIdByIndex(gridIdx, i).equals("-1")) {
                actualColumnsCnt = actualColumnsCnt + 1;
            }
        }
        return actualColumnsCnt;
    }

    public void editFields(List<String> vals, List<String> fieldNames, int elementsPosition) {
        editField(ConfigFieldType.CHECKBOX, vals.get(0), fieldNames.get(0), elementsPosition);
        editField(ConfigFieldType.DATE, vals.get(1), fieldNames.get(1), elementsPosition);
        editField(ConfigFieldType.DB_DROP_DOWN, vals.get(2), fieldNames.get(2), elementsPosition);
        editField(ConfigFieldType.DB_SELECTOR, vals.get(3), fieldNames.get(3), elementsPosition);
        editField(ConfigFieldType.DROP_DOWN, vals.get(4), fieldNames.get(4), elementsPosition);
        editField(ConfigFieldType.ELECTRONIC_FILE, vals.get(5), fieldNames.get(5), elementsPosition);
        editField(ConfigFieldType.HYPERLINK, vals.get(6), fieldNames.get(6), elementsPosition);
        editField(ConfigFieldType.LATITUDE, vals.get(7), fieldNames.get(7), elementsPosition);
        editField(ConfigFieldType.LONGITUDE, vals.get(8), fieldNames.get(8), elementsPosition);
        editField(ConfigFieldType.MEMO, vals.get(9), fieldNames.get(9), elementsPosition);
        editField(ConfigFieldType.NUMBER, vals.get(10), fieldNames.get(10), elementsPosition);
        editField(ConfigFieldType.SELECTOR, vals.get(11), fieldNames.get(11), elementsPosition);
        editField(ConfigFieldType.TEXT, vals.get(12), fieldNames.get(12), elementsPosition);
        editField(ConfigFieldType.TRACKOR_SELECTOR, vals.get(13), fieldNames.get(13), elementsPosition);
        editField(ConfigFieldType.WIKI, vals.get(14), fieldNames.get(14), elementsPosition);
        editField(ConfigFieldType.MULTI_SELECTOR, vals.get(15), fieldNames.get(15), elementsPosition);
        editField(ConfigFieldType.DATE_TIME, vals.get(16), fieldNames.get(16), elementsPosition);
        editField(ConfigFieldType.TIME, vals.get(17), fieldNames.get(17), elementsPosition);
        editField(ConfigFieldType.TRACKOR_DROP_DOWN, vals.get(18), fieldNames.get(18), elementsPosition);
        //CALCULATED
        //ROLLUP
        editField(ConfigFieldType.MULTI_TRACKOR_SELECTOR, vals.get(21), fieldNames.get(21), elementsPosition);
    }

    public void editField(ConfigFieldType fieldDataType, String value, String fieldName, int elementPosition) {
        Actions action = new Actions(seleniumSettings.getWebDriver());

        if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(fieldName, elementPosition);
                String actualVal = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx)).isSelected() ? "YES" : "NO";
                if (!actualVal.equals(value)) {
                    WebElement checkboxElement = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx));
                    WebElement newCheckbox = checkbox.findLabelByElement(checkboxElement);
                    element.click(newCheckbox);
                }
            } else {
                String actualVal = seleniumSettings.getWebDriver().findElement(By.name(fieldName)).isSelected() ? "YES" : "NO";
                if (!actualVal.equals(value)) {
                    WebElement checkboxElement = seleniumSettings.getWebDriver().findElement(By.name(fieldName));
                    WebElement newCheckbox = checkbox.findLabelByElement(checkboxElement);
                    element.click(newCheckbox);
                }
            }
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(fieldDataType) || ConfigFieldType.DROP_DOWN.equals(fieldDataType)
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(fieldName, elementPosition);
                element.moveToElementById("idx" + idx);
                new Select(seleniumSettings.getWebDriver().findElement(By.id("idx" + idx))).selectByVisibleText(value);
            } else {
                element.moveToElementByName(fieldName);
                new Select(seleniumSettings.getWebDriver().findElement(By.name(fieldName))).selectByVisibleText(value);
            }
        } else if (ConfigFieldType.TEXT.equals(fieldDataType) || ConfigFieldType.NUMBER.equals(fieldDataType)
                || ConfigFieldType.MEMO.equals(fieldDataType) || ConfigFieldType.HYPERLINK.equals(fieldDataType)
                || ConfigFieldType.DATE.equals(fieldDataType) || ConfigFieldType.DATE_TIME.equals(fieldDataType)
                || ConfigFieldType.TIME.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(fieldName, elementPosition);

                //chromedriver 2.43 for date, time, date/time clear() - sendkey - sendkey - alert after clear() (onblur event with delay)
                //element.moveToElementById("idx" + idx);
                //seleniumSettings.getWebDriver().findElement(By.id("idx" + idx)).clear();
                //seleniumSettings.getWebDriver().findElement(By.id("idx" + idx)).sendKeys(value);

                element.clickById("idx" + idx);
                String prevVal = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx)).getAttribute("value");

                Actions actionObject = new Actions(seleniumSettings.getWebDriver());
                for (int i = 0; i < prevVal.length(); i++) {
                    actionObject.sendKeys(Keys.ARROW_RIGHT).perform();
                }
                for (int i = 0; i < prevVal.length(); i++) {
                    actionObject.sendKeys(Keys.BACK_SPACE).perform();
                }
                actionObject.sendKeys(value).perform();
            } else {
                //chromedriver 2.43 for date, time, date/time clear() - sendkey - sendkey - alert after clear() (onblur event with delay)
                //element.moveToElementByName(fieldName);
                //seleniumSettings.getWebDriver().findElement(By.name(fieldName)).clear();
                //seleniumSettings.getWebDriver().findElement(By.name(fieldName)).sendKeys(value);

                element.clickByName(fieldName);
                String prevVal = seleniumSettings.getWebDriver().findElement(By.name(fieldName)).getAttribute("value");

                Actions actionObject = new Actions(seleniumSettings.getWebDriver());
                for (int i = 0; i < prevVal.length(); i++) {
                    actionObject.sendKeys(Keys.ARROW_RIGHT).perform();
                }
                for (int i = 0; i < prevVal.length(); i++) {
                    actionObject.sendKeys(Keys.BACK_SPACE).perform();
                }
                actionObject.sendKeys(value).perform();
            }
        } else if (ConfigFieldType.LATITUDE.equals(fieldDataType) || ConfigFieldType.LONGITUDE.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(fieldName, elementPosition);
                element.clickById("idx" + idx);
                if (seleniumSettings.getBrowser().equals("chrome")) {
                    seleniumSettings.getWebDriver().findElement(By.id("idx" + idx)).clear();
                    seleniumSettings.getWebDriver().findElement(By.id("idx" + idx)).sendKeys(value);
                } else if (seleniumSettings.getBrowser().equals("firefox")) {
                    Actions actionObject = new Actions(seleniumSettings.getWebDriver());
                    actionObject.sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,
                            Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,
                            Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT).perform();
                    actionObject.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                            Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                            Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE).perform();
                    actionObject.sendKeys(value).perform();
                } else {
                    throw new SeleniumUnexpectedException("Not support browser[" + seleniumSettings.getBrowser() + "]");
                }
            } else {
                element.clickByName(fieldName);
                if (seleniumSettings.getBrowser().equals("chrome")) {
                    seleniumSettings.getWebDriver().findElement(By.name(fieldName)).clear();
                    seleniumSettings.getWebDriver().findElement(By.name(fieldName)).sendKeys(value);
                } else if (seleniumSettings.getBrowser().equals("firefox")) {
                    Actions actionObject = new Actions(seleniumSettings.getWebDriver());
                    actionObject.sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,
                            Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,
                            Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT).perform();
                    actionObject.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                            Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                            Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE).perform();
                    actionObject.sendKeys(value).perform();
                } else {
                    throw new SeleniumUnexpectedException("Not support browser[" + seleniumSettings.getBrowser() + "]");
                }
            }
        } else if (ConfigFieldType.DB_SELECTOR.equals(fieldDataType) || ConfigFieldType.SELECTOR.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(fieldName, elementPosition);
                By btnOpen = By.id("idx" + idx + "_but");
                element.moveToElementById("idx" + idx + "_but");
                selector.selectRadio(btnOpen, By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1, value, 1L);
            } else {
                element.moveToElementByName(fieldName + "_but");
                selector.selectRadio(By.name(fieldName + "_but"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1, value, 1L);
            }
        } else if (ConfigFieldType.TRACKOR_SELECTOR.equals(fieldDataType)) {
            try {
                if (elementPosition > 1) {
                    String idx = getLastFieldIndex(fieldName, elementPosition);
                    By btnOpen = By.id("idx" + idx + "_but");
                    element.moveToElementById("idx" + idx + "_but");
                    selector.selectRadio(btnOpen, By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1, value, 1L);
                } else {
                    element.moveToElementByName(fieldName + "_but");
                    selector.selectRadio(By.name(fieldName + "_but"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1, value, 1L);
                }
                wait.waitFormLoad();
            } catch (UnhandledAlertException | SeleniumAlertException e) {
                seleniumLogger.warn("Alert Present " + seleniumSettings.getWebDriver().switchTo().alert().getText());
                Assert.assertTrue(seleniumSettings.getWebDriver().switchTo().alert().getText().contains("Following fields with unsaved changes has been modified on the server. Press \"OK\" to keep your values or \"Cancel\" to replace your values with new values from the server"));
                seleniumSettings.getWebDriver().switchTo().alert().accept();
                //seleniumSettings.getWebDriver().switchTo().defaultContent(); //need or not need?
                wait.waitFormLoad();
            } catch (WebDriverException e) {
                if (seleniumSettings.getBrowser().equals("firefox") && e.getMessage().startsWith("Failed to convert data to an object")) {
                    seleniumLogger.error("Alert Present " + seleniumSettings.getWebDriver().switchTo().alert().getText());
                    Assert.assertTrue(seleniumSettings.getWebDriver().switchTo().alert().getText().contains("Following fields with unsaved changes has been modified on the server. Press \"OK\" to keep your values or \"Cancel\" to replace your values with new values from the server"));
                    seleniumSettings.getWebDriver().switchTo().alert().accept();
                    //seleniumSettings.getWebDriver().switchTo().defaultContent(); //need or not need?
                    wait.waitFormLoad();
                } else {
                    throw e;
                }
            } catch (NullPointerException e) {
                seleniumLogger.error("Alert Present " + seleniumSettings.getWebDriver().switchTo().alert().getText());
                Assert.assertTrue(seleniumSettings.getWebDriver().switchTo().alert().getText().contains("Following fields with unsaved changes has been modified on the server. Press \"OK\" to keep your values or \"Cancel\" to replace your values with new values from the server"));
                seleniumSettings.getWebDriver().switchTo().alert().accept();
                //seleniumSettings.getWebDriver().switchTo().defaultContent(); //need or not need?
                wait.waitFormLoad();
            }
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(fieldDataType) || ConfigFieldType.MULTI_TRACKOR_SELECTOR.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(fieldName, elementPosition);
                element.moveToElementById("idx" + idx + "_disp");
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.id("idx" + idx + "_disp"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
                By btnOpen = By.id("idx" + idx + "_but");
                selector.selectCheckbox(btnOpen, 1, Arrays.asList(value.split(",")), 1L);
            } else {
                element.moveToElementByName(fieldName + "_disp");
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.name(fieldName + "_disp"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
                selector.selectCheckbox(By.name(fieldName + "_but"), 1, Arrays.asList(value.split(",")), 1L);
            }
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(fieldName + "_disp", elementPosition);
                idx = idx.replace("_disp", "");
                String hiddenInputFile = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx + "_disp")).getAttribute("hiddeninputfile");
                htmlInputFile.uploadOnForm(hiddenInputFile, value);
            } else {
                String hiddenInputFile = seleniumSettings.getWebDriver().findElement(By.name(fieldName + "_disp")).getAttribute("hiddeninputfile");
                htmlInputFile.uploadOnForm(hiddenInputFile, value);
            }
        } else if (ConfigFieldType.WIKI.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(fieldName, elementPosition);
                fckEditor.setValue("idx" + idx, value);
            } else {
                String id = seleniumSettings.getWebDriver().findElement(By.name(fieldName)).getAttribute("id");
                fckEditor.setValue(id, value);
            }
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    public void checkFields(List<String> fields, List<String> vals, int elementsPosition, boolean isOpenSelector, boolean isWikiReadOnly) {
        checkField(ConfigFieldType.CHECKBOX, fields.get(0), vals.get(0), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.DATE, fields.get(1), vals.get(1), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.DB_DROP_DOWN, fields.get(2), vals.get(2), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.DB_SELECTOR, fields.get(3), vals.get(3), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.DROP_DOWN, fields.get(4), vals.get(4), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.ELECTRONIC_FILE, fields.get(5), vals.get(5), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.HYPERLINK, fields.get(6), vals.get(6), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.LATITUDE, fields.get(7), vals.get(7), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.LONGITUDE, fields.get(8), vals.get(8), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.MEMO, fields.get(9), vals.get(9), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.NUMBER, fields.get(10), vals.get(10), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.SELECTOR, fields.get(11), vals.get(11), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.TEXT, fields.get(12), vals.get(12), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.TRACKOR_SELECTOR, fields.get(13), vals.get(13), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.WIKI, fields.get(14), vals.get(14), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.MULTI_SELECTOR, fields.get(15), vals.get(15), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.DATE_TIME, fields.get(16), vals.get(16), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.TIME, fields.get(17), vals.get(17), elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.TRACKOR_DROP_DOWN, fields.get(18), vals.get(18), elementsPosition, isOpenSelector, isWikiReadOnly);
        //CALCULATED
        //ROLLUP
        checkField(ConfigFieldType.MULTI_TRACKOR_SELECTOR, fields.get(21), vals.get(21), elementsPosition, isOpenSelector, isWikiReadOnly);
    }

    public void checkField(ConfigFieldType fieldDataType, String field, String val, int elementPosition, boolean isOpenSelector, boolean isWikiReadOnly) {
        if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                assertElement.assertCheckboxById("idx" + idx, val);
            } else {
                assertElement.assertCheckbox(field, val);
            }
        } else if (ConfigFieldType.DATE.equals(fieldDataType) || ConfigFieldType.HYPERLINK.equals(fieldDataType)
                || ConfigFieldType.LATITUDE.equals(fieldDataType) || ConfigFieldType.LONGITUDE.equals(fieldDataType)
                || ConfigFieldType.MEMO.equals(fieldDataType) || ConfigFieldType.NUMBER.equals(fieldDataType)
                || ConfigFieldType.TEXT.equals(fieldDataType) || ConfigFieldType.DATE_TIME.equals(fieldDataType)
                || ConfigFieldType.TIME.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                assertElement.assertTextById("idx" + idx, val);
            } else {
                assertElement.assertText(field, val);
            }
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(fieldDataType) || ConfigFieldType.DROP_DOWN.equals(fieldDataType)
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                assertElement.assertSelectById("idx" + idx, val);
            } else {
                assertElement.assertSelect(field, val);
            }
        } else if (ConfigFieldType.SELECTOR.equals(fieldDataType) || ConfigFieldType.TRACKOR_SELECTOR.equals(fieldDataType) || ConfigFieldType.DB_SELECTOR.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                assertElement.assertRadioPsSelectorById("idx" + idx + "_disp", "idx" + idx + "_but", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, val, 1L, isOpenSelector);
            } else {
                assertElement.assertRadioPsSelector(field + "_disp", field + "_but", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, val, 1L, isOpenSelector);
            }
        } else if (ConfigFieldType.WIKI.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                if (isWikiReadOnly) {
                    fckEditor.checkValueReadOnly("idx" + idx, val);
                } else {
                    fckEditor.checkValue("idx" + idx, val);
                }
            } else {
                if (isWikiReadOnly) {
                    fckEditor.checkValueReadOnly(field, val);
                } else {
                    String id = seleniumSettings.getWebDriver().findElement(By.name(field)).getAttribute("id");
                    fckEditor.checkValue(id, val);
                }
            }
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(fieldDataType) || ConfigFieldType.MULTI_TRACKOR_SELECTOR.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                assertElement.assertCheckboxPsSelectorById("idx" + idx + "_disp", "idx" + idx + "_but", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(val.split(",")), 1L, isOpenSelector);
            } else {
                assertElement.assertCheckboxPsSelector(field + "_disp", field + "_but", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(val.split(",")), 1L, isOpenSelector);
            }
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field + "_disp", elementPosition);
                idx = idx.replace("_disp", "");
                assertElement.assertTextById("idx" + idx + "_disp", val);
            } else {
                assertElement.assertText(field + "_disp", val);
            }
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    public void clearFields(List<String> fieldNames, int elementsPosition) {
        clearField(ConfigFieldType.CHECKBOX, fieldNames.get(0), elementsPosition);
        clearField(ConfigFieldType.DATE, fieldNames.get(1), elementsPosition);
        clearField(ConfigFieldType.DB_DROP_DOWN, fieldNames.get(2), elementsPosition);
        clearField(ConfigFieldType.DB_SELECTOR, fieldNames.get(3), elementsPosition);
        clearField(ConfigFieldType.DROP_DOWN, fieldNames.get(4), elementsPosition);
        clearField(ConfigFieldType.ELECTRONIC_FILE, fieldNames.get(5), elementsPosition);
        clearField(ConfigFieldType.HYPERLINK, fieldNames.get(6), elementsPosition);
        clearField(ConfigFieldType.LATITUDE, fieldNames.get(7), elementsPosition);
        clearField(ConfigFieldType.LONGITUDE, fieldNames.get(8), elementsPosition);
        clearField(ConfigFieldType.MEMO, fieldNames.get(9), elementsPosition);
        clearField(ConfigFieldType.NUMBER, fieldNames.get(10), elementsPosition);
        clearField(ConfigFieldType.SELECTOR, fieldNames.get(11), elementsPosition);
        clearField(ConfigFieldType.TEXT, fieldNames.get(12), elementsPosition);
        clearField(ConfigFieldType.TRACKOR_SELECTOR, fieldNames.get(13), elementsPosition);
        clearField(ConfigFieldType.WIKI, fieldNames.get(14), elementsPosition);
        clearField(ConfigFieldType.MULTI_SELECTOR, fieldNames.get(15), elementsPosition);
        clearField(ConfigFieldType.DATE_TIME, fieldNames.get(16), elementsPosition);
        clearField(ConfigFieldType.TIME, fieldNames.get(17), elementsPosition);
        clearField(ConfigFieldType.TRACKOR_DROP_DOWN, fieldNames.get(18), elementsPosition);
        //CALCULATED
        //ROLLUP
        clearField(ConfigFieldType.MULTI_TRACKOR_SELECTOR, fieldNames.get(21), elementsPosition);
    }

    public void clearField(ConfigFieldType fieldDataType, String field, int elementPosition) {
        Actions action = new Actions(seleniumSettings.getWebDriver());

        if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                String actualVal = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx)).isSelected() ? "YES" : "NO";
                if (!actualVal.equals("NO")) {
                    WebElement webCheckbox = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx));
                    WebElement newCheckbox = webCheckbox.findElement(By.xpath("./.."));
                    element.click(newCheckbox);
                }
            } else {
                String actualVal = seleniumSettings.getWebDriver().findElement(By.name(field)).isSelected() ? "YES" : "NO";
                if (!actualVal.equals("NO")) {
                    WebElement checkboxElement = seleniumSettings.getWebDriver().findElement(By.name(field));
                    WebElement newCheckbox = checkbox.findLabelByElement(checkboxElement);
                    element.click(newCheckbox);
                }
            }
        } else if (ConfigFieldType.DATE.equals(fieldDataType) || ConfigFieldType.HYPERLINK.equals(fieldDataType)
                || ConfigFieldType.MEMO.equals(fieldDataType) || ConfigFieldType.NUMBER.equals(fieldDataType)
                || ConfigFieldType.TEXT.equals(fieldDataType) || ConfigFieldType.DATE_TIME.equals(fieldDataType)
                || ConfigFieldType.TIME.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                element.moveToElementById("idx" + idx);
                seleniumSettings.getWebDriver().findElement(By.id("idx" + idx)).clear();
            } else {
                element.moveToElementByName(field);
                seleniumSettings.getWebDriver().findElement(By.name(field)).clear();
            }
        } else if (ConfigFieldType.LATITUDE.equals(fieldDataType) || ConfigFieldType.LONGITUDE.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                element.clickById("idx" + idx);
                if (seleniumSettings.getBrowser().equals("chrome")) {
                    seleniumSettings.getWebDriver().findElement(By.id("idx" + idx)).clear();
                } else if (seleniumSettings.getBrowser().equals("firefox")) {
                    Actions actionObject = new Actions(seleniumSettings.getWebDriver());
                    actionObject.sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,
                            Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,
                            Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT).perform();
                    actionObject.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                            Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                            Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE).perform();
                } else {
                    throw new SeleniumUnexpectedException("Not support browser[" + seleniumSettings.getBrowser() + "]");
                }
            } else {
                element.clickByName(field);
                if (seleniumSettings.getBrowser().equals("chrome")) {
                    seleniumSettings.getWebDriver().findElement(By.name(field)).clear();
                } else if (seleniumSettings.getBrowser().equals("firefox")) {
                    Actions actionObject = new Actions(seleniumSettings.getWebDriver());
                    actionObject.sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,
                            Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,
                            Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT).perform();
                    actionObject.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                            Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                            Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE).perform();
                } else {
                    throw new SeleniumUnexpectedException("Not support browser[" + seleniumSettings.getBrowser() + "]");
                }
            }
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(fieldDataType) || ConfigFieldType.DROP_DOWN.equals(fieldDataType)
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                element.moveToElementById("idx" + idx);
                new Select(seleniumSettings.getWebDriver().findElement(By.id("idx" + idx))).selectByVisibleText("");
            } else {
                element.moveToElementByName(field);
                new Select(seleniumSettings.getWebDriver().findElement(By.name(field))).selectByVisibleText("");
            }
        } else if (ConfigFieldType.SELECTOR.equals(fieldDataType) || ConfigFieldType.TRACKOR_SELECTOR.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                element.moveToElementById("idx" + idx + "_disp");
                seleniumSettings.getWebDriver().findElement(By.id("idx" + idx + "_disp")).clear();
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.id("idx" + idx + "_disp"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            } else {
                element.moveToElementByName(field + "_disp");
                seleniumSettings.getWebDriver().findElement(By.name(field + "_disp")).clear();
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.name(field + "_disp"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            }
        } else if (ConfigFieldType.DB_SELECTOR.equals(fieldDataType) || ConfigFieldType.MULTI_SELECTOR.equals(fieldDataType)
                || ConfigFieldType.MULTI_TRACKOR_SELECTOR.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                element.moveToElementById("idx" + idx + "_disp");
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.id("idx" + idx + "_disp"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            } else {
                element.moveToElementByName(field + "_disp");
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.name(field + "_disp"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            }
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field + "_disp", elementPosition);
                idx = idx.replace("_disp", "");
                element.moveToElementById("idx" + idx + "_disp");
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.id("idx" + idx + "_disp"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            } else {
                element.moveToElementByName(field + "_disp");
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.name(field + "_disp"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            }
        } else if (ConfigFieldType.WIKI.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                fckEditor.setValue("idx" + idx, "");
            } else {
                String id = seleniumSettings.getWebDriver().findElement(By.name(field)).getAttribute("id");
                fckEditor.setValue(id, "");
            }
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    public Map<String, String> transformValsForCheckCells(List<String> columnNames, List<String> values) {
        Map<String, String> vals = new HashMap<String, String>();

        vals.put(columnNames.get(0), values.get(0)); //CHECKBOX
        vals.put(columnNames.get(1), values.get(1)); //DATE
        vals.put(columnNames.get(2), values.get(2)); //DB_DROP_DOWN
        vals.put(columnNames.get(3), values.get(3)); //DB_SELECTOR
        vals.put(columnNames.get(4), values.get(4)); //DROP_DOWN
        vals.put(columnNames.get(5), values.get(5)); //ELECTRONIC_FILE
        vals.put(columnNames.get(6), values.get(6)); //HYPERLINK
        vals.put(columnNames.get(7), values.get(7)); //LATITUDE
        vals.put(columnNames.get(8), values.get(8)); //LONGITUDE
        vals.put(columnNames.get(9), values.get(9)); //MEMO
        vals.put(columnNames.get(10), values.get(10)); //NUMBER
        vals.put(columnNames.get(11), values.get(11)); //SELECTOR
        vals.put(columnNames.get(12), values.get(12)); //TEXT
        vals.put(columnNames.get(13), values.get(13)); //TRACKOR_SELECTOR
        vals.put(columnNames.get(14), values.get(14)); //WIKI
        vals.put(columnNames.get(15), values.get(15).replaceAll(",", ", ")); //MULTI_SELECTOR
        vals.put(columnNames.get(16), values.get(16)); //DATE_TIME
        vals.put(columnNames.get(17), values.get(17)); //TIME
        vals.put(columnNames.get(18), values.get(18)); //TRACKOR_DROPDOWN
        //CALCULATED
        //ROLLUP
        vals.put(columnNames.get(21), values.get(21).replaceAll(",", ", ")); //MULTI_TRACKOR_SELECTOR

        return vals;
    }

    public void editCells(Long gridIndex, int rowIndex, List<Integer> columns, List<String> vals,
            Map<String, String> gridExpVals, Map<String, String> expVals, List<String> columnNames, List<String> fieldNames) {
        editCell(gridIndex, rowIndex, columns.get(0), ConfigFieldType.CHECKBOX, vals.get(0), columnNames.get(0), fieldNames != null ? fieldNames.get(0) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(1), ConfigFieldType.DATE, vals.get(1), columnNames.get(1), fieldNames != null ? fieldNames.get(1) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(2), ConfigFieldType.DB_DROP_DOWN, vals.get(2), columnNames.get(2), fieldNames != null ? fieldNames.get(2) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(3), ConfigFieldType.DB_SELECTOR, vals.get(3), columnNames.get(3), fieldNames != null ? fieldNames.get(3) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(4), ConfigFieldType.DROP_DOWN, vals.get(4), columnNames.get(4), fieldNames != null ? fieldNames.get(4) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(5), ConfigFieldType.ELECTRONIC_FILE, vals.get(5), columnNames.get(5), fieldNames != null ? fieldNames.get(5) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(6), ConfigFieldType.HYPERLINK, vals.get(6), columnNames.get(6), fieldNames != null ? fieldNames.get(6) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(7), ConfigFieldType.LATITUDE, vals.get(7), columnNames.get(7), fieldNames != null ? fieldNames.get(7) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(8), ConfigFieldType.LONGITUDE, vals.get(8), columnNames.get(8), fieldNames != null ? fieldNames.get(8) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(9), ConfigFieldType.MEMO, vals.get(9), columnNames.get(9), fieldNames != null ? fieldNames.get(9) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(10), ConfigFieldType.NUMBER, vals.get(10), columnNames.get(10), fieldNames != null ? fieldNames.get(10) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(11), ConfigFieldType.SELECTOR, vals.get(11), columnNames.get(11), fieldNames != null ? fieldNames.get(11) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(12), ConfigFieldType.TEXT, vals.get(12), columnNames.get(12), fieldNames != null ? fieldNames.get(12) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(13), ConfigFieldType.TRACKOR_SELECTOR, vals.get(13), columnNames.get(13), fieldNames != null ? fieldNames.get(13) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(14), ConfigFieldType.WIKI, vals.get(14), columnNames.get(14), fieldNames != null ? fieldNames.get(14) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(15), ConfigFieldType.MULTI_SELECTOR, vals.get(15), columnNames.get(15), fieldNames != null ? fieldNames.get(15) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(16), ConfigFieldType.DATE_TIME, vals.get(16), columnNames.get(16), fieldNames != null ? fieldNames.get(16) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(17), ConfigFieldType.TIME, vals.get(17), columnNames.get(17), fieldNames != null ? fieldNames.get(17) : null, gridExpVals, expVals);
        editCell(gridIndex, rowIndex, columns.get(18), ConfigFieldType.TRACKOR_DROP_DOWN, vals.get(18), columnNames.get(18), fieldNames != null ? fieldNames.get(18) : null, gridExpVals, expVals);
        //CALCULATED
        //ROLLUP
        editCell(gridIndex, rowIndex, columns.get(21), ConfigFieldType.MULTI_TRACKOR_SELECTOR, vals.get(21), columnNames.get(21), fieldNames != null ? fieldNames.get(21) : null, gridExpVals, expVals);
    }

    public void editCell(Long gridIndex, int rowIndex, int columnIndex, ConfigFieldType fieldDataType, String value,
            String gridColumnId, String fieldName, Map<String, String> gridExpVals, Map<String, String> expVals) {
        Long scrollLeft = js.getGridScrollLeft(gridIndex, columnIndex);
        js.gridScrollLeft(gridIndex, scrollLeft);
        Long scrollTop = js.getGridScrollTop(gridIndex, rowIndex);
        js.gridScrollTop(gridIndex, scrollTop);

        WebElement gridCell = (WebElement) js.getGridCellByRowIndexAndColIndex(gridIndex, rowIndex, columnIndex);
        elementWait.waitElementVisible(gridCell);

        if (!ConfigFieldType.CHECKBOX.equals(fieldDataType) && !ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            element.doubleClick(gridCell);
            AbstractSeleniumCore.sleep(500L);
        }

        if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            WebElement elem = gridCell.findElement(By.tagName("input"));
            String val = (checkbox.isElementChecked(elem)) ? "YES" : "NO";
            if (!val.equals(value)) {
                checkbox.clickByElement(elem);
            }
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(fieldDataType) || ConfigFieldType.DROP_DOWN.equals(fieldDataType)
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(fieldDataType)) {
            jquery.waitLoad();
            Select sel = new Select(seleniumSettings.getWebDriver().findElement(By.name("epmDd1")));
            wait.waitListBoxLoad2(sel);
            sel.selectByVisibleText(value);
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.DB_SELECTOR.equals(fieldDataType) || ConfigFieldType.SELECTOR.equals(fieldDataType) || ConfigFieldType.TRACKOR_SELECTOR.equals(fieldDataType)) {
            selector.selectRadio(By.name("btn1"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1, value, 1L);
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(fieldDataType) || ConfigFieldType.MULTI_TRACKOR_SELECTOR.equals(fieldDataType)) {
            Actions action = new Actions(seleniumSettings.getWebDriver());
            action.moveToElement(seleniumSettings.getWebDriver().findElement(By.name("epmSelector1"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            selector.selectCheckbox(By.name("btn1"), 1, Arrays.asList(value.split(",")), 1L);
            gridExpVals.put(gridColumnId, value.replaceAll(",", ", "));
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.LATITUDE.equals(fieldDataType)) {
            //elementHelper.clickByName("epmLat1");
            if (seleniumSettings.getBrowser().equals("chrome")) {
                seleniumSettings.getWebDriver().findElement(By.name("epmLat1")).clear();
                seleniumSettings.getWebDriver().findElement(By.name("epmLat1")).sendKeys(value);
            } else if (seleniumSettings.getBrowser().equals("firefox")) {
                Actions actionObject = new Actions(seleniumSettings.getWebDriver());
                actionObject.sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,
                        Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,
                        Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT).perform();
                actionObject.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                        Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                        Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE).perform();
                actionObject.sendKeys(value).perform();
            } else {
                throw new SeleniumUnexpectedException("Not support browser[" + seleniumSettings.getBrowser() + "]");
            }
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.LONGITUDE.equals(fieldDataType)) {
            //elementHelper.clickByName("epmLong1");
            if (seleniumSettings.getBrowser().equals("chrome")) {
                seleniumSettings.getWebDriver().findElement(By.name("epmLong1")).clear();
                seleniumSettings.getWebDriver().findElement(By.name("epmLong1")).sendKeys(value);
            } else if (seleniumSettings.getBrowser().equals("firefox")) {
                Actions actionObject = new Actions(seleniumSettings.getWebDriver());
                actionObject.sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,
                        Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,
                        Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT).perform();
                actionObject.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                        Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                        Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE).perform();
                actionObject.sendKeys(value).perform();
            } else {
                throw new SeleniumUnexpectedException("Not support browser[" + seleniumSettings.getBrowser() + "]");
            }
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.MEMO.equals(fieldDataType) || ConfigFieldType.TEXT.equals(fieldDataType)) {
            seleniumSettings.getWebDriver().findElement(By.name("epmMemo1")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("epmMemo1")).sendKeys(value);
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.HYPERLINK.equals(fieldDataType)) {
            seleniumSettings.getWebDriver().findElement(By.name("epmLink1")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("epmLink1")).sendKeys(value);
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.NUMBER.equals(fieldDataType)) {
            seleniumSettings.getWebDriver().findElement(By.name("NumberField1")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("NumberField1")).sendKeys(value);
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.DATE.equals(fieldDataType) || ConfigFieldType.DATE_TIME.equals(fieldDataType)
                || ConfigFieldType.TIME.equals(fieldDataType)) {
            element.clickByName("epmDate1");
            String prevVal = seleniumSettings.getWebDriver().findElement(By.name("epmDate1")).getAttribute("value");

            Actions actionObject = new Actions(seleniumSettings.getWebDriver());
            for (int i = 0; i < prevVal.length(); i++) {
                actionObject.sendKeys(Keys.ARROW_RIGHT).perform();
            }
            for (int i = 0; i < prevVal.length(); i++) {
                actionObject.sendKeys(Keys.BACK_SPACE).perform();
            }
            actionObject.sendKeys(value).perform();

            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            element.moveToElement(gridCell);
            List<WebElement> buttons = gridCell.findElements(By.tagName("input"));
            if (buttons.size() == 3) {
                gridCell.findElement(By.id(EFILE_DELETE_BUTTON)).click();
                element.moveToElementById(AbstractSeleniumCore.BUTTON_SAVE_GRID_ID_BASE + gridIndex);
                element.moveToElement(gridCell);
            } else if (buttons.size() < 1 || 3 < buttons.size()) {
                throw new SeleniumUnexpectedException("Wrong efile buttons size");
            }
            htmlInputFile.beforeUploadOnGrid(gridIndex);
            gridCell.findElement(By.id(EFILE_EDIT_BUTTON)).click();
            String hiddenInputFile = gridCell.findElement(By.id(EFILE_EDIT_BUTTON)).getAttribute("hiddeninputfile");
            htmlInputFile.uploadOnGrid(gridIndex, hiddenInputFile, value);
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.WIKI.equals(fieldDataType)) {
            fckEditor.setValue("epmMemo1", value);
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }

            AbstractSeleniumCore.sleep(500L);

            js.selectGridCellByRowIndexAndColIndex2(gridIndex, rowIndex, columnIndex);
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    public void clearCells(Long gridIndex, int rowIndex, List<Integer> columns, Map<String, String> gridExpVals,
            Map<String, String> expVals, List<String> columnNames, List<String> fieldNames) {
        clearCell(gridIndex, rowIndex, columns.get(0), ConfigFieldType.CHECKBOX, columnNames.get(0), fieldNames != null ? fieldNames.get(0) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(1), ConfigFieldType.DATE, columnNames.get(1), fieldNames != null ? fieldNames.get(1) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(2), ConfigFieldType.DB_DROP_DOWN, columnNames.get(2), fieldNames != null ? fieldNames.get(2) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(3), ConfigFieldType.DB_SELECTOR, columnNames.get(3), fieldNames != null ? fieldNames.get(3) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(4), ConfigFieldType.DROP_DOWN, columnNames.get(4), fieldNames != null ? fieldNames.get(4) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(5), ConfigFieldType.ELECTRONIC_FILE, columnNames.get(5), fieldNames != null ? fieldNames.get(5) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(6), ConfigFieldType.HYPERLINK, columnNames.get(6), fieldNames != null ? fieldNames.get(6) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(7), ConfigFieldType.LATITUDE, columnNames.get(7), fieldNames != null ? fieldNames.get(7) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(8), ConfigFieldType.LONGITUDE, columnNames.get(8), fieldNames != null ? fieldNames.get(8) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(9), ConfigFieldType.MEMO, columnNames.get(9), fieldNames != null ? fieldNames.get(9) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(10), ConfigFieldType.NUMBER, columnNames.get(10), fieldNames != null ? fieldNames.get(10) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(11), ConfigFieldType.SELECTOR, columnNames.get(11), fieldNames != null ? fieldNames.get(11) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(12), ConfigFieldType.TEXT, columnNames.get(12), fieldNames != null ? fieldNames.get(12) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(13), ConfigFieldType.TRACKOR_SELECTOR, columnNames.get(13), fieldNames != null ? fieldNames.get(13) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(14), ConfigFieldType.WIKI, columnNames.get(14), fieldNames != null ? fieldNames.get(14) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(15), ConfigFieldType.MULTI_SELECTOR, columnNames.get(15), fieldNames != null ? fieldNames.get(15) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(16), ConfigFieldType.DATE_TIME, columnNames.get(16), fieldNames != null ? fieldNames.get(16) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(17), ConfigFieldType.TIME, columnNames.get(17), fieldNames != null ? fieldNames.get(17) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(18), ConfigFieldType.TRACKOR_DROP_DOWN, columnNames.get(18), fieldNames != null ? fieldNames.get(18) : null, gridExpVals, expVals);
        //CALCULATED
        //ROLLUP
        clearCell(gridIndex, rowIndex, columns.get(21), ConfigFieldType.MULTI_TRACKOR_SELECTOR, columnNames.get(21), fieldNames != null ? fieldNames.get(21) : null, gridExpVals, expVals);
    }

    public void clearCell(Long gridIndex, int rowIndex, int columnIndex, ConfigFieldType fieldDataType, String gridColumnId,
            String fieldName, Map<String, String> gridExpVals, Map<String, String> expVals) {
        Long scrollLeft = js.getGridScrollLeft(gridIndex, columnIndex);
        js.gridScrollLeft(gridIndex, scrollLeft);
        Long scrollTop = js.getGridScrollTop(gridIndex, rowIndex);
        js.gridScrollTop(gridIndex, scrollTop);

        WebElement gridCell = (WebElement) js.getGridCellByRowIndexAndColIndex(gridIndex, rowIndex, columnIndex);
        elementWait.waitElementVisible(gridCell);

        if (!ConfigFieldType.CHECKBOX.equals(fieldDataType) && !ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            element.doubleClick(gridCell);
            AbstractSeleniumCore.sleep(500L);
        }

        if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            WebElement elem = gridCell.findElement(By.tagName("input"));
            String val = (checkbox.isElementChecked(elem)) ? "YES" : "NO";
            if (!val.equals("NO")) {
                checkbox.clickByElement(elem);
            }
            gridExpVals.put(gridColumnId, "NO");
            if (fieldName != null) {
                expVals.put(fieldName, "NO");
            }
        } else if (ConfigFieldType.SELECTOR.equals(fieldDataType) || ConfigFieldType.TRACKOR_SELECTOR.equals(fieldDataType)) {
            seleniumSettings.getWebDriver().findElement(By.name("epmSelector1")).clear();
            Actions action = new Actions(seleniumSettings.getWebDriver());
            action.moveToElement(seleniumSettings.getWebDriver().findElement(By.name("epmSelector1"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            gridExpVals.put(gridColumnId, "");
            if (fieldName != null) {
                expVals.put(fieldName, "");
            }
        } else if (ConfigFieldType.DB_SELECTOR.equals(fieldDataType) || ConfigFieldType.MULTI_SELECTOR.equals(fieldDataType)
                || ConfigFieldType.MULTI_TRACKOR_SELECTOR.equals(fieldDataType)) {
            Actions action = new Actions(seleniumSettings.getWebDriver());
            action.moveToElement(seleniumSettings.getWebDriver().findElement(By.name("epmSelector1"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            gridExpVals.put(gridColumnId, "");
            if (fieldName != null) {
                expVals.put(fieldName, "");
            }
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            element.moveToElement(gridCell);
            List<WebElement> buttons = gridCell.findElements(By.tagName("input"));
            if (buttons.size() == 3) {
                gridCell.findElement(By.id(EFILE_DELETE_BUTTON)).click();
            } else if (buttons.size() < 1 || 3 < buttons.size()) {
                throw new SeleniumUnexpectedException("Wrong efile buttons size");
            }
            gridExpVals.put(gridColumnId, "");
            if (fieldName != null) {
                expVals.put(fieldName, "");
            }
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(fieldDataType) || ConfigFieldType.DROP_DOWN.equals(fieldDataType)
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(fieldDataType)) {
            jquery.waitLoad();
            Select sel = new Select(seleniumSettings.getWebDriver().findElement(By.name("epmDd1")));
            wait.waitListBoxLoad2(sel);
            sel.selectByVisibleText("");
            gridExpVals.put(gridColumnId, "");
            if (fieldName != null) {
                expVals.put(fieldName, "");
            }
        } else if (ConfigFieldType.LATITUDE.equals(fieldDataType)) {
            seleniumSettings.getWebDriver().findElement(By.name("epmLat1")).clear();
            gridExpVals.put(gridColumnId, "");
            if (fieldName != null) {
                expVals.put(fieldName, "");
            }
        } else if (ConfigFieldType.LONGITUDE.equals(fieldDataType)) {
            seleniumSettings.getWebDriver().findElement(By.name("epmLong1")).clear();
            gridExpVals.put(gridColumnId, "");
            if (fieldName != null) {
                expVals.put(fieldName, "");
            }
        } else if (ConfigFieldType.MEMO.equals(fieldDataType) || ConfigFieldType.TEXT.equals(fieldDataType)) {
            seleniumSettings.getWebDriver().findElement(By.name("epmMemo1")).clear();
            gridExpVals.put(gridColumnId, "");
            if (fieldName != null) {
                expVals.put(fieldName, "");
            }
        } else if (ConfigFieldType.HYPERLINK.equals(fieldDataType)) {
            seleniumSettings.getWebDriver().findElement(By.name("epmLink1")).clear();
            gridExpVals.put(gridColumnId, "");
            if (fieldName != null) {
                expVals.put(fieldName, "");
            }
        } else if (ConfigFieldType.NUMBER.equals(fieldDataType)) {
            seleniumSettings.getWebDriver().findElement(By.name("NumberField1")).clear();
            gridExpVals.put(gridColumnId, "");
            if (fieldName != null) {
                expVals.put(fieldName, "");
            }
        } else if (ConfigFieldType.DATE.equals(fieldDataType) || ConfigFieldType.DATE_TIME.equals(fieldDataType)
                || ConfigFieldType.TIME.equals(fieldDataType)) {
            seleniumSettings.getWebDriver().findElement(By.name("epmDate1")).clear();
            gridExpVals.put(gridColumnId, "");
            if (fieldName != null) {
                expVals.put(fieldName, "");
            }
        } else if (ConfigFieldType.WIKI.equals(fieldDataType)) {
            fckEditor.setValue("epmMemo1", "");
            gridExpVals.put(gridColumnId, "");
            if (fieldName != null) {
                expVals.put(fieldName, "");
            }

            AbstractSeleniumCore.sleep(500L);

            js.selectGridCellByRowIndexAndColIndex2(gridIndex, rowIndex, columnIndex);
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    public void rightClickCell(Long gridIndex, int rowIndex, int columnIndex) {
        Long scrollLeft = js.getGridScrollLeft(gridIndex, columnIndex);
        js.gridScrollLeft(gridIndex, scrollLeft);
        Long scrollTop = js.getGridScrollTop(gridIndex, rowIndex);
        js.gridScrollTop(gridIndex, scrollTop);

        WebElement gridCell = (WebElement) js.getGridCellByRowIndexAndColIndex(gridIndex, rowIndex, columnIndex);
        elementWait.waitElementVisible(gridCell);

        Actions action = new Actions(seleniumSettings.getWebDriver());
        action.contextClick(gridCell).perform();
    }

    public void checkCellsEnabled(Long gridIndex, int rowIndex, List<Integer> columns) {
        checkCellEnabled(gridIndex, rowIndex, columns.get(0), ConfigFieldType.CHECKBOX);
        checkCellEnabled(gridIndex, rowIndex, columns.get(1), ConfigFieldType.DATE);
        checkCellEnabled(gridIndex, rowIndex, columns.get(2), ConfigFieldType.DB_DROP_DOWN);
        checkCellEnabled(gridIndex, rowIndex, columns.get(3), ConfigFieldType.DB_SELECTOR);
        checkCellEnabled(gridIndex, rowIndex, columns.get(4), ConfigFieldType.DROP_DOWN);
        checkCellEnabled(gridIndex, rowIndex, columns.get(5), ConfigFieldType.ELECTRONIC_FILE);
        checkCellEnabled(gridIndex, rowIndex, columns.get(6), ConfigFieldType.HYPERLINK);
        checkCellEnabled(gridIndex, rowIndex, columns.get(7), ConfigFieldType.LATITUDE);
        checkCellEnabled(gridIndex, rowIndex, columns.get(8), ConfigFieldType.LONGITUDE);
        checkCellEnabled(gridIndex, rowIndex, columns.get(9), ConfigFieldType.MEMO);
        checkCellEnabled(gridIndex, rowIndex, columns.get(10), ConfigFieldType.NUMBER);
        checkCellEnabled(gridIndex, rowIndex, columns.get(11), ConfigFieldType.SELECTOR);
        checkCellEnabled(gridIndex, rowIndex, columns.get(12), ConfigFieldType.TEXT);
        checkCellEnabled(gridIndex, rowIndex, columns.get(13), ConfigFieldType.TRACKOR_SELECTOR);
        checkCellEnabled(gridIndex, rowIndex, columns.get(14), ConfigFieldType.WIKI);
        checkCellEnabled(gridIndex, rowIndex, columns.get(15), ConfigFieldType.MULTI_SELECTOR);
        checkCellEnabled(gridIndex, rowIndex, columns.get(16), ConfigFieldType.DATE_TIME);
        checkCellEnabled(gridIndex, rowIndex, columns.get(17), ConfigFieldType.TIME);
        checkCellEnabled(gridIndex, rowIndex, columns.get(18), ConfigFieldType.TRACKOR_DROP_DOWN);
        //CALCULATED
        //ROLLUP
        checkCellEnabled(gridIndex, rowIndex, columns.get(21), ConfigFieldType.MULTI_TRACKOR_SELECTOR);
    }

    public void checkCellEnabled(Long gridIndex, int rowIndex, int columnIndex, ConfigFieldType fieldDataType) {
        Long scrollLeft = js.getGridScrollLeft(gridIndex, columnIndex);
        js.gridScrollLeft(gridIndex, scrollLeft);
        Long scrollTop = js.getGridScrollTop(gridIndex, rowIndex);
        js.gridScrollTop(gridIndex, scrollTop);

        WebElement gridCell = (WebElement) js.getGridCellByRowIndexAndColIndex(gridIndex, rowIndex, columnIndex);
        elementWait.waitElementVisible(gridCell);

        if (!ConfigFieldType.CHECKBOX.equals(fieldDataType) && !ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            element.doubleClick(gridCell);
            AbstractSeleniumCore.sleep(500L);
        }

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            WebElement elem = gridCell.findElement(By.tagName("input"));
            assertElement.assertElementEnabled(elem);
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(fieldDataType) || ConfigFieldType.DROP_DOWN.equals(fieldDataType) 
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmDd1")).size(), 1);
        } else if (ConfigFieldType.DB_SELECTOR.equals(fieldDataType) || ConfigFieldType.SELECTOR.equals(fieldDataType) 
                || ConfigFieldType.TRACKOR_SELECTOR.equals(fieldDataType) || ConfigFieldType.MULTI_SELECTOR.equals(fieldDataType)
                || ConfigFieldType.MULTI_TRACKOR_SELECTOR.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmSelector1")).size(), 1);
        } else if (ConfigFieldType.LATITUDE.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmLat1")).size(), 1); 
        } else if (ConfigFieldType.LONGITUDE.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmLong1")).size(), 1);
        } else if (ConfigFieldType.MEMO.equals(fieldDataType) || ConfigFieldType.TEXT.equals(fieldDataType) || ConfigFieldType.WIKI.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmMemo1")).size(), 1);
        } else if (ConfigFieldType.HYPERLINK.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmLink1")).size(), 1);
        } else if (ConfigFieldType.NUMBER.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("NumberField1")).size(), 1);
        } else if (ConfigFieldType.DATE.equals(fieldDataType) || ConfigFieldType.DATE_TIME.equals(fieldDataType) || ConfigFieldType.TIME.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmDate1")).size(), 1);
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            element.moveToElement(gridCell);
            Assert.assertEquals(gridCell.findElements(By.id(EFILE_EDIT_BUTTON)).size(), 1);
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        js.selectGridCellByRowIndexAndColIndex2(gridIndex, rowIndex, columnIndex);
    }

    public void checkCellsDisabled(Long gridIndex, int rowIndex, List<Integer> columns) {
        checkCellDisabled(gridIndex, rowIndex, columns.get(0), ConfigFieldType.CHECKBOX);
        checkCellDisabled(gridIndex, rowIndex, columns.get(1), ConfigFieldType.DATE);
        checkCellDisabled(gridIndex, rowIndex, columns.get(2), ConfigFieldType.DB_DROP_DOWN);
        checkCellDisabled(gridIndex, rowIndex, columns.get(3), ConfigFieldType.DB_SELECTOR);
        checkCellDisabled(gridIndex, rowIndex, columns.get(4), ConfigFieldType.DROP_DOWN);
        checkCellDisabled(gridIndex, rowIndex, columns.get(5), ConfigFieldType.ELECTRONIC_FILE);
        checkCellDisabled(gridIndex, rowIndex, columns.get(6), ConfigFieldType.HYPERLINK);
        checkCellDisabled(gridIndex, rowIndex, columns.get(7), ConfigFieldType.LATITUDE);
        checkCellDisabled(gridIndex, rowIndex, columns.get(8), ConfigFieldType.LONGITUDE);
        checkCellDisabled(gridIndex, rowIndex, columns.get(9), ConfigFieldType.MEMO);
        checkCellDisabled(gridIndex, rowIndex, columns.get(10), ConfigFieldType.NUMBER);
        checkCellDisabled(gridIndex, rowIndex, columns.get(11), ConfigFieldType.SELECTOR);
        checkCellDisabled(gridIndex, rowIndex, columns.get(12), ConfigFieldType.TEXT);
        checkCellDisabled(gridIndex, rowIndex, columns.get(13), ConfigFieldType.TRACKOR_SELECTOR);
        checkCellDisabled(gridIndex, rowIndex, columns.get(14), ConfigFieldType.WIKI);
        checkCellDisabled(gridIndex, rowIndex, columns.get(15), ConfigFieldType.MULTI_SELECTOR);
        checkCellDisabled(gridIndex, rowIndex, columns.get(16), ConfigFieldType.DATE_TIME);
        checkCellDisabled(gridIndex, rowIndex, columns.get(17), ConfigFieldType.TIME);
        checkCellDisabled(gridIndex, rowIndex, columns.get(18), ConfigFieldType.TRACKOR_DROP_DOWN);
        //CALCULATED
        //ROLLUP
        checkCellDisabled(gridIndex, rowIndex, columns.get(21), ConfigFieldType.MULTI_TRACKOR_SELECTOR);
    }

    public void checkCellDisabled(Long gridIndex, int rowIndex, int columnIndex, ConfigFieldType fieldDataType) {
        Long scrollLeft = js.getGridScrollLeft(gridIndex, columnIndex);
        js.gridScrollLeft(gridIndex, scrollLeft);
        Long scrollTop = js.getGridScrollTop(gridIndex, rowIndex);
        js.gridScrollTop(gridIndex, scrollTop);

        WebElement gridCell = (WebElement) js.getGridCellByRowIndexAndColIndex(gridIndex, rowIndex, columnIndex);
        elementWait.waitElementVisible(gridCell);

        if (!ConfigFieldType.CHECKBOX.equals(fieldDataType) && !ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            element.doubleClick(gridCell);
            AbstractSeleniumCore.sleep(500L);
        }

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            WebElement elem = gridCell.findElement(By.tagName("input"));
            assertElement.assertElementDisabled(elem);
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(fieldDataType) || ConfigFieldType.DROP_DOWN.equals(fieldDataType) 
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmDd1")).size(), 0);
        } else if (ConfigFieldType.DB_SELECTOR.equals(fieldDataType) || ConfigFieldType.SELECTOR.equals(fieldDataType) 
                || ConfigFieldType.TRACKOR_SELECTOR.equals(fieldDataType) || ConfigFieldType.MULTI_SELECTOR.equals(fieldDataType)
                || ConfigFieldType.MULTI_TRACKOR_SELECTOR.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmSelector1")).size(), 0);
        } else if (ConfigFieldType.LATITUDE.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmLat1")).size(), 0); 
        } else if (ConfigFieldType.LONGITUDE.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmLong1")).size(), 0);
        } else if (ConfigFieldType.MEMO.equals(fieldDataType) || ConfigFieldType.TEXT.equals(fieldDataType) || ConfigFieldType.WIKI.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmMemo1")).size(), 0);
        } else if (ConfigFieldType.HYPERLINK.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmLink1")).size(), 0);
        } else if (ConfigFieldType.NUMBER.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("NumberField1")).size(), 0);
        } else if (ConfigFieldType.DATE.equals(fieldDataType) || ConfigFieldType.DATE_TIME.equals(fieldDataType) || ConfigFieldType.TIME.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmDate1")).size(), 0);
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            element.moveToElement(gridCell);
            Assert.assertEquals(gridCell.findElements(By.id(EFILE_EDIT_BUTTON)).size(), 0);
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        js.selectGridCellByRowIndexAndColIndex2(gridIndex, rowIndex, columnIndex);
    }

    public void checkColumnsExist(Long gridIndex, String prefix) {
        checkColumnExist(gridIndex, prefix + ":Calculated");
        checkColumnExist(gridIndex, prefix + ":Checkbox");
        checkColumnExist(gridIndex, prefix + ":Date");
        checkColumnExist(gridIndex, prefix + ":Date/Time");
        checkColumnExist(gridIndex, prefix + ":DB Drop-Down");
        checkColumnExist(gridIndex, prefix + ":DB Selector");
        checkColumnExist(gridIndex, prefix + ":Drop-Down");
        checkColumnExist(gridIndex, prefix + ":Electronic File");
        checkColumnExist(gridIndex, prefix + ":Hyperlink");
        checkColumnExist(gridIndex, prefix + ":Latitude");
        checkColumnExist(gridIndex, prefix + ":Longitude");
        checkColumnExist(gridIndex, prefix + ":Memo");
        checkColumnExist(gridIndex, prefix + ":MultiSelector");
        checkColumnExist(gridIndex, prefix + ":Number");
        checkColumnExist(gridIndex, prefix + ":Rollup");
        checkColumnExist(gridIndex, prefix + ":Selector");
        checkColumnExist(gridIndex, prefix + ":Text");
        checkColumnExist(gridIndex, prefix + ":Time");
        checkColumnExist(gridIndex, prefix + ":Trackor Drop-Down");
        checkColumnExist(gridIndex, prefix + ":Trackor Selector");
        checkColumnExist(gridIndex, prefix + ":Wiki");
        checkColumnExist(gridIndex, prefix + ":Multi Trackor Selector");
    }

    public void checkColumnExist(Long gridIndex, String columnLabel) {
        Assert.assertEquals(js.existColumnIndexByLabel(gridIndex, columnLabel), true, "Grid not have column");
    }

    public void checkColumnsNotExist(Long gridIndex, String prefix) {
        checkColumnNotExist(gridIndex, prefix + ":Calculated");
        checkColumnNotExist(gridIndex, prefix + ":Checkbox");
        checkColumnNotExist(gridIndex, prefix + ":Date");
        checkColumnNotExist(gridIndex, prefix + ":Date/Time");
        checkColumnNotExist(gridIndex, prefix + ":DB Drop-Down");
        checkColumnNotExist(gridIndex, prefix + ":DB Selector");
        checkColumnNotExist(gridIndex, prefix + ":Drop-Down");
        checkColumnNotExist(gridIndex, prefix + ":Electronic File");
        checkColumnNotExist(gridIndex, prefix + ":Hyperlink");
        checkColumnNotExist(gridIndex, prefix + ":Latitude");
        checkColumnNotExist(gridIndex, prefix + ":Longitude");
        checkColumnNotExist(gridIndex, prefix + ":Memo");
        checkColumnNotExist(gridIndex, prefix + ":MultiSelector");
        checkColumnNotExist(gridIndex, prefix + ":Number");
        checkColumnNotExist(gridIndex, prefix + ":Rollup");
        checkColumnNotExist(gridIndex, prefix + ":Selector");
        checkColumnNotExist(gridIndex, prefix + ":Text");
        checkColumnNotExist(gridIndex, prefix + ":Time");
        checkColumnNotExist(gridIndex, prefix + ":Trackor Drop-Down");
        checkColumnNotExist(gridIndex, prefix + ":Trackor Selector");
        checkColumnNotExist(gridIndex, prefix + ":Wiki");
        checkColumnNotExist(gridIndex, prefix + ":Multi Trackor Selector");
    }

    public void checkColumnNotExist(Long gridIndex, String columnLabel) {
        Assert.assertEquals(js.existColumnIndexByLabel(gridIndex, columnLabel), false, "Grid have column");
    }

    public void checkColumnNotExist(Long gridIndex, String columnLabel, String columnLabel2) {
        Assert.assertEquals(js.existColumnIndexByLabel(gridIndex, columnLabel, columnLabel2), false, "Grid have column");
    }

    public void checkFieldsExist(List<String> fieldIds) {
        checkFieldExist(fieldIds.get(0)); //CHECKBOX
        checkFieldExist(fieldIds.get(1)); //DATE
        checkFieldExist(fieldIds.get(2)); //DB_DROP_DOWN
        checkFieldExist(fieldIds.get(3) + "_disp"); //DB_SELECTOR
        checkFieldExist(fieldIds.get(4)); //DROP_DOWN
        checkFieldExist(fieldIds.get(5) + "_disp"); //ELECTRONIC_FILE
        checkFieldExist(fieldIds.get(6)); //HYPERLINK
        checkFieldExist(fieldIds.get(7)); //LATITUDE
        checkFieldExist(fieldIds.get(8)); //LONGITUDE
        checkFieldExist(fieldIds.get(9)); //MEMO
        checkFieldExist(fieldIds.get(10)); //NUMBER
        checkFieldExist(fieldIds.get(11) + "_disp"); //SELECTOR
        checkFieldExist(fieldIds.get(12)); //TEXT
        checkFieldExist(fieldIds.get(13) + "_disp"); //TRACKOR_SELECTOR
        checkFieldExist(fieldIds.get(14)); //WIKI
        checkFieldExist(fieldIds.get(15) + "_disp"); //MULTI_SELECTOR
        checkFieldExist(fieldIds.get(16)); //DATE_TIME
        checkFieldExist(fieldIds.get(17)); //TIME
        checkFieldExist(fieldIds.get(18)); //TRACKOR_DROPDOWN
        checkFieldExist(fieldIds.get(19)); //CALCULATED
        if (fieldIds.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            checkFieldExist(fieldIds.get(20)); //ROLLUP
        }
        checkFieldExist(fieldIds.get(21) + "_disp"); //MULTI_TRACKOR_SELECTOR
    }

    public void checkFieldExist(String fieldId) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name(fieldId)).size(), 1);
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void checkFieldsNotExist(List<String> fieldIds) {
        checkFieldNotExist(fieldIds.get(0)); //CHECKBOX
        checkFieldNotExist(fieldIds.get(1)); //DATE
        checkFieldNotExist(fieldIds.get(2)); //DB_DROP_DOWN
        checkFieldNotExist(fieldIds.get(3) + "_disp"); //DB_SELECTOR
        checkFieldNotExist(fieldIds.get(4)); //DROP_DOWN
        checkFieldNotExist(fieldIds.get(5) + "_disp"); //ELECTRONIC_FILE
        checkFieldNotExist(fieldIds.get(6)); //HYPERLINK
        checkFieldNotExist(fieldIds.get(7)); //LATITUDE
        checkFieldNotExist(fieldIds.get(8)); //LONGITUDE
        checkFieldNotExist(fieldIds.get(9)); //MEMO
        checkFieldNotExist(fieldIds.get(10)); //NUMBER
        checkFieldNotExist(fieldIds.get(11) + "_disp"); //SELECTOR
        checkFieldNotExist(fieldIds.get(12)); //TEXT
        checkFieldNotExist(fieldIds.get(13) + "_disp"); //TRACKOR_SELECTOR
        checkFieldNotExist(fieldIds.get(14)); //WIKI
        checkFieldNotExist(fieldIds.get(15) + "_disp"); //MULTI_SELECTOR
        checkFieldNotExist(fieldIds.get(16)); //DATE_TIME
        checkFieldNotExist(fieldIds.get(17)); //TIME
        checkFieldNotExist(fieldIds.get(18)); //TRACKOR_DROPDOWN
        checkFieldNotExist(fieldIds.get(19)); //CALCULATED
        if (fieldIds.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            checkFieldNotExist(fieldIds.get(20)); //ROLLUP
        }
        checkFieldNotExist(fieldIds.get(21)); //MULTI_TRACKOR_SELECTOR
    }

    public void checkFieldNotExist(String fieldId) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name(fieldId)).size(), 0);
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void checkFieldsEnabled(List<String> fieldNames, int elementPosition) {
        checkFieldEnabled(ConfigFieldType.CHECKBOX, fieldNames.get(0), elementPosition);
        checkFieldEnabled(ConfigFieldType.DATE, fieldNames.get(1), elementPosition);
        checkFieldEnabled(ConfigFieldType.DB_DROP_DOWN, fieldNames.get(2), elementPosition);
        checkFieldEnabled(ConfigFieldType.DB_SELECTOR, fieldNames.get(3), elementPosition);
        checkFieldEnabled(ConfigFieldType.DROP_DOWN, fieldNames.get(4), elementPosition);
        checkFieldEnabled(ConfigFieldType.ELECTRONIC_FILE, fieldNames.get(5), elementPosition);
        checkFieldEnabled(ConfigFieldType.HYPERLINK, fieldNames.get(6), elementPosition);
        checkFieldEnabled(ConfigFieldType.LATITUDE, fieldNames.get(7), elementPosition);
        checkFieldEnabled(ConfigFieldType.LONGITUDE, fieldNames.get(8), elementPosition);
        checkFieldEnabled(ConfigFieldType.MEMO, fieldNames.get(9), elementPosition);
        checkFieldEnabled(ConfigFieldType.NUMBER, fieldNames.get(10), elementPosition);
        checkFieldEnabled(ConfigFieldType.SELECTOR, fieldNames.get(11), elementPosition);
        checkFieldEnabled(ConfigFieldType.TEXT, fieldNames.get(12), elementPosition);
        checkFieldEnabled(ConfigFieldType.TRACKOR_SELECTOR, fieldNames.get(13), elementPosition);
        checkFieldEnabled(ConfigFieldType.WIKI, fieldNames.get(14), elementPosition);
        checkFieldEnabled(ConfigFieldType.MULTI_SELECTOR, fieldNames.get(15), elementPosition);
        checkFieldEnabled(ConfigFieldType.DATE_TIME, fieldNames.get(16), elementPosition);
        checkFieldEnabled(ConfigFieldType.TIME, fieldNames.get(17), elementPosition);
        checkFieldEnabled(ConfigFieldType.TRACKOR_DROP_DOWN, fieldNames.get(18), elementPosition);
        //CALCULATED
        //ROLLUP
        checkFieldEnabled(ConfigFieldType.MULTI_TRACKOR_SELECTOR, fieldNames.get(21), elementPosition);
    }

    public void checkFieldEnabled(ConfigFieldType configFieldType, String fieldName, int elementPosition) {
        if (ConfigFieldType.CHECKBOX.equals(configFieldType) || ConfigFieldType.DB_DROP_DOWN.equals(configFieldType)
                || ConfigFieldType.DROP_DOWN.equals(configFieldType) || ConfigFieldType.HYPERLINK.equals(configFieldType)
                || ConfigFieldType.LATITUDE.equals(configFieldType) || ConfigFieldType.LONGITUDE.equals(configFieldType)
                || ConfigFieldType.MEMO.equals(configFieldType) || ConfigFieldType.NUMBER.equals(configFieldType)
                || ConfigFieldType.TEXT.equals(configFieldType) || ConfigFieldType.WIKI.equals(configFieldType)
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(configFieldType)) {
            checkFieldEnabled(fieldName, elementPosition);
        } else if (ConfigFieldType.DATE.equals(configFieldType) || ConfigFieldType.DATE_TIME.equals(configFieldType)
                || ConfigFieldType.TIME.equals(configFieldType)) {
            checkFieldEnabled(fieldName, elementPosition);
            checkFieldEnabled(fieldName + "_but", elementPosition);
        } else if (ConfigFieldType.SELECTOR.equals(configFieldType) || ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldType)) {
            checkFieldEnabled(fieldName + "_disp", elementPosition);
            checkFieldEnabled(fieldName + "_but", elementPosition);
        } else if (ConfigFieldType.DB_SELECTOR.equals(configFieldType) || ConfigFieldType.ELECTRONIC_FILE.equals(configFieldType)
                || ConfigFieldType.MULTI_SELECTOR.equals(configFieldType) || ConfigFieldType.MULTI_TRACKOR_SELECTOR.equals(configFieldType)) {
            checkFieldEnabled(fieldName + "_but", elementPosition);
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    private void checkFieldEnabled(String fieldName, int elementPosition) {
        WebElement webElement;
        if (elementPosition > 1) {
            String idx = getLastFieldIndex(fieldName, elementPosition);
            webElement = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx));
        } else {
            webElement = seleniumSettings.getWebDriver().findElement(By.name(fieldName));
        }

        assertElement.assertElementEnabled(webElement);
    }

    public void checkFieldsDisabled(List<String> fieldNames, int elementPosition) {
        checkFieldDisabled(ConfigFieldType.CHECKBOX, fieldNames.get(0), elementPosition);
        checkFieldDisabled(ConfigFieldType.DATE, fieldNames.get(1), elementPosition);
        checkFieldDisabled(ConfigFieldType.DB_DROP_DOWN, fieldNames.get(2), elementPosition);
        checkFieldDisabled(ConfigFieldType.DB_SELECTOR, fieldNames.get(3), elementPosition);
        checkFieldDisabled(ConfigFieldType.DROP_DOWN, fieldNames.get(4), elementPosition);
        checkFieldDisabled(ConfigFieldType.ELECTRONIC_FILE, fieldNames.get(5), elementPosition);
        checkFieldDisabled(ConfigFieldType.HYPERLINK, fieldNames.get(6), elementPosition);
        checkFieldDisabled(ConfigFieldType.LATITUDE, fieldNames.get(7), elementPosition);
        checkFieldDisabled(ConfigFieldType.LONGITUDE, fieldNames.get(8), elementPosition);
        checkFieldDisabled(ConfigFieldType.MEMO, fieldNames.get(9), elementPosition);
        checkFieldDisabled(ConfigFieldType.NUMBER, fieldNames.get(10), elementPosition);
        checkFieldDisabled(ConfigFieldType.SELECTOR, fieldNames.get(11), elementPosition);
        checkFieldDisabled(ConfigFieldType.TEXT, fieldNames.get(12), elementPosition);
        checkFieldDisabled(ConfigFieldType.TRACKOR_SELECTOR, fieldNames.get(13), elementPosition);
        checkFieldDisabled(ConfigFieldType.WIKI, fieldNames.get(14), elementPosition);
        checkFieldDisabled(ConfigFieldType.MULTI_SELECTOR, fieldNames.get(15), elementPosition);
        checkFieldDisabled(ConfigFieldType.DATE_TIME, fieldNames.get(16), elementPosition);
        checkFieldDisabled(ConfigFieldType.TIME, fieldNames.get(17), elementPosition);
        checkFieldDisabled(ConfigFieldType.TRACKOR_DROP_DOWN, fieldNames.get(18), elementPosition);
        //CALCULATED
        //ROLLUP
        checkFieldDisabled(ConfigFieldType.MULTI_TRACKOR_SELECTOR, fieldNames.get(21), elementPosition);
    }

    public void checkFieldDisabled(ConfigFieldType configFieldType, String fieldName, int elementPosition) {
        if (ConfigFieldType.CHECKBOX.equals(configFieldType) || ConfigFieldType.DB_DROP_DOWN.equals(configFieldType)
                || ConfigFieldType.DROP_DOWN.equals(configFieldType) || ConfigFieldType.HYPERLINK.equals(configFieldType)
                || ConfigFieldType.LATITUDE.equals(configFieldType) || ConfigFieldType.LONGITUDE.equals(configFieldType)
                || ConfigFieldType.MEMO.equals(configFieldType) || ConfigFieldType.NUMBER.equals(configFieldType)
                || ConfigFieldType.TEXT.equals(configFieldType) || ConfigFieldType.WIKI.equals(configFieldType)
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(configFieldType)) {
            checkFieldDisabled(fieldName, elementPosition);
        } else if (ConfigFieldType.DATE.equals(configFieldType) || ConfigFieldType.DATE_TIME.equals(configFieldType)
                || ConfigFieldType.TIME.equals(configFieldType)) {
            checkFieldDisabled(fieldName, elementPosition);
            checkFieldDisabled(fieldName + "_but", elementPosition);
        } else if (ConfigFieldType.SELECTOR.equals(configFieldType) || ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldType)) {
            checkFieldDisabled(fieldName + "_disp", elementPosition);
            checkFieldDisabled(fieldName + "_but", elementPosition);
        } else if (ConfigFieldType.DB_SELECTOR.equals(configFieldType) || ConfigFieldType.ELECTRONIC_FILE.equals(configFieldType)
                || ConfigFieldType.MULTI_SELECTOR.equals(configFieldType) || ConfigFieldType.MULTI_TRACKOR_SELECTOR.equals(configFieldType)) {
            checkFieldDisabled(fieldName + "_but", elementPosition);
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType");
        }
    }

    private void checkFieldDisabled(String fieldName, int elementPosition) {
        WebElement webElement;
        if (elementPosition > 1) {
            String idx = getLastFieldIndex(fieldName, elementPosition);
            webElement = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx));
        } else {
            webElement = seleniumSettings.getWebDriver().findElement(By.name(fieldName));
        }

        assertElement.assertElementDisabled(webElement);
    }

}
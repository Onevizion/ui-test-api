package com.onevizion.uitest.api.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.jquery.JqueryWait;
import com.onevizion.uitest.api.vo.ConfigFieldType;

@Component
public class Tb {

    private final Logger logger = LoggerFactory.getLogger(Tb.class);

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Js js;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Wait wait;

    @Resource
    private FieldHistory fieldHistory;

    @Resource
    private PsSelector psSelector;

    @Resource
    private Element element;

    @Resource
    private Checkbox checkbox;

    @Resource
    private ElementWait elementWait;

    @Resource
    private ElementJs elementJs;

    @Resource
    private JqueryWait jqueryWait;

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

    public Long getColumnCount(Long gridIdx) {
        Long actualColumnsCnt = 0L;
        Long columnsCnt = js.getGridColumnsCount(gridIdx);
        for (long i = 0; i < columnsCnt; i++) {
            if (!js.isGridColumnHidden(gridIdx, i) && !js.getGridColIdByIndex(gridIdx, i).equals("-1")) {
                actualColumnsCnt = actualColumnsCnt + 1L;
            }
        }
        return actualColumnsCnt;
    }

    public void editFields(List<String> vals, List<String> fieldNames, List<String> columnNames, Map<String, String> expVals,
            Map<String, String> gridExpVals, int elementsPosition) {
        editField(ConfigFieldType.CHECKBOX, vals.get(0), columnNames != null ? columnNames.get(0) : null, fieldNames.get(0), expVals, gridExpVals, elementsPosition);
        editField(ConfigFieldType.DATE, vals.get(1), columnNames != null ? columnNames.get(1) : null, fieldNames.get(1), expVals, gridExpVals, elementsPosition);
        editField(ConfigFieldType.DB_DROP_DOWN, vals.get(2), columnNames != null ? columnNames.get(2) : null, fieldNames.get(2), expVals, gridExpVals, elementsPosition);
        editField(ConfigFieldType.DB_SELECTOR, vals.get(3), columnNames != null ? columnNames.get(3) : null, fieldNames.get(3), expVals, gridExpVals, elementsPosition);
        editField(ConfigFieldType.DROP_DOWN, vals.get(4), columnNames != null ? columnNames.get(4) : null, fieldNames.get(4), expVals, gridExpVals, elementsPosition);
        if (vals.get(5) != null && !vals.get(5).equals("")) {
            editField(ConfigFieldType.ELECTRONIC_FILE, vals.get(5), columnNames != null ? columnNames.get(5) : null, fieldNames.get(5), expVals, gridExpVals, elementsPosition);
        } else {
            clearField(ConfigFieldType.ELECTRONIC_FILE, fieldNames.get(5), columnNames != null ? columnNames.get(5) : null, expVals, gridExpVals, elementsPosition);
        }
        editField(ConfigFieldType.HYPERLINK, vals.get(6), columnNames != null ? columnNames.get(6) : null, fieldNames.get(6), expVals, gridExpVals, elementsPosition);
        editField(ConfigFieldType.LATITUDE, vals.get(7), columnNames != null ? columnNames.get(7) : null, fieldNames.get(7), expVals, gridExpVals, elementsPosition);
        editField(ConfigFieldType.LONGITUDE, vals.get(8), columnNames != null ? columnNames.get(8) : null, fieldNames.get(8), expVals, gridExpVals, elementsPosition);
        editField(ConfigFieldType.MEMO, vals.get(9), columnNames != null ? columnNames.get(9) : null, fieldNames.get(9), expVals, gridExpVals, elementsPosition);
        if (fieldNames.get(15) != null) {
            editField(ConfigFieldType.MULTI_SELECTOR, vals.get(15), columnNames != null ? columnNames.get(15) : null, fieldNames.get(15), expVals, gridExpVals, elementsPosition);
        }
        editField(ConfigFieldType.NUMBER, vals.get(10), columnNames != null ? columnNames.get(10) : null, fieldNames.get(10), expVals, gridExpVals, elementsPosition);
        editField(ConfigFieldType.SELECTOR, vals.get(11), columnNames != null ? columnNames.get(11) : null, fieldNames.get(11), expVals, gridExpVals, elementsPosition);
        editField(ConfigFieldType.TEXT, vals.get(12), columnNames != null ? columnNames.get(12) : null, fieldNames.get(12), expVals, gridExpVals, elementsPosition);
        editField(ConfigFieldType.TRACKOR_SELECTOR, vals.get(13), columnNames != null ? columnNames.get(13) : null, fieldNames.get(13), expVals, gridExpVals, elementsPosition);
        editField(ConfigFieldType.WIKI, vals.get(14), columnNames != null ? columnNames.get(14) : null, fieldNames.get(14), expVals, gridExpVals, elementsPosition);
        editField(ConfigFieldType.DATE_TIME, vals.get(16), columnNames != null ? columnNames.get(16) : null, fieldNames.get(16), expVals, gridExpVals, elementsPosition);
        editField(ConfigFieldType.TIME, vals.get(17), columnNames != null ? columnNames.get(17) : null, fieldNames.get(17), expVals, gridExpVals, elementsPosition);
        editField(ConfigFieldType.TRACKOR_DROP_DOWN, vals.get(18), columnNames != null ? columnNames.get(18) : null, fieldNames.get(18), expVals, gridExpVals, elementsPosition);
    }

    public void editField(ConfigFieldType fieldDataType, String value, String gridColumnId,
            String fieldName, Map<String, String> expVals, Map<String, String> gridExpVals, int elementPosition) {
        Actions action = new Actions(seleniumSettings.getWebDriver());

        if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(fieldName, elementPosition);
                String actualVal = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx)).isSelected() ? "YES" : "NO";
                if (actualVal != value) {
                    WebElement checkboxElement = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx));
                    WebElement newCheckbox = checkbox.findLabelByElement(checkboxElement);
                    element.click(newCheckbox);
                }
            } else {
                String actualVal = seleniumSettings.getWebDriver().findElement(By.name(fieldName)).isSelected() ? "YES" : "NO";
                if (actualVal != value) {
                    WebElement checkboxElement = seleniumSettings.getWebDriver().findElement(By.name(fieldName));
                    WebElement newCheckbox = checkbox.findLabelByElement(checkboxElement);
                    element.click(newCheckbox);
                }
            }
            expVals.put(fieldName, value);
            if (gridColumnId != null) {
                gridExpVals.put(gridColumnId, value);
            }
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(fieldDataType) || ConfigFieldType.DROP_DOWN.equals(fieldDataType)
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(fieldDataType)) {
            if (elementPosition > 1) {
                element.moveToElementById("idx" + getLastFieldIndex(fieldName, elementPosition));
                new Select(seleniumSettings.getWebDriver().findElement(By.id("idx" + getLastFieldIndex(fieldName, elementPosition)))).selectByVisibleText(value);
            } else {
                element.moveToElementByName(fieldName);
                new Select(seleniumSettings.getWebDriver().findElement(By.name(fieldName))).selectByVisibleText(value);
            }
            expVals.put(fieldName, value);
            if (gridColumnId != null) {
                gridExpVals.put(gridColumnId, value);
            }
        } else if (ConfigFieldType.TEXT.equals(fieldDataType) || ConfigFieldType.NUMBER.equals(fieldDataType)
                || ConfigFieldType.MEMO.equals(fieldDataType) || ConfigFieldType.HYPERLINK.equals(fieldDataType)
                || ConfigFieldType.DATE.equals(fieldDataType) || ConfigFieldType.DATE_TIME.equals(fieldDataType)
                || ConfigFieldType.TIME.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(fieldName, elementPosition);
                element.moveToElementById("idx" + idx);
                seleniumSettings.getWebDriver().findElement(By.id("idx" + idx)).clear();
                seleniumSettings.getWebDriver().findElement(By.id("idx" + idx)).sendKeys(value);
            } else {
                element.moveToElementByName(fieldName);
                seleniumSettings.getWebDriver().findElement(By.name(fieldName)).clear();
                seleniumSettings.getWebDriver().findElement(By.name(fieldName)).sendKeys(value);
            }
            expVals.put(fieldName, value);
            if (gridColumnId != null) {
                gridExpVals.put(gridColumnId, value);
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
            expVals.put(fieldName, value);
            if (gridColumnId != null) {
                gridExpVals.put(gridColumnId, value);
            }
        } else if (ConfigFieldType.DB_SELECTOR.equals(fieldDataType) || ConfigFieldType.SELECTOR.equals(fieldDataType) || ConfigFieldType.TRACKOR_SELECTOR.equals(fieldDataType)) {
            if (elementPosition > 1) {
                By btnOpen = By.id("idx" + getLastFieldIndex(fieldName, elementPosition) + "_but");
                element.moveToElementById("idx" + getLastFieldIndex(fieldName, elementPosition) + "_but");
                psSelector.selectSpecificValue(btnOpen, By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, value, 1L);
            } else {
                element.moveToElementByName(fieldName + "_but");
                psSelector.selectSpecificValue(By.name(fieldName + "_but"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, value, 1L);
            }
            expVals.put(fieldName, value);
            if (gridColumnId != null) {
                gridExpVals.put(gridColumnId, value);
            }
            if (ConfigFieldType.TRACKOR_SELECTOR.equals(fieldDataType)) {
                //TODO begin remove this code
                AbstractSeleniumCore.sleep(1000L);
                if (isAlertPresent()) {
                    logger.warn(seleniumSettings.getTestName() + " Alert Present " + seleniumSettings.getWebDriver().switchTo().alert().getText());
                    Assert.assertTrue(seleniumSettings.getWebDriver().switchTo().alert().getText().contains("Following fields with unsaved changes has been modified on the server. Press \"OK\" to keep your values or \"Cancel\" to replace your values with new values from the server"));
                    seleniumSettings.getWebDriver().switchTo().alert().accept();
                    seleniumSettings.getWebDriver().switchTo().defaultContent();
                }
                //TODO end remove this code
                wait.waitFormLoad();

                ////TODO begin remove this code
                //try {
                //    waitHelper.waitLoadingLoad();
                //} catch (UnhandledAlertException e) {
                //    if (seleniumSettings.getBrowser().equals("chrome")) {
                //        logger.warn(seleniumSettings.getTestName() + " Alert Present " + seleniumSettings.getWebDriver().switchTo().alert().getText());
                //        Assert.assertTrue(seleniumSettings.getWebDriver().switchTo().alert().getText().contains("Following fields with unsaved changes has been modified on the server. Press \"OK\" to keep your values or \"Cancel\" to replace your values with new values from the server"));
                //        seleniumSettings.getWebDriver().switchTo().alert().accept();
                //        seleniumSettings.getWebDriver().switchTo().defaultContent();
                //    } else if (seleniumSettings.getBrowser().equals("firefox")) {
                //        
                //    } else {
                //        throw new SeleniumUnexpectedException("Not support browser[" + seleniumSettings.getBrowser() + "]");
                //    }
                //}
                ////TODO end remove this code
                //waitHelper.waitLoadingLoad();
                //jqueryWaitHelper.waitJQueryLoad();
            }
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(fieldName, elementPosition);
                element.moveToElementById("idx" + idx + "_disp");
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.id("idx" + idx + "_disp"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
                By btnOpen = By.id("idx" + idx + "_but");
                psSelector.selectMultipleSpecificValues(btnOpen, 1L, Arrays.asList(value.split(",")), 1L);
            } else {
                element.moveToElementByName(fieldName + "_disp");
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.name(fieldName + "_disp"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
                psSelector.selectMultipleSpecificValues(By.name(fieldName + "_but"), 1L, Arrays.asList(value.split(",")), 1L);
            }
            expVals.put(fieldName, value);
            if (gridColumnId != null) {
                gridExpVals.put(gridColumnId, value.replaceAll(",", ", "));
            }
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            js.showInputForFileTb(fieldName);
            seleniumSettings.getWebDriver().switchTo().frame("ifrmHideForm");
            seleniumSettings.getWebDriver().findElement(By.name(fieldName)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(fieldName)).sendKeys(seleniumSettings.getUploadFilesPath() + value);
            //webDriver.switchTo().window((String) webDriver.getWindowHandles().toArray()[webDriver.getWindowHandles().size() - 1]);
            seleniumSettings.getWebDriver().switchTo().parentFrame(); /* For selenium tests in ie8*/
            js.hideInputForFileTb(fieldName);
            expVals.put(fieldName, value);
            if (gridColumnId != null) {
                gridExpVals.put("fName" + gridColumnId, value);
            }
        } else if (ConfigFieldType.WIKI.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(fieldName, elementPosition);
                wait.waitWebElement(By.id("idx" + idx));
                js.setValueToFCKEditor("idx" + idx, value);
            } else {
                String id = seleniumSettings.getWebDriver().findElement(By.name(fieldName)).getAttribute("id");
                wait.waitWebElement(By.id(id));
                js.setValueToFCKEditor(id, value);
            }
            expVals.put(fieldName, value);
            if (gridColumnId != null) {
                gridExpVals.put(gridColumnId, value);
            }
        }
    }

    public void checkFields(List<String> fields, Map<String, String> vals, int elementsPosition, boolean isOpenSelector, boolean isWikiReadOnly) {
        checkField(ConfigFieldType.CHECKBOX, fields.get(0), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.DATE, fields.get(1), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.DB_DROP_DOWN, fields.get(2), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.DB_SELECTOR, fields.get(3), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.DROP_DOWN, fields.get(4), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.ELECTRONIC_FILE, fields.get(5), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        if (fields.get(6) != null) {
            checkField(ConfigFieldType.HYPERLINK, fields.get(6), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        }
        checkField(ConfigFieldType.LATITUDE, fields.get(7), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.LONGITUDE, fields.get(8), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.MEMO, fields.get(9), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        if (fields.get(15) != null) {
            checkField(ConfigFieldType.MULTI_SELECTOR, fields.get(15), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        }
        checkField(ConfigFieldType.NUMBER, fields.get(10), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.SELECTOR, fields.get(11), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.TEXT, fields.get(12), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.TRACKOR_SELECTOR, fields.get(13), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.WIKI, fields.get(14), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.DATE_TIME, fields.get(16), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.TIME, fields.get(17), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
        checkField(ConfigFieldType.TRACKOR_DROP_DOWN, fields.get(18), vals, elementsPosition, isOpenSelector, isWikiReadOnly);
    }

    public void checkField(ConfigFieldType fieldDataType, String field, Map<String, String> vals, int elementPosition, boolean isOpenSelector, boolean isWikiReadOnly) {
        if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            if (elementPosition > 1) {
                assertElement.AssertCheckBoxByIdNew("idx" + getLastFieldIndex(field, elementPosition), vals.get(field));
            } else {
                assertElement.AssertCheckBoxNew(field, vals.get(field));
            }
        } else if (ConfigFieldType.DATE.equals(fieldDataType) || ConfigFieldType.HYPERLINK.equals(fieldDataType)
                || ConfigFieldType.LATITUDE.equals(fieldDataType) || ConfigFieldType.LONGITUDE.equals(fieldDataType)
                || ConfigFieldType.MEMO.equals(fieldDataType) || ConfigFieldType.NUMBER.equals(fieldDataType)
                || ConfigFieldType.TEXT.equals(fieldDataType) || ConfigFieldType.DATE_TIME.equals(fieldDataType)
                || ConfigFieldType.TIME.equals(fieldDataType)) {
            if (elementPosition > 1) {
                assertElement.AssertTextById("idx" + getLastFieldIndex(field, elementPosition), vals.get(field));
            } else {
                assertElement.AssertText(field, vals.get(field));
            }
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(fieldDataType) || ConfigFieldType.DROP_DOWN.equals(fieldDataType)
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(fieldDataType)) {
            if (elementPosition > 1) {
                assertElement.AssertSelectById("idx" + getLastFieldIndex(field, elementPosition), vals.get(field));
            } else {
                assertElement.AssertSelect(field, vals.get(field));
            }
        } else if (ConfigFieldType.SELECTOR.equals(fieldDataType) || ConfigFieldType.TRACKOR_SELECTOR.equals(fieldDataType) || ConfigFieldType.DB_SELECTOR.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                assertElement.AssertRadioPsSelectorById("idx" + idx + "_disp", "idx" + idx + "_but", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, vals.get(field), 1L, isOpenSelector);
            } else {
                assertElement.AssertRadioPsSelector(field + "_disp", field + "_but", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, vals.get(field), 1L, isOpenSelector);
            }
        } else if (ConfigFieldType.WIKI.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                if (isWikiReadOnly) {
                    seleniumSettings.getWebDriver().switchTo().frame("idx" + idx);
                    String actualVal = seleniumSettings.getWebDriver().findElement(By.tagName("body")).getAttribute("innerHTML").trim();
                    actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_1, AbstractSeleniumCore.SPECIAL_CHARACTERS_1);
                    actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_2, AbstractSeleniumCore.SPECIAL_CHARACTERS_2);
                    actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_3, AbstractSeleniumCore.SPECIAL_CHARACTERS_3);
                    actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_4, AbstractSeleniumCore.SPECIAL_CHARACTERS_4);
                    Assert.assertEquals(actualVal, vals.get(field), "Element with id=[idx" + idx + "] has wrong value");
                    seleniumSettings.getWebDriver().switchTo().parentFrame(); /* For selenium tests in ie8*/
                } else {
                    wait.waitWebElement(By.id("idx" + idx));
                    assertElement.AssertFCKEditor("idx" + idx, vals.get(field));
                }
            } else {
                if (isWikiReadOnly) {
                    seleniumSettings.getWebDriver().switchTo().frame(field);
                    String actualVal = seleniumSettings.getWebDriver().findElement(By.tagName("body")).getAttribute("innerHTML").trim();
                    actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_1, AbstractSeleniumCore.SPECIAL_CHARACTERS_1);
                    actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_2, AbstractSeleniumCore.SPECIAL_CHARACTERS_2);
                    actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_3, AbstractSeleniumCore.SPECIAL_CHARACTERS_3);
                    actualVal = actualVal.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_4, AbstractSeleniumCore.SPECIAL_CHARACTERS_4);
                    Assert.assertEquals(actualVal, vals.get(field), "Element with name=[" + field + "] has wrong value");
                    seleniumSettings.getWebDriver().switchTo().parentFrame(); /* For selenium tests in ie8*/
                } else {
                    String id = seleniumSettings.getWebDriver().findElement(By.name(field)).getAttribute("id");
                    wait.waitWebElement(By.id(id));
                    assertElement.AssertFCKEditor(id, vals.get(field));
                }
            }
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                assertElement.AssertCheckboxPsSelectorById("idx" + idx + "_disp", "idx" + idx + "_but", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(vals.get(field).split(",")), 1L, isOpenSelector);
            } else {
                assertElement.AssertCheckboxPsSelector(field + "_disp", field + "_but", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, Arrays.asList(vals.get(field).split(",")), 1L, isOpenSelector);
            }
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            if (elementPosition > 1) {
                assertElement.AssertTextById("idx" + getLastFieldIndex(field + "_disp", elementPosition) + "_disp", vals.get(field));
            } else {
                assertElement.AssertText(field + "_disp", vals.get(field));
            }
        }
    }

    public void clearFields(List<String> fieldNames, List<String> columnNames, Map<String, String> expVals,
            Map<String, String> gridExpVals, int elementsPosition) {
        clearField(ConfigFieldType.CHECKBOX, fieldNames.get(0), columnNames != null ? columnNames.get(0) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.DATE, fieldNames.get(1), columnNames != null ? columnNames.get(1) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.DB_DROP_DOWN, fieldNames.get(2), columnNames != null ? columnNames.get(2) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.DB_SELECTOR, fieldNames.get(3), columnNames != null ? columnNames.get(3) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.DROP_DOWN, fieldNames.get(4), columnNames != null ? columnNames.get(4) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.ELECTRONIC_FILE, fieldNames.get(5), columnNames != null ? columnNames.get(5) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.HYPERLINK, fieldNames.get(6), columnNames != null ? columnNames.get(6) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.LATITUDE, fieldNames.get(7), columnNames != null ? columnNames.get(7) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.LONGITUDE, fieldNames.get(8), columnNames != null ? columnNames.get(8) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.MEMO, fieldNames.get(9), columnNames != null ? columnNames.get(9) : null, expVals, gridExpVals, elementsPosition);
        if (fieldNames.get(15) != null) {
            clearField(ConfigFieldType.MULTI_SELECTOR, fieldNames.get(15), columnNames != null ? columnNames.get(15) : null, expVals, gridExpVals, elementsPosition);
        }
        clearField(ConfigFieldType.NUMBER, fieldNames.get(10), columnNames != null ? columnNames.get(10) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.SELECTOR, fieldNames.get(11), columnNames != null ? columnNames.get(11) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.TEXT, fieldNames.get(12), columnNames != null ? columnNames.get(12) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.TRACKOR_SELECTOR, fieldNames.get(13), columnNames != null ? columnNames.get(13) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.WIKI, fieldNames.get(14), columnNames != null ? columnNames.get(14) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.DATE_TIME, fieldNames.get(16), columnNames != null ? columnNames.get(16) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.TIME, fieldNames.get(17), columnNames != null ? columnNames.get(17) : null, expVals, gridExpVals, elementsPosition);
        clearField(ConfigFieldType.TRACKOR_DROP_DOWN, fieldNames.get(18), columnNames != null ? columnNames.get(18) : null, expVals, gridExpVals, elementsPosition);
    }

    public void clearField(ConfigFieldType fieldDataType, String field, String column, Map<String, String> expVals, Map<String, String> gridExpVals,
            int elementPosition) {
        Actions action = new Actions(seleniumSettings.getWebDriver());

        if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                String actualVal = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx)).isSelected() ? "YES" : "NO";
                if (actualVal != "NO") {
                    WebElement webCheckbox = seleniumSettings.getWebDriver().findElement(By.id("idx" + idx));
                    WebElement newCheckbox = webCheckbox.findElement(By.xpath("./.."));
                    element.click(newCheckbox);
                }
            } else {
                String actualVal = seleniumSettings.getWebDriver().findElement(By.name(field)).isSelected() ? "YES" : "NO";
                if (actualVal != "NO") {
                    WebElement checkboxElement = seleniumSettings.getWebDriver().findElement(By.name(field));
                    WebElement newCheckbox = checkbox.findLabelByElement(checkboxElement);
                    element.click(newCheckbox);
                }
            }
            expVals.put(field, "NO");
            if (column != null) {
                gridExpVals.put(column, "NO");
            }
        } else if (ConfigFieldType.DATE.equals(fieldDataType) || ConfigFieldType.HYPERLINK.equals(fieldDataType)
                || ConfigFieldType.MEMO.equals(fieldDataType) || ConfigFieldType.NUMBER.equals(fieldDataType)
                || ConfigFieldType.TEXT.equals(fieldDataType) || ConfigFieldType.DATE_TIME.equals(fieldDataType)
                || ConfigFieldType.TIME.equals(fieldDataType) || ConfigFieldType.LATITUDE.equals(fieldDataType)
                || ConfigFieldType.LONGITUDE.equals(fieldDataType)) {
            if (elementPosition > 1) {
                element.moveToElementById("idx" + getLastFieldIndex(field, elementPosition));
                seleniumSettings.getWebDriver().findElement(By.id("idx" + getLastFieldIndex(field, elementPosition))).clear();
            } else {
                element.moveToElementByName(field);
                seleniumSettings.getWebDriver().findElement(By.name(field)).clear();
            }
            expVals.put(field, "");
            if (column != null) {
                gridExpVals.put(column, "");
            }
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(fieldDataType) || ConfigFieldType.DROP_DOWN.equals(fieldDataType)
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(fieldDataType)) {
            if (elementPosition > 1) {
                element.moveToElementById("idx" + getLastFieldIndex(field, elementPosition));
                new Select(seleniumSettings.getWebDriver().findElement(By.id("idx" + getLastFieldIndex(field, elementPosition)))).selectByVisibleText("");
            } else {
                element.moveToElementByName(field);
                new Select(seleniumSettings.getWebDriver().findElement(By.name(field))).selectByVisibleText("");
            }
            expVals.put(field, "");
            if (column != null) {
                gridExpVals.put(column, "");
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
            expVals.put(field, "");
            if (column != null) {
                gridExpVals.put(column, "");
            }
        } else if (ConfigFieldType.DB_SELECTOR.equals(fieldDataType) || ConfigFieldType.MULTI_SELECTOR.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                element.moveToElementById("idx" + idx + "_disp");
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.id("idx" + idx + "_disp"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            } else {
                element.moveToElementByName(field + "_disp");
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.name(field + "_disp"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            }
            expVals.put(field, "");
            if (column != null) {
                gridExpVals.put(column, "");
            }
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field + "_disp", elementPosition);
                element.moveToElementById("idx" + idx + "_disp");
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.id("idx" + idx + "_disp"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            } else {
                element.moveToElementByName(field + "_disp");
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.name(field + "_disp"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            }
            expVals.put(field, "");
            if (column != null) {
                gridExpVals.put("fName" + column, "");
            }
        } else if (ConfigFieldType.WIKI.equals(fieldDataType)) {
            if (elementPosition > 1) {
                String idx = getLastFieldIndex(field, elementPosition);
                wait.waitWebElement(By.id("idx" + idx));
                js.setValueToFCKEditor("idx" + idx, "");
            } else {
                String id = seleniumSettings.getWebDriver().findElement(By.name(field)).getAttribute("id");
                wait.waitWebElement(By.id(id));
                js.setValueToFCKEditor(id, "");
            }
            expVals.put(field, "");
            if (column != null) {
                gridExpVals.put(column, "");
            }
        }
    }

    public void editCells(Long gridIndex, Long rowIndex, List<Long> columns, List<String> vals,
            Map<String, String> gridExpVals, Map<String, String> expVals, List<String> columnNames, List<String> fieldNames, Long tid, Long efileFieldId) {
        editCell(gridIndex, rowIndex, columns.get(0), ConfigFieldType.CHECKBOX, vals.get(0), columnNames.get(0), fieldNames != null ? fieldNames.get(0) : null, gridExpVals, expVals, null, null);
        editCell(gridIndex, rowIndex, columns.get(1), ConfigFieldType.DATE, vals.get(1), columnNames.get(1), fieldNames != null ? fieldNames.get(1) : null, gridExpVals, expVals, null, null);
        editCell(gridIndex, rowIndex, columns.get(2), ConfigFieldType.DB_DROP_DOWN, vals.get(2), columnNames.get(2), fieldNames != null ? fieldNames.get(2) : null, gridExpVals, expVals, null, null);
        editCell(gridIndex, rowIndex, columns.get(3), ConfigFieldType.DB_SELECTOR, vals.get(3), columnNames.get(3), fieldNames != null ? fieldNames.get(3) : null, gridExpVals, expVals, null, null);
        editCell(gridIndex, rowIndex, columns.get(4), ConfigFieldType.DROP_DOWN, vals.get(4), columnNames.get(4), fieldNames != null ? fieldNames.get(4) : null, gridExpVals, expVals, null, null);
        editCell(gridIndex, rowIndex, columns.get(5), ConfigFieldType.ELECTRONIC_FILE, vals.get(5), columnNames.get(5), fieldNames != null ? fieldNames.get(5) : null, gridExpVals, expVals, tid, efileFieldId);
        //hyperlink
        editCell(gridIndex, rowIndex, columns.get(7), ConfigFieldType.LATITUDE, vals.get(7), columnNames.get(7), fieldNames != null ? fieldNames.get(7) : null, gridExpVals, expVals, null, null);
        editCell(gridIndex, rowIndex, columns.get(8), ConfigFieldType.LONGITUDE, vals.get(8), columnNames.get(8), fieldNames != null ? fieldNames.get(8) : null, gridExpVals, expVals, null, null);
        editCell(gridIndex, rowIndex, columns.get(9), ConfigFieldType.MEMO, vals.get(9), columnNames.get(9), fieldNames != null ? fieldNames.get(9) : null, gridExpVals, expVals, null, null);
        editCell(gridIndex, rowIndex, columns.get(10), ConfigFieldType.NUMBER, vals.get(10), columnNames.get(10), fieldNames != null ? fieldNames.get(10) : null, gridExpVals, expVals, null, null);
        editCell(gridIndex, rowIndex, columns.get(11), ConfigFieldType.SELECTOR, vals.get(11), columnNames.get(11), fieldNames != null ? fieldNames.get(11) : null, gridExpVals, expVals, null, null);
        editCell(gridIndex, rowIndex, columns.get(12), ConfigFieldType.TEXT, vals.get(12), columnNames.get(12), fieldNames != null ? fieldNames.get(12) : null, gridExpVals, expVals, null, null);
        editCell(gridIndex, rowIndex, columns.get(13), ConfigFieldType.TRACKOR_SELECTOR, vals.get(13), columnNames.get(13), fieldNames != null ? fieldNames.get(13) : null, gridExpVals, expVals, null, null);
        editCell(gridIndex, rowIndex, columns.get(14), ConfigFieldType.WIKI, vals.get(14), columnNames.get(14), fieldNames != null ? fieldNames.get(14) : null, gridExpVals, expVals, null, null);
        if (columns.get(15) != null) {
            editCell(gridIndex, rowIndex, columns.get(15), ConfigFieldType.MULTI_SELECTOR, vals.get(15), columnNames.get(15), fieldNames != null ? fieldNames.get(15) : null, gridExpVals, expVals, null, null);
        }
        editCell(gridIndex, rowIndex, columns.get(16), ConfigFieldType.DATE_TIME, vals.get(16), columnNames.get(16), fieldNames != null ? fieldNames.get(16) : null, gridExpVals, expVals, null, null);
        editCell(gridIndex, rowIndex, columns.get(17), ConfigFieldType.TIME, vals.get(17), columnNames.get(17), fieldNames != null ? fieldNames.get(17) : null, gridExpVals, expVals, null, null);
        editCell(gridIndex, rowIndex, columns.get(18), ConfigFieldType.TRACKOR_DROP_DOWN, vals.get(18), columnNames.get(18), fieldNames != null ? fieldNames.get(18) : null, gridExpVals, expVals, null, null);
    }

    public void editCell(Long gridIndex, Long rowIndex, Long columnIndex, ConfigFieldType fieldDataType, String value,
            String gridColumnId, String fieldName, Map<String, String> gridExpVals, Map<String, String> expVals, Long tid, Long fieldId) {
        Long scrollLeft = js.getGridScrollLeft(gridIndex, columnIndex);
        js.gridScrollLeft(gridIndex, scrollLeft);
        Long scrollTop = js.getGridScrollTop(gridIndex, rowIndex);
        js.gridScrollTop(gridIndex, scrollTop);

        WebElement gridCell = (WebElement) js.getGridCellByRowIndexAndColIndex(gridIndex, rowIndex, columnIndex);
        elementWait.waitElementVisible(gridCell);

        if (!ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            element.doubleClick(gridCell);
        }

        AbstractSeleniumCore.sleep(500L);

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
            Select sel = new Select(seleniumSettings.getWebDriver().findElement(By.name("epmDd1")));
            wait.waitListBoxLoad2(sel);
            sel.selectByVisibleText(value);
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.DB_SELECTOR.equals(fieldDataType) || ConfigFieldType.SELECTOR.equals(fieldDataType) || ConfigFieldType.TRACKOR_SELECTOR.equals(fieldDataType)) {
            psSelector.selectSpecificValue(By.name("btn1"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, value, 1L);
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(fieldDataType)) {
            Actions action = new Actions(seleniumSettings.getWebDriver());
            action.moveToElement(seleniumSettings.getWebDriver().findElement(By.name("epmSelector1"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            psSelector.selectMultipleSpecificValues(By.name("btn1"), 1L, Arrays.asList(value.split(",")), 1L);
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
        } else if (ConfigFieldType.NUMBER.equals(fieldDataType)) {
            seleniumSettings.getWebDriver().findElement(By.name("NumberField1")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("NumberField1")).sendKeys(value);
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.DATE.equals(fieldDataType) || ConfigFieldType.DATE_TIME.equals(fieldDataType)
                || ConfigFieldType.TIME.equals(fieldDataType)) {
            seleniumSettings.getWebDriver().findElement(By.name("epmDate1")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("epmDate1")).sendKeys(value);
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            wait.waitWebElement(By.id("txtEfile1"));

//            if (seleniumSettings.getBrowser().equals("chrome")) {
//                WebElement frame = (WebElement) jsHelper.getFrameForFileTbGrid(gridIndex);
//                seleniumSettings.getWebDriver().switchTo().frame(frame);
//                waitHelper.waitWebElement(By.name("eFile_" + fieldId + "_" + tid));
//                seleniumSettings.getWebDriver().switchTo().parentFrame();
//                jsHelper.showInputForFileTbGrid2(gridIndex, frame, "eFile_" + fieldId + "_" + tid);
//                seleniumSettings.getWebDriver().switchTo().frame(frame);
//                seleniumSettings.getWebDriver().findElement(By.name("eFile_" + fieldId + "_" + tid)).clear();
//                seleniumSettings.getWebDriver().findElement(By.name("eFile_" + fieldId + "_" + tid)).sendKeys(seleniumSettings.getUploadFilesPath() + value);
//                seleniumSettings.getWebDriver().switchTo().parentFrame();
//                jsHelper.hideInputForFileTbGrid2(gridIndex, frame, "eFile_" + fieldId + "_" + tid);
//            } else if (seleniumSettings.getBrowser().equals("firefox")) {
                Actions action = new Actions(seleniumSettings.getWebDriver());
                action.moveToElement(seleniumSettings.getWebDriver().findElement(By.name("txtEfile1"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();

                element.doubleClick(gridCell);

                AbstractSeleniumCore.sleep(500L);

                wait.waitWebElement(By.id("txtEfile1"));

                WebElement frame = (WebElement) js.getFrameForFileTbGrid(gridIndex);
                seleniumSettings.getWebDriver().switchTo().frame(frame);
                wait.waitWebElement(By.name("eFile_" + fieldId + "_" + tid));
                seleniumSettings.getWebDriver().switchTo().parentFrame();
                js.showInputForFileTbGrid2(gridIndex, frame, "eFile_" + fieldId + "_" + tid);
                seleniumSettings.getWebDriver().switchTo().frame(frame);
                elementWait.waitElementVisibleByName("eFile_" + fieldId + "_" + tid);
                elementWait.waitElementDisplayByName("eFile_" + fieldId + "_" + tid);
                seleniumSettings.getWebDriver().findElement(By.name("eFile_" + fieldId + "_" + tid)).sendKeys(seleniumSettings.getUploadFilesPath() + value);
                seleniumSettings.getWebDriver().switchTo().parentFrame();
                js.hideInputForFileTbGrid2(gridIndex, frame, "eFile_" + fieldId + "_" + tid);
//            } else {
//                throw new SeleniumUnexpectedException("Not support browser[" + seleniumSettings.getBrowser() + "]");
//            }

            gridExpVals.put("fName" + gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }
        } else if (ConfigFieldType.WIKI.equals(fieldDataType)) {
            wait.waitWebElement(By.id("epmMemo1"));
            js.setValueToFCKEditor("epmMemo1", value);
            gridExpVals.put(gridColumnId, value);
            if (fieldName != null) {
                expVals.put(fieldName, value);
            }

            AbstractSeleniumCore.sleep(500L);

            js.selectGridCellByRowIndexAndColIndex2(gridIndex, rowIndex, columnIndex);
        }
    }

    public void clearCells(Long gridIndex, Long rowIndex, List<Long> columns, Map<String, String> gridExpVals,
            Map<String, String> expVals, List<String> columnNames, List<String> fieldNames) {
        clearCell(gridIndex, rowIndex, columns.get(0), ConfigFieldType.CHECKBOX, columnNames.get(0), fieldNames != null ? fieldNames.get(0) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(1), ConfigFieldType.DATE, columnNames.get(1), fieldNames != null ? fieldNames.get(1) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(2), ConfigFieldType.DB_DROP_DOWN, columnNames.get(2), fieldNames != null ? fieldNames.get(2) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(3), ConfigFieldType.DB_SELECTOR, columnNames.get(3), fieldNames != null ? fieldNames.get(3) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(4), ConfigFieldType.DROP_DOWN, columnNames.get(4), fieldNames != null ? fieldNames.get(4) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(5), ConfigFieldType.ELECTRONIC_FILE, columnNames.get(5), fieldNames != null ? fieldNames.get(5) : null, gridExpVals, expVals);
        //hyperlink
        clearCell(gridIndex, rowIndex, columns.get(7), ConfigFieldType.LATITUDE, columnNames.get(7), fieldNames != null ? fieldNames.get(7) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(8), ConfigFieldType.LONGITUDE, columnNames.get(8), fieldNames != null ? fieldNames.get(8) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(9), ConfigFieldType.MEMO, columnNames.get(9), fieldNames != null ? fieldNames.get(9) : null, gridExpVals, expVals);
        if (columns.get(15) != null) {
            clearCell(gridIndex, rowIndex, columns.get(15), ConfigFieldType.MULTI_SELECTOR, columnNames.get(15), fieldNames != null ? fieldNames.get(15) : null, gridExpVals, expVals);
        }
        clearCell(gridIndex, rowIndex, columns.get(10), ConfigFieldType.NUMBER, columnNames.get(10), fieldNames != null ? fieldNames.get(10) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(11), ConfigFieldType.SELECTOR, columnNames.get(11), fieldNames != null ? fieldNames.get(11) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(12), ConfigFieldType.TEXT, columnNames.get(12), fieldNames != null ? fieldNames.get(12) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(13), ConfigFieldType.TRACKOR_SELECTOR, columnNames.get(13), fieldNames != null ? fieldNames.get(13) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(14), ConfigFieldType.WIKI, columnNames.get(14), fieldNames != null ? fieldNames.get(14) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(16), ConfigFieldType.DATE_TIME, columnNames.get(16), fieldNames != null ? fieldNames.get(16) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(17), ConfigFieldType.TIME, columnNames.get(17), fieldNames != null ? fieldNames.get(17) : null, gridExpVals, expVals);
        clearCell(gridIndex, rowIndex, columns.get(18), ConfigFieldType.TRACKOR_DROP_DOWN, columnNames.get(18), fieldNames != null ? fieldNames.get(18) : null, gridExpVals, expVals);
    }

    public void clearCell(Long gridIndex, Long rowIndex, Long columnIndex, ConfigFieldType fieldDataType, String gridColumnId,
            String fieldName, Map<String, String> gridExpVals, Map<String, String> expVals) {
        Long scrollLeft = js.getGridScrollLeft(gridIndex, columnIndex);
        js.gridScrollLeft(gridIndex, scrollLeft);
        Long scrollTop = js.getGridScrollTop(gridIndex, rowIndex);
        js.gridScrollTop(gridIndex, scrollTop);

        WebElement gridCell = (WebElement) js.getGridCellByRowIndexAndColIndex(gridIndex, rowIndex, columnIndex);
        elementWait.waitElementVisible(gridCell);

        if (!ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            element.doubleClick(gridCell);
        }

        AbstractSeleniumCore.sleep(500L);

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
        } else if (ConfigFieldType.DB_SELECTOR.equals(fieldDataType) || ConfigFieldType.MULTI_SELECTOR.equals(fieldDataType)) {
            Actions action = new Actions(seleniumSettings.getWebDriver());
            action.moveToElement(seleniumSettings.getWebDriver().findElement(By.name("epmSelector1"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            gridExpVals.put(gridColumnId, "");
            if (fieldName != null) {
                expVals.put(fieldName, "");
            }
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            Actions action = new Actions(seleniumSettings.getWebDriver());
            action.moveToElement(seleniumSettings.getWebDriver().findElement(By.name("txtEfile1"))).click().keyDown(Keys.CONTROL).sendKeys(Keys.DELETE).keyUp(Keys.CONTROL).perform();
            gridExpVals.put("fName" + gridColumnId, "");
            if (fieldName != null) {
                expVals.put(fieldName, "");
            }
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(fieldDataType) || ConfigFieldType.DROP_DOWN.equals(fieldDataType)
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(fieldDataType)) {
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
            wait.waitWebElement(By.id("epmMemo1"));
            js.setValueToFCKEditor("epmMemo1", "");
            gridExpVals.put(gridColumnId, "");
            if (fieldName != null) {
                expVals.put(fieldName, "");
            }

            AbstractSeleniumCore.sleep(500L);

            js.selectGridCellByRowIndexAndColIndex2(gridIndex, rowIndex, columnIndex);
        }
    }

    public void rightClickCell(Long gridIndex, Long rowIndex, Long columnIndex) {
        Long scrollLeft = js.getGridScrollLeft(gridIndex, columnIndex);
        js.gridScrollLeft(gridIndex, scrollLeft);
        Long scrollTop = js.getGridScrollTop(gridIndex, rowIndex);
        js.gridScrollTop(gridIndex, scrollTop);

        WebElement gridCell = (WebElement) js.getGridCellByRowIndexAndColIndex(gridIndex, rowIndex, columnIndex);
        elementWait.waitElementVisible(gridCell);

        Actions action = new Actions(seleniumSettings.getWebDriver());
        action.contextClick(gridCell).perform();
    }

    public void checkCellsEnabled(Long gridIndex, Long rowIndex, List<Long> columns) {
        checkCellEnabled(gridIndex, rowIndex, columns.get(0), ConfigFieldType.CHECKBOX);
        checkCellEnabled(gridIndex, rowIndex, columns.get(1), ConfigFieldType.DATE);
        checkCellEnabled(gridIndex, rowIndex, columns.get(2), ConfigFieldType.DB_DROP_DOWN);
        checkCellEnabled(gridIndex, rowIndex, columns.get(3), ConfigFieldType.DB_SELECTOR);
        checkCellEnabled(gridIndex, rowIndex, columns.get(4), ConfigFieldType.DROP_DOWN);
        checkCellEnabled(gridIndex, rowIndex, columns.get(5), ConfigFieldType.ELECTRONIC_FILE);
        //hyperlink
        checkCellEnabled(gridIndex, rowIndex, columns.get(7), ConfigFieldType.LATITUDE);
        checkCellEnabled(gridIndex, rowIndex, columns.get(8), ConfigFieldType.LONGITUDE);
        checkCellEnabled(gridIndex, rowIndex, columns.get(9), ConfigFieldType.MEMO);
        if (columns.get(15) != null) {
            checkCellEnabled(gridIndex, rowIndex, columns.get(15), ConfigFieldType.MULTI_SELECTOR);
        }
        checkCellEnabled(gridIndex, rowIndex, columns.get(10), ConfigFieldType.NUMBER);
        checkCellEnabled(gridIndex, rowIndex, columns.get(11), ConfigFieldType.SELECTOR);
        checkCellEnabled(gridIndex, rowIndex, columns.get(12), ConfigFieldType.TEXT);
        checkCellEnabled(gridIndex, rowIndex, columns.get(13), ConfigFieldType.TRACKOR_SELECTOR);
        checkCellEnabled(gridIndex, rowIndex, columns.get(14), ConfigFieldType.WIKI);
        checkCellEnabled(gridIndex, rowIndex, columns.get(16), ConfigFieldType.DATE_TIME);
        checkCellEnabled(gridIndex, rowIndex, columns.get(17), ConfigFieldType.TIME);
        checkCellEnabled(gridIndex, rowIndex, columns.get(18), ConfigFieldType.TRACKOR_DROP_DOWN);
    }

    public void checkCellEnabled(Long gridIndex, Long rowIndex, Long columnIndex, ConfigFieldType fieldDataType) {
        Long scrollLeft = js.getGridScrollLeft(gridIndex, columnIndex);
        js.gridScrollLeft(gridIndex, scrollLeft);
        Long scrollTop = js.getGridScrollTop(gridIndex, rowIndex);
        js.gridScrollTop(gridIndex, scrollTop);

        WebElement gridCell = (WebElement) js.getGridCellByRowIndexAndColIndex(gridIndex, rowIndex, columnIndex);
        elementWait.waitElementVisible(gridCell);

        if (!ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            element.doubleClick(gridCell);
        }

        AbstractSeleniumCore.sleep(500L);

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            WebElement elem = gridCell.findElement(By.tagName("input"));
            assertElement.AssertElementEnabled(elem, true);
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(fieldDataType) || ConfigFieldType.DROP_DOWN.equals(fieldDataType) 
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmDd1")).size(), 1);
        } else if (ConfigFieldType.DB_SELECTOR.equals(fieldDataType) || ConfigFieldType.SELECTOR.equals(fieldDataType) 
                || ConfigFieldType.TRACKOR_SELECTOR.equals(fieldDataType) || ConfigFieldType.MULTI_SELECTOR.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmSelector1")).size(), 1);
        } else if (ConfigFieldType.LATITUDE.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmLat1")).size(), 1); 
        } else if (ConfigFieldType.LONGITUDE.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmLong1")).size(), 1);
        } else if (ConfigFieldType.MEMO.equals(fieldDataType) || ConfigFieldType.TEXT.equals(fieldDataType) || ConfigFieldType.WIKI.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmMemo1")).size(), 1);
        } else if (ConfigFieldType.NUMBER.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("NumberField1")).size(), 1);
        } else if (ConfigFieldType.DATE.equals(fieldDataType) || ConfigFieldType.DATE_TIME.equals(fieldDataType) || ConfigFieldType.TIME.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmDate1")).size(), 1);
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("btnEfile1")).size(), 1);
        }

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        js.selectGridCellByRowIndexAndColIndex2(gridIndex, rowIndex, columnIndex);
    }

    public void checkCellsDisabled(Long gridIndex, Long rowIndex, List<Long> columns) {
        checkCellDisabled(gridIndex, rowIndex, columns.get(0), ConfigFieldType.CHECKBOX);
        checkCellDisabled(gridIndex, rowIndex, columns.get(1), ConfigFieldType.DATE);
        checkCellDisabled(gridIndex, rowIndex, columns.get(2), ConfigFieldType.DB_DROP_DOWN);
        checkCellDisabled(gridIndex, rowIndex, columns.get(3), ConfigFieldType.DB_SELECTOR);
        checkCellDisabled(gridIndex, rowIndex, columns.get(4), ConfigFieldType.DROP_DOWN);
        checkCellDisabled(gridIndex, rowIndex, columns.get(5), ConfigFieldType.ELECTRONIC_FILE);
        //hyperlink
        checkCellDisabled(gridIndex, rowIndex, columns.get(7), ConfigFieldType.LATITUDE);
        checkCellDisabled(gridIndex, rowIndex, columns.get(8), ConfigFieldType.LONGITUDE);
        checkCellDisabled(gridIndex, rowIndex, columns.get(9), ConfigFieldType.MEMO);
        if (columns.get(15) != null) {
            checkCellDisabled(gridIndex, rowIndex, columns.get(15), ConfigFieldType.MULTI_SELECTOR);
        }
        checkCellDisabled(gridIndex, rowIndex, columns.get(10), ConfigFieldType.NUMBER);
        checkCellDisabled(gridIndex, rowIndex, columns.get(11), ConfigFieldType.SELECTOR);
        checkCellDisabled(gridIndex, rowIndex, columns.get(12), ConfigFieldType.TEXT);
        checkCellDisabled(gridIndex, rowIndex, columns.get(13), ConfigFieldType.TRACKOR_SELECTOR);
        checkCellDisabled(gridIndex, rowIndex, columns.get(14), ConfigFieldType.WIKI);
        checkCellDisabled(gridIndex, rowIndex, columns.get(16), ConfigFieldType.DATE_TIME);
        checkCellDisabled(gridIndex, rowIndex, columns.get(17), ConfigFieldType.TIME);
        checkCellDisabled(gridIndex, rowIndex, columns.get(18), ConfigFieldType.TRACKOR_DROP_DOWN);
    }

    public void checkCellDisabled(Long gridIndex, Long rowIndex, Long columnIndex, ConfigFieldType fieldDataType) {
        Long scrollLeft = js.getGridScrollLeft(gridIndex, columnIndex);
        js.gridScrollLeft(gridIndex, scrollLeft);
        Long scrollTop = js.getGridScrollTop(gridIndex, rowIndex);
        js.gridScrollTop(gridIndex, scrollTop);

        WebElement gridCell = (WebElement) js.getGridCellByRowIndexAndColIndex(gridIndex, rowIndex, columnIndex);
        elementWait.waitElementVisible(gridCell);

        if (!ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            element.doubleClick(gridCell);
        }

        AbstractSeleniumCore.sleep(500L);

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        if (ConfigFieldType.CHECKBOX.equals(fieldDataType)) {
            WebElement elem = gridCell.findElement(By.tagName("input"));
            assertElement.AssertElementEnabled(elem, false);
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(fieldDataType) || ConfigFieldType.DROP_DOWN.equals(fieldDataType) 
                || ConfigFieldType.TRACKOR_DROP_DOWN.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmDd1")).size(), 0);
        } else if (ConfigFieldType.DB_SELECTOR.equals(fieldDataType) || ConfigFieldType.SELECTOR.equals(fieldDataType) 
                || ConfigFieldType.TRACKOR_SELECTOR.equals(fieldDataType) || ConfigFieldType.MULTI_SELECTOR.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmSelector1")).size(), 0);
        } else if (ConfigFieldType.LATITUDE.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmLat1")).size(), 0); 
        } else if (ConfigFieldType.LONGITUDE.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmLong1")).size(), 0);
        } else if (ConfigFieldType.MEMO.equals(fieldDataType) || ConfigFieldType.TEXT.equals(fieldDataType) || ConfigFieldType.WIKI.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmMemo1")).size(), 0);
        } else if (ConfigFieldType.NUMBER.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("NumberField1")).size(), 0);
        } else if (ConfigFieldType.DATE.equals(fieldDataType) || ConfigFieldType.DATE_TIME.equals(fieldDataType) || ConfigFieldType.TIME.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("epmDate1")).size(), 0);
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(fieldDataType)) {
            Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name("btnEfile1")).size(), 0);
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
    }

    public void checkColumnExist(Long gridIndex, String columnLabel) {
        Assert.assertEquals(js.getColumnIndexByLabel(gridIndex, columnLabel) != null, true, "Grid not have column");
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
    }

    public void checkColumnNotExist(Long gridIndex, String columnLabel) {
        Assert.assertEquals(js.getColumnIndexByLabel(gridIndex, columnLabel) == null, true, "Grid have column");
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
        if (fieldIds.get(20) != null) { //Workplan and Tasks trackor types not support
            checkFieldExist(fieldIds.get(20)); //ROLLUP
        }
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
        if (fieldIds.get(20) != null) { //Workplan and Tasks trackor types not support
            checkFieldNotExist(fieldIds.get(20)); //ROLLUP
        }
    }

    public void checkFieldNotExist(String fieldId) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        Assert.assertEquals(seleniumSettings.getWebDriver().findElements(By.name(fieldId)).size(), 0);
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void assertFieldsDisabled(List<String> fieldIds) {//TODO elementPosition
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(0))), false);
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(1))), false);
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(2))), false);
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(3) + "_disp")), false);
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(4))), false);
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(5) + "_disp")), false);
        if (fieldIds.get(6) != null) {
            assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(6))), false);
        }
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(7))), false);
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(8))), false);
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(9))), false);
        if (fieldIds.get(15) != null) {
            assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(15) + "_disp")), false);
        }
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(10))), false);
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(11) + "_disp")), false);
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(12))), false);
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(13) + "_disp")), false);
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(16))), false);
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(17))), false);
        assertElement.AssertElementEnabled(seleniumSettings.getWebDriver().findElement(By.name(fieldIds.get(18))), false);
    }

    public void checkFieldsEnabled(List<String> fieldIds, int elementPosition) {
        checkFieldEnabled(fieldIds.get(0), elementPosition); //CHECKBOX
        checkFieldEnabled(fieldIds.get(1), elementPosition); //DATE
        checkFieldEnabled(fieldIds.get(2), elementPosition); //DB_DROP_DOWN
        checkFieldEnabled(fieldIds.get(3), elementPosition); //DB_SELECTOR
        checkFieldEnabled(fieldIds.get(4), elementPosition); //DROP_DOWN
        checkFieldEnabled(fieldIds.get(5) + "_but", elementPosition); //ELECTRONIC_FILE
        checkFieldEnabled(fieldIds.get(6), elementPosition); //HYPERLINK
        checkFieldEnabled(fieldIds.get(7), elementPosition); //LATITUDE
        checkFieldEnabled(fieldIds.get(8), elementPosition); //LONGITUDE
        checkFieldEnabled(fieldIds.get(9), elementPosition); //MEMO
        checkFieldEnabled(fieldIds.get(10), elementPosition); //NUMBER
        checkFieldEnabled(fieldIds.get(11), elementPosition); //SELECTOR
        checkFieldEnabled(fieldIds.get(12), elementPosition); //TEXT
        checkFieldEnabled(fieldIds.get(13), elementPosition); //TRACKOR_SELECTOR
        checkFieldEnabled(fieldIds.get(14), elementPosition); //WIKI
        checkFieldEnabled(fieldIds.get(15), elementPosition); //MULTI_SELECTOR
        checkFieldEnabled(fieldIds.get(16), elementPosition); //DATE_TIME
        checkFieldEnabled(fieldIds.get(17), elementPosition); //TIME
        checkFieldEnabled(fieldIds.get(18), elementPosition); //TRACKOR_DROPDOWN
    }

    public void checkFieldEnabled(String fieldId, int elementPosition) {
        assertElement.AssertFieldEnabled(fieldId, elementPosition);
    }

    public void checkFieldsDisabled(List<String> fieldIds, int elementPosition) {
        checkFieldDisabled(fieldIds.get(0), elementPosition); //CHECKBOX
        checkFieldDisabled(fieldIds.get(1), elementPosition); //DATE
        checkFieldDisabled(fieldIds.get(2), elementPosition); //DB_DROP_DOWN
        checkFieldDisabled(fieldIds.get(3), elementPosition); //DB_SELECTOR
        checkFieldDisabled(fieldIds.get(4), elementPosition); //DROP_DOWN
        checkFieldDisabled(fieldIds.get(5) + "_but", elementPosition); //ELECTRONIC_FILE
        checkFieldDisabled(fieldIds.get(6), elementPosition); //HYPERLINK
        checkFieldDisabled(fieldIds.get(7), elementPosition); //LATITUDE
        checkFieldDisabled(fieldIds.get(8), elementPosition); //LONGITUDE
        checkFieldDisabled(fieldIds.get(9), elementPosition); //MEMO
        checkFieldDisabled(fieldIds.get(10), elementPosition); //NUMBER
        checkFieldDisabled(fieldIds.get(11), elementPosition); //SELECTOR
        checkFieldDisabled(fieldIds.get(12), elementPosition); //TEXT
        checkFieldDisabled(fieldIds.get(13), elementPosition); //TRACKOR_SELECTOR
        checkFieldDisabled(fieldIds.get(14), elementPosition); //WIKI
        checkFieldDisabled(fieldIds.get(15), elementPosition); //MULTI_SELECTOR
        checkFieldDisabled(fieldIds.get(16), elementPosition); //DATE_TIME
        checkFieldDisabled(fieldIds.get(17), elementPosition); //TIME
        checkFieldDisabled(fieldIds.get(18), elementPosition); //TRACKOR_DROPDOWN
    }

    public void checkFieldDisabled(String fieldId, int elementPosition) {
        assertElement.AssertFieldDisabled(fieldId, elementPosition);
    }

    private boolean isAlertPresent() {
        try {
            seleniumSettings.getWebDriver().switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
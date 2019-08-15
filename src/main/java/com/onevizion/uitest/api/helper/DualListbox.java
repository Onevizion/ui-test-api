package com.onevizion.uitest.api.helper;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.view.View;

@Component
public class DualListbox {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Js js;

    @Resource
    private HtmlSelect htmlSelect;

    @Resource
    private ElementWait elementWait;

    @Resource
    private View view;

    public void removeValueByTextNew(String btnId, String text) {
        Long position = js.getNewDropDownElementPositionNew("rightListBox", "record", text);
        js.scrollNewDropDownTop("rightListBox", "scrollContainer", position * 28L);

        WebElement entityElem = (WebElement) js.getNewDropDownElementNew("rightListBox", "record", text);
        elementWait.waitElementVisible(entityElem);

        entityElem.click();

        seleniumSettings.getWebDriver().findElement(By.id(btnId)).click();
    }

    private void removeValueByValue(String btnId, String value) {
        

        List<WebElement> rightColumns = view.getRightColumns();
        for (WebElement rightColumn : rightColumns) {
            if (value.equals(rightColumn.getAttribute("id"))) {
                rightColumn.click();
                break;
            }
        }

        seleniumSettings.getWebDriver().findElement(By.id(btnId)).click();
    }

    public void removeValues(String btnId, List<String> columnNames) {
        removeValueByValue(btnId, columnNames.get(0)); //CHECKBOX
        removeValueByValue(btnId, columnNames.get(1)); //DATE
        removeValueByValue(btnId, columnNames.get(2)); //DB_DROP_DOWN
        removeValueByValue(btnId, columnNames.get(3)); //DB_SELECTOR
        removeValueByValue(btnId, columnNames.get(4)); //DROP_DOWN
        removeValueByValue(btnId, columnNames.get(5)); //ELECTRONIC_FILE
        removeValueByValue(btnId, columnNames.get(6)); //HYPERLINK
        removeValueByValue(btnId, columnNames.get(7)); //LATITUDE
        removeValueByValue(btnId, columnNames.get(8)); //LONGITUDE
        removeValueByValue(btnId, columnNames.get(9)); //MEMO
        removeValueByValue(btnId, columnNames.get(10)); //NUMBER
        removeValueByValue(btnId, columnNames.get(11)); //SELECTOR
        removeValueByValue(btnId, columnNames.get(12)); //TEXT
        removeValueByValue(btnId, columnNames.get(13)); //TRACKOR_SELECTOR
        removeValueByValue(btnId, columnNames.get(14)); //WIKI
        removeValueByValue(btnId, columnNames.get(15)); //MULTI_SELECTOR
        removeValueByValue(btnId, columnNames.get(16)); //DATE_TIME
        removeValueByValue(btnId, columnNames.get(17)); //TIME
        removeValueByValue(btnId, columnNames.get(18)); //TRACKOR_DROPDOWN
        removeValueByValue(btnId, columnNames.get(19)); //CALCULATED
        if (columnNames.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            removeValueByValue(btnId, columnNames.get(20)); //ROLLUP
        }
        removeValueByValue(btnId, columnNames.get(21)); //MULTI_TRACKOR_SELECTOR
    }

    public void addValueByText(Select select, String btnId, String text) {
        checkValueByTextIsPresent(select, text);
        deselectSelectOptions(select);
        select.selectByVisibleText(text);
        seleniumSettings.getWebDriver().findElement(By.id(btnId)).click();
    }

    /*new void to support new duallist box*/
    public void addValueByText(WebElement select, String btnId, String text) {
        checkValueByTextIsPresent(select, text);
        //deselectSelectOptions(select);
        for (WebElement option :select.findElements(By.tagName("div"))) {
            if (text.equals(htmlSelect.getOptionText(option, true))) {
                option.click();
                break;
            }
        }
        //select.selectByVisibleText(text);
        seleniumSettings.getWebDriver().findElement(By.id(btnId)).click();
    }

    public void addValueByTextNew(String btnId, String text) {
        checkValueByTextIsPresentNew(text);

        Long position = js.getNewDropDownElementPositionNew("leftListBox", "record", text);
        js.scrollNewDropDownTop("leftListBox", "scrollContainer", position * 28L);

        WebElement entityElem = (WebElement) js.getNewDropDownElementNew("leftListBox", "record", text);
        elementWait.waitElementVisible(entityElem);

        entityElem.click();

        seleniumSettings.getWebDriver().findElement(By.id(btnId)).click();
    }

    private void checkValueByTextIsPresent(Select select, String text) {
        int attemptsCnt = 0; //protection from the endless cycle
        int i = 0;
        do {
            try{
                for (WebElement option : select.getOptions()) {
                    if (text.equals(option.getText())) {
                        i = i + 1;
                        break;
                    }
                }
            } catch (StaleElementReferenceException e) {
                i = 0;
            }
            attemptsCnt = attemptsCnt + 1;
        } while (i < 1 && attemptsCnt <= 10);

        if (i < 1 && attemptsCnt > 10) {
            throw new SeleniumUnexpectedException("Value not found in duallist box");
        }
    }

    /*new void to support new duallist box*/
    private void checkValueByTextIsPresent(WebElement select, String text) {
        int attemptsCnt = 0; //protection from the endless cycle
        int i = 0;
        do {
            try{
                for (WebElement option : select.findElements(By.tagName("div"))) {
                    if (text.equals(htmlSelect.getOptionText(option, true))) {
                        i = i + 1;
                        break;
                    }
                }
            } catch (StaleElementReferenceException e) {
                i = 0;
            }
            attemptsCnt = attemptsCnt + 1;
        } while (i < 1 && attemptsCnt <= 10);

        if (i < 1 && attemptsCnt > 10) {
            throw new SeleniumUnexpectedException("Value not found in duallist box");
        }
    }

    private void checkValueByTextIsPresentNew(String text) {
        int attemptsCnt = 0; //protection from the endless cycle
        int i = 0;
        do {
            try{
                List<WebElement> leftColumns = view.getLeftColumns();
                for (WebElement leftColumn : leftColumns) {
                    if (text.equals(htmlSelect.getOptionTextNew(leftColumn))) {
                        i = i + 1;
                        break;
                    }
                }
            } catch (StaleElementReferenceException e) {
                i = 0;
            }
            attemptsCnt = attemptsCnt + 1;
        } while (i < 1 && attemptsCnt <= 10);

        if (i < 1 && attemptsCnt > 10) {
            throw new SeleniumUnexpectedException("Value not found in duallist box");
        }
    }

    public void checkValueByTextIsPresentInRightNew(String text) {
        int attemptsCnt = 0; //protection from the endless cycle
        int i = 0;
        do {
            try{
                List<WebElement> leftColumns = view.getRightColumns();
                for (WebElement leftColumn : leftColumns) {
                    if (text.equals(htmlSelect.getOptionTextNew(leftColumn))) {
                        i = i + 1;
                        break;
                    }
                }
            } catch (StaleElementReferenceException e) {
                i = 0;
            }
            attemptsCnt = attemptsCnt + 1;
        } while (i < 1 && attemptsCnt <= 10);

        if (i < 1 && attemptsCnt > 10) {
            throw new SeleniumUnexpectedException("Value not found in duallist box");
        }
    }

    private void addValueByValue(String btnId, String value) {
        checkValueByValueIsPresent(value);

        List<WebElement> leftColumns = view.getLeftColumns();
        for (int i = 0; i <= leftColumns.size(); i++) {
            if (value.equals(leftColumns.get(i).getAttribute("id"))) {
                js.scrollNewDropDownTop("leftListBox", "scrollContainer", i * 28L);
                leftColumns.get(i).click();
                break;
            }
        }

        seleniumSettings.getWebDriver().findElement(By.id(btnId)).click();
    }

    private void checkValueByValueIsPresent(String value) {
        int attemptsCnt = 0; //protection from the endless cycle
        int i = 0;
        do {
            try{
                List<WebElement> leftColumns = view.getLeftColumns();
                for (WebElement leftColumn : leftColumns) {
                    if (value.equals(leftColumn.getAttribute("id"))) {
                        i = i + 1;
                        break;
                    }
                }
            } catch (StaleElementReferenceException e) {
                i = 0;
            }
            attemptsCnt = attemptsCnt + 1;
        } while (i < 1 && attemptsCnt <= 10);

        if (i < 1 && attemptsCnt > 10) {
            throw new SeleniumUnexpectedException("Value not found in duallist box");
        }
    }

    public void addValues(String btnId, List<String> columnNames) {
        addValueByValue(btnId, columnNames.get(0)); //CHECKBOX
        addValueByValue(btnId, columnNames.get(1)); //DATE
        addValueByValue(btnId, columnNames.get(2)); //DB_DROP_DOWN
        addValueByValue(btnId, columnNames.get(3)); //DB_SELECTOR
        addValueByValue(btnId, columnNames.get(4)); //DROP_DOWN
        addValueByValue(btnId, columnNames.get(5)); //ELECTRONIC_FILE
        addValueByValue(btnId, columnNames.get(6)); //HYPERLINK
        addValueByValue(btnId, columnNames.get(7)); //LATITUDE
        addValueByValue(btnId, columnNames.get(8)); //LONGITUDE
        addValueByValue(btnId, columnNames.get(9)); //MEMO
        addValueByValue(btnId, columnNames.get(10)); //NUMBER
        addValueByValue(btnId, columnNames.get(11)); //SELECTOR
        addValueByValue(btnId, columnNames.get(12)); //TEXT
        addValueByValue(btnId, columnNames.get(13)); //TRACKOR_SELECTOR
        addValueByValue(btnId, columnNames.get(14)); //WIKI
        addValueByValue(btnId, columnNames.get(15)); //MULTI_SELECTOR
        addValueByValue(btnId, columnNames.get(16)); //DATE_TIME
        addValueByValue(btnId, columnNames.get(17)); //TIME
        addValueByValue(btnId, columnNames.get(18)); //TRACKOR_DROPDOWN
        addValueByValue(btnId, columnNames.get(19)); //CALCULATED
        if (columnNames.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            addValueByValue(btnId, columnNames.get(20)); //ROLLUP
        }
        addValueByValue(btnId, columnNames.get(21)); //MULTI_TRACKOR_SELECTOR
    }

    public void checkValues(String selectId, List<String> columnNames) {
        @SuppressWarnings("unchecked")
        List<String> elementsValues = (List<String>) js.getElementsValuesFromDualListBox(selectId);

        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(0)), true, "Select have wrong cnt"); //CHECKBOX
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(1)), true, "Select have wrong cnt"); //DATE
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(2)), true, "Select have wrong cnt"); //DB_DROP_DOWN
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(3)), true, "Select have wrong cnt"); //DB_SELECTOR
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(4)), true, "Select have wrong cnt"); //DROP_DOWN
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(5)), true, "Select have wrong cnt"); //ELECTRONIC_FILE
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(6)), true, "Select have wrong cnt"); //HYPERLINK
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(7)), true, "Select have wrong cnt"); //LATITUDE
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(8)), true, "Select have wrong cnt"); //LONGITUDE
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(9)), true, "Select have wrong cnt"); //MEMO
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(10)), true, "Select have wrong cnt"); //NUMBER
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(11)), true, "Select have wrong cnt"); //SELECTOR
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(12)), true, "Select have wrong cnt"); //TEXT
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(13)), true, "Select have wrong cnt"); //TRACKOR_SELECTOR
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(14)), true, "Select have wrong cnt"); //WIKI
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(15)), true, "Select have wrong cnt"); //MULTI_SELECTOR
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(16)), true, "Select have wrong cnt"); //DATE_TIME
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(17)), true, "Select have wrong cnt"); //TIME
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(18)), true, "Select have wrong cnt"); //TRACKOR_DROPDOWN
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(19)), true, "Select have wrong cnt"); //CALCULATED
        if (columnNames.get(20) != null) { //Workplan and Tasks and Workflow trackor types not support
            Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(20)), true, "Select have wrong cnt"); //ROLLUP
        }
        Assert.assertEquals(htmlSelect.isSelectOptionPresentByValue(elementsValues, columnNames.get(21)), true, "Select have wrong cnt"); //MULTI_TRACKOR_SELECTOR
    }

    private void deselectSelectOptions(final Select select) {
        if (select.isMultiple()) {
            select.deselectAll();
        }
    }

}
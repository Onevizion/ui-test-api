package com.onevizion.uitest.api.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class FieldHistory {

    //TODO split one method to many methods without boolean parameters "checkFieldsHistory"
    //TODO split one method to many methods without boolean parameters "checkFieldHistory"
    //TODO split one method to many methods without boolean parameters "openFieldHistoryForm"
    //TODO remove loop and wait while element will be visible "sub_item_text"

    private static Pattern regexTdContent = Pattern.compile("<TD class.+?>(.+?)</td>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    private static Pattern regexHrefContent = Pattern.compile("<A.+?>(.+?)</A>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    @Autowired
    private Js js;

    @Autowired
    private Wait wait;

    @Autowired
    private Grid grid;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private Window window;

    @Autowired
    private Element element;

    @Autowired
    private Tb tb;

    @Autowired
    private SeleniumSettings seleniumSettings;

    public void checkFieldsHistory(List<String> fieldIds, boolean isShowMenu, int elementPosition, @SuppressWarnings("unchecked") List<String> ... vals) {
        checkFieldHistory(false, fieldIds.get(0), getHistoryValsCheckbox(0, vals), isShowMenu, elementPosition); //CHECKBOX
        checkFieldHistory(false, fieldIds.get(1), getHistoryVals(1, vals), isShowMenu, elementPosition); //DATE
        checkFieldHistory(false, fieldIds.get(2), getHistoryVals(2, vals), isShowMenu, elementPosition); //DB_DROP_DOWN
        checkFieldHistory(false, fieldIds.get(3), getHistoryVals(3, vals), isShowMenu, elementPosition); //DB_SELECTOR
        checkFieldHistory(false, fieldIds.get(4), getHistoryVals(4, vals), isShowMenu, elementPosition); //DROP_DOWN
        checkFieldHistory(false, fieldIds.get(5) + "_disp", getHistoryValsEfile(5, vals), true, elementPosition); //ELECTRONIC_FILE
        checkFieldHistory(false, fieldIds.get(6), getHistoryVals(6, vals), isShowMenu, elementPosition); //HYPERLINK
        checkFieldHistory(false, fieldIds.get(7), getHistoryVals(7, vals), isShowMenu, elementPosition); //LATITUDE
        checkFieldHistory(false, fieldIds.get(8), getHistoryVals(8, vals), isShowMenu, elementPosition); //LONGITUDE
        checkFieldHistory(false, fieldIds.get(9), getHistoryVals(9, vals), isShowMenu, elementPosition); //MEMO
        checkFieldHistory(false, fieldIds.get(10), getHistoryVals(10, vals), isShowMenu, elementPosition); //NUMBER
        checkFieldHistory(false, fieldIds.get(11), getHistoryVals(11, vals), isShowMenu, elementPosition); //SELECTOR
        checkFieldHistory(false, fieldIds.get(12), getHistoryVals(12, vals), isShowMenu, elementPosition); //TEXT
        checkFieldHistory(false, fieldIds.get(13), getHistoryVals(13, vals), isShowMenu, elementPosition); //TRACKOR_SELECTOR
        checkFieldHistory(false, fieldIds.get(14), getHistoryVals(14, vals), isShowMenu, elementPosition); //WIKI
        checkFieldHistory(false, fieldIds.get(15), getHistoryValsMultiSelector(15, vals), isShowMenu, elementPosition); //MULTI_SELECTOR
        checkFieldHistory(false, fieldIds.get(16), getHistoryVals(16, vals), isShowMenu, elementPosition); //DATE_TIME
        checkFieldHistory(false, fieldIds.get(17), getHistoryVals(17, vals), isShowMenu, elementPosition); //TIME
        checkFieldHistory(false, fieldIds.get(18), getHistoryVals(18, vals), isShowMenu, elementPosition); //TRACKOR_DROPDOWN
        //CALCULATED
        //ROLLUP
        checkFieldHistory(false, fieldIds.get(21), getHistoryValsMultiSelector(21, vals), isShowMenu, elementPosition); //MULTI_TRACKOR_SELECTOR
    }

    public void checkFieldHistory(boolean isTaskDate, String fieldId, List<String> vals, boolean isShowMenu, int elementPosition) {
        openFieldHistoryForm(fieldId, isShowMenu, elementPosition);

        int rowsCnt = grid.getGridRowsCount(1L);
        Assert.assertEquals(rowsCnt, vals.size(), "Field History wrong");

        int i = rowsCnt - 1;
        for (String val : vals) {
            String value;
            if (isTaskDate) {
                value = js.getGridCellValueByRowIndexAndColIndex(1L, i, 1);
            } else {
                value = js.getGridCellValueByRowIndexAndColIndex(1L, i, 0);
            }

            Matcher regexTdMatcher = regexTdContent.matcher(value);
            if (regexTdMatcher.find()) {
                value = regexTdMatcher.group(1);
                Matcher regexHrefMatcher = regexHrefContent.matcher(value);
                if (regexHrefMatcher.find()) {
                    value = regexHrefMatcher.group(1);
                }
            } else {
                Matcher regexHrefMatcher = regexHrefContent.matcher(value);
                if (regexHrefMatcher.find()) {
                    value = regexHrefMatcher.group(1);
                }
            }

            value = value.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_1, AbstractSeleniumCore.SPECIAL_CHARACTERS_1);
            value = value.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_2, AbstractSeleniumCore.SPECIAL_CHARACTERS_2);
            value = value.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_3, AbstractSeleniumCore.SPECIAL_CHARACTERS_3);
            value = value.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_4, AbstractSeleniumCore.SPECIAL_CHARACTERS_4);

            //TODO remove isWiki condition (trim)
            //if (wikiIndex != null) {
                Assert.assertEquals(value.replaceAll("<P>", "<p>").replaceAll("</P>", "</p>").trim(), val, "Field History wrong");
            //} else {
            //    Assert.assertEquals(value, val, "Field History wrong");
            //}
            i = i - 1;
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    private void openFieldHistoryForm(String fieldId, boolean isShowMenu, int elementPosition) {
        String id;
        if (elementPosition > 1) {
            id = tb.getLastFieldIndex(fieldId, elementPosition);
            id = id.replace("_disp", ""); //for efile field
            id = id.replace("_start", "").replace("_finish", ""); //for task date
            id = "idx" + id;
        } else {
            id = seleniumSettings.getWebDriver().findElement(By.name(fieldId)).getAttribute("id");
            id = id.replace("_disp", ""); //for efile field
            id = id.replace("_start", "").replace("_finish", ""); //for task date
        }

        String menuItemText = "Field History";
        if (fieldId.contains("_start")) {
            menuItemText = "Start " + menuItemText;
        } else if (fieldId.contains("_finish")) {
            menuItemText = "Finish " + menuItemText;
        }

        if (isShowMenu) {
            element.moveToElementById(id + "_lbl");
            seleniumSettings.getWebDriver().findElement(By.id(id + "_lbl")).click();
            int i = 0;
            do {
                try {
                    for (WebElement webElement : seleniumSettings.getWebDriver().findElements(By.className("sub_item_text"))) {
                        if (menuItemText.equals(webElement.getText())) {
                            window.openModal(webElement);
                            break;
                        }
                    }
                } catch (StaleElementReferenceException e) {
                    
                }
                i++;
            } while (i < 5);
        } else {
            window.openModal(seleniumSettings.getWebDriver().findElement(By.id(id + "_lbl")));
        }

        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        grid2.waitLoad(1L);
    }

    private List<String> getHistoryVals(int position, @SuppressWarnings("unchecked") List<String> ... vals) {
        List<String> result = new ArrayList<>();
        for (List<String> val : vals) {
            if (val.get(position) != null) {
                if (result.isEmpty()) {
                    result.add(val.get(position));
                } else {
                    if (!result.get(result.size() - 1).equals(val.get(position))) {
                        result.add(val.get(position));
                    }
                }
            }
        }
        return result;
    }

    private List<String> getHistoryValsEfile(int position, @SuppressWarnings("unchecked") List<String> ... vals) {
        List<String> result = new ArrayList<>();
        for (List<String> val : vals) {
            if (val.get(position) != null) {
                result.add(val.get(position));
            }
        }
        return result;
    }

    private List<String> getHistoryValsCheckbox(int position, @SuppressWarnings("unchecked") List<String> ... vals) {
        List<String> result = new ArrayList<>();
        for (List<String> val : vals) {
            if (result.isEmpty()) {
                if (val.get(position).equals("YES")) {
                    result.add(val.get(position));
                }
            } else {
                if (!result.get(result.size() - 1).equals(val.get(position))) {
                    result.add(val.get(position));
                }
            }
        }
        return result;
    }

    private List<String> getHistoryValsMultiSelector(int position, @SuppressWarnings("unchecked") List<String> ... vals) {
        List<String> result = new ArrayList<>();
        for (List<String> val : vals) {
            if (result.isEmpty()) {
                result.add(val.get(position).replaceAll(", ", ",").replaceAll(",", ", "));
            } else {
                if (!result.get(result.size() - 1).equals(val.get(position).replaceAll(", ", ",").replaceAll(",", ", "))) {
                    result.add(val.get(position).replaceAll(", ", ",").replaceAll(",", ", "));
                }
            }
        }
        return result;
    }

}
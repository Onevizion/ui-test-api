package com.onevizion.uitest.api.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class FieldHistoryHelper {

    private static Pattern regexTdContent = Pattern.compile("<TD class.+?>(.+?)</td>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    private static Pattern regexHrefContent = Pattern.compile("<A.+?>(.+?)</A>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    @Resource
    private JsHelper jsHelper;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private WindowHelper windowHelper;

    @Resource
    private ElementHelper elementHelper;

    @Resource
    private TbHelper tbHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    public void checkFieldsHistory(List<String> fieldIds, boolean isShowMenu, int elementPosition, @SuppressWarnings("unchecked") List<String> ... vals) {
        checkFieldHistory(false, fieldIds.get(0), getHistoryValsCheckbox(0, vals), isShowMenu, elementPosition); //CHECKBOX
        checkFieldHistory(false, fieldIds.get(1), getHistoryVals(1, vals), isShowMenu, elementPosition); //DATE
        checkFieldHistory(false, fieldIds.get(2), getHistoryVals(2, vals), isShowMenu, elementPosition); //DB_DROP_DOWN
        checkFieldHistory(false, fieldIds.get(3), getHistoryVals(3, vals), isShowMenu, elementPosition); //DB_SELECTOR
        checkFieldHistory(false, fieldIds.get(4), getHistoryVals(4, vals), isShowMenu, elementPosition); //DROP_DOWN
        checkFieldHistory(false, fieldIds.get(5) + "_disp", getHistoryValsEfile(5, vals), true, elementPosition); //ELECTRONIC_FILE
        if (fieldIds.get(6) != null) {
            checkFieldHistory(false, fieldIds.get(6), getHistoryVals(6, vals), isShowMenu, elementPosition); //HYPERLINK
        }
        checkFieldHistory(false, fieldIds.get(7), getHistoryVals(7, vals), isShowMenu, elementPosition); //LATITUDE
        checkFieldHistory(false, fieldIds.get(8), getHistoryVals(8, vals), isShowMenu, elementPosition); //LONGITUDE
        checkFieldHistory(false, fieldIds.get(9), getHistoryVals(9, vals), isShowMenu, elementPosition); //MEMO
        checkFieldHistory(false, fieldIds.get(10), getHistoryVals(10, vals), isShowMenu, elementPosition); //NUMBER
        checkFieldHistory(false, fieldIds.get(11), getHistoryVals(11, vals), isShowMenu, elementPosition); //SELECTOR
        checkFieldHistory(false, fieldIds.get(12), getHistoryVals(12, vals), isShowMenu, elementPosition); //TEXT
        checkFieldHistory(false, fieldIds.get(13), getHistoryVals(13, vals), isShowMenu, elementPosition); //TRACKOR_SELECTOR
        checkFieldHistory(false, fieldIds.get(14), getHistoryVals(14, vals), isShowMenu, elementPosition); //WIKI
        if (fieldIds.get(15) != null) {
            checkFieldHistory(false, fieldIds.get(15), getHistoryValsMultiSelector(15, vals), isShowMenu, elementPosition); //MULTI_SELECTOR
        }
        checkFieldHistory(false, fieldIds.get(16), getHistoryVals(16, vals), isShowMenu, elementPosition); //DATE_TIME
        checkFieldHistory(false, fieldIds.get(17), getHistoryVals(17, vals), isShowMenu, elementPosition); //TIME
        checkFieldHistory(false, fieldIds.get(18), getHistoryVals(18, vals), isShowMenu, elementPosition); //TRACKOR_DROPDOWN
    }

    public void checkFieldHistory(boolean isTaskDate, String fieldId, List<String> vals, boolean isShowMenu, int elementPosition) {
        openFieldHistoryForm(fieldId, isShowMenu, elementPosition);

        Long rowsCnt = gridHelper.getGridRowsCount(1L);
        Assert.assertEquals(rowsCnt.intValue(), vals.size(), "Field History wrong");

        Long i = rowsCnt - 1L;
        for (String val : vals) {
            String value;
            if (isTaskDate) {
                value = jsHelper.getGridCellValueByRowIndexAndColIndex(1L, i, 1L);
            } else {
                value = jsHelper.getGridCellValueByRowIndexAndColIndex(1L, i, 0L);
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
            i = i - 1L;
        }

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    private void openFieldHistoryForm(String fieldId, boolean isShowMenu, int elementPosition) {
        String id;
        if (elementPosition > 1) {
            id = tbHelper.getLastFieldIndex(fieldId, elementPosition);
            id = "idx" + id;
        } else {
            id = seleniumSettings.getWebDriver().findElement(By.xpath("//*[string(@submitName)='" + fieldId + "'] | //*[string(@name)='" + fieldId + "']")).getAttribute("id");
            id = id.replace("_disp", ""); //for efile field
        }

        if (isShowMenu) {
            elementHelper.moveToElementById(id + "_lbl");
            seleniumSettings.getWebDriver().findElement(By.id(id + "_lbl")).click();
            int i = 0;
            do {
                try {
                    for (WebElement element : seleniumSettings.getWebDriver().findElements(By.className("sub_item_text"))) {
                        if ("Field History".equals(element.getText())) {
                            windowHelper.openModal(element);
                            break;
                        }
                    }
                } catch (StaleElementReferenceException e) {
                    
                }
                i++;
            } while (i < 5);
        } else {
            windowHelper.openModal(seleniumSettings.getWebDriver().findElement(By.id(id + "_lbl")));
        }

        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        waitHelper.waitGridLoad(1L, 1L);
    }

    private List<String> getHistoryVals(int position, @SuppressWarnings("unchecked") List<String> ... vals) {
        List<String> result = new ArrayList<String>();
        for (List<String> val : vals) {
            if (val.get(position) != null) {
                if (result.size() == 0) {
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
        List<String> result = new ArrayList<String>();
        for (List<String> val : vals) {
            if (val.get(position) != null) {
                result.add(val.get(position));
            }
        }
        return result;
    }

    private List<String> getHistoryValsCheckbox(int position, @SuppressWarnings("unchecked") List<String> ... vals) {
        List<String> result = new ArrayList<String>();
        for (List<String> val : vals) {
            if (result.size() == 0) {
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
        List<String> result = new ArrayList<String>();
        for (List<String> val : vals) {
            if (result.size() == 0) {
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
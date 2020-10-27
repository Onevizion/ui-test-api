package com.onevizion.uitest.api.helper.comment;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Tb;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class Comment {

    @Autowired
    private Tb tb;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Element element;

    @Autowired
    private CommentJs commentJs;

    @Autowired
    private Wait wait;

    @Autowired
    private Grid grid;

    @Autowired
    private Js js;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private ElementWait elementWait;

    public void openCommentFormFromForm(String fieldId, boolean isShowMenu, int elementPosition) {
        String id;
        if (elementPosition > 1) {
            //id = tbHelper.getLastFieldIndex(fieldId, elementPosition);
            id = "";
            id = "idx" + id;
        } else {
            id = seleniumSettings.getWebDriver().findElement(By.name(fieldId)).getAttribute("id");
            id = id.replace("_disp", ""); //for efile field
        }

        if (isShowMenu) {
            element.moveToElementById(id + "_lbl");
            seleniumSettings.getWebDriver().findElement(By.id(id + "_lbl")).click();
            int i = 0;
            do {
                try {
                    for (WebElement webElement : seleniumSettings.getWebDriver().findElements(By.className("sub_item_text"))) {
                        if ("Comments".equals(webElement.getText())) {
                            //windowHelper.openModal(element);
                            openCommentForm(webElement);
                            break;
                        }
                    }
                } catch (StaleElementReferenceException e) {
                    
                }
                i++;
            } while (i < 5);
        } else {
            //windowHelper.openModal(seleniumSettings.getWebDriver().findElement(By.id(id + "_lbl")));
        }

        //waitHelper.waitWebElement(By.id(AbstractSeleniumInternalPage.BUTTON_CANCEL_ID_BASE));
        //waitHelper.waitGridLoad(1L, 1L);
    }

    public void openCommentFormFromGrid(int rowIndex, int columnIndex) {
        tb.rightClickCell(0L, rowIndex, columnIndex);

        int i = 0;
        do {
            try {
                for (WebElement webElement : seleniumSettings.getWebDriver().findElements(By.className("sub_item_text"))) {
                    if ("Comments".equals(webElement.getText())) {
                        //windowHelper.openModal(element);
                        openCommentForm(webElement);
                        break;
                    }
                }
            } catch (StaleElementReferenceException e) {
                
            }
            i++;
        } while (i < 5);
    }

    private void openCommentForm(WebElement webElement) {
        webElement.click();

        wait.waitWebElement(By.className("dhxwin_active"));

        WebElement iframe = commentJs.getIframe();

        seleniumSettings.getWebDriver().switchTo().frame(iframe);
        wait.waitWebElement(By.id("comment"));
        wait.waitWebElement(By.id("btnSubmit"));
        wait.waitFormLoad();
        grid2.waitLoad();
    }

    public void addComment(String text) {
        elementWait.waitElementDisabledById("btnSubmit");
        elementWait.waitElementAttributeById("comment", "value", "");

        int rowsCntBefore = grid.getGridRowsCount(0L);

        seleniumSettings.getWebDriver().findElement(By.id("comment")).sendKeys(text);
        elementWait.waitElementEnabledById("btnSubmit");
        seleniumSettings.getWebDriver().findElement(By.id("btnSubmit")).findElement(By.xpath("..")).click();

        grid2.waitLoad();

        wait.waitGridRowsCount(0L, rowsCntBefore + 1);

        elementWait.waitElementDisabledById("btnSubmit");
        elementWait.waitElementAttributeById("comment", "value", "");

        AbstractSeleniumCore.correctSleep(500L);
    }

    public void deleteComment(String text) {
        int rowsCntBefore = grid.getGridRowsCount(0L);

        Long rowIndex = null;
        for (int i = 0; i < rowsCntBefore; i++) {
            String value = js.getGridCellValueByRowIndexAndColIndex(0L, i, 0);
            if (text.equals(value)) {
                if (rowIndex != null) {
                    throw new SeleniumUnexpectedException("Found many rows with same value[" + text + "]");
                }
                rowIndex = (long) i;
            }
        }

        if (rowIndex == null) {
            throw new SeleniumUnexpectedException("Row with value[" + text + "] not found");
        }

        js.selectGridRow(0L, rowIndex.intValue());
        String rowId = js.getGridRowIdByIndex(0L, rowIndex.intValue());

        elementWait.waitElementById("btnDelete" + rowId);
        elementWait.waitElementVisibleById("btnDelete" + rowId);
        elementWait.waitElementDisplayById("btnDelete" + rowId);

        seleniumSettings.getWebDriver().findElement(By.id("btnDelete" + rowId)).click();

        wait.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();

        grid2.waitLoad();

        wait.waitGridRowsCount(0L, rowsCntBefore - 1);
    }

    public void closeCommentForm() {
        seleniumSettings.getWebDriver().switchTo().parentFrame();

        seleniumSettings.getWebDriver().findElement(By.className("dhxwin_active")).findElement(By.className("dhxwin_button_close")).click();
    }

}
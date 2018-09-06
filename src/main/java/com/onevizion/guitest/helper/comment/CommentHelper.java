package com.onevizion.guitest.helper.comment;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.exception.SeleniumUnexpectedException;
import com.onevizion.guitest.helper.ElementHelper;
import com.onevizion.guitest.helper.ElementWaitHelper;
import com.onevizion.guitest.helper.GridHelper;
import com.onevizion.guitest.helper.JsHelper;
import com.onevizion.guitest.helper.TbHelper;
import com.onevizion.guitest.helper.WaitHelper;

@Component
public class CommentHelper {

    @Resource
    private TbHelper tbHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementHelper elementHelper;

    @Resource
    private CommentJsHelper commentJsHelper;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    public void openCommentFormFromForm(String fieldId, boolean isShowMenu, int elementPosition) {
        String id;
        if (elementPosition > 1) {
            //id = tbHelper.getLastFieldIndex(fieldId, elementPosition);
            id = "";
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
                        if ("Comments".equals(element.getText())) {
                            //windowHelper.openModal(element);
                            openCommentForm(element);
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

    public void openCommentFormFromGrid(Long rowIndex, Long columnIndex) {
        tbHelper.rightClickCell(0L, rowIndex, columnIndex);

        int i = 0;
        do {
            try {
                for (WebElement element : seleniumSettings.getWebDriver().findElements(By.className("sub_item_text"))) {
                    if ("Comments".equals(element.getText())) {
                        //windowHelper.openModal(element);
                        openCommentForm(element);
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

        waitHelper.waitWebElement(By.className("dhxwin_active"));

        WebElement iframe = commentJsHelper.getIframe();

        seleniumSettings.getWebDriver().switchTo().frame(iframe);
        waitHelper.waitWebElement(By.id("comment"));
        waitHelper.waitWebElement(By.id("btnSubmit"));
        waitHelper.waitFormLoad();
        waitHelper.waitGridLoad(0L, 0L);
    }

    public void addComment(String text) {
        elementWaitHelper.waitElementDisabledById("btnSubmit");
        elementWaitHelper.waitElementAttributeById("comment", "value", "");

        Long rowsCntBefore = gridHelper.getGridRowsCount(0L);

        seleniumSettings.getWebDriver().findElement(By.id("comment")).sendKeys(text);
        elementWaitHelper.waitElementEnabledById("btnSubmit");
        seleniumSettings.getWebDriver().findElement(By.id("btnSubmit")).findElement(By.xpath("..")).click();

        waitHelper.waitGridLoad(0L, 0L);

        waitHelper.waitGridRowsCount(0L, rowsCntBefore + 1L);

        elementWaitHelper.waitElementDisabledById("btnSubmit");
        elementWaitHelper.waitElementAttributeById("comment", "value", "");

        correctSleep(500L);
    }

    public void deleteComment(String text) {
        Long rowsCntBefore = gridHelper.getGridRowsCount(0L);

        Long rowIndex = null;
        for (Long i = 0L; i < rowsCntBefore; i++) {
            String value = jsHelper.getGridCellValueByRowIndexAndColIndex(0L, i, 0L);
            if (text.equals(value)) {
                if (rowIndex != null) {
                    throw new SeleniumUnexpectedException("Found many rows with same value[" + text + "]");
                }
                rowIndex = i;
            }
        }

        if (rowIndex == null) {
            throw new SeleniumUnexpectedException("Row with value[" + text + "] not found");
        }

        jsHelper.selectGridRow(0L, rowIndex);
        String rowId = jsHelper.getGridRowIdByIndex(0L, rowIndex);

        elementWaitHelper.waitElementById("btnDelete" + rowId);
        elementWaitHelper.waitElementVisibleById("btnDelete" + rowId);
        elementWaitHelper.waitElementDisplayById("btnDelete" + rowId);

        seleniumSettings.getWebDriver().findElement(By.id("btnDelete" + rowId)).click();

        waitHelper.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();

        waitHelper.waitGridLoad(0L, 0L);

        waitHelper.waitGridRowsCount(0L, rowsCntBefore - 1L);
    }

    public void closeCommentForm() {
        seleniumSettings.getWebDriver().switchTo().parentFrame();

        seleniumSettings.getWebDriver().findElement(By.className("dhxwin_active")).findElement(By.className("dhxwin_button_close")).click();
    }

    private void correctSleep(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
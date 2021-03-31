package com.onevizion.uitest.api.helper.workplan;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class Workplan {

    private static final String TEMPLATE_WP_NAME = "cbTmplWpid";
    private static final String WP_NAME = "name";
    private static final String START_DATE = "start";
    private static final String FINISH_DATE = "finish";
    private static final String AUTO_CALC_LAG_TO_PRED = "autoCalcLagToPred";
    private static final String DONT_SHIFT_PROJ_DATES = "dontShiftProjDates";
    private static final String ACTIVE = "active";

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Window window;

    @Autowired
    private Wait wait;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private Checkbox checkbox;

    public void openAddForm(Long gridIdx) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void closeFormOk(Long gridIdx) {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad(gridIdx);
    }

    public void closeFormCancel() {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void changeTemplateWpName(String templateWpName) {
        new Select(seleniumSettings.getWebDriver().findElement(By.name(TEMPLATE_WP_NAME))).selectByVisibleText(templateWpName);
    }

    public void changeWpName(String wpName) {
        seleniumSettings.getWebDriver().findElement(By.name(WP_NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(WP_NAME)).sendKeys(wpName);
    }

    public void changeStartDate(String startDate) {
        seleniumSettings.getWebDriver().findElement(By.name(START_DATE)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(START_DATE)).sendKeys(startDate);
    }

    public void changeFinishDate(String finishDate) {
        seleniumSettings.getWebDriver().findElement(By.name(FINISH_DATE)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(FINISH_DATE)).sendKeys(finishDate);
    }

    public void changeAutoCalcLagToPred(String autoCalcLagToPred) {
        if ((autoCalcLagToPred.equals("YES") && !checkbox.isCheckedByName(AUTO_CALC_LAG_TO_PRED))
                || (autoCalcLagToPred.equals("NO") && checkbox.isCheckedByName(AUTO_CALC_LAG_TO_PRED))) {
            checkbox.clickByName(AUTO_CALC_LAG_TO_PRED);
        }
    }

    public void changeDontShiftProjDates(String dontShiftProjDates) {
        if ((dontShiftProjDates.equals("YES") && !checkbox.isCheckedByName(DONT_SHIFT_PROJ_DATES))
                || (dontShiftProjDates.equals("NO") && checkbox.isCheckedByName(DONT_SHIFT_PROJ_DATES))) {
            checkbox.clickByName(DONT_SHIFT_PROJ_DATES);
        }
    }

    public void changeActive(String active) {
        if ((active.equals("YES") && !checkbox.isCheckedByName(ACTIVE))
                || (active.equals("NO") && checkbox.isCheckedByName(ACTIVE))) {
            checkbox.clickByName(ACTIVE);
        }
    }

}
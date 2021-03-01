package com.onevizion.uitest.api.helper.grid;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.vo.LockType;

@Component
public class Grid2 {

    private static final String SAVE_PANEL_ID_BASE = "savePanel";
    private static final String PROGRESS_BAR_ID_BASE = "progressBar";

    @Autowired
    private Grid2Wait grid2Wait;

    @Autowired
    private Grid2Js grid2Js;

    @Autowired
    private Wait wait;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired //TODO bug in Grid-115098 load views/filters before load grid
    private Jquery jquery; //TODO bug in Grid-115098 load views/filters before load grid

    public void waitLoad() {
        waitLoad(AbstractSeleniumCore.getGridIdx());
    }

    public void waitLoad(Long gridIdx) {
        jquery.waitLoad(); //TODO bug in Grid-115098 load views/filters before load grid
        wait.waitWebElement(By.id(AbstractSeleniumCore.GRID_ID_BASE + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + gridIdx));

        Long parentGridIdx;
        if (grid2Js.getIsSubGrid(gridIdx)) {
            parentGridIdx = grid2Js.getParentGridIdx(gridIdx);
        } else {
            parentGridIdx = gridIdx;
        }

        wait.waitWebElement(By.id(AbstractSeleniumCore.GRID_ID_BASE + parentGridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + parentGridIdx));

        elementWait.waitElementNotVisibleById(AbstractSeleniumCore.LOADING_ID_BASE + parentGridIdx);
        elementWait.waitElementNotDisplayById(AbstractSeleniumCore.LOADING_ID_BASE + parentGridIdx);

        grid2Wait.waitIsGridLoaded(gridIdx);
        grid2Wait.waitIsGridDataLoaded(gridIdx);
        grid2Wait.waitIsGridAllRowsLoaded(gridIdx);
        grid2Wait.waitIsGridUpdated(gridIdx);
    }

    public void waitProgressBarLoad() {
        waitProgressBarLoad(AbstractSeleniumCore.getGridIdx());
    }

    public void waitProgressBarLoad(Long gridIdx) {
        elementWait.waitElementNotVisibleById(PROGRESS_BAR_ID_BASE + gridIdx);
        elementWait.waitElementNotDisplayById(PROGRESS_BAR_ID_BASE + gridIdx);
    }

    public void saveChanges(Long gridId) {
        elementWait.waitElementEnabledById(AbstractSeleniumCore.BUTTON_SAVE_GRID_ID_BASE + gridId);
        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_SAVE_GRID_ID_BASE + gridId)).click();

        elementWait.waitElementNotVisibleById(AbstractSeleniumCore.SAVING_ID_BASE + gridId);
        elementWait.waitElementNotDisplayById(AbstractSeleniumCore.SAVING_ID_BASE + gridId);
        elementWait.waitElementNotVisibleById(AbstractSeleniumCore.LOADING_ID_BASE + gridId);
        elementWait.waitElementNotDisplayById(AbstractSeleniumCore.LOADING_ID_BASE + gridId);
        elementWait.waitElementNotVisibleById(SAVE_PANEL_ID_BASE + gridId);
        elementWait.waitElementNotDisplayById(SAVE_PANEL_ID_BASE + gridId);
    }

    public void saveChangesByCtrlS(Long gridId) {
        Actions actionObject = new Actions(seleniumSettings.getWebDriver());
        actionObject.keyDown(Keys.CONTROL).sendKeys("s").keyUp(Keys.CONTROL).perform();
    }

    public void saveChangesByCtrlSAndWait(Long gridId) {
        saveChangesByCtrlS(gridId);

        elementWait.waitElementNotVisibleById(AbstractSeleniumCore.SAVING_ID_BASE + gridId);
        elementWait.waitElementNotDisplayById(AbstractSeleniumCore.SAVING_ID_BASE + gridId);
        elementWait.waitElementNotVisibleById(AbstractSeleniumCore.LOADING_ID_BASE + gridId);
        elementWait.waitElementNotDisplayById(AbstractSeleniumCore.LOADING_ID_BASE + gridId);
        elementWait.waitElementNotVisibleById(SAVE_PANEL_ID_BASE + gridId);
        elementWait.waitElementNotDisplayById(SAVE_PANEL_ID_BASE + gridId);
    }

    public void waitSavePanelVisible(Long gridId) {
        elementWait.waitElementVisibleById(SAVE_PANEL_ID_BASE + gridId);
        elementWait.waitElementDisplayById(SAVE_PANEL_ID_BASE + gridId);
    }

    public void waitSavePanelHidden(Long gridId) {
        elementWait.waitElementNotVisibleById(SAVE_PANEL_ID_BASE + gridId);
        elementWait.waitElementNotDisplayById(SAVE_PANEL_ID_BASE + gridId);
    }

    public void waitGridCellLockType(Long gridId, int columnIndex, int rowIndex, LockType lockType) {
        grid2Wait.waitGridCellLockType(gridId, columnIndex, rowIndex, lockType);
    }

    public String getPageName(Long gridId) {
        return grid2Js.getPageName(gridId);
    }

    public List<String> getGridCellAllChildsFontColor(Long gridIdx, int rowIndex, int columnIndex) {
        return grid2Js.getGridCellAllChildsFontColor(gridIdx, rowIndex, columnIndex);
    }

    public List<String> getGridCellAllChildsBackgroundColor(Long gridIdx, int rowIndex, int columnIndex) {
        return grid2Js.getGridCellAllChildsBackgroundColor(gridIdx, rowIndex, columnIndex);
    }

}
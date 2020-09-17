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

    public void waitLoad(Long gridId) {
        jquery.waitLoad(); //TODO bug in Grid-115098 load views/filters before load grid
        wait.waitWebElement(By.id(AbstractSeleniumCore.GRID_ID_BASE + gridId));
        wait.waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + gridId));

        Long parentGridId;
        if (grid2Js.getIsSubGrid(gridId)) {
            parentGridId = grid2Js.getParentGridIdx(gridId);
        } else {
            parentGridId = gridId;
        }

        wait.waitWebElement(By.id(AbstractSeleniumCore.GRID_ID_BASE + parentGridId));
        wait.waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + parentGridId));

        elementWait.waitElementNotVisibleById(AbstractSeleniumCore.LOADING_ID_BASE + parentGridId);
        elementWait.waitElementNotDisplayById(AbstractSeleniumCore.LOADING_ID_BASE + parentGridId);

        grid2Wait.waitIsGridLoaded(gridId);
        grid2Wait.waitIsGridDataLoaded(gridId);
        grid2Wait.waitIsGridAllRowsLoaded(gridId);
        grid2Wait.waitIsGridUpdated(gridId);
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
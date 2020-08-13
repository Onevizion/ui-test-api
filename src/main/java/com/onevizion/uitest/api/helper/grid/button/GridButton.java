package com.onevizion.uitest.api.helper.grid.button;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class GridButton {

    private static final String BUTTON_OPTIONS_ID_BASE = "btnoptions";
    private static final String BUTTON_OPTIONS_PANEL_ID_BASE = "optionsGrouppopupMenu";

    private static final String BUTTON_COLORS_ID_BASE = "itemColors";

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private Window window;

    @Autowired
    private Grid2 grid2;

    public void openColorsGrid(Long gridIdx) {
        openOptionsPanel(gridIdx);

        elementWait.waitElementById(BUTTON_COLORS_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_COLORS_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_COLORS_ID_BASE + gridIdx);

        window.openModal(By.id(BUTTON_COLORS_ID_BASE + gridIdx));
        grid2.waitLoad();
    }

    private void openOptionsPanel(Long gridIdx) {
        elementWait.waitElementById(BUTTON_OPTIONS_ID_BASE + gridIdx);
        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_OPTIONS_ID_BASE + gridIdx)).click();

        elementWait.waitElementById(BUTTON_OPTIONS_PANEL_ID_BASE + gridIdx);
        elementWait.waitElementVisibleById(BUTTON_OPTIONS_PANEL_ID_BASE + gridIdx);
        elementWait.waitElementDisplayById(BUTTON_OPTIONS_PANEL_ID_BASE + gridIdx);
    }

}
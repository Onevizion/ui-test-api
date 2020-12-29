package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class EntityComponentFieldHistory {

    private static final String COLUMN_ID_USER = "2";
    private static final String COLUMN_ID_VALUE = "0";

    @Autowired
    private Wait wait;

    @Autowired
    private Window window;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private Grid grid;

    @Autowired
    private SeleniumSettings seleniumSettings;

    public void testOnForm(String componentFieldElementId, List<String> values) {
        window.openModal(By.id(componentFieldElementId));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        wait.waitFormLoad();

        grid2.waitLoad(1L);

        for(int i = 0; i < values.size(); i++) {
            checkGridRowByRowIndex(1L, i, values.get(i));
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    private void checkGridRowByRowIndex(Long gridId, int rowIndex, String newVal) {
        Map<String, String> gridVals = new HashMap<>();

        gridVals.put(COLUMN_ID_USER, seleniumSettings.getTestUser());
        gridVals.put(COLUMN_ID_VALUE, newVal);

        grid.checkGridRowByRowIndex(gridId, rowIndex, gridVals);
    }

}
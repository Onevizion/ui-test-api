package com.onevizion.uitest.api.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class CompAuditLog {

    private static final String COLUMN_ID_USER = "3";
    private static final String COLUMN_ID_ACTION = "5";
    private static final String COLUMN_ID_TABLE = "7";
    private static final String COLUMN_ID_FIELD = "8";
    private static final String COLUMN_ID_NEW_VAL = "9";
    private static final String COLUMN_ID_OLD_VAL = "10";

    private static final String COMPONENT_FIELD_HISTORY_COLUMN_ID_USER = "2";
    private static final String COMPONENT_FIELD_HISTORY_COLUMN_ID_VALUE = "0";

    @Autowired
    private Js js;

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

    public void checkGridRowsByRowIndex(Long gridId, int rowIndexStart, int rowIndexEnd, String action, String table) {
        Map<String, String> gridVals = new HashMap<>();

        gridVals.put(COLUMN_ID_USER, seleniumSettings.getTestUser());
        gridVals.put(COLUMN_ID_ACTION, action);
        gridVals.put(COLUMN_ID_TABLE, table);

        grid.checkGridRowsByRowIndex(gridId, rowIndexStart, rowIndexEnd, gridVals);
    }

    public int checkGridRowByRowIndex(Long gridId, int rowIndex, String action, String table, String field, String newVal, String oldVal) {
        Map<String, String> gridVals = new HashMap<>();

        gridVals.put(COLUMN_ID_USER, seleniumSettings.getTestUser());
        gridVals.put(COLUMN_ID_ACTION, action);
        gridVals.put(COLUMN_ID_TABLE, table);
        gridVals.put(COLUMN_ID_FIELD, field);
        gridVals.put(COLUMN_ID_NEW_VAL, newVal);
        gridVals.put(COLUMN_ID_OLD_VAL, oldVal);

        grid.checkGridRowByRowIndex(gridId, rowIndex, gridVals);

        rowIndex = rowIndex + 1;

        return rowIndex;
    }

    public void checkCompFieldHistory(String componentFieldElementId, List<String> values) {
        window.openModal(By.id(componentFieldElementId));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        wait.waitFormLoad();

        grid2.waitLoad(1L);

        int rowsCount = js.getGridRowsCount(1L);
        Assert.assertEquals(rowsCount, values.size());

        for(int i = 0; i < values.size(); i++) {
            checkCompFieldHistoryGridRowByRowIndex(1L, i, values.get(i));
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    private void checkCompFieldHistoryGridRowByRowIndex(Long gridId, int rowIndex, String newVal) {
        Map<String, String> gridVals = new HashMap<>();

        gridVals.put(COMPONENT_FIELD_HISTORY_COLUMN_ID_USER, seleniumSettings.getTestUser());
        gridVals.put(COMPONENT_FIELD_HISTORY_COLUMN_ID_VALUE, newVal);

        grid.checkGridRowByRowIndex(gridId, rowIndex, gridVals);
    }

}
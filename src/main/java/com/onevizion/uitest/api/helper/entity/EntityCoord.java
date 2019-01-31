package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.PsSelector;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.vo.entity.Coord;

@Component
public class EntityCoord {

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private PsSelector psSelector;

    @Resource
    private Js js;

    @Resource
    private Grid grid;

    public void add(Coord coord) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        psSelector.selectSpecificValue(By.id("btnlatitudeField"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, coord.getLatFieldName(), 1L);

        psSelector.selectSpecificValue(By.id("btnlongitudeField"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, coord.getLongFieldName(), 1L);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testInGrid(Long gridId, Long rowIndex, Coord coord) {
        Map<Long, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Latitude Field Name"), coord.getLatFieldName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Latitude Field Label"), coord.getLatFieldLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Longitude Field Name"), coord.getLongFieldName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Longitude Field Label"), coord.getLongFieldLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Trackor Type"), coord.getTrackorTypeLabel());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
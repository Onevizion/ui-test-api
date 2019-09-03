package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.PsSelector;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.vo.entity.Coord;

@Component
public class EntityCoord {

    private static final String NAME = "name";
    private static final String LATITUDE = "latitudeField";
    private static final String LATITUDE_BUTTON = "btnlatitudeField";
    private static final String LONGITUDE = "longitudeField";
    private static final String LONGITUDE_BUTTON = "btnlongitudeField";

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

    @Resource
    private Grid2 grid2;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private AssertElement assertElement;

    public void add(Coord coord) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(coord.getName());

        psSelector.selectSpecificValue(By.name(LATITUDE_BUTTON), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, coord.getLatFieldName(), 1L);

        psSelector.selectSpecificValue(By.name(LONGITUDE_BUTTON), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, coord.getLongFieldName(), 1L);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(Coord coord) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(coord.getName());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testOnForm(Coord coord) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertText(NAME, coord.getName());
        assertElement.assertRadioPsSelector(LATITUDE, null, null, coord.getLatFieldName(), null, false);
        assertElement.assertRadioPsSelector(LONGITUDE, null, null, coord.getLongFieldName(), null, false);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, Coord coord) {
        Map<Long, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Pair Name"), coord.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Latitude Field Name"), coord.getLatFieldName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Latitude Field Label"), coord.getLatFieldLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Longitude Field Name"), coord.getLongFieldName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Longitude Field Label"), coord.getLongFieldLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Trackor Type"), coord.getTrackorTypeLabel());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}
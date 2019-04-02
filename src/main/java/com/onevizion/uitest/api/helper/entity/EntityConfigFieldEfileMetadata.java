package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
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
import com.onevizion.uitest.api.vo.entity.ConfigFieldVoEfileMetadata;

@Component
public class EntityConfigFieldEfileMetadata {

    private static final String TYPE = "fileMetadataTypeId";
    private static final String FIELD = "metadataCfid";
    private static final String FIELD_BUTTON = "btnmetadataCfid";

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private PsSelector psSelector;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Grid grid;

    @Resource
    private Grid2 grid2;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Js js;

    public void add(ConfigFieldVoEfileMetadata configFieldVoEfileMetadata) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + 3L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        new Select(seleniumSettings.getWebDriver().findElement(By.name(TYPE))).selectByVisibleText(configFieldVoEfileMetadata.getType().getLabel());

        psSelector.selectSpecificValue(By.name(FIELD_BUTTON), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, configFieldVoEfileMetadata.getConfigField(), 1L);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad(3L);
    }

    public void testOnForm(ConfigFieldVoEfileMetadata configFieldVoEfileMetadata) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + 3L));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertSelect(TYPE, configFieldVoEfileMetadata.getType().getLabel());
        assertElement.assertRadioPsSelector(FIELD, FIELD_BUTTON, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVoEfileMetadata.getConfigField(), 1L, true);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, ConfigFieldVoEfileMetadata configFieldVoEfileMetadata) {
        Map<Long, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Metadata Type"), configFieldVoEfileMetadata.getType().getLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Metadata Config Field"), configFieldVoEfileMetadata.getConfigField());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

    public void removeAll() {
        while (!grid.isGridEmpty(3L)) {
            grid.deleteCurrentRow(3L);
        }
    }

}
package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Selector;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.form.Form;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.vo.entity.ConfigFieldVoEfileMetadata;

@Component
public class EntityConfigFieldEfileMetadata {

    private static final String TYPE = "fileMetadataTypeId";
    private static final String FIELD = "metadataCfid";
    private static final String FIELD_BUTTON = "btnmetadataCfid";

    @Autowired
    private Window window;

    @Autowired
    private Selector selector;

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Form form;

    @Autowired
    private Grid grid;

    @Autowired
    private Grid2 grid2;

    @Autowired
    private AssertElement assertElement;

    @Autowired
    private Js js;

    public void add(ConfigFieldVoEfileMetadata configFieldVoEfileMetadata) {
        form.openAddWithoutTabs(3);

        new Select(seleniumSettings.getWebDriver().findElement(By.name(TYPE))).selectByVisibleText(configFieldVoEfileMetadata.getType().getLabel());

        selector.selectRadio(By.name(FIELD_BUTTON), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1, configFieldVoEfileMetadata.getConfigField(), 1L);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad(3L);
    }

    public void testOnForm(ConfigFieldVoEfileMetadata configFieldVoEfileMetadata) {
        form.openEditWithoutTabs(3);

        assertElement.assertSelect(TYPE, configFieldVoEfileMetadata.getType().getLabel());
        assertElement.assertRadioPsSelector(FIELD, FIELD_BUTTON, AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVoEfileMetadata.getConfigField(), 1L, true);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, int rowIndex, ConfigFieldVoEfileMetadata configFieldVoEfileMetadata) {
        Map<Integer, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Metadata Type"), configFieldVoEfileMetadata.getType().getLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Metadata Field"), configFieldVoEfileMetadata.getConfigField());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

    public void removeAll() {
        while (!grid.isGridEmpty(3L)) {
            grid.deleteCurrentRow(3L);
        }
    }

}
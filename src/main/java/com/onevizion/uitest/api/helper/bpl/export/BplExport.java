package com.onevizion.uitest.api.helper.bpl.export;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Qs;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.vo.BplComponentType;

@Component
public class BplExport {

    private static final String COLUMN_LABEL = "Component Name";

    @Resource
    private Js js;

    @Resource
    private Wait wait;

    @Resource
    private Qs qs;

    @Resource
    private Checkbox checkbox;

    @Resource
    private Grid grid;

    public void openSubGrid(Long gridIdx, BplComponentType bplComponentType) {
        Long rowIndex = getComponentTypeRowIndex(gridIdx, bplComponentType);
        Long subGridId = getComponentTypeGridId(gridIdx, bplComponentType);

        js.openSubGrid(gridIdx, rowIndex, 0L);
        wait.waitGridLoad(subGridId, gridIdx);
    }

    public void closeSubGrid(Long gridIdx, BplComponentType bplComponentType) {
        Long rowIndex = getComponentTypeRowIndex(gridIdx, bplComponentType);

        js.openSubGrid(gridIdx, rowIndex, 0L);
    }

    public void checkComponentsCount(Long gridIdx, BplComponentType bplComponentType, Long count) {
        Long subGridId = getComponentTypeGridId(gridIdx, bplComponentType);

        Assert.assertEquals(grid.getGridRowsCount(subGridId), count);
    }

    public void selectComponent(Long gridIdx, BplComponentType bplComponentType, String componentName) {
        Long subGridId = getComponentTypeGridId(gridIdx, bplComponentType);

        Long columnIndex = js.getColumnIndexByLabel(subGridId, bplComponentType.getColumnName());
        qs.searchValue(subGridId, bplComponentType.getColumnName(), "\"" + componentName + "\"");
        Assert.assertEquals(js.getGridCellValueByRowIndexAndColIndex(subGridId, 0L, columnIndex), componentName);
        checkbox.clickById("cb" + js.getGridSelectedRowId(subGridId));
    }

    private Long getComponentTypeGridId(Long gridIdx, BplComponentType bplComponentType) {
        Long rowIndex = getComponentTypeRowIndex(gridIdx, bplComponentType);

        js.selectGridRow(gridIdx, rowIndex);
        return NumberUtils.createLong(js.getGridSelectedRowId(gridIdx));
    }

    @SuppressWarnings("unchecked")
    private Long getComponentTypeRowIndex(Long gridIdx, BplComponentType bplComponentType) {
        Long rowIndex = null;
        Long rowsCount = js.getGridRowsCount(gridIdx);
        Long columnIndex = js.getColumnIndexByLabel(gridIdx, COLUMN_LABEL);
        List<String> vals = (List<String>) js.getGridCellsValuesForColumnByColIndex(gridIdx, rowsCount, columnIndex);
        for (int i = 0; i < rowsCount; i++) {
            if (bplComponentType.getName().equals(vals.get(i))) {
                rowIndex = Long.valueOf(i);
            }
        }

        if (rowIndex == null) {
            throw new SeleniumUnexpectedException("Component Name [" + bplComponentType.getName() + "] not found");
        }

        return rowIndex;
    }

}
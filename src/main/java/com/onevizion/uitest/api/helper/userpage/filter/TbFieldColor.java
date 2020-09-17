package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;

@Component
public class TbFieldColor {

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String trackorColumnId, String value, Map<String, List<String>> colors) {
        int rowsCnt = grid.getGridRowsCount(0L);

        int columnIndex = js.getColumnIndexById(0L, columnId);
        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0);

        int trackorColumnIndex = js.getColumnIndexById(0L, trackorColumnId);
        String trackorFieldName = js.getGridColumnLabelByColIndex(0L, trackorColumnIndex, 0);
        List<String> trackorCellVals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, trackorColumnIndex);

        userpageFilter.checkFilterEqualColor(fieldName, trackorFieldName, value, trackorCellVals, colors);
        userpageFilter.checkFilterNotEqualColor(fieldName, trackorFieldName, value, trackorCellVals, colors);
        userpageFilter.checkFilterIsFieldColor(fieldName, trackorFieldName, trackorCellVals, colors);
        userpageFilter.checkFilterIsFieldNotColor(fieldName, trackorFieldName, trackorCellVals, colors);
    }

}
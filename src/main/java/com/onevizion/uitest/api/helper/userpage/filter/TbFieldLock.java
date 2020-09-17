package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;

@Component
public class TbFieldLock {

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String trackorColumnId, List<String> trackors) {
        int rowsCnt = grid.getGridRowsCount(0L);

        int columnIndex = js.getColumnIndexById(0L, columnId);
        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0);

        int trackorColumnIndex = js.getColumnIndexById(0L, trackorColumnId);
        String trackorFieldName = js.getGridColumnLabelByColIndex(0L, trackorColumnIndex, 0);
        List<String> trackorCellVals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, trackorColumnIndex);

        userpageFilter.checkFilterIsFieldLocked(fieldName, trackorFieldName, trackorCellVals, trackors);
        userpageFilter.checkFilterIsFieldUnlocked(fieldName, trackorFieldName, trackorCellVals, trackors);
    }

}
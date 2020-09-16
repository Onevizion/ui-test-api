package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;

@Component
public class TbRelationLock {

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String trackorColumnId, List<String> trackors) {
        int columnIndex = js.getGridColIndexById(0L, columnId);

        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0L);

        Long rowsCnt = grid.getGridRowsCount(0L);
        int trackorColumnIndex = js.getGridColIndexById(0L, trackorColumnId);
        String trackorFieldName = js.getGridColumnLabelByColIndex(0L, trackorColumnIndex, 0L);
        List<String> trackorCellVals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, trackorColumnIndex);

        userpageFilter.checkFilterIsRelationLocked(fieldName, trackorFieldName, trackorCellVals, trackors);
        userpageFilter.checkFilterIsRelationUnlocked(fieldName, trackorFieldName, trackorCellVals, trackors);
    }

}
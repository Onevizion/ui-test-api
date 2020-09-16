package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.FilterOperatorType;

@Component
public class TbPsTrackorSelectorFieldByText {

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String value, boolean supportOuterOperations, List<String> ... cellValsKeys) {
        int columnIndex = js.getGridColIndexById(0L, columnId);

        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0L);

        Long rowsCnt = grid.getGridRowsCount(0L);
        List<String> cellVals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        userpageFilter.checkFilterTrackorSelectorByText(fieldName, FilterOperatorType.EQUAL, value, columnIndex, cellVals);
        userpageFilter.checkFilterTrackorSelectorByText(fieldName, FilterOperatorType.NOT_EQUAL, value, columnIndex, cellVals);

        if (supportOuterOperations) {
            userpageFilter.checkFilterTrackorSelectorByText(fieldName, FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER, value, columnIndex, cellVals, cellValsKeys);
            userpageFilter.checkFilterTrackorSelectorByText(fieldName, FilterOperatorType.NOT_EQUAL_AND_EMPTY_FOR_OTHER, value, columnIndex, cellVals, cellValsKeys);
        }
    }

}
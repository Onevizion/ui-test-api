package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.FilterOperatorType;

@Component
public class TbXitorClass {

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String value, boolean supportOuterOperations, List<String> ... cellValsKeys) {
        int columnIndex = js.getColumnIndexById(0L, columnId);

        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0L);

        Long rowsCnt = grid.getGridRowsCount(0L);
        List<String> cellVals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        List<FilterOperatorType> operators = FilterOperatorType.getXitorClassOperators(supportOuterOperations);
        userpageFilter.checkFilterOperators(fieldName, null, operators);

        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.EQUAL, ConfigFieldType.DROP_DOWN, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.NOT_EQUAL, ConfigFieldType.DROP_DOWN, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.NULL, ConfigFieldType.DROP_DOWN, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.NOT_NULL, ConfigFieldType.DROP_DOWN, columnIndex, cellVals);

        if (supportOuterOperations) {
            userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER, ConfigFieldType.DROP_DOWN, columnIndex, cellVals, cellValsKeys);
            userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.NOT_EQUAL_AND_EMPTY_FOR_OTHER, ConfigFieldType.DROP_DOWN, columnIndex, cellVals, cellValsKeys);
        }
    }

}
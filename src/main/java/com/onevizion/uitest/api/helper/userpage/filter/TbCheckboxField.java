package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.FilterOperatorType;

@Component
public class TbCheckboxField {

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, boolean supportOuterOperations, List<String> ... cellValsKeys) {
        Long columnIndex = js.getGridColIndexById(0L, columnId);

        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0L);

        Long rowsCnt = grid.getGridRowsCount(0L);
        List<String> cellVals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        List<FilterOperatorType> operators = FilterOperatorType.getCheckboxOperators(supportOuterOperations);
        userpageFilter.checkFilterOperators(fieldName, null, operators);

        userpageFilter.checkFilter(fieldName, null, "Yes", null, FilterOperatorType.EQUAL, ConfigFieldType.CHECKBOX, columnIndex, null, cellVals, null);
        userpageFilter.checkFilter(fieldName, null, "No", null, FilterOperatorType.EQUAL, ConfigFieldType.CHECKBOX, columnIndex, null, cellVals, null);

        if (supportOuterOperations) {
            userpageFilter.checkFilter(fieldName, null, "Yes", null, FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER, ConfigFieldType.CHECKBOX, columnIndex, null, cellVals, null, cellValsKeys);
            userpageFilter.checkFilter(fieldName, null, "No", null, FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER, ConfigFieldType.CHECKBOX, columnIndex, null, cellVals, null, cellValsKeys);
        }
    }

}
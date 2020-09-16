package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.FilterOperatorType;

@Component
public class TbXitorKey {

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String columnId2, String value, boolean supportOuterOperations, boolean supportFieldOperations, List<String> newTrackors, List<String> ... cellValsKeys) {
        Long rowsCnt = grid.getGridRowsCount(0L);

        int columnIndex = js.getGridColIndexById(0L, columnId);
        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0L);
        List<String> cellVals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        List<FilterOperatorType> operators = FilterOperatorType.getXitorKeyOperators(supportOuterOperations, supportFieldOperations);
        userpageFilter.checkFilterOperators(fieldName, null, operators);

        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.EQUAL, ConfigFieldType.TRACKOR_SELECTOR, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.NOT_EQUAL, ConfigFieldType.TRACKOR_SELECTOR, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.NULL, ConfigFieldType.TRACKOR_SELECTOR, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.NOT_NULL, ConfigFieldType.TRACKOR_SELECTOR, columnIndex, cellVals);

        if (supportFieldOperations) {
            int columnIndex2 = js.getGridColIndexById(0L, columnId2);
            String fieldName2 = js.getGridColumnLabelByColIndex(0L, columnIndex2, 0L);
            List<String> cellVals2 = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex2);

            userpageFilter.checkFilterWithFieldOperation(fieldName, fieldName2, value, null, FilterOperatorType.EQUAL_FIELD, ConfigFieldType.TRACKOR_SELECTOR, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterWithFieldOperation(fieldName, fieldName2, value, null, FilterOperatorType.NOT_EQUAL_FIELD, ConfigFieldType.TRACKOR_SELECTOR, columnIndex, columnIndex2, cellVals, cellVals2);
        }

        if (supportOuterOperations) {
            userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER, ConfigFieldType.TRACKOR_SELECTOR, columnIndex, cellVals, cellValsKeys);
            userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.NOT_EQUAL_AND_EMPTY_FOR_OTHER, ConfigFieldType.TRACKOR_SELECTOR, columnIndex, cellVals, cellValsKeys);
        }

        userpageFilter.checkFilterIsNew(fieldName, cellVals, newTrackors);
        userpageFilter.checkFilterIsNotNew(fieldName, cellVals, newTrackors);
    }

}
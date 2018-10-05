package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.FilterOperatorType;

@Component
public class TbNumberField {

    @Resource
    private Js js;

    @Resource
    private Grid grid;

    @Resource
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String columnId2, String value, boolean supportOuterOperations, boolean supportFieldOperations, List<String> ... cellValsKeys) {
        Long columnIndex = js.getGridColIndexById(0L, columnId);
        Long columnIndex2 = null;
        if (supportFieldOperations) {
            columnIndex2 = js.getGridColIndexById(0L, columnId2);
        }
        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0L);
        String fieldName2 = null;
        if (supportFieldOperations) {
            fieldName2 = js.getGridColumnLabelByColIndex(0L, columnIndex2, 0L);
        }

        Long rowsCnt = grid.getGridRowsCount(0L);
        List<String> cellVals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);
        List<String> cellVals2 = null;
        if (supportFieldOperations) {
            cellVals2 = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex2);
        }

        List<FilterOperatorType> operators = FilterOperatorType.getNumberOperators(supportOuterOperations, supportFieldOperations);
        userpageFilter.checkFilterOperators(fieldName, null, operators);

        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.EQUAL, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.MORE, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.LESS, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.MORE_AND_EQUAL, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.LESS_AND_EQUAL, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.NOT_EQUAL, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.NULL, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.NOT_NULL, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2);

        if (supportFieldOperations) {
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.EQUAL_FIELD, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.NOT_EQUAL_FIELD, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.MORE_FIELD, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.LESS_FIELD, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.MORE_AND_EQUAL_FIELD, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.LESS_AND_EQUAL_FIELD, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2);
        }

        if (supportOuterOperations) {
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2, cellValsKeys);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, FilterOperatorType.NOT_EQUAL_AND_EMPTY_FOR_OTHER, ConfigFieldType.NUMBER, columnIndex, columnIndex2, cellVals, cellVals2, cellValsKeys);
        }
    }

}
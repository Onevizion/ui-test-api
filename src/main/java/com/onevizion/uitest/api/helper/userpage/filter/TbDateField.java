package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.FilterOperatorType;

@Component
public class TbDateField {

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String columnId2, String value, String valuePlusMinus, String valueWithin, boolean supportOuterOperations, boolean supportFieldOperations, List<String> ... cellValsKeys) {
        Long rowsCnt = grid.getGridRowsCount(0L);

        int columnIndex = js.getColumnIndexById(0L, columnId);
        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0L);
        List<String> cellVals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        List<FilterOperatorType> operators = FilterOperatorType.getDateOperators(supportOuterOperations, supportFieldOperations);
        userpageFilter.checkFilterOperators(fieldName, null, operators);

        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.EQUAL, ConfigFieldType.DATE, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.MORE, ConfigFieldType.DATE, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.LESS, ConfigFieldType.DATE, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.MORE_AND_EQUAL, ConfigFieldType.DATE, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.LESS_AND_EQUAL, ConfigFieldType.DATE, columnIndex, cellVals);
        userpageFilter.checkFilterMoreAndEqualToday(fieldName, valuePlusMinus, null, ConfigFieldType.DATE);
        userpageFilter.checkFilterLessAndEqualToday(fieldName, valuePlusMinus, null, ConfigFieldType.DATE);
        userpageFilter.checkFilterWithin(fieldName, valueWithin, null, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisWeek(fieldName, valuePlusMinus, null, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisWeekToDate(fieldName, null, null, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisMonth(fieldName, valuePlusMinus, null, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisMonthToDate(fieldName, null, null, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisQuarter(fieldName, valuePlusMinus, null, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisQuarterToDate(fieldName, null, null, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisYear(fieldName, valuePlusMinus, null, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisYearToDate(fieldName, null, null, ConfigFieldType.DATE);
        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.NOT_EQUAL, ConfigFieldType.DATE, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.NULL, ConfigFieldType.DATE, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.NOT_NULL, ConfigFieldType.DATE, columnIndex, cellVals);

        if (supportFieldOperations) {
            int columnIndex2 = js.getColumnIndexById(0L, columnId2);
            String fieldName2 = js.getGridColumnLabelByColIndex(0L, columnIndex2, 0L);
            List<String> cellVals2 = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex2);

            userpageFilter.checkFilterWithFieldOperation(fieldName, fieldName2, value, null, FilterOperatorType.EQUAL_FIELD, ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterWithFieldOperation(fieldName, fieldName2, value, null, FilterOperatorType.NOT_EQUAL_FIELD, ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterWithFieldOperation(fieldName, fieldName2, value, null, FilterOperatorType.MORE_FIELD, ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterWithFieldOperation(fieldName, fieldName2, value, null, FilterOperatorType.LESS_FIELD, ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterWithFieldOperation(fieldName, fieldName2, value, null, FilterOperatorType.MORE_AND_EQUAL_FIELD, ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterWithFieldOperation(fieldName, fieldName2, value, null, FilterOperatorType.LESS_AND_EQUAL_FIELD, ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
        }

        if (supportOuterOperations) {
            userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER, ConfigFieldType.DATE, columnIndex, cellVals, cellValsKeys);
            userpageFilter.checkFilter(fieldName, value, null, FilterOperatorType.NOT_EQUAL_AND_EMPTY_FOR_OTHER, ConfigFieldType.DATE, columnIndex, cellVals, cellValsKeys);
        }
    }

}
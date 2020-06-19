package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.FilterOperatorType;

@Component
public class TbDateTimeField {

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String columnId2, String value, String valuePlusMinus, String valueWithin, boolean supportFieldOperations) {
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

        List<FilterOperatorType> operators = FilterOperatorType.getDateTimeOperators(supportFieldOperations);
        userpageFilter.checkFilterOperators(fieldName, null, operators);

        userpageFilter.checkFilter(fieldName, fieldName2, value, null, FilterOperatorType.EQUAL, ConfigFieldType.DATE_TIME, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilter(fieldName, fieldName2, value, null, FilterOperatorType.MORE, ConfigFieldType.DATE_TIME, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilter(fieldName, fieldName2, value, null, FilterOperatorType.LESS, ConfigFieldType.DATE_TIME, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilter(fieldName, fieldName2, value, null, FilterOperatorType.MORE_AND_EQUAL, ConfigFieldType.DATE_TIME, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilter(fieldName, fieldName2, value, null, FilterOperatorType.LESS_AND_EQUAL, ConfigFieldType.DATE_TIME, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterMoreAndEqualToday(fieldName, valuePlusMinus, null, ConfigFieldType.DATE_TIME);
        userpageFilter.checkFilterLessAndEqualToday(fieldName, valuePlusMinus, null, ConfigFieldType.DATE_TIME);
        userpageFilter.checkFilterWithin(fieldName, valueWithin, null, ConfigFieldType.DATE_TIME);
        userpageFilter.checkFilterThisWeek(fieldName, valuePlusMinus, null, ConfigFieldType.DATE_TIME);
        userpageFilter.checkFilterThisWeekToDate(fieldName, null, null, ConfigFieldType.DATE_TIME);
        userpageFilter.checkFilterThisMonth(fieldName, valuePlusMinus, null, ConfigFieldType.DATE_TIME);
        userpageFilter.checkFilterThisMonthToDate(fieldName, null, null, ConfigFieldType.DATE_TIME);
        userpageFilter.checkFilterThisQuarter(fieldName, valuePlusMinus, null, ConfigFieldType.DATE_TIME);
        userpageFilter.checkFilterThisQuarterToDate(fieldName, null, null, ConfigFieldType.DATE_TIME);
        userpageFilter.checkFilterThisYear(fieldName, valuePlusMinus, null, ConfigFieldType.DATE_TIME);
        userpageFilter.checkFilterThisYearToDate(fieldName, null, null, ConfigFieldType.DATE_TIME);
        userpageFilter.checkFilter(fieldName, fieldName2, value, null, FilterOperatorType.NOT_EQUAL, ConfigFieldType.DATE_TIME, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilter(fieldName, fieldName2, value, null, FilterOperatorType.NULL, ConfigFieldType.DATE_TIME, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilter(fieldName, fieldName2, value, null, FilterOperatorType.NOT_NULL, ConfigFieldType.DATE_TIME, columnIndex, columnIndex2, cellVals, cellVals2);

        if (supportFieldOperations) {
            userpageFilter.checkFilter(fieldName, fieldName2, value, null, FilterOperatorType.EQUAL_FIELD, ConfigFieldType.DATE_TIME, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilter(fieldName, fieldName2, value, null, FilterOperatorType.NOT_EQUAL_FIELD, ConfigFieldType.DATE_TIME, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilter(fieldName, fieldName2, value, null, FilterOperatorType.MORE_FIELD, ConfigFieldType.DATE_TIME, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilter(fieldName, fieldName2, value, null, FilterOperatorType.LESS_FIELD, ConfigFieldType.DATE_TIME, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilter(fieldName, fieldName2, value, null, FilterOperatorType.MORE_AND_EQUAL_FIELD, ConfigFieldType.DATE_TIME, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilter(fieldName, fieldName2, value, null, FilterOperatorType.LESS_AND_EQUAL_FIELD, ConfigFieldType.DATE_TIME, columnIndex, columnIndex2, cellVals, cellVals2);
        }
    }

}
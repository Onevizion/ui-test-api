package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.FilterOperatorType;

@Component
public class TbTaskDateOnTbPage {

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String value, String valuePlusMinus, String valueWithin, String startFinish) {
        int rowsCnt = grid.getGridRowsCount(0L);

        int columnIndex = js.getColumnIndexById(0L, columnId);
        if ("F".equals(startFinish)) {
            columnIndex = columnIndex + 1;
        }
        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0);
        List<String> cellVals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        List<FilterOperatorType> operators = FilterOperatorType.getTaskDateOperators();
        userpageFilter.checkFilterOperators(fieldName, Arrays.asList("S", "F"), operators);

        userpageFilter.checkFilter(fieldName, value, startFinish, FilterOperatorType.EQUAL, ConfigFieldType.DATE, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, startFinish, FilterOperatorType.MORE, ConfigFieldType.DATE, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, startFinish, FilterOperatorType.LESS, ConfigFieldType.DATE, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, startFinish, FilterOperatorType.MORE_AND_EQUAL, ConfigFieldType.DATE, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, startFinish, FilterOperatorType.LESS_AND_EQUAL, ConfigFieldType.DATE, columnIndex, cellVals);
        userpageFilter.checkFilterMoreAndEqualToday(fieldName, valuePlusMinus, startFinish, ConfigFieldType.DATE);
        userpageFilter.checkFilterLessAndEqualToday(fieldName, valuePlusMinus, startFinish, ConfigFieldType.DATE);
        userpageFilter.checkFilterWithin(fieldName, valueWithin, startFinish, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisWeek(fieldName, valuePlusMinus, startFinish, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisWeekToDate(fieldName, null, startFinish, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisMonth(fieldName, valuePlusMinus, startFinish, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisMonthToDate(fieldName, null, startFinish, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisQuarter(fieldName, valuePlusMinus, startFinish, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisQuarterToDate(fieldName, null, startFinish, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisYear(fieldName, valuePlusMinus, startFinish, ConfigFieldType.DATE);
        userpageFilter.checkFilterThisYearToDate(fieldName, null, startFinish, ConfigFieldType.DATE);
        //userpageFilter.checkFilter(fieldName, value, startFinish, FilterOperatorType.NOT_EQUAL, ConfigFieldType.DATE, columnIndex, cellVals); //TODO BUG
        userpageFilter.checkFilter(fieldName, value, startFinish, FilterOperatorType.NULL, ConfigFieldType.DATE, columnIndex, cellVals);
        userpageFilter.checkFilter(fieldName, value, startFinish, FilterOperatorType.NOT_NULL, ConfigFieldType.DATE, columnIndex, cellVals);
    }

}
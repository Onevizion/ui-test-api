package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.FilterOperatorType;

@Component
public class TbTaskDate {

    @Resource
    private Js js;

    @Resource
    private Grid grid;

    @Resource
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String value, String startFinish) {
        Long columnIndex = js.getGridColIndexById(0L, columnId);

        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0L);
        if ("F".equals(startFinish)) {
            columnIndex = columnIndex + 1L;
        }

        Long rowsCnt = grid.getGridRowsCount(0L);
        List<String> cellVals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        List<FilterOperatorType> operators = FilterOperatorType.getTaskDateOperators();
        userpageFilter.checkFilterOperators(fieldName, Arrays.asList("S", "F"), operators);

        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, FilterOperatorType.EQUAL, ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, FilterOperatorType.MORE, ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, FilterOperatorType.LESS, ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, FilterOperatorType.MORE_AND_EQUAL, ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, FilterOperatorType.LESS_AND_EQUAL, ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        //TODO
        //>=Today
        //<=Today
        //Within
        //This Wk
        //This Wk to Dt
        //This Mo
        //This Mo to Dt
        //This FQ
        //This FQ to Dt
        //This FY
        //This FY to Dt
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, FilterOperatorType.NOT_EQUAL, ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, FilterOperatorType.NULL, ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, FilterOperatorType.NOT_NULL, ConfigFieldType.DATE, columnIndex, null, cellVals, null);
    }

}
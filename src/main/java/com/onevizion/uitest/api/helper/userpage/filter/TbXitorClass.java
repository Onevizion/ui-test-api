package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.ConfigFieldType;

@Component
public class TbXitorClass {

    @Resource
    private Js js;

    @Resource
    private Grid grid;

    @Resource
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String value, boolean supportOuterOperations, List<String> ... cellValsKeys) {
        Long columnIndex = js.getGridColIndexById(0L, columnId);

        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0L);

        Long rowsCnt = grid.getGridRowsCount(0L);
        List<String> cellVals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        if (supportOuterOperations) {
            userpageFilter.checkFilterOperators(fieldName, null, Arrays.asList("=", "(+)=", "<>", "(+)<>", "Is Null", "Is Not Null"));
        } else {
            userpageFilter.checkFilterOperators(fieldName, null, Arrays.asList("=", "<>", "Is Null", "Is Not Null"));
        }

        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "=", ConfigFieldType.DROP_DOWN, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "<>", ConfigFieldType.DROP_DOWN, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "Is Null", ConfigFieldType.DROP_DOWN, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "Is Not Null", ConfigFieldType.DROP_DOWN, columnIndex, null, cellVals, null);

        if (supportOuterOperations) {
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "(+)=", ConfigFieldType.DROP_DOWN, columnIndex, null, cellVals, null, cellValsKeys);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "(+)<>", ConfigFieldType.DROP_DOWN, columnIndex, null, cellVals, null, cellValsKeys);
        }
    }

}
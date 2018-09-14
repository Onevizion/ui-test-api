package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.JsHelper;
import com.onevizion.uitest.api.vo.ConfigFieldType;

@Component
public class TbCheckboxField {

    @Resource
    private JsHelper jsHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, boolean supportOuterOperations, List<String> ... cellValsKeys) {
        Long columnIndex = jsHelper.getGridColIndexById(0L, columnId);

        String fieldName = jsHelper.getGridColumnLabelByColIndex(0L, columnIndex, 0L);

        Long rowsCnt = gridHelper.getGridRowsCount(0L);
        List<String> cellVals = (List<String>) jsHelper.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        if (supportOuterOperations) {
            userpageFilter.checkFilterOperators(fieldName, null, Arrays.asList("=", "(+)="));
        } else {
            userpageFilter.checkFilterOperators(fieldName, null, Arrays.asList("="));
        }

        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, "Yes", null, "=", ConfigFieldType.CHECKBOX, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, "No", null, "=", ConfigFieldType.CHECKBOX, columnIndex, null, cellVals, null);

        if (supportOuterOperations) {
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, "Yes", null, "(+)=", ConfigFieldType.CHECKBOX, columnIndex, null, cellVals, null, cellValsKeys);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, "No", null, "(+)=", ConfigFieldType.CHECKBOX, columnIndex, null, cellVals, null, cellValsKeys);
        }
    }

}
package com.onevizion.guitest.helper.userpage.filter;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.guitest.helper.GridHelper;
import com.onevizion.guitest.helper.JsHelper;
import com.onevizion.guitest.vo.ConfigFieldType;

@Component
public class TbCheckboxField {

    @Resource
    private JsHelper jsHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private UserFilterHelper userFilterHelper;

    @SuppressWarnings("unchecked")
    public void test(String columnId, boolean supportOuterOperations, List<String> ... cellValsKeys) {
        Long columnIndex = jsHelper.getGridColIndexById(0L, columnId);

        String fieldName = jsHelper.getGridColumnLabelByColIndex(0L, columnIndex, 0L);

        Long rowsCnt = gridHelper.getGridRowsCount(0L);
        List<String> cellVals = (List<String>) jsHelper.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        if (supportOuterOperations) {
            userFilterHelper.checkFilterOperators(fieldName, null, Arrays.asList("=", "(+)="));
        } else {
            userFilterHelper.checkFilterOperators(fieldName, null, Arrays.asList("="));
        }

        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, "Yes", null, "=", ConfigFieldType.CHECKBOX, columnIndex, null, cellVals, null);
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, "No", null, "=", ConfigFieldType.CHECKBOX, columnIndex, null, cellVals, null);

        if (supportOuterOperations) {
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, "Yes", null, "(+)=", ConfigFieldType.CHECKBOX, columnIndex, null, cellVals, null, cellValsKeys);
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, "No", null, "(+)=", ConfigFieldType.CHECKBOX, columnIndex, null, cellVals, null, cellValsKeys);
        }
    }

}
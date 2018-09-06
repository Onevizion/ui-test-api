package com.onevizion.guitest.helper.userpage.filter;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.guitest.helper.GridHelper;
import com.onevizion.guitest.helper.JsHelper;
import com.onevizion.guitest.vo.ConfigFieldType;

@Component
public class TbCalculatedField {

    @Resource
    private JsHelper jsHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private UserFilterHelper userFilterHelper;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String value, boolean supportOuterOperations, List<String> ... cellValsKeys) {
        Long columnIndex = jsHelper.getGridColIndexById(0L, columnId);

        String fieldName = jsHelper.getGridColumnLabelByColIndex(0L, columnIndex, 0L);

        Long rowsCnt = gridHelper.getGridRowsCount(0L);
        List<String> cellVals = (List<String>) jsHelper.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        if (supportOuterOperations) {
            userFilterHelper.checkFilterOperators(fieldName, null, Arrays.asList("=", "(+)=", "<>", "(+)<>", "Is Null", "Is Not Null"));
        } else {
            userFilterHelper.checkFilterOperators(fieldName, null, Arrays.asList("=", "<>", "Is Null", "Is Not Null"));
        }

        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "=", ConfigFieldType.CALCULATED, columnIndex, null, cellVals, null);
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "<>", ConfigFieldType.CALCULATED, columnIndex, null, cellVals, null);
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "Is Null", ConfigFieldType.CALCULATED, columnIndex, null, cellVals, null);
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "Is Not Null", ConfigFieldType.CALCULATED, columnIndex, null, cellVals, null);

        if (supportOuterOperations) {
            //TODO null pointer exception
            //on TB for parent and child fields
            //userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "(+)=", FieldDataType.CALCULATED, columnIndex, null, cellVals, null, cellValsKeys);
            //userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "(+)<>", FieldDataType.CALCULATED, columnIndex, null, cellVals, null, cellValsKeys);
        }
    }

}
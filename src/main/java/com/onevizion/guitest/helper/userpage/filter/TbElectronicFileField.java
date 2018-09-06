package com.onevizion.guitest.helper.userpage.filter;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.guitest.helper.GridHelper;
import com.onevizion.guitest.helper.JsHelper;
import com.onevizion.guitest.vo.ConfigFieldType;

@Component
public class TbElectronicFileField {

    @Resource
    private JsHelper jsHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private UserFilterHelper userFilterHelper;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String value) {
        Long columnIndex = jsHelper.getGridColIndexById(0L, columnId);

        String fieldName = jsHelper.getGridColumnLabelByColIndex(0L, columnIndex, 0L);

        Long rowsCnt = gridHelper.getGridRowsCount(0L);
        List<String> cellVals = (List<String>) jsHelper.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        userFilterHelper.checkFilterOperators(fieldName, null, Arrays.asList("=", "<>", "Is Null", "Is Not Null"));
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "=", ConfigFieldType.ELECTRONIC_FILE, columnIndex, null, cellVals, null);
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "<>", ConfigFieldType.ELECTRONIC_FILE, columnIndex, null, cellVals, null);
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "Is Null", ConfigFieldType.ELECTRONIC_FILE, columnIndex, null, cellVals, null);
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "Is Not Null", ConfigFieldType.ELECTRONIC_FILE, columnIndex, null, cellVals, null);
    }

}
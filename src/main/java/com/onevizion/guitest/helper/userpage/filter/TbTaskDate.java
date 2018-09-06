package com.onevizion.guitest.helper.userpage.filter;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.guitest.helper.GridHelper;
import com.onevizion.guitest.helper.JsHelper;
import com.onevizion.guitest.vo.ConfigFieldType;

@Component
public class TbTaskDate {

    @Resource
    private JsHelper jsHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private UserFilterHelper userFilterHelper;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String value, String startFinish) {
        Long columnIndex = jsHelper.getGridColIndexById(0L, columnId);

        String fieldName = jsHelper.getGridColumnLabelByColIndex(0L, columnIndex, 0L);
        if ("F".equals(startFinish)) {
            columnIndex = columnIndex + 1L;
        }

        Long rowsCnt = gridHelper.getGridRowsCount(0L);
        List<String> cellVals = (List<String>) jsHelper.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        userFilterHelper.checkFilterOperators(fieldName, Arrays.asList("S", "F"), Arrays.asList("=", ">", "<", ">=", "<=", ">=Today", "<=Today", "Within", "This Wk",
                "This Wk to Dt", "This Mo", "This Mo to Dt", "This FQ", "This FQ to Dt", "This FY", "This FY to Dt", "<>", "Is Null", "Is Not Null"));
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, "=", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, ">", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, "<", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, ">=", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, "<=", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
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
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, "<>", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, "Is Null", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, "Is Not Null", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
    }

}
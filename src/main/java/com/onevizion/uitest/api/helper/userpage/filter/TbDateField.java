package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.JsHelper;
import com.onevizion.uitest.api.vo.ConfigFieldType;

@Component
public class TbDateField {

    @Resource
    private JsHelper jsHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String columnId2, String value, boolean supportOuterOperations, boolean supportFieldOperations, List<String> ... cellValsKeys) {
        Long columnIndex = jsHelper.getGridColIndexById(0L, columnId);
        Long columnIndex2 = null;
        if (supportFieldOperations) {
            columnIndex2 = jsHelper.getGridColIndexById(0L, columnId2);
        }

        String fieldName = jsHelper.getGridColumnLabelByColIndex(0L, columnIndex, 0L);
        String fieldName2 = null;
        if (supportFieldOperations) {
            fieldName2 = jsHelper.getGridColumnLabelByColIndex(0L, columnIndex2, 0L);
        }

        Long rowsCnt = gridHelper.getGridRowsCount(0L);
        List<String> cellVals = (List<String>) jsHelper.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);
        List<String> cellVals2 = null;
        if (supportFieldOperations) {
            cellVals2 = (List<String>) jsHelper.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex2);
        }

        if (supportOuterOperations && supportFieldOperations) {
            userpageFilter.checkFilterOperators(fieldName, null, Arrays.asList("=", "(+)=", ">", "<", ">=", "<=", ">=Today", "<=Today", "Within", "This Wk",
                    "This Wk to Dt", "This Mo", "This Mo to Dt", "This FQ", "This FQ to Dt", "This FY", "This FY to Dt", "<>", "(+)<>", "Is Null", "Is Not Null", "=Field", "<>Field", ">Field", "<Field", ">=Field", "<=Field"));
        } else if (supportOuterOperations && !supportFieldOperations) {
            userpageFilter.checkFilterOperators(fieldName, null, Arrays.asList("=", "(+)=", ">", "<", ">=", "<=", ">=Today", "<=Today", "Within", "This Wk",
                    "This Wk to Dt", "This Mo", "This Mo to Dt", "This FQ", "This FQ to Dt", "This FY", "This FY to Dt", "<>", "(+)<>", "Is Null", "Is Not Null"));
        } else if (!supportOuterOperations && supportFieldOperations) {
            userpageFilter.checkFilterOperators(fieldName, null, Arrays.asList("=", ">", "<", ">=", "<=", ">=Today", "<=Today", "Within", "This Wk",
                    "This Wk to Dt", "This Mo", "This Mo to Dt", "This FQ", "This FQ to Dt", "This FY", "This FY to Dt", "<>", "Is Null", "Is Not Null", "=Field", "<>Field", ">Field", "<Field", ">=Field", "<=Field"));
        } else {
            userpageFilter.checkFilterOperators(fieldName, null, Arrays.asList("=", ">", "<", ">=", "<=", ">=Today", "<=Today", "Within", "This Wk",
                    "This Wk to Dt", "This Mo", "This Mo to Dt", "This FQ", "This FQ to Dt", "This FY", "This FY to Dt", "<>", "Is Null", "Is Not Null"));
        }

        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "=", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, ">", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "<", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, ">=", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "<=", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);

        //TODO >=Today
        /*for (Integer i = 0; i < 3; i++) {
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, ">=Today", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, Arrays.asList(" - ", i.toString()));
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, ">=Today", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, Arrays.asList(" + ", i.toString()));
        }*/

        //TODO <=Today
        /*for (Integer i = 0; i < 3; i++) {
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "<=Today", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, Arrays.asList(" - ", i.toString()));
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "<=Today", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, Arrays.asList(" + ", i.toString()));
        }*/

        //TODO Within
        /*for (Integer i = 1; i < 3; i++) {
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "Within", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, Arrays.asList(i.toString(), i.toString()));
        }*/

        //TODO This Wk
        /*for (Integer i = 0; i < 3; i++) {
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "This Wk", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, Arrays.asList(" - ", i.toString()));
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "This Wk", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, Arrays.asList(" + ", i.toString()));
        }*/

        //TODO This Mo
        /*for (Integer i = 0; i < 3; i++) {
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "This Mo", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, Arrays.asList(" - ", i.toString()));
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "This Mo", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, Arrays.asList(" + ", i.toString()));
        }*/

        //TODO This FQ
        /*for (Integer i = 0; i < 3; i++) {
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "This FQ", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, Arrays.asList(" - ", i.toString()));
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "This FQ", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, Arrays.asList(" + ", i.toString()));
        }*/

        //TODO This FY
        /*for (Integer i = 0; i < 2; i++) {
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "This FY", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, Arrays.asList(" - ", i.toString()));
            userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "This FY", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, Arrays.asList(" + ", i.toString()));
        }*/

        //TODO This Wk to Dt
        //userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "This Wk to Dt", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
        //TODO This Mo to Dt
        //userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "This Mo to Dt", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
        //TODO This FQ to Dt
        //userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "This FQ to Dt", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
        //TODO This FY to Dt
        //userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "This FY to Dt", FieldDataType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);

        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "<>", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "Is Null", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "Is Not Null", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);

        if (supportFieldOperations) {
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "=Field", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "<>Field", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, ">Field", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "<Field", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, ">=Field", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "<=Field", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2);
        }

        if (supportOuterOperations) {
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "(+)=", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, cellValsKeys);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "(+)<>", ConfigFieldType.DATE, columnIndex, columnIndex2, cellVals, cellVals2, cellValsKeys);
        }
    }

    @SuppressWarnings("unchecked")
    public void createTrackorsForDateTest(String columnId, String columnId2){
        Long columnIndex = jsHelper.getGridColIndexById(0L, columnId);
        Long columnIndex2 = jsHelper.getGridColIndexById(0L, columnId2);

        String fieldName = jsHelper.getGridColumnLabelByColIndex(0L, columnIndex, 0L);
        String fieldName2 = jsHelper.getGridColumnLabelByColIndex(0L, columnIndex2, 0L);

        for (Integer i = 0; i < 3; i++) {
            userpageFilter.createTrackorForDateTest(fieldName, fieldName2, ">=Today", Arrays.asList(" - ", i.toString()));
            userpageFilter.createTrackorForDateTest(fieldName, fieldName2, ">=Today", Arrays.asList(" + ", i.toString()));
        }
    }

}
package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.JsHelper;
import com.onevizion.uitest.api.vo.ConfigFieldType;

@Component
public class TbPsTrackorSelectorField {

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
            userpageFilter.checkFilterOperators(fieldName, null, Arrays.asList("=", "(+)=", "<>", "(+)<>", "Is Null", "Is Not Null", "=Field", "<>Field", "Is New", "Is Not New"));
        } else if (supportOuterOperations && !supportFieldOperations) {
            userpageFilter.checkFilterOperators(fieldName, null, Arrays.asList("=", "(+)=", "<>", "(+)<>", "Is Null", "Is Not Null", "Is New", "Is Not New"));
        } else if (!supportOuterOperations && supportFieldOperations) {
            userpageFilter.checkFilterOperators(fieldName, null, Arrays.asList("=", "<>", "Is Null", "Is Not Null", "=Field", "<>Field", "Is New", "Is Not New"));
        } else {
            userpageFilter.checkFilterOperators(fieldName, null, Arrays.asList("=", "<>", "Is Null", "Is Not Null", "Is New", "Is Not New"));
        }

        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "=", ConfigFieldType.TRACKOR_SELECTOR, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "<>", ConfigFieldType.TRACKOR_SELECTOR, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "Is Null", ConfigFieldType.TRACKOR_SELECTOR, columnIndex, columnIndex2, cellVals, cellVals2);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "Is Not Null", ConfigFieldType.TRACKOR_SELECTOR, columnIndex, columnIndex2, cellVals, cellVals2);

        if (supportFieldOperations) {
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "=Field", ConfigFieldType.TRACKOR_SELECTOR, columnIndex, columnIndex2, cellVals, cellVals2);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "<>Field", ConfigFieldType.TRACKOR_SELECTOR, columnIndex, columnIndex2, cellVals, cellVals2);
        }

        if (supportOuterOperations) {
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "(+)=", ConfigFieldType.TRACKOR_SELECTOR, columnIndex, columnIndex2, cellVals, cellVals2, cellValsKeys);
            userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, fieldName2, value, null, "(+)<>", ConfigFieldType.TRACKOR_SELECTOR, columnIndex, columnIndex2, cellVals, cellVals2, cellValsKeys);
        }

        //TODO Is New
        //TODO Is Not New
    }

}
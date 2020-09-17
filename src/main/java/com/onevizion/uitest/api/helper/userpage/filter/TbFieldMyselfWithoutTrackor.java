package com.onevizion.uitest.api.helper.userpage.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;

@Component
public class TbFieldMyselfWithoutTrackor {

    @Autowired
    private Js js;

    @Autowired
    private Grid grid;

    @Autowired
    private UserpageFilter userpageFilter;

    public void test(String columnId) {
        int rowsCnt = grid.getGridRowsCount(0L);

        int columnIndex = js.getColumnIndexById(0L, columnId);
        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0);

        userpageFilter.checkFilterEqualMyselfWithoutTrackor(fieldName, rowsCnt);
        userpageFilter.checkFilterNotEqualMyselfWithoutTrackor(fieldName, rowsCnt);
    }

}
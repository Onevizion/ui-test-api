package com.onevizion.uitest.api.helper.userpage.filter;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;

@Component
public class TbFieldMyselfWithoutTrackor {

    @Resource
    private Js js;

    @Resource
    private Grid grid;

    @Resource
    private UserpageFilter userpageFilter;

    public void test(String columnId) {
        Long columnIndex = js.getGridColIndexById(0L, columnId);

        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0L);

        Long rowsCnt = grid.getGridRowsCount(0L);

        userpageFilter.checkFilterEqualMyselfWithoutTrackor(fieldName, rowsCnt);
        userpageFilter.checkFilterNotEqualMyselfWithoutTrackor(fieldName, rowsCnt);
    }

}
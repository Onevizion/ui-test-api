package com.onevizion.uitest.api.helper.grid.group;

import java.util.List;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class GridGroupJs extends Js {

    String getGroupId(Long gridIdx, String groupLabel) {
        return execJs(
                "var obj = gridArr[" + gridIdx + "].grid.objTxtValue;" + 
                "return Object.keys(obj).find(key => obj[key] === '" + groupLabel + "');");
    }

    @SuppressWarnings("unchecked")
    List<String> getColumnValsInGroup(Long gridIdx, int columnIndex, String groupId) {
        return (List<String>) execJs2(
                "var columnVals = [];" + 
                "gridArr[" + gridIdx + "].grid.forEachRowInGroup('" + groupId + "', function(rowId) {" + 
                "    columnVals.push(gridArr[" + gridIdx + "].grid.cellById(rowId, " + columnIndex + ").getTxtValue().trim());" + 
                "});" + 
                "return columnVals;");
    }

}
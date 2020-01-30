package com.onevizion.uitest.api.helper;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class TbJs extends Js {

    Long getGroupColumnIndex(Long gridIdx) {
        return NumberUtils.createLong(execJs("return gridArr[" + gridIdx + "].grid._gIndex;"));
    }

    List<String> getGridRowsIdInGroup(Long gridIdx, String group){
        String script = "var groups = [];\n" + 
                "var i = 0;\n" + 
                "if (gridArr[" + gridIdx + "].grid.objTxtValue) {\n" + 
                "    for (const [ key, value ] of Object.entries(gridArr[" + gridIdx + "].grid.objTxtValue)) {\n" + 
                "        if (value == \"" + group + "\") {\n" + 
                "            groups[i] = key;\n" + 
                "            i++;\n" + 
                "        }\n" + 
                "    }\n" + 
                "}  else {\n" + 
                "    groups[i] = \"" + group + "\";\n" + 
                "}\n" + 
                "var rowIds = \"\";\n" + 
                "for (var i=0; i<groups.length; i++) {\n" + 
                "    if (gridArr[" + gridIdx + "].grid._groups[groups[i]]) {\n" + 
                "        gridArr[" + gridIdx + "].grid.forEachRowInGroup(groups[i], function(rowId) {\r\n" + 
                "            concatRowIds(rowId);\r\n" + 
                "        });\n" + 
                "    }\n" + 
                "}\n" + 
                "return rowIds;" + 
                "rowIds = rowIds.substring(0, rowIds.length-1);\n" +
                "function concatRowIds(rowId){\n" + 
                "    rowIds = rowIds + rowId + \",\";\n" + 
                "}";

        String resultStr = (String) execJs2(script);
        List<String> result = Arrays.asList(resultStr.split(","));

        return result;
    }

}
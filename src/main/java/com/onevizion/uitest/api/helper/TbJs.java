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
                "        var h = gridArr[" + gridIdx + "].grid._groups[groups[i]].row.nextSibling;\n" + 
                "        if (h) {\n" + 
                "            while (h && !h._cntr) {\n" + 
                "                rowIds = rowIds + h.idd + \",\";\n" + 
                "                h = h.nextSibling\n" + 
                "            }\n" + 
                "        } else {\n" + 
                "            var e = gridArr[" + gridIdx + "].grid._groups[groups[i]]._childs;\n" + 
                "            if (e) {\n" + 
                "                for (var c = 0; c < e.length; c++) {\n" + 
                "                    rowIds = rowIds + h.idd + \",\";\n" + 
                "                }\n" + 
                "            }\n" + 
                "        }\n" + 
                "    }\n" + 
                "}\n" + 
                "rowIds = rowIds.substring(0, rowIds.length-1);\n" + 
                "return rowIds;";

        String resultStr = (String) execJs2(script);
        List<String> result = Arrays.asList(resultStr.split(","));

        return result;
    }

}
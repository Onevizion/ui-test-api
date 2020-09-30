package com.onevizion.uitest.api.helper.organizer;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class OrganizerJs extends Js {

    String getItemIdByText(String itemName) {
        return execJs("var itemsIds = (treeArr[0].tree.getSubItems(-1) + ',' + treeArr[0].tree.getSubItems(-2)).split(',');\n" + 
                "var isFind = false;\n" + 
                "var result = '';\n" + 
                "itemsIds.forEach(function(itemId) {\n" + 
                "    if(treeArr[0].tree.getItemText(itemId)=='" + itemName + "') {\n" + 
                "        if(result!=''){\n" + 
                "            throw 'Item with name[" + itemName + "] found many times';\n" + 
                "        } else {\n" + 
                "            result = itemId;\n" + 
                "            isFind = true;\n" + 
                "        }\n" + 
                "    }\n" + 
                "});\n" + 
                "if(!isFind) {\n" + 
                "    throw 'Item with name[" + itemName + "] not found';\n" + 
                "}\n" + 
                "return result;");
    }

}
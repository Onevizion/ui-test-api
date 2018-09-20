package com.onevizion.uitest.api.helper.tree;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class TreeJs extends Js {

    String isTreeLoaded(String treeId) {
        return execJs("return treeArr['" + treeId + "'].PageLoaded;");
    }

    Boolean isTreeArrExist() {
        //TODO firefox 59 bug
        //https://github.com/mozilla/geckodriver/issues/1067
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1420923
        return Boolean.valueOf(execJs("return typeof window.treeArr !== 'undefined';"));
    }

    String getTreeAllSubItems(Long treeId, String itemId) {
        return execJs("return treeArr[" + treeId + "].tree.getAllSubItems('" + itemId + "');");
    }

    String getTreeSubItems(Long treeId, String itemId) {
        return execJs("return treeArr[" + treeId + "].tree.getSubItems('" + itemId + "');");
    }

    void selectItemInTree(Long treeId, String itemId) {
        execJs("treeArr[" + treeId + "].tree.selectItem('" + itemId + "', true, false);");
    }

    String getItemTextInTreeById(Long treeId, String itemId) {
        return execJs("return treeArr[" + treeId + "].tree.getItemText('" + itemId + "');");
    }

    String getSelectedItemInTree(Long treeId) {
        return execJs("return treeArr[" + treeId + "].tree.getSelectedItemId();");
    }

    void selectItemInTree(Long treeId, String rootItemId, String treePath) {
        execJs(""
                + "var treeId = '" + treeId + "';"
                + "var rootItemId = '" + rootItemId + "';"
                + "var treePathStr = '" + treePath + "';"
                + "var treePathArr = treePathStr.split('-->');"
                + "for (var i = 0; i < treePathArr.length; i++) {"
                + "    treePathArr[i] = treePathArr[i].trim();"
                + "}"
                + ""
                + "var itemId = qwerty(treeId, rootItemId, treePathArr);"
                + "treeArr[treeId].tree.selectItem(itemId, true, false);"
                + ""
                + "function qwerty(treeId, itemId, treePathArr) {"
                + "    var subItemsStr = treeArr[treeId].tree.getSubItems(itemId);"
                + "    var subItemsArr = subItemsStr.split(',');"
                + "    for (var i = 0; i < subItemsArr.length; i++) {"
                + "        subItemsArr[i] = subItemsArr[i].trim();"
                + "    }"
                + ""
                + "    var itemExist = false;"
                + "    var item;"
                + "    for (var i = 0; i < subItemsArr.length; i++) {"
                + "        var itemText = treeArr[treeId].tree.getItemText(subItemsArr[i]);"
                + "        itemText = itemText.replace(/<\\/?[^>]+(>|$)/g, \"\");" //https://stackoverflow.com/questions/5002111/javascript-how-to-strip-html-tags-from-string
                + ""
                + "        if (itemText == treePathArr[1]) {"
                + "            if (itemExist) {"
                + "                throw \"error1\";"
                + "            }"
                + "            itemExist = true;"
                + "            item = subItemsArr[i];"
                + "        }"
                + "    }"
                + ""
                + "    if (itemExist) {"
                + "        treePathArr.shift();"
                + "        if (treePathArr.length == 1) {"
                + "            return item;"
                + "        } else {"
                + "            return qwerty(treeId, item, treePathArr);"
                + "        }"
                + "    } else {"
                + "        throw \"error2\";"
                + "    }"
                + "}");
    }

    void selectParentItemInTree(Long treeId, String rootItemId, String treePath) {
        execJs(""
                + "var treeId = '" + treeId + "';"
                + "var rootItemId = '" + rootItemId + "';"
                + "var treePathStr = '" + treePath + "';"
                + "var treePathArr = treePathStr.split('-->');"
                + "for (var i = 0; i < treePathArr.length; i++) {"
                + "    treePathArr[i] = treePathArr[i].trim();"
                + "}"
                + "treePathArr.pop();"
                + ""
                + "if (treePathArr.length == 1) {"
                + "    var itemText = treeArr[treeId].tree.getItemText(rootItemId);"
                + "    itemText = itemText.replace(/<\\/?[^>]+(>|$)/g, \"\");" //https://stackoverflow.com/questions/5002111/javascript-how-to-strip-html-tags-from-string
                + "    if (itemText == treePathArr[0]) {"
                + "        treeArr[treeId].tree.selectItem(rootItemId, true, false);"
                + "    } else {"
                + "        throw \"error3\";"
                + "    }"
                + "} else {"
                + "    var itemId = qwerty(treeId, rootItemId, treePathArr);"
                + "    treeArr[treeId].tree.selectItem(itemId, true, false);"
                + "}"
                + ""
                + "function qwerty(treeId, itemId, treePathArr) {"
                + "    var subItemsStr = treeArr[treeId].tree.getSubItems(itemId);"
                + "    var subItemsArr = subItemsStr.split(',');"
                + "    for (var i = 0; i < subItemsArr.length; i++) {"
                + "        subItemsArr[i] = subItemsArr[i].trim();"
                + "    }"
                + ""
                + "    var itemExist = false;"
                + "    var item;"
                + "    for (var i = 0; i < subItemsArr.length; i++) {"
                + "        var itemText = treeArr[treeId].tree.getItemText(subItemsArr[i]);"
                + "        itemText = itemText.replace(/<\\/?[^>]+(>|$)/g, \"\");" //https://stackoverflow.com/questions/5002111/javascript-how-to-strip-html-tags-from-string
                + ""
                + "        if (itemText == treePathArr[1]) {"
                + "            if (itemExist) {"
                + "                throw \"error1\";"
                + "            }"
                + "            itemExist = true;"
                + "            item = subItemsArr[i];"
                + "        }"
                + "    }"
                + ""
                + "    if (itemExist) {"
                + "        treePathArr.shift();"
                + "        if (treePathArr.length == 1) {"
                + "            return item;"
                + "        } else {"
                + "            return qwerty(treeId, item, treePathArr);"
                + "        }"
                + "    } else {"
                + "        throw \"error2\";"
                + "    }"
                + "}");
    }

}
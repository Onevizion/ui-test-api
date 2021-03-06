package com.onevizion.uitest.api.helper.tree;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.vo.TreeNode;
import com.onevizion.uitest.api.vo.entity.MenuItem;
import com.onevizion.uitest.api.vo.entity.TrackorTreeItem;

@Component
public class Tree {

    @Autowired
    private TreeJs treeJs;

    @Autowired
    private TreeWait treeWait;

    public void selectItem(Long treeId, String rootItemId, TrackorTreeItem trackorTreeItem) {
        treeJs.selectItem(treeId, rootItemId, trackorTreeItem.getTreePath());
    }

    public void selectItem(Long treeId, String rootItemId, MenuItem menuItem) {
        treeJs.selectItem(treeId, rootItemId, menuItem.getMenuPath());
    }

    public void selectParentItem(Long treeId, String rootItemId, TrackorTreeItem trackorTreeItem) {
        treeJs.selectParentItem(treeId, rootItemId, trackorTreeItem.getTreePath());
    }

    public TreeNode get(Long treeId) {
        return getNode(treeId, "-1");
    }

    private TreeNode getNode(Long treeId, String itemId) {
        String name = treeJs.getItemTextById(treeId.toString(), itemId);
        name = name.replaceAll("^<[lL][aA][bB][eE][lL].*?>", "").replaceAll("</[lL][aA][bB][eE][lL]>$", "");

        TreeNode tree = new TreeNode(name);

        String subItemsStr = treeJs.getSubItems(treeId, itemId);
        if (StringUtils.isEmpty(subItemsStr)) {
            return tree;
        }

        String[] subItems = subItemsStr.split(",");
        for (String subItem : subItems) {
            tree.addNode(getNode(treeId, subItem));
        }

        return tree;
    }

    public void waitLoad(Long treeId) {
        treeWait.waitLoad(treeId);
    }

    public void waitLoad(String treeId) {
        treeWait.waitLoad(treeId);
    }

    public String getAllSubItems(Long treeId, String itemId) {
        return getAllSubItems(treeId.toString(), itemId);
    }

    public String getAllSubItems(String treeId, String itemId) {
        return treeJs.getAllSubItems(treeId, itemId);
    }

    public String getSubItems(Long treeId, String itemId) {
        return treeJs.getSubItems(treeId, itemId);
    }

    public void selectItem(Long treeId, String itemId) {
        treeJs.selectItem(treeId, itemId);
    }

    public String getItemTextById(Long treeId, String itemId) {
        return getItemTextById(treeId.toString(), itemId);
    }

    public String getItemTextById(String treeId, String itemId) {
        return treeJs.getItemTextById(treeId, itemId);
    }

    public String getItemIdByText(Long treeId, String itemText) {
        return treeJs.getItemIdByText(treeId.toString(), itemText);
    }

    public String getSelectedItem(Long treeId) {
        return treeJs.getSelectedItem(treeId);
    }

    public void checkItemCheckbox(Long treeId, String itemId) {
        treeJs.checkItemCheckbox(treeId, itemId);
    }

}
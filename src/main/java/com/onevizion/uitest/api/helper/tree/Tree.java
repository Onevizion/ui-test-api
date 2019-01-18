package com.onevizion.uitest.api.helper.tree;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.vo.TreeNode;
import com.onevizion.uitest.api.vo.entity.MenuItem;
import com.onevizion.uitest.api.vo.entity.TrackorTreeItem;

@Component
public class Tree {

    @Resource
    private Element element;

    @Resource
    private TreeJs treeJs;

    @Resource
    private TreeWait treeWait;

    @Resource
    private SeleniumSettings seleniumSettings;

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
        String name = treeJs.getItemTextById(treeId, itemId);
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
        return treeJs.getAllSubItems(treeId, itemId);
    }

    public void selectItem(Long treeId, String itemId) {
        treeJs.selectItem(treeId, itemId);
    }

    public String getItemTextById(Long treeId, String itemId) {
        return treeJs.getItemTextById(treeId, itemId);
    }

    public String getSelectedItem(Long treeId) {
        return treeJs.getSelectedItem(treeId);
    }

    public void checkItemCheckbox(Long treeId, String itemId) {
        treeJs.checkItemCheckbox(treeId, itemId);
    }

}
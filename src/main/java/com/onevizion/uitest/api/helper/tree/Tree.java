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

    public void selectTreeItem(Long treeId, String rootItemId, TrackorTreeItem trackorTreeItem) {
        treeJs.selectItemInTree(treeId, rootItemId, trackorTreeItem.getTreePath());
    }

    public void selectTreeItem(Long treeId, String rootItemId, MenuItem menuItem) {
        treeJs.selectItemInTree(treeId, rootItemId, menuItem.getMenuPath());
    }

    public void selectParentTreeItem(Long treeId, String rootItemId, TrackorTreeItem trackorTreeItem) {
        treeJs.selectParentItemInTree(treeId, rootItemId, trackorTreeItem.getTreePath());
    }

    public TreeNode getTree(Long treeId) {
        return getTreeNode(treeId, "-1");
    }

    private TreeNode getTreeNode(Long treeId, String itemId) {
        String name = treeJs.getItemTextInTreeById(treeId, itemId);
        name = name.replaceAll("^<[lL][aA][bB][eE][lL].*?>", "").replaceAll("</[lL][aA][bB][eE][lL]>$", "");

        TreeNode tree = new TreeNode(name);

        String subItemsStr = treeJs.getTreeSubItems(treeId, itemId);
        if (StringUtils.isEmpty(subItemsStr)) {
            return tree;
        }

        String[] subItems = subItemsStr.split(",");
        for (String subItem : subItems) {
            tree.addNode(getTreeNode(treeId, subItem));
        }

        return tree;
    }

    public void waitTreeLoad(Long treeId) {
        treeWait.waitTreeLoad(treeId);
    }

    public void waitTreeLoad(String treeId) {
        treeWait.waitTreeLoad(treeId);
    }

    public String getTreeAllSubItems(Long treeId, String itemId) {
        return treeJs.getTreeAllSubItems(treeId, itemId);
    }

    public void selectItemInTree(Long treeId, String itemId) {
        treeJs.selectItemInTree(treeId, itemId);
    }

    public String getItemTextInTreeById(Long treeId, String itemId) {
        return treeJs.getItemTextInTreeById(treeId, itemId);
    }

    public String getSelectedItemInTree(Long treeId) {
        return treeJs.getSelectedItemInTree(treeId);
    }

}
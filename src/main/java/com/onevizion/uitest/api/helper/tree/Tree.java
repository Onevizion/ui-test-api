package com.onevizion.uitest.api.helper.tree;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
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

    public void selectTreeItem(String itemText) {
        String itemId = "";
        String curText = "";
        while((!curText.equals(itemText)) && (itemId != null)) {
            itemId = treeJs.getItemIdInTreeByText(AbstractSeleniumCore.getTreeIdx(), itemText);
            curText = treeJs.getItemTextInTreeById(AbstractSeleniumCore.getTreeIdx(), itemId).replaceAll("^<.*?>", "").replaceAll("</.*?>$", "");
        } 
    }

    public String getTreeItemParentText(String itemText) {
        selectTreeItem(itemText);
        String itemId = treeJs.getSelectedItemInTree(AbstractSeleniumCore.getTreeIdx());
        String parentId = treeJs.getItemParentId(AbstractSeleniumCore.getTreeIdx(), itemId);
        String parentText = treeJs.getItemTextInTreeById(AbstractSeleniumCore.getTreeIdx(), parentId);
        parentText = parentText.replaceAll("^<.*?>", "").replaceAll("</.*?>$", "");
        return parentText;
    }

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

    public void waitTreeLoadCnt(int cnt) {
        treeWait.waitTreeLoadCnt(cnt);
    }

    public Boolean isTreeArrExist() {
        return treeJs.isTreeArrExist();
    }

    public String getTreeAllSubItems(Long treeId, String itemId) {
        return treeJs.getTreeAllSubItems(treeId, itemId);
    }

    public String getTreeSubItems(Long treeId, String itemId) {
        return treeJs.getTreeSubItems(treeId, itemId);
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

    public String getItemParentId(Long treeId, String itemId) {
        return treeJs.getItemParentId(treeId, itemId);
    }

    public String getItemIdInTreeByText(Long treeId, String itemText) {
        return treeJs.getItemIdInTreeByText(treeId, itemText);
    }

    public void selectItemInTree(Long treeId, String rootItemId, String treePath) {
        treeJs.selectItemInTree(treeId, rootItemId, treePath);
    }

    public void selectParentItemInTree(Long treeId, String rootItemId, String treePath) {
        treeJs.selectParentItemInTree(treeId, rootItemId, treePath);
    }

}
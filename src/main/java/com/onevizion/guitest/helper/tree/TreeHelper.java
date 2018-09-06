package com.onevizion.guitest.helper.tree;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.onevizion.guitest.AbstractSeleniumCore;
import com.onevizion.guitest.SeleniumSettings;
import com.onevizion.guitest.helper.ElementHelper;
import com.onevizion.guitest.vo.TreeNode;
import com.onevizion.guitest.vo.entity.MenuItem;
import com.onevizion.guitest.vo.entity.TrackorTreeItem;

@Component
public class TreeHelper {

    @Resource
    private ElementHelper elementHelper;

    @Resource
    private TreeJsHelper treeJsHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    public void selectTreeItem(String itemText) {
        String itemId = "";
        String curText = "";
        while((!curText.equals(itemText)) && (itemId != null)) {
            itemId = treeJsHelper.getItemIdInTreeByText(AbstractSeleniumCore.getTreeIdx(), itemText);
            curText = treeJsHelper.getItemTextInTreeById(AbstractSeleniumCore.getTreeIdx(), itemId).replaceAll("^<.*?>", "").replaceAll("</.*?>$", "");
        } 
    }

    public String getTreeItemParentText(String itemText) {
        selectTreeItem(itemText);
        String itemId = treeJsHelper.getSelectedItemInTree(AbstractSeleniumCore.getTreeIdx());
        String parentId = treeJsHelper.getItemParentId(AbstractSeleniumCore.getTreeIdx(), itemId);
        String parentText = treeJsHelper.getItemTextInTreeById(AbstractSeleniumCore.getTreeIdx(), parentId);
        parentText = parentText.replaceAll("^<.*?>", "").replaceAll("</.*?>$", "");
        return parentText;
    }

    public void selectTreeItem(Long treeId, String rootItemId, TrackorTreeItem trackorTreeItem) {
        treeJsHelper.selectItemInTree(treeId, rootItemId, trackorTreeItem.getTreePath());
    }

    public void selectTreeItem(Long treeId, String rootItemId, MenuItem menuItem) {
        treeJsHelper.selectItemInTree(treeId, rootItemId, menuItem.getMenuPath());
    }

    public void selectParentTreeItem(Long treeId, String rootItemId, TrackorTreeItem trackorTreeItem) {
        treeJsHelper.selectParentItemInTree(treeId, rootItemId, trackorTreeItem.getTreePath());
    }

    public TreeNode getTree(Long treeId) {
        return getTreeNode(treeId, "-1");
    }

    private TreeNode getTreeNode(Long treeId, String itemId) {
        String name = treeJsHelper.getItemTextInTreeById(treeId, itemId);
        name = name.replaceAll("^<[lL][aA][bB][eE][lL].*?>", "").replaceAll("</[lL][aA][bB][eE][lL]>$", "");

        TreeNode tree = new TreeNode(name);

        String subItemsStr = treeJsHelper.getTreeSubItems(treeId, itemId);
        if (StringUtils.isEmpty(subItemsStr)) {
            return tree;
        }

        String[] subItems = subItemsStr.split(",");
        for (String subItem : subItems) {
            tree.addNode(getTreeNode(treeId, subItem));
        }

        return tree;
    }

}
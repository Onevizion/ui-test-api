package com.onevizion.uitest.api.vo;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private String name;
    private List<TreeNode> nodes = new ArrayList<>();

    public TreeNode(String name) {
        this.name = name;
    }

    public void addNode(TreeNode treeNode){
        nodes.add(treeNode);
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        TreeNode other = (TreeNode) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }

        if (nodes == null) {
            if (other.nodes != null) {
                return false;
            }
        } else if (!nodes.equals(other.nodes)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return toStringNode(0, this);
    }

    private String toStringNode(int level, TreeNode tree) {
        String indent = "    ";
        String result = System.lineSeparator();

        for (int i = 0; i < level; i++) {
            result = result + indent;
        }

        result = result + tree.getName();
        for (TreeNode node : tree.nodes) {
            result = result + toStringNode(level + 1, node);
        }

        return result;
    }

}
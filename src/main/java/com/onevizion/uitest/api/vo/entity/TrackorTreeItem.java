package com.onevizion.uitest.api.vo.entity;

public class TrackorTreeItem {

    private String treePath;
    private String trackorType;
    private String cardinality;
    private String uniqueBy;
    private String color;
    private String childRequiresParent;
    private String onParentDeleteCascade;
    private String lockable;
    private String showAll;

    public TrackorTreeItem(String treePath, String trackorType, String cardinality, String uniqueBy, String color, String childRequiresParent, String onParentDeleteCascade, String lockable, String showAll) {
        this.treePath = treePath;
        this.trackorType = trackorType;
        this.cardinality = cardinality;
        this.uniqueBy = uniqueBy;
        this.color = color;
        this.childRequiresParent = childRequiresParent;
        this.onParentDeleteCascade = onParentDeleteCascade;
        this.lockable = lockable;
        this.showAll = showAll;
    }

    public String getTreePath() {
        return treePath;
    }

    public String getTrackorType() {
        return trackorType;
    }

    public String getCardinality() {
        return cardinality;
    }

    public String getUniqueBy() {
        return uniqueBy;
    }

    public String getColor() {
        return color;
    }

    public String getChildRequiresParent() {
        return childRequiresParent;
    }

    public String getOnParentDeleteCascade() {
        return onParentDeleteCascade;
    }

    public String getLockable() {
        return lockable;
    }

    public String getShowAll() {
        return showAll;
    }

}
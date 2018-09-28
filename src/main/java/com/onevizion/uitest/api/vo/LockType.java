package com.onevizion.uitest.api.vo;

public enum LockType {

    LOCK("Lock", "locked"),
    UNLOCK("Unlock", "unlocked");

    private String label;
    private String gridCellClass;

    private LockType(String label, String gridCellClass) {
        this.label = label;
        this.gridCellClass = gridCellClass;
    }

    public String getLabel() {
        return label;
    }

    public String getGridCellClass() {
        return gridCellClass;
    }

    public static LockType getByGridCellClass(String gridCellClass) {
        for (LockType lockType : values()) {
            if (lockType.getGridCellClass().equals(gridCellClass)) {
                return lockType;
            }
        }
        throw new EnumConstantNotPresentException(LockType.class, gridCellClass);
    }

}
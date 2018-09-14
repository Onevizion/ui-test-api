package com.onevizion.uitest.api.vo;

public enum DashletCalcMethodType {

    AVERAGE("Average", "avg", 0L),
    SUM("Sum", "sum", 1L),
    MAXIMUM("Maximum", "max", 2L),
    MINIMUM("Minimum", "min", 3L),
    COUNT("Count", "coung", 4L);

    private String label;

    private String name;

    private Long idx;

    private DashletCalcMethodType(String label, String name, Long idx) {
        this.label = label;
        this.name = name;
        this.idx = idx;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public Long getIdx() {
        return idx;
    }


}
package com.onevizion.uitest.api.vo;

public enum DashColumnCalcMethodType {

    AVERAGE(0L),
    SUM(1L),
    MAXIMUM(2L),
    MINIMUM(3L),
    COUNT(4L),
    DISTINCT(5L);

    private Long idx;

    private DashColumnCalcMethodType(Long idx) {
        this.idx = idx;
    }

    public Long getIdx() {
        return idx;
    }

}
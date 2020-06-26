package com.onevizion.uitest.api.vo;

public enum DashColumnChartType {

    AREA(0L),
    BAR(1L),
    COLUMN(2L),
    LINE(3L),
    PIE(4L),
    SPLINE(5L),
    WATERFALL(8L);

    private Long idx;

    private DashColumnChartType(Long idx) {
        this.idx = idx;
    }

    public Long getIdx() {
        return idx;
    }

}
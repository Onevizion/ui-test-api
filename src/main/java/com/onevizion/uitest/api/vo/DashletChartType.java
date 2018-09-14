package com.onevizion.uitest.api.vo;

public enum DashletChartType {

    AREA("Area", 0L),
    BAR("Bar", 1L),
    COLUMN("Column", 2L),
    LINE("Line", 3L),
    PIE("Pie", 4L),
    SPLINE("Spine", 5L);

    private String label;

    private Long idx;

    private DashletChartType(String label, Long idx) {
        this.label = label;
        this.idx = idx;
    }

    public String getLabel() {
        return label;
    }

    public Long getIdx() {
        return idx;
    }

}
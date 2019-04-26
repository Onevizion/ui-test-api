package com.onevizion.uitest.api.vo;

public enum DashDisplayModeType {

    CHART(0L),
    TABLE(1L);

    private Long idx;

    private DashDisplayModeType(Long idx) {
        this.idx = idx;
    }

    public Long getIdx() {
        return idx;
    }

}
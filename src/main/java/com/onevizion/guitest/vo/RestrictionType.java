package com.onevizion.guitest.vo;

public enum RestrictionType {

    NONE(0L),
    UNION(1L),
    INTERSECTION(2L);

    private Long id;

    private RestrictionType(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
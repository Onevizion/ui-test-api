package com.onevizion.uitest.api.vo;

public enum ProcessStatusType {

    PENDING(0L),
    RUNNING(1L),
    EXECUTED_WITHOUT_ERRORS(2L),
    EXECUTED_WIT_ERRORS(3L),
    IN_QUEUE(4L),
    RUNNING_IMPORTING_DATA(5L),
    EXECUTED_WITHOUT_WARNINGS(6L),
    EXECUTED_WITH_WARNINGS(7L),
    RUNNING_PARSING_CSV(8L),
    RUNNING_SEARCHING_PK(9L),
    RUNNING_GENERATING_SQL(10L),
    RECOVERING(11L),
    RECOVERED(12L),
    AWAITING_PDF(13L),
    INTERRUPTED(14L),
    INTERRUPTING(15L),
    RUNNING_IMPORT_START_RULES(16L),
    RUNNING_IMPORT_FINISH_RULES(17L),
    CANCELED_NUM_CELLS_LIMIT(20L);

    private Long id;

    private ProcessStatusType(Long id) {
        this.id = id;
    }

    public static ProcessStatusType getForId(Long id) {
        for (ProcessStatusType s : ProcessStatusType.values()) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Not supported process status: [" + id + "]");
    }

    public Long getId() {
        return id;
    }

}
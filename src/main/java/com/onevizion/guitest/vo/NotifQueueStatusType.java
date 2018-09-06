package com.onevizion.guitest.vo;

public enum NotifQueueStatusType {

    BUILDING(0L),
    NOT_SENT(1L),
    SENDING(2L),
    FAIL_WILL_RETRY(3L),
    FAIL(4L),
    SUCCESS(5L);

    private Long id;

    private NotifQueueStatusType(Long id) {
        this.id = id;
    }

    public static NotifQueueStatusType getForId(Long id) {
        for (NotifQueueStatusType s : NotifQueueStatusType.values()) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Not supported notif queue status: [" + id + "]");
    }

    public Long getId() {
        return id;
    }

}
package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class TrackorClass {

    private String trackorType;
    private String name;
    private String orderNumber;
    private String usedCount;

    private TrackorClass() {
        
    }

    public static Builder newBuilder() {
        return new TrackorClass().new Builder();
    }

    public String getTrackorType() {
        return trackorType;
    }

    public String getName() {
        return name;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getUsedCount() {
        return usedCount;
    }

    public class Builder {

        private Builder() {
            
        }

        public TrackorClass build() {
            if (TrackorClass.this.trackorType == null ||
                    TrackorClass.this.name == null ||
                    TrackorClass.this.orderNumber == null ||
                    TrackorClass.this.usedCount == null) {
                throw new SeleniumUnexpectedException("");
            }
            return TrackorClass.this;
        }

        public Builder setTrackorType(String trackorType) {
            TrackorClass.this.trackorType = trackorType;
            return this;
        }

        public Builder setName(String name) {
            TrackorClass.this.name = name;
            return this;
        }

        public Builder setOrderNumber(String orderNumber) {
            TrackorClass.this.orderNumber = orderNumber;
            return this;
        }

        public Builder setUsedCount(String usedCount) {
            TrackorClass.this.usedCount = usedCount;
            return this;
        }

    }

}
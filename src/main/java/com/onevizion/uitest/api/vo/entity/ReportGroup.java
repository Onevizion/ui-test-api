package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ReportGroup {

    private String name;

    private ReportGroup() {
        
    }

    public static Builder newBuilder() {
        return new ReportGroup().new Builder();
    }

    public String getName() {
        return name;
    }

    public class Builder {

        private Builder() {
            
        }

        public ReportGroup build() {
            if (ReportGroup.this.name == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ReportGroup.this;
        }

        public Builder setName(String name) {
            ReportGroup.this.name = name;
            return this;
        }

    }

}
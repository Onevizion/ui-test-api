package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class ConfigFieldRollup {

    private String trackorType;

    private ConfigFieldRollup() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldRollup().new Builder();
    }

    public String getTrackorType() {
        return trackorType;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldRollup build() {
            if (ConfigFieldRollup.this.trackorType == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldRollup.this;
        }

        public Builder setTrackorType(String trackorType) {
            ConfigFieldRollup.this.trackorType = trackorType;
            return this;
        }

    }

}
package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoRollup {

    private String trackorType;

    private ConfigFieldVoRollup() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoRollup().new Builder();
    }

    public String getTrackorType() {
        return trackorType;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoRollup build() {
            if (ConfigFieldVoRollup.this.trackorType == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoRollup.this;
        }

        public Builder setTrackorType(String trackorType) {
            ConfigFieldVoRollup.this.trackorType = trackorType;
            return this;
        }

    }

}
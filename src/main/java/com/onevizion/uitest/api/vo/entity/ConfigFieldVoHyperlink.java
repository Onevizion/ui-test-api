package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoHyperlink {

    private String length;

    private ConfigFieldVoHyperlink() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoHyperlink().new Builder();
    }

    public String getLength() {
        return length;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoHyperlink build() {
            if (ConfigFieldVoHyperlink.this.length == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoHyperlink.this;
        }

        public Builder setLength(String length) {
            ConfigFieldVoHyperlink.this.length = length;
            return this;
        }

    }

}
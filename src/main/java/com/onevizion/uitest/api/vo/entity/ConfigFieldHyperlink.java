package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldHyperlink {

    private String length;

    private ConfigFieldHyperlink() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldHyperlink().new Builder();
    }

    public String getLength() {
        return length;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldHyperlink build() {
            if (ConfigFieldHyperlink.this.length == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldHyperlink.this;
        }

        public Builder setLength(String length) {
            ConfigFieldHyperlink.this.length = length;
            return this;
        }

    }

}
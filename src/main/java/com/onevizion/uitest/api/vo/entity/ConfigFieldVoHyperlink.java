package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoHyperlink {

    private String length;
    private String validation;
    private String validationEnabled;

    private ConfigFieldVoHyperlink() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoHyperlink().new Builder();
    }

    public String getLength() {
        return length;
    }

    public String getValidation() {
        return validation;
    }

    public String getValidationEnabled() {
        return validationEnabled;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoHyperlink build() {
            if (ConfigFieldVoHyperlink.this.length == null ||
                    ConfigFieldVoHyperlink.this.validation == null ||
                    ConfigFieldVoHyperlink.this.validationEnabled == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoHyperlink.this;
        }

        public Builder setLength(String length) {
            ConfigFieldVoHyperlink.this.length = length;
            return this;
        }

        public Builder setValidation(String validation) {
            ConfigFieldVoHyperlink.this.validation = validation;
            return this;
        }

        public Builder setValidationEnabled(String validationEnabled) {
            ConfigFieldVoHyperlink.this.validationEnabled = validationEnabled;
            return this;
        }

    }

}
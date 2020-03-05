package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoTime {

    private String defValueSql;
    private String showSeconds;
    private String validation;
    private String validationEnabled;

    private ConfigFieldVoTime() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoTime().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public String getShowSeconds() {
        return showSeconds;
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

        public ConfigFieldVoTime build() {
            if (ConfigFieldVoTime.this.defValueSql == null ||
                    ConfigFieldVoTime.this.showSeconds == null ||
                    ConfigFieldVoTime.this.validation == null ||
                    ConfigFieldVoTime.this.validationEnabled == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoTime.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoTime.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setShowSeconds(String showSeconds) {
            ConfigFieldVoTime.this.showSeconds = showSeconds;
            return this;
        }

        public Builder setValidation(String validation) {
            ConfigFieldVoTime.this.validation = validation;
            return this;
        }

        public Builder setValidationEnabled(String validationEnabled) {
            ConfigFieldVoTime.this.validationEnabled = validationEnabled;
            return this;
        }

    }

}
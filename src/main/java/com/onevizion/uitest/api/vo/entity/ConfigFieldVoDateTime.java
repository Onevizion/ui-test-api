package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoDateTime {

    private String defValueSql;
    private String showSeconds;
    private String validation;
    private String validationEnabled;

    private ConfigFieldVoDateTime() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoDateTime().new Builder();
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

        public ConfigFieldVoDateTime build() {
            if (ConfigFieldVoDateTime.this.defValueSql == null ||
                    ConfigFieldVoDateTime.this.showSeconds == null ||
                    ConfigFieldVoDateTime.this.validation == null ||
                    ConfigFieldVoDateTime.this.validationEnabled == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoDateTime.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoDateTime.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setShowSeconds(String showSeconds) {
            ConfigFieldVoDateTime.this.showSeconds = showSeconds;
            return this;
        }

        public Builder setValidation(String validation) {
            ConfigFieldVoDateTime.this.validation = validation;
            return this;
        }

        public Builder setValidationEnabled(String validationEnabled) {
            ConfigFieldVoDateTime.this.validationEnabled = validationEnabled;
            return this;
        }

    }

}
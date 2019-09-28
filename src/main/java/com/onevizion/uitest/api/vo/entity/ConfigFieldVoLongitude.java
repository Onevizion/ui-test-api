package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoLongitude {

    private String defValueSql;
    private String validation;
    private String validationEnabled;

    private ConfigFieldVoLongitude() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoLongitude().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
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

        public ConfigFieldVoLongitude build() {
            if (ConfigFieldVoLongitude.this.defValueSql == null ||
                    ConfigFieldVoLongitude.this.validation == null ||
                    ConfigFieldVoLongitude.this.validationEnabled == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoLongitude.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoLongitude.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setValidation(String validation) {
            ConfigFieldVoLongitude.this.validation = validation;
            return this;
        }

        public Builder setValidationEnabled(String validationEnabled) {
            ConfigFieldVoLongitude.this.validationEnabled = validationEnabled;
            return this;
        }

    }

}
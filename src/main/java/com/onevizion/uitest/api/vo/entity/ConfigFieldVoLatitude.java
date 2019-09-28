package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoLatitude {

    private String defValueSql;
    private String validation;
    private String validationEnabled;

    private ConfigFieldVoLatitude() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoLatitude().new Builder();
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

        public ConfigFieldVoLatitude build() {
            if (ConfigFieldVoLatitude.this.defValueSql == null ||
                    ConfigFieldVoLatitude.this.validation == null ||
                    ConfigFieldVoLatitude.this.validationEnabled == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoLatitude.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoLatitude.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setValidation(String validation) {
            ConfigFieldVoLatitude.this.validation = validation;
            return this;
        }

        public Builder setValidationEnabled(String validationEnabled) {
            ConfigFieldVoLatitude.this.validationEnabled = validationEnabled;
            return this;
        }

    }

}
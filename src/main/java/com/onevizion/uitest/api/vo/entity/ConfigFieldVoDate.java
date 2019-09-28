package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoDate {

    private String defValueSql;
    private String validation;
    private String validationEnabled;

    private ConfigFieldVoDate() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoDate().new Builder();
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

        public ConfigFieldVoDate build() {
            if (ConfigFieldVoDate.this.defValueSql == null ||
                    ConfigFieldVoDate.this.validation == null ||
                    ConfigFieldVoDate.this.validationEnabled == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoDate.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoDate.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setValidation(String validation) {
            ConfigFieldVoDate.this.validation = validation;
            return this;
        }

        public Builder setValidationEnabled(String validationEnabled) {
            ConfigFieldVoDate.this.validationEnabled = validationEnabled;
            return this;
        }

    }

}
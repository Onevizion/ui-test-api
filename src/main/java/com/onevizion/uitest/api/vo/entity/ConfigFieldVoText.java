package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoText {

    private String length;
    private String defValueSql;
    private String validation;
    private String validationEnabled;

    private ConfigFieldVoText() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoText().new Builder();
    }

    public String getLength() {
        return length;
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

        public ConfigFieldVoText build() {
            if (ConfigFieldVoText.this.length == null ||
                    ConfigFieldVoText.this.defValueSql == null ||
                    ConfigFieldVoText.this.validation == null ||
                    ConfigFieldVoText.this.validationEnabled == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoText.this;
        }

        public Builder setLength(String length) {
            ConfigFieldVoText.this.length = length;
            return this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoText.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setValidation(String validation) {
            ConfigFieldVoText.this.validation = validation;
            return this;
        }

        public Builder setValidationEnabled(String validationEnabled) {
            ConfigFieldVoText.this.validationEnabled = validationEnabled;
            return this;
        }

    }

}
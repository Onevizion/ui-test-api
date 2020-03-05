package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoWiki {

    private String length;
    private String defValueSql;
    private String lines;
    private String validation;
    private String validationEnabled;

    private ConfigFieldVoWiki() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoWiki().new Builder();
    }

    public String getLength() {
        return length;
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public String getLines() {
        return lines;
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

        public ConfigFieldVoWiki build() {
            if (ConfigFieldVoWiki.this.length == null ||
                    ConfigFieldVoWiki.this.defValueSql == null ||
                    ConfigFieldVoWiki.this.lines == null ||
                    ConfigFieldVoWiki.this.validation == null ||
                    ConfigFieldVoWiki.this.validationEnabled == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoWiki.this;
        }

        public Builder setLength(String length) {
            ConfigFieldVoWiki.this.length = length;
            return this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoWiki.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setLines(String lines) {
            ConfigFieldVoWiki.this.lines = lines;
            return this;
        }

        public Builder setValidation(String validation) {
            ConfigFieldVoWiki.this.validation = validation;
            return this;
        }

        public Builder setValidationEnabled(String validationEnabled) {
            ConfigFieldVoWiki.this.validationEnabled = validationEnabled;
            return this;
        }

    }

}
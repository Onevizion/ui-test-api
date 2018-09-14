package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoText {

    private String length;
    private String defValueSql;

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

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoText build() {
            if (ConfigFieldVoText.this.length == null ||
                    ConfigFieldVoText.this.defValueSql == null) {
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

    }

}
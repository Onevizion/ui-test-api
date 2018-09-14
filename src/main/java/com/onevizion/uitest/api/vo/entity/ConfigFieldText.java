package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldText {

    private String length;
    private String defValueSql;

    private ConfigFieldText() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldText().new Builder();
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

        public ConfigFieldText build() {
            if (ConfigFieldText.this.length == null ||
                    ConfigFieldText.this.defValueSql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldText.this;
        }

        public Builder setLength(String length) {
            ConfigFieldText.this.length = length;
            return this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldText.this.defValueSql = defValueSql;
            return this;
        }

    }

}
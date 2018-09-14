package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldDate {

    private String defValueSql;

    private ConfigFieldDate() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldDate().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldDate build() {
            if (ConfigFieldDate.this.defValueSql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldDate.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldDate.this.defValueSql = defValueSql;
            return this;
        }

    }

}
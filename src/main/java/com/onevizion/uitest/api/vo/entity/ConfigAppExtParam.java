package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigAppExtParam {

    private String name;
    private String description;
    private String sql;

    private ConfigAppExtParam() {
        
    }

    public static Builder newBuilder() {
        return new ConfigAppExtParam().new Builder();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSql() {
        return sql;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigAppExtParam build() {
            if (ConfigAppExtParam.this.name == null ||
                    ConfigAppExtParam.this.description == null ||
                    ConfigAppExtParam.this.sql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigAppExtParam.this;
        }

        public Builder setName(String name) {
            ConfigAppExtParam.this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            ConfigAppExtParam.this.description = description;
            return this;
        }

        public Builder setSql(String sql) {
            ConfigAppExtParam.this.sql = sql;
            return this;
        }

    }

}
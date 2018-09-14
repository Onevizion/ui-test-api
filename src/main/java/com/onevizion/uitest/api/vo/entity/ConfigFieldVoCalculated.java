package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoCalculated {

    private String sql;

    private ConfigFieldVoCalculated() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoCalculated().new Builder();
    }

    public String getSql() {
        return sql;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoCalculated build() {
            if (ConfigFieldVoCalculated.this.sql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoCalculated.this;
        }

        public Builder setSql(String sql) {
            ConfigFieldVoCalculated.this.sql = sql;
            return this;
        }

    }

}
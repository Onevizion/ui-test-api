package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class ConfigFieldCalculated {

    private String sql;

    private ConfigFieldCalculated() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldCalculated().new Builder();
    }

    public String getSql() {
        return sql;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldCalculated build() {
            if (ConfigFieldCalculated.this.sql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldCalculated.this;
        }

        public Builder setSql(String sql) {
            ConfigFieldCalculated.this.sql = sql;
            return this;
        }

    }

}
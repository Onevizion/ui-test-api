package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoDbSelector {

    private String defValueSql;
    private String sql;

    private ConfigFieldVoDbSelector() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoDbSelector().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public String getSql() {
        return sql;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoDbSelector build() {
            if (ConfigFieldVoDbSelector.this.defValueSql == null ||
                    ConfigFieldVoDbSelector.this.sql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoDbSelector.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoDbSelector.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setSql(String sql) {
            ConfigFieldVoDbSelector.this.sql = sql;
            return this;
        }

    }

}
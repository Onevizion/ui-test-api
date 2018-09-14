package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoDbDropDown {

    private String defValueSql;
    private String sql;

    private ConfigFieldVoDbDropDown() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoDbDropDown().new Builder();
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

        public ConfigFieldVoDbDropDown build() {
            if (ConfigFieldVoDbDropDown.this.defValueSql == null ||
                    ConfigFieldVoDbDropDown.this.sql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoDbDropDown.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoDbDropDown.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setSql(String sql) {
            ConfigFieldVoDbDropDown.this.sql = sql;
            return this;
        }

    }

}
package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldDbDropDown {

    private String defValueSql;
    private String sql;

    private ConfigFieldDbDropDown() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldDbDropDown().new Builder();
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

        public ConfigFieldDbDropDown build() {
            if (ConfigFieldDbDropDown.this.defValueSql == null ||
                    ConfigFieldDbDropDown.this.sql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldDbDropDown.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldDbDropDown.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setSql(String sql) {
            ConfigFieldDbDropDown.this.sql = sql;
            return this;
        }

    }

}
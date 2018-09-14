package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldDbSelector {

    private String defValueSql;
    private String sql;

    private ConfigFieldDbSelector() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldDbSelector().new Builder();
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

        public ConfigFieldDbSelector build() {
            if (ConfigFieldDbSelector.this.defValueSql == null ||
                    ConfigFieldDbSelector.this.sql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldDbSelector.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldDbSelector.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setSql(String sql) {
            ConfigFieldDbSelector.this.sql = sql;
            return this;
        }

    }

}
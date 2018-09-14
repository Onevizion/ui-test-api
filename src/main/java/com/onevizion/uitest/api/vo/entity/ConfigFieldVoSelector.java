package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoSelector {

    private String defValueSql;
    private String table;

    private ConfigFieldVoSelector() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoSelector().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public String getTable() {
        return table;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoSelector build() {
            if (ConfigFieldVoSelector.this.defValueSql == null ||
                    ConfigFieldVoSelector.this.table == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoSelector.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoSelector.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setTable(String table) {
            ConfigFieldVoSelector.this.table = table;
            return this;
        }

    }

}
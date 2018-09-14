package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldSelector {

    private String defValueSql;
    private String table;

    private ConfigFieldSelector() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldSelector().new Builder();
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

        public ConfigFieldSelector build() {
            if (ConfigFieldSelector.this.defValueSql == null ||
                    ConfigFieldSelector.this.table == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldSelector.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldSelector.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setTable(String table) {
            ConfigFieldSelector.this.table = table;
            return this;
        }

    }

}
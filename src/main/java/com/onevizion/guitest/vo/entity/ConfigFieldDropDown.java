package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class ConfigFieldDropDown {

    private String defValueSql;
    private String table;

    private ConfigFieldDropDown() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldDropDown().new Builder();
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

        public ConfigFieldDropDown build() {
            if (ConfigFieldDropDown.this.defValueSql == null ||
                    ConfigFieldDropDown.this.table == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldDropDown.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldDropDown.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setTable(String table) {
            ConfigFieldDropDown.this.table = table;
            return this;
        }

    }

}
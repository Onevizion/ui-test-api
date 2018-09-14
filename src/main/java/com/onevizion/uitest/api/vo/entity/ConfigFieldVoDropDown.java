package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoDropDown {

    private String defValueSql;
    private String table;

    private ConfigFieldVoDropDown() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoDropDown().new Builder();
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

        public ConfigFieldVoDropDown build() {
            if (ConfigFieldVoDropDown.this.defValueSql == null ||
                    ConfigFieldVoDropDown.this.table == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoDropDown.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoDropDown.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setTable(String table) {
            ConfigFieldVoDropDown.this.table = table;
            return this;
        }

    }

}
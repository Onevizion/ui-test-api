package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class ConfigFieldMultiSelector {

    private String defValueSql;
    private String lines;
    private String table;

    private ConfigFieldMultiSelector() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldMultiSelector().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public String getLines() {
        return lines;
    }

    public String getTable() {
        return table;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldMultiSelector build() {
            if (ConfigFieldMultiSelector.this.defValueSql == null ||
                    ConfigFieldMultiSelector.this.lines == null ||
                    ConfigFieldMultiSelector.this.table == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldMultiSelector.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldMultiSelector.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setLines(String lines) {
            ConfigFieldMultiSelector.this.lines = lines;
            return this;
        }

        public Builder setTable(String table) {
            ConfigFieldMultiSelector.this.table = table;
            return this;
        }

    }

}
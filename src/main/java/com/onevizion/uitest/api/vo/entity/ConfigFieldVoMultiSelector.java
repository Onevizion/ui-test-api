package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoMultiSelector {

    private String defValueSql;
    private String lines;
    private String table;

    private ConfigFieldVoMultiSelector() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoMultiSelector().new Builder();
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

        public ConfigFieldVoMultiSelector build() {
            if (ConfigFieldVoMultiSelector.this.defValueSql == null ||
                    ConfigFieldVoMultiSelector.this.lines == null ||
                    ConfigFieldVoMultiSelector.this.table == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoMultiSelector.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoMultiSelector.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setLines(String lines) {
            ConfigFieldVoMultiSelector.this.lines = lines;
            return this;
        }

        public Builder setTable(String table) {
            ConfigFieldVoMultiSelector.this.table = table;
            return this;
        }

    }

}
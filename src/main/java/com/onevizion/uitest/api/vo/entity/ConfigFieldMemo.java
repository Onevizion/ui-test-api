package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldMemo {

    private String length;
    private String defValueSql;
    private String lines;

    private ConfigFieldMemo() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldMemo().new Builder();
    }

    public String getLength() {
        return length;
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public String getLines() {
        return lines;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldMemo build() {
            if (ConfigFieldMemo.this.length == null ||
                    ConfigFieldMemo.this.defValueSql == null ||
                    ConfigFieldMemo.this.lines == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldMemo.this;
        }

        public Builder setLength(String length) {
            ConfigFieldMemo.this.length = length;
            return this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldMemo.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setLines(String lines) {
            ConfigFieldMemo.this.lines = lines;
            return this;
        }

    }

}
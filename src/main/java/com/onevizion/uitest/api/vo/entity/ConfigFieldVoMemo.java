package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoMemo {

    private String length;
    private String defValueSql;
    private String lines;

    private ConfigFieldVoMemo() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoMemo().new Builder();
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

        public ConfigFieldVoMemo build() {
            if (ConfigFieldVoMemo.this.length == null ||
                    ConfigFieldVoMemo.this.defValueSql == null ||
                    ConfigFieldVoMemo.this.lines == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoMemo.this;
        }

        public Builder setLength(String length) {
            ConfigFieldVoMemo.this.length = length;
            return this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoMemo.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setLines(String lines) {
            ConfigFieldVoMemo.this.lines = lines;
            return this;
        }

    }

}
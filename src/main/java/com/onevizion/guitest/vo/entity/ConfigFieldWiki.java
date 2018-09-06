package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class ConfigFieldWiki {

    private String length;
    private String defValueSql;
    private String lines;

    private ConfigFieldWiki() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldWiki().new Builder();
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

        public ConfigFieldWiki build() {
            if (ConfigFieldWiki.this.length == null ||
                    ConfigFieldWiki.this.defValueSql == null ||
                    ConfigFieldWiki.this.lines == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldWiki.this;
        }

        public Builder setLength(String length) {
            ConfigFieldWiki.this.length = length;
            return this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldWiki.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setLines(String lines) {
            ConfigFieldWiki.this.lines = lines;
            return this;
        }

    }

}
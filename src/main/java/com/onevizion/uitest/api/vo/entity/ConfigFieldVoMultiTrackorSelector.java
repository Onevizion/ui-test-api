package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoMultiTrackorSelector {

    private String defValueSql;
    private String trackorType;
    private String lines;

    private ConfigFieldVoMultiTrackorSelector() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoMultiTrackorSelector().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public String getTrackorType() {
        return trackorType;
    }

    public String getLines() {
        return lines;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoMultiTrackorSelector build() {
            if (ConfigFieldVoMultiTrackorSelector.this.defValueSql == null ||
                    ConfigFieldVoMultiTrackorSelector.this.trackorType == null ||
                    ConfigFieldVoMultiTrackorSelector.this.lines == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoMultiTrackorSelector.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoMultiTrackorSelector.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setTrackorType(String trackorType) {
            ConfigFieldVoMultiTrackorSelector.this.trackorType = trackorType;
            return this;
        }

        public Builder setLines(String lines) {
            ConfigFieldVoMultiTrackorSelector.this.lines = lines;
            return this;
        }

    }

}
package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoTrackorSelector {

    private String defValueSql;
    private String sql;
    private String trackorType;
    private String shortName;
    private String myThingsFilter;
    private String myThingsMarker;
    private String displayField;

    private ConfigFieldVoTrackorSelector() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoTrackorSelector().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public String getSql() {
        return sql;
    }

    public String getTrackorType() {
        return trackorType;
    }

    public String getShortName() {
        return shortName;
    }

    public String getMyThingsFilter() {
        return myThingsFilter;
    }

    public String getMyThingsMarker() {
        return myThingsMarker;
    }

    public String getDisplayField() {
        return displayField;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoTrackorSelector build() {
            if (ConfigFieldVoTrackorSelector.this.defValueSql == null ||
                    ConfigFieldVoTrackorSelector.this.sql == null ||
                    ConfigFieldVoTrackorSelector.this.trackorType == null ||
                    ConfigFieldVoTrackorSelector.this.shortName == null ||
                    ConfigFieldVoTrackorSelector.this.myThingsFilter == null ||
                    ConfigFieldVoTrackorSelector.this.myThingsMarker == null ||
                    ConfigFieldVoTrackorSelector.this.displayField == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoTrackorSelector.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoTrackorSelector.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setSql(String sql) {
            ConfigFieldVoTrackorSelector.this.sql = sql;
            return this;
        }

        public Builder setTrackorType(String trackorType) {
            ConfigFieldVoTrackorSelector.this.trackorType = trackorType;
            return this;
        }

        public Builder setShortName(String shortName) {
            ConfigFieldVoTrackorSelector.this.shortName = shortName;
            return this;
        }

        public Builder setMyThingsFilter(String myThingsFilter) {
            ConfigFieldVoTrackorSelector.this.myThingsFilter = myThingsFilter;
            return this;
        }

        public Builder setMyThingsMarker(String myThingsMarker) {
            ConfigFieldVoTrackorSelector.this.myThingsMarker = myThingsMarker;
            return this;
        }

        public Builder setDisplayField(String displayField) {
            ConfigFieldVoTrackorSelector.this.displayField = displayField;
            return this;
        }

    }

}
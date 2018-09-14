package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldTrackorSelector {

    private String defValueSql;
    private String sql;
    private String trackorType;
    private String shortName;
    private String myThingsFilter;
    private String myThingsMarker;
    private String displayField;

    private ConfigFieldTrackorSelector() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldTrackorSelector().new Builder();
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

        public ConfigFieldTrackorSelector build() {
            if (ConfigFieldTrackorSelector.this.defValueSql == null ||
                    ConfigFieldTrackorSelector.this.sql == null ||
                    ConfigFieldTrackorSelector.this.trackorType == null ||
                    ConfigFieldTrackorSelector.this.shortName == null ||
                    ConfigFieldTrackorSelector.this.myThingsFilter == null ||
                    ConfigFieldTrackorSelector.this.myThingsMarker == null ||
                    ConfigFieldTrackorSelector.this.displayField == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldTrackorSelector.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldTrackorSelector.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setSql(String sql) {
            ConfigFieldTrackorSelector.this.sql = sql;
            return this;
        }

        public Builder setTrackorType(String trackorType) {
            ConfigFieldTrackorSelector.this.trackorType = trackorType;
            return this;
        }

        public Builder setShortName(String shortName) {
            ConfigFieldTrackorSelector.this.shortName = shortName;
            return this;
        }

        public Builder setMyThingsFilter(String myThingsFilter) {
            ConfigFieldTrackorSelector.this.myThingsFilter = myThingsFilter;
            return this;
        }

        public Builder setMyThingsMarker(String myThingsMarker) {
            ConfigFieldTrackorSelector.this.myThingsMarker = myThingsMarker;
            return this;
        }

        public Builder setDisplayField(String displayField) {
            ConfigFieldTrackorSelector.this.displayField = displayField;
            return this;
        }

    }

}
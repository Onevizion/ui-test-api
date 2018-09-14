package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoTrackorDropDown {

    private String defValueSql;
    private String sql;
    private String trackorType;
    private String shortName;
    private String myThingsFilter;
    private String myThingsMarker;

    private ConfigFieldVoTrackorDropDown() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoTrackorDropDown().new Builder();
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

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoTrackorDropDown build() {
            if (ConfigFieldVoTrackorDropDown.this.defValueSql == null ||
                    ConfigFieldVoTrackorDropDown.this.sql == null ||
                    ConfigFieldVoTrackorDropDown.this.trackorType == null ||
                    ConfigFieldVoTrackorDropDown.this.shortName == null ||
                    ConfigFieldVoTrackorDropDown.this.myThingsFilter == null ||
                    ConfigFieldVoTrackorDropDown.this.myThingsMarker == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoTrackorDropDown.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoTrackorDropDown.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setSql(String sql) {
            ConfigFieldVoTrackorDropDown.this.sql = sql;
            return this;
        }

        public Builder setTrackorType(String trackorType) {
            ConfigFieldVoTrackorDropDown.this.trackorType = trackorType;
            return this;
        }

        public Builder setShortName(String shortName) {
            ConfigFieldVoTrackorDropDown.this.shortName = shortName;
            return this;
        }

        public Builder setMyThingsFilter(String myThingsFilter) {
            ConfigFieldVoTrackorDropDown.this.myThingsFilter = myThingsFilter;
            return this;
        }

        public Builder setMyThingsMarker(String myThingsMarker) {
            ConfigFieldVoTrackorDropDown.this.myThingsMarker = myThingsMarker;
            return this;
        }

    }

}
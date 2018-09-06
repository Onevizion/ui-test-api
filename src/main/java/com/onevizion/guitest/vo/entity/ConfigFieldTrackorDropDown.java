package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class ConfigFieldTrackorDropDown {

    private String defValueSql;
    private String sql;
    private String trackorType;
    private String shortName;
    private String myThingsFilter;
    private String myThingsMarker;

    private ConfigFieldTrackorDropDown() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldTrackorDropDown().new Builder();
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

        public ConfigFieldTrackorDropDown build() {
            if (ConfigFieldTrackorDropDown.this.defValueSql == null ||
                    ConfigFieldTrackorDropDown.this.sql == null ||
                    ConfigFieldTrackorDropDown.this.trackorType == null ||
                    ConfigFieldTrackorDropDown.this.shortName == null ||
                    ConfigFieldTrackorDropDown.this.myThingsFilter == null ||
                    ConfigFieldTrackorDropDown.this.myThingsMarker == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldTrackorDropDown.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldTrackorDropDown.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setSql(String sql) {
            ConfigFieldTrackorDropDown.this.sql = sql;
            return this;
        }

        public Builder setTrackorType(String trackorType) {
            ConfigFieldTrackorDropDown.this.trackorType = trackorType;
            return this;
        }

        public Builder setShortName(String shortName) {
            ConfigFieldTrackorDropDown.this.shortName = shortName;
            return this;
        }

        public Builder setMyThingsFilter(String myThingsFilter) {
            ConfigFieldTrackorDropDown.this.myThingsFilter = myThingsFilter;
            return this;
        }

        public Builder setMyThingsMarker(String myThingsMarker) {
            ConfigFieldTrackorDropDown.this.myThingsMarker = myThingsMarker;
            return this;
        }

    }

}
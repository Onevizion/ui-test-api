package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class ConfigFieldLongitude {

    private String defValueSql;

    private ConfigFieldLongitude() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldLongitude().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldLongitude build() {
            if (ConfigFieldLongitude.this.defValueSql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldLongitude.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldLongitude.this.defValueSql = defValueSql;
            return this;
        }

    }

}
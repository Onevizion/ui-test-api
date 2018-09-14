package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldLatitude {

    private String defValueSql;

    private ConfigFieldLatitude() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldLatitude().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldLatitude build() {
            if (ConfigFieldLatitude.this.defValueSql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldLatitude.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldLatitude.this.defValueSql = defValueSql;
            return this;
        }

    }

}
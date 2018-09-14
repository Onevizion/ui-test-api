package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoLatitude {

    private String defValueSql;

    private ConfigFieldVoLatitude() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoLatitude().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoLatitude build() {
            if (ConfigFieldVoLatitude.this.defValueSql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoLatitude.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoLatitude.this.defValueSql = defValueSql;
            return this;
        }

    }

}
package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoLongitude {

    private String defValueSql;

    private ConfigFieldVoLongitude() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoLongitude().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoLongitude build() {
            if (ConfigFieldVoLongitude.this.defValueSql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoLongitude.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoLongitude.this.defValueSql = defValueSql;
            return this;
        }

    }

}
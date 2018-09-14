package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoDate {

    private String defValueSql;

    private ConfigFieldVoDate() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoDate().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoDate build() {
            if (ConfigFieldVoDate.this.defValueSql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoDate.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoDate.this.defValueSql = defValueSql;
            return this;
        }

    }

}
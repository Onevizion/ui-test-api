package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class ConfigFieldCheckbox {

    private String defValueSql;

    private ConfigFieldCheckbox() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldCheckbox().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldCheckbox build() {
            if (ConfigFieldCheckbox.this.defValueSql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldCheckbox.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldCheckbox.this.defValueSql = defValueSql;
            return this;
        }

    }

}
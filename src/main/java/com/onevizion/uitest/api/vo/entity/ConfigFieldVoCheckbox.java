package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoCheckbox {

    private String defValueSql;

    private ConfigFieldVoCheckbox() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoCheckbox().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoCheckbox build() {
            if (ConfigFieldVoCheckbox.this.defValueSql == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoCheckbox.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoCheckbox.this.defValueSql = defValueSql;
            return this;
        }

    }

}
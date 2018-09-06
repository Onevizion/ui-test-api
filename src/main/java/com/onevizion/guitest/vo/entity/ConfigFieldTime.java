package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class ConfigFieldTime {

    private String defValueSql;
    private String showSeconds;

    private ConfigFieldTime() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldTime().new Builder();
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public String getShowSeconds() {
        return showSeconds;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldTime build() {
            if (ConfigFieldTime.this.defValueSql == null ||
                    ConfigFieldTime.this.showSeconds == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldTime.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldTime.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setShowSeconds(String showSeconds) {
            ConfigFieldTime.this.showSeconds = showSeconds;
            return this;
        }

    }

}
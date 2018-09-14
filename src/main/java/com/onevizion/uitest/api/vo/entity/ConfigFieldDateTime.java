package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldDateTime {

    private String defValueSql;
    private String showSeconds;

    private ConfigFieldDateTime() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldDateTime().new Builder();
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

        public ConfigFieldDateTime build() {
            if (ConfigFieldDateTime.this.defValueSql == null ||
                    ConfigFieldDateTime.this.showSeconds == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldDateTime.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldDateTime.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setShowSeconds(String showSeconds) {
            ConfigFieldDateTime.this.showSeconds = showSeconds;
            return this;
        }

    }

}
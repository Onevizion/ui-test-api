package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoTime {

    private String defValueSql;
    private String showSeconds;

    private ConfigFieldVoTime() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoTime().new Builder();
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

        public ConfigFieldVoTime build() {
            if (ConfigFieldVoTime.this.defValueSql == null ||
                    ConfigFieldVoTime.this.showSeconds == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoTime.this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoTime.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setShowSeconds(String showSeconds) {
            ConfigFieldVoTime.this.showSeconds = showSeconds;
            return this;
        }

    }

}
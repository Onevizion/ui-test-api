package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.vo.ConfigFieldEfileMetadataType;

public class ConfigFieldVoEfileMetadata {

    private ConfigFieldEfileMetadataType type;
    private String configField;

    private ConfigFieldVoEfileMetadata() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoEfileMetadata().new Builder();
    }

    public ConfigFieldEfileMetadataType getType() {
        return type;
    }

    public String getConfigField() {
        return configField;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoEfileMetadata build() {
            if (ConfigFieldVoEfileMetadata.this.type == null ||
                    ConfigFieldVoEfileMetadata.this.configField == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoEfileMetadata.this;
        }

        public Builder setType(ConfigFieldEfileMetadataType type) {
            ConfigFieldVoEfileMetadata.this.type = type;
            return this;
        }

        public Builder setConfigField(String configField) {
            ConfigFieldVoEfileMetadata.this.configField = configField;
            return this;
        }

    }

}
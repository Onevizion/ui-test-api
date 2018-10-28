package com.onevizion.uitest.api.vo.entity;

import java.util.List;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoEfile {

    private String resizeMode;
    private String resizeWidth;
    private String resizeHeight;
    private String rotate;
    private String logBlobChanges;
    private String uploadToAws;
    private String autocaptionTemplate;
    private List<ConfigFieldVoEfileMetadata> metadatas;

    private ConfigFieldVoEfile() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoEfile().new Builder();
    }

    public String getResizeMode() {
        return resizeMode;
    }

    public String getResizeWidth() {
        return resizeWidth;
    }

    public String getResizeHeight() {
        return resizeHeight;
    }

    public String getRotate() {
        return rotate;
    }

    public String getLogBlobChanges() {
        return logBlobChanges;
    }

    public String getUploadToAws() {
        return uploadToAws;
    }

    public String getAutocaptionTemplate() {
        return autocaptionTemplate;
    }

    public List<ConfigFieldVoEfileMetadata> getMetadatas() {
        return metadatas;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoEfile build() {
            if (ConfigFieldVoEfile.this.resizeMode == null ||
                    ConfigFieldVoEfile.this.resizeWidth == null ||
                    ConfigFieldVoEfile.this.resizeHeight == null ||
                    ConfigFieldVoEfile.this.rotate == null ||
                    ConfigFieldVoEfile.this.logBlobChanges == null ||
                    ConfigFieldVoEfile.this.uploadToAws == null ||
                    ConfigFieldVoEfile.this.autocaptionTemplate == null ||
                    ConfigFieldVoEfile.this.metadatas == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoEfile.this;
        }

        public Builder setResizeMode(String resizeMode) {
            ConfigFieldVoEfile.this.resizeMode = resizeMode;
            return this;
        }

        public Builder setResizeWidth(String resizeWidth) {
            ConfigFieldVoEfile.this.resizeWidth = resizeWidth;
            return this;
        }

        public Builder setResizeHeight(String resizeHeight) {
            ConfigFieldVoEfile.this.resizeHeight = resizeHeight;
            return this;
        }

        public Builder setRotate(String rotate) {
            ConfigFieldVoEfile.this.rotate = rotate;
            return this;
        }

        public Builder setLogBlobChanges(String logBlobChanges) {
            ConfigFieldVoEfile.this.logBlobChanges = logBlobChanges;
            return this;
        }

        public Builder setUploadToAws(String uploadToAws) {
            ConfigFieldVoEfile.this.uploadToAws = uploadToAws;
            return this;
        }

        public Builder setAutocaptionTemplate(String autocaptionTemplate) {
            ConfigFieldVoEfile.this.autocaptionTemplate = autocaptionTemplate;
            return this;
        }

        public Builder setMetadatas(List<ConfigFieldVoEfileMetadata> metadatas) {
            ConfigFieldVoEfile.this.metadatas = metadatas;
            return this;
        }

    }

}
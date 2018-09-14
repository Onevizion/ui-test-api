package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoEfile {

    private String extractMetadata;
    private String imageLatitude;
    private String imageLongitude;
    private String imageTimeSnapshot;
    private String resizeMode;
    private String resizeWidth;
    private String resizeHeight;
    private String rotate;
    private String logBlobChanges;
    private String uploadToAws;
    private String autocaptionTemplate;

    private ConfigFieldVoEfile() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoEfile().new Builder();
    }

    public String getExtractMetadata() {
        return extractMetadata;
    }

    public String getImageLatitude() {
        return imageLatitude;
    }

    public String getImageLongitude() {
        return imageLongitude;
    }

    public String getImageTimeSnapshot() {
        return imageTimeSnapshot;
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

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoEfile build() {
            if (ConfigFieldVoEfile.this.extractMetadata == null ||
                    ConfigFieldVoEfile.this.imageLatitude == null ||
                    ConfigFieldVoEfile.this.imageLongitude == null ||
                    ConfigFieldVoEfile.this.imageTimeSnapshot == null ||
                    ConfigFieldVoEfile.this.resizeMode == null ||
                    ConfigFieldVoEfile.this.resizeWidth == null ||
                    ConfigFieldVoEfile.this.resizeHeight == null ||
                    ConfigFieldVoEfile.this.rotate == null ||
                    ConfigFieldVoEfile.this.logBlobChanges == null ||
                    ConfigFieldVoEfile.this.uploadToAws == null ||
                    ConfigFieldVoEfile.this.autocaptionTemplate == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoEfile.this;
        }

        public Builder setExtractMetadata(String extractMetadata) {
            ConfigFieldVoEfile.this.extractMetadata = extractMetadata;
            return this;
        }

        public Builder setImageLatitude(String imageLatitude) {
            ConfigFieldVoEfile.this.imageLatitude = imageLatitude;
            return this;
        }

        public Builder setImageLongitude(String imageLongitude) {
            ConfigFieldVoEfile.this.imageLongitude = imageLongitude;
            return this;
        }

        public Builder setImageTimeSnapshot(String imageTimeSnapshot) {
            ConfigFieldVoEfile.this.imageTimeSnapshot = imageTimeSnapshot;
            return this;
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

    }

}
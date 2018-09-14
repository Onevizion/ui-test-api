package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldEfile {

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

    private ConfigFieldEfile() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldEfile().new Builder();
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

        public ConfigFieldEfile build() {
            if (ConfigFieldEfile.this.extractMetadata == null ||
                    ConfigFieldEfile.this.imageLatitude == null ||
                    ConfigFieldEfile.this.imageLongitude == null ||
                    ConfigFieldEfile.this.imageTimeSnapshot == null ||
                    ConfigFieldEfile.this.resizeMode == null ||
                    ConfigFieldEfile.this.resizeWidth == null ||
                    ConfigFieldEfile.this.resizeHeight == null ||
                    ConfigFieldEfile.this.rotate == null ||
                    ConfigFieldEfile.this.logBlobChanges == null ||
                    ConfigFieldEfile.this.uploadToAws == null ||
                    ConfigFieldEfile.this.autocaptionTemplate == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldEfile.this;
        }

        public Builder setExtractMetadata(String extractMetadata) {
            ConfigFieldEfile.this.extractMetadata = extractMetadata;
            return this;
        }

        public Builder setImageLatitude(String imageLatitude) {
            ConfigFieldEfile.this.imageLatitude = imageLatitude;
            return this;
        }

        public Builder setImageLongitude(String imageLongitude) {
            ConfigFieldEfile.this.imageLongitude = imageLongitude;
            return this;
        }

        public Builder setImageTimeSnapshot(String imageTimeSnapshot) {
            ConfigFieldEfile.this.imageTimeSnapshot = imageTimeSnapshot;
            return this;
        }

        public Builder setResizeMode(String resizeMode) {
            ConfigFieldEfile.this.resizeMode = resizeMode;
            return this;
        }

        public Builder setResizeWidth(String resizeWidth) {
            ConfigFieldEfile.this.resizeWidth = resizeWidth;
            return this;
        }

        public Builder setResizeHeight(String resizeHeight) {
            ConfigFieldEfile.this.resizeHeight = resizeHeight;
            return this;
        }

        public Builder setRotate(String rotate) {
            ConfigFieldEfile.this.rotate = rotate;
            return this;
        }

        public Builder setLogBlobChanges(String logBlobChanges) {
            ConfigFieldEfile.this.logBlobChanges = logBlobChanges;
            return this;
        }

        public Builder setUploadToAws(String uploadToAws) {
            ConfigFieldEfile.this.uploadToAws = uploadToAws;
            return this;
        }

        public Builder setAutocaptionTemplate(String autocaptionTemplate) {
            ConfigFieldEfile.this.autocaptionTemplate = autocaptionTemplate;
            return this;
        }

    }

}
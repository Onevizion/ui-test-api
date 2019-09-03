package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class Coord {

    private String name;
    private String latFieldName;
    private String latFieldLabel;
    private String longFieldName;
    private String longFieldLabel;
    private String trackorTypeName;
    private String trackorTypeLabel;

    private Coord() {
        
    }

    public static Builder newBuilder() {
        return new Coord().new Builder();
    }

    public String getName() {
        return name;
    }

    public String getLatFieldName() {
        return latFieldName;
    }

    public String getLatFieldLabel() {
        return latFieldLabel;
    }

    public String getLongFieldName() {
        return longFieldName;
    }

    public String getLongFieldLabel() {
        return longFieldLabel;
    }

    public String getTrackorTypeName() {
        return trackorTypeName;
    }

    public String getTrackorTypeLabel() {
        return trackorTypeLabel;
    }

    public class Builder {

        private Builder() {
            
        }

        public Coord build() {
            if (Coord.this.name == null ||
                    Coord.this.latFieldName == null ||
                    Coord.this.latFieldLabel == null ||
                    Coord.this.longFieldName == null ||
                    Coord.this.longFieldLabel == null ||
                    Coord.this.trackorTypeName == null ||
                    Coord.this.trackorTypeLabel == null) {
                throw new SeleniumUnexpectedException("");
            }
            return Coord.this;
        }

        public Builder setName(String name) {
            Coord.this.name = name;
            return this;
        }

        public Builder setLatFieldName(String latFieldName) {
            Coord.this.latFieldName = latFieldName;
            return this;
        }

        public Builder setLatFieldLabel(String latFieldLabel) {
            Coord.this.latFieldLabel = latFieldLabel;
            return this;
        }

        public Builder setLongFieldName(String longFieldName) {
            Coord.this.longFieldName = longFieldName;
            return this;
        }

        public Builder setLongFieldLabel(String longFieldLabel) {
            Coord.this.longFieldLabel = longFieldLabel;
            return this;
        }

        public Builder setTrackorTypeName(String trackorTypeName) {
            Coord.this.trackorTypeName = trackorTypeName;
            return this;
        }

        public Builder setTrackorTypeLabel(String trackorTypeLabel) {
            Coord.this.trackorTypeLabel = trackorTypeLabel;
            return this;
        }

    }

}
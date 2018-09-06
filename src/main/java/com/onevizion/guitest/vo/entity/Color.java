package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class Color {

    private String name;
    private String value;
    private String description;

    private Color() {
        
    }

    public static Builder newBuilder() {
        return new Color().new Builder();
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public class Builder {

        private Builder() {
            
        }

        public Color build() {
            if (Color.this.name == null ||
                    Color.this.value == null ||
                    Color.this.description == null) {
                throw new SeleniumUnexpectedException("");
            }
            return Color.this;
        }

        public Builder setName(String name) {
            Color.this.name = name;
            return this;
        }

        public Builder setValue(String value) {
            Color.this.value = value;
            return this;
        }

        public Builder setDescription(String description) {
            Color.this.description = description;
            return this;
        }

    }

}
package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class Validation {

    private String name;
    private String pattern;
    private String errorMessage;

    private Validation() {
        
    }

    public static Builder newBuilder() {
        return new Validation().new Builder();
    }

    public String getName() {
        return name;
    }

    public String getPattern() {
        return pattern;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public class Builder {

        private Builder() {
            
        }

        public Validation build() {
            if (Validation.this.name == null ||
                    Validation.this.pattern == null ||
                    Validation.this.errorMessage == null) {
                throw new SeleniumUnexpectedException("");
            }
            return Validation.this;
        }

        public Builder setName(String name) {
            Validation.this.name = name;
            return this;
        }

        public Builder setPattern(String pattern) {
            Validation.this.pattern = pattern;
            return this;
        }

        public Builder setErrorMessage(String errorMessage) {
            Validation.this.errorMessage = errorMessage;
            return this;
        }

    }

}
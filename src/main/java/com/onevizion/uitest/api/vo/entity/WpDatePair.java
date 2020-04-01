package com.onevizion.uitest.api.vo.entity;

import java.util.Map;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class WpDatePair {

    private String name;
    private String label;
    private String shortName;
    private String shortLabel;
    private String use;

    private Map<String, String> roles;

    private WpDatePair() {
        
    }

    public static Builder newBuilder() {
        return new WpDatePair().new Builder();
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getShortName() {
        return shortName;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public String getUse() {
        return use;
    }

    public Map<String, String> getRoles() {
        return roles;
    }

    public class Builder {

        private Builder() {
            
        }

        public WpDatePair build() {
            if (WpDatePair.this.name == null ||
                    WpDatePair.this.label == null ||
                    WpDatePair.this.shortName == null ||
                    WpDatePair.this.shortLabel == null ||
                    WpDatePair.this.use == null ||
                    WpDatePair.this.roles == null) {
                throw new SeleniumUnexpectedException("");
            }
            return WpDatePair.this;
        }

        public Builder setName(String name) {
            WpDatePair.this.name = name;
            return this;
        }

        public Builder setLabel(String label) {
            WpDatePair.this.label = label;
            return this;
        }

        public Builder setShortName(String shortName) {
            WpDatePair.this.shortName = shortName;
            return this;
        }

        public Builder setShortLabel(String shortLabel) {
            WpDatePair.this.shortLabel = shortLabel;
            return this;
        }

        public Builder setUse(String use) {
            WpDatePair.this.use = use;
            return this;
        }

        public Builder setRoles(Map<String, String> roles) {
            WpDatePair.this.roles = roles;
            return this;
        }

    }

}
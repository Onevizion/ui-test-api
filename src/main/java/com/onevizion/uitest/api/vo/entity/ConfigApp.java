package com.onevizion.uitest.api.vo.entity;

import java.util.List;
import java.util.Map;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigApp {

    private String name;
    private String label;
    private String trackorType;
    private String relatedTrackorType;
    private String icon;
    private String compPackage;
    private String isMaster;
    private List<String> tabs;
    private Map<String, String> roles;
    private List<String> classes;
    private List<String> pages;

    private ConfigApp() {
        
    }

    public static Builder newBuilder() {
        return new ConfigApp().new Builder();
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getTrackorType() {
        return trackorType;
    }

    public String getRelatedTrackorType() {
        return relatedTrackorType;
    }

    public String getIcon() {
        return icon;
    }

    public String getCompPackage() {
        return compPackage;
    }

    public String getIsMaster() {
        return isMaster;
    }

    public List<String> getTabs() {
        return tabs;
    }

    public Map<String, String> getRoles() {
        return roles;
    }

    public List<String> getClasses() {
        return classes;
    }

    public List<String> getPages() {
        return pages;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigApp build() {
            if (ConfigApp.this.name == null ||
                    ConfigApp.this.label == null ||
                    ConfigApp.this.trackorType == null ||
                    ConfigApp.this.relatedTrackorType == null ||
                    ConfigApp.this.icon == null ||
                    ConfigApp.this.compPackage == null ||
                    ConfigApp.this.isMaster == null ||
                    ConfigApp.this.tabs == null ||
                    ConfigApp.this.roles == null ||
                    ConfigApp.this.classes == null ||
                    ConfigApp.this.pages == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigApp.this;
        }

        public Builder setName(String name) {
            ConfigApp.this.name = name;
            return this;
        }

        public Builder setLabel(String label) {
            ConfigApp.this.label = label;
            return this;
        }

        public Builder setTrackorType(String trackorType) {
            ConfigApp.this.trackorType = trackorType;
            return this;
        }

        public Builder setRelatedTrackorType(String relatedTrackorType) {
            ConfigApp.this.relatedTrackorType = relatedTrackorType;
            return this;
        }

        public Builder setIcon(String icon) {
            ConfigApp.this.icon = icon;
            return this;
        }

        public Builder setCompPackage(String compPackage) {
            ConfigApp.this.compPackage = compPackage;
            return this;
        }

        public Builder setIsMaster(String isMaster) {
            ConfigApp.this.isMaster = isMaster;
            return this;
        }

        public Builder setTabs(List<String> tabs) {
            ConfigApp.this.tabs = tabs;
            return this;
        }

        public Builder setRoles(Map<String, String> roles) {
            ConfigApp.this.roles = roles;
            return this;
        }

        public Builder setClasses(List<String> classes) {
            ConfigApp.this.classes = classes;
            return this;
        }

        public Builder setPages(List<String> pages) {
            ConfigApp.this.pages = pages;
            return this;
        }

    }

}
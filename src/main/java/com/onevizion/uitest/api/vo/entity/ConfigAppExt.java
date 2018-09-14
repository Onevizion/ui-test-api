package com.onevizion.uitest.api.vo.entity;

import java.util.List;
import java.util.Map;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigAppExt {

    private String name;
    private String label;
    private String url;
    private String trackorType;
    private String icon;
    private String formWidth;
    private String formHeight;
    private String formPosX;
    private String formPosY;
    private String showInModalWindow;
    private String centerForm;
    private String runHidden;
    private String compPackage;

    private List<ConfigAppExtParam> paramsPath;
    private List<ConfigAppExtParam> paramsGet;

    private Map<String, String> roles;
    private List<String> classes;
    private List<String> pages;

    private ConfigAppExt() {
        
    }

    public static Builder newBuilder() {
        return new ConfigAppExt().new Builder();
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    public String getTrackorType() {
        return trackorType;
    }

    public String getIcon() {
        return icon;
    }

    public String getFormWidth() {
        return formWidth;
    }

    public String getFormHeight() {
        return formHeight;
    }

    public String getFormPosX() {
        return formPosX;
    }

    public String getFormPosY() {
        return formPosY;
    }

    public String getShowInModalWindow() {
        return showInModalWindow;
    }

    public String getCenterForm() {
        return centerForm;
    }

    public String getRunHidden() {
        return runHidden;
    }

    public String getCompPackage() {
        return compPackage;
    }

    public List<ConfigAppExtParam> getParamsPath() {
        return paramsPath;
    }

    public List<ConfigAppExtParam> getParamsGet() {
        return paramsGet;
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

        public ConfigAppExt build() {
            if (ConfigAppExt.this.name == null ||
                    ConfigAppExt.this.label == null ||
                    ConfigAppExt.this.url == null ||
                    ConfigAppExt.this.trackorType == null ||
                    ConfigAppExt.this.icon == null ||
                    ConfigAppExt.this.formWidth == null ||
                    ConfigAppExt.this.formHeight == null ||
                    ConfigAppExt.this.formPosX == null ||
                    ConfigAppExt.this.formPosY == null ||
                    ConfigAppExt.this.showInModalWindow == null ||
                    ConfigAppExt.this.centerForm == null ||
                    ConfigAppExt.this.runHidden == null ||
                    ConfigAppExt.this.compPackage == null ||
                    ConfigAppExt.this.paramsPath == null ||
                    ConfigAppExt.this.paramsGet == null ||
                    ConfigAppExt.this.roles == null ||
                    ConfigAppExt.this.classes == null ||
                    ConfigAppExt.this.pages == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigAppExt.this;
        }

        public Builder setName(String name) {
            ConfigAppExt.this.name = name;
            return this;
        }

        public Builder setLabel(String label) {
            ConfigAppExt.this.label = label;
            return this;
        }

        public Builder setUrl(String url) {
            ConfigAppExt.this.url = url;
            return this;
        }

        public Builder setTrackorType(String trackorType) {
            ConfigAppExt.this.trackorType = trackorType;
            return this;
        }

        public Builder setIcon(String icon) {
            ConfigAppExt.this.icon = icon;
            return this;
        }

        public Builder setFormWidth(String formWidth) {
            ConfigAppExt.this.formWidth = formWidth;
            return this;
        }

        public Builder setFormHeight(String formHeight) {
            ConfigAppExt.this.formHeight = formHeight;
            return this;
        }

        public Builder setFormPosX(String formPosX) {
            ConfigAppExt.this.formPosX = formPosX;
            return this;
        }

        public Builder setFormPosY(String formPosY) {
            ConfigAppExt.this.formPosY = formPosY;
            return this;
        }

        public Builder setShowInModalWindow(String showInModalWindow) {
            ConfigAppExt.this.showInModalWindow = showInModalWindow;
            return this;
        }

        public Builder setCenterForm(String centerForm) {
            ConfigAppExt.this.centerForm = centerForm;
            return this;
        }

        public Builder setRunHidden(String runHidden) {
            ConfigAppExt.this.runHidden = runHidden;
            return this;
        }

        public Builder setCompPackage(String compPackage) {
            ConfigAppExt.this.compPackage = compPackage;
            return this;
        }

        public Builder setParamsPath(List<ConfigAppExtParam> paramsPath) {
            ConfigAppExt.this.paramsPath = paramsPath;
            return this;
        }

        public Builder setParamsGet(List<ConfigAppExtParam> paramsGet) {
            ConfigAppExt.this.paramsGet = paramsGet;
            return this;
        }

        public Builder setRoles(Map<String, String> roles) {
            ConfigAppExt.this.roles = roles;
            return this;
        }

        public Builder setClasses(List<String> classes) {
            ConfigAppExt.this.classes = classes;
            return this;
        }

        public Builder setPages(List<String> pages) {
            ConfigAppExt.this.pages = pages;
            return this;
        }

    }

}
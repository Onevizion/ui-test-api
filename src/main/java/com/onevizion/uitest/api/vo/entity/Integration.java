package com.onevizion.uitest.api.vo.entity;

import java.util.List;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class Integration {

    private String url;
    private String name;
    private String version;
    private String command;
    private String settingsFile;
    private String logLevel;
    private String readFromStdout;
    private String description;
    private String schedule;
    private String enabled;
    private List<String> packages;

    private Integration() {
        
    }

    public static Builder newBuilder() {
        return new Integration().new Builder();
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getCommand() {
        return command;
    }

    public String getSettingsFile() {
        return settingsFile;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public String getReadFromStdout() {
        return readFromStdout;
    }

    public String getDescription() {
        return description;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getEnabled() {
        return enabled;
    }

    public List<String> getPackages() {
        return packages;
    }

    public class Builder {

        private Builder() {
            
        }

        public Integration build() {
            if (Integration.this.url == null ||
                    Integration.this.name == null ||
                    Integration.this.version == null ||
                    Integration.this.command == null ||
                    Integration.this.settingsFile == null ||
                    Integration.this.logLevel == null ||
                    Integration.this.readFromStdout == null ||
                    Integration.this.description == null ||
                    Integration.this.schedule == null ||
                    Integration.this.enabled == null ||
                    Integration.this.packages == null) {
                throw new SeleniumUnexpectedException("");
            }
            return Integration.this;
        }

        public Builder setUrl(String url) {
            Integration.this.url = url;
            return this;
        }

        public Builder setName(String name) {
            Integration.this.name = name;
            return this;
        }

        public Builder setVersion(String version) {
            Integration.this.version = version;
            return this;
        }

        public Builder setCommand(String command) {
            Integration.this.command = command;
            return this;
        }

        public Builder setSettingsFile(String settingsFile) {
            Integration.this.settingsFile = settingsFile;
            return this;
        }

        public Builder setLogLevel(String logLevel) {
            Integration.this.logLevel = logLevel;
            return this;
        }

        public Builder setReadFromStdout(String readFromStdout) {
            Integration.this.readFromStdout = readFromStdout.toUpperCase();
            return this;
        }

        public Builder setDescription(String description) {
            Integration.this.description = description;
            return this;
        }

        public Builder setSchedule(String schedule) {
            Integration.this.schedule = schedule;
            return this;
        }

        public Builder setEnabled(String enabled) {
            Integration.this.enabled = enabled.toUpperCase();
            return this;
        }

        public Builder setPackages(List<String> packages) {
            Integration.this.packages = packages;
            return this;
        }

    }

}
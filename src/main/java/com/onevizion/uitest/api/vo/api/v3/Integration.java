package com.onevizion.uitest.api.vo.api.v3;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Integration implements Comparable<Integration> {

    private String integrationId;
    private String integrationName;
    private String enabled;
    private String lastRun;
    private String readFromStdout;
    private String version;
    private String schedule;
    private String gitRepoUrl;
    private String description;
    private String logLevel;
    private String command;

    @JsonProperty("integration_id")
    public String getIntegrationId() {
        return integrationId;
    }

    public void setIntegrationId(String integrationId) {
        this.integrationId = integrationId;
    }

    @JsonProperty("integration_name")
    public String getIntegrationName() {
        return integrationName;
    }

    public void setIntegrationName(String integrationName) {
        this.integrationName = integrationName;
    }

    @JsonProperty("enabled")
    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    @JsonProperty("last_run")
    public String getLastRun() {
        return lastRun;
    }

    public void setLastRun(String lastRun) {
        this.lastRun = lastRun;
    }

    @JsonProperty("read_from_stdout")
    public String getReadFromStdout() {
        return readFromStdout;
    }

    public void setReadFromStdout(String readFromStdout) {
        this.readFromStdout = readFromStdout;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty("schedule")
    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    @JsonProperty("git_repo_url")
    public String getGitRepoUrl() {
        return gitRepoUrl;
    }

    public void setGitRepoUrl(String gitRepoUrl) {
        this.gitRepoUrl = gitRepoUrl;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("log_level")
    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    @JsonProperty("command")
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public int compareTo(Integration o) {
        int val1 = this.integrationId.compareTo(o.integrationId);
        if (val1 != 0) {
            return val1;
        }
        int val2 = this.integrationName.compareTo(o.integrationName);
        if (val2 != 0) {
            return val2;
        }
        int val3 = this.enabled.compareTo(o.enabled);
        if (val3 != 0) {
            return val3;
        }
        int val4 = this.lastRun.compareTo(o.lastRun);
        if (val4 != 0) {
            return val4;
        }
        int val5 = this.readFromStdout.compareTo(o.readFromStdout);
        if (val5 != 0) {
            return val5;
        }
        int val6 = this.version.compareTo(o.version);
        if (val6 != 0) {
            return val6;
        }
        int val7 = this.schedule.compareTo(o.schedule);
        if (val7 != 0) {
            return val7;
        }
        int val8 = this.gitRepoUrl.compareTo(o.gitRepoUrl);
        if (val8 != 0) {
            return val8;
        }
        int val9 = this.description.compareTo(o.description);
        if (val9 != 0) {
            return val9;
        }
        int val10 = this.logLevel.compareTo(o.logLevel);
        if (val10 != 0) {
            return val10;
        }
        return this.command.compareTo(o.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, description, enabled, gitRepoUrl, integrationId, integrationName, lastRun,
                logLevel, readFromStdout, schedule, version);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Integration other = (Integration) obj;
        return Objects.equals(command, other.command) && Objects.equals(description, other.description)
                && Objects.equals(enabled, other.enabled) && Objects.equals(gitRepoUrl, other.gitRepoUrl)
                && Objects.equals(integrationId, other.integrationId)
                && Objects.equals(integrationName, other.integrationName) && Objects.equals(lastRun, other.lastRun)
                && Objects.equals(logLevel, other.logLevel) && Objects.equals(readFromStdout, other.readFromStdout)
                && Objects.equals(schedule, other.schedule) && Objects.equals(version, other.version);
    }

    @Override
    public String toString() {
        return "Integration [integrationId=" + integrationId + ", integrationName=" + integrationName + ", enabled="
                + enabled + ", lastRun=" + lastRun + ", readFromStdout=" + readFromStdout + ", version=" + version
                + ", schedule=" + schedule + ", gitRepoUrl=" + gitRepoUrl + ", description=" + description
                + ", logLevel=" + logLevel + ", command=" + command + "]";
    }

}
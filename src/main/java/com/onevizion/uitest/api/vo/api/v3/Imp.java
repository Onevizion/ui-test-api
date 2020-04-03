package com.onevizion.uitest.api.vo.api.v3;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Imp implements Comparable<Imp> {

    private String id;
    private String name;
    private String externalProcedure;
    private List<String> actions;
    private String defaultAction;
    private String description;
    private String validationMode;
    private String lineDelimiter;
    private String fieldDelimiter;
    private String dateFormat;
    private String stringQuote;
    private String daysToKeepParsedData;
    private String maxRuntime;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("external_procedure")
    public String getExternalProcedure() {
        return externalProcedure;
    }

    public void setExternalProcedure(String externalProcedure) {
        this.externalProcedure = externalProcedure;
    }

    @JsonProperty("actions")
    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    @JsonProperty("default_action")
    public String getDefaultAction() {
        return defaultAction;
    }

    public void setDefaultAction(String defaultAction) {
        this.defaultAction = defaultAction;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("validation_mode")
    public String getValidationMode() {
        return validationMode;
    }

    public void setValidationMode(String validationMode) {
        this.validationMode = validationMode;
    }

    @JsonProperty("line_delimiter")
    public String getLineDelimiter() {
        return lineDelimiter;
    }

    public void setLineDelimiter(String lineDelimiter) {
        this.lineDelimiter = lineDelimiter;
    }

    @JsonProperty("field_delimiter")
    public String getFieldDelimiter() {
        return fieldDelimiter;
    }

    public void setFieldDelimiter(String fieldDelimiter) {
        this.fieldDelimiter = fieldDelimiter;
    }

    @JsonProperty("date_format")
    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @JsonProperty("string_quote")
    public String getStringQuote() {
        return stringQuote;
    }

    public void setStringQuote(String stringQuote) {
        this.stringQuote = stringQuote;
    }

    @JsonProperty("days_to_keep_parsed_data")
    public String getDaysToKeepParsedData() {
        return daysToKeepParsedData;
    }

    public void setDaysToKeepParsedData(String daysToKeepParsedData) {
        this.daysToKeepParsedData = daysToKeepParsedData;
    }

    @JsonProperty("max_runtime")
    public String getMaxRuntime() {
        return maxRuntime;
    }

    public void setMaxRuntime(String maxRuntime) {
        this.maxRuntime = maxRuntime;
    }

    @Override
    public int compareTo(Imp o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actions, dateFormat, daysToKeepParsedData, defaultAction, description, externalProcedure,
                fieldDelimiter, id, lineDelimiter, maxRuntime, name, stringQuote, validationMode);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Imp)) {
            return false;
        }
        Imp other = (Imp) obj;
        return Objects.equals(actions, other.actions) && Objects.equals(dateFormat, other.dateFormat)
                && Objects.equals(daysToKeepParsedData, other.daysToKeepParsedData)
                && Objects.equals(defaultAction, other.defaultAction) && Objects.equals(description, other.description)
                && Objects.equals(externalProcedure, other.externalProcedure)
                && Objects.equals(fieldDelimiter, other.fieldDelimiter) && Objects.equals(id, other.id)
                && Objects.equals(lineDelimiter, other.lineDelimiter) && Objects.equals(maxRuntime, other.maxRuntime)
                && Objects.equals(name, other.name) && Objects.equals(stringQuote, other.stringQuote)
                && Objects.equals(validationMode, other.validationMode);
    }

    @Override
    public String toString() {
        return "Imp [id=" + id + ", name=" + name + ", externalProcedure=" + externalProcedure + ", actions=" + actions
                + ", defaultAction=" + defaultAction + ", description=" + description + ", validationMode="
                + validationMode + ", lineDelimiter=" + lineDelimiter + ", fieldDelimiter=" + fieldDelimiter
                + ", dateFormat=" + dateFormat + ", stringQuote=" + stringQuote + ", daysToKeepParsedData="
                + daysToKeepParsedData + ", maxRuntime=" + maxRuntime + "]";
    }

}
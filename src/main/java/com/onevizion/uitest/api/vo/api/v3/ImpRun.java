package com.onevizion.uitest.api.vo.api.v3;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImpRun implements Comparable<ImpRun> {

    private String processId;
    private String importId;
    private String submitted;
    private String scheduled;
    private String finished;
    private String rowsProcessed;
    private String gridCount;
    private String pkCount;
    private String importName;
    private String owner;
    private String csvParsingRuntime;
    private String pkSearchingRuntime;
    private String dataImportingRuntime;
    private String comments;
    private String errorMessage;
    private String status;
    private String action;
    private String maxRuntime;
    private List<ImpRunError> warnings;

    @JsonProperty("process_id")
    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    @JsonProperty("import_id")
    public String getImportId() {
        return importId;
    }

    public void setImportId(String importId) {
        this.importId = importId;
    }

    @JsonProperty("submitted")
    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    @JsonProperty("scheduled")
    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    @JsonProperty("finished")
    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    @JsonProperty("rows_processed")
    public String getRowsProcessed() {
        return rowsProcessed;
    }

    public void setRowsProcessed(String rowsProcessed) {
        this.rowsProcessed = rowsProcessed;
    }

    @JsonProperty("grid_count")
    public String getGridCount() {
        return gridCount;
    }

    public void setGridCount(String gridCount) {
        this.gridCount = gridCount;
    }

    @JsonProperty("pk_count")
    public String getPkCount() {
        return pkCount;
    }

    public void setPkCount(String pkCount) {
        this.pkCount = pkCount;
    }

    @JsonProperty("import_name")
    public String getImportName() {
        return importName;
    }

    public void setImportName(String importName) {
        this.importName = importName;
    }

    @JsonProperty("owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @JsonProperty("csv_parsing_runtime")
    public String getCsvParsingRuntime() {
        return csvParsingRuntime;
    }

    public void setCsvParsingRuntime(String csvParsingRuntime) {
        this.csvParsingRuntime = csvParsingRuntime;
    }

    @JsonProperty("pk_searching_runtime")
    public String getPkSearchingRuntime() {
        return pkSearchingRuntime;
    }

    public void setPkSearchingRuntime(String pkSearchingRuntime) {
        this.pkSearchingRuntime = pkSearchingRuntime;
    }

    @JsonProperty("data_importing_runtime")
    public String getDataImportingRuntime() {
        return dataImportingRuntime;
    }

    public void setDataImportingRuntime(String dataImportingRuntime) {
        this.dataImportingRuntime = dataImportingRuntime;
    }

    @JsonProperty("comments")
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @JsonProperty("error_message")
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("action")
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @JsonProperty("max_runtime")
    public String getMaxRuntime() {
        return maxRuntime;
    }

    public void setMaxRuntime(String maxRuntime) {
        this.maxRuntime = maxRuntime;
    }

    @JsonProperty("warnings")
    public List<ImpRunError> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<ImpRunError> warnings) {
        this.warnings = warnings;
    }

    @Override
    public int compareTo(ImpRun o) {
        return this.processId.compareTo(o.processId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, comments, csvParsingRuntime, dataImportingRuntime, errorMessage, finished,
                gridCount, importId, importName, maxRuntime, owner, pkCount, pkSearchingRuntime, processId,
                rowsProcessed, scheduled, status, submitted, warnings);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImpRun)) {
            return false;
        }
        ImpRun other = (ImpRun) obj;
        return Objects.equals(action, other.action) && Objects.equals(comments, other.comments)
                && Objects.equals(csvParsingRuntime, other.csvParsingRuntime)
                && Objects.equals(dataImportingRuntime, other.dataImportingRuntime)
                && Objects.equals(errorMessage, other.errorMessage) && Objects.equals(finished, other.finished)
                && Objects.equals(gridCount, other.gridCount) && Objects.equals(importId, other.importId)
                && Objects.equals(importName, other.importName) && Objects.equals(maxRuntime, other.maxRuntime)
                && Objects.equals(owner, other.owner) && Objects.equals(pkCount, other.pkCount)
                && Objects.equals(pkSearchingRuntime, other.pkSearchingRuntime)
                && Objects.equals(processId, other.processId) && Objects.equals(rowsProcessed, other.rowsProcessed)
                && Objects.equals(scheduled, other.scheduled) && Objects.equals(status, other.status)
                && Objects.equals(submitted, other.submitted) && Objects.equals(warnings, other.warnings);
    }

    @Override
    public String toString() {
        return "ImpRun [processId=" + processId + ", importId=" + importId + ", submitted=" + submitted + ", scheduled="
                + scheduled + ", finished=" + finished + ", rowsProcessed=" + rowsProcessed + ", gridCount=" + gridCount
                + ", pkCount=" + pkCount + ", importName=" + importName + ", owner=" + owner + ", csvParsingRuntime="
                + csvParsingRuntime + ", pkSearchingRuntime=" + pkSearchingRuntime + ", dataImportingRuntime="
                + dataImportingRuntime + ", comments=" + comments + ", errorMessage=" + errorMessage + ", status="
                + status + ", action=" + action + ", maxRuntime=" + maxRuntime + ", warnings=" + warnings + "]";
    }

}
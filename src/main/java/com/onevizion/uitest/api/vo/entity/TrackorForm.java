package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class TrackorForm {

    private String name;
    private String reportName;
    private String importName;
    private String userName;
    private String interval;
    private String description;
    private String importEmailSubject;
    private String importEmailBody;
    private String createImport;

    private TrackorForm() {
        
    }

    public static Builder newBuilder() {
        return new TrackorForm().new Builder();
    }

    public String getName() {
        return name;
    }

    public String getReportName() {
        return reportName;
    }

    public String getImportName() {
        return importName;
    }

    public String getUserName() {
        return userName;
    }

    public String getInterval() {
        return interval;
    }

    public String getDescription() {
        return description;
    }

    public String getImportEmailSubject() {
        return importEmailSubject;
    }

    public String getImportEmailBody() {
        return importEmailBody;
    }

    public String getCreateImport() {
        return createImport;
    }

    public class Builder {

        private Builder() {
            
        }

        public TrackorForm build() {
            if (TrackorForm.this.name == null ||
                    TrackorForm.this.reportName == null ||
                    TrackorForm.this.importName == null ||
                    TrackorForm.this.userName == null ||
                    TrackorForm.this.interval == null ||
                    TrackorForm.this.description == null ||
                    TrackorForm.this.importEmailSubject == null ||
                    TrackorForm.this.importEmailBody == null ||
                    TrackorForm.this.createImport == null) {
                throw new SeleniumUnexpectedException("");
            }
            return TrackorForm.this;
        }

        public Builder setName(String name) {
            TrackorForm.this.name = name;
            return this;
        }

        public Builder setReportName(String reportName) {
            TrackorForm.this.reportName = reportName;
            return this;
        }

        public Builder setImportName(String importName) {
            TrackorForm.this.importName = importName;
            return this;
        }

        public Builder setUserName(String userName) {
            TrackorForm.this.userName = userName;
            return this;
        }

        public Builder setInterval(String interval) {
            TrackorForm.this.interval = interval;
            return this;
        }

        public Builder setDescription(String description) {
            TrackorForm.this.description = description;
            return this;
        }

        public Builder setImportEmailSubject(String importEmailSubject) {
            TrackorForm.this.importEmailSubject = importEmailSubject;
            return this;
        }

        public Builder setImportEmailBody(String importEmailBody) {
            TrackorForm.this.importEmailBody = importEmailBody;
            return this;
        }

        public Builder setCreateImport(String createImport) {
            TrackorForm.this.createImport = createImport;
            return this;
        }

    }

}
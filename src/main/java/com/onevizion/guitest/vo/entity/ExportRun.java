package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class ExportRun {

    private String mode;
    private String delivery;
    private String comments;
    private String gridPageName;
    private String trackorTypeName;
    private String statusName;
    private String errorMessage;
    private String filePath;

    private ExportRun() {
        
    }

    public static Builder newBuilder() {
        return new ExportRun().new Builder();
    }

    public String getMode() {
        return mode;
    }

    public String getDelivery() {
        return delivery;
    }

    public String getComments() {
        return comments;
    }

    public String getGridPageName() {
        return gridPageName;
    }

    public String getTrackorTypeName() {
        return trackorTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getFilePath() {
        return filePath;
    }

    public class Builder {

        private Builder() {
            
        }

        public ExportRun build() {
            if (ExportRun.this.mode == null ||
                    ExportRun.this.delivery == null ||
                    ExportRun.this.comments == null ||
                    ExportRun.this.gridPageName == null ||
                    ExportRun.this.trackorTypeName == null ||
                    ExportRun.this.statusName == null ||
                    ExportRun.this.errorMessage == null ||
                    ExportRun.this.filePath == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ExportRun.this;
        }

        public Builder setMode(String mode) {
            ExportRun.this.mode = mode;
            return this;
        }

        public Builder setDelivery(String delivery) {
            ExportRun.this.delivery = delivery;
            return this;
        }

        public Builder setComments(String comments) {
            ExportRun.this.comments = comments;
            return this;
        }

        public Builder setGridPageName(String gridPageName) {
            ExportRun.this.gridPageName = gridPageName;
            return this;
        }

        public Builder setTrackorTypeName(String trackorTypeName) {
            ExportRun.this.trackorTypeName = trackorTypeName;
            return this;
        }

        public Builder setStatusName(String statusName) {
            ExportRun.this.statusName = statusName;
            return this;
        }

        public Builder setErrorMessage(String errorMessage) {
            ExportRun.this.errorMessage = errorMessage;
            return this;
        }

        public Builder setFilePath(String filePath) {
            ExportRun.this.filePath = filePath;
            return this;
        }

    }

}
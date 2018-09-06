package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class TrackorTourStep {

    private String elementType;
    private String elementName;
    private String elementId;
    private String duration;
    private String orphaned;
    private String backdrop;
    private String placement;
    private String action;
    private String formNumber;
    private String titleLabel;
    private String contentLabel;

    private TrackorTourStep() {
        
    }

    public static Builder newBuilder() {
        return new TrackorTourStep().new Builder();
    }

    public String getElementType() {
        return elementType;
    }

    public String getElementName() {
        return elementName;
    }

    public String getElementId() {
        return elementId;
    }

    public String getDuration() {
        return duration;
    }

    public String getOrphaned() {
        return orphaned;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getPlacement() {
        return placement;
    }

    public String getAction() {
        return action;
    }

    public String getFormNumber() {
        return formNumber;
    }

    public String getTitleLabel() {
        return titleLabel;
    }

    public String getContentLabel() {
        return contentLabel;
    }

    public class Builder {

        private Builder() {
            
        }

        public TrackorTourStep build() {
            if (TrackorTourStep.this.elementType == null ||
                    TrackorTourStep.this.elementName == null ||
                    TrackorTourStep.this.elementId == null ||
                    TrackorTourStep.this.duration == null ||
                    TrackorTourStep.this.orphaned == null ||
                    TrackorTourStep.this.backdrop == null ||
                    TrackorTourStep.this.placement == null ||
                    TrackorTourStep.this.action == null ||
                    TrackorTourStep.this.formNumber == null ||
                    TrackorTourStep.this.titleLabel == null ||
                    TrackorTourStep.this.contentLabel == null) {
                throw new SeleniumUnexpectedException("");
            }
            return TrackorTourStep.this;
        }

        public Builder setElementType(String elementType) {
            TrackorTourStep.this.elementType = elementType;
            return this;
        }

        public Builder setElementName(String elementName) {
            TrackorTourStep.this.elementName = elementName;
            return this;
        }

        public Builder setElementId(String elementId) {
            TrackorTourStep.this.elementId = elementId;
            return this;
        }

        public Builder setDuration(String duration) {
            TrackorTourStep.this.duration = duration;
            return this;
        }

        public Builder setOrphaned(String orphaned) {
            TrackorTourStep.this.orphaned = orphaned;
            return this;
        }

        public Builder setBackdrop(String backdrop) {
            TrackorTourStep.this.backdrop = backdrop;
            return this;
        }

        public Builder setPlacement(String placement) {
            TrackorTourStep.this.placement = placement;
            return this;
        }

        public Builder setAction(String action) {
            TrackorTourStep.this.action = action;
            return this;
        }

        public Builder setFormNumber(String formNumber) {
            TrackorTourStep.this.formNumber = formNumber;
            return this;
        }

        public Builder setTitleLabel(String titleLabel) {
            TrackorTourStep.this.titleLabel = titleLabel;
            return this;
        }

        public Builder setContentLabel(String contentLabel) {
            TrackorTourStep.this.contentLabel = contentLabel;
            return this;
        }

    }

}
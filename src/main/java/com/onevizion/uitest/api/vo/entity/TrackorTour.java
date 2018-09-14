package com.onevizion.uitest.api.vo.entity;

import java.util.List;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class TrackorTour {

    private String label;
    private String trackorType;
    private String startPlace;
    private String pageName;
    private String appletName;
    private String tabName;
    private String orderNumber;
    private String description;

    private List<String> roles;

    private List<TrackorTourStep> steps;

    private TrackorTour() {
        
    }

    public static Builder newBuilder() {
        return new TrackorTour().new Builder();
    }

    public String getLabel() {
        return label;
    }

    public String getTrackorType() {
        return trackorType;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public String getPageName() {
        return pageName;
    }

    public String getAppletName() {
        return appletName;
    }

    public String getTabName() {
        return tabName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getRoles() {
        return roles;
    }

    public List<TrackorTourStep> getSteps() {
        return steps;
    }

    public class Builder {

        private Builder() {
            
        }

        public TrackorTour build() {
            if (TrackorTour.this.label == null ||
                    TrackorTour.this.trackorType == null ||
                    TrackorTour.this.startPlace == null ||
                    TrackorTour.this.pageName == null ||
                    TrackorTour.this.appletName == null ||
                    TrackorTour.this.tabName == null ||
                    TrackorTour.this.orderNumber == null ||
                    TrackorTour.this.description == null ||
                    TrackorTour.this.roles == null ||
                    TrackorTour.this.steps == null) {
                throw new SeleniumUnexpectedException("");
            }
            return TrackorTour.this;
        }

        public Builder setLabel(String label) {
            TrackorTour.this.label = label;
            return this;
        }

        public Builder setTrackorType(String trackorType) {
            TrackorTour.this.trackorType = trackorType;
            return this;
        }

        public Builder setStartPlace(String startPlace) {
            TrackorTour.this.startPlace = startPlace;
            return this;
        }

        public Builder setPageName(String pageName) {
            TrackorTour.this.pageName = pageName;
            return this;
        }

        public Builder setAppletName(String appletName) {
            TrackorTour.this.appletName = appletName;
            return this;
        }

        public Builder setTabName(String tabName) {
            TrackorTour.this.tabName = tabName;
            return this;
        }

        public Builder setOrderNumber(String orderNumber) {
            TrackorTour.this.orderNumber = orderNumber;
            return this;
        }

        public Builder setDescription(String description) {
            TrackorTour.this.description = description;
            return this;
        }

        public Builder setRoles(List<String> roles) {
            TrackorTour.this.roles = roles;
            return this;
        }

        public Builder setSteps(List<TrackorTourStep> steps) {
            TrackorTour.this.steps = steps;
            return this;
        }

    }

}
package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class RuleType {

    private String name;
    private String label;
    private String description;
    private String startDateTime;
    private String repeatInterval;

    private RuleType() {
        
    }

    public static Builder newBuilder() {
        return new RuleType().new Builder();
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public String getRepeatInterval() {
        return repeatInterval;
    }

    public class Builder {

        private Builder() {
            
        }

        public RuleType build() {
            if (RuleType.this.name == null ||
                    RuleType.this.label == null ||
                    RuleType.this.description == null ||
                    RuleType.this.startDateTime == null ||
                    RuleType.this.repeatInterval == null) {
                throw new SeleniumUnexpectedException("");
            }
            return RuleType.this;
        }

        public Builder setName(String name) {
            RuleType.this.name = name;
            return this;
        }

        public Builder setLabel(String label) {
            RuleType.this.label = label;
            return this;
        }

        public Builder setDescription(String description) {
            RuleType.this.description = description;
            return this;
        }

        public Builder setStartDateTime(String startDateTime) {
            RuleType.this.startDateTime = startDateTime;
            return this;
        }

        public Builder setRepeatInterval(String repeatInterval) {
            RuleType.this.repeatInterval = repeatInterval;
            return this;
        }

    }

}
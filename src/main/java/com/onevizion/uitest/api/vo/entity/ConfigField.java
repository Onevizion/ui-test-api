package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.vo.ConfigFieldType;

public class ConfigField {

    private ConfigFieldType configFieldType;
    private String trackorTypeLabel;
    private String label;
    private String name;
    private String compPackage;
    private String color;
    private String width;

    private String description;
    private String comment;
    private String mandatory;
    private String twoColumns;
    private String lockable;
    private String multiple;
    private String readOnly;
    private String calcTotal;
    private String notCloneValue;
    private String notCloneLock;

    private ConfigFieldEfile configFieldEfile;
    private ConfigFieldNumber configFieldNumber;
    private ConfigFieldTrackorSelector configFieldTrackorSelector;
    private ConfigFieldTrackorDropDown configFieldTrackorDropDown;
    private ConfigFieldTime configFieldTime;
    private ConfigFieldDateTime configFieldDateTime;
    private ConfigFieldRollup configFieldRollup;
    private ConfigFieldDropDown configFieldDropDown;
    private ConfigFieldSelector configFieldSelector;
    private ConfigFieldMultiSelector configFieldMultiSelector;
    private ConfigFieldMemo configFieldMemo;
    private ConfigFieldWiki configFieldWiki;
    private ConfigFieldText configFieldText;
    private ConfigFieldDate configFieldDate;
    private ConfigFieldCheckbox configFieldCheckbox;
    private ConfigFieldLatitude configFieldLatitude;
    private ConfigFieldLongitude configFieldLongitude;
    private ConfigFieldHyperlink configFieldHyperlink;
    private ConfigFieldDbDropDown configFieldDbDropDown;
    private ConfigFieldDbSelector configFieldDbSelector;
    private ConfigFieldCalculated configFieldCalculated;

    private ConfigField() {
        
    }

    public static Builder newBuilder() {
        return new ConfigField().new Builder();
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigField build() {
            if (ConfigField.this.configFieldType == null ||
                    ConfigField.this.trackorTypeLabel == null ||
                    ConfigField.this.label == null ||
                    ConfigField.this.name == null ||
                    ConfigField.this.compPackage == null ||
                    ConfigField.this.color == null ||
                    ConfigField.this.width == null ||
                    ConfigField.this.description == null ||
                    ConfigField.this.comment == null ||
                    ConfigField.this.mandatory == null ||
                    ConfigField.this.twoColumns == null ||
                    ConfigField.this.lockable == null ||
                    ConfigField.this.multiple == null ||
                    ConfigField.this.readOnly == null ||
                    ConfigField.this.calcTotal == null ||
                    ConfigField.this.notCloneValue == null ||
                    ConfigField.this.notCloneLock == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigField.this;
        }

        public Builder setConfigFieldType(ConfigFieldType configFieldType) {
            ConfigField.this.configFieldType = configFieldType;
            return this;
        }

        public Builder setTrackorTypeLabel(String trackorTypeLabel) {
            ConfigField.this.trackorTypeLabel = trackorTypeLabel;
            return this;
        }

        public Builder setLabel(String label) {
            ConfigField.this.label = label;
            return this;
        }

        public Builder setName(String name) {
            ConfigField.this.name = name;
            return this;
        }

        public Builder setCompPackage(String compPackage) {
            ConfigField.this.compPackage = compPackage;
            return this;
        }

        public Builder setColor(String color) {
            ConfigField.this.color = color;
            return this;
        }

        public Builder setWidth(String width) {
            ConfigField.this.width = width;
            return this;
        }

        public Builder setDescription(String description) {
            ConfigField.this.description = description;
            return this;
        }

        public Builder setComment(String comment) {
            ConfigField.this.comment = comment;
            return this;
        }

        public Builder setMandatory(String mandatory) {
            ConfigField.this.mandatory = mandatory;
            return this;
        }

        public Builder setTwoColumns(String twoColumns) {
            ConfigField.this.twoColumns = twoColumns;
            return this;
        }

        public Builder setLockable(String lockable) {
            ConfigField.this.lockable = lockable;
            return this;
        }

        public Builder setMultiple(String multiple) {
            ConfigField.this.multiple = multiple;
            return this;
        }

        public Builder setReadOnly(String readOnly) {
            ConfigField.this.readOnly = readOnly;
            return this;
        }

        public Builder setCalcTotal(String calcTotal) {
            ConfigField.this.calcTotal = calcTotal;
            return this;
        }

        public Builder setNotCloneValue(String notCloneValue) {
            ConfigField.this.notCloneValue = notCloneValue;
            return this;
        }

        public Builder setNotCloneLock(String notCloneLock) {
            ConfigField.this.notCloneLock = notCloneLock;
            return this;
        }

        public Builder setConfigFieldEfile(ConfigFieldEfile configFieldEfile) {
            ConfigField.this.configFieldEfile = configFieldEfile;
            return this;
        }

        public Builder setConfigFieldNumber(ConfigFieldNumber configFieldNumber) {
            ConfigField.this.configFieldNumber = configFieldNumber;
            return this;
        }

        public Builder setConfigFieldTrackorSelector(ConfigFieldTrackorSelector configFieldTrackorSelector) {
            ConfigField.this.configFieldTrackorSelector = configFieldTrackorSelector;
            return this;
        }

        public Builder setConfigFieldTrackorDropDown(ConfigFieldTrackorDropDown configFieldTrackorDropDown) {
            ConfigField.this.configFieldTrackorDropDown = configFieldTrackorDropDown;
            return this;
        }

        public Builder setConfigFieldTime(ConfigFieldTime configFieldTime) {
            ConfigField.this.configFieldTime = configFieldTime;
            return this;
        }

        public Builder setConfigFieldDateTime(ConfigFieldDateTime configFieldDateTime) {
            ConfigField.this.configFieldDateTime = configFieldDateTime;
            return this;
        }

        public Builder setConfigFieldRollup(ConfigFieldRollup configFieldRollup) {
            ConfigField.this.configFieldRollup = configFieldRollup;
            return this;
        }

        public Builder setConfigFieldDropDown(ConfigFieldDropDown configFieldDropDown) {
            ConfigField.this.configFieldDropDown = configFieldDropDown;
            return this;
        }

        public Builder setConfigFieldSelector(ConfigFieldSelector configFieldSelector) {
            ConfigField.this.configFieldSelector = configFieldSelector;
            return this;
        }

        public Builder setConfigFieldMultiSelector(ConfigFieldMultiSelector configFieldMultiSelector) {
            ConfigField.this.configFieldMultiSelector = configFieldMultiSelector;
            return this;
        }

        public Builder setConfigFieldMemo(ConfigFieldMemo configFieldMemo) {
            ConfigField.this.configFieldMemo = configFieldMemo;
            return this;
        }

        public Builder setConfigFieldWiki(ConfigFieldWiki configFieldWiki) {
            ConfigField.this.configFieldWiki = configFieldWiki;
            return this;
        }

        public Builder setConfigFieldText(ConfigFieldText configFieldText) {
            ConfigField.this.configFieldText = configFieldText;
            return this;
        }

        public Builder setConfigFieldDate(ConfigFieldDate configFieldDate) {
            ConfigField.this.configFieldDate = configFieldDate;
            return this;
        }

        public Builder setConfigFieldCheckbox(ConfigFieldCheckbox configFieldCheckbox) {
            ConfigField.this.configFieldCheckbox = configFieldCheckbox;
            return this;
        }

        public Builder setConfigFieldLatitude(ConfigFieldLatitude configFieldLatitude) {
            ConfigField.this.configFieldLatitude = configFieldLatitude;
            return this;
        }

        public Builder setConfigFieldLongitude(ConfigFieldLongitude configFieldLongitude) {
            ConfigField.this.configFieldLongitude = configFieldLongitude;
            return this;
        }

        public Builder setConfigFieldHyperlink(ConfigFieldHyperlink configFieldHyperlink) {
            ConfigField.this.configFieldHyperlink = configFieldHyperlink;
            return this;
        }

        public Builder setConfigFieldDbDropDown(ConfigFieldDbDropDown configFieldDbDropDown) {
            ConfigField.this.configFieldDbDropDown = configFieldDbDropDown;
            return this;
        }

        public Builder setConfigFieldDbSelector(ConfigFieldDbSelector configFieldDbSelector) {
            ConfigField.this.configFieldDbSelector = configFieldDbSelector;
            return this;
        }

        
        public Builder setConfigFieldCalculated(ConfigFieldCalculated configFieldCalculated) {
            ConfigField.this.configFieldCalculated = configFieldCalculated;
            return this;
        }

    }

    public ConfigFieldType getConfigFieldType() {
        return configFieldType;
    }

    public String getTrackorTypeLabel() {
        return trackorTypeLabel;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public String getCompPackage() {
        return compPackage;
    }

    public String getColor() {
        return color;
    }

    public String getWidth() {
        return width;
    }

    public String getDescription() {
        return description;
    }

    public String getComment() {
        return comment;
    }

    public String getMandatory() {
        return mandatory;
    }

    public String getTwoColumns() {
        return twoColumns;
    }

    public String getLockable() {
        return lockable;
    }

    public String getMultiple() {
        return multiple;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public String getCalcTotal() {
        return calcTotal;
    }

    public String getNotCloneValue() {
        return notCloneValue;
    }

    public String getNotCloneLock() {
        return notCloneLock;
    }

    public ConfigFieldEfile getConfigFieldEfile() {
        return configFieldEfile;
    }

    public ConfigFieldNumber getConfigFieldNumber() {
        return configFieldNumber;
    }

    public ConfigFieldTrackorSelector getConfigFieldTrackorSelector() {
        return configFieldTrackorSelector;
    }

    public ConfigFieldTrackorDropDown getConfigFieldTrackorDropDown() {
        return configFieldTrackorDropDown;
    }

    public ConfigFieldTime getConfigFieldTime() {
        return configFieldTime;
    }

    public ConfigFieldDateTime getConfigFieldDateTime() {
        return configFieldDateTime;
    }

    public ConfigFieldRollup getConfigFieldRollup() {
        return configFieldRollup;
    }

    public ConfigFieldDropDown getConfigFieldDropDown() {
        return configFieldDropDown;
    }

    public ConfigFieldSelector getConfigFieldSelector() {
        return configFieldSelector;
    }

    public ConfigFieldMultiSelector getConfigFieldMultiSelector() {
        return configFieldMultiSelector;
    }

    public ConfigFieldMemo getConfigFieldMemo() {
        return configFieldMemo;
    }

    public ConfigFieldWiki getConfigFieldWiki() {
        return configFieldWiki;
    }

    public ConfigFieldText getConfigFieldText() {
        return configFieldText;
    }

    public ConfigFieldDate getConfigFieldDate() {
        return configFieldDate;
    }

    public ConfigFieldCheckbox getConfigFieldCheckbox() {
        return configFieldCheckbox;
    }

    public ConfigFieldLatitude getConfigFieldLatitude() {
        return configFieldLatitude;
    }

    public ConfigFieldLongitude getConfigFieldLongitude() {
        return configFieldLongitude;
    }

    public ConfigFieldHyperlink getConfigFieldHyperlink() {
        return configFieldHyperlink;
    }

    public ConfigFieldDbDropDown getConfigFieldDbDropDown() {
        return configFieldDbDropDown;
    }

    public ConfigFieldDbSelector getConfigFieldDbSelector() {
        return configFieldDbSelector;
    }

    public ConfigFieldCalculated getConfigFieldCalculated() {
        return configFieldCalculated;
    }

}
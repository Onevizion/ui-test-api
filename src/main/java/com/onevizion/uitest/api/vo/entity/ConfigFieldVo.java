package com.onevizion.uitest.api.vo.entity;

import java.util.List;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.vo.ConfigFieldType;

public class ConfigFieldVo {

    private ConfigFieldType configFieldType;
    private String trackorTypeLabel;
    private String label;
    private String name;
    private String color;
    private String width;

    private String description;
    private String comment;
    private String mandatory;
    private String twoColumns;
    private String lockable;
    private String readOnly;
    private String notCloneValue;
    private String notCloneLock;
    private String barcode;
    private String showExpandedList;

    private List<String> packages;

    private ConfigFieldVoEfile configFieldVoEfile;
    private ConfigFieldVoNumber configFieldVoNumber;
    private ConfigFieldVoTrackorSelector configFieldVoTrackorSelector;
    private ConfigFieldVoTrackorDropDown configFieldVoTrackorDropDown;
    private ConfigFieldVoTime configFieldVoTime;
    private ConfigFieldVoDateTime configFieldVoDateTime;
    private ConfigFieldVoRollup configFieldVoRollup;
    private ConfigFieldVoDropDown configFieldVoDropDown;
    private ConfigFieldVoSelector configFieldVoSelector;
    private ConfigFieldVoMultiSelector configFieldVoMultiSelector;
    private ConfigFieldVoMemo configFieldVoMemo;
    private ConfigFieldVoWiki configFieldVoWiki;
    private ConfigFieldVoText configFieldVoText;
    private ConfigFieldVoDate configFieldVoDate;
    private ConfigFieldVoCheckbox configFieldVoCheckbox;
    private ConfigFieldVoLatitude configFieldVoLatitude;
    private ConfigFieldVoLongitude configFieldVoLongitude;
    private ConfigFieldVoHyperlink configFieldVoHyperlink;
    private ConfigFieldVoDbDropDown configFieldVoDbDropDown;
    private ConfigFieldVoDbSelector configFieldVoDbSelector;
    private ConfigFieldVoCalculated configFieldVoCalculated;
    private ConfigFieldVoMultiTrackorSelector configFieldVoMultiTrackorSelector;

    private ConfigFieldVo() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVo().new Builder();
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVo build() {
            if (ConfigFieldVo.this.configFieldType == null ||
                    ConfigFieldVo.this.trackorTypeLabel == null ||
                    ConfigFieldVo.this.label == null ||
                    ConfigFieldVo.this.name == null ||
                    ConfigFieldVo.this.color == null ||
                    ConfigFieldVo.this.width == null ||
                    ConfigFieldVo.this.description == null ||
                    ConfigFieldVo.this.comment == null ||
                    ConfigFieldVo.this.mandatory == null ||
                    ConfigFieldVo.this.twoColumns == null ||
                    ConfigFieldVo.this.lockable == null ||
                    ConfigFieldVo.this.readOnly == null ||
                    ConfigFieldVo.this.notCloneValue == null ||
                    ConfigFieldVo.this.notCloneLock == null ||
                    ConfigFieldVo.this.barcode == null ||
                    ConfigFieldVo.this.showExpandedList == null ||
                    ConfigFieldVo.this.packages == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVo.this;
        }

        public Builder setConfigFieldType(ConfigFieldType configFieldType) {
            ConfigFieldVo.this.configFieldType = configFieldType;
            return this;
        }

        public Builder setTrackorTypeLabel(String trackorTypeLabel) {
            ConfigFieldVo.this.trackorTypeLabel = trackorTypeLabel;
            return this;
        }

        public Builder setLabel(String label) {
            ConfigFieldVo.this.label = label;
            return this;
        }

        public Builder setName(String name) {
            ConfigFieldVo.this.name = name;
            return this;
        }

        public Builder setColor(String color) {
            ConfigFieldVo.this.color = color;
            return this;
        }

        public Builder setWidth(String width) {
            ConfigFieldVo.this.width = width;
            return this;
        }

        public Builder setDescription(String description) {
            ConfigFieldVo.this.description = description;
            return this;
        }

        public Builder setComment(String comment) {
            ConfigFieldVo.this.comment = comment;
            return this;
        }

        public Builder setMandatory(String mandatory) {
            ConfigFieldVo.this.mandatory = mandatory;
            return this;
        }

        public Builder setTwoColumns(String twoColumns) {
            ConfigFieldVo.this.twoColumns = twoColumns;
            return this;
        }

        public Builder setLockable(String lockable) {
            ConfigFieldVo.this.lockable = lockable;
            return this;
        }

        public Builder setReadOnly(String readOnly) {
            ConfigFieldVo.this.readOnly = readOnly;
            return this;
        }

        public Builder setNotCloneValue(String notCloneValue) {
            ConfigFieldVo.this.notCloneValue = notCloneValue;
            return this;
        }

        public Builder setNotCloneLock(String notCloneLock) {
            ConfigFieldVo.this.notCloneLock = notCloneLock;
            return this;
        }

        public Builder setBarcode(String barcode) {
            ConfigFieldVo.this.barcode = barcode;
            return this;
        }

        public Builder setShowExpandedList(String showExpandedList) {
            ConfigFieldVo.this.showExpandedList = showExpandedList;
            return this;
        }

        public Builder setPackages(List<String> packages) {
            ConfigFieldVo.this.packages = packages;
            return this;
        }

        public Builder setConfigFieldEfile(ConfigFieldVoEfile configFieldVoEfile) {
            ConfigFieldVo.this.configFieldVoEfile = configFieldVoEfile;
            return this;
        }

        public Builder setConfigFieldNumber(ConfigFieldVoNumber configFieldVoNumber) {
            ConfigFieldVo.this.configFieldVoNumber = configFieldVoNumber;
            return this;
        }

        public Builder setConfigFieldTrackorSelector(ConfigFieldVoTrackorSelector configFieldVoTrackorSelector) {
            ConfigFieldVo.this.configFieldVoTrackorSelector = configFieldVoTrackorSelector;
            return this;
        }

        public Builder setConfigFieldTrackorDropDown(ConfigFieldVoTrackorDropDown configFieldVoTrackorDropDown) {
            ConfigFieldVo.this.configFieldVoTrackorDropDown = configFieldVoTrackorDropDown;
            return this;
        }

        public Builder setConfigFieldTime(ConfigFieldVoTime configFieldVoTime) {
            ConfigFieldVo.this.configFieldVoTime = configFieldVoTime;
            return this;
        }

        public Builder setConfigFieldDateTime(ConfigFieldVoDateTime configFieldVoDateTime) {
            ConfigFieldVo.this.configFieldVoDateTime = configFieldVoDateTime;
            return this;
        }

        public Builder setConfigFieldRollup(ConfigFieldVoRollup configFieldVoRollup) {
            ConfigFieldVo.this.configFieldVoRollup = configFieldVoRollup;
            return this;
        }

        public Builder setConfigFieldDropDown(ConfigFieldVoDropDown configFieldVoDropDown) {
            ConfigFieldVo.this.configFieldVoDropDown = configFieldVoDropDown;
            return this;
        }

        public Builder setConfigFieldSelector(ConfigFieldVoSelector configFieldVoSelector) {
            ConfigFieldVo.this.configFieldVoSelector = configFieldVoSelector;
            return this;
        }

        public Builder setConfigFieldMultiSelector(ConfigFieldVoMultiSelector configFieldVoMultiSelector) {
            ConfigFieldVo.this.configFieldVoMultiSelector = configFieldVoMultiSelector;
            return this;
        }

        public Builder setConfigFieldMemo(ConfigFieldVoMemo configFieldVoMemo) {
            ConfigFieldVo.this.configFieldVoMemo = configFieldVoMemo;
            return this;
        }

        public Builder setConfigFieldWiki(ConfigFieldVoWiki configFieldVoWiki) {
            ConfigFieldVo.this.configFieldVoWiki = configFieldVoWiki;
            return this;
        }

        public Builder setConfigFieldText(ConfigFieldVoText configFieldVoText) {
            ConfigFieldVo.this.configFieldVoText = configFieldVoText;
            return this;
        }

        public Builder setConfigFieldDate(ConfigFieldVoDate configFieldVoDate) {
            ConfigFieldVo.this.configFieldVoDate = configFieldVoDate;
            return this;
        }

        public Builder setConfigFieldCheckbox(ConfigFieldVoCheckbox configFieldVoCheckbox) {
            ConfigFieldVo.this.configFieldVoCheckbox = configFieldVoCheckbox;
            return this;
        }

        public Builder setConfigFieldLatitude(ConfigFieldVoLatitude configFieldVoLatitude) {
            ConfigFieldVo.this.configFieldVoLatitude = configFieldVoLatitude;
            return this;
        }

        public Builder setConfigFieldLongitude(ConfigFieldVoLongitude configFieldVoLongitude) {
            ConfigFieldVo.this.configFieldVoLongitude = configFieldVoLongitude;
            return this;
        }

        public Builder setConfigFieldHyperlink(ConfigFieldVoHyperlink configFieldVoHyperlink) {
            ConfigFieldVo.this.configFieldVoHyperlink = configFieldVoHyperlink;
            return this;
        }

        public Builder setConfigFieldDbDropDown(ConfigFieldVoDbDropDown configFieldVoDbDropDown) {
            ConfigFieldVo.this.configFieldVoDbDropDown = configFieldVoDbDropDown;
            return this;
        }

        public Builder setConfigFieldDbSelector(ConfigFieldVoDbSelector configFieldVoDbSelector) {
            ConfigFieldVo.this.configFieldVoDbSelector = configFieldVoDbSelector;
            return this;
        }

        public Builder setConfigFieldCalculated(ConfigFieldVoCalculated configFieldVoCalculated) {
            ConfigFieldVo.this.configFieldVoCalculated = configFieldVoCalculated;
            return this;
        }

        public Builder setConfigFieldMultiTrackorSelector(ConfigFieldVoMultiTrackorSelector configFieldVoMultiTrackorSelector) {
            ConfigFieldVo.this.configFieldVoMultiTrackorSelector = configFieldVoMultiTrackorSelector;
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

    public String getReadOnly() {
        return readOnly;
    }

    public String getNotCloneValue() {
        return notCloneValue;
    }

    public String getNotCloneLock() {
        return notCloneLock;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getShowExpandedList() {
        return showExpandedList;
    }

    public List<String> getPackages() {
        return packages;
    }

    public ConfigFieldVoEfile getConfigFieldEfile() {
        return configFieldVoEfile;
    }

    public ConfigFieldVoNumber getConfigFieldNumber() {
        return configFieldVoNumber;
    }

    public ConfigFieldVoTrackorSelector getConfigFieldTrackorSelector() {
        return configFieldVoTrackorSelector;
    }

    public ConfigFieldVoTrackorDropDown getConfigFieldTrackorDropDown() {
        return configFieldVoTrackorDropDown;
    }

    public ConfigFieldVoTime getConfigFieldTime() {
        return configFieldVoTime;
    }

    public ConfigFieldVoDateTime getConfigFieldDateTime() {
        return configFieldVoDateTime;
    }

    public ConfigFieldVoRollup getConfigFieldRollup() {
        return configFieldVoRollup;
    }

    public ConfigFieldVoDropDown getConfigFieldDropDown() {
        return configFieldVoDropDown;
    }

    public ConfigFieldVoSelector getConfigFieldSelector() {
        return configFieldVoSelector;
    }

    public ConfigFieldVoMultiSelector getConfigFieldMultiSelector() {
        return configFieldVoMultiSelector;
    }

    public ConfigFieldVoMemo getConfigFieldMemo() {
        return configFieldVoMemo;
    }

    public ConfigFieldVoWiki getConfigFieldWiki() {
        return configFieldVoWiki;
    }

    public ConfigFieldVoText getConfigFieldText() {
        return configFieldVoText;
    }

    public ConfigFieldVoDate getConfigFieldDate() {
        return configFieldVoDate;
    }

    public ConfigFieldVoCheckbox getConfigFieldCheckbox() {
        return configFieldVoCheckbox;
    }

    public ConfigFieldVoLatitude getConfigFieldLatitude() {
        return configFieldVoLatitude;
    }

    public ConfigFieldVoLongitude getConfigFieldLongitude() {
        return configFieldVoLongitude;
    }

    public ConfigFieldVoHyperlink getConfigFieldHyperlink() {
        return configFieldVoHyperlink;
    }

    public ConfigFieldVoDbDropDown getConfigFieldDbDropDown() {
        return configFieldVoDbDropDown;
    }

    public ConfigFieldVoDbSelector getConfigFieldDbSelector() {
        return configFieldVoDbSelector;
    }

    public ConfigFieldVoCalculated getConfigFieldCalculated() {
        return configFieldVoCalculated;
    }

    public ConfigFieldVoMultiTrackorSelector getConfigFieldMultiTrackorSelector() {
        return configFieldVoMultiTrackorSelector;
    }

}
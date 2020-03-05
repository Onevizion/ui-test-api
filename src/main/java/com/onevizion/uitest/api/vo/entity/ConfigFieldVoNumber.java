package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ConfigFieldVoNumber {

    private String length;
    private String defValueSql;
    private String prefix;
    private String suffix;
    private String decimal;
    private String negativeColor;
    private String positiveColor;
    private String parensForNegative;
    private String separateThousands;
    private String validation;
    private String validationEnabled;

    private ConfigFieldVoNumber() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldVoNumber().new Builder();
    }

    public String getLength() {
        return length;
    }

    public String getDefValueSql() {
        return defValueSql;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getDecimal() {
        return decimal;
    }

    public String getNegativeColor() {
        return negativeColor;
    }

    public String getPositiveColor() {
        return positiveColor;
    }

    public String getParensForNegative() {
        return parensForNegative;
    }

    public String getSeparateThousands() {
        return separateThousands;
    }

    public String getValidation() {
        return validation;
    }

    public String getValidationEnabled() {
        return validationEnabled;
    }

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldVoNumber build() {
            if (ConfigFieldVoNumber.this.length == null ||
                    ConfigFieldVoNumber.this.defValueSql == null ||
                    ConfigFieldVoNumber.this.prefix == null ||
                    ConfigFieldVoNumber.this.suffix == null ||
                    ConfigFieldVoNumber.this.decimal == null ||
                    ConfigFieldVoNumber.this.negativeColor == null ||
                    ConfigFieldVoNumber.this.positiveColor == null ||
                    ConfigFieldVoNumber.this.parensForNegative == null ||
                    ConfigFieldVoNumber.this.separateThousands == null ||
                    ConfigFieldVoNumber.this.validation == null ||
                    ConfigFieldVoNumber.this.validationEnabled == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldVoNumber.this;
        }

        public Builder setLength(String length) {
            ConfigFieldVoNumber.this.length = length;
            return this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldVoNumber.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setPrefix(String prefix) {
            ConfigFieldVoNumber.this.prefix = prefix;
            return this;
        }

        public Builder setSuffix(String suffix) {
            ConfigFieldVoNumber.this.suffix = suffix;
            return this;
        }

        public Builder setDecimal(String decimal) {
            ConfigFieldVoNumber.this.decimal = decimal;
            return this;
        }

        public Builder setNegativeColor(String negativeColor) {
            ConfigFieldVoNumber.this.negativeColor = negativeColor;
            return this;
        }

        public Builder setPositiveColor(String positiveColor) {
            ConfigFieldVoNumber.this.positiveColor = positiveColor;
            return this;
        }

        public Builder setParensForNegative(String parensForNegative) {
            ConfigFieldVoNumber.this.parensForNegative = parensForNegative;
            return this;
        }

        public Builder setSeparateThousands(String separateThousands) {
            ConfigFieldVoNumber.this.separateThousands = separateThousands;
            return this;
        }

        public Builder setValidation(String validation) {
            ConfigFieldVoNumber.this.validation = validation;
            return this;
        }

        public Builder setValidationEnabled(String validationEnabled) {
            ConfigFieldVoNumber.this.validationEnabled = validationEnabled;
            return this;
        }

    }

}
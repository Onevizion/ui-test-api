package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class ConfigFieldNumber {

    private String length;
    private String defValueSql;
    private String prefix;
    private String suffix;
    private String decimal;
    private String negativeColor;
    private String positiveColor;
    private String parensForNegative;
    private String separateThousands;

    private ConfigFieldNumber() {
        
    }

    public static Builder newBuilder() {
        return new ConfigFieldNumber().new Builder();
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

    public class Builder {

        private Builder() {
            
        }

        public ConfigFieldNumber build() {
            if (ConfigFieldNumber.this.length == null ||
                    ConfigFieldNumber.this.defValueSql == null ||
                    ConfigFieldNumber.this.prefix == null ||
                    ConfigFieldNumber.this.suffix == null ||
                    ConfigFieldNumber.this.decimal == null ||
                    ConfigFieldNumber.this.negativeColor == null ||
                    ConfigFieldNumber.this.positiveColor == null ||
                    ConfigFieldNumber.this.parensForNegative == null ||
                    ConfigFieldNumber.this.separateThousands == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ConfigFieldNumber.this;
        }

        public Builder setLength(String length) {
            ConfigFieldNumber.this.length = length;
            return this;
        }

        public Builder setDefValueSql(String defValueSql) {
            ConfigFieldNumber.this.defValueSql = defValueSql;
            return this;
        }

        public Builder setPrefix(String prefix) {
            ConfigFieldNumber.this.prefix = prefix;
            return this;
        }

        public Builder setSuffix(String suffix) {
            ConfigFieldNumber.this.suffix = suffix;
            return this;
        }

        public Builder setDecimal(String decimal) {
            ConfigFieldNumber.this.decimal = decimal;
            return this;
        }

        public Builder setNegativeColor(String negativeColor) {
            ConfigFieldNumber.this.negativeColor = negativeColor;
            return this;
        }

        public Builder setPositiveColor(String positiveColor) {
            ConfigFieldNumber.this.positiveColor = positiveColor;
            return this;
        }

        public Builder setParensForNegative(String parensForNegative) {
            ConfigFieldNumber.this.parensForNegative = parensForNegative;
            return this;
        }

        public Builder setSeparateThousands(String separateThousands) {
            ConfigFieldNumber.this.separateThousands = separateThousands;
            return this;
        }

    }

}
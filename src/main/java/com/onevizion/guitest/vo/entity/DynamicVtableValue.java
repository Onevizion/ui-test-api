package com.onevizion.guitest.vo.entity;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class DynamicVtableValue {

    private String value;
    private String ordNum;
    private String color;
    private String display;
    private String filterable;

    private DynamicVtableValue() {
        
    }

    public static Builder newBuilder() {
        return new DynamicVtableValue().new Builder();
    }

    public String getValue() {
        return value;
    }

    public String getOrdNum() {
        return ordNum;
    }

    public String getColor() {
        return color;
    }

    public String getDisplay() {
        return display;
    }

    public String getFilterable() {
        return filterable;
    }

    public class Builder {

        private Builder() {
            
        }

        public DynamicVtableValue build() {
            if (DynamicVtableValue.this.value == null ||
                    DynamicVtableValue.this.ordNum == null ||
                    DynamicVtableValue.this.color == null ||
                    DynamicVtableValue.this.display == null ||
                    DynamicVtableValue.this.filterable == null) {
                throw new SeleniumUnexpectedException("");
            }
            return DynamicVtableValue.this;
        }

        public Builder setValue(String value) {
            DynamicVtableValue.this.value = value;
            return this;
        }

        public Builder setOrdNum(String ordNum) {
            DynamicVtableValue.this.ordNum = ordNum;
            return this;
        }

        public Builder setColor(String color) {
            DynamicVtableValue.this.color = color;
            return this;
        }

        public Builder setDisplay(String display) {
            DynamicVtableValue.this.display = display;
            return this;
        }

        public Builder setFilterable(String filterable) {
            DynamicVtableValue.this.filterable = filterable;
            return this;
        }

    }

}
package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ComponentPackage {

    private String name;

    private ComponentPackage() {
        
    }

    public static Builder newBuilder() {
        return new ComponentPackage().new Builder();
    }

    public String getName() {
        return name;
    }

    public class Builder {

        private Builder() {
            
        }

        public ComponentPackage build() {
            if (ComponentPackage.this.name == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ComponentPackage.this;
        }

        public Builder setName(String name) {
            ComponentPackage.this.name = name;
            return this;
        }

    }

}
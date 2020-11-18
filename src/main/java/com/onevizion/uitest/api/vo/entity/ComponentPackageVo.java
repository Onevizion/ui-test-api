package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ComponentPackageVo {

    private String name;

    private ComponentPackageVo() {
        
    }

    public static Builder newBuilder() {
        return new ComponentPackageVo().new Builder();
    }

    public String getName() {
        return name;
    }

    public class Builder {

        private Builder() {
            
        }

        public ComponentPackageVo build() {
            if (ComponentPackageVo.this.name == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ComponentPackageVo.this;
        }

        public Builder setName(String name) {
            ComponentPackageVo.this.name = name;
            return this;
        }

    }

}
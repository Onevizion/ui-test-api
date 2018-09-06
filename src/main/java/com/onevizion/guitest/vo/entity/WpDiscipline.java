package com.onevizion.guitest.vo.entity;

import java.util.List;

import com.onevizion.guitest.exception.SeleniumUnexpectedException;

public class WpDiscipline {

    private String name;
    private String description;

    private List<String> roles;

    private WpDiscipline() {
        
    }

    public static Builder newBuilder() {
        return new WpDiscipline().new Builder();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getRoles() {
        return roles;
    }

    public class Builder {

        private Builder() {
            
        }

        public WpDiscipline build() {
            if (WpDiscipline.this.name == null ||
                    WpDiscipline.this.description == null ||
                    WpDiscipline.this.roles == null) {
                throw new SeleniumUnexpectedException("");
            }
            return WpDiscipline.this;
        }

        public Builder setName(String name) {
            WpDiscipline.this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            WpDiscipline.this.description = description;
            return this;
        }

        public Builder setRoles(List<String> roles) {
            WpDiscipline.this.roles = roles;
            return this;
        }

    }

}
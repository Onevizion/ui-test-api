package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class SecurityRole {

    private String name;
    private String description;

    private SecurityRoleDefaultPrivs defaultPrivs;

    private SecurityRoleDefaultLockPrivs defaultLockPrivs;

    private SecurityRoleDefaultAssignments defaultAssignments;

    private SecurityRole() {
        
    }

    public static Builder newBuilder() {
        return new SecurityRole().new Builder();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public SecurityRoleDefaultPrivs getDefaultPrivs() {
        return defaultPrivs;
    }

    public SecurityRoleDefaultLockPrivs getDefaultLockPrivs() {
        return defaultLockPrivs;
    }

    public SecurityRoleDefaultAssignments getDefaultAssignments() {
        return defaultAssignments;
    }

    public class Builder {

        private Builder() {
            
        }

        public SecurityRole build() {
            if (SecurityRole.this.name == null ||
                    SecurityRole.this.description == null ||
                    SecurityRole.this.defaultPrivs == null ||
                    SecurityRole.this.defaultLockPrivs == null ||
                    SecurityRole.this.defaultAssignments == null) {
                throw new SeleniumUnexpectedException("");
            }
            return SecurityRole.this;
        }

        public Builder setName(String name) {
            SecurityRole.this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            SecurityRole.this.description = description;
            return this;
        }

        public Builder setDefaultPrivs(SecurityRoleDefaultPrivs defaultPrivs) {
            SecurityRole.this.defaultPrivs = defaultPrivs;
            return this;
        }

        public Builder setDefaultLockPrivs(SecurityRoleDefaultLockPrivs defaultLockPrivs) {
            SecurityRole.this.defaultLockPrivs = defaultLockPrivs;
            return this;
        }

        public Builder setDefaultAssignments(SecurityRoleDefaultAssignments defaultAssignments) {
            SecurityRole.this.defaultAssignments = defaultAssignments;
            return this;
        }

    }

}
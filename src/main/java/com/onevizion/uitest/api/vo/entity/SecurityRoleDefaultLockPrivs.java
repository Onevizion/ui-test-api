package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class SecurityRoleDefaultLockPrivs {

    private String lockableFieldL;
    private String lockableFieldU;
    private String lockableRelationL;
    private String lockableRelationU;

    private SecurityRoleDefaultLockPrivs() {
        
    }

    public static Builder newBuilder() {
        return new SecurityRoleDefaultLockPrivs().new Builder();
    }

    public String getLockableFieldL() {
        return lockableFieldL;
    }

    public String getLockableFieldU() {
        return lockableFieldU;
    }

    public String getLockableRelationL() {
        return lockableRelationL;
    }

    public String getLockableRelationU() {
        return lockableRelationU;
    }

    public class Builder {

        private Builder() {
            
        }

        public SecurityRoleDefaultLockPrivs build() {
            if (SecurityRoleDefaultLockPrivs.this.lockableFieldL == null ||
                    SecurityRoleDefaultLockPrivs.this.lockableFieldU == null ||
                    SecurityRoleDefaultLockPrivs.this.lockableRelationL == null ||
                    SecurityRoleDefaultLockPrivs.this.lockableRelationU == null) {
                throw new SeleniumUnexpectedException("");
            }
            return SecurityRoleDefaultLockPrivs.this;
        }

        public Builder setLockableFieldL(String lockableFieldL) {
            SecurityRoleDefaultLockPrivs.this.lockableFieldL = lockableFieldL;
            return this;
        }

        public Builder setLockableFieldU(String lockableFieldU) {
            SecurityRoleDefaultLockPrivs.this.lockableFieldU = lockableFieldU;
            return this;
        }

        public Builder setLockableRelationL(String lockableRelationL) {
            SecurityRoleDefaultLockPrivs.this.lockableRelationL = lockableRelationL;
            return this;
        }

        public Builder setLockableRelationU(String lockableRelationU) {
            SecurityRoleDefaultLockPrivs.this.lockableRelationU = lockableRelationU;
            return this;
        }

    }

}
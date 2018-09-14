package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class SecurityRoleDefaultPrivs {

    private String appletR;
    private String appletE;
    private String appletA;
    private String appletD;
    private String configAppletR;
    private String configAppletE;
    private String configTabR;
    private String configTabE;
    private String relationR;
    private String relationE;
    private String relationA;
    private String relationD;
    private String superuserAppletR;
    private String superuserAppletE;
    private String superuserAppletA;
    private String superuserAppletD;
    private String trackorTypeR;
    private String trackorTypeE;
    private String trackorTypeA;
    private String trackorTypeD;
    private String workflowR;
    private String workflowE;
    private String workflowA;

    private SecurityRoleDefaultPrivs() {
        
    }

    public static Builder newBuilder() {
        return new SecurityRoleDefaultPrivs().new Builder();
    }

    public String getAppletR() {
        return appletR;
    }

    public String getAppletE() {
        return appletE;
    }

    public String getAppletA() {
        return appletA;
    }

    public String getAppletD() {
        return appletD;
    }

    public String getConfigAppletR() {
        return configAppletR;
    }

    public String getConfigAppletE() {
        return configAppletE;
    }

    public String getConfigTabR() {
        return configTabR;
    }

    public String getConfigTabE() {
        return configTabE;
    }

    public String getRelationR() {
        return relationR;
    }

    public String getRelationE() {
        return relationE;
    }

    public String getRelationA() {
        return relationA;
    }

    public String getRelationD() {
        return relationD;
    }

    public String getSuperuserAppletR() {
        return superuserAppletR;
    }

    public String getSuperuserAppletE() {
        return superuserAppletE;
    }

    public String getSuperuserAppletA() {
        return superuserAppletA;
    }

    public String getSuperuserAppletD() {
        return superuserAppletD;
    }

    public String getTrackorTypeR() {
        return trackorTypeR;
    }

    public String getTrackorTypeE() {
        return trackorTypeE;
    }

    public String getTrackorTypeA() {
        return trackorTypeA;
    }

    public String getTrackorTypeD() {
        return trackorTypeD;
    }

    public String getWorkflowR() {
        return workflowR;
    }

    public String getWorkflowE() {
        return workflowE;
    }

    public String getWorkflowA() {
        return workflowA;
    }

    public class Builder {

        private Builder() {
            
        }

        public SecurityRoleDefaultPrivs build() {
            if (SecurityRoleDefaultPrivs.this.appletR == null ||
                    SecurityRoleDefaultPrivs.this.appletE == null ||
                    SecurityRoleDefaultPrivs.this.appletA == null ||
                    SecurityRoleDefaultPrivs.this.appletD == null ||
                    SecurityRoleDefaultPrivs.this.configAppletR == null ||
                    SecurityRoleDefaultPrivs.this.configAppletE == null ||
                    SecurityRoleDefaultPrivs.this.configTabR == null ||
                    SecurityRoleDefaultPrivs.this.configTabE == null ||
                    SecurityRoleDefaultPrivs.this.relationR == null ||
                    SecurityRoleDefaultPrivs.this.relationE == null ||
                    SecurityRoleDefaultPrivs.this.relationA == null ||
                    SecurityRoleDefaultPrivs.this.relationD == null ||
                    SecurityRoleDefaultPrivs.this.superuserAppletR == null ||
                    SecurityRoleDefaultPrivs.this.superuserAppletE == null ||
                    SecurityRoleDefaultPrivs.this.superuserAppletA == null ||
                    SecurityRoleDefaultPrivs.this.superuserAppletD == null ||
                    SecurityRoleDefaultPrivs.this.trackorTypeR == null ||
                    SecurityRoleDefaultPrivs.this.trackorTypeE == null ||
                    SecurityRoleDefaultPrivs.this.trackorTypeA == null ||
                    SecurityRoleDefaultPrivs.this.trackorTypeD == null ||
                    SecurityRoleDefaultPrivs.this.workflowR == null ||
                    SecurityRoleDefaultPrivs.this.workflowE == null ||
                    SecurityRoleDefaultPrivs.this.workflowA == null) {
                throw new SeleniumUnexpectedException("");
            }
            return SecurityRoleDefaultPrivs.this;
        }

        public Builder setAppletR(String appletR) {
            SecurityRoleDefaultPrivs.this.appletR = appletR;
            return this;
        }

        public Builder setAppletE(String appletE) {
            SecurityRoleDefaultPrivs.this.appletE = appletE;
            return this;
        }

        public Builder setAppletA(String appletA) {
            SecurityRoleDefaultPrivs.this.appletA = appletA;
            return this;
        }

        public Builder setAppletD(String appletD) {
            SecurityRoleDefaultPrivs.this.appletD = appletD;
            return this;
        }

        public Builder setConfigAppletR(String configAppletR) {
            SecurityRoleDefaultPrivs.this.configAppletR = configAppletR;
            return this;
        }

        public Builder setConfigAppletE(String configAppletE) {
            SecurityRoleDefaultPrivs.this.configAppletE = configAppletE;
            return this;
        }

        public Builder setConfigTabR(String configTabR) {
            SecurityRoleDefaultPrivs.this.configTabR = configTabR;
            return this;
        }

        public Builder setConfigTabE(String configTabE) {
            SecurityRoleDefaultPrivs.this.configTabE = configTabE;
            return this;
        }

        public Builder setRelationR(String relationR) {
            SecurityRoleDefaultPrivs.this.relationR = relationR;
            return this;
        }

        public Builder setRelationE(String relationE) {
            SecurityRoleDefaultPrivs.this.relationE = relationE;
            return this;
        }

        public Builder setRelationA(String relationA) {
            SecurityRoleDefaultPrivs.this.relationA = relationA;
            return this;
        }

        public Builder setRelationD(String relationD) {
            SecurityRoleDefaultPrivs.this.relationD = relationD;
            return this;
        }

        public Builder setSuperuserAppletR(String superuserAppletR) {
            SecurityRoleDefaultPrivs.this.superuserAppletR = superuserAppletR;
            return this;
        }

        public Builder setSuperuserAppletE(String superuserAppletE) {
            SecurityRoleDefaultPrivs.this.superuserAppletE = superuserAppletE;
            return this;
        }

        public Builder setSuperuserAppletA(String superuserAppletA) {
            SecurityRoleDefaultPrivs.this.superuserAppletA = superuserAppletA;
            return this;
        }

        public Builder setSuperuserAppletD(String superuserAppletD) {
            SecurityRoleDefaultPrivs.this.superuserAppletD = superuserAppletD;
            return this;
        }

        public Builder setTrackorTypeR(String trackorTypeR) {
            SecurityRoleDefaultPrivs.this.trackorTypeR = trackorTypeR;
            return this;
        }

        public Builder setTrackorTypeE(String trackorTypeE) {
            SecurityRoleDefaultPrivs.this.trackorTypeE = trackorTypeE;
            return this;
        }

        public Builder setTrackorTypeA(String trackorTypeA) {
            SecurityRoleDefaultPrivs.this.trackorTypeA = trackorTypeA;
            return this;
        }

        public Builder setTrackorTypeD(String trackorTypeD) {
            SecurityRoleDefaultPrivs.this.trackorTypeD = trackorTypeD;
            return this;
        }

        public Builder setWorkflowR(String workflowR) {
            SecurityRoleDefaultPrivs.this.workflowR = workflowR;
            return this;
        }

        public Builder setWorkflowE(String workflowE) {
            SecurityRoleDefaultPrivs.this.workflowE = workflowE;
            return this;
        }

        public Builder setWorkflowA(String workflowA) {
            SecurityRoleDefaultPrivs.this.workflowA = workflowA;
            return this;
        }

    }

}
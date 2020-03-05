package com.onevizion.uitest.api.vo.api.v3;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Priv implements Comparable<Priv> {

    private String securityGroup;
    private String secGroupType;
    private String priv;

    @JsonProperty("security_group")
    public String getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(String securityGroup) {
        this.securityGroup = securityGroup;
    }

    @JsonProperty("sec_group_type")
    public String getSecGroupType() {
        return secGroupType;
    }

    public void setSecGroupType(String secGroupType) {
        this.secGroupType = secGroupType;
    }

    @JsonProperty("priv")
    public String getPriv() {
        return priv;
    }

    public void setPriv(String priv) {
        this.priv = priv;
    }

    @Override
    public int compareTo(Priv o) {
        return this.securityGroup.compareTo(o.securityGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priv, securityGroup, secGroupType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Priv other = (Priv) obj;
        return Objects.equals(priv, other.priv) && Objects.equals(securityGroup, other.securityGroup)
                && Objects.equals(secGroupType, other.secGroupType);
    }

    @Override
    public String toString() {
        return "Priv [securityGroup=" + securityGroup + ", secGroupType=" + secGroupType + ", priv=" + priv + "]";
    }

}
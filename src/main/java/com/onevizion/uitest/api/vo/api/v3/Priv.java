package com.onevizion.uitest.api.vo.api.v3;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Priv implements Comparable<Priv> {

    private String securityGroup;
    private String tokenType;
    private String priv;

    @JsonProperty("security_group")
    public String getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(String securityGroup) {
        this.securityGroup = securityGroup;
    }

    @JsonProperty("token_type")
    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
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
        return Objects.hash(priv, securityGroup, tokenType);
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
                && Objects.equals(tokenType, other.tokenType);
    }

    @Override
    public String toString() {
        return "Priv [securityGroup=" + securityGroup + ", tokenType=" + tokenType + ", priv=" + priv + "]";
    }

}
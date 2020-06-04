package com.onevizion.uitest.api.vo.api.v3;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token implements Comparable<Token> {

    private String id;
    private String name;
    private String accessKey;
    private String secretKey;
    private String userId;
    private String expirationTs;
    private String type;
    private String status;

    @JsonProperty("auth_token_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("access_key")
    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    @JsonProperty("secret_key")
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("expiration_ts")
    public String getExpirationTs() {
        return expirationTs;
    }

    public void setExpirationTs(String expirationTs) {
        this.expirationTs = expirationTs;
    }

    @JsonProperty("auth_token_type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(Token o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessKey, expirationTs, id, name, secretKey, status, type, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Token)) {
            return false;
        }
        Token other = (Token) obj;
        return Objects.equals(accessKey, other.accessKey) && Objects.equals(expirationTs, other.expirationTs)
                && Objects.equals(id, other.id) && Objects.equals(name, other.name)
                && Objects.equals(secretKey, other.secretKey) && Objects.equals(status, other.status)
                && Objects.equals(type, other.type) && Objects.equals(userId, other.userId);
    }

    @Override
    public String toString() {
        return "Token [id=" + id + ", name=" + name + ", accessKey=" + accessKey + ", secretKey=" + secretKey
                + ", userId=" + userId + ", expirationTs=" + expirationTs + ", type=" + type + ", status=" + status
                + "]";
    }

}
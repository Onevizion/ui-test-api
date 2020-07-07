package com.onevizion.uitest.api.vo.api.v3;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatUser implements Comparable<ChatUser> {

    private String userId;
    private String userName;

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int compareTo(ChatUser o) {
        return this.userId.compareTo(o.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName);
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
        ChatUser other = (ChatUser) obj;
        return Objects.equals(userId, other.userId) && Objects.equals(userName, other.userName);
    }

    @Override
    public String toString() {
        return "ChatSubscriber [userId=" + userId + ", userName=" + userName + "]";
    }

}
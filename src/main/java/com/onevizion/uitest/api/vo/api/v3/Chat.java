package com.onevizion.uitest.api.vo.api.v3;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Chat implements Comparable<Chat> {

    private String chatId;
    private String chatName;
    private String chatType;
    private String createdTs;
    private String trackorId;
    private String taskId;

    @JsonProperty("chat_id")
    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    @JsonProperty("name")
    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    @JsonProperty("chat_type")
    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    @JsonProperty("created_ts")
    public String getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(String createdTs) {
        this.createdTs = createdTs;
    }

    @JsonProperty("trackor_id")
    public String getTrackorId() {
        return trackorId;
    }

    public void setTrackorId(String trackorId) {
        this.trackorId = trackorId;
    }

    @JsonProperty("task_id")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String trackorId) {
        this.taskId = trackorId;
    }

    @Override
    public int compareTo(Chat o) {
        return this.chatId.compareTo(o.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, chatName, chatType, createdTs, trackorId, taskId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Chat)) {
            return false;
        }
        Chat other = (Chat) obj;
        return Objects.equals(chatId, other.chatId) && Objects.equals(chatName, other.chatName)
                && Objects.equals(chatType, other.chatType) && Objects.equals(createdTs, other.createdTs)
                && Objects.equals(trackorId, other.trackorId) && Objects.equals(taskId, other.taskId);
    }

    @Override
    public String toString() {
        return "Chat [chatId=" + chatId + ", chatName=" + chatName + ", chatType=" + chatType + ", createdTs="
                + createdTs + ", trackorId=" + trackorId + ", taskId=" + taskId + "]";
    }

}
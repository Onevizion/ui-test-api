package com.onevizion.uitest.api.vo.api.v3;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatMessage implements Comparable<ChatMessage> {

    private String chatMessageId;
    private String chatId;
    private String text;
    private String userId;
    private String userName;
    private String ts;
    private List<ChatMessageFile> files;
    private String userMessages;

    @JsonProperty("chat_message_id")
    public String getChatMessageId() {
        return chatMessageId;
    }

    public void setChatMessageId(String chatMessageId) {
        this.chatMessageId = chatMessageId;
    }

    @JsonProperty("chat_id")
    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

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

    @JsonProperty("ts")
    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    @JsonProperty("files")
    public List<ChatMessageFile> getFiles() {
        return files;
    }

    public void setFiles(List<ChatMessageFile> files) {
        this.files = files;
    }

    @JsonProperty("user_messages")
    public String getUserMessages() {
        return userMessages;
    }

    public void setUserMessages(String userMessages) {
        this.userMessages = userMessages;
    }

    @Override
    public int compareTo(ChatMessage o) {
        return this.chatMessageId.compareTo(o.chatMessageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatMessageId, chatId, text, userId, userName, ts, files, userMessages);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChatMessage)) {
            return false;
        }
        ChatMessage other = (ChatMessage) obj;
        return Objects.equals(chatMessageId, other.chatMessageId) && Objects.equals(chatId, other.chatId)
                && Objects.equals(text, other.text) && Objects.equals(userId, other.userId)
                && Objects.equals(userName, other.userName) && Objects.equals(ts, other.ts)
                && Objects.equals(files, other.files) && Objects.equals(userMessages, other.userMessages);
    }

    @Override
    public String toString() {
        return "ChatMessage [chatMessageId=" + chatMessageId + ", chatId=" + chatId + ", text=" + text + ", userId="
                + userId + ", userName=" + userName + ", ts=" + ts + ", files=" + files + ", userMessages="
                + userMessages + "]";
    }

}
package com.onevizion.uitest.api.vo.api.v3;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatMessageFile implements Comparable<ChatMessageFile> {

    private String blobDataId;
    private String fileName;
    private String fileSize;
    private String fileType;

    @JsonProperty("blob_data_id")
    public String getBlobDataId() {
        return blobDataId;
    }

    public void setBlobDataId(String blobDataId) {
        this.blobDataId = blobDataId;
    }

    @JsonProperty("file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @JsonProperty("file_size")
    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    @JsonProperty("file_type")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public int compareTo(ChatMessageFile o) {
        return this.blobDataId.compareTo(o.blobDataId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blobDataId, fileName, fileSize, fileType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChatMessageFile)) {
            return false;
        }
        ChatMessageFile other = (ChatMessageFile) obj;
        return Objects.equals(blobDataId, other.blobDataId) && Objects.equals(fileName, other.fileName)
                && Objects.equals(fileSize, other.fileSize) && Objects.equals(fileType, other.fileType);
    }

    @Override
    public String toString() {
        return "ChatMessageFile [blobDataId=" + blobDataId + ", fileName=" + fileName + ", fileSize=" + fileSize
                + ", fileType=" + fileType + "]";
    }

}
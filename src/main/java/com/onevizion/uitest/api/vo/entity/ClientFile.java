package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class ClientFile {

    private String fileGroup;
    private String fileName;

    private ClientFile() {
        
    }

    public static Builder newBuilder() {
        return new ClientFile().new Builder();
    }

    public String getFileGroup() {
        return fileGroup;
    }

    public String getFileName() {
        return fileName;
    }

    public class Builder {

        private Builder() {
            
        }

        public ClientFile build() {
            if (ClientFile.this.fileGroup == null ||
                    ClientFile.this.fileName == null) {
                throw new SeleniumUnexpectedException("");
            }
            return ClientFile.this;
        }

        public Builder setFileGroup(String fileGroup) {
            ClientFile.this.fileGroup = fileGroup;
            return this;
        }

        public Builder setFileName(String fileName) {
            ClientFile.this.fileName = fileName;
            return this;
        }

    }

}
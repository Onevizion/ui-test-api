package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class Announcement {

    private String startDate;
    private String finishDate;
    private String text;

    private Announcement() {
        
    }

    public static Builder newBuilder() {
        return new Announcement().new Builder();
    }

    public String getStartDate() {
        return startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public String getText() {
        return text;
    }

    public class Builder {

        private Builder() {
            
        }

        public Announcement build() {
            if (Announcement.this.startDate == null ||
                    Announcement.this.finishDate == null ||
                    Announcement.this.text == null) {
                throw new SeleniumUnexpectedException("");
            }
            return Announcement.this;
        }

        public Builder setStartDate(String startDate) {
            Announcement.this.startDate = startDate;
            return this;
        }

        public Builder setFinishDate(String finishDate) {
            Announcement.this.finishDate = finishDate;
            return this;
        }

        public Builder setText(String text) {
            Announcement.this.text = text;
            return this;
        }

    }

}
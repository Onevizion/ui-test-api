package com.onevizion.uitest.api.vo.api.v3;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pagination<T> implements Comparable<Pagination<T>> {

    private List<T> data;
    private String totalCount;
    private String hasBefore;
    private String beforeCursor;
    private String hasAfter;
    private String afterCursor;

    @JsonProperty("data")
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @JsonProperty("total_count")
    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    @JsonProperty("hasBefore")
    public String getHasBefore() {
        return hasBefore;
    }

    public void setHasBefore(String hasBefore) {
        this.hasBefore = hasBefore;
    }

    @JsonProperty("before_cursor")
    public String getBeforeCursor() {
        return beforeCursor;
    }

    public void setBeforeCursor(String beforeCursor) {
        this.beforeCursor = beforeCursor;
    }

    @JsonProperty("hasAfter")
    public String getHasAfter() {
        return hasAfter;
    }

    public void setHasAfter(String hasAfter) {
        this.hasAfter = hasAfter;
    }

    @JsonProperty("after_cursor")
    public String getAfterCursor() {
        return afterCursor;
    }

    public void setAfterCursor(String afterCursor) {
        this.afterCursor = afterCursor;
    }

    @Override
    public int compareTo(Pagination<T> o) {
        @SuppressWarnings("unchecked")
        int val1 = ((Pagination<T>) this.data).compareTo((Pagination<T>) o.data);
        if (val1 != 0) {
            return val1;
        }
        int val2 = this.totalCount.compareTo(o.totalCount);
        if (val2 != 0) {
            return val2;
        }
        int val3 = this.hasBefore.compareTo(o.hasBefore);
        if (val3 != 0) {
            return val3;
        }
        int val4 = this.beforeCursor.compareTo(o.beforeCursor);
        if (val4 != 0) {
            return val4;
        }
        int val5 = this.hasAfter.compareTo(o.hasAfter);
        if (val5 != 0) {
            return val5;
        }
        return this.afterCursor.compareTo(o.afterCursor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, totalCount, hasBefore, beforeCursor, hasAfter, afterCursor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Pagination)) {
            return false;
        }
        @SuppressWarnings("unchecked")
        Pagination<T> other = (Pagination<T>) obj;
        return Objects.equals(data, other.data) && Objects.equals(totalCount, other.totalCount)
                && Objects.equals(hasBefore, other.hasBefore) && Objects.equals(beforeCursor, other.beforeCursor)
                && Objects.equals(hasAfter, other.hasAfter) && Objects.equals(afterCursor, other.afterCursor);
    }

    @Override
    public String toString() {
        return "Pagination [data=" + data + ", totalCount=" + totalCount + ", hasBefore=" + hasBefore
                + ", beforeCursor=" + beforeCursor + ", hasAfter=" + hasAfter + ", afterCursor=" + afterCursor + "]";
    }

}
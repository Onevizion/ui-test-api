package com.onevizion.uitest.api.vo.api.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class Option implements Comparable<Option> {

    private String id;
    private String value;

    public static Builder newBuilder() {
        return new Option().new Builder();
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public class Builder {

        private Builder() {
            
        }

        public Option build() {
            if (Option.this.id == null ||
                    Option.this.value == null) {
                throw new SeleniumUnexpectedException("");
            }
            return Option.this;
        }

        public Builder setId(String id) {
            Option.this.id = id;
            return this;
        }

        public Builder setValue(String value) {
            Option.this.value = value;
            return this;
        }
    }

    @Override
    public int compareTo(Option o) {
        int val1 = this.id.compareTo(o.id);
        if (val1 != 0) {
            return val1;
        }
        return this.value.compareTo(o.value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Option other = (Option) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Option [id=" + id + ", value=" + value + "]";
    }

}
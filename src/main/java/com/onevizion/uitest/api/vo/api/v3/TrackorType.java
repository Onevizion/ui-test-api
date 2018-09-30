package com.onevizion.uitest.api.vo.api.v3;

public class TrackorType implements Comparable<TrackorType> {

    private String id;
    private String name;
    private String label;
    private String prefix;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public int compareTo(TrackorType o) {
        int val1 = this.id.compareTo(o.id);
        if (val1 != 0) {
            return val1;
        }
        int val2 = this.name.compareTo(o.name);
        if (val2 != 0) {
            return val2;
        }
        int val3 = this.label.compareTo(o.label);
        if (val3 != 0) {
            return val3;
        }
        return this.prefix.compareTo(o.prefix);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
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
        TrackorType other = (TrackorType) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (prefix == null) {
            if (other.prefix != null)
                return false;
        } else if (!prefix.equals(other.prefix))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TrackorType [id=" + id + ", name=" + name + ", label=" + label + ", prefix=" + prefix + "]";
    }

}
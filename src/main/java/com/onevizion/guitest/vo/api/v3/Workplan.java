package com.onevizion.guitest.vo.api.v3;

public class Workplan implements Comparable<Workplan> {

    private String id;
    private String name;
    private String template_id;
    private String template_name;
    private String proj_start_date;
    private String proj_finish_date;
    private String trackor_id;
    private String active;

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

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public String getProj_start_date() {
        return proj_start_date;
    }

    public void setProj_start_date(String proj_start_date) {
        this.proj_start_date = proj_start_date;
    }

    public String getProj_finish_date() {
        return proj_finish_date;
    }

    public void setProj_finish_date(String proj_finish_date) {
        this.proj_finish_date = proj_finish_date;
    }

    public String getTrackor_id() {
        return trackor_id;
    }

    public void setTrackor_id(String trackor_id) {
        this.trackor_id = trackor_id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public int compareTo(Workplan o) {
        int val1 = this.id.compareTo(o.id);
        if (val1 != 0) {
            return val1;
        }
        int val2 = this.name.compareTo(o.name);
        if (val2 != 0) {
            return val2;
        }
        int val3 = this.template_id.compareTo(o.template_id);
        if (val3 != 0) {
            return val3;
        }
        int val4 = this.template_name.compareTo(o.template_name);
        if (val4 != 0) {
            return val4;
        }
        int val5 = this.proj_start_date.compareTo(o.proj_start_date);
        if (val5 != 0) {
            return val5;
        }
        int val6 = this.proj_finish_date.compareTo(o.proj_finish_date);
        if (val6 != 0) {
            return val6;
        }
        int val7 = this.trackor_id.compareTo(o.trackor_id);
        if (val7 != 0) {
            return val7;
        }
        int val8 = this.active.compareTo(o.active);
        return val8;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((active == null) ? 0 : active.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((proj_finish_date == null) ? 0 : proj_finish_date.hashCode());
        result = prime * result + ((proj_start_date == null) ? 0 : proj_start_date.hashCode());
        result = prime * result + ((template_id == null) ? 0 : template_id.hashCode());
        result = prime * result + ((template_name == null) ? 0 : template_name.hashCode());
        result = prime * result + ((trackor_id == null) ? 0 : trackor_id.hashCode());
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
        Workplan other = (Workplan) obj;
        if (active == null) {
            if (other.active != null)
                return false;
        } else if (!active.equals(other.active))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (proj_finish_date == null) {
            if (other.proj_finish_date != null)
                return false;
        } else if (!proj_finish_date.equals(other.proj_finish_date))
            return false;
        if (proj_start_date == null) {
            if (other.proj_start_date != null)
                return false;
        } else if (!proj_start_date.equals(other.proj_start_date))
            return false;
        if (template_id == null) {
            if (other.template_id != null)
                return false;
        } else if (!template_id.equals(other.template_id))
            return false;
        if (template_name == null) {
            if (other.template_name != null)
                return false;
        } else if (!template_name.equals(other.template_name))
            return false;
        if (trackor_id == null) {
            if (other.trackor_id != null)
                return false;
        } else if (!trackor_id.equals(other.trackor_id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Workplan [id=" + id + ", name=" + name + ", template_id=" + template_id + ", template_name="
                + template_name + ", proj_start_date=" + proj_start_date + ", proj_finish_date=" + proj_finish_date
                + ", trackor_id=" + trackor_id + ", active=" + active + "]";
    }

}
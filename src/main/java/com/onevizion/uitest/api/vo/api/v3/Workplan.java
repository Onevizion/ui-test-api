package com.onevizion.uitest.api.vo.api.v3;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Workplan implements Comparable<Workplan> {

    private String id;
    private String name;
    private String templateId;
    private String templateName;
    private String projStartDate;
    private String projFinishDate;
    private String trackorId;
    private String active;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("template_id")
    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @JsonProperty("template_name")
    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @JsonProperty("proj_start_date")
    public String getProjStartDate() {
        return projStartDate;
    }

    public void setProjStartDate(String projStartDate) {
        this.projStartDate = projStartDate;
    }

    @JsonProperty("proj_finish_date")
    public String getProjFinishDate() {
        return projFinishDate;
    }

    public void setProjFinishDate(String projFinishDate) {
        this.projFinishDate = projFinishDate;
    }

    @JsonProperty("trackor_id")
    public String getTrackorId() {
        return trackorId;
    }

    public void setTrackorId(String trackorId) {
        this.trackorId = trackorId;
    }

    @JsonProperty("active")
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
        int val3 = this.templateId.compareTo(o.templateId);
        if (val3 != 0) {
            return val3;
        }
        int val4 = this.templateName.compareTo(o.templateName);
        if (val4 != 0) {
            return val4;
        }
        int val5 = this.projStartDate.compareTo(o.projStartDate);
        if (val5 != 0) {
            return val5;
        }
        int val6 = this.projFinishDate.compareTo(o.projFinishDate);
        if (val6 != 0) {
            return val6;
        }
        int val7 = this.trackorId.compareTo(o.trackorId);
        if (val7 != 0) {
            return val7;
        }
        return this.active.compareTo(o.active);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((active == null) ? 0 : active.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((projFinishDate == null) ? 0 : projFinishDate.hashCode());
        result = prime * result + ((projStartDate == null) ? 0 : projStartDate.hashCode());
        result = prime * result + ((templateId == null) ? 0 : templateId.hashCode());
        result = prime * result + ((templateName == null) ? 0 : templateName.hashCode());
        result = prime * result + ((trackorId == null) ? 0 : trackorId.hashCode());
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
        if (projFinishDate == null) {
            if (other.projFinishDate != null)
                return false;
        } else if (!projFinishDate.equals(other.projFinishDate))
            return false;
        if (projStartDate == null) {
            if (other.projStartDate != null)
                return false;
        } else if (!projStartDate.equals(other.projStartDate))
            return false;
        if (templateId == null) {
            if (other.templateId != null)
                return false;
        } else if (!templateId.equals(other.templateId))
            return false;
        if (templateName == null) {
            if (other.templateName != null)
                return false;
        } else if (!templateName.equals(other.templateName))
            return false;
        if (trackorId == null) {
            if (other.trackorId != null)
                return false;
        } else if (!trackorId.equals(other.trackorId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Workplan [id=" + id + ", name=" + name + ", templateId=" + templateId + ", templateName="
                + templateName + ", projStartDate=" + projStartDate + ", projFinishDate=" + projFinishDate
                + ", trackorId=" + trackorId + ", active=" + active + "]";
    }

}
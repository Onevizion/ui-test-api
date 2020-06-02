package com.onevizion.uitest.api.vo.entity;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class SecurityRoleDefaultAssignments {

    private String discipline;
    private String menuAppliction;
    private String globalView;
    private String globalFilter;
    private String globalPortal;
    private String rule;
    private String importt;
    private String report;
    private String globalNotif;
    private String chatNotif;
    private String trackorTour;

    private SecurityRoleDefaultAssignments() {
        
    }

    public static Builder newBuilder() {
        return new SecurityRoleDefaultAssignments().new Builder();
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getMenuAppliction() {
        return menuAppliction;
    }

    public String getGlobalView() {
        return globalView;
    }
    public String getGlobalFilter() {
        return globalFilter;
    }

    public String getGlobalPortal() {
        return globalPortal;
    }

    public String getRule() {
        return rule;
    }

    public String getImportt() {
        return importt;
    }

    public String getReport() {
        return report;
    }

    public String getGlobalNotif() {
        return globalNotif;
    }

    public String getChatNotif() {
        return chatNotif;
    }

    public String getTrackorTour() {
        return trackorTour;
    }

    public class Builder {

        private Builder() {
            
        }

        public SecurityRoleDefaultAssignments build() {
            if (SecurityRoleDefaultAssignments.this.discipline == null ||
                    SecurityRoleDefaultAssignments.this.menuAppliction == null ||
                    SecurityRoleDefaultAssignments.this.globalView == null ||
                    SecurityRoleDefaultAssignments.this.globalFilter == null ||
                    SecurityRoleDefaultAssignments.this.globalPortal == null ||
                    SecurityRoleDefaultAssignments.this.rule == null ||
                    SecurityRoleDefaultAssignments.this.importt == null ||
                    SecurityRoleDefaultAssignments.this.report == null ||
                    SecurityRoleDefaultAssignments.this.globalNotif == null ||
                    SecurityRoleDefaultAssignments.this.chatNotif == null ||
                    SecurityRoleDefaultAssignments.this.trackorTour == null) {
                throw new SeleniumUnexpectedException("");
            }
            return SecurityRoleDefaultAssignments.this;
        }

        public Builder setDiscipline(String discipline) {
            SecurityRoleDefaultAssignments.this.discipline = discipline;
            return this;
        }

        public Builder setMenuAppliction(String menuAppliction) {
            SecurityRoleDefaultAssignments.this.menuAppliction = menuAppliction;
            return this;
        }

        public Builder setGlobalView(String globalView) {
            SecurityRoleDefaultAssignments.this.globalView = globalView;
            return this;
        }

        public Builder setGlobalFilter(String globalFilter) {
            SecurityRoleDefaultAssignments.this.globalFilter = globalFilter;
            return this;
        }

        public Builder setGlobalPortal(String globalPortal) {
            SecurityRoleDefaultAssignments.this.globalPortal = globalPortal;
            return this;
        }

        public Builder setRule(String rule) {
            SecurityRoleDefaultAssignments.this.rule = rule;
            return this;
        }

        public Builder setImportt(String importt) {
            SecurityRoleDefaultAssignments.this.importt = importt;
            return this;
        }

        public Builder setReport(String report) {
            SecurityRoleDefaultAssignments.this.report = report;
            return this;
        }

        public Builder setGlobalNotif(String globalNotif) {
            SecurityRoleDefaultAssignments.this.globalNotif = globalNotif;
            return this;
        }

        public Builder setChatNotif(String chatNotif) {
            SecurityRoleDefaultAssignments.this.chatNotif = chatNotif;
            return this;
        }

        public Builder setTrackorTour(String trackorTour) {
            SecurityRoleDefaultAssignments.this.trackorTour = trackorTour;
            return this;
        }

    }

}
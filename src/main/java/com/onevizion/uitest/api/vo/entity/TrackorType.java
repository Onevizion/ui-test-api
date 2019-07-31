package com.onevizion.uitest.api.vo.entity;

import java.util.List;

public class TrackorType {

    private String name;
    private String label;
    private String labelItemId;
    private String labelClass;
    private String labelPrefix;
    private String labelMyItems;
    private String aliasField;
    private List<String> autoFilterFields;
    private String limitWp;
    private String clone;
    private String template;
    private String user;
    private String efileContainer;
    private String comments;

    private String autoKey;
    private String autoKeyStartAt;
    private String owner1;
    private String field1;
    private String separator1;
    private String owner2;
    private String field2;
    private String separator2;
    private String staticText3;
    private String separator3;
    private String digits4;
    private String uniqueAcross4;

    private List<String> packages;

    public TrackorType(String name, String label, String labelItemId, String labelClass, String labelPrefix, String labelMyItems, String aliasField,
            List<String> autoFilterFields, String limitWp, String clone, String template, String user, String efileContainer, String comments,
            String autoKey, String autoKeyStartAt, String owner1, String field1, String separator1, String owner2, String field2,
            String separator2, String staticText3, String separator3, String digits4, String uniqueAcross4, List<String> packages) {
        this.name = name;
        this.label = label;
        this.labelItemId = labelItemId;
        this.labelClass = labelClass;
        this.labelPrefix = labelPrefix;
        this.labelMyItems = labelMyItems;
        this.aliasField = aliasField;
        this.autoFilterFields = autoFilterFields;
        this.limitWp = limitWp;
        this.clone = clone;
        this.template = template;
        this.user = user;
        this.efileContainer = efileContainer;
        this.comments = comments;

        this.autoKey = autoKey;
        this.autoKeyStartAt = autoKeyStartAt;
        this.owner1 = owner1;
        this.field1 = field1;
        this.separator1 = separator1;
        this.owner2 = owner2;
        this.field2 = field2;
        this.separator2 = separator2;
        this.staticText3 = staticText3;
        this.separator3 = separator3;
        this.digits4 = digits4;
        this.uniqueAcross4 = uniqueAcross4;

        this.packages = packages;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getLabelItemId() {
        return labelItemId;
    }

    public String getLabelClass() {
        return labelClass;
    }

    public String getLabelPrefix() {
        return labelPrefix;
    }

    public String getLabelMyItems() {
        return labelMyItems;
    }

    public String getAliasField() {
        return aliasField;
    }

    public List<String> getAutoFilterFields() {
        return autoFilterFields;
    }

    public String getLimitWp() {
        return limitWp;
    }

    public String getClone() {
        return clone;
    }

    public String getTemplate() {
        return template;
    }

    public String getUser() {
        return user;
    }

    public String getEfileContainer() {
        return efileContainer;
    }

    public String getComments() {
        return comments;
    }

    public String getAutoKey() {
        return autoKey;
    }

    public String getAutoKeyStartAt() {
        return autoKeyStartAt;
    }

    public String getOwner1() {
        return owner1;
    }

    public String getField1() {
        return field1;
    }

    public String getSeparator1() {
        return separator1;
    }

    public String getOwner2() {
        return owner2;
    }

    public String getField2() {
        return field2;
    }

    public String getSeparator2() {
        return separator2;
    }

    public String getStaticText3() {
        return staticText3;
    }

    public String getSeparator3() {
        return separator3;
    }

    public String getDigits4() {
        return digits4;
    }

    public String getUniqueAcross4() {
        return uniqueAcross4;
    }

    public List<String> getPackages() {
        return packages;
    }

}
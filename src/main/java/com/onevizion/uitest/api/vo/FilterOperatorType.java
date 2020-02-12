package com.onevizion.uitest.api.vo;

import java.util.Arrays;
import java.util.List;

public enum FilterOperatorType {

    EQUAL("=", "equal"),
    NOT_EQUAL("<>", "not_equal"),
    MORE(">", "greater"),
    MORE_AND_EQUAL(">=", "greater_or_equal"),
    LESS("<", "less"),
    LESS_AND_EQUAL("<=", "less_or_equal"),
    EQUAL_AND_EMPTY_FOR_OTHER("(+)=", "outer_equal"),
    NOT_EQUAL_AND_EMPTY_FOR_OTHER("(+)<>", "outer_not_equal"),
    NULL("Is Null", "null"),
    NOT_NULL("Is Not Null", "is_not_null"),
    EQUAL_FIELD("=Field", "field_equal"),
    NOT_EQUAL_FIELD("<>Field", "field_not_equal"),
    MORE_FIELD(">Field", "field_greater"),
    MORE_AND_EQUAL_FIELD(">=Field", "field_greater_or_equal"),
    LESS_FIELD("<Field", "field_less"),
    LESS_AND_EQUAL_FIELD("<=Field", "field_less_or_equal"),
    NEW("Is New", "new"),
    NOT_NEW("Is Not New", "not_new"),
    MORE_AND_EQUAL_TODAY(">=Today", "gt_today"),
    LESS_AND_EQUAL_TODAY("<=Today", "lt_today"),
    WITHIN("Within", "within"),
    THIS_WK("This Wk", "this_week"),
    THIS_MO("This Mo", "this_month"),
    THIS_FQ("This FQ", "this_quarter"),
    THIS_FY("This FY", "this_year"),
    THIS_WK_TO_DT("This Wk to Dt", "this_week_to_date"),
    THIS_MO_TO_DT("This Mo to Dt", "this_month_to_date"),
    THIS_FQ_TO_DT("This FQ to Dt", "this_quarter_to_date"),
    THIS_FY_TO_DT("This FY to Dt", "this_year_to_date"),
    FIELD_LOCK("Is Field Locked", "field_locked"),
    FIELD_UNLOCK("Is Field Unlocked", "field_unlocked");

    private String value;
    private String valueApiV3;

    private FilterOperatorType(String value, String valueApiV3) {
        this.value = value;
        this.valueApiV3 = valueApiV3;
    }

    public String getValue() {
        return value;
    }

    public String getValueApiV3() {
        return valueApiV3;
    }

    public static List<FilterOperatorType> getCalculatedOperators() {
        return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL);
    }

    public static List<FilterOperatorType> getCheckboxOperators(boolean supportOuterOperations) {
        if (supportOuterOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER);
        } else {
            return Arrays.asList(EQUAL);
        }
    }

    public static List<FilterOperatorType> getDateOperators(boolean supportOuterOperations, boolean supportFieldOperations) {
        if (supportOuterOperations && supportFieldOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, MORE_AND_EQUAL_TODAY, LESS_AND_EQUAL_TODAY,
                    WITHIN, THIS_WK, THIS_WK_TO_DT, THIS_MO, THIS_MO_TO_DT, THIS_FQ, THIS_FQ_TO_DT, THIS_FY, THIS_FY_TO_DT, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER,
                    NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD, MORE_FIELD, LESS_FIELD, MORE_AND_EQUAL_FIELD, LESS_AND_EQUAL_FIELD);
        } else if (supportOuterOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, MORE_AND_EQUAL_TODAY, LESS_AND_EQUAL_TODAY, WITHIN, THIS_WK,
                    THIS_WK_TO_DT, THIS_MO, THIS_MO_TO_DT, THIS_FQ, THIS_FQ_TO_DT, THIS_FY, THIS_FY_TO_DT, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL);
        } else if (supportFieldOperations) {
            return Arrays.asList(EQUAL, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, MORE_AND_EQUAL_TODAY, LESS_AND_EQUAL_TODAY, WITHIN, THIS_WK,
                    THIS_WK_TO_DT, THIS_MO, THIS_MO_TO_DT, THIS_FQ, THIS_FQ_TO_DT, THIS_FY, THIS_FY_TO_DT, NOT_EQUAL, NULL, NOT_NULL, EQUAL_FIELD,
                    NOT_EQUAL_FIELD, MORE_FIELD, LESS_FIELD, MORE_AND_EQUAL_FIELD, LESS_AND_EQUAL_FIELD);
        } else {
            return Arrays.asList(EQUAL, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, MORE_AND_EQUAL_TODAY, LESS_AND_EQUAL_TODAY, WITHIN, THIS_WK,
                    THIS_WK_TO_DT, THIS_MO, THIS_MO_TO_DT, THIS_FQ, THIS_FQ_TO_DT, THIS_FY, THIS_FY_TO_DT, NOT_EQUAL, NULL, NOT_NULL);
        }
    }

    public static List<FilterOperatorType> getDateTimeOperators(boolean supportFieldOperations) {
        if (supportFieldOperations) {
            return Arrays.asList(EQUAL, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, MORE_AND_EQUAL_TODAY, LESS_AND_EQUAL_TODAY,
                    WITHIN, THIS_WK, THIS_WK_TO_DT, THIS_MO, THIS_MO_TO_DT, THIS_FQ, THIS_FQ_TO_DT, THIS_FY, THIS_FY_TO_DT,
                    NOT_EQUAL, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD, MORE_FIELD, LESS_FIELD, MORE_AND_EQUAL_FIELD, LESS_AND_EQUAL_FIELD);
        } else {
            return Arrays.asList(EQUAL, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, MORE_AND_EQUAL_TODAY, LESS_AND_EQUAL_TODAY,
                    WITHIN, THIS_WK, THIS_WK_TO_DT, THIS_MO, THIS_MO_TO_DT, THIS_FQ, THIS_FQ_TO_DT, THIS_FY, THIS_FY_TO_DT, NOT_EQUAL, NULL, NOT_NULL);
        }
    }

    public static List<FilterOperatorType> getDbDropDownOperators() {
        return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL);
    }

    public static List<FilterOperatorType> getDropDownOperators(boolean supportOuterOperations, boolean supportFieldOperations) {
        if (supportOuterOperations && supportFieldOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD);
        } else if (supportOuterOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL);
        } else if (supportFieldOperations) {
            return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD);
        } else {
            return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL);
        }
    }

    public static List<FilterOperatorType> getElectronicFileOperators() {
        return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL);
    }

    public static List<FilterOperatorType> getEpmDbSelectorOperators() {
        return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL);
    }

    public static List<FilterOperatorType> getHyperlinkOperators() {
        return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL);
    }

    public static List<FilterOperatorType> getLatitudeOperators(boolean supportOuterOperations, boolean supportFieldOperations) {
        if (supportOuterOperations && supportFieldOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER,
                    NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD, MORE_FIELD, LESS_FIELD, MORE_AND_EQUAL_FIELD, LESS_AND_EQUAL_FIELD);
        } else if (supportOuterOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL);
        } else if (supportFieldOperations) {
            return Arrays.asList(EQUAL, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, NOT_EQUAL, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD, MORE_FIELD,
                    LESS_FIELD, MORE_AND_EQUAL_FIELD, LESS_AND_EQUAL_FIELD);
        } else {
            return Arrays.asList(EQUAL, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, NOT_EQUAL, NULL, NOT_NULL);
        }
    }

    public static List<FilterOperatorType> getLongitudeOperators(boolean supportOuterOperations, boolean supportFieldOperations) {
        if (supportOuterOperations && supportFieldOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER,
                    NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD, MORE_FIELD, LESS_FIELD, MORE_AND_EQUAL_FIELD, LESS_AND_EQUAL_FIELD);
        } else if (supportOuterOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER,
                    NULL, NOT_NULL);
        } else if (supportFieldOperations) {
            return Arrays.asList(EQUAL, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, NOT_EQUAL, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD, MORE_FIELD,
                    LESS_FIELD, MORE_AND_EQUAL_FIELD, LESS_AND_EQUAL_FIELD);
        } else {
            return Arrays.asList(EQUAL, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, NOT_EQUAL, NULL, NOT_NULL);
        }
    }

    public static List<FilterOperatorType> getMemoOperators(boolean supportOuterOperations) {
        if (supportOuterOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL);
        } else {
            return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL);
        }
    }

    public static List<FilterOperatorType> getMultiSelectorOperators() {
        return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL);
    }

    public static List<FilterOperatorType> getNumberOperators(boolean supportOuterOperations, boolean supportFieldOperations) {
        if (supportOuterOperations && supportFieldOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER,
                    NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD, MORE_FIELD, LESS_FIELD, MORE_AND_EQUAL_FIELD, LESS_AND_EQUAL_FIELD);
        } else if (supportOuterOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL);
        } else if (supportFieldOperations) {
            return Arrays.asList(EQUAL, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, NOT_EQUAL, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD, MORE_FIELD,
                    LESS_FIELD, MORE_AND_EQUAL_FIELD, LESS_AND_EQUAL_FIELD);
        } else {
            return Arrays.asList(EQUAL, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, NOT_EQUAL, NULL, NOT_NULL);
        }
    }

    public static List<FilterOperatorType> getPsSelectorOperators(boolean supportOuterOperations, boolean supportFieldOperations) {
        if (supportOuterOperations && supportFieldOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD);
        } else if (supportOuterOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL);
        } else if (supportFieldOperations) {
            return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD);
        } else {
            return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL);
        }
    }

    public static List<FilterOperatorType> getPsTrackorSelectorOperators(boolean supportOuterOperations, boolean supportFieldOperations) {
        if (supportOuterOperations && supportFieldOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD, NEW, NOT_NEW);
        } else if (supportOuterOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL, NEW, NOT_NEW);
        } else if (supportFieldOperations) {
            return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD, NEW, NOT_NEW);
        } else {
            return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL, NEW, NOT_NEW);
        }
    }

    public static List<FilterOperatorType> getMultiTrackorSelectorOperators() {
        return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL, NEW, NOT_NEW);
    }

    public static List<FilterOperatorType> getRollupOperators() {
        return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL);
    }

    public static List<FilterOperatorType> getTaskDateOperators() {
        return Arrays.asList(EQUAL, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, MORE_AND_EQUAL_TODAY, LESS_AND_EQUAL_TODAY, WITHIN, THIS_WK,
                THIS_WK_TO_DT, THIS_MO, THIS_MO_TO_DT, THIS_FQ, THIS_FQ_TO_DT, THIS_FY, THIS_FY_TO_DT, NOT_EQUAL, NULL, NOT_NULL);
    }

    public static List<FilterOperatorType> getTextOperators(boolean supportOuterOperations, boolean supportFieldOperations) {
        if (supportOuterOperations && supportFieldOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD);
        } else if (supportOuterOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL);
        } else if (supportFieldOperations) {
            return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD);
        } else {
            return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL);
        }
    }

    public static List<FilterOperatorType> getTimeOperators(boolean supportFieldOperations) {
        if (supportFieldOperations) {
            return Arrays.asList(EQUAL, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, NOT_EQUAL, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD,
                    MORE_FIELD, LESS_FIELD, MORE_AND_EQUAL_FIELD, LESS_AND_EQUAL_FIELD);
        } else {
            return Arrays.asList(EQUAL, MORE, LESS, MORE_AND_EQUAL, LESS_AND_EQUAL, NOT_EQUAL, NULL, NOT_NULL);
        }
    }

    public static List<FilterOperatorType> getTrackorDropDownOperators() {
        return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL, NEW, NOT_NEW);
    }

    public static List<FilterOperatorType> getWikiOperators() {
        return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL);
    }

    public static List<FilterOperatorType> getWpNameOperators() {
        return Arrays.asList(EQUAL);
    }

    public static List<FilterOperatorType> getWpTemplateNameOperators() {
        return Arrays.asList(EQUAL);
    }

    public static List<FilterOperatorType> getXitorClassOperators(boolean supportOuterOperations) {
        if (supportOuterOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL);
        } else {
            return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL);
        }
    }

    public static List<FilterOperatorType> getXitorKeyOperators(boolean supportOuterOperations, boolean supportFieldOperations) {
        if (supportOuterOperations && supportFieldOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD, NEW, NOT_NEW);
        } else if (supportOuterOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL, NEW, NOT_NEW);
        } else if (supportFieldOperations) {
            return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL, EQUAL_FIELD, NOT_EQUAL_FIELD, NEW, NOT_NEW);
        } else {
            return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL, NEW, NOT_NEW);
        }
    }

}
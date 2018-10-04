package com.onevizion.uitest.api.vo;

import java.util.Arrays;
import java.util.List;

public enum FilterOperatorType {

    EQUAL("="),
    NOT_EQUAL("<>"),
    MORE(">"),
    MORE_AND_EQUAL(">="),
    LESS("<"),
    LESS_AND_EQUAL("<="),
    EQUAL_AND_EMPTY_FOR_OTHER("(+)="),
    NOT_EQUAL_AND_EMPTY_FOR_OTHER("(+)<>"),
    NULL("Is Null"),
    NOT_NULL("Is Not Null"),
    EQUAL_FIELD("=Field"),
    NOT_EQUAL_FIELD("<>Field"),
    MORE_FIELD(">Field"),
    MORE_AND_EQUAL_FIELD(">=Field"),
    LESS_FIELD("<Field"),
    LESS_AND_EQUAL_FIELD("<=Field"),
    NEW("Is New"),
    NOT_NEW("Is Not New"),
    MORE_AND_EQUAL_TODAY(">=Today"),
    LESS_AND_EQUAL_TODAY("<=Today"),
    WITHIN("Within"),
    THIS_WK("This Wk"),
    THIS_MO("This Mo"),
    THIS_FQ("This FQ"),
    THIS_FY("This FY"),
    THIS_WK_TO_DT("This Wk to Dt"),
    THIS_MO_TO_DT("This Mo to Dt"),
    THIS_FQ_TO_DT("This FQ to Dt"),
    THIS_FY_TO_DT("This FY to Dt");

    private String value;

    private FilterOperatorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static List<FilterOperatorType> getCalculatedOperators(boolean supportOuterOperations) {
        if (supportOuterOperations) {
            return Arrays.asList(EQUAL, EQUAL_AND_EMPTY_FOR_OTHER, NOT_EQUAL, NOT_EQUAL_AND_EMPTY_FOR_OTHER, NULL, NOT_NULL);
        } else {
            return Arrays.asList(EQUAL, NOT_EQUAL, NULL, NOT_NULL);
        }
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
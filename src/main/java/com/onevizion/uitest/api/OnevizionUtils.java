package com.onevizion.uitest.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OnevizionUtils {

    private OnevizionUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String removeHTMLTags(String str) {
        if (str == null) {
            return null;
        }
        String patternString = "</?[a-z][a-z0-9]*[^<>]*>";
        return str.replaceAll(patternString, "");
    }

    public static Date strToDate(String date, String format) {
        if (date != null && !date.isEmpty()) {
            String newFormat = format.replace('D', 'd').replace('Y', 'y');
            SimpleDateFormat dateFormat = new SimpleDateFormat(newFormat);
            try {
                return dateFormat.parse(date);
            } catch (ParseException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String escapeStringForRestApiV3(String originalString) {
        String escapedString = originalString.replaceAll("\\\\", "\\\\\\\\");
        escapedString = escapedString.replaceAll("\\n", "\\\\n");
        escapedString = escapedString.replaceAll("\\t", "\\\\t");
        escapedString = escapedString.replaceAll("\\r", "\\\\r");
        return escapedString.replaceAll("\"", "'");
    }

}
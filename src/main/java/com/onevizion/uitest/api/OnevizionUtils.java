package com.onevizion.uitest.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OnevizionUtils {

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

}
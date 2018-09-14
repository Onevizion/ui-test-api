package com.onevizion.uitest.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

	public static String dateFmt(Date date, String format) {
        if (date == null || format == null) {
            return "";
        }

        String result = "";

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Integer day = calendar.get(Calendar.DAY_OF_MONTH);
        String strDay = (day < 10) ? "0" + day : day.toString();
        Integer month = calendar.get(Calendar.MONTH) + 1;
        String strMonth = (month < 10) ? "0" + month : month.toString();
        Integer year = calendar.get(Calendar.YEAR);
        String strYear;
        if (format.length() == 8) {
            // Two digit year date format
            year = year - 2000;
            strYear = year.toString();
            if (strYear.length() < 2) {
                strYear = "0" + strYear;
            }
        } else {
            strYear = year.toString();
        }

        switch (format) {
            case "MM/DD/YYYY":
            case "MM/DD/YY":
                result = strMonth + "/" + strDay + "/" + strYear;
                break;
            case "DD/MM/YYYY":
            case "DD/MM/YY":
                result = strDay + "/" + strMonth + "/" + strYear;
                break;
            case "MM.DD.YYYY":
            case "MM.DD.YY":
                result = strMonth + "." + strDay + "." + strYear;
                break;
            case "DD.MM.YYYY":
            case "DD.MM.YY":
                result = strDay + "." + strMonth + "." + strYear;
                break;
        }
        return result;
    }

}
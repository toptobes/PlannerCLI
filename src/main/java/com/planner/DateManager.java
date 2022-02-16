package com.planner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateManager {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static String format = "dd-MM-yyyy";

    public static String getFormat() {
        return format;
    }

    public static void setFormat(String format) {
        DateManager.format = format;
    }

    public static long toUnixTimestamp(String time) throws ParseException {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        return dateFormat.parse(time).getTime() / 1000;
    }
}

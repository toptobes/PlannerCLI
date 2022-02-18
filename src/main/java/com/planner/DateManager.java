package com.planner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateManager {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static String format = "MM-dd-yyyy";
    private static TimeZone timeZone = Calendar.getInstance().getTimeZone();

    public static long toUnixTimestamp(String time) throws ParseException {
        dateFormat.setTimeZone(timeZone);
        return dateFormat.parse(time).getTime() / 1000;
    }

    public static String toFormattedDate(long time) throws ParseException {
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(new Date(time));
    }

    public static String getFormat() {
        return format;
    }

    public static void setFormat(String format) {
        DateManager.format = format;
    }

    public static TimeZone getTimeZone() {
        return timeZone;
    }

    public static void setTimeZone(TimeZone timeZone) {
        DateManager.timeZone = timeZone;
    }
}

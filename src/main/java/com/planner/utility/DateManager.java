package com.planner.utility;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateManager {
    private static ZoneId timeZone = ZoneId.systemDefault();
    private static ZoneOffset timeZoneOffset = timeZone.getRules().getOffset(LocalDateTime.now());
    private static String format;
    private static DateTimeFormatter dateFormat;

    public static Instant toInstant(String time) /*throws DateTimeParseException*/ {
        return ZonedDateTime.parse(time, dateFormat).toInstant();
    }

    public static String toFormattedDate(Instant instance) {
        return dateFormat.toFormat().format(ZonedDateTime.ofInstant(instance, timeZoneOffset));
    }

    public static void setFormat(String format) throws IllegalArgumentException {
        if (format == null) {
            format = "MM.dd.yyyy - HH:mm";
        }

        DateManager.format = format;

        dateFormat = new DateTimeFormatterBuilder()
                .appendPattern(format)
                .parseDefaulting(ChronoField.YEAR, ZonedDateTime.now().getYear())
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, ZonedDateTime.now().getMonthValue())
                .parseDefaulting(ChronoField.DAY_OF_MONTH, ZonedDateTime.now().getDayOfMonth())
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter()
                .withZone(timeZone);
    }

    public static void setTimeZone(ZoneId timeZone) {
        DateManager.timeZone = timeZone;
        DateManager.timeZoneOffset = timeZone.getRules().getOffset(LocalDateTime.now());
    }

    public static String getFormat() {
        return format;
    }

    public static ZoneId getTimeZone() {
        return timeZone;
    }

    public static ZoneOffset getTimeZoneOffset() {
        return timeZoneOffset;
    }


    public static DateTimeFormatter getDateFormat() {
        return dateFormat;
    }

    public static void setDateFormat(DateTimeFormatter dateFormat) {
        DateManager.dateFormat = dateFormat;
    }

    private DateManager() {
        throw new IllegalStateException("Utility class");
    }
}

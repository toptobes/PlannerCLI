package com.planner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Test {
    public static void main(String[] args) {
        var dateFormat = new DateTimeFormatterBuilder()
                .appendPattern("MM.dd.yyyy - HH:mm")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter()
                .withZone(ZoneId.systemDefault());

        ZonedDateTime date = ZonedDateTime.now();
        System.out.println(DateTimeFormatter.ofPattern("dd/MM/yyy - hh:mm").format(date));

        System.out.println(ZonedDateTime.of(LocalDateTime.parse("02.20.2022 - 11:35", dateFormat), ZoneId.systemDefault()));

        System.out.println(ZonedDateTime.now());

        var timeZoneOffset = ZoneId.systemDefault().getRules().getOffset(LocalDateTime.now());

        System.out.println(dateFormat.toFormat().format(ZonedDateTime.ofInstant(ZonedDateTime.parse("02.20.2022 - 11:35", dateFormat).toInstant(), timeZoneOffset)));
    }
}

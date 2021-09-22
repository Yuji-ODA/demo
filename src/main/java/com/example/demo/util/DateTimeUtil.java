package com.example.demo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtil {

    public static ZonedDateTime localDateTime2zonedDateTime(LocalDateTime from) {
        return from.atZone(ZoneId.systemDefault());
    }

    public static OffsetDateTime localDateTime2offsetDateTime(LocalDateTime from) {
        return OffsetDateTime.of(from, defaultZoneOffset());
    }

    public static LocalDateTime offsetDateTime2LocalDateTime(OffsetDateTime from) {
        return from.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime zonedDateTime2LocalDateTime(ZonedDateTime from) {
        return from.toLocalDateTime();
    }

    public static LocalDate date2LocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date localDate2Date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().toInstant(defaultZoneOffset()));
    }

    public static java.sql.Date localDate2SqlDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    public static ZoneOffset defaultZoneOffset() {
        return ZoneId.systemDefault().getRules().getOffset(LocalDateTime.MAX);
    }
}

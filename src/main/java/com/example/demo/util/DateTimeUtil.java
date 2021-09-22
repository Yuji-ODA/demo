package com.example.demo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtil {

    /**
     *　LocalDateTimeからZonedDateTimeへの変換
     * @param from 変換するLocalDateTime
     * @return 変換されたZonedDateTime
     */
    @NonNull
    public static ZonedDateTime localDateTime2zonedDateTime(@NonNull LocalDateTime from) {
        return from.atZone(ZoneId.systemDefault());
    }

    /**
     *　ZonedDateTimeからLocalDateTimeへの変換
     * @param from 変換するZonedDateTime
     * @return 変換されたLocalDateTime
     */
    @NonNull
    public static LocalDateTime zonedDateTime2LocalDateTime(@NonNull ZonedDateTime from) {
        return LocalDateTime.ofInstant(from.toInstant(), ZoneId.systemDefault());
    }

    /**
     *　LocalDateTimeからOffsetDateTimeへの変換
     * @param from 変換するLocalDateTime
     * @return 変換されたOffsetDateTime
     */
    @NonNull
    public static OffsetDateTime localDateTime2offsetDateTime(@NonNull LocalDateTime from) {
        return localDateTime2zonedDateTime(from).toOffsetDateTime();
    }

    /**
     *　OffsetDateTimeからLocalDateTimeへの変換
     * @param from 変換するOffsetDateTime
     * @return 変換されたLocalDateTime
     */
    @NonNull
    public static LocalDateTime offsetDateTime2LocalDateTime(@NonNull OffsetDateTime from) {
        return LocalDateTime.ofInstant(from.toInstant(), ZoneId.systemDefault());
    }

    /**
     * java.util.DateからLocalDateへの変換
     * @param from 変換するjava.util.DAte
     * @return 変換されたLocalDate
     */
    @NonNull
    public static LocalDate date2LocalDate(@NonNull Date from) {
        return from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     *　LocalDateからjava.util.Dateへの変換
     * @param from 変換するLocalDate
     * @return 変換されたjava.util.Date
     */
    @NonNull
    public static Date localDate2Date(@NonNull LocalDate from) {
        return Date.from(localDateTime2zonedDateTime(from.atStartOfDay()).toInstant());
    }

    /**
     *　LocalDateからjava.sql.Dateへの変換
     * @param from 変換するLocalDate
     * @return 変換されたjava.sql.Date
     */
    @NonNull
    public static java.sql.Date localDate2SqlDate(@NonNull LocalDate from) {
        return java.sql.Date.valueOf(from);
    }
}

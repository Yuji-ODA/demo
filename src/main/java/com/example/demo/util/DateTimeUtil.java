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
    public static ZonedDateTime toZonedDateTime(@NonNull LocalDateTime from) {
        return from.atZone(ZoneId.systemDefault());
    }

    /**
     *　ZonedDateTimeからLocalDateTimeへの変換
     * @param from 変換するZonedDateTime
     * @return 変換されたLocalDateTime
     */
    @NonNull
    public static LocalDateTime toLocalDateTime(@NonNull ZonedDateTime from) {
        return LocalDateTime.ofInstant(from.toInstant(), ZoneId.systemDefault());
    }

    /**
     *　LocalDateTimeからOffsetDateTimeへの変換
     * @param from 変換するLocalDateTime
     * @return 変換されたOffsetDateTime
     */
    @NonNull
    public static OffsetDateTime toOffsetDateTime(@NonNull LocalDateTime from) {
        return from.atOffset(ZoneId.systemDefault().getRules().getOffset(from));
    }

    /**
     *　OffsetDateTimeからLocalDateTimeへの変換
     * @param from 変換するOffsetDateTime
     * @return 変換されたLocalDateTime
     */
    @NonNull
    public static LocalDateTime toLocalDateTime(@NonNull OffsetDateTime from) {
        return LocalDateTime.ofInstant(from.toInstant(), ZoneId.systemDefault());
    }

    /**
     * java.util.DateからLocalDateへの変換
     * @param from 変換するjava.util.Date
     * @return 変換されたLocalDate
     */
    @NonNull
    public static LocalDate toLocalDate(@NonNull Date from) {
        return LocalDate.ofInstant(from.toInstant(), ZoneId.systemDefault());
    }

    /**
     * java.sql.DateからLocalDateへの変換
     * @param from 変換するjava.sql.Date
     * @return 変換されたLocalDate
     */
    @NonNull
    public static LocalDate toLocalDate(@NonNull java.sql.Date from) {
        // java.sql.Date#toInstantがUnsupportedExceptionになるのでこれを回避する
        return toLocalDate(new Date(from.getTime()));
    }

    /**
     *　LocalDateからjava.util.Dateへの変換
     * @param from 変換するLocalDate
     * @return 変換されたjava.util.Date
     */
    @NonNull
    public static Date toDate(@NonNull LocalDate from) {
        return Date.from(toZonedDateTime(from.atStartOfDay()).toInstant());
    }

    /**
     *　LocalDateからjava.sql.Dateへの変換
     * @param from 変換するLocalDate
     * @return 変換されたjava.sql.Date
     */
    @NonNull
    public static java.sql.Date toSqlDate(@NonNull LocalDate from) {
        return java.sql.Date.valueOf(from);
    }
}

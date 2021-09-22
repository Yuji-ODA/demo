package com.example.demo.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.example.demo.util.DateTimeUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

class DateTimeUtilTest {

    static OffsetDateTime offsetDateTime;
    static ZonedDateTime zonedDateTime;
    static LocalDateTime localDateTime;
    static Date date;
    static java.sql.Date sqlDate;

    @BeforeAll
    static void setupStatic() {
        offsetDateTime = OffsetDateTime.of(2021, 9, 21, 0, 0, 0, 0, ZoneOffset.UTC);
        zonedDateTime = ZonedDateTime.of(2021, 9, 21, 0, 0, 0, 0, ZoneOffset.UTC);
        localDateTime = LocalDateTime.of(2021, 9, 21, 9, 0, 0, 0);
        date = new GregorianCalendar(2021, Calendar.SEPTEMBER, 21).getTime();
        sqlDate = new java.sql.Date(date.getTime());
    }

    @Test
    void localDateTime2zonedDateTime() {
        assertThat(toZonedDateTime(localDateTime))
                .isEqualTo(zonedDateTime);
    }

    @Test
    void localDateTime2offsetDateTime() {
        assertThat(toOffsetDateTime(localDateTime))
                .isEqualTo(offsetDateTime);
    }

    @Test
    void offsetDateTime2LocalDateTime() {
        assertThat(toLocalDateTime(offsetDateTime))
                .isEqualTo(localDateTime);
    }

    @Test
    void zonedDateTime2LocalDateTime() {
        assertThat(toLocalDateTime(zonedDateTime))
                .isEqualTo(localDateTime);
    }

    @Test
    void date2LocalDate() {
        assertThat(toLocalDate(date))
                .isEqualTo(localDateTime.toLocalDate());
    }

    @Test
    void localDate2Date() {
        assertThat(toDate(localDateTime.toLocalDate()))
                .isEqualTo(date);
    }

    @Test
    void localDate2SqlDate() {
        assertThat(toSqlDate(localDateTime.toLocalDate()))
                .isEqualTo(sqlDate);
    }
}

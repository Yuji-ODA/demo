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
        assertThat(DateTimeUtil.localDateTime2zonedDateTime(localDateTime))
                .isEqualTo(zonedDateTime);
    }

    @Test
    void localDateTime2offsetDateTime() {
        assertThat(DateTimeUtil.localDateTime2offsetDateTime(localDateTime))
                .isEqualTo(offsetDateTime);
    }

    @Test
    void offsetDateTime2LocalDateTime() {
        assertThat(DateTimeUtil.offsetDateTime2LocalDateTime(offsetDateTime))
                .isEqualTo(localDateTime);
    }

    @Test
    void zonedDateTime2LocalDateTime() {
        assertThat(DateTimeUtil.zonedDateTime2LocalDateTime(zonedDateTime))
                .isEqualTo(localDateTime);
    }

    @Test
    void date2LocalDate() {
        assertThat(DateTimeUtil.date2LocalDate(date))
                .isEqualTo(localDateTime.toLocalDate());
    }

    @Test
    void localDate2Date() {
        assertThat(DateTimeUtil.localDate2Date(localDateTime.toLocalDate()))
                .isEqualTo(date);
    }

    @Test
    void localDate2SqlDate() {
        assertThat(DateTimeUtil.localDate2SqlDate(localDateTime.toLocalDate()))
                .isEqualTo(sqlDate);
    }
}
